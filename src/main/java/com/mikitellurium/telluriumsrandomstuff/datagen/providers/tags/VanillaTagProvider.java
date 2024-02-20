package com.mikitellurium.telluriumsrandomstuff.datagen.providers.tags;

import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class VanillaTagProvider {

    public static class Blocks extends BlockTagsProvider {

        public Blocks(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                      @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, "minecraft", existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                    .add(ModBlocks.GRATE_MAGMA_BLOCK.get())
                    .add(ModBlocks.HYDRODYNAMIC_RAIL.get())
                    .add(ModBlocks.SOUL_MAGMA_BLOCK.get())
                    .add(ModBlocks.GRATE_SOUL_MAGMA_BLOCK.get())
                    .add(ModBlocks.SOUL_FURNACE.get())
                    .add(ModBlocks.SOUL_MAGMA_BRICKS.get())
                    .add(ModBlocks.SOUL_MAGMA_BRICK_SLAB.get())
                    .add(ModBlocks.OPAL.get())
                    .add(ModBlocks.OPAL_COBBLESTONE.get())
                    .add(ModBlocks.OPAL_BRICKS.get())
                    .add(ModBlocks.CUT_OPAL_BRICKS.get())
                    .add(ModBlocks.CRACKED_OPAL_BRICKS.get())
                    .add(ModBlocks.CHISELED_OPAL_BRICKS.get())
                    .add(ModBlocks.CRACKED_CUT_OPAL_BRICKS.get())
                    .add(ModBlocks.OPAL_SLAB.get())
                    .add(ModBlocks.OPAL_STAIRS.get())
                    .add(ModBlocks.OPAL_COBBLESTONE_SLAB.get())
                    .add(ModBlocks.OPAL_COBBLESTONE_STAIRS.get())
                    .add(ModBlocks.OPAL_COBBLESTONE_WALL.get())
                    .add(ModBlocks.OPAL_BRICK_SLAB.get())
                    .add(ModBlocks.OPAL_BRICK_STAIRS.get())
                    .add(ModBlocks.OPAL_BRICK_WALL.get())
                    .add(ModBlocks.CUT_OPAL_BRICK_SLAB.get())
                    .add(ModBlocks.CUT_OPAL_BRICK_STAIRS.get())
                    .add(ModBlocks.CUT_OPAL_BRICK_WALL.get())
                    .add(ModBlocks.CRACKED_OPAL_BRICK_SLAB.get())
                    .add(ModBlocks.CRACKED_CUT_OPAL_BRICK_SLAB.get())
                    .add(ModBlocks.OPAL_PRESSURE_PLATE.get())
                    .add(ModBlocks.OPAL_BUTTON.get())
                    .add(ModBlocks.OPAL_CRYSTAL_ORE.get())
                    .add(ModBlocks.RAW_OPAL_CRYSTAL_BLOCK.get())
                    .add(ModBlocks.OPAL_CRYSTAL_BLOCK.get())
                    .add(ModBlocks.SOUL_OBSIDIAN.get())
                    .add(ModBlocks.SOUL_ANCHOR.get())
                    .add(ModBlocks.EXTRACTOR.get())
                    .add(ModBlocks.STONE_ITEM_PEDESTAL.get())
                    .add(ModBlocks.STONE_BRICK_ITEM_PEDESTAL.get())
                    .add(ModBlocks.MOSSY_STONE_BRICK_ITEM_PEDESTAL.get())
                    .add(ModBlocks.POLISHED_GRANITE_ITEM_PEDESTAL.get())
                    .add(ModBlocks.POLISHED_DIORITE_ITEM_PEDESTAL.get())
                    .add(ModBlocks.POLISHED_ANDESITE_ITEM_PEDESTAL.get())
                    .add(ModBlocks.POLISHED_DEEPSLATE_ITEM_PEDESTAL.get())
                    .add(ModBlocks.DEEPSLATE_BRICK_ITEM_PEDESTAL.get())
                    .add(ModBlocks.DEEPSLATE_TILE_ITEM_PEDESTAL.get())
                    .add(ModBlocks.CUT_SANDSTONE_ITEM_PEDESTAL.get())
                    .add(ModBlocks.CUT_RED_SANDSTONE_ITEM_PEDESTAL.get())
                    .add(ModBlocks.PRISMARINE_BRICK_ITEM_PEDESTAL.get())
                    .add(ModBlocks.NETHER_BRICK_ITEM_PEDESTAL.get())
                    .add(ModBlocks.RED_NETHER_BRICK_ITEM_PEDESTAL.get())
                    .add(ModBlocks.POLISHED_BLACKSTONE_ITEM_PEDESTAL.get())
                    .add(ModBlocks.POLISHED_BLACKSTONE_BRICK_ITEM_PEDESTAL.get())
                    .add(ModBlocks.END_STONE_BRICK_ITEM_PEDESTAL.get())
                    .add(ModBlocks.PURPUR_ITEM_PEDESTAL.get())
                    .add(ModBlocks.QUARTZ_ITEM_PEDESTAL.get())
                    .add(ModBlocks.SOUL_MAGMA_BRICK_ITEM_PEDESTAL.get())
                    .add(ModBlocks.OPAL_ITEM_PEDESTAL.get())
                    .add(ModBlocks.OPAL_BRICK_ITEM_PEDESTAL.get())
                    .add(ModBlocks.CUT_OPAL_BRICK_ITEM_PEDESTAL.get())
                    .add(ModBlocks.SOUL_INFUSER.get())
                    .add(ModBlocks.ALCHEMIXER.get());

            this.tag(BlockTags.MINEABLE_WITH_SHOVEL)
                    .add(ModBlocks.GRATE_SOUL_SAND.get())
                    .add(ModBlocks.INFUSED_SOUL_SAND.get());

            this.tag(BlockTags.MINEABLE_WITH_AXE)
                    .add(ModBlocks.SOUL_JACK_O_LANTERN.get());

            this.tag(BlockTags.MINEABLE_WITH_HOE)
                    .add(ModBlocks.AWAKENED_SCULK_SHRIEKER.get());

            this.tag(BlockTags.BUTTONS)
                    .add(ModBlocks.OPAL_BUTTON.get());

            this.tag(BlockTags.STONE_BUTTONS)
                    .add(ModBlocks.OPAL_BUTTON.get());

            this.tag(BlockTags.CAULDRONS)
                    .add(ModBlocks.SOUL_LAVA_CAULDRON.get());

            this.tag(BlockTags.DRIPSTONE_REPLACEABLE)
                    .add(ModBlocks.OPAL.get());

            this.tag(BlockTags.FLOWER_POTS)
                    .add(ModBlocks.POTTED_BRIGHT_TORCHFLOWER.get())
                    .add(ModBlocks.POTTED_SOUL_TORCHFLOWER.get());

            this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
                    .add(ModBlocks.SOUL_OBSIDIAN.get())
                    .add(ModBlocks.SOUL_ANCHOR.get());

            this.tag(BlockTags.NEEDS_IRON_TOOL)
                    .add(ModBlocks.OPAL_CRYSTAL_ORE.get())
                    .add(ModBlocks.OPAL_CRYSTAL_BLOCK.get())
                    .add(ModBlocks.RAW_OPAL_CRYSTAL_BLOCK.get());

            this.tag(BlockTags.PRESSURE_PLATES)
                    .add(ModBlocks.OPAL_PRESSURE_PLATE.get());

            this.tag(BlockTags.STONE_PRESSURE_PLATES)
                    .add(ModBlocks.OPAL_PRESSURE_PLATE.get());

            this.tag(BlockTags.RAILS)
                    .add(ModBlocks.HYDRODYNAMIC_RAIL.get());

            this.tag(BlockTags.SLABS)
                    .add(ModBlocks.OPAL_SLAB.get())
                    .add(ModBlocks.OPAL_COBBLESTONE_SLAB.get())
                    .add(ModBlocks.OPAL_BRICK_SLAB.get())
                    .add(ModBlocks.CUT_OPAL_BRICK_SLAB.get())
                    .add(ModBlocks.CRACKED_OPAL_BRICK_SLAB.get())
                    .add(ModBlocks.CRACKED_CUT_OPAL_BRICK_SLAB.get())
                    .add(ModBlocks.SOUL_MAGMA_BRICK_SLAB.get());

            this.tag(BlockTags.SOUL_FIRE_BASE_BLOCKS)
                    .add(ModBlocks.GRATE_SOUL_SAND.get())
                    .add(ModBlocks.SOUL_MAGMA_BLOCK.get())
                    .add(ModBlocks.GRATE_SOUL_MAGMA_BLOCK.get())
                    .add(ModBlocks.SOUL_MAGMA_BRICKS.get())
                    .add(ModBlocks.SOUL_MAGMA_BRICK_SLAB.get())
                    .add(ModBlocks.SOUL_FURNACE.get())
                    .add(ModBlocks.SOUL_OBSIDIAN.get())
                    .add(ModBlocks.INFUSED_SOUL_SAND.get())
                    .add(ModBlocks.SOUL_INFUSER.get());

            this.tag(BlockTags.SOUL_SPEED_BLOCKS)
                    .add(ModBlocks.SOUL_MAGMA_BLOCK.get())
                    .add(ModBlocks.SOUL_MAGMA_BRICKS.get())
                    .add(ModBlocks.SOUL_MAGMA_BRICK_SLAB.get())
                    .add(ModBlocks.INFUSED_SOUL_SAND.get());

            this.tag(BlockTags.STAIRS)
                    .add(ModBlocks.OPAL_STAIRS.get())
                    .add(ModBlocks.OPAL_COBBLESTONE_STAIRS.get())
                    .add(ModBlocks.OPAL_BRICK_STAIRS.get())
                    .add(ModBlocks.CUT_OPAL_BRICK_STAIRS.get());

            this.tag(BlockTags.STONE_BRICKS)
                    .add(ModBlocks.OPAL_BRICKS.get())
                    .add(ModBlocks.CUT_OPAL_BRICKS.get())
                    .add(ModBlocks.CHISELED_OPAL_BRICKS.get())
                    .add(ModBlocks.CRACKED_OPAL_BRICKS.get())
                    .add(ModBlocks.CRACKED_CUT_OPAL_BRICKS.get());

            this.tag(BlockTags.STRIDER_WARM_BLOCKS)
                    .add(ModBlocks.SOUL_LAVA_BLOCK.get());

            this.tag(BlockTags.WALLS)
                    .add(ModBlocks.OPAL_COBBLESTONE_WALL.get())
                    .add(ModBlocks.OPAL_BRICK_WALL.get())
                    .add(ModBlocks.CUT_OPAL_BRICK_WALL.get());

            this.tag(BlockTags.INFINIBURN_OVERWORLD)
                    .add(ModBlocks.GRATE_MAGMA_BLOCK.get())
                    .add(ModBlocks.SOUL_MAGMA_BLOCK.get())
                    .add(ModBlocks.GRATE_SOUL_MAGMA_BLOCK.get());

            this.tag(BlockTags.MAINTAINS_FARMLAND)
                    .add(ModBlocks.BRIGHT_TORCHFLOWER_CROP.get())
                    .add(ModBlocks.SOUL_TORCHFLOWER_CROP.get());

            this.tag(BlockTags.SMALL_FLOWERS)
                    .add(ModBlocks.BRIGHT_TORCHFLOWER.get())
                    .add(ModBlocks.SOUL_TORCHFLOWER.get());
        }
    }

    public static class Items extends ItemTagsProvider {

        public Items(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> future,
                     CompletableFuture<TagLookup<Block>> blockLookup, @Nullable ExistingFileHelper existingFileHelper) {
            super(packOutput, future, blockLookup, "minecraft", existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            this.tag(ItemTags.AXES)
                    .add(ModItems.OPAL_CRYSTAL_AXE.get());

            this.tag(ItemTags.HOES)
                    .add(ModItems.OPAL_CRYSTAL_HOE.get());

            this.tag(ItemTags.PICKAXES)
                    .add(ModItems.OPAL_CRYSTAL_PICKAXE.get());

            this.tag(ItemTags.SHOVELS)
                    .add(ModItems.OPAL_CRYSTAL_SHOVEL.get());

            this.tag(ItemTags.SWORDS)
                    .add(ModItems.OPAL_CRYSTAL_SWORD.get());

            this.tag(ItemTags.STONE_CRAFTING_MATERIALS)
                    .add(ModBlocks.OPAL_COBBLESTONE.get().asItem());

            this.tag(ItemTags.STONE_TOOL_MATERIALS)
                    .add(ModBlocks.OPAL_COBBLESTONE.get().asItem());

            this.copy(BlockTags.BUTTONS, ItemTags.BUTTONS);
            this.copy(BlockTags.STONE_BUTTONS, ItemTags.STONE_BUTTONS);
            this.copy(BlockTags.RAILS, ItemTags.RAILS);
            this.copy(BlockTags.SLABS, ItemTags.SLABS);
            this.copy(BlockTags.STAIRS, ItemTags.STAIRS);
            this.copy(BlockTags.STONE_BRICKS, ItemTags.STONE_BRICKS);
            this.copy(BlockTags.WALLS, ItemTags.WALLS);
        }
    }

}
