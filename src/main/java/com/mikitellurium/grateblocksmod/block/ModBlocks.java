package com.mikitellurium.grateblocksmod.block;

import com.mikitellurium.grateblocksmod.GrateBlocksMod;
import com.mikitellurium.grateblocksmod.block.custom.CustomBubbleColumnBlock;
import com.mikitellurium.grateblocksmod.block.custom.GrateMagmaBlock;
import com.mikitellurium.grateblocksmod.block.custom.GrateSoulSand;
import com.mikitellurium.grateblocksmod.item.GrateBlocksCreativeTab;
import com.mikitellurium.grateblocksmod.item.ModItems;
import com.mikitellurium.grateblocksmod.sounds.ModSoundTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class ModBlocks {
    //Creating blocks registry
    public static  final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, GrateBlocksMod.MOD_ID);

    public static final RegistryObject<Block> GRATE_SOUL_SAND = registerBlock("grate_soul_sand", () ->
            new GrateSoulSand(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.COLOR_BROWN).strength(0.5F)
                    .sound(ModSoundTypes.GRATE_SOUL_SAND).isValidSpawn(ModBlocks::always).isRedstoneConductor(ModBlocks::always)
                    .isViewBlocking(ModBlocks::always).isSuffocating(ModBlocks::always)), GrateBlocksCreativeTab.TAB_GRATEBLOCKS);

    public static final RegistryObject<Block> GRATE_MAGMA_BLOCK = registerBlock("grate_magma_block", () ->
            new GrateMagmaBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.NETHER).requiresCorrectToolForDrops()
                    .lightLevel((lightLevel) -> 3).randomTicks().strength(0.5F).isValidSpawn((entity1, entity2, entity3, entity4) -> entity4
                            .fireImmune()).hasPostProcess(ModBlocks::always).emissiveRendering(ModBlocks::always)), GrateBlocksCreativeTab.TAB_GRATEBLOCKS);

    public static final RegistryObject<Block> CUSTOM_BUBBLE_COLUMN = registerBlock("custom_bubble_column", () ->
            new CustomBubbleColumnBlock(BlockBehaviour.Properties.of(Material.BUBBLE_COLUMN).noCollission().noDrops()), null);

    private static boolean always(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) { return true; }

    private static Boolean always(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, EntityType<?> entityType) {
        return true;
    }

    //Method to register blocks
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, @Nullable CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }
    //Method to register items of blocks
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                            CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(tab)));
    }
    //Registration event
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
