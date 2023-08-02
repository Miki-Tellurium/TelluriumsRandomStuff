package com.mikitellurium.telluriumsrandomstuff.common.content.block;

import com.mikitellurium.telluriumsrandomstuff.common.content.block.interaction.ModCauldronInteractions;
import com.mikitellurium.telluriumsrandomstuff.common.content.fluid.SoulLavaFluid;
import com.mikitellurium.telluriumsrandomstuff.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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

    protected double getContentHeight(BlockState blockState) {
        return 0.9375D;
    }

    @Override
    public boolean isFull(BlockState blockState) {
        return true;
    }

    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos pos, Entity entity) {
        if (this.isEntityInsideContent(blockState, pos, entity) &&
                !entity.getType().is(ModTags.EntityTypes.SOUL_LAVA_IMMUNE)) {
            if (entity instanceof LivingEntity livingEntity) {
                SoulLavaFluid.soulLavaHurt(livingEntity);
            } else {
                entity.lavaHurt();
            }
        }
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        return 5;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState blockState, HitResult target, BlockGetter level, BlockPos pos,
                                       Player player) {
        return Items.CAULDRON.getDefaultInstance();
    }

}
