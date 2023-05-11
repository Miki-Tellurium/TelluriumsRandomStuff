package com.mikitellurium.telluriumsrandomstuff.block.custom;

import com.mikitellurium.telluriumsrandomstuff.blockentity.custom.SoulAnchorBlockEntity;
import com.mikitellurium.telluriumsrandomstuff.capability.SoulAnchorCapabilityProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.level.material.FluidState;
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
            BlockEntity blockEntity = level.getBlockEntity(pos);
            ItemStack itemStack = player.getItemInHand(interactionHand);
            if (!blockState.getValue(CHARGED)) {
                // If the player use a totem of undying on uncharged anchor
                if (player.getItemInHand(interactionHand).is(Items.TOTEM_OF_UNDYING)) {
                    if (blockEntity instanceof SoulAnchorBlockEntity) {
                        player.getCapability(SoulAnchorCapabilityProvider.SOUL_ANCHOR_CAPABILITY).ifPresent((soulAnchor) -> {
                            if (!soulAnchor.hasChargedAnchor()) {
                                soulAnchor.charge(player, level, pos, blockState);
                            } else {
                                player.sendSystemMessage(Component.literal("You already have a charged soul anchor"));
                            }
                        });
                    }
                    if (!player.isCreative()) itemStack.shrink(1);
                } else {
                    this.openContainer(level, pos, player);
                }
            } else {
                if (blockEntity instanceof SoulAnchorBlockEntity && player instanceof ServerPlayer) {
                    player.getCapability(SoulAnchorCapabilityProvider.SOUL_ANCHOR_CAPABILITY).ifPresent((soulAnchor) -> {
                            // Fill the soul anchor with the saved inventory then clear it
                            if (soulAnchor.hasSavedInventory() && soulAnchor.hasRecentlyDied()) {
                                soulAnchor.putInventoryInAnchor((SoulAnchorBlockEntity) blockEntity);
                                soulAnchor.clearInventory();
                                soulAnchor.setRecentlyDied(false);
                                soulAnchor.discharge(player, level, pos, blockState);
                            }
                    });

                    this.openContainer(level, pos, player);
                }
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    private void openContainer(Level level, BlockPos pos, Player player) {
        player.getCapability(SoulAnchorCapabilityProvider.SOUL_ANCHOR_CAPABILITY).ifPresent((soulAnchor) -> {
            System.out.println("inventory " + soulAnchor.hasSavedInventory());
            System.out.println("has died " + soulAnchor.hasRecentlyDied());
            System.out.println("has charged " + soulAnchor.hasChargedAnchor());
        });

        SoulAnchorBlockEntity soulAnchor = (SoulAnchorBlockEntity)level.getBlockEntity(pos);
        if (player != null && soulAnchor != null) {
            NetworkHooks.openScreen((ServerPlayer) player, soulAnchor, pos);
        } else {
            throw new RuntimeException("Player or container provider missing");
        }
    }

    public static void charge(@Nullable Entity entity, Level level, BlockPos pos, BlockState blockState) {
        level.setBlockAndUpdate(pos, blockState.setValue(CHARGED, true));
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(entity, blockState));
        level.playSound(null, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D,
                (double)pos.getZ() + 0.5D, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS,
                1.0F, 1.0F);
    }

    public static void discharge(@Nullable Entity entity, Level level, BlockPos pos, BlockState blockState) {
        level.setBlockAndUpdate(pos, blockState.setValue(CHARGED, false));
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(entity, blockState));
        level.playSound(null, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D,
                (double)pos.getZ() + 0.5D, SoundEvents.RESPAWN_ANCHOR_DEPLETE.get(), SoundSource.BLOCKS,
                1.0F, 1.0F);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState blockState, Level level, BlockPos pos, Player player,
                                       boolean willHarvest, FluidState fluid) {
        player.getCapability(SoulAnchorCapabilityProvider.SOUL_ANCHOR_CAPABILITY).ifPresent((soulAnchor) -> {
            if (blockState.getValue(CHARGED) && soulAnchor.hasChargedAnchor()) {
                soulAnchor.setChargedAnchor(false);
            }
        });
        return super.onDestroyedByPlayer(blockState, level, pos, player, willHarvest, fluid);
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
