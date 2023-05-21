package com.mikitellurium.telluriumsrandomstuff.blockentity.custom;

import com.mikitellurium.telluriumsrandomstuff.block.custom.ExtractorBlock;
import com.mikitellurium.telluriumsrandomstuff.blockentity.ModBlockEntities;
import com.mikitellurium.telluriumsrandomstuff.gui.menu.ExtractorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSourceImpl;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ExtractorBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return !alreadyContains(stack.getItem());
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public ExtractorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.EXTRACTOR.get(), pos, state);
    }

    private SimpleContainer getInventory() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        return inventory;
    }

    public boolean hasInventoryBehind(Level level, BlockPos blockPos) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity == null) return false;
        return blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).isPresent();
    }

    @SuppressWarnings("ConstantConditions")
    public void dispenseItem(BlockEntity blockEntity, BlockSourceImpl blockSource, DispenseItemBehavior behavior) {
        blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent((inventory) -> {
            int slot;

            if (this.getInventory().isEmpty()) { // If there are no filter items
                // Select a random not empty slot
                do {
                    slot = this.level.random.nextInt(inventory.getSlots());
                } while (inventory.getStackInSlot(slot).isEmpty());

            } else { // If there are filter items
                List<Integer> compatibleSlots = new ArrayList<>();
                // Add all slot index that have compatible items to arraylist
                for (int i = 0; i < itemHandler.getSlots(); i++) {
                    if (itemHandler.getStackInSlot(i).isEmpty()) {
                        continue; // Jump this slot if it's empty
                    }
                    Item filterItem = itemHandler.getStackInSlot(i).getItem();
                    for (int j = 0; j < inventory.getSlots(); j++) {
                        if (inventory.getStackInSlot(j).getItem() == filterItem) {
                            compatibleSlots.add(j);
                        }
                    }
                }

                if (compatibleSlots.size() == 0) { // No compatible item where found
                    this.dispenseFailed();
                    return;
                } else {
                    // Select a random slot from arraylist
                    slot = compatibleSlots.get(this.level.random.nextInt(compatibleSlots.size()));
                }
            }

            // Dispense the item from the chosen slot
            ItemStack itemStack = inventory.getStackInSlot(slot);
            behavior.dispense(blockSource, itemStack);
        });
    }

    @SuppressWarnings("ConstantConditions")
    private void dispenseFailed() {
        this.level.levelEvent(1001, this.worldPosition, 0);
        this.level.gameEvent(null, GameEvent.DISPENSE_FAIL, this.worldPosition);
    }

    public boolean isInventoryEmpty(IItemHandler inventory) {
        for (int i = 0; i < inventory.getSlots(); i++) {
            if (!inventory.getStackInSlot(i).isEmpty()) return false;
        }

        return true;
    }

    @SuppressWarnings("ConstantConditions")
    public void dropItemsOnBreak() {
        SimpleContainer inventory = getInventory();
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    private boolean alreadyContains(Item item) {
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            if (itemHandler.getStackInSlot(i).getItem() == item) {
                return true;
            }
        }

        return false;
    }

    // Gui stuff
    @Override
    public Component getDisplayName() {
        return Component.translatable("blockentity.telluriumsrandomstuff.extractor");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new ExtractorMenu(containerId, playerInventory, this);
    }

    // Capability stuff
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    // NBT stuff
    @Override
    public void deserializeNBT(CompoundTag nbt) {
        super.deserializeNBT(nbt);
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
    protected void saveAdditional(CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("inventory"));
    }

}
