package com.mikitellurium.telluriumsrandomstuff.common.recipe;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/* Not a real recipe type */
public record PotionMixingRecipe(ItemStack firstPotion, ItemStack secondPotion) {

    private static final Map<Item, Integer> priorities = Map.of(
            Items.POTION, 1,
            Items.SPLASH_POTION, 2,
            Items.LINGERING_POTION, 3
    );
    // todo fix same potion duration and amplifier
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
        cost += this.getRecipeMalus();
        return cost * 100;
    }

    public ItemStack assemble() {
        List<MobEffectInstance> mobEffects = this.getEffects();
        ItemStack result = PotionUtils.setCustomEffects(new ItemStack(this.getPotionType()), mobEffects);
        result.getOrCreateTag().putInt("CustomPotionColor", PotionUtils.getColor(mobEffects));
        return result.setHoverName(Component.translatable("item.telluriumsrandomstuff.mixed_potion.name"));
    }

    public boolean matches(PotionMixingRecipe recipe) {
        return ItemStack.matches(this.firstPotion, recipe.firstPotion) && ItemStack.matches(this.secondPotion, recipe.secondPotion);
    }

    private Item getPotionType() {
        Item firstType = this.firstPotion.getItem();
        Item secondType = this.secondPotion.getItem();
        return priorities.get(firstType) > priorities.get(secondType) ? firstType : secondType;
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
                mobEffects.add(new MobEffectInstance(effectType,
                        Math.min(effect.getDuration(), instance.getDuration()),
                        effect.getAmplifier() + 1));
            } else {
                mobEffects.add(effect);
            }
        }

        secondPotionEffects.stream()
                .filter(instance -> firstPotionEffects.stream().noneMatch(e -> e.getEffect() == instance.getEffect()))
                .forEach(mobEffects::add);

        return mobEffects;
    }

    private int getRecipeMalus() {
        return Math.abs(priorities.get(this.firstPotion.getItem()) - priorities.get(this.secondPotion.getItem()));
    }

}
