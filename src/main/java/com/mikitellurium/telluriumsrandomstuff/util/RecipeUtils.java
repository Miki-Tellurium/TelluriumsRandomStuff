package com.mikitellurium.telluriumsrandomstuff.util;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.registry.ModEnchantments;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulFurnaceRecipe;
import mezz.jei.api.recipe.vanilla.IJeiAnvilRecipe;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.stream.Stream;

public class RecipeUtils {

    public static final List<ItemStack> soulHarvestingBooks = List.of(
            RecipeUtils.addEnchantment(Items.ENCHANTED_BOOK.getDefaultInstance(), ModEnchantments.SOUL_HARVESTING.get(), 1),
            RecipeUtils.addEnchantment(Items.ENCHANTED_BOOK.getDefaultInstance(), ModEnchantments.SOUL_HARVESTING.get(), 2),
            RecipeUtils.addEnchantment(Items.ENCHANTED_BOOK.getDefaultInstance(), ModEnchantments.SOUL_HARVESTING.get(), 3)
    );
    public static final List<ItemStack> soulHarvestingSwords = List.of(
            RecipeUtils.addEnchantment(ModItems.OPAL_CRYSTAL_SWORD.get().getDefaultInstance(), ModEnchantments.SOUL_HARVESTING.get(), 1),
            RecipeUtils.addEnchantment(ModItems.OPAL_CRYSTAL_SWORD.get().getDefaultInstance(), ModEnchantments.SOUL_HARVESTING.get(), 2),
            RecipeUtils.addEnchantment(ModItems.OPAL_CRYSTAL_SWORD.get().getDefaultInstance(), ModEnchantments.SOUL_HARVESTING.get(), 3)
    );
    private static final RepairData opaliumRepairData = new RepairData(Ingredient.of(ModItems.OPAL_CRYSTAL.get()),
            ModItems.OPAL_CRYSTAL_SWORD.get().getDefaultInstance(),
            ModItems.OPAL_CRYSTAL_AXE.get().getDefaultInstance(),
            ModItems.OPAL_CRYSTAL_PICKAXE.get().getDefaultInstance(),
            ModItems.OPAL_CRYSTAL_SHOVEL.get().getDefaultInstance(),
            ModItems.OPAL_CRYSTAL_HOE.get().getDefaultInstance()
            );

    public static List<SoulFurnaceRecipe> getConvertedVanillaRecipes(List<SmeltingRecipe> smeltingRecipes) {
        List<SoulFurnaceRecipe> soulFurnaceRecipes = NonNullList.create();
        for (SmeltingRecipe recipe : smeltingRecipes) {
            soulFurnaceRecipes.add(convert(recipe));
        }
        return soulFurnaceRecipes;
    }

    private static SoulFurnaceRecipe convert(SmeltingRecipe recipe) {
        String itemId = ForgeRegistries.ITEMS.getDelegateOrThrow(recipe.getResultItem(RegistryAccess.EMPTY).getItem())
                .key().location().getPath();
        ResourceLocation id = new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, "soul_furnace_" + itemId);

        Ingredient ingredient = recipe.getIngredients().get(0);
        ItemStack output = recipe.getResultItem(RegistryAccess.EMPTY);
        return new SoulFurnaceRecipe(id, output, ingredient, 0);
    }

    private static ItemStack addEnchantment(ItemStack itemStack, Enchantment enchantment, int level) {
        if (itemStack.is(Items.ENCHANTED_BOOK)) {
            EnchantedBookItem.addEnchantment(itemStack, new EnchantmentInstance(enchantment, level));
        } else {
            itemStack.enchant(enchantment, level);
        }
        return itemStack;
    }

    /*
     *   Anvil recipe helper code taken from JEI Code, credit to mezz and the JEI developers
     */
    public static List<IJeiAnvilRecipe> getAnvilRecipes(IVanillaRecipeFactory vanillaRecipeFactory) {
        return getRepairRecipes(opaliumRepairData, vanillaRecipeFactory).toList();
    }

    private static Stream<IJeiAnvilRecipe> getRepairRecipes(RepairData repairData, IVanillaRecipeFactory vanillaRecipeFactory) {
        Ingredient repairIngredient = repairData.getRepairIngredient();
        List<ItemStack> repairables = repairData.getRepairables();

        List<ItemStack> repairMaterials = List.of(repairIngredient.getItems());

        return repairables.stream()
                .mapMulti((itemStack, consumer) -> {
                    ItemStack damagedThreeQuarters = itemStack.copy();
                    damagedThreeQuarters.setDamageValue(damagedThreeQuarters.getMaxDamage() * 3 / 4);
                    ItemStack damagedHalf = itemStack.copy();
                    damagedHalf.setDamageValue(damagedHalf.getMaxDamage() / 2);

                    IJeiAnvilRecipe repairWithSame = vanillaRecipeFactory.createAnvilRecipe(List.of(damagedThreeQuarters), List.of(damagedThreeQuarters), List.of(damagedHalf));
                    consumer.accept(repairWithSame);

                    if (!repairMaterials.isEmpty()) {
                        ItemStack damagedFully = itemStack.copy();
                        damagedFully.setDamageValue(damagedFully.getMaxDamage());
                        IJeiAnvilRecipe repairWithMaterial = vanillaRecipeFactory.createAnvilRecipe(List.of(damagedFully), repairMaterials, List.of(damagedThreeQuarters));
                        consumer.accept(repairWithMaterial);
                    }
                });
    }

    private static class RepairData {
        private final Ingredient repairIngredient;
        private final List<ItemStack> repairables;

        public RepairData(Ingredient repairIngredient, ItemStack... repairables) {
            this.repairIngredient = repairIngredient;
            this.repairables = List.of(repairables);
        }

        public Ingredient getRepairIngredient() {
            return repairIngredient;
        }

        public List<ItemStack> getRepairables() {
            return repairables;
        }
    }

}
