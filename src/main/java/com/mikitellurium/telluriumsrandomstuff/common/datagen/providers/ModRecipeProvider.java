package com.mikitellurium.telluriumsrandomstuff.common.datagen.providers;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.common.content.item.LavaGooglesItem;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulSmeltingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(DataGenerator dataGenerator) {
        super(dataGenerator.getPackOutput());
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        buildSoulSmeltingRecipes(consumer);
        buildShapedRecipes(consumer);
        buildShapelessRecipes(consumer);
        buildSmeltingRecipes(consumer);
        buildBlastingRecipes(consumer);
        buildStonecuttingRecipes(consumer);
    }

    private void buildSoulSmeltingRecipes(Consumer<FinishedRecipe> consumer) {
        SoulSmeltingRecipeBuilder.addRecipe(Ingredient.of(Items.GLOWSTONE_DUST), ModItems.BLUE_GLOWSTONE_DUST.get(), 20)
                .save(consumer, modResourceLocation("blue_glowstone_dust_from_soul_smelting"));
        SoulSmeltingRecipeBuilder.addRecipe(Ingredient.of(Items.WHEAT), Items.BREAD,10)
                .save(consumer, modResourceLocation("bread_from_soul_smelting"));
        SoulSmeltingRecipeBuilder.addRecipe(Ingredient.of(Blocks.LANTERN), Blocks.SOUL_LANTERN,10)
                .save(consumer, modResourceLocation("soul_lantern_from_soul_smelting"));
        SoulSmeltingRecipeBuilder.addRecipe(Ingredient.of(Items.ROTTEN_FLESH), Items.LEATHER,30)
                .save(consumer, modResourceLocation("leather_from_soul_smelting"));
        SoulSmeltingRecipeBuilder.addRecipe(Ingredient.of(Items.POISONOUS_POTATO), ModItems.MYSTIC_POTATO.get(), 800)
                .save(consumer, modResourceLocation("mystic_potato_from_soul_smelting"));
        SoulSmeltingRecipeBuilder.addRecipe(Ingredient.of(Blocks.CAMPFIRE), Blocks.SOUL_CAMPFIRE, 250)
                .save(consumer, modResourceLocation("soul_campfire_from_soul_smelting"));
        SoulSmeltingRecipeBuilder.addRecipe(Ingredient.of(Blocks.MAGMA_BLOCK), ModBlocks.SOUL_MAGMA_BLOCK.get(), 100)
                .save(consumer, modResourceLocation("soul_magma_block_from_soul_smelting"));
        SoulSmeltingRecipeBuilder.addRecipe(Ingredient.of(Blocks.CRYING_OBSIDIAN), ModBlocks.SOUL_OBSIDIAN_BLOCK.get(), 500)
                .save(consumer, modResourceLocation("soul_obsidian_from_soul_smelting"));
        SoulSmeltingRecipeBuilder.addRecipe(Ingredient.of(Blocks.TORCH), Blocks.SOUL_TORCH, 5)
                .save(consumer, modResourceLocation("soul_torch_from_soul_smelting"));
        SoulSmeltingRecipeBuilder.addRecipe(Ingredient.of(ModItems.BRIGHT_TORCHFLOWER_SEEDS.get()), ModItems.SOUL_TORCHFLOWER_SEEDS.get(), 20)
                .save(consumer, modResourceLocation("soul_torchflower_seeds_from_soul_smelting"));
        SoulSmeltingRecipeBuilder.addRecipe(Ingredient.of(Items.AMETHYST_SHARD), ModItems.MOLTEN_AMETHYST.get(), 80)
                .save(consumer, modResourceLocation("molten_amethyst_from_soul_smelting"));
    }

    private void buildShapedRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_GLOWSTONE.get(), 1)
                .pattern("##")
                .pattern("##")
                .define('#', ModItems.BLUE_GLOWSTONE_DUST.get())
                .unlockedBy("has_blue_glowstone_dust", has(ModItems.BLUE_GLOWSTONE_DUST.get()))
                .save(consumer, modResourceLocation("blue_glowstone"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_OPAL_BRICKS.get(), 1)
                .pattern("#")
                .pattern("#")
                .define('#', ModBlocks.CUT_OPAL_BRICK_SLAB.get())
                .unlockedBy("has_cut_opal_bricks_slab", has(ModBlocks.CUT_OPAL_BRICK_SLAB.get()))
                .save(consumer, modResourceLocation("chiseled_opal_bricks_with_cut_opal_brick_slab"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_OPAL_BRICKS.get(), 1)
                .pattern("#")
                .pattern("#")
                .define('#', ModBlocks.OPAL_BRICK_SLAB.get())
                .unlockedBy("has_opal_bricks_slab", has(ModBlocks.OPAL_BRICK_SLAB.get()))
                .save(consumer, modResourceLocation("chiseled_opal_bricks_with_opal_brick_slab"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRACKED_CUT_OPAL_BRICK_SLAB.get(), 6)
                .pattern("###")
                .define('#', ModBlocks.CRACKED_CUT_OPAL_BRICKS.get())
                .unlockedBy("has_cracked_cut_opal_bricks", has(ModBlocks.CRACKED_CUT_OPAL_BRICKS.get()))
                .save(consumer, modResourceLocation("cracked_cut_opal_brick_slab"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRACKED_OPAL_BRICK_SLAB.get(), 6)
                .pattern("###")
                .define('#', ModBlocks.CRACKED_OPAL_BRICKS.get())
                .unlockedBy("has_cracked_opal_bricks", has(ModBlocks.CRACKED_OPAL_BRICKS.get()))
                .save(consumer, modResourceLocation("cracked_opal_brick_slab"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_OPAL_BRICK_SLAB.get(), 6)
                .pattern("###")
                .define('#', ModBlocks.CUT_OPAL_BRICKS.get())
                .unlockedBy("has_cut_opal_bricks", has(ModBlocks.CUT_OPAL_BRICKS.get()))
                .save(consumer, modResourceLocation("cut_opal_brick_slab"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_OPAL_BRICK_STAIRS.get(), 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', ModBlocks.CUT_OPAL_BRICKS.get())
                .group("stone_stairs")
                .unlockedBy("has_cut_opal_bricks", has(ModBlocks.CUT_OPAL_BRICKS.get()))
                .save(consumer, modResourceLocation("cut_opal_brick_stairs"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_OPAL_BRICK_WALL.get(), 6)
                .pattern("###")
                .pattern("###")
                .define('#', ModBlocks.CUT_OPAL_BRICKS.get())
                .unlockedBy("has_cut_opal_bricks", has(ModBlocks.CUT_OPAL_BRICKS.get()))
                .save(consumer, modResourceLocation("cut_opal_brick_wall"));
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.EXTRACTOR_BLOCK.get(), 1)
                .pattern("XXX")
                .pattern("#FD")
                .pattern("XXX")
                .define('#', Blocks.HOPPER)
                .define('D', Blocks.DROPPER)
                .define('F', ModItems.FILTER.get())
                .define('X', Blocks.COBBLESTONE)
                .unlockedBy("has_dropper", has(Blocks.DROPPER))
                .unlockedBy("has_hopper", has(Blocks.HOPPER))
                .save(consumer, modResourceLocation("extractor"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FILTER.get(), 1)
                .pattern("X#X")
                .pattern("# #")
                .pattern("X#X")
                .define('#', Items.STRING)
                .define('X', Items.STICK)
                .unlockedBy("has_dropper", has(Blocks.DROPPER))
                .unlockedBy("has_hopper", has(Blocks.HOPPER))
                .save(consumer, modResourceLocation("filter"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRATE_MAGMA_BLOCK.get(), 1)
                .pattern("#")
                .pattern("X")
                .define('#', Blocks.IRON_BARS)
                .define('X', Blocks.MAGMA_BLOCK)
                .unlockedBy("has_iron_bars", has(Blocks.IRON_BARS))
                .unlockedBy("has_magma_block", has(Blocks.MAGMA_BLOCK))
                .save(consumer, modResourceLocation("grate_magma_block"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRATE_SOUL_MAGMA_BLOCK.get(), 1)
                .pattern("#")
                .pattern("X")
                .define('#', Blocks.IRON_BARS)
                .define('X', ModBlocks.SOUL_MAGMA_BLOCK.get())
                .unlockedBy("has_iron_bars", has(Blocks.IRON_BARS))
                .unlockedBy("has_soul_magma_block", has(ModBlocks.SOUL_MAGMA_BLOCK.get()))
                .save(consumer, modResourceLocation("grate_soul_magma_block"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRATE_SOUL_SAND.get(), 1)
                .pattern("#")
                .pattern("X")
                .define('#', Blocks.IRON_BARS)
                .define('X', Blocks.SOUL_SAND)
                .unlockedBy("has_iron_bars", has(Blocks.IRON_BARS))
                .unlockedBy("has_soul_sand", has(Blocks.SOUL_SAND))
                .save(consumer, modResourceLocation("grate_soul_sand"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, ModBlocks.HYDRODYNAMIC_RAIL.get(), 12)
                .pattern("XLX")
                .pattern("X#X")
                .pattern("XPX")
                .define('X', Items.COPPER_INGOT)
                .define('L', Items.LAPIS_LAZULI)
                .define('#', Items.STICK)
                .define('P', Items.PRISMARINE_CRYSTALS)
                .unlockedBy("has_lapis_lazuli", has(Items.LAPIS_LAZULI))
                .unlockedBy("has_copper", has(Items.COPPER_INGOT))
                .unlockedBy("has_prismarine_crystals", has(Items.PRISMARINE_CRYSTALS))
                .save(consumer, modResourceLocation("hydrodynamic_rail"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.OPAL_BRICK_SLAB.get(), 6)
                .pattern("###")
                .define('#', ModBlocks.OPAL_BRICKS.get())
                .unlockedBy("has_opal_bricks", has(ModBlocks.OPAL_BRICKS.get()))
                .save(consumer, modResourceLocation("opal_brick_slab"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.OPAL_BRICK_STAIRS.get(), 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', ModBlocks.OPAL_BRICKS.get())
                .group("stone_stairs")
                .unlockedBy("has_opal_bricks", has(ModBlocks.OPAL_BRICKS.get()))
                .save(consumer, modResourceLocation("opal_brick_stairs"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.OPAL_BRICK_WALL.get(), 6)
                .pattern("###")
                .pattern("###")
                .define('#', ModBlocks.OPAL_BRICKS.get())
                .unlockedBy("has_opal_bricks", has(ModBlocks.OPAL_BRICKS.get()))
                .save(consumer, modResourceLocation("opal_brick_wall"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.OPAL_BRICKS.get(), 4)
                .pattern("##")
                .pattern("##")
                .define('#', ModBlocks.OPAL.get())
                .unlockedBy("has_opal", has(ModBlocks.OPAL.get()))
                .save(consumer, modResourceLocation("opal_bricks"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.OPAL_COBBLESTONE_SLAB.get(), 6)
                .pattern("###")
                .define('#', ModBlocks.OPAL_COBBLESTONE.get())
                .unlockedBy("has_opal_cobblestone", has(ModBlocks.OPAL_COBBLESTONE.get()))
                .save(consumer, modResourceLocation("opal_cobblestone_slab"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.OPAL_COBBLESTONE_STAIRS.get(), 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', ModBlocks.OPAL_COBBLESTONE.get())
                .group("stone_stairs")
                .unlockedBy("has_opal_cobblestone", has(ModBlocks.OPAL_COBBLESTONE.get()))
                .save(consumer, modResourceLocation("opal_cobblestone_stairs"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.OPAL_COBBLESTONE_WALL.get(), 6)
                .pattern("###")
                .pattern("###")
                .define('#', ModBlocks.OPAL_COBBLESTONE.get())
                .unlockedBy("has_opal_cobblestone", has(ModBlocks.OPAL_COBBLESTONE.get()))
                .save(consumer, modResourceLocation("opal_cobblestone_wall"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.OPAL_CRYSTAL_AXE.get(), 1)
                .pattern("XX")
                .pattern("X#")
                .pattern(" #")
                .define('#', Items.STICK)
                .define('X', ModItems.OPAL_CRYSTAL.get())
                .group("equipment")
                .unlockedBy("has_opal_crystal", has(ModItems.OPAL_CRYSTAL.get()))
                .save(consumer, modResourceLocation("opal_crystal_axe"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.OPAL_CRYSTAL_BLOCK.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.OPAL_CRYSTAL.get())
                .unlockedBy("has_opal_crystal", has(ModItems.OPAL_CRYSTAL.get()))
                .save(consumer, modResourceLocation("opal_crystal_block"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.OPAL_CRYSTAL_HOE.get(), 1)
                .pattern("XX")
                .pattern(" #")
                .pattern(" #")
                .define('#', Items.STICK)
                .define('X', ModItems.OPAL_CRYSTAL.get())
                .group("equipment")
                .unlockedBy("has_opal_crystal", has(ModItems.OPAL_CRYSTAL.get()))
                .save(consumer, modResourceLocation("opal_crystal_hoe"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.OPAL_CRYSTAL_PICKAXE.get(), 1)
                .pattern("XXX")
                .pattern(" # ")
                .pattern(" # ")
                .define('#', Items.STICK)
                .define('X', ModItems.OPAL_CRYSTAL.get())
                .group("equipment")
                .unlockedBy("has_opal_crystal", has(ModItems.OPAL_CRYSTAL.get()))
                .save(consumer, modResourceLocation("opal_crystal_pickaxe"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.OPAL_CRYSTAL_SHOVEL.get(), 1)
                .pattern("X")
                .pattern("#")
                .pattern("#")
                .define('#', Items.STICK)
                .define('X', ModItems.OPAL_CRYSTAL.get())
                .group("equipment")
                .unlockedBy("has_opal_crystal", has(ModItems.OPAL_CRYSTAL.get()))
                .save(consumer, modResourceLocation("opal_crystal_shovel"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.OPAL_CRYSTAL_SWORD.get(), 1)
                .pattern("X")
                .pattern("X")
                .pattern("#")
                .define('#', Items.STICK)
                .define('X', ModItems.OPAL_CRYSTAL.get())
                .group("equipment")
                .unlockedBy("has_opal_crystal", has(ModItems.OPAL_CRYSTAL.get()))
                .save(consumer, modResourceLocation("opal_crystal_sword"));
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.OPAL_PRESSURE_PLATE.get(), 1)
                .pattern("##")
                .define('#', ModBlocks.OPAL.get())
                .unlockedBy("has_opal", has(ModBlocks.OPAL.get()))
                .save(consumer, modResourceLocation("opal_pressure_plate"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.OPAL_SLAB.get(), 6)
                .pattern("###")
                .define('#', ModBlocks.OPAL.get())
                .unlockedBy("has_opal", has(ModBlocks.OPAL.get()))
                .save(consumer, modResourceLocation("opal_slab"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.OPAL_STAIRS.get(), 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', ModBlocks.OPAL.get())
                .group("stone_stairs")
                .unlockedBy("has_opal", has(ModBlocks.OPAL.get()))
                .save(consumer, modResourceLocation("opal_stairs"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.RAW_OPAL_CRYSTAL_BLOCK.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.RAW_OPAL_CRYSTAL.get())
                .unlockedBy("has_raw_opal_crystal", has(ModItems.RAW_OPAL_CRYSTAL.get()))
                .save(consumer, modResourceLocation("raw_opal_crystal_block"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SOUL_ANCHOR_BLOCK.get(), 1)
                .pattern("XLX")
                .pattern("X#X")
                .pattern("XPX")
                .define('#', Blocks.ENDER_CHEST)
                .define('X', ModBlocks.SOUL_OBSIDIAN_BLOCK.get())
                .define('P', ModBlocks.BLUE_GLOWSTONE.get())
                .define('L', Items.DIAMOND)
                .unlockedBy("has_ender_chest", has(Blocks.ENDER_CHEST))
                .unlockedBy("has_soul_obsidian", has(ModBlocks.SOUL_OBSIDIAN_BLOCK.get()))
                .save(consumer, modResourceLocation("soul_anchor"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SOUL_FURNACE_BLOCK.get(), 1)
                .pattern("XBX")
                .pattern("XFX")
                .pattern("###")
                .define('#', Blocks.OBSIDIAN)
                .define('X', ModBlocks.SOUL_MAGMA_BLOCK.get())
                .define('F', Blocks.BLAST_FURNACE)
                .define('B', Items.BUCKET)
                .unlockedBy("has_blast_furnace", has(Blocks.BLAST_FURNACE))
                .unlockedBy("has_soul_magma_block", has(ModBlocks.SOUL_MAGMA_BLOCK.get()))
                .save(consumer, modResourceLocation("soul_furnace"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SOUL_MAGMA_BLOCK.get(), 8)
                .pattern("XXX")
                .pattern("X#X")
                .pattern("XXX")
                .define('#', ModItems.SOUL_LAVA_BUCKET.get())
                .define('X', Blocks.MAGMA_BLOCK)
                .unlockedBy("has_soul_lava_bucket", has(ModItems.SOUL_LAVA_BUCKET.get()))
                .unlockedBy("has_magma_block", has(Blocks.MAGMA_BLOCK))
                .save(consumer, modResourceLocation("soul_magma_block"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SOUL_MAGMA_BRICK_SLAB.get(), 6)
                .pattern("###")
                .define('#', ModBlocks.SOUL_MAGMA_BRICKS.get())
                .unlockedBy("has_soul_magma_bricks", has(ModBlocks.SOUL_MAGMA_BRICKS.get()))
                .save(consumer, modResourceLocation("soul_magma_brick_slab"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SOUL_MAGMA_BRICKS.get(), 4)
                .pattern("##")
                .pattern("##")
                .define('#', ModBlocks.SOUL_MAGMA_BLOCK.get())
                .unlockedBy("has_soul_magma_block", has(ModBlocks.SOUL_MAGMA_BLOCK.get()))
                .save(consumer, modResourceLocation("soul_magma_bricks"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.LAVA_GOOGLES.get(), 1)
                .pattern("sLs")
                .pattern("#X#")
                .define('X', Blocks.GLASS)
                .define('#', ModItems.AMETHYST_LENS.get())
                .define('s', Items.STRING)
                .define('L', Items.LEATHER)
                .unlockedBy("has_amethyst_lens", has(ModItems.AMETHYST_LENS.get()))
                .save(consumer, modResourceLocation("lava_googles"));
    }

    private void buildShapelessRecipes(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BRIGHT_TORCHFLOWER_SEEDS.get(), 1)
                .requires(Items.TORCHFLOWER_SEEDS)
                .requires(Items.GLOW_INK_SAC)
                .unlockedBy("has_torchflower_seeds", has(Items.TORCHFLOWER_SEEDS))
                .save(consumer, modResourceLocation("bright_torchflower_seeds"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, ModBlocks.OPAL_BUTTON.get(), 1)
                .requires(ModBlocks.OPAL.get())
                .unlockedBy("has_opal", has(ModBlocks.OPAL.get()))
                .save(consumer, modResourceLocation("opal_button"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.OPAL_CRYSTAL.get(), 9)
                .requires(ModBlocks.OPAL_CRYSTAL_BLOCK.get())
                .unlockedBy("has_opal_crystal_block", has(ModBlocks.OPAL_CRYSTAL_BLOCK.get()))
                .save(consumer, modResourceLocation("opal_crystal_from_block"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RAW_OPAL_CRYSTAL.get(), 9)
                .requires(ModBlocks.RAW_OPAL_CRYSTAL_BLOCK.get())
                .unlockedBy("has_raw_opal_crystal_block", has(ModBlocks.RAW_OPAL_CRYSTAL_BLOCK.get()))
                .save(consumer, modResourceLocation("raw_opal_crystal_from_block"));
    }

    private void buildSmeltingRecipes(Consumer<FinishedRecipe> consumer) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.CUT_OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.CRACKED_CUT_OPAL_BRICKS.get(), 0.1F, 200)
                .unlockedBy("has_cut_opal_bricks", has(ModBlocks.CUT_OPAL_BRICKS.get()))
                .save(consumer, modResourceLocation("cracked_cut_opal_bricks_from_smelting"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.CRACKED_OPAL_BRICKS.get(), 0.1F, 200)
                .unlockedBy("has_opal_bricks", has(ModBlocks.OPAL_BRICKS.get()))
                .save(consumer, modResourceLocation("cracked_opal_bricks_from_smelting"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.OPAL_CRYSTAL_ORE.get()), RecipeCategory.MISC,
                        ModItems.OPAL_CRYSTAL.get(), 1.0F, 200)
                .unlockedBy("has_opal_crystal_ore", has(ModBlocks.OPAL_CRYSTAL_ORE.get()))
                .save(consumer, modResourceLocation("opal_crystal_from_smelting_opal_crystal_ore"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.RAW_OPAL_CRYSTAL.get()), RecipeCategory.MISC,
                        ModItems.OPAL_CRYSTAL.get(), 1.0F, 200)
                .unlockedBy("has_raw_opal_crystal", has(ModItems.RAW_OPAL_CRYSTAL.get()))
                .save(consumer, modResourceLocation("opal_crystal_from_smelting_raw_opal_crystal"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.OPAL_COBBLESTONE.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.OPAL.get(), 1.0F, 200)
                .unlockedBy("has_opal_cobblestone", has(ModBlocks.OPAL_COBBLESTONE.get()))
                .save(consumer, modResourceLocation("opal_from_smelting"));
    }

    private void buildBlastingRecipes(Consumer<FinishedRecipe> consumer) {
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModBlocks.OPAL_CRYSTAL_ORE.get()), RecipeCategory.MISC,
                        ModItems.OPAL_CRYSTAL.get(), 1.0F, 100)
                .unlockedBy("has_opal_crystal_ore", has(ModBlocks.OPAL_CRYSTAL_ORE.get()))
                .save(consumer, modResourceLocation("opal_crystal_from_blasting_opal_crystal_ore"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.RAW_OPAL_CRYSTAL.get()), RecipeCategory.MISC,
                        ModItems.OPAL_CRYSTAL.get(), 1.0F, 100)
                .unlockedBy("has_raw_opal_crystal", has(ModItems.RAW_OPAL_CRYSTAL.get()))
                .save(consumer, modResourceLocation("opal_crystal_from_blasting_raw_opal_crystal"));
    }

    private void buildStonecuttingRecipes(Consumer<FinishedRecipe> consumer) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.CUT_OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.CHISELED_OPAL_BRICKS.get(), 1)
                .unlockedBy("has_cut_opal_bricks", has(ModBlocks.CUT_OPAL_BRICKS.get()))
                .save(consumer, modResourceLocation("chiseled_opal_bricks_from_cut_opal_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.CHISELED_OPAL_BRICKS.get(), 1)
                .unlockedBy("has_opal_bricks", has(ModBlocks.OPAL_BRICKS.get()))
                .save(consumer, modResourceLocation("chiseled_opal_bricks_from_opal_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.CHISELED_OPAL_BRICKS.get(), 1)
                .unlockedBy("has_opal", has(ModBlocks.OPAL.get()))
                .save(consumer, modResourceLocation("chiseled_opal_bricks_from_opal_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.CRACKED_OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.CRACKED_CUT_OPAL_BRICK_SLAB.get(), 2)
                .unlockedBy("has_cracked_opal_bricks", has(ModBlocks.CRACKED_OPAL_BRICKS.get()))
                .save(consumer, modResourceLocation("cracked_cut_opal_brick_slab_from_cracked_cut_opal_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.CRACKED_OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.CRACKED_OPAL_BRICK_SLAB.get(), 2)
                .unlockedBy("has_cracked_opal_bricks", has(ModBlocks.CRACKED_OPAL_BRICKS.get()))
                .save(consumer, modResourceLocation("cracked_opal_brick_slab_from_cracked_opal_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.CUT_OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.CUT_OPAL_BRICK_SLAB.get(), 2)
                .unlockedBy("has_cut_opal_bricks", has(ModBlocks.CUT_OPAL_BRICKS.get()))
                .save(consumer, modResourceLocation("cut_opal_brick_slab_from_cut_opal_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.CUT_OPAL_BRICK_SLAB.get(), 2)
                .unlockedBy("has_opal_bricks", has(ModBlocks.CUT_OPAL_BRICKS.get()))
                .save(consumer, modResourceLocation("cut_opal_brick_slab_from_opal_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.CUT_OPAL_BRICK_SLAB.get(), 2)
                .unlockedBy("has_opal", has(ModBlocks.OPAL.get()))
                .save(consumer, modResourceLocation("cut_opal_brick_slab_from_opal_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.CUT_OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.CUT_OPAL_BRICK_STAIRS.get(), 1)
                .unlockedBy("has_cut_opal_bricks", has(ModBlocks.CUT_OPAL_BRICKS.get()))
                .save(consumer, modResourceLocation("cut_opal_brick_stairs_from_cut_opal_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.CUT_OPAL_BRICK_STAIRS.get(), 1)
                .unlockedBy("has_opal_bricks", has(ModBlocks.OPAL_BRICKS.get()))
                .save(consumer, modResourceLocation("cut_opal_brick_stairs_from_opal_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.CUT_OPAL_BRICK_STAIRS.get(), 1)
                .unlockedBy("has_opal", has(ModBlocks.OPAL.get()))
                .save(consumer, modResourceLocation("cut_opal_brick_stairs_from_opal_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.CUT_OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.CUT_OPAL_BRICK_WALL.get(), 1)
                .unlockedBy("has_cut_opal_bricks", has(ModBlocks.CUT_OPAL_BRICKS.get()))
                .save(consumer, modResourceLocation("cut_opal_brick_wall_from_cut_opal_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.CUT_OPAL_BRICK_WALL.get(), 1)
                .unlockedBy("has_opal_bricks", has(ModBlocks.OPAL_BRICKS.get()))
                .save(consumer, modResourceLocation("cut_opal_brick_wall_from_opal_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.CUT_OPAL_BRICK_WALL.get(), 1)
                .unlockedBy("has_opal", has(ModBlocks.OPAL.get()))
                .save(consumer, modResourceLocation("cut_opal_brick_wall_from_opal_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.CUT_OPAL_BRICKS.get(), 1)
                .unlockedBy("has_opal_bricks", has(ModBlocks.OPAL_BRICKS.get()))
                .save(consumer, modResourceLocation("cut_opal_bricks_from_opal_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.CUT_OPAL_BRICKS.get(), 1)
                .unlockedBy("has_opal", has(ModBlocks.OPAL.get()))
                .save(consumer, modResourceLocation("cut_opal_bricks_from_opal_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.OPAL_BRICK_SLAB.get(), 2)
                .unlockedBy("has_opal_bricks", has(ModBlocks.OPAL_BRICKS.get()))
                .save(consumer, modResourceLocation("opal_brick_slab_from_opal_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.OPAL_BRICK_SLAB.get(), 2)
                .unlockedBy("has_opal", has(ModBlocks.OPAL.get()))
                .save(consumer, modResourceLocation("opal_brick_slab_from_opal_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.OPAL_BRICK_STAIRS.get(), 1)
                .unlockedBy("has_opal_bricks", has(ModBlocks.OPAL_BRICKS.get()))
                .save(consumer, modResourceLocation("opal_brick_stairs_from_opal_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.OPAL_BRICK_STAIRS.get(), 1)
                .unlockedBy("has_opal", has(ModBlocks.OPAL.get()))
                .save(consumer, modResourceLocation("opal_brick_stairs_from_opal_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.OPAL_BRICK_WALL.get(), 1)
                .unlockedBy("has_opal_bricks", has(ModBlocks.OPAL_BRICKS.get()))
                .save(consumer, modResourceLocation("opal_brick_wall_from_opal_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.OPAL_BRICK_WALL.get(), 1)
                .unlockedBy("has_opal", has(ModBlocks.OPAL.get()))
                .save(consumer, modResourceLocation("opal_brick_wall_from_opal_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.OPAL_BRICKS.get(), 1)
                .unlockedBy("has_opal", has(ModBlocks.OPAL.get()))
                .save(consumer, modResourceLocation("opal_bricks_from_opal_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL_COBBLESTONE.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.OPAL_COBBLESTONE_SLAB.get(), 2)
                .unlockedBy("has_opal_cobblestone", has(ModBlocks.OPAL_COBBLESTONE.get()))
                .save(consumer, modResourceLocation("opal_cobblestone_slab_from_opal_cobblestone_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL_COBBLESTONE.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.OPAL_COBBLESTONE_STAIRS.get(), 1)
                .unlockedBy("has_opal_cobblestone", has(ModBlocks.OPAL_COBBLESTONE.get()))
                .save(consumer, modResourceLocation("opal_cobblestone_stairs_from_opal_cobblestone_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL_COBBLESTONE.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.OPAL_COBBLESTONE_WALL.get(), 1)
                .unlockedBy("has_opal_cobblestone", has(ModBlocks.OPAL_COBBLESTONE.get()))
                .save(consumer, modResourceLocation("opal_cobblestone_wall_from_opal_cobblestone_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.OPAL_SLAB.get(), 2)
                .unlockedBy("has_opal", has(ModBlocks.OPAL.get()))
                .save(consumer, modResourceLocation("opal_slab_from_opal_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.OPAL_STAIRS.get(), 1)
                .unlockedBy("has_opal", has(ModBlocks.OPAL.get()))
                .save(consumer, modResourceLocation("opal_stairs_from_opal_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.SOUL_MAGMA_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.SOUL_MAGMA_BRICK_SLAB.get(), 2)
                .unlockedBy("has_soul_magma_block", has(ModBlocks.SOUL_MAGMA_BLOCK.get()))
                .save(consumer, modResourceLocation("soul_magma_brick_slab_from_soul_magma_block_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.SOUL_MAGMA_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.SOUL_MAGMA_BRICK_SLAB.get(), 2)
                .unlockedBy("has_soul_magma_bricks", has(ModBlocks.SOUL_MAGMA_BRICKS.get()))
                .save(consumer, modResourceLocation("soul_magma_brick_slab_from_soul_magma_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.SOUL_MAGMA_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.SOUL_MAGMA_BRICKS.get(), 1)
                .unlockedBy("has_soul_magma_block", has(ModBlocks.SOUL_MAGMA_BLOCK.get()))
                .save(consumer, modResourceLocation("soul_magma_bricks_from_stone_magma_blocks_stonecutting"));
    }

    private ResourceLocation modResourceLocation(String id) {
        return new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, id);
    }

}
