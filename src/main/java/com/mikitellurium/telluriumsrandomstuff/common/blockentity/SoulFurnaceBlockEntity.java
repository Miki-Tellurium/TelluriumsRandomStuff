package com.mikitellurium.telluriumsrandomstuff.common.blockentity;

import com.mikitellurium.telluriumsrandomstuff.client.gui.menu.SoulFurnaceMenu;
import com.mikitellurium.telluriumsrandomstuff.common.block.SoulFurnaceBlock;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlockEntities;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.util.SpecialMappedItemStackHandler;
import com.mikitellurium.telluriumsrandomstuff.util.WrappedHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class SoulFurnaceBlockEntity extends AbstractSoulLavaFurnace implements MenuProvider {

    private static final int BUCKET_SLOT = 0;
    private static final int INPUT_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;
    private final SpecialMappedItemStackHandler itemHandler =
            new SpecialMappedItemStackHandler(3, BUCKET_SLOT, new int[] {INPUT_SLOT}, new int[] {OUTPUT_SLOT}) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return isInput(slot) || (isBucket(slot) && stack.is(ModItems.SOUL_LAVA_BUCKET.get()));
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    // Handle item transportation trough other blocks
    // Credit to Kaupenjoe
    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            Map.of(Direction.UP, LazyOptional.of(() -> new WrappedHandler(itemHandler,
                            (i) -> i == INPUT_SLOT,
                            (i, s) -> true)),
                    Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler,
                            (i) -> i == OUTPUT_SLOT || hasEmptyBucket(i),
                            (i, s) -> false)),
                    Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler,
                            (i) -> i == BUCKET_SLOT,
                            (i, s) -> i == BUCKET_SLOT && itemHandler.isItemValid(BUCKET_SLOT, s))),
                    Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler,
                            (i) -> i == BUCKET_SLOT,
                            (i, s) -> i == BUCKET_SLOT && itemHandler.isItemValid(BUCKET_SLOT, s))),
                    Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler,
                            (i) -> i == BUCKET_SLOT,
                            (i, s) -> i == BUCKET_SLOT && itemHandler.isItemValid(BUCKET_SLOT, s))),
                    Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler,
                            (i) -> i == BUCKET_SLOT,
                            (i, s) -> i == BUCKET_SLOT && itemHandler.isItemValid(BUCKET_SLOT, s)))
            );
    private ItemStack itemCheck = ItemStack.EMPTY;

    public SoulFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SOUL_FURNACE.get(), RecipeType.SMELTING, pos, state);
    }

    @Override
    void handleTankRefill() {
        super.handleTankRefill(itemHandler, BUCKET_SLOT);
    }

    @Override
    void validateCurrentRecipe() {
        // For some reason half the time the input item is air, accounting for that
        if (!this.itemHandler.getStackInSlot(INPUT_SLOT).is(Items.AIR)) {
            if (this.itemHandler.getStackInSlot(INPUT_SLOT).getItem() != itemCheck.getItem()) {
                this.resetProgress();
            }
            itemCheck = this.itemHandler.getStackInSlot(INPUT_SLOT);
        }
    }

    @Override
    boolean hasValidRecipe() {
        if (this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() >=
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize()) return false;

        Optional<?> optionalRecipe = getRecipe();
        if (optionalRecipe.isEmpty()) return false;
        Recipe<?> recipe = (Recipe<?>) optionalRecipe.get();
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ||
                recipe.getResultItem(level.registryAccess()).getItem() == this.itemHandler.getStackInSlot(OUTPUT_SLOT).getItem();
    }

    @Override
    @SuppressWarnings("all")
    void smeltItem() {
        Optional<?> optionalRecipe = getRecipe();
        if (optionalRecipe.isPresent()) {
            Recipe<Container> recipe = (Recipe<Container>) optionalRecipe.get();
            ItemStack result = recipe.getResultItem(level.registryAccess());
            this.itemHandler.getStackInSlot(INPUT_SLOT).shrink(1);
            ItemStack outputStack = this.itemHandler.getStackInSlot(OUTPUT_SLOT);
            if (outputStack.isEmpty()) {
                this.itemHandler.setStackInSlot(OUTPUT_SLOT, result);
            } else {
                outputStack.grow(result.getCount());
            }
        }
    }

    @Override
    Optional<Recipe<Container>> getRecipe() {
        return this.quickCheck().getRecipeFor(new SimpleContainer(this.itemHandler.getStackInSlot(INPUT_SLOT)), this.level);
    }

    public SimpleContainer getInventory() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        return inventory;
    }

    protected ItemStackHandler getItemHandler() {
        return this.itemHandler;
    }

    private boolean hasEmptyBucket(int slot) {
        return slot == BUCKET_SLOT && this.itemHandler.getStackInSlot(BUCKET_SLOT).is(Items.BUCKET);
    }

    @SuppressWarnings("ConstantConditions")
    public void dropItemsOnBreak() {
        SimpleContainer inventory = getInventory();
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("blockentity.telluriumsrandomstuff.soul_furnace");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new SoulFurnaceMenu(id, inventory, this, this.getContainerData());
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
                Direction localDir = this.getBlockState().getValue(SoulFurnaceBlock.FACING);

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
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("inventory"));
        itemCheck = itemHandler.getStackInSlot(INPUT_SLOT);
    }

}
