package com.mikitellurium.telluriumsrandomstuff.common.item;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.test.KeyEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Mod.EventBusSubscriber(modid = TelluriumsRandomStuffMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SpiritBottleItem extends Item {

    private static final String STORED_TAG = "StoredSouls";
    private final int capacity;

    public SpiritBottleItem(int capacity, Properties properties) {
        super(properties.stacksTo(1));
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack bottleStack, Slot slot, ClickAction action, Player player) {
        if (bottleStack.getCount() != 1) return false;
        ItemStack itemStack = slot.getItem();
        if (!SoulStorageItem.isSoulStorageItem(slot.getItem())) return false;
        if (action != ClickAction.PRIMARY) {
            SoulStorageItem soulStorage = (SoulStorageItem) itemStack.getItem();
            int count = itemStack.getCount();
            for (int i = 0; i < itemStack.getCount(); i++) {
                int soulsForUnit = soulStorage.getSoulsForUnit();
                int stored = addSouls(bottleStack, soulStorage.getSoulsForUnit(), true);
                if (stored == soulsForUnit) {
                    addSouls(bottleStack, soulStorage.getSoulsForUnit(), false);
                    count--;
                } else {
                    break;
                }
            }
            itemStack.setCount(count);
            return true;
        } else {

        }
        return false;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag isAdvanced) {
        Component component = Component.literal(getStoredSouls(itemStack) + "/" + this.capacity).withStyle(ChatFormatting.GRAY);
        components.add(component);
    }

    @Override
    public boolean isBarVisible(ItemStack itemStack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack itemStack) {
        int amount = getStoredSouls(itemStack);
        if (amount <= 0) return 0;
        return Math.min((int) (13.0F * amount / this.capacity) + 1, 13);
    }

    @Override
    public int getBarColor(ItemStack itemStack) {
        return 25855;
    }

    @Override
    public void verifyTagAfterLoad(CompoundTag tag) {
        super.verifyTagAfterLoad(tag);
        if (tag.contains(STORED_TAG)) {
            int stored = tag.getInt(STORED_TAG);
            if (stored < 0) {
                tag.putInt(STORED_TAG, 0);
            } else {
                tag.putInt(STORED_TAG, Math.min(stored, this.capacity));
            }
        }
    }

    /* Return the amount added */
    private static int addSouls(ItemStack bottleStack, int amount, boolean simulate) {
        SpiritBottleItem item = (SpiritBottleItem) bottleStack.getItem();
        CompoundTag tag = bottleStack.getOrCreateTag();
        int capacity = item.getCapacity();
        int stored = getStoredSouls(bottleStack);
        int sum = stored + amount;
        if (sum > capacity) {
            int remainder = sum - capacity;
            amount -= remainder;
        }
        if (!simulate) tag.putInt(STORED_TAG, stored + amount);
        return amount;
    }

    /* Return the amount removed */
    private static int removeSouls(ItemStack bottleStack, int amount, boolean simulate) {
        CompoundTag tag = bottleStack.getOrCreateTag();
        int stored = getStoredSouls(bottleStack);
        int remainder = stored - amount;
        if (remainder < 0) {
            amount = stored;
        }
        if (!simulate) tag.putInt(STORED_TAG, stored - amount);
        return amount;
    }

    private static int getStoredSouls(ItemStack itemStack) {
        CompoundTag tag = itemStack.getOrCreateTag();
        return tag.contains(STORED_TAG, Tag.TAG_INT) ? tag.getInt(STORED_TAG) : 0;
    }

}
