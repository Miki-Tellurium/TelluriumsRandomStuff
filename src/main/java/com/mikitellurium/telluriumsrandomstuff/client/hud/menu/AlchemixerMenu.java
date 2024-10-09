package com.mikitellurium.telluriumsrandomstuff.client.hud.menu;

import com.mikitellurium.telluriumsrandomstuff.common.blockentity.AlchemixerBlockEntity;
import com.mikitellurium.telluriumsrandomstuff.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class AlchemixerMenu extends AbstractSoulFuelMenu {

    public AlchemixerMenu(int id, Inventory inventory, FriendlyByteBuf data) {
        this(id, inventory, inventory.player.level().getBlockEntity(data.readBlockPos()), new SimpleContainerData(3));
    }

    public AlchemixerMenu(int id, Inventory inventory, BlockEntity entity, ContainerData data) {
        super(id, ModMenuTypes.ALCHEMIXER.get(), inventory, 4, entity, data);
        this.getBlockEntity().getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent((itemHandler) -> {
            this.addSlot(new SlotItemHandler(itemHandler, 0, 8, 60));
            this.addSlot(new SlotItemHandler(itemHandler, 1, 62, 16));
            this.addSlot(new SlotItemHandler(itemHandler, 2, 120, 16));
            this.addSlot(new SlotItemHandler(itemHandler, 3, 91, 57));
        });
    }

    @Override
    public boolean isItemValid(int slot, ItemStack itemStack) {
        return ((AlchemixerBlockEntity)this.getBlockEntity()).isItemValid(slot, itemStack);
    }

    public int getRecipeCost() {
        return this.getData().get(2);
    }

}
