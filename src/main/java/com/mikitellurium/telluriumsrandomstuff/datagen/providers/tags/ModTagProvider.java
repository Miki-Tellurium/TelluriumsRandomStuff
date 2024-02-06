package com.mikitellurium.telluriumsrandomstuff.datagen.providers.tags;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModTagProvider {

    public static class Blocks extends BlockTagsProvider {

        public Blocks(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                      @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, TelluriumsRandomStuffMod.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            this.tag(ModTags.Blocks.BUBBLE_COLUMN_DRAG_DOWN)
                    .add(ModBlocks.GRATE_MAGMA_BLOCK.get())
                    .add(ModBlocks.SOUL_MAGMA_BLOCK.get())
                    .add(ModBlocks.GRATE_SOUL_MAGMA_BLOCK.get());

            this.tag(ModTags.Blocks.BUBBLE_COLUMN_LIFT_UP)
                    .add(ModBlocks.GRATE_SOUL_SAND.get())
                    .add(ModBlocks.INFUSED_SOUL_SAND.get());

            this.tag(ModTags.Blocks.BUBBLE_COLUMN_GENERATOR)
                    .addTag(ModTags.Blocks.BUBBLE_COLUMN_DRAG_DOWN)
                    .addTag(ModTags.Blocks.BUBBLE_COLUMN_LIFT_UP);
        }
    }

    public static class Items extends ItemTagsProvider {

        public Items(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider,
                     CompletableFuture<TagLookup<Block>> blockLookup, @Nullable ExistingFileHelper existingFileHelper) {
            super(packOutput, lookupProvider, blockLookup, TelluriumsRandomStuffMod.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            this.tag(ModTags.Items.OPAL_CRYSTALS)
                    .add(ModBlocks.OPAL_CRYSTAL_BLOCK.get().asItem())
                    .add(ModBlocks.RAW_OPAL_CRYSTAL_BLOCK.get().asItem())
                    .add(ModItems.RAW_OPAL_CRYSTAL.get())
                    .add(ModItems.OPAL_CRYSTAL.get())
                    .add(ModItems.OPAL_CRYSTAL_AXE.get())
                    .add(ModItems.OPAL_CRYSTAL_PICKAXE.get())
                    .add(ModItems.OPAL_CRYSTAL_SHOVEL.get())
                    .add(ModItems.OPAL_CRYSTAL_SWORD.get())
                    .add(ModItems.OPAL_CRYSTAL_HOE.get());

            this.tag(ModTags.Items.OPAL_STONES)
                    .add(ModBlocks.OPAL.get().asItem())
                    .add(ModBlocks.OPAL_COBBLESTONE.get().asItem())
                    .add(ModBlocks.OPAL_BRICKS.get().asItem())
                    .add(ModBlocks.CUT_OPAL_BRICKS.get().asItem())
                    .add(ModBlocks.CHISELED_OPAL_BRICKS.get().asItem())
                    .add(ModBlocks.CRACKED_OPAL_BRICKS.get().asItem())
                    .add(ModBlocks.CRACKED_CUT_OPAL_BRICKS.get().asItem())
                    .add(ModBlocks.OPAL_SLAB.get().asItem())
                    .add(ModBlocks.OPAL_COBBLESTONE_SLAB.get().asItem())
                    .add(ModBlocks.OPAL_BRICK_SLAB.get().asItem())
                    .add(ModBlocks.CUT_OPAL_BRICK_SLAB.get().asItem())
                    .add(ModBlocks.CRACKED_OPAL_BRICK_SLAB.get().asItem())
                    .add(ModBlocks.CRACKED_CUT_OPAL_BRICK_SLAB.get().asItem())
                    .add(ModBlocks.OPAL_STAIRS.get().asItem())
                    .add(ModBlocks.OPAL_COBBLESTONE_STAIRS.get().asItem())
                    .add(ModBlocks.OPAL_BRICK_STAIRS.get().asItem())
                    .add(ModBlocks.CUT_OPAL_BRICK_STAIRS.get().asItem())
                    .add(ModBlocks.OPAL_COBBLESTONE_WALL.get().asItem())
                    .add(ModBlocks.OPAL_BRICK_WALL.get().asItem())
                    .add(ModBlocks.CUT_OPAL_BRICK_WALL.get().asItem())
                    .add(ModBlocks.OPAL_PRESSURE_PLATE.get().asItem())
                    .add(ModBlocks.OPAL_BUTTON.get().asItem())
                    .add(ModBlocks.OPAL_CRYSTAL_ORE.get().asItem());

            this.tag(ModTags.Items.ALLAY_DUPLICATION_ITEMS)
                    .add(net.minecraft.world.item.Items.ECHO_SHARD);
        }
    }

    public static class EntityTypes extends EntityTypeTagsProvider {

        public EntityTypes(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider,
                           @Nullable ExistingFileHelper existingFileHelper) {
            super(packOutput, lookupProvider, TelluriumsRandomStuffMod.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            this.tag(ModTags.EntityTypes.SOUL_LAVA_IMMUNE)
                    .addTag(EntityTypeTags.SKELETONS)
                    .add(EntityType.SKELETON_HORSE);

            this.tag(ModTags.EntityTypes.SOUL_FRAGMENT_DROP_BOSS)
                    .addTag(Tags.EntityTypes.BOSSES)
                    .add(EntityType.ELDER_GUARDIAN);
        }
    }

}
