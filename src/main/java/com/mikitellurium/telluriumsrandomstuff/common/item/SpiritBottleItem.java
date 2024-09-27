package com.mikitellurium.telluriumsrandomstuff.common.item;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.common.capability.SoulStorage;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.atomic.AtomicReference;

@Mod.EventBusSubscriber(modid = TelluriumsRandomStuffMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SpiritBottleItem extends SoulStorageItem {

    public SpiritBottleItem(int typesCapacity, int capacity, Properties properties) {
        super(typesCapacity, capacity, properties);
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack itemStack, Slot slot, ClickAction action, Player player) {
        if (itemStack.getCount() != 1 || !SoulStorageItem.isSoulStorageItem(slot.getItem()) || action != ClickAction.SECONDARY) {
            return false;
        } else {
            ItemStack itemStack1 = slot.getItem();
            AtomicReference<ItemStack> remainderStack = new AtomicReference<>(itemStack1);
            SoulStorage.performAction(itemStack, itemStack1, (storage, storage1) -> {
                storage1.forEach((entityType, integer) -> {
                    if (storage1.isEmpty()) {
                        remainderStack.set(((SoulStorageItem)itemStack1.getItem()).getEmptyRemainder());
                    } else if (storage.canInsert(entityType)) {
                        int remainder = itemStack1.getCount();
                        for (int i = 0; i < itemStack1.getCount(); i++) {
                            int amount = storage1.getAmount(entityType);
                            int stored = storage.grow(entityType, amount, true);
                            if (stored == amount) {
                                storage.grow(entityType, amount, false);
                                remainder--;
                            } else {
                                break;
                            }
                        }
                        ItemStack itemStack2;
                        if (remainder <= 0) {
                            itemStack2 = ((SoulStorageItem)itemStack1.getItem()).getEmptyRemainder();
                        } else {
                            itemStack2 = new ItemStack(itemStack1.getItem(), remainder);
                        }
                        if (itemStack2.getCount() != 1) {
                            SoulStorage.performAction(itemStack2, (storage2) -> storage2.addAll(storage1));
                        }
                        remainderStack.set(itemStack2);
                    }
                });
            });
            slot.set(remainderStack.get());
            return true;
        }
    }

    @Override
    public ItemStack getEmptyRemainder() {
        return this.getDefaultInstance();
    }

}
