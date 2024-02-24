package com.mikitellurium.telluriumsrandomstuff.client.gui.menu;

import com.mikitellurium.telluriumsrandomstuff.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class SoulFurnaceMenu extends AbstractSoulFuelMenu {

    public SoulFurnaceMenu(int id, Inventory inventory, FriendlyByteBuf data) {
        this(id, inventory, inventory.player.level().getBlockEntity(data.readBlockPos()), new SimpleContainerData(4));
    }

    public SoulFurnaceMenu(int id, Inventory inventory, BlockEntity blockEntity, ContainerData data) {
        super(id, ModMenuTypes.SOUL_FURNACE_MENU.get(), inventory, 3, blockEntity, data);
        this.getBlockEntity().getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent((itemHandler) -> {
            this.addSlot(new SlotItemHandler(itemHandler, 0, 8, 60));
            this.addSlot(new SlotItemHandler(itemHandler, 1, 56, 30));
            this.addSlot(new SlotItemHandler(itemHandler, 2, 116, 30));
        });
    }

    public boolean isLit() {
        return this.getData().get(2) > 0;
    }

    public int getScaledLitTime() {
        int litTime = this.getData().get(2);
        int maxlitTime = this.getData().get(3);
        int progressFireSize = 13;

        return maxlitTime != 0 && litTime != 0 ? litTime * progressFireSize / maxlitTime : 0;
    }

}
