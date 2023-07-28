package com.mikitellurium.telluriumsrandomstuff.common.content.item;

import com.mikitellurium.telluriumsrandomstuff.client.render.LavaGooglesLayer;
import com.mikitellurium.telluriumsrandomstuff.registry.ModArmorMaterials;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LavaGooglesItem extends Item implements Equipable {

    public static final String tag_color = "color";

    public LavaGooglesItem() {
        super(new Item.Properties()
                .defaultDurability(64)
                .fireResistant());
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return this.swapWithEquipmentSlot(this, level, player, hand);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.equals(Enchantments.VANISHING_CURSE) ||
                enchantment.equals(Enchantments.BINDING_CURSE) ||
                enchantment.equals(Enchantments.UNBREAKING) ||
                enchantment.equals(Enchantments.MENDING) ||
                enchantment.equals(Enchantments.FIRE_PROTECTION);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components,
                                TooltipFlag isAdvanced) {
        DyeColor dyeColor = getColor(itemStack);
        if (dyeColor != null) {
            MutableComponent colorString = Component.literal(dyeColor.getSerializedName())
                    .withStyle((style -> style.withColor(dyeColor.getTextColor())));
            components.add(Component.literal("Color: ").append(colorString));
        }
    }

    public static void setColor(ItemStack itemStack, DyeColor dyeColor) {
        itemStack.getOrCreateTag().putInt(tag_color, dyeColor.getId());
    }

    public static DyeColor getColor(ItemStack itemStack) {
        CompoundTag tag = itemStack.getTag();
        if (tag != null && tag.contains(tag_color)) {
            return DyeColor.byId(tag.getInt(tag_color));
        } else {
            return null;
        }
    }

}
