package com.mikitellurium.telluriumsrandomstuff.common.block.interaction;

import com.mikitellurium.telluriumsrandomstuff.api.DispenserCauldronInteractionManager;
import com.mikitellurium.telluriumsrandomstuff.common.blockentity.AwakenedSculkShriekerBlockEntity;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlockEntities;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class ModDispenserBehaviours {

    public static final DefaultDispenseItemBehavior DEFAULT_DISPENSE = new DefaultDispenseItemBehavior();

    private static final DefaultDispenseItemBehavior SOUL_LAVA_DISPENSER_BEHAVIOR = new DefaultDispenseItemBehavior() {

        @Override
        public ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
            DispensibleContainerItem dispensiblecontaineritem = (DispensibleContainerItem) itemStack.getItem();
            BlockPos blockpos = blockSource.getPos().relative(blockSource.getBlockState().getValue(DispenserBlock.FACING));
            Level level = blockSource.getLevel();
            if (dispensiblecontaineritem.emptyContents(null, level, blockpos, null, itemStack)) {
                dispensiblecontaineritem.checkExtraContent(null, level, itemStack, blockpos);
                return new ItemStack(Items.BUCKET);
            } else {
                return DEFAULT_DISPENSE.dispense(blockSource, itemStack);
            }
        }
    };

    private static final DefaultDispenseItemBehavior SOUL_CLUSTER_DISPENSE_BEHAVIOR = new DefaultDispenseItemBehavior() {

        @Override
        public ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
            BlockPos blockpos = blockSource.getPos().relative(blockSource.getBlockState().getValue(DispenserBlock.FACING));
            ServerLevel level = blockSource.getLevel();
            BlockState blockState = level.getBlockState(blockpos);
            if (blockState.getValue(SculkShriekerBlock.CAN_SUMMON) && !blockState.getValue(SculkShriekerBlock.SHRIEKING)) {
                Optional<AwakenedSculkShriekerBlockEntity> shriekerBlockEntity = level.getBlockEntity(blockpos, ModBlockEntities.AWAKENED_SCULK_SHRIEKER.get());
                if (shriekerBlockEntity.isPresent()) {
                    shriekerBlockEntity.get().tryShriek(level, null);
                    itemStack.shrink(1);
                    return itemStack;
                } else {
                    return DEFAULT_DISPENSE.dispense(blockSource, itemStack);
                }
            } else {
                return DEFAULT_DISPENSE.dispense(blockSource, itemStack);
            }
        }
    };

    public static void register() {
        DispenserBlock.registerBehavior(ModItems.SOUL_LAVA_BUCKET.get(), SOUL_LAVA_DISPENSER_BEHAVIOR);
        DispenserBlock.registerBehavior(ModItems.LAVA_GOOGLES.get(), ArmorItem.DISPENSE_ITEM_BEHAVIOR);
        DispenserBlock.registerBehavior(ModItems.SOUL_CLUSTER.get(), SOUL_CLUSTER_DISPENSE_BEHAVIOR);
        DispenserCauldronInteractionManager.registerCauldronBucketInteraction(Items.BUCKET, Blocks.CAULDRON, SoundEvents.DISPENSER_DISPENSE);
        DispenserCauldronInteractionManager.registerCauldronBucketInteraction(Items.WATER_BUCKET, Blocks.WATER_CAULDRON, SoundEvents.BUCKET_EMPTY);
        DispenserCauldronInteractionManager.registerCauldronBucketInteraction(Items.LAVA_BUCKET, Blocks.LAVA_CAULDRON, SoundEvents.BUCKET_EMPTY_LAVA);
        DispenserCauldronInteractionManager.registerCauldronBucketInteraction(Items.POWDER_SNOW_BUCKET, Blocks.POWDER_SNOW_CAULDRON, SoundEvents.BUCKET_EMPTY_POWDER_SNOW);
        DispenserCauldronInteractionManager.registerCauldronBucketInteraction(ModItems.SOUL_LAVA_BUCKET.get(), ModBlocks.SOUL_LAVA_CAULDRON.get(), SoundEvents.BUCKET_EMPTY_LAVA);
    }

}
