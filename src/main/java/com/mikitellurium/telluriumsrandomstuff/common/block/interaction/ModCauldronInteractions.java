package com.mikitellurium.telluriumsrandomstuff.common.block.interaction;

import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Map;

import static net.minecraft.core.cauldron.CauldronInteraction.emptyBucket;
import static net.minecraft.core.cauldron.CauldronInteraction.fillBucket;

public class ModCauldronInteractions {

    public static Map<Item, CauldronInteraction> SOUL_LAVA = CauldronInteraction.newInteractionMap();
    private static final CauldronInteraction FILL_SOUL_LAVA = (blockState, level, blockPos, player, interactionHand, itemStack) ->
            emptyBucket(level, blockPos, player, interactionHand, itemStack, ModBlocks.SOUL_LAVA_CAULDRON.get().defaultBlockState(),
            SoundEvents.BUCKET_EMPTY_LAVA);

    public static void register() {
        // Add the soul lava interaction to the vanilla cauldrons
        CauldronInteraction.EMPTY.put(ModItems.SOUL_LAVA_BUCKET.get(), FILL_SOUL_LAVA);
        CauldronInteraction.WATER.put(ModItems.SOUL_LAVA_BUCKET.get(), FILL_SOUL_LAVA);
        CauldronInteraction.LAVA.put(ModItems.SOUL_LAVA_BUCKET.get(), FILL_SOUL_LAVA);
        CauldronInteraction.POWDER_SNOW.put(ModItems.SOUL_LAVA_BUCKET.get(), FILL_SOUL_LAVA);
        // Add soul lava cauldron interactions
        SOUL_LAVA.put(Items.BUCKET, (blockState, level, blockPos, player, interactionHand, itemStack) ->
                fillBucket(blockState, level, blockPos, player, interactionHand, itemStack, new ItemStack(ModItems.SOUL_LAVA_BUCKET.get()),
                (state) -> true, SoundEvents.BUCKET_FILL_LAVA));
        SOUL_LAVA.put(ModItems.SOUL_LAVA_BUCKET.get(), FILL_SOUL_LAVA);
        CauldronInteraction.addDefaultInteractions(SOUL_LAVA);
    }

}
