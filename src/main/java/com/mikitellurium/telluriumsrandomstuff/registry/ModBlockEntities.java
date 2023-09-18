package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.common.content.blockentity.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static  final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TelluriumsRandomStuffMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<SoulFurnaceBlockEntity>> SOUL_FURNACE = BLOCK_ENTITIES.register(
            "soul_furnace", () -> BlockEntityType.Builder.of(SoulFurnaceBlockEntity::new,
                    ModBlocks.SOUL_FURNACE.get()).build(null));

    public static final RegistryObject<BlockEntityType<SoulAnchorBlockEntity>> SOUL_ANCHOR = BLOCK_ENTITIES.register(
            "soul_anchor", () -> BlockEntityType.Builder.of(SoulAnchorBlockEntity::new,
                    ModBlocks.SOUL_ANCHOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<ExtractorBlockEntity>> EXTRACTOR = BLOCK_ENTITIES.register(
            "extractor", () -> BlockEntityType.Builder.of(ExtractorBlockEntity::new,
                    ModBlocks.EXTRACTOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<AwakenedSculkShriekerBlockEntity>> AWAKENED_SCULK_SHRIEKER =
            BLOCK_ENTITIES.register(
            "awakened_sculk_shrieker", () -> BlockEntityType.Builder.of(AwakenedSculkShriekerBlockEntity::new,
                    ModBlocks.AWAKENED_SCULK_SHRIEKER.get()).build(null));

    public static final RegistryObject<BlockEntityType<ItemPedestalBlockEntity>> ITEM_PEDESTAL =
            BLOCK_ENTITIES.register("item_pedestal", () -> BlockEntityType.Builder.of(ItemPedestalBlockEntity::new,
                    ModBlocks.STONE_ITEM_PEDESTAL.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}
