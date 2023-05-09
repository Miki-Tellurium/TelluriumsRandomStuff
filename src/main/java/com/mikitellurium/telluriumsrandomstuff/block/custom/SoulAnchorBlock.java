package com.mikitellurium.telluriumsrandomstuff.block.custom;

import com.mikitellurium.telluriumsrandomstuff.blockentity.custom.SoulAnchorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class SoulAnchorBlock extends BaseEntityBlock {

    public static final BooleanProperty CHARGED = BooleanProperty.create("charged");

    public SoulAnchorBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.RESPAWN_ANCHOR)
                .lightLevel(SoulAnchorBlock::getSoulAnchorLightLevel));
        this.registerDefaultState(this.stateDefinition.any().setValue(CHARGED, false));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState blockState) {
        return new SoulAnchorBlockEntity(pos, blockState);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player,
                                 InteractionHand interactionHand, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            ItemStack itemStack = player.getItemInHand(interactionHand);
            if (player.getItemInHand(interactionHand).is(Items.TOTEM_OF_UNDYING)) {
                charge(player, level, pos, blockState);
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
            } else {
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (blockEntity instanceof SoulAnchorBlockEntity && player instanceof ServerPlayer) {
                    this.openContainer(level, pos, player);
                } else {
                    throw new IllegalStateException("Container provider or player is missing");
                }
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    private void openContainer(Level level, BlockPos pos, Player player) {
        NetworkHooks.openScreen((ServerPlayer)player, (SoulAnchorBlockEntity)level.getBlockEntity(pos), pos);
    }

    private static void charge(@Nullable Entity entity, Level level, BlockPos pos, BlockState blockState) {
        level.setBlockAndUpdate(pos, blockState.setValue(CHARGED, !blockState.getValue(CHARGED)));
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(entity, blockState));
        level.playSound(null, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D,
                (double)pos.getZ() + 0.5D, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS,
                1.0F, 1.0F);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof SoulAnchorBlockEntity) {
                ((SoulAnchorBlockEntity) blockEntity).dropItemsOnBreak();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    public static int getSoulAnchorLightLevel(BlockState blockState) {
        return blockState.getValue(CHARGED) ? 12 : 0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(CHARGED);
    }

}
