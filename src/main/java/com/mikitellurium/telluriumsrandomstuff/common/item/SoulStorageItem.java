package com.mikitellurium.telluriumsrandomstuff.common.item;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.client.item.SoulStorageTooltip;
import com.mikitellurium.telluriumsrandomstuff.common.capability.SoulStorageCapabilityProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = TelluriumsRandomStuffMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SoulStorageItem extends Item {

    private final int typesCapacity; // Max amount of entity types that can be stored
    private final int capacity; // Max amount of souls for a single entity

    public SoulStorageItem(int typesCapacity, int capacity, Item.Properties properties) {
        super(properties);
        this.typesCapacity = typesCapacity;
        this.capacity = capacity;
    }

    public int getTypesCapacity() {
        return typesCapacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public ItemStack getEmptyRemainder() {
        return ItemStack.EMPTY;
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack itemStack) {
        return Optional.of(new SoulStorageTooltip(itemStack));
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new SoulStorageCapabilityProvider(this.typesCapacity, this.capacity);
    }

    public static boolean isSoulStorageItem(ItemStack itemStack) {
        return itemStack.getItem() instanceof SoulStorageItem;
    }

}
