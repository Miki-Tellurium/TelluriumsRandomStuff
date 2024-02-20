package com.mikitellurium.telluriumsrandomstuff.datagen.providers;

import com.mikitellurium.telluriumsrandomstuff.common.block.ItemPedestalBlock;
import com.mikitellurium.telluriumsrandomstuff.datagen.recipebuilders.LavaGooglesRecipeBuilder;
import com.mikitellurium.telluriumsrandomstuff.datagen.recipebuilders.SoulInfusionRecipeBuilder;
import com.mikitellurium.telluriumsrandomstuff.datagen.recipebuilders.SoulLavaTransmutationRecipeBuilder;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import com.mikitellurium.telluriumsrandomstuff.util.RecipeHelper;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Map;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(DataGenerator dataGenerator) {
        super(dataGenerator.getPackOutput());
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        buildSoulInfusingRecipes(consumer);
        buildSoulLavaTransmutationRecipe(consumer);
        buildShapedRecipes(consumer);
        buildShapelessRecipes(consumer);
        buildSmeltingRecipes(consumer);
        buildBlastingRecipes(consumer);
        buildStonecuttingRecipes(consumer);
    }

    private void buildSoulInfusingRecipes(Consumer<FinishedRecipe> consumer) {
        SoulInfusionRecipeBuilder.addRecipe(Ingredient.of(Items.GLOWSTONE_DUST), Ingredient.of(ModItems.SMALL_SOUL_FRAGMENT.get()),
                        ModItems.BLUE_GLOWSTONE_DUST.get(), 20)
                .save(consumer, modLoc("blue_glowstone_dust_from_soul_infusion"));
        SoulInfusionRecipeBuilder.addRecipe(Ingredient.of(Items.WHEAT), Ingredient.of(ModItems.SMALL_SOUL_FRAGMENT.get()),
                        Items.BREAD, 10)
                .save(consumer, modLoc("bread_from_soul_infusion"));
        SoulInfusionRecipeBuilder.addRecipe(Ingredient.of(Blocks.LANTERN), Ingredient.of(ModItems.SMALL_SOUL_FRAGMENT.get()),
                        Blocks.SOUL_LANTERN,10)
                .save(consumer, modLoc("soul_lantern_from_soul_infusion"));
        SoulInfusionRecipeBuilder.addRecipe(Ingredient.of(Items.ROTTEN_FLESH), Ingredient.of(ModItems.SMALL_SOUL_FRAGMENT.get()),
                        Items.LEATHER,30)
                .save(consumer, modLoc("leather_from_soul_infusion"));
        SoulInfusionRecipeBuilder.addRecipe(Ingredient.of(Items.POISONOUS_POTATO), Ingredient.of(ModItems.SOUL_CLUSTER.get()),
                        ModItems.MYSTIC_POTATO.get(), 800)
                .save(consumer, modLoc("mystic_potato_from_soul_infusion"));
        SoulInfusionRecipeBuilder.addRecipe(Ingredient.of(Blocks.CAMPFIRE), Ingredient.of(ModItems.SOUL_FRAGMENT.get()),
                        Blocks.SOUL_CAMPFIRE, 250)
                .save(consumer, modLoc("soul_campfire_from_soul_infusion"));
        SoulInfusionRecipeBuilder.addRecipe(Ingredient.of(Blocks.MAGMA_BLOCK), Ingredient.of(ModItems.SMALL_SOUL_FRAGMENT.get()),
                        ModBlocks.SOUL_MAGMA_BLOCK.get(), 100)
                .save(consumer, modLoc("soul_magma_block_from_soul_infusion"));
        SoulInfusionRecipeBuilder.addRecipe(Ingredient.of(Blocks.CRYING_OBSIDIAN), Ingredient.of(ModItems.SOUL_FRAGMENT.get()),
                        ModBlocks.SOUL_OBSIDIAN.get(), 500)
                .save(consumer, modLoc("soul_obsidian_from_soul_infusion"));
        SoulInfusionRecipeBuilder.addRecipe(Ingredient.of(Blocks.TORCH), Ingredient.of(ModItems.SMALL_SOUL_FRAGMENT.get()),
                        Blocks.SOUL_TORCH, 5)
                .save(consumer, modLoc("soul_torch_from_soul_infusion"));
        SoulInfusionRecipeBuilder.addRecipe(Ingredient.of(ModItems.BRIGHT_TORCHFLOWER_SEEDS.get()), Ingredient.of(ModItems.SMALL_SOUL_FRAGMENT.get()),
                        ModItems.SOUL_TORCHFLOWER_SEEDS.get(), 20)
                .save(consumer, modLoc("soul_torchflower_seeds_from_soul_infusion"));
        SoulInfusionRecipeBuilder.addRecipe(Ingredient.of(Items.AMETHYST_SHARD), Ingredient.of(ModItems.SOUL_FRAGMENT.get()),
                        ModItems.MOLTEN_AMETHYST.get(), 80)
                .save(consumer, modLoc("molten_amethyst_from_soul_infusion"));
        SoulInfusionRecipeBuilder.addRecipe(Ingredient.of(Blocks.SCULK_SHRIEKER), Ingredient.of(ModItems.SOUL_CLUSTER.get()),
                        ModBlocks.AWAKENED_SCULK_SHRIEKER.get(), 400)
                .save(consumer, modLoc("awakened_sculk_shrieker_from_soul_infusion"));
        SoulInfusionRecipeBuilder.addRecipe(Ingredient.of(Blocks.JACK_O_LANTERN), Ingredient.of(ModItems.SMALL_SOUL_FRAGMENT.get()),
                        ModBlocks.SOUL_JACK_O_LANTERN.get(), 50)
                .save(consumer, modLoc("soul_jack_o_lantern_from_soul_infusion"));
        SoulInfusionRecipeBuilder.addRecipe(Ingredient.of(Blocks.SAND), Ingredient.of(ModItems.SMALL_SOUL_FRAGMENT.get()),
                        Blocks.SOUL_SAND, 50)
                .save(consumer, modLoc("soul_sand_from_soul_infusion"));
        SoulInfusionRecipeBuilder.addRecipe(Ingredient.of(Items.IRON_INGOT), Ingredient.of(ModItems.SOUL_FRAGMENT.get()),
                        ModItems.SOUL_INFUSED_IRON_INGOT.get(), 100)
                .save(consumer, modLoc("soul_infused_iron_ingot_from_soul_infusion"));
    }

    private void buildSoulLavaTransmutationRecipe(Consumer<FinishedRecipe> consumer) {
        SoulLavaTransmutationRecipeBuilder.addRecipe(Ingredient.of(Blocks.MAGMA_BLOCK), ModBlocks.SOUL_MAGMA_BLOCK.get())
                .save(consumer, modLoc("soul_magma_block_from_transmutation"));
        SoulLavaTransmutationRecipeBuilder.addRecipe(Ingredient.of(Blocks.CRYING_OBSIDIAN), ModBlocks.SOUL_OBSIDIAN.get())
                .save(consumer, modLoc("soul_obsidian_from_transmutation"));
    }

    private void buildShapedRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_GLOWSTONE.get(), 1)
                .pattern("##")
                .pattern("##")
                .define('#', ModItems.BLUE_GLOWSTONE_DUST.get())
                .unlockedBy("has_blue_glowstone_dust", has(ModItems.BLUE_GLOWSTONE_DUST.get()))
                .save(consumer, modLoc("blue_glowstone"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_OPAL_BRICKS.get(), 1)
                .pattern("#")
                .pattern("#")
                .define('#', ModBlocks.CUT_OPAL_BRICK_SLAB.get())
                .unlockedBy("has_cut_opal_bricks_slab", has(ModBlocks.CUT_OPAL_BRICK_SLAB.get()))
                .save(consumer, modLoc("chiseled_opal_bricks_with_cut_opal_brick_slab"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_OPAL_BRICKS.get(), 1)
                .pattern("#")
                .pattern("#")
                .define('#', ModBlocks.OPAL_BRICK_SLAB.get())
                .unlockedBy("has_opal_bricks_slab", has(ModBlocks.OPAL_BRICK_SLAB.get()))
                .save(consumer, modLoc("chiseled_opal_bricks_with_opal_brick_slab"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRACKED_CUT_OPAL_BRICK_SLAB.get(), 6)
                .pattern("###")
                .define('#', ModBlocks.CRACKED_CUT_OPAL_BRICKS.get())
                .unlockedBy("has_cracked_cut_opal_bricks", has(ModBlocks.CRACKED_CUT_OPAL_BRICKS.get()))
                .save(consumer, modLoc("cracked_cut_opal_brick_slab"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRACKED_OPAL_BRICK_SLAB.get(), 6)
                .pattern("###")
                .define('#', ModBlocks.CRACKED_OPAL_BRICKS.get())
                .unlockedBy("has_cracked_opal_bricks", has(ModBlocks.CRACKED_OPAL_BRICKS.get()))
                .save(consumer, modLoc("cracked_opal_brick_slab"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_OPAL_BRICK_SLAB.get(), 6)
                .pattern("###")
                .define('#', ModBlocks.CUT_OPAL_BRICKS.get())
                .unlockedBy("has_cut_opal_bricks", has(ModBlocks.CUT_OPAL_BRICKS.get()))
                .save(consumer, modLoc("cut_opal_brick_slab"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_OPAL_BRICK_STAIRS.get(), 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', ModBlocks.CUT_OPAL_BRICKS.get())
                .group("stone_stairs")
                .unlockedBy("has_cut_opal_bricks", has(ModBlocks.CUT_OPAL_BRICKS.get()))
                .save(consumer, modLoc("cut_opal_brick_stairs"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_OPAL_BRICK_WALL.get(), 6)
                .pattern("###")
                .pattern("###")
                .define('#', ModBlocks.CUT_OPAL_BRICKS.get())
                .unlockedBy("has_cut_opal_bricks", has(ModBlocks.CUT_OPAL_BRICKS.get()))
                .save(consumer, modLoc("cut_opal_brick_wall"));
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.EXTRACTOR.get(), 1)
                .pattern("XXX")
                .pattern("#FD")
                .pattern("XXX")
                .define('#', Blocks.HOPPER)
                .define('D', Blocks.DROPPER)
                .define('F', ModItems.FILTER.get())
                .define('X', Blocks.COBBLESTONE)
                .unlockedBy("has_dropper", has(Blocks.DROPPER))
                .unlockedBy("has_hopper", has(Blocks.HOPPER))
                .save(consumer, modLoc("extractor"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FILTER.get(), 1)
                .pattern("X#X")
                .pattern("# #")
                .pattern("X#X")
                .define('#', Items.STRING)
                .define('X', Items.STICK)
                .unlockedBy("has_dropper", has(Blocks.DROPPER))
                .unlockedBy("has_hopper", has(Blocks.HOPPER))
                .save(consumer, modLoc("filter"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRATE_MAGMA_BLOCK.get(), 1)
                .pattern("#")
                .pattern("X")
                .define('#', Blocks.IRON_BARS)
                .define('X', Blocks.MAGMA_BLOCK)
                .unlockedBy("has_iron_bars", has(Blocks.IRON_BARS))
                .unlockedBy("has_magma_block", has(Blocks.MAGMA_BLOCK))
                .save(consumer, modLoc("grate_magma_block"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRATE_SOUL_MAGMA_BLOCK.get(), 1)
                .pattern("#")
                .pattern("X")
                .define('#', Blocks.IRON_BARS)
                .define('X', ModBlocks.SOUL_MAGMA_BLOCK.get())
                .unlockedBy("has_iron_bars", has(Blocks.IRON_BARS))
                .unlockedBy("has_soul_magma_block", has(ModBlocks.SOUL_MAGMA_BLOCK.get()))
                .save(consumer, modLoc("grate_soul_magma_block"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRATE_SOUL_SAND.get(), 1)
                .pattern("#")
                .pattern("X")
                .define('#', Blocks.IRON_BARS)
                .define('X', Blocks.SOUL_SAND)
                .unlockedBy("has_iron_bars", has(Blocks.IRON_BARS))
                .unlockedBy("has_soul_sand", has(Blocks.SOUL_SAND))
                .save(consumer, modLoc("grate_soul_sand"));
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
                .save(consumer, modLoc("hydrodynamic_rail"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.OPAL_BRICK_SLAB.get(), 6)
                .pattern("###")
                .define('#', ModBlocks.OPAL_BRICKS.get())
                .unlockedBy("has_opal_bricks", has(ModBlocks.OPAL_BRICKS.get()))
                .save(consumer, modLoc("opal_brick_slab"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.OPAL_BRICK_STAIRS.get(), 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', ModBlocks.OPAL_BRICKS.get())
                .group("stone_stairs")
                .unlockedBy("has_opal_bricks", has(ModBlocks.OPAL_BRICKS.get()))
                .save(consumer, modLoc("opal_brick_stairs"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.OPAL_BRICK_WALL.get(), 6)
                .pattern("###")
                .pattern("###")
                .define('#', ModBlocks.OPAL_BRICKS.get())
                .unlockedBy("has_opal_bricks", has(ModBlocks.OPAL_BRICKS.get()))
                .save(consumer, modLoc("opal_brick_wall"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.OPAL_BRICKS.get(), 4)
                .pattern("##")
                .pattern("##")
                .define('#', ModBlocks.OPAL.get())
                .unlockedBy("has_opal", has(ModBlocks.OPAL.get()))
                .save(consumer, modLoc("opal_bricks"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.OPAL_COBBLESTONE_SLAB.get(), 6)
                .pattern("###")
                .define('#', ModBlocks.OPAL_COBBLESTONE.get())
                .unlockedBy("has_opal_cobblestone", has(ModBlocks.OPAL_COBBLESTONE.get()))
                .save(consumer, modLoc("opal_cobblestone_slab"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.OPAL_COBBLESTONE_STAIRS.get(), 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', ModBlocks.OPAL_COBBLESTONE.get())
                .group("stone_stairs")
                .unlockedBy("has_opal_cobblestone", has(ModBlocks.OPAL_COBBLESTONE.get()))
                .save(consumer, modLoc("opal_cobblestone_stairs"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.OPAL_COBBLESTONE_WALL.get(), 6)
                .pattern("###")
                .pattern("###")
                .define('#', ModBlocks.OPAL_COBBLESTONE.get())
                .unlockedBy("has_opal_cobblestone", has(ModBlocks.OPAL_COBBLESTONE.get()))
                .save(consumer, modLoc("opal_cobblestone_wall"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.OPAL_CRYSTAL_AXE.get(), 1)
                .pattern("XX")
                .pattern("X#")
                .pattern(" #")
                .define('#', Items.STICK)
                .define('X', ModItems.OPAL_CRYSTAL.get())
                .group("equipment")
                .unlockedBy("has_opal_crystal", has(ModItems.OPAL_CRYSTAL.get()))
                .save(consumer, modLoc("opal_crystal_axe"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.OPAL_CRYSTAL_BLOCK.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.OPAL_CRYSTAL.get())
                .unlockedBy("has_opal_crystal", has(ModItems.OPAL_CRYSTAL.get()))
                .save(consumer, modLoc("opal_crystal_block"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.OPAL_CRYSTAL_HOE.get(), 1)
                .pattern("XX")
                .pattern(" #")
                .pattern(" #")
                .define('#', Items.STICK)
                .define('X', ModItems.OPAL_CRYSTAL.get())
                .group("equipment")
                .unlockedBy("has_opal_crystal", has(ModItems.OPAL_CRYSTAL.get()))
                .save(consumer, modLoc("opal_crystal_hoe"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.OPAL_CRYSTAL_PICKAXE.get(), 1)
                .pattern("XXX")
                .pattern(" # ")
                .pattern(" # ")
                .define('#', Items.STICK)
                .define('X', ModItems.OPAL_CRYSTAL.get())
                .group("equipment")
                .unlockedBy("has_opal_crystal", has(ModItems.OPAL_CRYSTAL.get()))
                .save(consumer, modLoc("opal_crystal_pickaxe"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.OPAL_CRYSTAL_SHOVEL.get(), 1)
                .pattern("X")
                .pattern("#")
                .pattern("#")
                .define('#', Items.STICK)
                .define('X', ModItems.OPAL_CRYSTAL.get())
                .group("equipment")
                .unlockedBy("has_opal_crystal", has(ModItems.OPAL_CRYSTAL.get()))
                .save(consumer, modLoc("opal_crystal_shovel"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.OPAL_CRYSTAL_SWORD.get(), 1)
                .pattern("X")
                .pattern("X")
                .pattern("#")
                .define('#', Items.STICK)
                .define('X', ModItems.OPAL_CRYSTAL.get())
                .group("equipment")
                .unlockedBy("has_opal_crystal", has(ModItems.OPAL_CRYSTAL.get()))
                .save(consumer, modLoc("opal_crystal_sword"));
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.OPAL_PRESSURE_PLATE.get(), 1)
                .pattern("##")
                .define('#', ModBlocks.OPAL.get())
                .unlockedBy("has_opal", has(ModBlocks.OPAL.get()))
                .save(consumer, modLoc("opal_pressure_plate"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.OPAL_SLAB.get(), 6)
                .pattern("###")
                .define('#', ModBlocks.OPAL.get())
                .unlockedBy("has_opal", has(ModBlocks.OPAL.get()))
                .save(consumer, modLoc("opal_slab"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.OPAL_STAIRS.get(), 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', ModBlocks.OPAL.get())
                .group("stone_stairs")
                .unlockedBy("has_opal", has(ModBlocks.OPAL.get()))
                .save(consumer, modLoc("opal_stairs"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.RAW_OPAL_CRYSTAL_BLOCK.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.RAW_OPAL_CRYSTAL.get())
                .unlockedBy("has_raw_opal_crystal", has(ModItems.RAW_OPAL_CRYSTAL.get()))
                .save(consumer, modLoc("raw_opal_crystal_block"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SOUL_ANCHOR.get(), 1)
                .pattern("XLX")
                .pattern("X#X")
                .pattern("XPX")
                .define('#', Blocks.ENDER_CHEST)
                .define('X', ModBlocks.SOUL_OBSIDIAN.get())
                .define('P', ModBlocks.BLUE_GLOWSTONE.get())
                .define('L', Items.DIAMOND)
                .unlockedBy("has_ender_chest", has(Blocks.ENDER_CHEST))
                .unlockedBy("has_soul_obsidian", has(ModBlocks.SOUL_OBSIDIAN.get()))
                .save(consumer, modLoc("soul_anchor"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SOUL_FURNACE.get(), 1)
                .pattern("XBX")
                .pattern("XFX")
                .pattern("###")
                .define('#', Blocks.OBSIDIAN)
                .define('X', ModBlocks.SOUL_MAGMA_BRICKS.get())
                .define('F', Blocks.BLAST_FURNACE)
                .define('B', Items.BUCKET)
                .unlockedBy("has_blast_furnace", has(Blocks.BLAST_FURNACE))
                .unlockedBy("has_soul_magma_bricks", has(ModBlocks.SOUL_MAGMA_BRICKS.get()))
                .save(consumer, modLoc("soul_furnace"));
//        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SOUL_MAGMA_BLOCK.get(), 8)
//                .pattern("XXX")
//                .pattern("X#X")
//                .pattern("XXX")
//                .define('#', ModItems.SOUL_LAVA_BUCKET.get())
//                .define('X', Blocks.MAGMA_BLOCK)
//                .unlockedBy("has_soul_lava_bucket", has(ModItems.SOUL_LAVA_BUCKET.get()))
//                .unlockedBy("has_magma_block", has(Blocks.MAGMA_BLOCK))
//                .save(consumer, modLoc("soul_magma_block"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SOUL_MAGMA_BRICK_SLAB.get(), 6)
                .pattern("###")
                .define('#', ModBlocks.SOUL_MAGMA_BRICKS.get())
                .unlockedBy("has_soul_magma_bricks", has(ModBlocks.SOUL_MAGMA_BRICKS.get()))
                .save(consumer, modLoc("soul_magma_brick_slab"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SOUL_MAGMA_BRICKS.get(), 4)
                .pattern("##")
                .pattern("##")
                .define('#', ModBlocks.SOUL_MAGMA_BLOCK.get())
                .unlockedBy("has_soul_magma_block", has(ModBlocks.SOUL_MAGMA_BLOCK.get()))
                .save(consumer, modLoc("soul_magma_bricks"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.LAVA_GOOGLES.get(), 1)
                .pattern("sLs")
                .pattern("#X#")
                .define('X', Blocks.GLASS)
                .define('#', ModItems.AMETHYST_LENS.get())
                .define('s', Items.STRING)
                .define('L', Items.LEATHER)
                .unlockedBy("has_amethyst_lens", has(ModItems.AMETHYST_LENS.get()))
                .save(consumer, modLoc("lava_googles"));

        for (Map.Entry<Block, DyeColor> entry : RecipeHelper.getStainedGlassSet()) {
            LavaGooglesRecipeBuilder.googles(entry.getKey())
                    .save(consumer, modLoc(entry.getValue().getSerializedName() + "_lava_googles"));
        }

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, Items.SPECTRAL_ARROW, 4)
                .pattern(" # ")
                .pattern("#a#")
                .pattern(" # ")
                .define('a', Items.ARROW)
                .define('#', ModItems.BLUE_GLOWSTONE_DUST.get())
                .unlockedBy("has_blue_glowstone_dust", has(ModItems.BLUE_GLOWSTONE_DUST.get()))
                .save(consumer, modLoc("spectral_arrows_with_blue_glowstone_dust"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModBlocks.BLUE_REDSTONE_LAMP.get(), 1)
                .pattern(" # ")
                .pattern("#g#")
                .pattern(" # ")
                .define('g', ModBlocks.BLUE_GLOWSTONE.get())
                .define('#', Items.REDSTONE)
                .unlockedBy("has_blue_glowstone", has(ModBlocks.BLUE_GLOWSTONE.get()))
                .save(consumer, modLoc("blue_redstone_lamp"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModBlocks.SOUL_JACK_O_LANTERN.get(), 1)
                .pattern("#")
                .pattern("I")
                .define('I', Blocks.SOUL_TORCH)
                .define('#', Blocks.CARVED_PUMPKIN)
                .unlockedBy("has_soul_torch", has(Blocks.SOUL_TORCH))
                .unlockedBy("has_carved_pumpkin", has(Blocks.CARVED_PUMPKIN))
                .save(consumer, modLoc("soul_jack_o_lantern"));
        this.itemPedestalShaped(consumer, "stone_item_pedestal", (ItemPedestalBlock)ModBlocks.STONE_ITEM_PEDESTAL.get(),
                Blocks.STONE);
        this.itemPedestalShaped(consumer, "stone_brick_item_pedestal", (ItemPedestalBlock)ModBlocks.STONE_BRICK_ITEM_PEDESTAL.get(),
                Blocks.STONE_BRICKS);
        this.itemPedestalShaped(consumer, "mossy_stone_brick_item_pedestal", (ItemPedestalBlock)ModBlocks.MOSSY_STONE_BRICK_ITEM_PEDESTAL.get(),
                Blocks.MOSSY_STONE_BRICKS);
        this.itemPedestalShaped(consumer, "polished_granite_item_pedestal", (ItemPedestalBlock)ModBlocks.POLISHED_GRANITE_ITEM_PEDESTAL.get(),
                Blocks.POLISHED_GRANITE);
        this.itemPedestalShaped(consumer, "polished_diorite_item_pedestal", (ItemPedestalBlock)ModBlocks.POLISHED_DIORITE_ITEM_PEDESTAL.get(),
                Blocks.POLISHED_DIORITE);
        this.itemPedestalShaped(consumer, "polished_andesite_item_pedestal", (ItemPedestalBlock)ModBlocks.POLISHED_ANDESITE_ITEM_PEDESTAL.get(),
                Blocks.POLISHED_ANDESITE);
        this.itemPedestalShaped(consumer, "polished_deepslate_item_pedestal", (ItemPedestalBlock)ModBlocks.POLISHED_DEEPSLATE_ITEM_PEDESTAL.get(),
                Blocks.POLISHED_DEEPSLATE);
        this.itemPedestalShaped(consumer, "deepslate_brick_item_pedestal", (ItemPedestalBlock)ModBlocks.DEEPSLATE_BRICK_ITEM_PEDESTAL.get(),
                Blocks.DEEPSLATE_BRICKS);
        this.itemPedestalShaped(consumer, "deepslate_tile_item_pedestal", (ItemPedestalBlock)ModBlocks.DEEPSLATE_TILE_ITEM_PEDESTAL.get(),
                Blocks.DEEPSLATE_TILES);
        this.itemPedestalShaped(consumer, "cut_sandstone_item_pedestal", (ItemPedestalBlock)ModBlocks.CUT_SANDSTONE_ITEM_PEDESTAL.get(),
                Blocks.CUT_SANDSTONE);
        this.itemPedestalShaped(consumer, "cut_red_sandstone_item_pedestal", (ItemPedestalBlock)ModBlocks.CUT_RED_SANDSTONE_ITEM_PEDESTAL.get(),
                Blocks.CUT_RED_SANDSTONE);
        this.itemPedestalShaped(consumer, "prismarine_brick_item_pedestal", (ItemPedestalBlock)ModBlocks.PRISMARINE_BRICK_ITEM_PEDESTAL.get(),
                Blocks.PRISMARINE_BRICKS);
        this.itemPedestalShaped(consumer, "nether_brick_item_pedestal", (ItemPedestalBlock)ModBlocks.NETHER_BRICK_ITEM_PEDESTAL.get(),
                Blocks.NETHER_BRICKS);
        this.itemPedestalShaped(consumer, "red_nether_brick_item_pedestal", (ItemPedestalBlock)ModBlocks.RED_NETHER_BRICK_ITEM_PEDESTAL.get(),
                Blocks.RED_NETHER_BRICKS);
        this.itemPedestalShaped(consumer, "polished_blackstone_item_pedestal", (ItemPedestalBlock)ModBlocks.POLISHED_BLACKSTONE_ITEM_PEDESTAL.get(),
                Blocks.POLISHED_BLACKSTONE);
        this.itemPedestalShaped(consumer, "polished_blackstone_brick_item_pedestal", (ItemPedestalBlock)ModBlocks.POLISHED_BLACKSTONE_BRICK_ITEM_PEDESTAL.get(),
                Blocks.POLISHED_BLACKSTONE_BRICKS);
        this.itemPedestalShaped(consumer, "end_stone_brick_item_pedestal", (ItemPedestalBlock)ModBlocks.END_STONE_BRICK_ITEM_PEDESTAL.get(),
                Blocks.END_STONE_BRICKS);
        this.itemPedestalShaped(consumer, "purpur_item_pedestal", (ItemPedestalBlock)ModBlocks.PURPUR_ITEM_PEDESTAL.get(),
                Blocks.PURPUR_BLOCK);
        this.itemPedestalShaped(consumer, "quartz_item_pedestal", (ItemPedestalBlock)ModBlocks.QUARTZ_ITEM_PEDESTAL.get(),
                Blocks.QUARTZ_BLOCK);
        this.itemPedestalShaped(consumer, "soul_magma_brick_item_pedestal", (ItemPedestalBlock)ModBlocks.SOUL_MAGMA_BRICK_ITEM_PEDESTAL.get(),
                ModBlocks.SOUL_MAGMA_BRICKS.get());
        this.itemPedestalShaped(consumer, "opal_item_pedestal", (ItemPedestalBlock)ModBlocks.OPAL_ITEM_PEDESTAL.get(),
                ModBlocks.OPAL.get());
        this.itemPedestalShaped(consumer, "opal_brick_item_pedestal", (ItemPedestalBlock)ModBlocks.OPAL_BRICK_ITEM_PEDESTAL.get(),
                ModBlocks.OPAL_BRICKS.get());
        this.itemPedestalShaped(consumer, "cut_opal_brick_item_pedestal", (ItemPedestalBlock)ModBlocks.CUT_OPAL_BRICK_ITEM_PEDESTAL.get(),
                ModBlocks.CUT_OPAL_BRICKS.get());
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SOUL_FRAGMENT.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.SMALL_SOUL_FRAGMENT.get())
                .unlockedBy("has_small_soul_fragment", has(ModItems.SMALL_SOUL_FRAGMENT.get()))
                .save(consumer, modLoc("small_soul_fragment"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.INFUSED_SOUL_SAND.get(), 1)
                .pattern(" # ")
                .pattern("#s#")
                .pattern(" # ")
                .define('#', Blocks.SOUL_SAND)
                .define('s', ModItems.SOUL_FRAGMENT.get())
                .unlockedBy("has_soul_fragment", has(ModItems.SOUL_FRAGMENT.get()))
                .save(consumer, modLoc("infused_soul_sand"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SOUL_CLUSTER.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.SOUL_FRAGMENT.get())
                .unlockedBy("has_soul_fragment", has(ModItems.SOUL_FRAGMENT.get()))
                .save(consumer, modLoc("soul_cluster"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SOUL_INFUSER.get(), 1)
                .pattern("XXX")
                .pattern("XFX")
                .pattern("X#X")
                .define('#', Blocks.BREWING_STAND)
                .define('X', ModBlocks.SOUL_OBSIDIAN.get())
                .define('F', ModBlocks.SOUL_FURNACE.get())
                .unlockedBy("has_brewing_stand", has(Blocks.BREWING_STAND))
                .unlockedBy("has_soul_obsidian", has(ModBlocks.SOUL_OBSIDIAN.get()))
                .unlockedBy("has_soul_furnace", has(ModBlocks.SOUL_FURNACE.get()))
                .save(consumer, modLoc("soul_infuser"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SOUL_IRON_ROD.get(), 1)
                .pattern(" X ")
                .pattern("X#X")
                .pattern(" X ")
                .define('#', Items.BLAZE_ROD)
                .define('X', ModItems.SOUL_INFUSED_IRON_INGOT.get())
                .unlockedBy("has_soul_iron_ingot", has(ModItems.SOUL_INFUSED_IRON_INGOT.get()))
                .save(consumer, modLoc("soul_iron_rod"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ALCHEMIXER.get(), 1)
                .pattern(" I ")
                .pattern("XvX")
                .define('I', ModItems.SOUL_IRON_ROD.get())
                .define('X', Blocks.OBSIDIAN)
                .define('v', Items.BUCKET)
                .unlockedBy("has_soul_rod", has(ModItems.SOUL_IRON_ROD.get()))
                .unlockedBy("has_brewing_stand", has(Blocks.BREWING_STAND))
                .save(consumer, modLoc("alchemixer"));
    }

    private void buildShapelessRecipes(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BRIGHT_TORCHFLOWER_SEEDS.get(), 1)
                .requires(Items.TORCHFLOWER_SEEDS)
                .requires(Items.GLOW_INK_SAC)
                .unlockedBy("has_torchflower_seeds", has(Items.TORCHFLOWER_SEEDS))
                .save(consumer, modLoc("bright_torchflower_seeds"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, ModBlocks.OPAL_BUTTON.get(), 1)
                .requires(ModBlocks.OPAL.get())
                .unlockedBy("has_opal", has(ModBlocks.OPAL.get()))
                .save(consumer, modLoc("opal_button"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.OPAL_CRYSTAL.get(), 9)
                .requires(ModBlocks.OPAL_CRYSTAL_BLOCK.get())
                .unlockedBy("has_opal_crystal_block", has(ModBlocks.OPAL_CRYSTAL_BLOCK.get()))
                .save(consumer, modLoc("opal_crystal_from_block"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RAW_OPAL_CRYSTAL.get(), 9)
                .requires(ModBlocks.RAW_OPAL_CRYSTAL_BLOCK.get())
                .unlockedBy("has_raw_opal_crystal_block", has(ModBlocks.RAW_OPAL_CRYSTAL_BLOCK.get()))
                .save(consumer, modLoc("raw_opal_crystal_from_block"));
    }

    private void buildSmeltingRecipes(Consumer<FinishedRecipe> consumer) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.CUT_OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.CRACKED_CUT_OPAL_BRICKS.get(), 0.1F, 200)
                .unlockedBy("has_cut_opal_bricks", has(ModBlocks.CUT_OPAL_BRICKS.get()))
                .save(consumer, modLoc("cracked_cut_opal_bricks_from_smelting"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.CRACKED_OPAL_BRICKS.get(), 0.1F, 200)
                .unlockedBy("has_opal_bricks", has(ModBlocks.OPAL_BRICKS.get()))
                .save(consumer, modLoc("cracked_opal_bricks_from_smelting"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.OPAL_CRYSTAL_ORE.get()), RecipeCategory.MISC,
                        ModItems.OPAL_CRYSTAL.get(), 1.0F, 200)
                .unlockedBy("has_opal_crystal_ore", has(ModBlocks.OPAL_CRYSTAL_ORE.get()))
                .save(consumer, modLoc("opal_crystal_from_smelting_opal_crystal_ore"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.RAW_OPAL_CRYSTAL.get()), RecipeCategory.MISC,
                        ModItems.OPAL_CRYSTAL.get(), 1.0F, 200)
                .unlockedBy("has_raw_opal_crystal", has(ModItems.RAW_OPAL_CRYSTAL.get()))
                .save(consumer, modLoc("opal_crystal_from_smelting_raw_opal_crystal"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.OPAL_COBBLESTONE.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.OPAL.get(), 1.0F, 200)
                .unlockedBy("has_opal_cobblestone", has(ModBlocks.OPAL_COBBLESTONE.get()))
                .save(consumer, modLoc("opal_from_smelting"));
    }

    private void buildBlastingRecipes(Consumer<FinishedRecipe> consumer) {
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModBlocks.OPAL_CRYSTAL_ORE.get()), RecipeCategory.MISC,
                        ModItems.OPAL_CRYSTAL.get(), 1.0F, 100)
                .unlockedBy("has_opal_crystal_ore", has(ModBlocks.OPAL_CRYSTAL_ORE.get()))
                .save(consumer, modLoc("opal_crystal_from_blasting_opal_crystal_ore"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.RAW_OPAL_CRYSTAL.get()), RecipeCategory.MISC,
                        ModItems.OPAL_CRYSTAL.get(), 1.0F, 100)
                .unlockedBy("has_raw_opal_crystal", has(ModItems.RAW_OPAL_CRYSTAL.get()))
                .save(consumer, modLoc("opal_crystal_from_blasting_raw_opal_crystal"));
    }

    private void buildStonecuttingRecipes(Consumer<FinishedRecipe> consumer) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.CUT_OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.CHISELED_OPAL_BRICKS.get(), 1)
                .unlockedBy("has_cut_opal_bricks", has(ModBlocks.CUT_OPAL_BRICKS.get()))
                .save(consumer, modLoc("chiseled_opal_bricks_from_cut_opal_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.CHISELED_OPAL_BRICKS.get(), 1)
                .unlockedBy("has_opal_bricks", has(ModBlocks.OPAL_BRICKS.get()))
                .save(consumer, modLoc("chiseled_opal_bricks_from_opal_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.CHISELED_OPAL_BRICKS.get(), 1)
                .unlockedBy("has_opal", has(ModBlocks.OPAL.get()))
                .save(consumer, modLoc("chiseled_opal_bricks_from_opal_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.CRACKED_OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.CRACKED_CUT_OPAL_BRICK_SLAB.get(), 2)
                .unlockedBy("has_cracked_opal_bricks", has(ModBlocks.CRACKED_OPAL_BRICKS.get()))
                .save(consumer, modLoc("cracked_cut_opal_brick_slab_from_cracked_cut_opal_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.CRACKED_OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.CRACKED_OPAL_BRICK_SLAB.get(), 2)
                .unlockedBy("has_cracked_opal_bricks", has(ModBlocks.CRACKED_OPAL_BRICKS.get()))
                .save(consumer, modLoc("cracked_opal_brick_slab_from_cracked_opal_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.CUT_OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.CUT_OPAL_BRICK_SLAB.get(), 2)
                .unlockedBy("has_cut_opal_bricks", has(ModBlocks.CUT_OPAL_BRICKS.get()))
                .save(consumer, modLoc("cut_opal_brick_slab_from_cut_opal_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.CUT_OPAL_BRICK_SLAB.get(), 2)
                .unlockedBy("has_opal_bricks", has(ModBlocks.CUT_OPAL_BRICKS.get()))
                .save(consumer, modLoc("cut_opal_brick_slab_from_opal_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.CUT_OPAL_BRICK_SLAB.get(), 2)
                .unlockedBy("has_opal", has(ModBlocks.OPAL.get()))
                .save(consumer, modLoc("cut_opal_brick_slab_from_opal_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.CUT_OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.CUT_OPAL_BRICK_STAIRS.get(), 1)
                .unlockedBy("has_cut_opal_bricks", has(ModBlocks.CUT_OPAL_BRICKS.get()))
                .save(consumer, modLoc("cut_opal_brick_stairs_from_cut_opal_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.CUT_OPAL_BRICK_STAIRS.get(), 1)
                .unlockedBy("has_opal_bricks", has(ModBlocks.OPAL_BRICKS.get()))
                .save(consumer, modLoc("cut_opal_brick_stairs_from_opal_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.CUT_OPAL_BRICK_STAIRS.get(), 1)
                .unlockedBy("has_opal", has(ModBlocks.OPAL.get()))
                .save(consumer, modLoc("cut_opal_brick_stairs_from_opal_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.CUT_OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.CUT_OPAL_BRICK_WALL.get(), 1)
                .unlockedBy("has_cut_opal_bricks", has(ModBlocks.CUT_OPAL_BRICKS.get()))
                .save(consumer, modLoc("cut_opal_brick_wall_from_cut_opal_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.CUT_OPAL_BRICK_WALL.get(), 1)
                .unlockedBy("has_opal_bricks", has(ModBlocks.OPAL_BRICKS.get()))
                .save(consumer, modLoc("cut_opal_brick_wall_from_opal_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.CUT_OPAL_BRICK_WALL.get(), 1)
                .unlockedBy("has_opal", has(ModBlocks.OPAL.get()))
                .save(consumer, modLoc("cut_opal_brick_wall_from_opal_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.CUT_OPAL_BRICKS.get(), 1)
                .unlockedBy("has_opal_bricks", has(ModBlocks.OPAL_BRICKS.get()))
                .save(consumer, modLoc("cut_opal_bricks_from_opal_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.CUT_OPAL_BRICKS.get(), 1)
                .unlockedBy("has_opal", has(ModBlocks.OPAL.get()))
                .save(consumer, modLoc("cut_opal_bricks_from_opal_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.OPAL_BRICK_SLAB.get(), 2)
                .unlockedBy("has_opal_bricks", has(ModBlocks.OPAL_BRICKS.get()))
                .save(consumer, modLoc("opal_brick_slab_from_opal_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.OPAL_BRICK_SLAB.get(), 2)
                .unlockedBy("has_opal", has(ModBlocks.OPAL.get()))
                .save(consumer, modLoc("opal_brick_slab_from_opal_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.OPAL_BRICK_STAIRS.get(), 1)
                .unlockedBy("has_opal_bricks", has(ModBlocks.OPAL_BRICKS.get()))
                .save(consumer, modLoc("opal_brick_stairs_from_opal_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.OPAL_BRICK_STAIRS.get(), 1)
                .unlockedBy("has_opal", has(ModBlocks.OPAL.get()))
                .save(consumer, modLoc("opal_brick_stairs_from_opal_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.OPAL_BRICK_WALL.get(), 1)
                .unlockedBy("has_opal_bricks", has(ModBlocks.OPAL_BRICKS.get()))
                .save(consumer, modLoc("opal_brick_wall_from_opal_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.OPAL_BRICK_WALL.get(), 1)
                .unlockedBy("has_opal", has(ModBlocks.OPAL.get()))
                .save(consumer, modLoc("opal_brick_wall_from_opal_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.OPAL_BRICKS.get(), 1)
                .unlockedBy("has_opal", has(ModBlocks.OPAL.get()))
                .save(consumer, modLoc("opal_bricks_from_opal_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL_COBBLESTONE.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.OPAL_COBBLESTONE_SLAB.get(), 2)
                .unlockedBy("has_opal_cobblestone", has(ModBlocks.OPAL_COBBLESTONE.get()))
                .save(consumer, modLoc("opal_cobblestone_slab_from_opal_cobblestone_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL_COBBLESTONE.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.OPAL_COBBLESTONE_STAIRS.get(), 1)
                .unlockedBy("has_opal_cobblestone", has(ModBlocks.OPAL_COBBLESTONE.get()))
                .save(consumer, modLoc("opal_cobblestone_stairs_from_opal_cobblestone_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL_COBBLESTONE.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.OPAL_COBBLESTONE_WALL.get(), 1)
                .unlockedBy("has_opal_cobblestone", has(ModBlocks.OPAL_COBBLESTONE.get()))
                .save(consumer, modLoc("opal_cobblestone_wall_from_opal_cobblestone_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.OPAL_SLAB.get(), 2)
                .unlockedBy("has_opal", has(ModBlocks.OPAL.get()))
                .save(consumer, modLoc("opal_slab_from_opal_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.OPAL.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.OPAL_STAIRS.get(), 1)
                .unlockedBy("has_opal", has(ModBlocks.OPAL.get()))
                .save(consumer, modLoc("opal_stairs_from_opal_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.SOUL_MAGMA_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.SOUL_MAGMA_BRICK_SLAB.get(), 2)
                .unlockedBy("has_soul_magma_block", has(ModBlocks.SOUL_MAGMA_BLOCK.get()))
                .save(consumer, modLoc("soul_magma_brick_slab_from_soul_magma_block_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.SOUL_MAGMA_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.SOUL_MAGMA_BRICK_SLAB.get(), 2)
                .unlockedBy("has_soul_magma_bricks", has(ModBlocks.SOUL_MAGMA_BRICKS.get()))
                .save(consumer, modLoc("soul_magma_brick_slab_from_soul_magma_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.SOUL_MAGMA_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.SOUL_MAGMA_BRICKS.get(), 1)
                .unlockedBy("has_soul_magma_block", has(ModBlocks.SOUL_MAGMA_BLOCK.get()))
                .save(consumer, modLoc("soul_magma_bricks_from_stone_magma_blocks_stonecutting"));
    }

    public void itemPedestalShaped(Consumer<FinishedRecipe> consumer, String id, ItemPedestalBlock itemPedestal, Block material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, itemPedestal, 2)
                .pattern(" I ")
                .pattern(" # ")
                .pattern("###")
                .define('#', material)
                .define('I', Items.ITEM_FRAME)
                .unlockedBy("has_" + id, has(material))
                .unlockedBy("has_item_frame", has(Items.ITEM_FRAME))
                .save(consumer, modLoc(id));
    }

    private ResourceLocation modLoc(String id) {
        return FastLoc.modLoc(id);
    }

}
