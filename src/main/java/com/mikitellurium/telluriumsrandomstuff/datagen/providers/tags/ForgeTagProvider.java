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
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ForgeTagProvider {

    public static class Blocks extends BlockTagsProvider {

        public Blocks(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                      @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, "forge", existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            this.tag(Tags.Blocks.STONE)
                    .add(ModBlocks.OPAL.get());

            this.tag(Tags.Blocks.COBBLESTONE)
                    .add(ModBlocks.OPAL_COBBLESTONE.get());

            this.tag(Tags.Blocks.COBBLESTONE_NORMAL)
                    .add(ModBlocks.OPAL_COBBLESTONE.get());

            this.tag(Tags.Blocks.OBSIDIAN)
                    .add(ModBlocks.SOUL_OBSIDIAN.get());

            this.tag(Tags.Blocks.ORES)
                    .add(ModBlocks.OPAL_CRYSTAL_ORE.get());

            this.tag(Tags.Blocks.STORAGE_BLOCKS)
                    .add(ModBlocks.OPAL_CRYSTAL_BLOCK.get())
                    .add(ModBlocks.RAW_OPAL_CRYSTAL_BLOCK.get())
                    .add(ModBlocks.SOUL_INFUSED_IRON_BLOCK.get());
        }
    }

    public static class Items extends ItemTagsProvider {

        public Items(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider,
                     CompletableFuture<TagLookup<Block>> blockLookup, @Nullable ExistingFileHelper existingFileHelper) {
            super(packOutput, lookupProvider, blockLookup, "forge", existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            this.tag(Tags.Items.TOOLS)
                    .add(ModItems.OPAL_CRYSTAL_SWORD.get())
                    .add(ModItems.OPAL_CRYSTAL_PICKAXE.get())
                    .add(ModItems.OPAL_CRYSTAL_AXE.get())
                    .add(ModItems.OPAL_CRYSTAL_SWORD.get())
                    .add(ModItems.OPAL_CRYSTAL_HOE.get())
                    .add(ModItems.SOUL_INFUSED_IRON_SWORD.get())
                    .add(ModItems.SOUL_INFUSED_IRON_PICKAXE.get())
                    .add(ModItems.SOUL_INFUSED_IRON_AXE.get())
                    .add(ModItems.SOUL_INFUSED_IRON_SHOVEL.get())
                    .add(ModItems.SOUL_INFUSED_IRON_HOE.get())
                    .add(ModItems.GRAPPLING_HOOK.get());

            this.tag(Tags.Items.ARMORS)
                    .add(ModItems.SOUL_INFUSED_IRON_BOOTS.get())
                    .add(ModItems.SOUL_INFUSED_IRON_LEGGINGS.get())
                    .add(ModItems.SOUL_INFUSED_IRON_CHESTPLATE.get())
                    .add(ModItems.SOUL_INFUSED_IRON_HELMET.get());

            this.tag(Tags.Items.ARMORS_BOOTS)
                    .add(ModItems.SOUL_INFUSED_IRON_BOOTS.get());

            this.tag(Tags.Items.ARMORS_LEGGINGS)
                    .add(ModItems.SOUL_INFUSED_IRON_LEGGINGS.get());

            this.tag(Tags.Items.ARMORS_CHESTPLATES)
                    .add(ModItems.SOUL_INFUSED_IRON_CHESTPLATE.get());

            this.tag(Tags.Items.ARMORS_HELMETS)
                    .add(ModItems.SOUL_INFUSED_IRON_HELMET.get());

            this.tag(Tags.Items.DUSTS)
                    .add(ModItems.BLUE_GLOWSTONE_DUST.get());

            this.tag(Tags.Items.RAW_MATERIALS)
                    .add(ModItems.RAW_OPAL_CRYSTAL.get());

            this.tag(Tags.Items.GEMS)
                    .add(ModItems.OPAL_CRYSTAL.get());

            this.tag(Tags.Items.INGOTS)
                    .add(ModItems.SOUL_INFUSED_IRON_INGOT.get());

            this.tag(Tags.Items.RODS)
                    .add(ModItems.SOUL_IRON_ROD.get());

            this.copy(Tags.Blocks.COBBLESTONE, Tags.Items.COBBLESTONE);
            this.copy(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS);
        }
    }

}
