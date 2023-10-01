package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static class Blocks {

        public static final TagKey<Block> BUBBLE_COLUMN_GENERATOR = create("bubble_column_generator");
        public static final TagKey<Block> BUBBLE_COLUMN_DRAG_DOWN = create("bubble_column_drag_down");
        public static final TagKey<Block> BUBBLE_COLUMN_LIFT_UP = create("bubble_column_lift_up");
        //public static final TagKey<Block> SOUL_LAVA_CONVERTER = create("soul_lava_converter");
        public static final TagKey<Block> NEEDS_OPAL_CRYSTAL_TOOL = create("needs_opal_crystal_tools");

        public static TagKey<Block> create(String name) {
            return BlockTags.create(new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, name));
        }

    }

    public static class Items {

        public static final TagKey<Item> OPAL_STONES = create("opal_stones");
        public static final TagKey<Item> OPAL_CRYSTALS = create("opal_crystals");
        public static final TagKey<Item> ALLAY_DUPLICATION_ITEMS = create("allay_duplication_items");

        public static TagKey<Item> create(String name) {
            return ItemTags.create(new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, name));
        }
    }

    public static class EntityTypes {

        public static TagKey<EntityType<?>> SOUL_LAVA_IMMUNE = create("soul_lava_immune");

        public static TagKey<EntityType<?>> create(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, name));
        }
    }

}
