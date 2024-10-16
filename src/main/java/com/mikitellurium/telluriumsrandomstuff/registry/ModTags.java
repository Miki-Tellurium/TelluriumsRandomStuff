package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.core.registries.Registries;
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
        public static final TagKey<Block> NEEDS_OPAL_CRYSTAL_TOOL = create("needs_opal_crystal_tools");
        public static final TagKey<Block> NEEDS_SPIRITED_IRON_TOOL = create("needs_spirited_iron_tools");

        public static TagKey<Block> create(String name) {
            return BlockTags.create(FastLoc.modLoc(name));
        }

    }

    public static class Items {

        public static final TagKey<Item> OPAL_STONES = create("opal_stones");
        public static final TagKey<Item> OPAL_CRYSTALS = create("opal_crystals");
        public static final TagKey<Item> ALLAY_DUPLICATION_ITEMS = create("allay_duplication_items");
        public static final TagKey<Item> SOUL_LAVA_IMMUNE = create("soul_lava_immune");

        public static TagKey<Item> create(String name) {
            return ItemTags.create(FastLoc.modLoc(name));
        }
    }

    public static class EntityTypes {

        public static final TagKey<EntityType<?>> SOUL_LAVA_IMMUNE = create("soul_lava_immune");
        public static final TagKey<EntityType<?>> SOUL_FRAGMENT_DROP_BOSS = create("soul_fragment_drop_boss");

        public static TagKey<EntityType<?>> create(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, FastLoc.modLoc(name));
        }
    }

}
