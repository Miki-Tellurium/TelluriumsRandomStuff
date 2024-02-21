package com.mikitellurium.telluriumsrandomstuff.client.gui.menu;

import com.mikitellurium.telluriumsrandomstuff.common.blockentity.AlchemixerBlockEntity;
import com.mikitellurium.telluriumsrandomstuff.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class AlchemixerMenu extends AbstractSoulFuelMenu {

    public AlchemixerMenu(int id, Inventory inventory, FriendlyByteBuf data) {
        this(id, inventory, inventory.player.level().getBlockEntity(data.readBlockPos()), new SimpleContainerData(3));
    }

    public AlchemixerMenu(int id, Inventory inventory, BlockEntity entity, ContainerData data) {
        super(id, ModMenuTypes.ALCHEMIXER_MENU.get(), inventory, 4, entity, data);
        this.getBlockEntity().getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent((itemHandler) -> {
            this.addSlot(new SlotItemHandler(itemHandler, 0, 8, 60));
            this.addSlot(new SlotItemHandler(itemHandler, 1, 62, 16));
            this.addSlot(new SlotItemHandler(itemHandler, 2, 120, 16));
            this.addSlot(new SlotItemHandler(itemHandler, 3, 91, 57));
        });
    }

    public int getRecipeCost() {
        return this.getData().get(2);
    }

}
