package com.mikitellurium.telluriumsrandomstuff.common.blockentity;

import com.mikitellurium.telluriumsrandomstuff.client.gui.menu.AlchemixerMenu;
import com.mikitellurium.telluriumsrandomstuff.common.block.AlchemixerBlock;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.PotionMixingRecipe;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlockEntities;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.util.CachedObject;
import com.mikitellurium.telluriumsrandomstuff.util.LogUtils;
import com.mikitellurium.telluriumsrandomstuff.util.MappedItemStackHandler;
import com.mikitellurium.telluriumsrandomstuff.util.WrappedHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;

public class AlchemixerBlockEntity extends AbstractSoulFueledBlockEntity implements MenuProvider {

    private final int bucketSlot = 0;
    private static final int INPUT_SLOT1 = 1;
    private static final int INPUT_SLOT2 = 2;
    private static final int OUTPUT_SLOT = 3;
    private static final Predicate<ItemStack> isValidPotionReceptacle = (itemStack -> {
        Potion potion = PotionUtils.getPotion(itemStack);
        return potion == Potions.THICK || potion == Potions.MUNDANE;
    });
    private final MappedItemStackHandler itemHandler = new MappedItemStackHandler(4,
            (i) -> i == INPUT_SLOT1 || i == INPUT_SLOT2, (i) -> i == OUTPUT_SLOT, (i) -> i == bucketSlot) {

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return (isInput(slot) && stack.getItem() instanceof PotionItem) ||
                    (isOutput(slot) && isValidPotionReceptacle.test(stack)) ||
                    (isBucket(slot) && stack.is(ModItems.SOUL_LAVA_BUCKET.get()));
        }

        @Override
        protected void onContentsChanged(int slot) {
            AlchemixerBlockEntity.this.setChanged();
        }
    };
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap = Map.of(
            Direction.UP, LazyOptional.of(() -> new WrappedHandler(itemHandler,
                    (i, s) -> true,
                    itemHandler::isInput)),
            Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler,
                    (i, s) -> false,
                    (i) -> itemHandler.isOutput(i) || hasEmptyBucket(i))),
            Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler,
                    (i, s) -> itemHandler.isBucket(i) && itemHandler.isItemValid(this.getBucketSlot(), s),
                    itemHandler::isBucket)),
            Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler,
                    (i, s) -> itemHandler.isBucket(i) && itemHandler.isItemValid(this.getBucketSlot(), s),
                    itemHandler::isBucket)),
            Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler,
                    (i, s) -> itemHandler.isBucket(i) && itemHandler.isItemValid(this.getBucketSlot(), s),
                    itemHandler::isBucket)),
            Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler,
                    (i, s) -> itemHandler.isBucket(i) && itemHandler.isItemValid(this.getBucketSlot(), s),
                    itemHandler::isBucket)));

    private int progress = 0;
    private int maxProgress = 400;
    private int recipeCost = 0;
    private final ContainerData containerData = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> AlchemixerBlockEntity.this.progress;
                case 1 -> AlchemixerBlockEntity.this.maxProgress;
                case 2 -> AlchemixerBlockEntity.this.recipeCost;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> AlchemixerBlockEntity.this.progress = value;
                case 1 -> AlchemixerBlockEntity.this.maxProgress = value;
                case 2 -> AlchemixerBlockEntity.this.recipeCost = value;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    };
    private CachedObject<PotionMixingRecipe> cachedRecipe = CachedObject.empty();

    public AlchemixerBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.ALCHEMIXER.get(), pos, blockState, 4000);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (level.isClientSide) {
            return;
        }
        this.handleTankRefill();
        Optional<PotionMixingRecipe> optionalRecipe = this.getRecipe();
        if (optionalRecipe.isPresent() && this.canProcessRecipe(optionalRecipe.get())) {
            PotionMixingRecipe recipe = optionalRecipe.get();
            this.validateCurrentRecipe(recipe);
            this.onProcessRecipe(recipe);
        } else {
            this.resetProgress();
        }
        this.recipeCost = optionalRecipe.map(PotionMixingRecipe::getRecipeCost).orElse(0);

        level.setBlock(blockPos, blockState
                .setValue(AlchemixerBlock.HAS_BOTTLE[0], !itemHandler.getStackInSlot(INPUT_SLOT1).isEmpty())
                .setValue(AlchemixerBlock.HAS_BOTTLE[1], !itemHandler.getStackInSlot(INPUT_SLOT2).isEmpty())
                .setValue(AlchemixerBlock.HAS_BOTTLE[2], !itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty()), 2);
        setChanged(level, blockPos, blockState);
    }

    private void handleTankRefill() {
        final int amount = 1000;
        if (this.itemHandler.getStackInSlot(this.bucketSlot).is(ModItems.SOUL_LAVA_BUCKET.get())
                && this.canRefillFluidTank(amount)) {
            this.itemHandler.setStackInSlot(bucketSlot, Items.BUCKET.getDefaultInstance());
            this.fillTank(amount);
        }
    }

    private void validateCurrentRecipe(PotionMixingRecipe recipe) {
        if (!this.cachedRecipe.isPresent() || !recipe.matches(this.cachedRecipe.get())) {
            this.resetProgress();
        }
        this.cachedRecipe = CachedObject.of(recipe);
    }

    protected boolean canProcessRecipe(PotionMixingRecipe recipe) {
        return isValidPotionReceptacle.test(this.itemHandler.getStackInSlot(OUTPUT_SLOT)) &&
                this.hasEnoughFuel(recipe.getRecipeCost());
    }

    protected void onProcessRecipe(PotionMixingRecipe recipe) {
        this.progress++;
        if (this.progress >= this.maxProgress) {
            this.produceOutput(recipe);
            this.resetProgress();
            this.level.levelEvent(1035, this.worldPosition, 0);
        }
    }

    protected void produceOutput(PotionMixingRecipe recipe) {
        this.getItemHandler().setStackInSlot(INPUT_SLOT1, Items.GLASS_BOTTLE.getDefaultInstance());
        this.getItemHandler().setStackInSlot(INPUT_SLOT2, Items.GLASS_BOTTLE.getDefaultInstance());
        this.drainTank(recipe.getRecipeCost());
        this.getItemHandler().setStackInSlot(OUTPUT_SLOT, recipe.assemble());
    }

    protected void resetProgress() {
        this.progress = 0;
    }

    protected Optional<PotionMixingRecipe> getRecipe() {
        ItemStack firstStack = this.itemHandler.getStackInSlot(INPUT_SLOT1);
        ItemStack secondStack = this.itemHandler.getStackInSlot(INPUT_SLOT2);
        if (firstStack.isEmpty() || secondStack.isEmpty() ||
                !(firstStack.getItem() instanceof PotionItem) || !(secondStack.getItem() instanceof PotionItem)) {
            return Optional.empty();
        }
        return Optional.of(new PotionMixingRecipe(firstStack, secondStack));
    }

    public boolean hasEnoughFuel(int recipeCost) {
        return this.getFluidTank().getFluidAmount() >= recipeCost;
    }

    public MappedItemStackHandler getItemHandler() {
        return this.itemHandler;
    }

    private boolean hasEmptyBucket(int slot) {
        return slot == this.bucketSlot && this.itemHandler.getStackInSlot(this.bucketSlot).is(Items.BUCKET);
    }

    public int getBucketSlot() {
        return this.bucketSlot;
    }

    public SimpleContainer getInventory() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        return inventory;
    }

    @SuppressWarnings("ConstantConditions")
    public void dropItemsOnBreak() {
        SimpleContainer inventory = getInventory();
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public ContainerData getContainerData() {
        return this.containerData;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("blockentity.telluriumsrandomstuff.alchemixer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new AlchemixerMenu(id, inventory, this, this.containerData);
    }

    // Capabilities
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            if(side == null) {
                return lazyItemHandler.cast();
            }
            // Return capability based on side
            if(directionWrappedHandlerMap.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(AbstractFurnaceBlock.FACING);

                if(side == Direction.UP || side == Direction.DOWN) {
                    return directionWrappedHandlerMap.get(side).cast();
                }
                // Get the correct direction based on the furnace FACING property
                return switch (localDir) {
                    default -> directionWrappedHandlerMap.get(side.getOpposite()).cast();
                    case EAST -> directionWrappedHandlerMap.get(side.getClockWise()).cast();
                    case SOUTH -> directionWrappedHandlerMap.get(side).cast();
                    case WEST -> directionWrappedHandlerMap.get(side.getCounterClockWise()).cast();
                };
            }
        }

        return super.getCapability(cap, side);
    }

    // NBT
    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        Optional<PotionMixingRecipe> optionalRecipe = this.getRecipe();
        cachedRecipe = optionalRecipe.map(CachedObject::of).orElseGet(CachedObject::empty);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("alchemixer.inventory", itemHandler.serializeNBT());
        tag.putInt("alchemixer.progress", this.progress);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("alchemixer.inventory"));
        this.progress = tag.getInt("alchemixer.progress");
    }

}
