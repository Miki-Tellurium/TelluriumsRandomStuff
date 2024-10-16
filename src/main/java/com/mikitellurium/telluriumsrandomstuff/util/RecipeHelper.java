package com.mikitellurium.telluriumsrandomstuff.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mikitellurium.telluriumsrandomstuff.api.potionmixing.PotionMixingFunction;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulFurnaceSmeltingRecipe;
import com.mikitellurium.telluriumsrandomstuff.api.potionmixing.PotionMixingManager;
import mezz.jei.api.recipe.vanilla.IJeiAnvilRecipe;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StainedGlassBlock;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.stream.Stream;

public class RecipeHelper {
    // todo use stained glass dyecolor field
    private static final Set<StainedGlassBlock> glassColors = Util.make(new HashSet<>(), (set) -> {
        ForgeRegistries.BLOCKS.getValues().stream()
                .filter((block) -> block instanceof StainedGlassBlock)
                .forEach((block) -> set.add((StainedGlassBlock) block));
    });

    public static final Ingredient WATER_BOTTLE = Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER));
    public static final ItemStack THICK_POTION = PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.THICK);
    public static final ItemStack MUNDANE_POTION = PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.MUNDANE);

    public static Set<StainedGlassBlock> getStainedGlassSet() {
        return glassColors;
    }

    public static List<SoulFurnaceSmeltingRecipe> getConvertedVanillaRecipes(List<SmeltingRecipe> smeltingRecipes) {
        List<SoulFurnaceSmeltingRecipe> soulFurnaceRecipes = NonNullList.create();
        for (SmeltingRecipe recipe : smeltingRecipes) {
            soulFurnaceRecipes.add(convertSmelting(recipe));
        }
        return soulFurnaceRecipes;
    }

    public static SoulFurnaceSmeltingRecipe convertSmelting(SmeltingRecipe recipe) {
        String itemId = ForgeRegistries.ITEMS.getDelegateOrThrow(recipe.getResultItem(RegistryAccess.EMPTY).getItem())
                .key().location().getPath();
        ResourceLocation id = FastLoc.modLoc(itemId + "_from_soul_furnace_smelting");

        Ingredient ingredient = recipe.getIngredients().get(0);
        ItemStack output = recipe.getResultItem(RegistryAccess.EMPTY);
        return new SoulFurnaceSmeltingRecipe(id, output, ingredient);
    }

    public static void validateJsonElement(JsonObject obj, String... keys) {
        Arrays.asList(keys).forEach((s) -> {
            if (!obj.has(s))
                throw new JsonSyntaxException("Missing " + s + ", expected to find a string or object");
        });
    }

    public static Ingredient ingredientFromJson(JsonElement element) {
        Ingredient ingredient;
        if (element.isJsonObject()) {
            JsonObject obj = element.getAsJsonObject();
            ingredient = Ingredient.of(CraftingHelper.getItemStack(obj, true));
        } else {
            List<ItemStack> itemStacks = new ArrayList<>();
            element.getAsJsonArray().forEach((e) -> {
                JsonObject obj = e.getAsJsonObject();
                itemStacks.add(CraftingHelper.getItemStack(obj, true));
            });
            ingredient = Ingredient.of(itemStacks.toArray(new ItemStack[]{}));
        }
        return ingredient;
    }

    public static Item itemFromJson(JsonObject object, String memberName) {
        String s = GsonHelper.getAsString(object, memberName);
        Holder<Item> item = ForgeRegistries.ITEMS.getHolder(ResourceLocation.tryParse(s)).orElseThrow(() -> new JsonSyntaxException("Unknown item '" + s + "'"));
        if (item.get() == Items.AIR) {
            throw new JsonSyntaxException("Empty ingredient not allowed here");
        } else {
            return item.get();
        }
    }

    public static ItemStack itemStackFromJson(JsonObject object, String memberName) {
        if (!object.has(memberName)) throw new JsonSyntaxException("Missing " + memberName + ", expected to find a string or object");
        ItemStack output;
        if (object.get(memberName).isJsonObject()) {
            JsonObject resultJson = GsonHelper.getAsJsonObject(object, memberName);
            output = CraftingHelper.getItemStack(resultJson, true);
        } else {
            String result = GsonHelper.getAsString(object, memberName);
            ResourceLocation resourcelocation = new ResourceLocation(result);
            output = new ItemStack(ForgeRegistries.ITEMS.getDelegateOrThrow(resourcelocation));
        }
        return output;
    }

    public static List<ItemStack> getRandomPotionList(long randomSeed) {
        List<ItemStack> itemStacks = new ArrayList<>();
        ForgeRegistries.POTIONS.getValues().stream().filter((potion -> !potion.getEffects().isEmpty()))
                .forEach((potion) -> itemStacks.add(PotionUtils.setPotion(new ItemStack(Items.POTION), potion)));
        Collections.shuffle(itemStacks, new Random(randomSeed));
        return itemStacks;
    }

    public static List<ItemStack> getPotionsByFunctionType(PotionMixingFunction function) {
        List<ItemStack> itemStacks = new ArrayList<>();
        ForgeRegistries.POTIONS.getValues().stream().filter((potion -> potion.getEffects().size() == 1))
                .forEach((potion) -> {
                    if (PotionMixingManager.getFunction(potion.getEffects().get(0).getEffect()) == function) {
                        itemStacks.add(PotionUtils.setPotion(new ItemStack(Items.POTION), potion));
                    }
                });
        return itemStacks;
    }

    /*
     *   Anvil recipe helper code taken from JEI Code, credit to mezz and the JEI developers
     */
    public static List<IJeiAnvilRecipe> getAnvilRecipes(RepairData repairData, IVanillaRecipeFactory vanillaRecipeFactory) {
        return getRepairRecipes(repairData, vanillaRecipeFactory).toList();
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

    public static class RepairData {
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
