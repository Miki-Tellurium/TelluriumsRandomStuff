package com.mikitellurium.telluriumsrandomstuff.common.blockentity;

import com.mikitellurium.telluriumsrandomstuff.common.networking.ModMessages;
import com.mikitellurium.telluriumsrandomstuff.common.networking.packets.DisplayNameSyncS2CPacket;
import com.mikitellurium.telluriumsrandomstuff.common.networking.packets.ItemStackSyncS2CPacket;
import com.mikitellurium.telluriumsrandomstuff.common.networking.packets.RotOffsetSyncS2CPacket;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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
    private static final String tag_inventory = "inventory";
    private static final String tag_alwaysDisplayName = "alwaysDisplayName";
    private final int itemRotationTime = 125;
    private int rotTick = 0;
    private float rotOffset = Mth.PI * 2;
    private boolean alwaysDisplayName = false;

    public ItemPedestalBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ITEM_PEDESTAL.get(), pos, state);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, ItemPedestalBlockEntity itemPedestal) {
        if (level.isClientSide && !itemPedestal.isEmpty()) {
            if (itemPedestal.rotTick < itemPedestal.itemRotationTime) {
                itemPedestal.rotTick++;
            } else {
                itemPedestal.rotTick = 0;
            }
        }
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
            this.setRandomRotOffset();
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

    public void syncItem(ItemStack itemStack) {
        this.itemHandler.setStackInSlot(0, itemStack);
    }

    public int getRotTick() {
        return rotTick;
    }

    public void setRandomRotTick() {
        this.rotTick += level.random.nextInt(itemRotationTime);
    }

    public float getRotOffset() {
        return rotOffset;
    }

    public void setRandomRotOffset() {
        this.rotOffset = (Mth.PI * 2.0F) * level.random.nextFloat();
        ModMessages.sendToClients(new RotOffsetSyncS2CPacket(rotOffset, worldPosition));
    }

    public void syncRotOffset(float f) {
        this.rotOffset = f;
    }

    public boolean alwaysDisplayName() {
        return alwaysDisplayName;
    }

    public void syncAlwaysDisplayName(boolean alwaysDisplayName) {
        this.alwaysDisplayName = alwaysDisplayName;
    }

    public void setAlwaysDisplayName() {
        this.alwaysDisplayName = true;
        ModMessages.sendToClients(new DisplayNameSyncS2CPacket(alwaysDisplayName, worldPosition));
        this.setChanged();
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
        this.setRandomRotTick();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put(tag_inventory, itemHandler.serializeNBT());
        tag.putBoolean(tag_alwaysDisplayName, alwaysDisplayName);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound(tag_inventory));
        alwaysDisplayName = tag.getBoolean(tag_alwaysDisplayName);
    }

}
