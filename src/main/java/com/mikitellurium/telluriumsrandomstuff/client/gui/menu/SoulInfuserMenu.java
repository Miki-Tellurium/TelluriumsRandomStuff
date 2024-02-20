package com.mikitellurium.telluriumsrandomstuff.client.gui.menu;

import com.mikitellurium.telluriumsrandomstuff.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class SoulInfuserMenu extends AbstractSoulFuelMenu {

    public SoulInfuserMenu(int id, Inventory inventory, FriendlyByteBuf data) {
        this(id, inventory, inventory.player.level().getBlockEntity(data.readBlockPos()), new SimpleContainerData(2));
    }

    public SoulInfuserMenu(int id, Inventory inventory, BlockEntity blockEntity, ContainerData data) {
        super(id, ModMenuTypes.SOUL_INFUSER_MENU.get(), inventory, 4, blockEntity, data);
        this.getBlockEntity().getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent((itemHandler) -> {
            this.addSlot(new SlotItemHandler(itemHandler, 0, 8, 60));
            this.addSlot(new SlotItemHandler(itemHandler, 1, 47, 15));
            this.addSlot(new SlotItemHandler(itemHandler, 2, 47, 52));
            this.addSlot(new SlotItemHandler(itemHandler, 3, 116, 34));
        });
    }

}
