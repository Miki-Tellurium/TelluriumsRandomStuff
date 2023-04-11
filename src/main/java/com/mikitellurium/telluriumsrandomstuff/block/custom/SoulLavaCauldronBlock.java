package com.mikitellurium.telluriumsrandomstuff.block.custom;

import com.mikitellurium.telluriumsrandomstuff.block.interaction.ModCauldronInteractions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;

public class SoulLavaCauldronBlock extends AbstractCauldronBlock {

    public SoulLavaCauldronBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.LAVA_CAULDRON), ModCauldronInteractions.SOUL_LAVA);
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

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return Items.CAULDRON.getDefaultInstance();
    }

}
