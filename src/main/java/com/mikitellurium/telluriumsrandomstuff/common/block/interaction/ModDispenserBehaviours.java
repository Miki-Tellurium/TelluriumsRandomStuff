package com.mikitellurium.telluriumsrandomstuff.common.block.interaction;

import com.mikitellurium.telluriumsrandomstuff.api.DispenserCauldronInteractionManager;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;

public class ModDispenserBehaviours {

    private static final DefaultDispenseItemBehavior SOUL_LAVA_DISPENSER_BEHAVIOR = new DefaultDispenseItemBehavior() {
        private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

        @Override
        public ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
            DispensibleContainerItem dispensiblecontaineritem = (DispensibleContainerItem) itemStack.getItem();
            BlockPos blockpos = blockSource.getPos().relative(blockSource.getBlockState().getValue(DispenserBlock.FACING));
            Level level = blockSource.getLevel();
            if (dispensiblecontaineritem.emptyContents(null, level, blockpos, null, itemStack)) {
                dispensiblecontaineritem.checkExtraContent(null, level, itemStack, blockpos);
                return new ItemStack(Items.BUCKET);
            } else {
                return this.defaultDispenseItemBehavior.dispense(blockSource, itemStack);
            }
        }
    };

    public static void register() {
        DispenserBlock.registerBehavior(ModItems.SOUL_LAVA_BUCKET.get(), SOUL_LAVA_DISPENSER_BEHAVIOR);
        DispenserBlock.registerBehavior(ModItems.LAVA_GOOGLES.get(), ArmorItem.DISPENSE_ITEM_BEHAVIOR);
        DispenserCauldronInteractionManager.registerCauldronBucketInteraction(Items.BUCKET, Blocks.CAULDRON, SoundEvents.DISPENSER_DISPENSE);
        DispenserCauldronInteractionManager.registerCauldronBucketInteraction(Items.WATER_BUCKET, Blocks.WATER_CAULDRON, SoundEvents.BUCKET_EMPTY);
        DispenserCauldronInteractionManager.registerCauldronBucketInteraction(Items.LAVA_BUCKET, Blocks.LAVA_CAULDRON, SoundEvents.BUCKET_EMPTY_LAVA);
        DispenserCauldronInteractionManager.registerCauldronBucketInteraction(Items.POWDER_SNOW_BUCKET, Blocks.POWDER_SNOW_CAULDRON, SoundEvents.BUCKET_EMPTY_POWDER_SNOW);
        DispenserCauldronInteractionManager.registerCauldronBucketInteraction(ModItems.SOUL_LAVA_BUCKET.get(), ModBlocks.SOUL_LAVA_CAULDRON.get(), SoundEvents.BUCKET_EMPTY_LAVA);
    }

}
