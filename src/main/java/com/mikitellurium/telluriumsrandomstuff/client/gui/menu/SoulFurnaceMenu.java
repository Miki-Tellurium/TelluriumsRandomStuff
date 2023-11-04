package com.mikitellurium.telluriumsrandomstuff.client.gui.menu;

import com.mikitellurium.telluriumsrandomstuff.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class SoulFurnaceMenu extends AbstractSoulFurnaceMenu {

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

}
