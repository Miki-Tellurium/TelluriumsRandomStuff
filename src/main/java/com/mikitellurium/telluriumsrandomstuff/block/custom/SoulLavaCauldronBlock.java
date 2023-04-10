package com.mikitellurium.telluriumsrandomstuff.block.custom;

import com.mikitellurium.telluriumsrandomstuff.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class SoulLavaCauldronBlock extends AbstractCauldronBlock {

    public SoulLavaCauldronBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.LAVA_CAULDRON), CauldronInteraction.EMPTY);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            ItemStack itemStack = pPlayer.getItemInHand(pHand);
            if (itemStack.is(Items.BUCKET)) {
                pPlayer.setItemInHand(pHand, ItemUtils.createFilledResult(itemStack, pPlayer, ModItems.SOUL_LAVA_BUCKET.get().getDefaultInstance()));
                pPlayer.awardStat(Stats.USE_CAULDRON);
                pPlayer.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
                pLevel.setBlockAndUpdate(pPos, Blocks.CAULDRON.defaultBlockState());
                pLevel.playSound(null, pPos, SoundEvents.BUCKET_FILL_LAVA, SoundSource.BLOCKS, 1.0F, 1.0F);
                pLevel.gameEvent(null, GameEvent.FLUID_PICKUP, pPos);

                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    protected double getContentHeight(BlockState state) {
        return 0.9375D;
    }

    @Override
    public boolean isFull(BlockState pState) {
        return true;
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (this.isEntityInsideContent(pState, pPos, pEntity)) {
            pEntity.lavaHurt();
        }
    }

    @Override
    public int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        return 5;
    }

}
