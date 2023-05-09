package com.mikitellurium.telluriumsrandomstuff.blockentity;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.block.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.blockentity.custom.SoulAnchorBlockEntity;
import com.mikitellurium.telluriumsrandomstuff.blockentity.custom.SoulFurnaceBlockEntity;
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
                    ModBlocks.SOUL_FURNACE_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<SoulAnchorBlockEntity>> SOUL_ANCHOR = BLOCK_ENTITIES.register(
            "soul_anchor", () -> BlockEntityType.Builder.of(SoulAnchorBlockEntity::new,
                    ModBlocks.SOUL_ANCHOR_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}
