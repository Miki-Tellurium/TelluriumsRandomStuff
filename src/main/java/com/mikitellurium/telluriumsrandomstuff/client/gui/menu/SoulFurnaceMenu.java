package com.mikitellurium.telluriumsrandomstuff.client.gui.menu;

import com.mikitellurium.telluriumsrandomstuff.common.blockentity.SoulFurnaceBlockEntity;
import com.mikitellurium.telluriumsrandomstuff.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class SoulFurnaceMenu extends AbstractSoulFuelMenu {

    public SoulFurnaceMenu(int id, Inventory inventory, FriendlyByteBuf data) {
        this(id, inventory, inventory.player.level().getBlockEntity(data.readBlockPos()), new SimpleContainerData(7));
    }

    public SoulFurnaceMenu(int id, Inventory inventory, BlockEntity blockEntity, ContainerData data) {
        super(id, ModMenuTypes.SOUL_FURNACE_MENU.get(), inventory, 9, blockEntity, data);
        this.getBlockEntity().getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent((itemHandler) -> {
            this.addSlot(new SlotItemHandler(itemHandler, 0, 8, 60));
            this.addSlot(new SlotItemHandler(itemHandler, 1, 44, 18));
            this.addSlot(new SlotItemHandler(itemHandler, 2, 67, 18));
            this.addSlot(new SlotItemHandler(itemHandler, 3, 44, 36));
            this.addSlot(new SlotItemHandler(itemHandler, 4, 67, 36));
            this.addSlot(new SlotItemHandler(itemHandler, 5, 121, 18));
            this.addSlot(new SlotItemHandler(itemHandler, 6, 139, 18));
            this.addSlot(new SlotItemHandler(itemHandler, 7, 121, 36));
            this.addSlot(new SlotItemHandler(itemHandler, 8, 139, 36));
        });
    }

    @Override
    public boolean isItemValid(int slot, ItemStack itemStack) {
        return ((SoulFurnaceBlockEntity)this.getBlockEntity()).isItemValid(slot, itemStack);
    }

    public boolean isCrafting(int slot) {
        return this.getData().get(slot) > 0;
    }

    public boolean isLit() {
        return this.getData().get(5) > 0;
    }

    public int getScaledLitTime() {
        int litTime = this.getData().get(5);
        int maxLitTime = this.getData().get(6);
        int progressFireSize = 13;
        return maxLitTime != 0 && litTime != 0 ? litTime * progressFireSize / maxLitTime : 0;
    }

    public int getScaledProgress(int data) {
        int progress = this.getData().get(data);
        int maxProgress = this.getData().get(0);
        int progressBarSize = 16;
        return maxProgress != 0 && progress != 0 ? progress * progressBarSize / maxProgress : 0;
    }

}
