package com.mikitellurium.telluriumsrandomstuff.common.content.block;

import com.mikitellurium.telluriumsrandomstuff.common.content.blockentity.SoulAnchorBlockEntity;
import com.mikitellurium.telluriumsrandomstuff.common.capability.SoulAnchorCapabilityProvider;
import com.mikitellurium.telluriumsrandomstuff.common.capability.SoulAnchorLevelData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
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
                    if (blockEntity instanceof SoulAnchorBlockEntity soulAnchorBlockEntity) {
                        player.getCapability(SoulAnchorCapabilityProvider.SOUL_ANCHOR_CAPABILITY).ifPresent((soulAnchor) -> {
                            if (!soulAnchor.hasChargedAnchor()) {
                                soulAnchor.charge(player, level, pos, blockState);
                                soulAnchorBlockEntity.setSavedPlayer(player.getUUID());
                                if (soulAnchor.hasSavedInventory()) {
                                    soulAnchor.clearInventory();
                                    soulAnchor.setCanRecoverInventory(false);
                                }
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
                if (blockEntity instanceof SoulAnchorBlockEntity soulAnchorBlockEntity && player instanceof ServerPlayer) {
                    player.getCapability(SoulAnchorCapabilityProvider.SOUL_ANCHOR_CAPABILITY).ifPresent((soulAnchor) -> {
                            if (soulAnchor.hasSavedInventory() && soulAnchor.canRecoverInventory() &&
                            player.getUUID().equals(soulAnchorBlockEntity.getSavedPlayer())) {
                                // Fill the soul anchor with the saved inventory then clear it
                                soulAnchor.putInventoryInAnchor(soulAnchorBlockEntity);
                                soulAnchor.clearInventory();
                                soulAnchor.setCanRecoverInventory(false);
                                soulAnchor.discharge(player, level, pos, blockState);
                                soulAnchorBlockEntity.clearSavedPlayer();
                            }
                    });

                    this.openContainer(level, pos, player);
                }
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    private void openContainer(Level level, BlockPos pos, Player player) {
        SoulAnchorBlockEntity soulAnchor = (SoulAnchorBlockEntity)level.getBlockEntity(pos);
        if (player != null && soulAnchor != null) {
            NetworkHooks.openScreen((ServerPlayer) player, soulAnchor, pos);
        } else {
            throw new RuntimeException("Player or container provider missing");
        }
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState blockState, Level level, BlockPos pos, Player player,
                                       boolean willHarvest, FluidState fluid) {
        if (blockState.getValue(CHARGED)) {
            if (level.getBlockEntity(pos) instanceof SoulAnchorBlockEntity soulAnchorBlockEntity) {
                if (soulAnchorBlockEntity.getSavedPlayer() != null) {
                    Player savedPlayer = level.getPlayerByUUID(soulAnchorBlockEntity.getSavedPlayer());
                    // If the owner broke the soul anchor
                    if (savedPlayer == player) {
                        System.out.println("Same player");
                        player.getCapability(SoulAnchorCapabilityProvider.SOUL_ANCHOR_CAPABILITY).ifPresent((soulAnchor) -> {
                            soulAnchor.setChargedAnchor(false);
                            soulAnchor.clearInventory();
                        });
                        // If a different player broke the soul anchor
                    } else if (savedPlayer != null) {
                        System.out.println("Diff player");
                        savedPlayer.getCapability(SoulAnchorCapabilityProvider.SOUL_ANCHOR_CAPABILITY).ifPresent((soulAnchor) -> {
                            soulAnchor.setChargedAnchor(false);
                            soulAnchor.clearInventory();
                        });
                        // If the owner is offline save him in world data so he can be removed on next login
                    } else {
                        SoulAnchorLevelData data = SoulAnchorLevelData.get(level);
                        data.addPlayer(soulAnchorBlockEntity.getSavedPlayer());
                        System.out.println("Saved data");
                    }
                }
            }
        }
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

    @Override
    public boolean isSignalSource(BlockState blockState) {
        return true;
    }

    @Override
    public int getSignal(BlockState blockState, BlockGetter blockGetter, BlockPos pos, Direction direction) {
        return blockState.getValue(CHARGED) ? 5 : 0;
    }

    @Override
    public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
        super.onNeighborChange(state, level, pos, neighbor);
    }

    public static int getSoulAnchorLightLevel(BlockState blockState) {
        return blockState.getValue(CHARGED) ? 12 : 0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(CHARGED);
    }

}
