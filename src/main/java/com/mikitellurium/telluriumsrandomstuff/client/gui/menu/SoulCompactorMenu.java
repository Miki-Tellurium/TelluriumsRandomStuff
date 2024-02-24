package com.mikitellurium.telluriumsrandomstuff.client.gui.menu;

import com.mikitellurium.telluriumsrandomstuff.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class SoulCompactorMenu extends AbstractSoulFuelMenu {

    public SoulCompactorMenu(int id, Inventory inventory, FriendlyByteBuf data) {
        this(id, inventory, inventory.player.level().getBlockEntity(data.readBlockPos()), new SimpleContainerData(2));
    }

    public SoulCompactorMenu(int id, Inventory inventory, BlockEntity blockEntity, ContainerData data) {
        super(id, ModMenuTypes.SOUL_COMPACTOR_MENU.get(), inventory, 10, 29, blockEntity, data);
        this.getBlockEntity().getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent((itemHandler) -> {
            this.addSlot(new SlotItemHandler(itemHandler, 0, 8, 89));
            this.addSlot(new SlotItemHandler(itemHandler, 1, 53, 46));
            this.addSlot(new SlotItemHandler(itemHandler, 2, 59, 14));
            this.addSlot(new SlotItemHandler(itemHandler, 3, 91, 8));
            this.addSlot(new SlotItemHandler(itemHandler, 4, 123, 14));
            this.addSlot(new SlotItemHandler(itemHandler, 5, 129, 46));
            this.addSlot(new SlotItemHandler(itemHandler, 6, 123, 78));
            this.addSlot(new SlotItemHandler(itemHandler, 7, 91, 84));
            this.addSlot(new SlotItemHandler(itemHandler, 8, 59, 78));
            this.addSlot(new SlotItemHandler(itemHandler, 9, 91, 46));
        });
    }

}
