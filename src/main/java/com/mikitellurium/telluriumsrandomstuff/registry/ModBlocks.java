package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.common.block.*;
import com.mikitellurium.telluriumsrandomstuff.common.fluid.SoulLavaBlock;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static  final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, FastLoc.modId());

    public static final RegistryObject<Block> GRATE_SOUL_SAND = registerBlockWithItem("grate_soul_sand", GrateSoulSandBlock::new);

    public static final RegistryObject<Block> GRATE_MAGMA_BLOCK = registerBlockWithItem("grate_magma_block", GrateMagmaBlock::new);

    public static final RegistryObject<Block> CUSTOM_BUBBLE_COLUMN = registerBlockWithItem("custom_bubble_column", CustomBubbleColumnBlock::new);

    public static final RegistryObject<Block> HYDRODYNAMIC_RAIL = registerBlockWithItem("hydrodynamic_rail", HydrodynamicRailBlock::new);

    public static final RegistryObject<LiquidBlock> SOUL_LAVA_BLOCK = BLOCKS.register("soul_lava_block",
            () -> new SoulLavaBlock(ModFluids.SOUL_LAVA_SOURCE));

    public static final RegistryObject<Block> SOUL_MAGMA_BLOCK = registerBlockWithItem("soul_magma_block", SoulMagmaBlock::new);

    public static final RegistryObject<Block> GRATE_SOUL_MAGMA_BLOCK = registerBlockWithItem("grate_soul_magma_block", GrateSoulMagmaBlock::new);

    public static final RegistryObject<Block> SOUL_FURNACE = registerBlockWithItem("soul_furnace", SoulFurnaceBlock::new);

    public static final RegistryObject<Block> SOUL_MAGMA_BRICKS = registerBlockWithItem("soul_magma_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS)
                    .isValidSpawn((blockState, blockGetter, blockPos, entityType) -> entityType.is(ModTags.EntityTypes.SOUL_LAVA_IMMUNE))
                    .lightLevel((blockState) -> 2)
                    .emissiveRendering((blockState, blockGetter, blockPos) -> true)));

    public static final RegistryObject<Block> SOUL_MAGMA_BRICK_SLAB = registerBlockWithItem("soul_magma_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(ModBlocks.SOUL_MAGMA_BRICKS.get())));

    public static final RegistryObject<Block> SOUL_LAVA_CAULDRON = BLOCKS.register("soul_lava_cauldron", SoulLavaCauldronBlock::new);

    public static final RegistryObject<Block> OPAL = registerBlockWithItem("opal",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> OPAL_COBBLESTONE = registerBlockWithItem("opal_cobblestone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));

    public static final RegistryObject<Block> OPAL_BRICKS = registerBlockWithItem("opal_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> CUT_OPAL_BRICKS = registerBlockWithItem("cut_opal_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> CHISELED_OPAL_BRICKS = registerBlockWithItem("chiseled_opal_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> CRACKED_OPAL_BRICKS = registerBlockWithItem("cracked_opal_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> CRACKED_CUT_OPAL_BRICKS = registerBlockWithItem("cracked_cut_opal_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> OPAL_SLAB = registerBlockWithItem("opal_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(ModBlocks.OPAL_COBBLESTONE.get())));

    public static final RegistryObject<Block> OPAL_COBBLESTONE_SLAB = registerBlockWithItem("opal_cobblestone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(ModBlocks.OPAL_COBBLESTONE.get())));

    public static final RegistryObject<Block> OPAL_BRICK_SLAB = registerBlockWithItem("opal_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(ModBlocks.OPAL_COBBLESTONE.get())));

    public static final RegistryObject<Block> CUT_OPAL_BRICK_SLAB = registerBlockWithItem("cut_opal_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(ModBlocks.OPAL_COBBLESTONE.get())));

    public static final RegistryObject<Block> CRACKED_OPAL_BRICK_SLAB = registerBlockWithItem("cracked_opal_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(ModBlocks.OPAL_COBBLESTONE.get())));

    public static final RegistryObject<Block> CRACKED_CUT_OPAL_BRICK_SLAB = registerBlockWithItem("cracked_cut_opal_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(ModBlocks.OPAL_COBBLESTONE.get())));

    public static final RegistryObject<Block> OPAL_STAIRS = registerBlockWithItem("opal_stairs",
            () -> new StairBlock(Blocks.STONE::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> OPAL_COBBLESTONE_STAIRS = registerBlockWithItem("opal_cobblestone_stairs",
            () -> new StairBlock(Blocks.COBBLESTONE::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));

    public static final RegistryObject<Block> OPAL_BRICK_STAIRS = registerBlockWithItem("opal_brick_stairs",
            () -> new StairBlock(Blocks.STONE::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> CUT_OPAL_BRICK_STAIRS = registerBlockWithItem("cut_opal_brick_stairs",
            () -> new StairBlock(Blocks.STONE::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> OPAL_COBBLESTONE_WALL = registerBlockWithItem("opal_cobblestone_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));

    public static final RegistryObject<Block> OPAL_BRICK_WALL = registerBlockWithItem("opal_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> CUT_OPAL_BRICK_WALL = registerBlockWithItem("cut_opal_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> OPAL_PRESSURE_PLATE = registerBlockWithItem("opal_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS,
                    BlockBehaviour.Properties.copy(Blocks.STONE_PRESSURE_PLATE), BlockSetType.STONE));

    public static final RegistryObject<Block> OPAL_BUTTON = registerBlockWithItem("opal_button",
            () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON), BlockSetType.STONE, 20, false));

    public static final RegistryObject<Block> OPAL_CRYSTAL_ORE = registerBlockWithItem("opal_crystal_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE)));

    public static final RegistryObject<Block> RAW_OPAL_CRYSTAL_BLOCK = registerBlockWithItem("raw_opal_crystal_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_GOLD_BLOCK)));

    public static final RegistryObject<Block> OPAL_CRYSTAL_BLOCK = registerBlockWithItem("opal_crystal_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK)));

    public static final RegistryObject<Block> SOUL_OBSIDIAN = registerBlockWithItem("soul_obsidian", SoulObsidianBlock::new);

    public static final RegistryObject<Block> SOUL_ANCHOR = registerBlockWithItem("soul_anchor", SoulAnchorBlock::new);

    public static final RegistryObject<Block> EXTRACTOR = registerBlockWithItem("extractor", ExtractorBlock::new);

    public static final RegistryObject<Block> BRIGHT_TORCHFLOWER = registerBlockWithItem("bright_torchflower",
            () -> new FlowerBlock(() -> MobEffects.NIGHT_VISION, 5, BlockBehaviour.Properties.copy(Blocks.TORCHFLOWER)
                    .lightLevel((blockState) -> 15)));

    public static final RegistryObject<Block> POTTED_BRIGHT_TORCHFLOWER = BLOCKS.register("potted_bright_torchflower",
            () -> new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.FLOWER_POT, BRIGHT_TORCHFLOWER,
                    BlockBehaviour.Properties.copy(Blocks.POTTED_TORCHFLOWER).lightLevel((blockState) -> 15)));

    public static final RegistryObject<Block> BRIGHT_TORCHFLOWER_CROP = BLOCKS.register("bright_torchflower_crop",
            BrightTorchflowerCropBlock::new);

    public static final RegistryObject<Block> SOUL_TORCHFLOWER = registerBlockWithItem("soul_torchflower",
            () -> new FlowerBlock(() -> MobEffects.FIRE_RESISTANCE, 5, BlockBehaviour.Properties.copy(Blocks.TORCHFLOWER)
                    .lightLevel((blockState) -> 15)));

    public static final RegistryObject<Block> POTTED_SOUL_TORCHFLOWER = BLOCKS.register("potted_soul_torchflower",
            () -> new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.FLOWER_POT, SOUL_TORCHFLOWER,
                    BlockBehaviour.Properties.copy(Blocks.POTTED_TORCHFLOWER).lightLevel((blockState) -> 15)));

    public static final RegistryObject<Block> SOUL_TORCHFLOWER_CROP = BLOCKS.register("soul_torchflower_crop",
            SoulTorchFlowerCropBlock::new);

    public static final RegistryObject<Block> BLUE_GLOWSTONE = registerBlockWithItem("blue_glowstone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.GLOWSTONE)));

    public static final RegistryObject<Block> BLUE_REDSTONE_LAMP = registerBlockWithItem("blue_redstone_lamp",
            () -> new RedstoneLampBlock(BlockBehaviour.Properties.copy(Blocks.REDSTONE_LAMP)));

    public static final RegistryObject<Block> AWAKENED_SCULK_SHRIEKER = registerBlockWithItem("awakened_sculk_shrieker",
            AwakenedSculkShriekerBlock::new);

    public static final RegistryObject<Block> SOUL_JACK_O_LANTERN = registerBlockWithItem("soul_jack_o_lantern",
            () -> new CarvedPumpkinBlock(BlockBehaviour.Properties.copy(Blocks.JACK_O_LANTERN).lightLevel((state) -> 10)));

    public static final RegistryObject<Block> STONE_ITEM_PEDESTAL = registerBlockWithItem("stone_item_pedestal",
            () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> STONE_BRICK_ITEM_PEDESTAL = registerBlockWithItem("stone_brick_item_pedestal",
            () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));

    public static final RegistryObject<Block> MOSSY_STONE_BRICK_ITEM_PEDESTAL = registerBlockWithItem("mossy_stone_brick_item_pedestal",
            () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.MOSSY_STONE_BRICKS)));

    public static final RegistryObject<Block> POLISHED_GRANITE_ITEM_PEDESTAL = registerBlockWithItem("polished_granite_item_pedestal",
            () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_GRANITE)));

    public static final RegistryObject<Block> POLISHED_DIORITE_ITEM_PEDESTAL = registerBlockWithItem("polished_diorite_item_pedestal",
            () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DIORITE)));

    public static final RegistryObject<Block> POLISHED_ANDESITE_ITEM_PEDESTAL = registerBlockWithItem("polished_andesite_item_pedestal",
            () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_ANDESITE)));

    public static final RegistryObject<Block> POLISHED_DEEPSLATE_ITEM_PEDESTAL = registerBlockWithItem("polished_deepslate_item_pedestal",
            () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));

    public static final RegistryObject<Block> DEEPSLATE_BRICK_ITEM_PEDESTAL = registerBlockWithItem("deepslate_brick_item_pedestal",
            () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICKS)));

    public static final RegistryObject<Block> DEEPSLATE_TILE_ITEM_PEDESTAL = registerBlockWithItem("deepslate_tile_item_pedestal",
            () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_TILES)));

    public static final RegistryObject<Block> CUT_SANDSTONE_ITEM_PEDESTAL = registerBlockWithItem("cut_sandstone_item_pedestal",
            () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.CUT_SANDSTONE)));

    public static final RegistryObject<Block> CUT_RED_SANDSTONE_ITEM_PEDESTAL = registerBlockWithItem("cut_red_sandstone_item_pedestal",
            () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.CUT_RED_SANDSTONE)));

    public static final RegistryObject<Block> PRISMARINE_BRICK_ITEM_PEDESTAL = registerBlockWithItem("prismarine_brick_item_pedestal",
            () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.PRISMARINE_BRICKS)));

    public static final RegistryObject<Block> NETHER_BRICK_ITEM_PEDESTAL = registerBlockWithItem("nether_brick_item_pedestal",
            () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS)));

    public static final RegistryObject<Block> RED_NETHER_BRICK_ITEM_PEDESTAL = registerBlockWithItem("red_nether_brick_item_pedestal",
            () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.RED_NETHER_BRICKS)));

    public static final RegistryObject<Block> POLISHED_BLACKSTONE_ITEM_PEDESTAL = registerBlockWithItem("polished_blackstone_item_pedestal",
            () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_BLACKSTONE)));

    public static final RegistryObject<Block> POLISHED_BLACKSTONE_BRICK_ITEM_PEDESTAL = registerBlockWithItem("polished_blackstone_brick_item_pedestal",
            () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_BLACKSTONE_BRICKS)));

    public static final RegistryObject<Block> END_STONE_BRICK_ITEM_PEDESTAL = registerBlockWithItem("end_stone_brick_item_pedestal",
            () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.END_STONE_BRICKS)));

    public static final RegistryObject<Block> PURPUR_ITEM_PEDESTAL = registerBlockWithItem("purpur_item_pedestal",
            () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.PURPUR_BLOCK)));

    public static final RegistryObject<Block> QUARTZ_ITEM_PEDESTAL = registerBlockWithItem("quartz_item_pedestal",
            () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BLOCK)));

    public static final RegistryObject<Block> SOUL_MAGMA_BRICK_ITEM_PEDESTAL = registerBlockWithItem("soul_magma_brick_item_pedestal",
            () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(SOUL_MAGMA_BRICKS.get())));

    public static final RegistryObject<Block> OPAL_ITEM_PEDESTAL = registerBlockWithItem("opal_item_pedestal",
            () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(OPAL.get())));

    public static final RegistryObject<Block> OPAL_BRICK_ITEM_PEDESTAL = registerBlockWithItem("opal_brick_item_pedestal",
            () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(OPAL_BRICKS.get())));

    public static final RegistryObject<Block> CUT_OPAL_BRICK_ITEM_PEDESTAL = registerBlockWithItem("cut_opal_brick_item_pedestal",
            () -> new ItemPedestalBlock(BlockBehaviour.Properties.copy(CUT_OPAL_BRICKS.get())));

    public static final RegistryObject<Block> INFUSED_SOUL_SAND = registerBlockWithItem("infused_soul_sand",
            InfusedSoulSandBlock::new);

    public static final RegistryObject<Block> SOUL_INFUSER = registerBlockWithItem("soul_infuser",
            SoulInfuserBlock::new);

    //Method to register blocks
    private static <T extends Block> RegistryObject<T> registerBlockWithItem(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }
    //Method to register items of blocks
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }
    //Registration event
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
