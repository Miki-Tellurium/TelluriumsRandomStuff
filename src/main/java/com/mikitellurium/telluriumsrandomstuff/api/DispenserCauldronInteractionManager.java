package com.mikitellurium.telluriumsrandomstuff.api;

import com.mikitellurium.telluriumsrandomstuff.common.block.interaction.dispenserbehavior.DispenseBucketToCauldronBehavior;
import com.mikitellurium.telluriumsrandomstuff.common.config.ModCommonConfig;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;

import java.util.HashMap;
import java.util.Map;

public class DispenserCauldronInteractionManager {

    private static final Map<Item, Block> DISPENSER_CAULDRON_INTERACTIONS = new HashMap<>();

    /**
     * Register a new dispenser/cauldron interaction.
     * <p>
     * Call this during the mod common setup phase.
     * @param bucket The bucket used to fill the cauldron
     * @param cauldron The cauldron block resulting from the interaction
     * @param bucketEmptySound The sound that the dispenser should make when using the bucket
     * @throws IllegalArgumentException If the item passed is not a {@link BucketItem}/{@link SolidBucketItem}
     * or if the block passed is not a {@link AbstractCauldronBlock}
     */
    public static void registerCauldronBucketInteraction(Item bucket, Block cauldron, SoundEvent bucketEmptySound) {
        if (ModCommonConfig.ENABLE_MOD_DISPENSER_BEHAVIOR.get()) {
            if (!(cauldron instanceof AbstractCauldronBlock)) {
                throw new IllegalArgumentException("Block is not a valid cauldron block");
            } else if (!(bucket instanceof BucketItem || bucket instanceof SolidBucketItem)) {
                throw new IllegalArgumentException("Item is not a valid bucket");
            }
            DISPENSER_CAULDRON_INTERACTIONS.put(bucket, cauldron);
            DispenserBlock.registerBehavior(bucket, new DispenseBucketToCauldronBehavior(bucketEmptySound));
        }
    }

    /**
     * Retrieves the resulting cauldron block when a specified bucket is used.
     *
     * @param bucket The bucket used
     * @return The resulting cauldron block, or {@code null} if not found
     */
    public static Block getResultingCauldron(Item bucket) {
        return DISPENSER_CAULDRON_INTERACTIONS.get(bucket);
    }

    /**
     * Retrieves the remainder bucket item when a specified cauldron block is interacted with.
     * If no matching cauldron is found, the default bucket item is returned.
     *
     * @param cauldron The cauldron block interacted with
     * @return The remainder bucket item, or the default bucket item if no matching cauldron is found
     */
    public static ItemStack getRemainderBucket(Block cauldron) {
        for (Map.Entry<Item, Block> entry : DISPENSER_CAULDRON_INTERACTIONS.entrySet()) {
            if (entry.getValue().equals(cauldron)) {
                return entry.getKey().getDefaultInstance();
            }
        }

        return Items.BUCKET.getDefaultInstance();
    }

}
