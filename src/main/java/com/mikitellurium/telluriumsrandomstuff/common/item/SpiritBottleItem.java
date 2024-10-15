package com.mikitellurium.telluriumsrandomstuff.common.item;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.test.KeyEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.FastColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
        if (action == ClickAction.PRIMARY && SoulStorageItem.isSoulStorageItem(slot.getItem())) {
            ItemStack remainderStack = fillBottle(bottleStack, itemStack);
            if (!ItemStack.matches(itemStack, remainderStack)) {
                slot.set(remainderStack);
                this.playFillSound(player);
            }
            return true;
        } else if (action == ClickAction.SECONDARY && itemStack.is(ModItems.SMALL_SOUL_FRAGMENT.get())) {
            if (itemStack.getCount() < itemStack.getMaxStackSize()) {
                int toRemove = itemStack.getMaxStackSize() - itemStack.getCount();
                int amount = removeSouls(bottleStack, toRemove, false);
                if (amount > 0) {
                    itemStack.setCount(itemStack.getCount() + amount);
                    this.playEmptySound(player);
                }
                return true;
            }
        } else if (action == ClickAction.SECONDARY && itemStack.isEmpty()) {
            int amount = removeSouls(bottleStack, 64, false);
            if (amount > 0) {
                slot.set(new ItemStack(ModItems.SMALL_SOUL_FRAGMENT.get(), amount));
                this.playEmptySound(player);
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack bottleStack, ItemStack itemStack, Slot slot, ClickAction action, Player player, SlotAccess slotAccess) {
        if (action == ClickAction.PRIMARY && SoulStorageItem.isSoulStorageItem(itemStack)) {
            ItemStack remainderStack = fillBottle(bottleStack, itemStack);
            if (!ItemStack.matches(itemStack, remainderStack)) {
                slotAccess.set(remainderStack);
                this.playFillSound(player);
            }
            return true;
        }
        return false;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag isAdvanced) {
        components.add(Component.literal(getStoredSouls(itemStack) + "/" + this.capacity).withStyle(ChatFormatting.GRAY));
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

    private void playFillSound(Entity entity) {
        entity.playSound(SoundEvents.BOTTLE_FILL, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    private void playEmptySound(Entity entity) {
        entity.playSound(SoundEvents.BOTTLE_EMPTY, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    /* Return the remainder stack */
    private static ItemStack fillBottle(ItemStack bottleStack, ItemStack soulStack) {
        SoulStorageItem soulStorage = (SoulStorageItem) soulStack.getItem();
        int count = soulStack.getCount();
        for (int i = 0; i < soulStack.getCount(); i++) {
            int soulsForUnit = soulStorage.getSoulsForUnit();
            int stored = addSouls(bottleStack, soulStorage.getSoulsForUnit(), true);
            if (stored == soulsForUnit) {
                addSouls(bottleStack, soulStorage.getSoulsForUnit(), false);
                count--;
            } else {
                break;
            }
        }
        return soulStack.copyWithCount(count);
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
