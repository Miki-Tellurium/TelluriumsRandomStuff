package com.mikitellurium.telluriumsrandomstuff.api;

import com.mikitellurium.telluriumsrandomstuff.common.block.interaction.dispenserbehavior.*;
import com.mikitellurium.telluriumsrandomstuff.config.ModCommonConfig;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import net.minecraft.Util;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ModDispenserBehaviours {

    public static final DefaultDispenseItemBehavior DEFAULT_DISPENSE = new DefaultDispenseItemBehavior();
    private static final Map<AbstractCauldronBlock, Function<ItemStack, ItemStack>> BUCKET_FILL_FUNCTIONS = Util.make(new HashMap<>(), (map) -> {
        map.put((AbstractCauldronBlock) Blocks.CAULDRON, (itemStack) -> Items.BUCKET.getDefaultInstance());
        map.put((AbstractCauldronBlock) Blocks.WATER_CAULDRON, (itemStack) -> Items.WATER_BUCKET.getDefaultInstance());
        map.put((AbstractCauldronBlock) Blocks.LAVA_CAULDRON, (itemStack) -> Items.LAVA_BUCKET.getDefaultInstance());
        map.put((AbstractCauldronBlock) Blocks.POWDER_SNOW_CAULDRON, (itemStack) -> Items.POWDER_SNOW_BUCKET.getDefaultInstance());
        map.put((AbstractCauldronBlock) ModBlocks.SOUL_LAVA_CAULDRON.get(), (itemStack) -> ModItems.SOUL_LAVA_BUCKET.get().getDefaultInstance());
    });
    private static final Map<AbstractCauldronBlock, Function<BlockState, SoundEvent>> DISPENSE_SOUND_FUNCTIONS = Util.make(new HashMap<>(), (map) -> {
        map.put((AbstractCauldronBlock) Blocks.CAULDRON, (blockState) -> SoundEvents.DISPENSER_FAIL);
        map.put((AbstractCauldronBlock) Blocks.WATER_CAULDRON, (blockState) -> SoundEvents.BUCKET_FILL);
        map.put((AbstractCauldronBlock) Blocks.LAVA_CAULDRON, (blockState) -> SoundEvents.BUCKET_FILL_LAVA);
        map.put((AbstractCauldronBlock) Blocks.POWDER_SNOW_CAULDRON, (blockState) -> SoundEvents.BUCKET_FILL_POWDER_SNOW);
        map.put((AbstractCauldronBlock) ModBlocks.SOUL_LAVA_CAULDRON.get(), (blockState) -> SoundEvents.BUCKET_FILL_LAVA);
    });

    public static void addCauldronFunctions(AbstractCauldronBlock cauldron, Function<ItemStack, ItemStack> fillFunction,
                                            Function<BlockState, SoundEvent> soundFunction) {
        BUCKET_FILL_FUNCTIONS.put(cauldron, fillFunction);
        DISPENSE_SOUND_FUNCTIONS.put(cauldron, soundFunction);
    }

    public static ItemStack getFilledBucket(AbstractCauldronBlock cauldron, ItemStack originalStack) {
        return BUCKET_FILL_FUNCTIONS.get(cauldron).apply(originalStack);
    }

    public static SoundEvent getDispenseSound(BlockState blockState) {
        return DISPENSE_SOUND_FUNCTIONS.get((AbstractCauldronBlock) blockState.getBlock()).apply(blockState);
    }

    public static void register() {
        DispenserBlock.registerBehavior(ModItems.SOUL_LAVA_BUCKET.get(), new DispenseSoulLava());
        DispenserBlock.registerBehavior(ModItems.LAVA_GOOGLES.get(), ArmorItem.DISPENSE_ITEM_BEHAVIOR);
        DispenserBlock.registerBehavior(ModItems.SOUL_CLUSTER.get(), new DispenseSoulCluster());
        if (ModCommonConfig.ENABLE_MOD_DISPENSER_BEHAVIOR.get()) {
            DispenserBlock.registerBehavior(Items.BUCKET, new DispenseEmptyBucketToCauldron());
            DispenserBlock.registerBehavior(Items.WATER_BUCKET, new DispenseWaterBucketToCauldron());
            DispenserBlock.registerBehavior(Items.LAVA_BUCKET, new DispenseLavaBucketToCauldron());
            DispenserBlock.registerBehavior(Items.POWDER_SNOW_BUCKET, new DispensePowderSnowBucketToCauldron());
            DispenserBlock.registerBehavior(ModItems.SOUL_LAVA_BUCKET.get(), new DispenseSoulLavaBucketToCauldron());
        }
    }

}
