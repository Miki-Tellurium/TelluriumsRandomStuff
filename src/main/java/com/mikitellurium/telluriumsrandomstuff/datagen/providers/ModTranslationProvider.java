package com.mikitellurium.telluriumsrandomstuff.datagen.providers;

import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModEnchantments;
import com.mikitellurium.telluriumsrandomstuff.registry.ModEntities;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModTranslationProvider extends LanguageProvider {

    public ModTranslationProvider(PackOutput output) {
        super(output, FastLoc.modId(), "en_us");
    }

    @Override
    protected void addTranslations() {
        add("creativemodetab.telluriumsrandomstuff_creative_tab", "Tellurium's Random Stuff");
        // Items
        addItem(ModItems.SOUL_LAVA_BUCKET, "Soul Lava Bucket");
        addItem(ModItems.MYSTIC_POTATO, "Mystic Potato");
        addItem(ModItems.RAW_OPAL_CRYSTAL, "Raw Opal Crystal");
        addItem(ModItems.OPAL_CRYSTAL, "Opal Crystal");
        addItem(ModItems.OPAL_CRYSTAL_SWORD, "Opal Crystal Sword");
        addItem(ModItems.OPAL_CRYSTAL_SHOVEL, "Opal Crystal Shovel");
        addItem(ModItems.OPAL_CRYSTAL_PICKAXE, "Opal Crystal Pickaxe");
        addItem(ModItems.OPAL_CRYSTAL_AXE, "Opal Crystal Axe");
        addItem(ModItems.OPAL_CRYSTAL_HOE, "Opal Crystal Hoe");
        addItem(ModItems.FILTER, "Filter");
        translation("item", "bright_torchflower", "Bright Torchflower");
        addItem(ModItems.BRIGHT_TORCHFLOWER_SEEDS, "Bright Torchflower Seeds");
        translation("item", "soul_torchflower", "Soul Torchflower");
        addItem(ModItems.SOUL_TORCHFLOWER_SEEDS, "Soul Torchflower Seeds");
        addItem(ModItems.BLUE_GLOWSTONE_DUST, "Blue Glowstone Dust");
        addItem(ModItems.LAVA_GOOGLES, "Lava Googles");
        addItem(ModItems.MOLTEN_AMETHYST, "Molten Amethyst");
        addItem(ModItems.AMETHYST_LENS, "Amethyst Lens");
        addItem(ModItems.SMALL_SOUL_FRAGMENT, "Small Soul Fragment");
        addItem(ModItems.SOUL_FRAGMENT, "Soul Fragment");
        addItem(ModItems.SOUL_CLUSTER, "Soul Cluster");
        addItem(ModItems.SOUL_INFUSER_LIT, "Soul Infuser");
        addItem(ModItems.GRAPPLING_HOOK, "Grappling Hook");
        addItem(ModItems.SOUL_INFUSED_IRON_INGOT, "Soul Infused Iron Ingot");
        addItem(ModItems.SOUL_IRON_ROD, "Soul Iron Rod");
        addItem(ModItems.SOUL_INFUSED_IRON_SWORD, "Soul Infused Iron Sword");
        addItem(ModItems.SOUL_INFUSED_IRON_SHOVEL, "Soul Infused Iron Shovel");
        addItem(ModItems.SOUL_INFUSED_IRON_PICKAXE, "Soul Infused Iron Pickaxe");
        addItem(ModItems.SOUL_INFUSED_IRON_AXE, "Soul Infused Iron Axe");
        addItem(ModItems.SOUL_INFUSED_IRON_HOE, "Soul Infused Iron Hoe");
        addItem(ModItems.SOUL_INFUSED_IRON_BOOTS, "Soul Infused Iron Boots");
        addItem(ModItems.SOUL_INFUSED_IRON_LEGGINGS, "Soul Infused Iron Leggings");
        addItem(ModItems.SOUL_INFUSED_IRON_CHESTPLATE, "Soul Infused Iron Chestplate");
        addItem(ModItems.SOUL_INFUSED_IRON_HELMET, "Soul Infused Iron Helmet");

        translation("item", "lava_googles.tooltip.color", "Color");
        translation("item", "mystic_potato.tooltip", "What will this do?");
        translation("item", "mixed_potion.name", "Mixed Potion");
        translation("item", "mixed_splash_potion.name", "Mixed Splash Potion");
        translation("item", "mixed_lingering_potion.name", "Mixed Lingering Potion");
        // Blocks
        addBlock(ModBlocks.GRATE_MAGMA_BLOCK, "Magma Block with Grate");
        addBlock(ModBlocks.GRATE_SOUL_SAND, "Soul Sand with Grate");
        addBlock(ModBlocks.HYDRODYNAMIC_RAIL, "Hydrodynamic Rail");
        addBlock(ModBlocks.SOUL_MAGMA_BLOCK, "Soul Magma Block");
        addBlock(ModBlocks.GRATE_SOUL_MAGMA_BLOCK, "Soul Magma Block with Grate");
        addBlock(ModBlocks.SOUL_FURNACE, "Soul Furnace");
        addBlock(ModBlocks.SOUL_MAGMA_BRICKS, "Soul Magma Bricks");
        addBlock(ModBlocks.SOUL_MAGMA_BRICK_SLAB, "Soul Magma Brick Slab");
        addBlock(ModBlocks.OPAL, "Opal");
        addBlock(ModBlocks.OPAL_COBBLESTONE, "Opal Cobblestone");
        addBlock(ModBlocks.OPAL_BRICKS, "Opal Bricks");
        addBlock(ModBlocks.CUT_OPAL_BRICKS, "Cut Opal Bricks");
        addBlock(ModBlocks.CHISELED_OPAL_BRICKS, "Chiseled Opal Bricks");
        addBlock(ModBlocks.CRACKED_OPAL_BRICKS, "Cracked Opal Bricks");
        addBlock(ModBlocks.CRACKED_CUT_OPAL_BRICKS, "Cracked Cut Opal Bricks");
        addBlock(ModBlocks.OPAL_SLAB, "Opal Slab");
        addBlock(ModBlocks.OPAL_COBBLESTONE_SLAB, "Opal Cobblestone Slab");
        addBlock(ModBlocks.OPAL_BRICK_SLAB, "Opal Brick Slab");
        addBlock(ModBlocks.CUT_OPAL_BRICK_SLAB, "Cut Opal Brick Slab");
        addBlock(ModBlocks.CRACKED_OPAL_BRICK_SLAB, "Cracked Opal Brick Slab");
        addBlock(ModBlocks.CRACKED_CUT_OPAL_BRICK_SLAB, "Cracked Cut Opal Brick Slab");
        addBlock(ModBlocks.OPAL_STAIRS, "Opal Stairs");
        addBlock(ModBlocks.OPAL_COBBLESTONE_STAIRS, "Opal Cobblestone Stairs");
        addBlock(ModBlocks.OPAL_BRICK_STAIRS, "Opal Brick Stairs");
        addBlock(ModBlocks.CUT_OPAL_BRICK_STAIRS, "Cut Opal Brick Stairs");
        addBlock(ModBlocks.OPAL_COBBLESTONE_WALL, "Opal Cobblestone Wall");
        addBlock(ModBlocks.OPAL_BRICK_WALL, "Opal Brick Wall");
        addBlock(ModBlocks.CUT_OPAL_BRICK_WALL, "Cut Opal Brick Wall");
        addBlock(ModBlocks.OPAL_PRESSURE_PLATE, "Opal Pressure Plate");
        addBlock(ModBlocks.OPAL_BUTTON, "Opal Button");
        addBlock(ModBlocks.OPAL_CRYSTAL_ORE, "Opal Crystal Ore");
        addBlock(ModBlocks.RAW_OPAL_CRYSTAL_BLOCK, "Raw Opal Crystal Block");
        addBlock(ModBlocks.OPAL_CRYSTAL_BLOCK, "Opal Crystal Block");
        addBlock(ModBlocks.SOUL_OBSIDIAN, "Soul Obsidian");
        addBlock(ModBlocks.SOUL_ANCHOR, "Soul Anchor");
        addBlock(ModBlocks.EXTRACTOR, "Extractor");
        addBlock(ModBlocks.BRIGHT_TORCHFLOWER, "Bright Torchflower");
        addBlock(ModBlocks.SOUL_TORCHFLOWER, "Soul Torchflower");
        addBlock(ModBlocks.BLUE_GLOWSTONE, "Blue Glowstone");
        addBlock(ModBlocks.BLUE_REDSTONE_LAMP, "Blue Redstone Lamp");
        addBlock(ModBlocks.AWAKENED_SCULK_SHRIEKER, "Awakened Sculk Shrieker");
        addBlock(ModBlocks.SOUL_JACK_O_LANTERN, "Soul Jack o'Lantern");
        addBlock(ModBlocks.STONE_ITEM_PEDESTAL, "Stone Item Pedestal");
        addBlock(ModBlocks.STONE_BRICK_ITEM_PEDESTAL, "Stone Brick Item Pedestal");
        addBlock(ModBlocks.MOSSY_STONE_BRICK_ITEM_PEDESTAL, "Mossy Stone Brick Item Pedestal");
        addBlock(ModBlocks.POLISHED_GRANITE_ITEM_PEDESTAL, "Polished Granite Item Pedestal");
        addBlock(ModBlocks.POLISHED_DIORITE_ITEM_PEDESTAL, "Polished Diorite Item Pedestal");
        addBlock(ModBlocks.POLISHED_ANDESITE_ITEM_PEDESTAL, "Polished Andesite Item Pedestal");
        addBlock(ModBlocks.POLISHED_DEEPSLATE_ITEM_PEDESTAL, "Polished Deepslate Item Pedestal");
        addBlock(ModBlocks.DEEPSLATE_BRICK_ITEM_PEDESTAL, "Deepslate Brick Item Pedestal");
        addBlock(ModBlocks.DEEPSLATE_TILE_ITEM_PEDESTAL, "Deepslate Tile Item Pedestal");
        addBlock(ModBlocks.CUT_SANDSTONE_ITEM_PEDESTAL, "Cut Sandstone Item Pedestal");
        addBlock(ModBlocks.CUT_RED_SANDSTONE_ITEM_PEDESTAL, "Cut Red Sandstone Item Pedestal");
        addBlock(ModBlocks.PRISMARINE_BRICK_ITEM_PEDESTAL, "Prismarine Brick Item Pedestal");
        addBlock(ModBlocks.NETHER_BRICK_ITEM_PEDESTAL, "Nether Brick Item Pedestal");
        addBlock(ModBlocks.RED_NETHER_BRICK_ITEM_PEDESTAL, "Red Nether Brick Item Pedestal");
        addBlock(ModBlocks.POLISHED_BLACKSTONE_ITEM_PEDESTAL, "Polished Blackstone Item Pedestal");
        addBlock(ModBlocks.POLISHED_BLACKSTONE_BRICK_ITEM_PEDESTAL, "Polished Blackstone Brick Item Pedestal");
        addBlock(ModBlocks.END_STONE_BRICK_ITEM_PEDESTAL, "End Stone Brick Item Pedestal");
        addBlock(ModBlocks.PURPUR_ITEM_PEDESTAL, "Purpur Item Pedestal");
        addBlock(ModBlocks.QUARTZ_ITEM_PEDESTAL, "Quartz Item Pedestal");
        addBlock(ModBlocks.SOUL_MAGMA_BRICK_ITEM_PEDESTAL, "Soul Magma Brick Item Pedestal");
        addBlock(ModBlocks.OPAL_ITEM_PEDESTAL, "Opal Item Pedestal");
        addBlock(ModBlocks.OPAL_BRICK_ITEM_PEDESTAL, "Opal Brick Item Pedestal");
        addBlock(ModBlocks.CUT_OPAL_BRICK_ITEM_PEDESTAL, "Cut Opal Brick Item Pedestal");
        addBlock(ModBlocks.INFUSED_SOUL_SAND, "Infused Soul Sand");
        addBlock(ModBlocks.SOUL_INFUSER, "Soul Infuser");
        addBlock(ModBlocks.SOUL_LAVA_CAULDRON, "Soul Lava Cauldron");
        addBlock(ModBlocks.ALCHEMIXER, "Alchemixer");
        addBlock(ModBlocks.SOUL_COMPACTOR, "Soul Compactor");

        translation("block", "awakened_sculk_shrieker.tooltip.warning", "Warning!");
        translation("block", "awakened_sculk_shrieker.tooltip.message", "This can spawn a Warden anywhere. Use with caution.");
        // Block Entities
        translation("blockentity", "soul_furnace", "Soul Furnace");
        translation("blockentity", "soul_anchor", "Soul Anchor");
        translation("blockentity", "soul_anchor.warning", "You already have a charged soul anchor");
        translation("blockentity", "extractor", "Extractor");
        translation("blockentity", "soul_infuser", "Soul Infuser");
        translation("blockentity", "alchemixer", "Alchemixer");
        // Fluids
        translation("fluid_type", "soul_lava", "Soul Lava");
        // Entity Types
        addEntityType(ModEntities.GRAPPLING_HOOK, "Grappling Hook");
        // Enchantments
        addEnchantment(ModEnchantments.SOUL_HARVESTING, "Soul Harvesting");
        // Trims
        translation("trim_material", "opal_crystal", "Opal Crystal Material");
        // Jei
        translation("jei", "category.soul_furnace_smelting", "Soul Furnace Smelting");
        translation("jei", "category.soul_infusion", "Soul Infusion");
        translation("jei", "category.soul_infusion.cost", "Cost");
        translation("jei", "category.soul_lava_crafting", "Soul Lava Crafting");
        translation("jei", "category.soul_lava_crafting.description", "Let lava drip through infused soul sand and into an empty cauldron.");
        translation("jei", "category.amethyst_lens_crafting", "Amethyst Lens Crafting");
        translation("jei", "category.soul_lava_transmutation", "Soul Lava Transmutation");
        translation("jei", "category.potion_mixing", "Potion Mixing");
        translation("jei", "category.potion_mixing.label.amplifier", "Increase Level");
        translation("jei", "category.potion_mixing.label.duration", "Increase Duration");
        translation("jei", "category.potion_mixing.label.effect_mix", "Mix Effects");
    }

    private void translation(String prefix, String suffix, String translation) {
        this.add(prefix + "." + FastLoc.modId() + "." + suffix, translation);
    }

}
