package com.mikitellurium.telluriumsrandomstuff.block.interaction;

import com.mikitellurium.telluriumsrandomstuff.block.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.item.ModItems;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.Map;

import static net.minecraft.core.cauldron.CauldronInteraction.emptyBucket;
import static net.minecraft.core.cauldron.CauldronInteraction.fillBucket;

public class ModCauldronInteraction {

    public static final Map<Item, CauldronInteraction> SOUL_LAVA_INTERACTION = CauldronInteraction.newInteractionMap();

    public static final CauldronInteraction FILL_SOUL_LAVA = (blockState, level, blockPos, player, interactionHand, itemStack) ->
            emptyBucket(level, blockPos, player, interactionHand, itemStack, ModBlocks.SOUL_LAVA_CAULDRON_BLOCK.get().defaultBlockState(),
                    SoundEvents.BUCKET_EMPTY_LAVA);

    public static final CauldronInteraction EMPTY_SOUL_LAVA = (blockState, level, blockPos, player, interactionHand, itemStack) ->
            fillBucket(blockState, level, blockPos, player, interactionHand, itemStack, new ItemStack(Items.LAVA_BUCKET),
                    (state) -> true, SoundEvents.BUCKET_FILL_LAVA);

    public static void bootstrap() {
        SOUL_LAVA_INTERACTION.put(Items.BUCKET, EMPTY_SOUL_LAVA);
        SOUL_LAVA_INTERACTION.put(ModItems.SOUL_LAVA_BUCKET.get(), FILL_SOUL_LAVA);
    }

}
