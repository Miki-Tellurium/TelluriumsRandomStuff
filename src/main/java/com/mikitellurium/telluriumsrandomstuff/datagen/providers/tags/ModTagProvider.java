package com.mikitellurium.telluriumsrandomstuff.datagen.providers.tags;

import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.registry.ModTags;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModTagProvider {
    public static class Blocks extends BlockTagsProvider {
        public Blocks(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                      @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, FastLoc.modId(), existingFileHelper);
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

            this.tag(ModTags.Blocks.FLOATING_WALKWAYS)
                    .add(ModBlocks.OAK_FLOATING_WALKWAY.get())
                    .add(ModBlocks.SPRUCE_FLOATING_WALKWAY.get())
                    .add(ModBlocks.JUNGLE_FLOATING_WALKWAY.get())
                    .add(ModBlocks.ACACIA_FLOATING_WALKWAY.get())
                    .add(ModBlocks.DARK_OAK_FLOATING_WALKWAY.get())
                    .add(ModBlocks.MANGROVE_FLOATING_WALKWAY.get())
                    .add(ModBlocks.CHERRY_FLOATING_WALKWAY.get())
                    .add(ModBlocks.BAMBOO_FLOATING_WALKWAY.get())
                    .add(ModBlocks.CRIMSON_FLOATING_WALKWAY.get())
                    .add(ModBlocks.WARPED_FLOATING_WALKWAY.get());
        }
    }

    public static class Items extends ItemTagsProvider {
        public Items(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider,
                     CompletableFuture<TagLookup<Block>> blockLookup, @Nullable ExistingFileHelper existingFileHelper) {
            super(packOutput, lookupProvider, blockLookup, FastLoc.modId(), existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            this.tag(ModTags.Items.ALLAY_DUPLICATION_ITEMS)
                    .add(net.minecraft.world.item.Items.ECHO_SHARD)
                    .add(net.minecraft.world.item.Items.AMETHYST_SHARD);

            this.tag(ModTags.Items.SOUL_LAVA_IMMUNE)
                    .add(ModItems.SMALL_SOUL_FRAGMENT.get())
                    .add(ModItems.SOUL_FRAGMENT.get())
                    .add(ModItems.SOUL_CLUSTER.get());
        }
    }

    public static class EntityTypes extends EntityTypeTagsProvider {
        public EntityTypes(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider,
                           @Nullable ExistingFileHelper existingFileHelper) {
            super(packOutput, lookupProvider, FastLoc.modId(), existingFileHelper);
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
