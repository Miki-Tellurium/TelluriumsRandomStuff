package com.mikitellurium.telluriumsrandomstuff.common.recipe;

import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import com.mikitellurium.telluriumsrandomstuff.common.effect.MobEffectUpgrade;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* Not a real recipe type */
public record PotionMixingRecipe(ItemStack firstPotion, ItemStack secondPotion) {

    public static final String TAG_MIXED = FastLoc.modId() + ".mixed";

    public PotionMixingRecipe(ItemStack firstPotion, ItemStack secondPotion) {
        this.firstPotion = firstPotion.copy();
        this.secondPotion = secondPotion.copy();
    }

    public int getRecipeCost() {
        int cost = 0;
        for (MobEffectInstance instance : this.getRawEffects()) {
            int amplifier = instance.getAmplifier() + 1;
            cost += amplifier * amplifier;
            if (!instance.getEffect().isInstantenous()) {
                cost += Math.max(instance.getDuration() / 1200, 1);
            } else {
                cost += amplifier * 2;
            }
        }
        return cost * 100;
    }

    public ItemStack assemble() {
        return getMixedPotion(new ItemStack(Items.POTION), this.getEffects());
    }

    public boolean matches(PotionMixingRecipe recipe) {
        return ItemStack.matches(this.firstPotion, recipe.firstPotion) && ItemStack.matches(this.secondPotion, recipe.secondPotion);
    }

    private List<MobEffectInstance> getRawEffects() {
        List<MobEffectInstance> mobEffects = new ArrayList<>();
        mobEffects.addAll(PotionUtils.getMobEffects(this.firstPotion));
        mobEffects.addAll(PotionUtils.getMobEffects(this.secondPotion));
        return mobEffects;
    }

    private List<MobEffectInstance> getEffects() {
        List<MobEffectInstance> mobEffects = new ArrayList<>();
        List<MobEffectInstance> firstPotionEffects = PotionUtils.getMobEffects(this.firstPotion);
        List<MobEffectInstance> secondPotionEffects = PotionUtils.getMobEffects(this.secondPotion);

        for (MobEffectInstance effect : firstPotionEffects) {
            MobEffect effectType = effect.getEffect();
            Optional<MobEffectInstance> matchingInstance = secondPotionEffects.stream()
                    .filter(instance -> instance.getEffect() == effectType)
                    .findFirst();

            if (matchingInstance.isPresent()) {
                MobEffectInstance instance = matchingInstance.get();
                mobEffects.add(MobEffectUpgrade.getCategory(effectType).getMixedInstance(effect, instance));
            } else {
                mobEffects.add(effect);
            }
        }

        secondPotionEffects.stream()
                .filter(instance -> firstPotionEffects.stream().noneMatch(e -> e.getEffect() == instance.getEffect()))
                .forEach(mobEffects::add);

        return mobEffects;
    }

    public static ItemStack getMixedPotion(ItemStack baseStack, List<MobEffectInstance> mobEffects) {
        ItemStack result = PotionUtils.setCustomEffects(baseStack, mobEffects);
        CompoundTag tag = result.getOrCreateTag();
        tag.putBoolean(TAG_MIXED, true);
        tag.putInt("CustomPotionColor", PotionUtils.getColor(mobEffects));
        return result;
    }

}
