package com.mikitellurium.telluriumsrandomstuff.block.custom;

import com.mikitellurium.telluriumsrandomstuff.blockentity.custom.ExtractorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSourceImpl;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class ExtractorBlock extends DispenserBlock {

    private static final DispenseItemBehavior DEFAULT_DISPENSE = new DefaultDispenseItemBehavior();

    public ExtractorBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.DROPPER));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH)
                .setValue(TRIGGERED, false));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ExtractorBlockEntity(blockPos, blockState);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof ExtractorBlockEntity && pPlayer instanceof ServerPlayer) {
                this.openContainer(pLevel, pPos, pPlayer);
            } else {
                throw new IllegalStateException("Container provider or player is missing");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }

    protected void openContainer(Level level, BlockPos pos, Player player) {
        ExtractorBlockEntity extractor = (ExtractorBlockEntity) level.getBlockEntity(pos);
        if (player != null && extractor != null) {
            NetworkHooks.openScreen((ServerPlayer) player, extractor, pos);
        } else {
            throw new RuntimeException("Player or container provider missing");
        }
    }

    @Override
    public void tick(BlockState blockState, ServerLevel level, BlockPos blockPos, RandomSource random) {
        this.dispenseFrom(level, blockState, blockPos);
    }

    protected void dispenseFrom(ServerLevel level, BlockState blockState, BlockPos blockPos) {
        BlockSourceImpl blockSource = new BlockSourceImpl(level, blockPos);
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof ExtractorBlockEntity extractor) {
            Direction back = blockState.getValue(FACING).getOpposite();
            if (!extractor.hasInventoryBehind(level, blockPos.relative(back))) {
                dispenseFailed(level, blockPos);
            } else {
                BlockEntity blockEntityBehind = level.getBlockEntity(blockPos.relative(back));
                blockEntityBehind.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent((inventory) -> {
                    if (extractor.isInventoryEmpty(inventory)) {
                        dispenseFailed(level, blockPos);
                    } else {
                        DispenseItemBehavior dispenseitembehavior = this.getDispenseMethod();
                        if (dispenseitembehavior != DispenseItemBehavior.NOOP) {
                            extractor.dispenseItem(blockEntityBehind, blockSource, dispenseitembehavior);
                        }
                    }
                });
            }
        }
    }

    private void dispenseFailed(Level level, BlockPos blockPos) {
        level.levelEvent(1001, blockPos, 0);
        level.gameEvent(null, GameEvent.DISPENSE_FAIL, blockPos);
    }

    protected DispenseItemBehavior getDispenseMethod() {
        return DEFAULT_DISPENSE;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof ExtractorBlockEntity) {
                ((ExtractorBlockEntity) blockEntity).dropItemsOnBreak();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

}
