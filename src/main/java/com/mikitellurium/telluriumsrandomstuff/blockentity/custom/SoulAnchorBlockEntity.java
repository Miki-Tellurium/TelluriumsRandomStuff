package com.mikitellurium.telluriumsrandomstuff.blockentity.custom;

import com.mikitellurium.telluriumsrandomstuff.blockentity.ModBlockEntities;
import com.mikitellurium.telluriumsrandomstuff.util.CachedPlayer;
import com.mikitellurium.telluriumsrandomstuff.gui.menu.SoulAnchorMenu;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class SoulAnchorBlockEntity extends BlockEntity implements MenuProvider {

    public static ForgeConfigSpec.IntValue ITEM_VOID_CHANCE;

    private final ItemStackHandler itemHandler = new ItemStackHandler(41) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
           return false;
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private CachedPlayer savedPlayer = new CachedPlayer();

    public SoulAnchorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SOUL_ANCHOR.get(), pos, state);
    }

    public SimpleContainer getInventory() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        return inventory;
    }

    @SuppressWarnings("ConstantConditions")
    public void savePlayerInventory(SimpleContainer playerInventory) {
        for (int i = 0; i < playerInventory.getContainerSize(); i++) {
            ItemStack stack = playerInventory.getItem(i);
            if (stack.isEmpty()) continue;
            if (this.level.random.nextInt(100) + 1 <= ITEM_VOID_CHANCE.get()) continue;
            if (EnchantmentHelper.hasVanishingCurse(stack)) continue;
            itemHandler.setStackInSlot(i, stack.copy());
        }
    }

    @SuppressWarnings("ConstantConditions")
    public void dropItemsOnBreak() {
        SimpleContainer inventory = getInventory();
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public void setSavedPlayer(UUID player) {
        savedPlayer.set(player);
    }

    public UUID getSavedPlayer() {
        return savedPlayer.get();
    }

    public void clearSavedPlayer() {
        savedPlayer.clear();
    }

    // Gui stuff
    @Override
    public Component getDisplayName() {
        return Component.translatable("blockentity.telluriumsrandomstuff.soul_anchor");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new SoulAnchorMenu(containerId, playerInventory, this);
    }

    // Capability stuff
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) {
                return lazyItemHandler.cast();
            }
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
        if (savedPlayer.get() != null) {
            tag.putUUID("savedPlayer", savedPlayer.get());
        }
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("inventory"));
        try {
            savedPlayer.set(tag.getUUID("savedPlayer"));
        } catch (NullPointerException e) {
            // No player was saved
        }
    }

}
