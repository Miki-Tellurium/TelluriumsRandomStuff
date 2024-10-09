package com.mikitellurium.telluriumsrandomstuff.client.hud.menu;

import com.mikitellurium.telluriumsrandomstuff.common.blockentity.SoulAnchorBlockEntity;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class SoulAnchorMenu extends QuickMoveContainerMenu {

    private final SoulAnchorBlockEntity blockEntity;
    private final Level level;

    public SoulAnchorMenu(int id, Inventory inventory, FriendlyByteBuf data) {
        this(id, inventory, inventory.player.level().getBlockEntity(data.readBlockPos()));
    }

    public SoulAnchorMenu(int id, Inventory inventory, BlockEntity entity) {
        super(ModMenuTypes.SOUL_ANCHOR.get(), id, 41);
        checkContainerSize(inventory, 41);
        blockEntity = (SoulAnchorBlockEntity)entity;
        this.level = inventory.player.level();

        addPlayerHotbar(inventory);
        addPlayerInventory(inventory);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent((itemHandler) -> {
            // Armor slots
            this.addSlot(new SlotItemHandler(itemHandler, 36, 8, -9));
            this.addSlot(new SlotItemHandler(itemHandler, 37, 26, -9));
            this.addSlot(new SlotItemHandler(itemHandler, 38, 44, -9));
            this.addSlot(new SlotItemHandler(itemHandler, 39, 62, -9));
            // Off-Hand Slot
            this.addSlot(new SlotItemHandler(itemHandler, 40, 98, -9));
            // Inventory slots
            for (int i = 0; i < 3; ++i) {
                for (int l = 0; l < 9; ++l) {
                    this.addSlot(new SlotItemHandler(itemHandler, l + i * 9 + 9, 8 + l * 18, 11 + i * 18));
                }
            }
            // HotBar slots
            for (int i = 0; i < 9; ++i) {
                this.addSlot(new SlotItemHandler(itemHandler, i, 8 + i * 18, 67));
            }
        });
    }

    @Override
    public boolean isItemValid(int slot, ItemStack itemStack) {
        return this.getBlockEntity().isItemValid(slot, itemStack);
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModBlocks.SOUL_ANCHOR.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 98 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 156));
        }
    }

    public SoulAnchorBlockEntity getBlockEntity() {
        return blockEntity;
    }

}
