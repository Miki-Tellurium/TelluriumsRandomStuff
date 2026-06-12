package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.common.block.*;
import com.mikitellurium.telluriumsrandomstuff.common.fluid.SoulLavaBlock;
import com.mikitellurium.telluriumsrandomstuff.common.item.FloatingWalkwayBlockItem;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {

    public static final RegistryObject<Block> GRATE_SOUL_SAND = registerBlock("grate_soul_sand", GrateSoulSandBlock::new);
    public static final RegistryObject<Block> GRATE_MAGMA_BLOCK = registerBlock("grate_magma_block", GrateMagmaBlock::new);
    public static final RegistryObject<Block> CUSTOM_BUBBLE_COLUMN = registerBlock("custom_bubble_column", CustomBubbleColumnBlock::new);
    public static final RegistryObject<Block> HYDRODYNAMIC_RAIL = registerBlock("hydrodynamic_rail", HydrodynamicRailBlock::new);
    public static final RegistryObject<LiquidBlock> SOUL_LAVA_BLOCK = registerBlock("soul_lava_block", () -> new SoulLavaBlock(ModFluids.SOUL_LAVA_SOURCE), false);
    public static final RegistryObject<Block> SOUL_MAGMA_BLOCK = registerBlock("soul_magma_block", SoulMagmaBlock::new);
    public static final RegistryObject<Block> GRATE_SOUL_MAGMA_BLOCK = registerBlock("grate_soul_magma_block", GrateSoulMagmaBlock::new);
    public static final RegistryObject<Block> SOUL_FURNACE = registerBlock("soul_furnace", SoulFurnaceBlock::new);
    public static final RegistryObject<Block> SOUL_MAGMA_BRICKS = registerBlock("soul_magma_bricks", SoulMagmaBricksBlock::new);
    public static final RegistryObject<Block> SOUL_MAGMA_BRICK_SLAB = registerBlock("soul_magma_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(ModBlocks.SOUL_MAGMA_BRICKS.get())));
    public static final RegistryObject<Block> SOUL_LAVA_CAULDRON = registerBlock("soul_lava_cauldron", SoulLavaCauldronBlock::new, false);
    public static final RegistryObject<Block> OPAL = registerBlock("opal", () -> new OpalBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> SOUL_OBSIDIAN = registerBlock("soul_obsidian", SoulObsidianBlock::new);
    public static final RegistryObject<Block> SOUL_ANCHOR = registerBlock("soul_anchor", SoulAnchorBlock::new);
    public static final RegistryObject<Block> EXTRACTOR = registerBlock("extractor", ExtractorBlock::new);
    public static final RegistryObject<Block> BRIGHT_TORCHFLOWER = registerBlock("bright_torchflower", () -> new FlowerBlock(() -> MobEffects.NIGHT_VISION, 5, BlockBehaviour.Properties.copy(Blocks.TORCHFLOWER).lightLevel((blockState) -> 15)));
    public static final RegistryObject<Block> POTTED_BRIGHT_TORCHFLOWER = registerBlock("potted_bright_torchflower", () -> new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.FLOWER_POT, BRIGHT_TORCHFLOWER, BlockBehaviour.Properties.copy(Blocks.POTTED_TORCHFLOWER).lightLevel((blockState) -> 15)), false);
    public static final RegistryObject<Block> BRIGHT_TORCHFLOWER_CROP = registerBlock("bright_torchflower_crop", BrightTorchflowerCropBlock::new, false);
    public static final RegistryObject<Block> SOUL_TORCHFLOWER = registerBlock("soul_torchflower", () -> new FlowerBlock(() -> MobEffects.FIRE_RESISTANCE, 5, BlockBehaviour.Properties.copy(Blocks.TORCHFLOWER).lightLevel((blockState) -> 15)));
    public static final RegistryObject<Block> POTTED_SOUL_TORCHFLOWER = registerBlock("potted_soul_torchflower", () -> new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.FLOWER_POT, SOUL_TORCHFLOWER, BlockBehaviour.Properties.copy(Blocks.POTTED_TORCHFLOWER).lightLevel((blockState) -> 15)), false);
    public static final RegistryObject<Block> SOUL_TORCHFLOWER_CROP = registerBlock("soul_torchflower_crop", SoulTorchFlowerCropBlock::new, false);
    public static final RegistryObject<Block> BLUE_GLOWSTONE = registerBlock("blue_glowstone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GLOWSTONE)));
    public static final RegistryObject<Block> BLUE_REDSTONE_LAMP = registerBlock("blue_redstone_lamp", () -> new RedstoneLampBlock(BlockBehaviour.Properties.copy(Blocks.REDSTONE_LAMP)));
    public static final RegistryObject<Block> AWAKENED_SCULK_SHRIEKER = registerBlock("awakened_sculk_shrieker", AwakenedSculkShriekerBlock::new);
    public static final RegistryObject<Block> SOUL_JACK_O_LANTERN = registerBlock("soul_jack_o_lantern", () -> new CarvedPumpkinBlock(BlockBehaviour.Properties.copy(Blocks.JACK_O_LANTERN).lightLevel((state) -> 10)));
    public static final RegistryObject<Block> STONE_ITEM_PEDESTAL = registerBlock("stone_item_pedestal", () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> STONE_BRICK_ITEM_PEDESTAL = registerBlock("stone_brick_item_pedestal", () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> MOSSY_STONE_BRICK_ITEM_PEDESTAL = registerBlock("mossy_stone_brick_item_pedestal", () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.MOSSY_STONE_BRICKS).noOcclusion()));
    public static final RegistryObject<Block> POLISHED_GRANITE_ITEM_PEDESTAL = registerBlock("polished_granite_item_pedestal", () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_GRANITE).noOcclusion()));
    public static final RegistryObject<Block> POLISHED_DIORITE_ITEM_PEDESTAL = registerBlock("polished_diorite_item_pedestal", () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DIORITE).noOcclusion()));
    public static final RegistryObject<Block> POLISHED_ANDESITE_ITEM_PEDESTAL = registerBlock("polished_andesite_item_pedestal", () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_ANDESITE).noOcclusion()));
    public static final RegistryObject<Block> POLISHED_DEEPSLATE_ITEM_PEDESTAL = registerBlock("polished_deepslate_item_pedestal", () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE).noOcclusion()));
    public static final RegistryObject<Block> DEEPSLATE_BRICK_ITEM_PEDESTAL = registerBlock("deepslate_brick_item_pedestal", () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICKS).noOcclusion()));
    public static final RegistryObject<Block> DEEPSLATE_TILE_ITEM_PEDESTAL = registerBlock("deepslate_tile_item_pedestal", () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_TILES).noOcclusion()));
    public static final RegistryObject<Block> CUT_SANDSTONE_ITEM_PEDESTAL = registerBlock("cut_sandstone_item_pedestal", () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.CUT_SANDSTONE).noOcclusion()));
    public static final RegistryObject<Block> CUT_RED_SANDSTONE_ITEM_PEDESTAL = registerBlock("cut_red_sandstone_item_pedestal", () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.CUT_RED_SANDSTONE).noOcclusion()));
    public static final RegistryObject<Block> PRISMARINE_BRICK_ITEM_PEDESTAL = registerBlock("prismarine_brick_item_pedestal", () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.PRISMARINE_BRICKS).noOcclusion()));
    public static final RegistryObject<Block> NETHER_BRICK_ITEM_PEDESTAL = registerBlock("nether_brick_item_pedestal", () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS).noOcclusion()));
    public static final RegistryObject<Block> RED_NETHER_BRICK_ITEM_PEDESTAL = registerBlock("red_nether_brick_item_pedestal", () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.RED_NETHER_BRICKS).noOcclusion()));
    public static final RegistryObject<Block> POLISHED_BLACKSTONE_ITEM_PEDESTAL = registerBlock("polished_blackstone_item_pedestal", () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_BLACKSTONE).noOcclusion()));
    public static final RegistryObject<Block> POLISHED_BLACKSTONE_BRICK_ITEM_PEDESTAL = registerBlock("polished_blackstone_brick_item_pedestal", () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_BLACKSTONE_BRICKS).noOcclusion()));
    public static final RegistryObject<Block> END_STONE_BRICK_ITEM_PEDESTAL = registerBlock("end_stone_brick_item_pedestal", () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.END_STONE_BRICKS).noOcclusion()));
    public static final RegistryObject<Block> PURPUR_ITEM_PEDESTAL = registerBlock("purpur_item_pedestal", () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.PURPUR_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> QUARTZ_ITEM_PEDESTAL = registerBlock("quartz_item_pedestal", () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> INFUSED_SOUL_SAND = registerBlock("infused_soul_sand", InfusedSoulSandBlock::new);
    public static final RegistryObject<Block> SOUL_INFUSER = registerBlock("soul_infuser", SoulInfuserBlock::new);
    public static final RegistryObject<Block> ALCHEMIXER = registerBlock("alchemixer", AlchemixerBlock::new);
    public static final RegistryObject<Block> SOUL_COMPACTOR = registerBlock("soul_compactor", SoulCompactorBlock::new);
    public static final RegistryObject<Block> SPIRITED_IRON_BLOCK = registerBlock("spirited_iron_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SOUL_ASSEMBLY_TABLE = registerBlock("soul_assembly_table", SoulAssemblyTableBlock::new);
    public static final RegistryObject<Block> OAK_FLOATING_WALKWAY = registerBlock("oak_floating_walkway", () -> new FloatingWalkwayBlock(BlockBehaviour.Properties.of().mapColor(Blocks.OAK_PLANKS.defaultMapColor()).strength(1.5F, 3.0F).sound(SoundType.WOOD).noOcclusion().requiresCorrectToolForDrops()), (block) -> new FloatingWalkwayBlockItem(block, new Item.Properties()));
    public static final RegistryObject<Block> SPRUCE_FLOATING_WALKWAY = registerBlock("spruce_floating_walkway", () -> new FloatingWalkwayBlock(BlockBehaviour.Properties.of().mapColor(Blocks.SPRUCE_PLANKS.defaultMapColor()).strength(1.5F, 3.0F).sound(SoundType.WOOD).noOcclusion().requiresCorrectToolForDrops()), (block) -> new FloatingWalkwayBlockItem(block, new Item.Properties()));
    public static final RegistryObject<Block> BIRCH_FLOATING_WALKWAY = registerBlock("birch_floating_walkway", () -> new FloatingWalkwayBlock(BlockBehaviour.Properties.of().mapColor(Blocks.BIRCH_PLANKS.defaultMapColor()).strength(1.5F, 3.0F).sound(SoundType.WOOD).noOcclusion().requiresCorrectToolForDrops()), (block) -> new FloatingWalkwayBlockItem(block, new Item.Properties()));
    public static final RegistryObject<Block> JUNGLE_FLOATING_WALKWAY = registerBlock("jungle_floating_walkway", () -> new FloatingWalkwayBlock(BlockBehaviour.Properties.of().mapColor(Blocks.JUNGLE_PLANKS.defaultMapColor()).strength(1.5F, 3.0F).sound(SoundType.WOOD).noOcclusion().requiresCorrectToolForDrops()), (block) -> new FloatingWalkwayBlockItem(block, new Item.Properties()));
    public static final RegistryObject<Block> ACACIA_FLOATING_WALKWAY = registerBlock("acacia_floating_walkway", () -> new FloatingWalkwayBlock(BlockBehaviour.Properties.of().mapColor(Blocks.ACACIA_PLANKS.defaultMapColor()).strength(1.5F, 3.0F).sound(SoundType.WOOD).noOcclusion().requiresCorrectToolForDrops()), (block) -> new FloatingWalkwayBlockItem(block, new Item.Properties()));
    public static final RegistryObject<Block> DARK_OAK_FLOATING_WALKWAY = registerBlock("dark_oak_floating_walkway", () -> new FloatingWalkwayBlock(BlockBehaviour.Properties.of().mapColor(Blocks.DARK_OAK_PLANKS.defaultMapColor()).strength(1.5F, 3.0F).sound(SoundType.WOOD).noOcclusion().requiresCorrectToolForDrops()), (block) -> new FloatingWalkwayBlockItem(block, new Item.Properties()));
    public static final RegistryObject<Block> MANGROVE_FLOATING_WALKWAY = registerBlock("mangrove_floating_walkway", () -> new FloatingWalkwayBlock(BlockBehaviour.Properties.of().mapColor(Blocks.MANGROVE_PLANKS.defaultMapColor()).strength(1.5F, 3.0F).sound(SoundType.WOOD).noOcclusion().requiresCorrectToolForDrops()), (block) -> new FloatingWalkwayBlockItem(block, new Item.Properties()));
    public static final RegistryObject<Block> CHERRY_FLOATING_WALKWAY = registerBlock("cherry_floating_walkway", () -> new FloatingWalkwayBlock(BlockBehaviour.Properties.of().mapColor(Blocks.CHERRY_PLANKS.defaultMapColor()).strength(1.5F, 3.0F).sound(SoundType.WOOD).noOcclusion().requiresCorrectToolForDrops()), (block) -> new FloatingWalkwayBlockItem(block, new Item.Properties()));
    public static final RegistryObject<Block> BAMBOO_FLOATING_WALKWAY = registerBlock("bamboo_floating_walkway", () -> new FloatingWalkwayBlock(BlockBehaviour.Properties.of().mapColor(Blocks.BAMBOO_PLANKS.defaultMapColor()).strength(1.5F, 3.0F).sound(SoundType.WOOD).noOcclusion().requiresCorrectToolForDrops()), (block) -> new FloatingWalkwayBlockItem(block, new Item.Properties()));
    public static final RegistryObject<Block> CRIMSON_FLOATING_WALKWAY = registerBlock("crimson_floating_walkway", () -> new FloatingWalkwayBlock(BlockBehaviour.Properties.of().mapColor(Blocks.CRIMSON_PLANKS.defaultMapColor()).strength(1.5F, 3.0F).sound(SoundType.WOOD).noOcclusion().requiresCorrectToolForDrops()), (block) -> new FloatingWalkwayBlockItem(block, new Item.Properties()));
    public static final RegistryObject<Block> WARPED_FLOATING_WALKWAY = registerBlock("warped_floating_walkway", () -> new FloatingWalkwayBlock(BlockBehaviour.Properties.of().mapColor(Blocks.WARPED_PLANKS.defaultMapColor()).strength(1.5F, 3.0F).sound(SoundType.WOOD).noOcclusion().requiresCorrectToolForDrops()), (block) -> new FloatingWalkwayBlockItem(block, new Item.Properties()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        return registerBlock(name, block, true);
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, boolean withItem) {
        RegistryObject<T> object = ModRegistries.BLOCKS.register(name, block);
        if (withItem) registerBlockItem(name, object);
        return object;
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, Function<T, BlockItem> blockItemFunction) {
        RegistryObject<T> object = ModRegistries.BLOCKS.register(name, block);
        registerBlockItem(name, () -> blockItemFunction.apply(object.get()));
        return object;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return registerBlockItem(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static <T extends Item> RegistryObject<Item> registerBlockItem(String name, Supplier<T> blockItem) {
        return ModRegistries.ITEMS.register(name, blockItem);
    }

    protected static void register(IEventBus eventBus) {
        ModRegistries.BLOCKS.register(eventBus);
    }

}
