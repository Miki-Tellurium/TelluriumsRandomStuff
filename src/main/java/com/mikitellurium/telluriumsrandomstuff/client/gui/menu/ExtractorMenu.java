package com.mikitellurium.telluriumsrandomstuff.client.gui.menu;

import com.mikitellurium.telluriumsrandomstuff.common.blockentity.ExtractorBlockEntity;
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

public class ExtractorMenu extends QuickMoveContainerMenu {

    private final ExtractorBlockEntity blockEntity;
    private final Level level;

    public ExtractorMenu(int id, Inventory inventory, FriendlyByteBuf data) {
        this(id, inventory, inventory.player.level().getBlockEntity(data.readBlockPos()));
    }

    public ExtractorMenu(int id, Inventory inventory, BlockEntity entity) {
        super(ModMenuTypes.EXTRACTOR.get(), id, 3);
        checkContainerSize(inventory, 3);
        blockEntity = (ExtractorBlockEntity) entity;
        this.level = inventory.player.level();

        addPlayerHotbar(inventory);
        addPlayerInventory(inventory);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent((itemHandler) -> {
            this.addSlot(new SlotItemHandler(itemHandler, 0, 62, 20));
            this.addSlot(new SlotItemHandler(itemHandler, 1, 80, 20));
            this.addSlot(new SlotItemHandler(itemHandler, 2, 98, 20));
        });
    }

    @Override
    public boolean isItemValid(int slot, ItemStack itemStack) {
        return this.getBlockEntity().isItemValid(slot, itemStack);
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModBlocks.EXTRACTOR.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 51 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 109));
        }
    }

    public ExtractorBlockEntity getBlockEntity() {
        return blockEntity;
    }

}
