package com.mikitellurium.telluriumsrandomstuff.blockentity.custom;

import com.mikitellurium.telluriumsrandomstuff.block.custom.SoulFurnaceBlock;
import com.mikitellurium.telluriumsrandomstuff.blockentity.ModBlockEntities;
import com.mikitellurium.telluriumsrandomstuff.gui.SoulFurnaceMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SoulFurnaceBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    private final int BUCKET_SLOT = 0;
    private final int INPUT_SLOT = 1;
    private final int OUTPUT_SLOT = 2;
    private int progress = 0;
    private int maxProgress = 96;
    public RecipeType<? extends AbstractCookingRecipe> recipeType;
    public final ContainerData containerData = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> SoulFurnaceBlockEntity.this.progress;
                case 1 -> SoulFurnaceBlockEntity.this.maxProgress;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> SoulFurnaceBlockEntity.this.progress = value;
                case 1 -> SoulFurnaceBlockEntity.this.maxProgress = value;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public SoulFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SOUL_FURNACE.get(), pos, state);
        this.recipeType = RecipeType.SMELTING;
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, SoulFurnaceBlockEntity furnace) {
        if (level.isClientSide) {
            return;
        }

        if (hasValidRecipe(level, furnace)) {
            furnace.progress++;
            level.setBlock(blockPos, blockState.setValue(SoulFurnaceBlock.LIT, true), 2);
            setChanged(level, blockPos, blockState);
            if (furnace.progress >= furnace.maxProgress) {
                craftItem(level, furnace);
                furnace.resetProgress();
            }

        } else {
            furnace.resetProgress();
            level.setBlock(blockPos, blockState.setValue(SoulFurnaceBlock.LIT, false), 2);
            setChanged(level, blockPos, blockState);
        }
    }

    private static boolean hasValidRecipe(Level level, SoulFurnaceBlockEntity furnace) {
        if (furnace.itemHandler.getStackInSlot(furnace.INPUT_SLOT).isEmpty()) return false;
        if (furnace.itemHandler.getStackInSlot(furnace.OUTPUT_SLOT).getCount() >=
                furnace.itemHandler.getStackInSlot(furnace.OUTPUT_SLOT).getMaxStackSize())
            return false;
        // add if output item is different than item in output slot
        // Example of smeltable block
        if (furnace.itemHandler.getStackInSlot(furnace.INPUT_SLOT).is(Blocks.IRON_ORE.asItem())) {
            System.out.println("Iron ore found");
            return true;
        }

        return false;
    }

    private static void craftItem(Level level, SoulFurnaceBlockEntity furnace) {
        furnace.itemHandler.extractItem(furnace.INPUT_SLOT, 1, false);
        furnace.itemHandler.insertItem(furnace.OUTPUT_SLOT, Items.IRON_INGOT.getDefaultInstance(), false);
    }

    private void resetProgress() {
        this.progress = 0;
    }

    @SuppressWarnings("ConstantConditions")
    public void dropItemsOnBreak() {
        SimpleContainer inventory = getInventory();
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    private SimpleContainer getInventory() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        return inventory;
    }

    // Gui stuff
    @Override
    public Component getDisplayName() {
        return Component.translatable("telluriumsrandomstuff.blockentity.soul_furnace");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new SoulFurnaceMenu(pContainerId, pPlayerInventory, this, this.containerData);
    }

    // NBT stuff
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

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
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putInt("soul_furnace.progress", this.progress);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("soul_furnace.progress");
    }

}
