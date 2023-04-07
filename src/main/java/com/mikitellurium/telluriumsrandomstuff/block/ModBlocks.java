package com.mikitellurium.telluriumsrandomstuff.block;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.block.custom.*;
import com.mikitellurium.telluriumsrandomstuff.fluid.ModFluids;
import com.mikitellurium.telluriumsrandomstuff.fluid.custom.SoulLavaBlock;
import com.mikitellurium.telluriumsrandomstuff.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
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

    public static final RegistryObject<Block> OPAL = registerBlock("opal", OpalBlock::new);

    public static final RegistryObject<Block> OPAL_COBBLESTONE = registerBlock("opal_cobblestone", OpalCobblestoneBlock::new);

    public static final RegistryObject<Block> OPAL_BRICKS = registerBlock("opal_bricks", OpalBlock::new);

    public static final RegistryObject<Block> CUT_OPAL_BRICKS = registerBlock("cut_opal_bricks", OpalBlock::new);

    public static final RegistryObject<Block> CHISELED_OPAL_BRICKS = registerBlock("chiseled_opal_bricks", OpalBlock::new);

    public static final RegistryObject<Block> CRACKED_OPAL_BRICKS = registerBlock("cracked_opal_bricks", OpalBlock::new);

    public static final RegistryObject<Block> CRACKED_CUT_OPAL_BRICKS = registerBlock("cracked_cut_opal_bricks", OpalBlock::new);

    public static final RegistryObject<Block> OPAL_SLAB = registerBlock("opal_slab", OpalSlabBlock::new);

    public static final RegistryObject<Block> OPAL_COBBLESTONE_SLAB = registerBlock("opal_cobblestone_slab", OpalSlabBlock::new);

    public static final RegistryObject<Block> OPAL_BRICK_SLAB = registerBlock("opal_brick_slab", OpalSlabBlock::new);

    public static final RegistryObject<Block> CUT_OPAL_BRICK_SLAB = registerBlock("cut_opal_brick_slab", OpalSlabBlock::new);

    public static final RegistryObject<Block> CRACKED_OPAL_BRICK_SLAB = registerBlock("cracked_opal_brick_slab", OpalSlabBlock::new);

    public static final RegistryObject<Block> CRACKED_CUT_OPAL_BRICK_SLAB = registerBlock("cracked_cut_opal_brick_slab", OpalSlabBlock::new);

    public static final RegistryObject<Block> OPAL_STAIRS = registerBlock("opal_stairs", OpalStairBlock::new);

    public static final RegistryObject<Block> OPAL_COBBLESTONE_STAIRS = registerBlock("opal_cobblestone_stairs", OpalCobblestoneStairBlock::new);

    public static final RegistryObject<Block> OPAL_BRICK_STAIRS = registerBlock("opal_brick_stairs", OpalStairBlock::new);

    public static final RegistryObject<Block> CUT_OPAL_BRICK_STAIRS = registerBlock("cut_opal_brick_stairs", OpalStairBlock::new);

    public static final RegistryObject<Block> OPAL_COBBLESTONE_WALL = registerBlock("opal_cobblestone_wall", OpalCobblestoneWallBlock::new);

    public static final RegistryObject<Block> OPAL_BRICK_WALL = registerBlock("opal_brick_wall", OpalWallBlock::new);

    public static final RegistryObject<Block> CUT_OPAL_BRICK_WALL = registerBlock("cut_opal_brick_wall", OpalWallBlock::new);

    public static final RegistryObject<Block> OPAL_PRESSURE_PLATE = registerBlock("opal_pressure_plate", OpalPressurePlateBlock::new);

    public static final RegistryObject<Block> OPAL_BUTTON = registerBlock("opal_button", OpalButtonBlock::new);

    public static final RegistryObject<LiquidBlock> SOUL_LAVA_BLOCK = BLOCKS.register("soul_lava_block",
            () -> new SoulLavaBlock(ModFluids.SOUL_LAVA_SOURCE));

    public static Boolean always(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) { return true; }

    public static Boolean always(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, EntityType<?> entityType) {
        return true;
    }

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
