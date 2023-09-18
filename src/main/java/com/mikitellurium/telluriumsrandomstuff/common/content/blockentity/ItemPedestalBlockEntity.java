package com.mikitellurium.telluriumsrandomstuff.common.content.blockentity;

import com.mikitellurium.telluriumsrandomstuff.common.networking.ModMessages;
import com.mikitellurium.telluriumsrandomstuff.common.networking.packets.ItemStackSyncS2CPacket;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlockEntities;
import com.mikitellurium.telluriumsrandomstuff.util.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("ConstantConditions")
public class ItemPedestalBlockEntity extends BlockEntity {

    private final ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide) {
                ModMessages.sendToClients(new ItemStackSyncS2CPacket(this.getStackInSlot(0), worldPosition));
            }
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public ItemPedestalBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ITEM_PEDESTAL.get(), pos, state);
    }

    public ItemStack getItem() {
        return itemHandler.getStackInSlot(0).copy();
    }

    public boolean isEmpty() {
        return this.getItem().isEmpty();
    }

    public boolean insertItem(ItemStack stack, boolean shouldShrink) {
        if (!stack.isEmpty()) {
            ItemStack newStack = stack.copy();
            newStack.setCount(1);
            itemHandler.setStackInSlot(0, newStack);
            if (shouldShrink) {
                stack.shrink(1);
            }
            return true;
        }

        return false;
    }

    public void removeItem() {
        this.dropItem();
        itemHandler.setStackInSlot(0, ItemStack.EMPTY);
    }

    public void dropItem() {
        Containers.dropContents(this.level, this.worldPosition.above(), new SimpleContainer(this.getItem()));
    }

    public void syncItems(ItemStack itemStack) {
        this.itemHandler.setStackInSlot(0, itemStack);
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
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
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
