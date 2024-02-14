package com.mikitellurium.telluriumsrandomstuff.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulFurnaceSmeltingRecipe;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import mezz.jei.api.recipe.vanilla.IJeiAnvilRecipe;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class RecipeHelper {

    private static final Map<Block, DyeColor> glassColors = new HashMap<>();
    static {
        glassColors.put(Blocks.BLACK_STAINED_GLASS, DyeColor.BLACK);
        glassColors.put(Blocks.BLUE_STAINED_GLASS, DyeColor.BLUE);
        glassColors.put(Blocks.BROWN_STAINED_GLASS, DyeColor.BROWN);
        glassColors.put(Blocks.CYAN_STAINED_GLASS, DyeColor.CYAN);
        glassColors.put(Blocks.GRAY_STAINED_GLASS, DyeColor.GRAY);
        glassColors.put(Blocks.GREEN_STAINED_GLASS, DyeColor.GREEN);
        glassColors.put(Blocks.LIGHT_BLUE_STAINED_GLASS, DyeColor.LIGHT_BLUE);
        glassColors.put(Blocks.LIGHT_GRAY_STAINED_GLASS, DyeColor.LIGHT_GRAY);
        glassColors.put(Blocks.LIME_STAINED_GLASS, DyeColor.LIME);
        glassColors.put(Blocks.MAGENTA_STAINED_GLASS, DyeColor.MAGENTA);
        glassColors.put(Blocks.ORANGE_STAINED_GLASS, DyeColor.ORANGE);
        glassColors.put(Blocks.PINK_STAINED_GLASS, DyeColor.PINK);
        glassColors.put(Blocks.PURPLE_STAINED_GLASS, DyeColor.PURPLE);
        glassColors.put(Blocks.RED_STAINED_GLASS, DyeColor.RED);
        glassColors.put(Blocks.WHITE_STAINED_GLASS, DyeColor.WHITE);
        glassColors.put(Blocks.YELLOW_STAINED_GLASS, DyeColor.YELLOW);
    }

//    public static final List<ItemStack> soulHarvestingBooks = List.of(
//            RecipeUtils.addEnchantment(Items.ENCHANTED_BOOK.getDefaultInstance(), ModEnchantments.SOUL_HARVESTING.get(), 1),
//            RecipeUtils.addEnchantment(Items.ENCHANTED_BOOK.getDefaultInstance(), ModEnchantments.SOUL_HARVESTING.get(), 2),
//            RecipeUtils.addEnchantment(Items.ENCHANTED_BOOK.getDefaultInstance(), ModEnchantments.SOUL_HARVESTING.get(), 3)
//    );
//    public static final List<ItemStack> soulHarvestingSwords = List.of(
//            RecipeUtils.addEnchantment(ModItems.OPAL_CRYSTAL_SWORD.get().getDefaultInstance(), ModEnchantments.SOUL_HARVESTING.get(), 1),
//            RecipeUtils.addEnchantment(ModItems.OPAL_CRYSTAL_SWORD.get().getDefaultInstance(), ModEnchantments.SOUL_HARVESTING.get(), 2),
//            RecipeUtils.addEnchantment(ModItems.OPAL_CRYSTAL_SWORD.get().getDefaultInstance(), ModEnchantments.SOUL_HARVESTING.get(), 3)
//    );

    public static DyeColor getGlassColor(Block glass) {
        return glassColors.get(glass);
    }

    public static Set<Map.Entry<Block, DyeColor>> getStainedGlassSet() {
        return glassColors.entrySet();
    }

    private static final RepairData opaliumRepairData = new RepairData(Ingredient.of(ModItems.OPAL_CRYSTAL.get()),
            ModItems.OPAL_CRYSTAL_SWORD.get().getDefaultInstance(),
            ModItems.OPAL_CRYSTAL_AXE.get().getDefaultInstance(),
            ModItems.OPAL_CRYSTAL_PICKAXE.get().getDefaultInstance(),
            ModItems.OPAL_CRYSTAL_SHOVEL.get().getDefaultInstance(),
            ModItems.OPAL_CRYSTAL_HOE.get().getDefaultInstance()
            );

    public static List<SoulFurnaceSmeltingRecipe> getConvertedVanillaRecipes(List<SmeltingRecipe> smeltingRecipes) {
        List<SoulFurnaceSmeltingRecipe> soulFurnaceRecipes = NonNullList.create();
        for (SmeltingRecipe recipe : smeltingRecipes) {
            soulFurnaceRecipes.add(convert(recipe));
        }
        return soulFurnaceRecipes;
    }

    private static SoulFurnaceSmeltingRecipe convert(SmeltingRecipe recipe) {
        String itemId = ForgeRegistries.ITEMS.getDelegateOrThrow(recipe.getResultItem(RegistryAccess.EMPTY).getItem())
                .key().location().getPath();
        ResourceLocation id = FastLoc.modLoc(itemId + "_from_soul_furnace_smelting");

        Ingredient ingredient = recipe.getIngredients().get(0);
        ItemStack output = recipe.getResultItem(RegistryAccess.EMPTY);
        return new SoulFurnaceSmeltingRecipe(id, output, ingredient);
    }

    public static ItemStack stackFromJson(JsonObject recipe, String memberName) {
        if (!recipe.has(memberName)) throw new JsonSyntaxException("Missing " + memberName + ", expected to find a string or object");
        ItemStack output;
        if (recipe.get(memberName).isJsonObject()) {
            JsonObject resultJson = GsonHelper.getAsJsonObject(recipe, memberName);
            output = ShapedRecipe.itemStackFromJson(resultJson);
        } else {
            String result = GsonHelper.getAsString(recipe, memberName);
            ResourceLocation resourcelocation = new ResourceLocation(result);
            output = new ItemStack(ForgeRegistries.ITEMS.getDelegateOrThrow(resourcelocation));
        }
        return output;
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
