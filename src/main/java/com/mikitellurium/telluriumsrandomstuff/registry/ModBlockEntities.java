package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.common.blockentity.*;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlockEntities {

    public static final RegistryObject<BlockEntityType<SoulFurnaceBlockEntity>> SOUL_FURNACE = registerBlockEntity(
            "soul_furnace", () -> BlockEntityType.Builder.of(SoulFurnaceBlockEntity::new,
                    ModBlocks.SOUL_FURNACE.get()).build(null));

    public static final RegistryObject<BlockEntityType<SoulAnchorBlockEntity>> SOUL_ANCHOR = registerBlockEntity(
            "soul_anchor", () -> BlockEntityType.Builder.of(SoulAnchorBlockEntity::new,
                    ModBlocks.SOUL_ANCHOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<ExtractorBlockEntity>> EXTRACTOR = registerBlockEntity(
            "extractor", () -> BlockEntityType.Builder.of(ExtractorBlockEntity::new,
                    ModBlocks.EXTRACTOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<AwakenedSculkShriekerBlockEntity>> AWAKENED_SCULK_SHRIEKER =
            registerBlockEntity(
            "awakened_sculk_shrieker", () -> BlockEntityType.Builder.of(AwakenedSculkShriekerBlockEntity::new,
                    ModBlocks.AWAKENED_SCULK_SHRIEKER.get()).build(null));

    public static final RegistryObject<BlockEntityType<ItemPedestalBlockEntity>> ITEM_PEDESTAL =
            registerBlockEntity("item_pedestal", () -> BlockEntityType.Builder.of(ItemPedestalBlockEntity::new,
                            ModBlocks.STONE_ITEM_PEDESTAL.get(), ModBlocks.STONE_BRICK_ITEM_PEDESTAL.get(),
                            ModBlocks.MOSSY_STONE_BRICK_ITEM_PEDESTAL.get(), ModBlocks.POLISHED_GRANITE_ITEM_PEDESTAL.get(),
                            ModBlocks.POLISHED_DIORITE_ITEM_PEDESTAL.get(), ModBlocks.POLISHED_ANDESITE_ITEM_PEDESTAL.get(),
                            ModBlocks.POLISHED_DEEPSLATE_ITEM_PEDESTAL.get(), ModBlocks.DEEPSLATE_BRICK_ITEM_PEDESTAL.get(),
                            ModBlocks.DEEPSLATE_TILE_ITEM_PEDESTAL.get(), ModBlocks.CUT_SANDSTONE_ITEM_PEDESTAL.get(),
                            ModBlocks.CUT_RED_SANDSTONE_ITEM_PEDESTAL.get(), ModBlocks.PRISMARINE_BRICK_ITEM_PEDESTAL.get(),
                            ModBlocks.NETHER_BRICK_ITEM_PEDESTAL.get(), ModBlocks.RED_NETHER_BRICK_ITEM_PEDESTAL.get(),
                            ModBlocks.POLISHED_BLACKSTONE_ITEM_PEDESTAL.get(), ModBlocks.POLISHED_BLACKSTONE_BRICK_ITEM_PEDESTAL.get(),
                            ModBlocks.END_STONE_BRICK_ITEM_PEDESTAL.get(), ModBlocks.PURPUR_ITEM_PEDESTAL.get(),
                            ModBlocks.QUARTZ_ITEM_PEDESTAL.get(), ModBlocks.OPAL_ITEM_PEDESTAL.get(),
                            ModBlocks.OPAL_BRICK_ITEM_PEDESTAL.get(), ModBlocks.CUT_OPAL_BRICK_ITEM_PEDESTAL.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<SoulInfuserBlockEntity>> SOUL_INFUSER = registerBlockEntity(
            "soul_infuser", () -> BlockEntityType.Builder.of(SoulInfuserBlockEntity::new,
                    ModBlocks.SOUL_INFUSER.get()).build(null));

    public static final RegistryObject<BlockEntityType<AlchemixerBlockEntity>> ALCHEMIXER = registerBlockEntity(
            "alchemixer", () -> BlockEntityType.Builder.of(AlchemixerBlockEntity::new,
                    ModBlocks.ALCHEMIXER.get()).build(null));

    public static final RegistryObject<BlockEntityType<SoulCompactorBlockEntity>> SOUL_COMPACTOR = registerBlockEntity(
            "soul_compactor", () -> BlockEntityType.Builder.of(SoulCompactorBlockEntity::new,
                    ModBlocks.SOUL_COMPACTOR.get()).build(null));

    private static <B extends BlockEntity> RegistryObject<BlockEntityType<B>> registerBlockEntity(
            String name, Supplier<BlockEntityType<B>> blockEntity) {
        return ModRegistries.BLOCK_ENTITIES.register(name, blockEntity);
    }

    protected static void register(IEventBus eventBus) {
        ModRegistries.BLOCK_ENTITIES.register(eventBus);
    }

}
