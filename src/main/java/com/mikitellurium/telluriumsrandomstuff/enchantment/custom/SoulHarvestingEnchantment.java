package com.mikitellurium.telluriumsrandomstuff.enchantment.custom;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class SoulHarvestingEnchantment extends Enchantment {

    private final Method dropFromLootTable = ObfuscationReflectionHelper.findMethod(LivingEntity.class,
            "dropFromLootTable", DamageSource.class, boolean.class);

    public SoulHarvestingEnchantment(Rarity rarity, EquipmentSlot... equipmentSlots) {
        super(rarity, EnchantmentCategory.WEAPON, equipmentSlots);
        dropFromLootTable.setAccessible(true);
    }

    @Override
    public void doPostAttack(LivingEntity attacker, Entity target, int level) {
        if (!attacker.level.isClientSide) {
            if (target instanceof LivingEntity livingTarget) {
                DamageSource damageSource = livingTarget.getLastDamageSource();
                int i = ForgeHooks.getLootingLevel(livingTarget, attacker, damageSource);
                livingTarget.captureDrops(new ArrayList<>());

                try {
                    if (this.shouldDropLoot(livingTarget) && attacker.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                        this.dropFromLootTable.invoke(livingTarget, damageSource, true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Collection<ItemEntity> drops = livingTarget.captureDrops(null);
                if (!ForgeHooks.onLivingDrops(livingTarget, damageSource, drops, i, true))
                    drops.forEach(e -> attacker.level.addFreshEntity(e));
            }
        }

        super.doPostAttack(attacker, target, level);
    }

    private boolean shouldDropLoot(LivingEntity entity) {
        return !entity.isBaby();
    }

    @Override
    public boolean allowedInCreativeTab(Item book, Set<EnchantmentCategory> allowedCategories) {
        return true;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    protected boolean checkCompatibility(Enchantment enchantment) {
        return super.checkCompatibility(enchantment) && enchantment != Enchantments.SWEEPING_EDGE;
    }

    @Override
    public boolean canEnchant(ItemStack itemStack) {
        return itemStack.is(Tags.Items.TOOLS_SWORDS);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return false;
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean isDiscoverable() {
        return false;
    }

}
