package com.mikitellurium.telluriumsrandomstuff.block;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.block.custom.*;
import com.mikitellurium.telluriumsrandomstuff.config.ModCommonConfig;
import com.mikitellurium.telluriumsrandomstuff.fluid.ModFluids;
import com.mikitellurium.telluriumsrandomstuff.fluid.custom.SoulLavaBlock;
import com.mikitellurium.telluriumsrandomstuff.item.ModItems;
import com.mikitellurium.telluriumsrandomstuff.util.LevelUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static  final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, TelluriumsRandomStuffMod.MOD_ID);

    public static final RegistryObject<Block> GRATE_SOUL_SAND = registerBlock("grate_soul_sand", GrateSoulSandBlock::new);

    public static final RegistryObject<Block> GRATE_MAGMA_BLOCK = registerBlock("grate_magma_block", GrateMagmaBlock::new);

    public static final RegistryObject<Block> CUSTOM_BUBBLE_COLUMN = registerBlock("custom_bubble_column", CustomBubbleColumnBlock::new);

    public static final RegistryObject<Block> HYDRODYNAMIC_RAIL = registerBlock("hydrodynamic_rail", HydrodynamicRailBlock::new);

    public static final RegistryObject<LiquidBlock> SOUL_LAVA_BLOCK = BLOCKS.register("soul_lava_block",
            () -> new SoulLavaBlock(ModFluids.SOUL_LAVA_SOURCE));

    public static final RegistryObject<Block> SOUL_MAGMA_BLOCK = registerBlock("soul_magma_block", SoulMagmaBlock::new);

    public static final RegistryObject<Block> GRATE_SOUL_MAGMA_BLOCK = registerBlock("grate_soul_magma_block", GrateSoulMagmaBlock::new);

    public static final RegistryObject<Block> SOUL_FURNACE_BLOCK = registerBlock("soul_furnace", SoulFurnaceBlock::new);

    public static final RegistryObject<Block> SOUL_MAGMA_BRICKS = registerBlock("soul_magma_bricks",
            ()-> new Block(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS)
                    .isValidSpawn((blockState, blockGetter, blockPos, entityType) -> LevelUtils.isSoulBlockValidSpawn(entityType))
                    .lightLevel((blockState) -> 2)
                    .emissiveRendering((blockState, blockGetter, blockPos) -> true)));

    public static final RegistryObject<Block> SOUL_MAGMA_BRICK_SLAB = registerBlock("soul_magma_brick_slab",
            ()-> new SlabBlock(BlockBehaviour.Properties.copy(ModBlocks.SOUL_MAGMA_BRICKS.get())
                    .isValidSpawn((blockState, blockGetter, blockPos, entityType) -> false)
                    .lightLevel((blockState) -> 2)
                    .emissiveRendering((blockState, blockGetter, blockPos) -> true)));

    public static final RegistryObject<Block> SOUL_LAVA_CAULDRON_BLOCK = BLOCKS.register("soul_lava_cauldron", SoulLavaCauldronBlock::new);

    public static final RegistryObject<Block> OPAL = registerBlock("opal",
            ()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> OPAL_COBBLESTONE = registerBlock("opal_cobblestone",
            ()-> new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));

    public static final RegistryObject<Block> OPAL_BRICKS = registerBlock("opal_bricks",
            ()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> CUT_OPAL_BRICKS = registerBlock("cut_opal_bricks",
            ()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> CHISELED_OPAL_BRICKS = registerBlock("chiseled_opal_bricks",
            ()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> CRACKED_OPAL_BRICKS = registerBlock("cracked_opal_bricks",
            ()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> CRACKED_CUT_OPAL_BRICKS = registerBlock("cracked_cut_opal_bricks",
            ()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> OPAL_SLAB = registerBlock("opal_slab",
            ()-> new SlabBlock(BlockBehaviour.Properties.copy(ModBlocks.OPAL_COBBLESTONE.get())));

    public static final RegistryObject<Block> OPAL_COBBLESTONE_SLAB = registerBlock("opal_cobblestone_slab",
            ()-> new SlabBlock(BlockBehaviour.Properties.copy(ModBlocks.OPAL_COBBLESTONE.get())));

    public static final RegistryObject<Block> OPAL_BRICK_SLAB = registerBlock("opal_brick_slab",
            ()-> new SlabBlock(BlockBehaviour.Properties.copy(ModBlocks.OPAL_COBBLESTONE.get())));

    public static final RegistryObject<Block> CUT_OPAL_BRICK_SLAB = registerBlock("cut_opal_brick_slab",
            ()-> new SlabBlock(BlockBehaviour.Properties.copy(ModBlocks.OPAL_COBBLESTONE.get())));

    public static final RegistryObject<Block> CRACKED_OPAL_BRICK_SLAB = registerBlock("cracked_opal_brick_slab",
            ()-> new SlabBlock(BlockBehaviour.Properties.copy(ModBlocks.OPAL_COBBLESTONE.get())));

    public static final RegistryObject<Block> CRACKED_CUT_OPAL_BRICK_SLAB = registerBlock("cracked_cut_opal_brick_slab",
            ()-> new SlabBlock(BlockBehaviour.Properties.copy(ModBlocks.OPAL_COBBLESTONE.get())));

    public static final RegistryObject<Block> OPAL_STAIRS = registerBlock("opal_stairs",
            ()-> new StairBlock(Blocks.STONE::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> OPAL_COBBLESTONE_STAIRS = registerBlock("opal_cobblestone_stairs",
            () -> new StairBlock(Blocks.COBBLESTONE::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));

    public static final RegistryObject<Block> OPAL_BRICK_STAIRS = registerBlock("opal_brick_stairs",
            ()-> new StairBlock(Blocks.STONE::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> CUT_OPAL_BRICK_STAIRS = registerBlock("cut_opal_brick_stairs",
            ()-> new StairBlock(Blocks.STONE::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> OPAL_COBBLESTONE_WALL = registerBlock("opal_cobblestone_wall",
            ()-> new WallBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));

    public static final RegistryObject<Block> OPAL_BRICK_WALL = registerBlock("opal_brick_wall",
            ()-> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> CUT_OPAL_BRICK_WALL = registerBlock("cut_opal_brick_wall",
            ()-> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> OPAL_PRESSURE_PLATE = registerBlock("opal_pressure_plate",
            ()-> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS,
                    BlockBehaviour.Properties.copy(Blocks.STONE_PRESSURE_PLATE), BlockSetType.STONE));

    public static final RegistryObject<Block> OPAL_BUTTON = registerBlock("opal_button",
            ()-> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON), BlockSetType.STONE, 20, false));

    public static final RegistryObject<Block> OPAL_CRYSTAL_ORE = registerBlock("opal_crystal_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE)));

    public static final RegistryObject<Block> RAW_OPAL_CRYSTAL_BLOCK = registerBlock("raw_opal_crystal_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_GOLD_BLOCK)));

    public static final RegistryObject<Block> OPAL_CRYSTAL_BLOCK = registerBlock("opal_crystal_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK)));

    public static final RegistryObject<Block> SOUL_OBSIDIAN_BLOCK = registerBlock("soul_obsidian", SoulObsidianBlock::new);

    public static final RegistryObject<Block> SOUL_ANCHOR_BLOCK = registerBlock("soul_anchor", SoulAnchorBlock::new);

    public static final RegistryObject<Block> EXTRACTOR_BLOCK = registerBlock("extractor", ExtractorBlock::new);

    public static final RegistryObject<Block> BRIGHT_TORCHFLOWER = registerBlock("bright_torchflower",
            () -> new FlowerBlock(() -> MobEffects.NIGHT_VISION, 5, BlockBehaviour.Properties.copy(Blocks.TORCHFLOWER)
                    .lightLevel((blockState) -> 15)));

    public static RegistryObject<Block> POTTED_BRIGHT_TORCHFLOWER = BLOCKS.register("potted_bright_torchflower",
            () -> new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.FLOWER_POT, BRIGHT_TORCHFLOWER,
                    BlockBehaviour.Properties.copy(Blocks.POTTED_TORCHFLOWER).lightLevel((blockState) -> 15)));

    public static final RegistryObject<Block> SOUL_TORCHFLOWER = registerBlock("soul_torchflower",
            () -> new FlowerBlock(() -> MobEffects.FIRE_RESISTANCE, 5, BlockBehaviour.Properties.copy(Blocks.TORCHFLOWER)
                    .lightLevel((blockState) -> 15)));

    public static RegistryObject<Block> POTTED_SOUL_TORCHFLOWER = BLOCKS.register("potted_soul_torchflower",
            () -> new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.FLOWER_POT, SOUL_TORCHFLOWER,
                    BlockBehaviour.Properties.copy(Blocks.POTTED_TORCHFLOWER).lightLevel((blockState) -> 15)));

    //Method to register blocks
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
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
