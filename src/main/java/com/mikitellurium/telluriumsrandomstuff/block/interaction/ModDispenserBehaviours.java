package com.mikitellurium.telluriumsrandomstuff.block.interaction;

import com.mikitellurium.telluriumsrandomstuff.block.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;

import java.util.HashMap;
import java.util.Map;

public class ModDispenserBehaviours {

    protected static final Map<Item, Block> DISPENSER_CAULDRON_INTERACTIONS = new HashMap<>();

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
        registerCauldronInteraction(Items.BUCKET, Blocks.CAULDRON);
        registerCauldronInteraction(Items.WATER_BUCKET, Blocks.WATER_CAULDRON);
        registerCauldronInteraction(Items.LAVA_BUCKET, Blocks.LAVA_CAULDRON);
        registerCauldronInteraction(Items.POWDER_SNOW_BUCKET, Blocks.POWDER_SNOW_CAULDRON);
        registerCauldronInteraction(ModItems.SOUL_LAVA_BUCKET.get(), ModBlocks.SOUL_LAVA_CAULDRON_BLOCK.get());
    }

    /**
     * Register a new dispenser/cauldron interaction.
     * <p>
     * Call this during the mod common setup phase.
     * @param bucket The bucket used to fill the cauldron
     * @param cauldron The cauldron block to add the interaction to
     * @throws IllegalArgumentException If the item passed is not a {@link BucketItem}/{@link SolidBucketItem}
     * or if the block passed is not a {@link AbstractCauldronBlock}
     */
    public static void registerCauldronInteraction(Item bucket, Block cauldron) {
        if (!(cauldron instanceof AbstractCauldronBlock)) {
            throw new IllegalArgumentException("Block is not a valid cauldron block");
        } else if (!(bucket instanceof BucketItem || bucket instanceof SolidBucketItem)) {
            throw new IllegalArgumentException("Item is not a valid bucket");
        }
        DISPENSER_CAULDRON_INTERACTIONS.put(bucket, cauldron);
        DispenserBlock.registerBehavior(bucket, new DispenserCauldronBehavior());
    }

}
