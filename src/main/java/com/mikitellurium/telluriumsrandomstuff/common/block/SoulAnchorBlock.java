package com.mikitellurium.telluriumsrandomstuff.common.block;

import com.mikitellurium.telluriumsrandomstuff.common.blockentity.SoulAnchorBlockEntity;
import com.mikitellurium.telluriumsrandomstuff.common.capability.SoulAnchorCapabilityProvider;
import com.mikitellurium.telluriumsrandomstuff.common.capability.SoulAnchorLevelData;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
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
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
                if (player.getItemInHand(interactionHand).is(ModItems.TOTEM_OF_BINDING.get())) {
                    if (blockEntity instanceof SoulAnchorBlockEntity soulAnchorBlockEntity) {
                        player.getCapability(SoulAnchorCapabilityProvider.INSTANCE).ifPresent((soulAnchor) -> {
                            if (!soulAnchor.hasChargedAnchor()) {
                                soulAnchor.charge(player, level, pos, blockState);
                                soulAnchorBlockEntity.setCachedPlayer(player.getUUID());
                                if (soulAnchor.hasSavedInventory()) {
                                    soulAnchor.clearInventory();
                                    soulAnchor.setCanRecoverInventory(false);
                                }
                            } else {
                                player.displayClientMessage(Component.translatable(
                                        "blockentity.telluriumsrandomstuff.soul_anchor.warning"), true);
                            }
                        });
                    }
                    if (!player.isCreative()) itemStack.shrink(1);
                } else {
                    this.openContainer(level, pos, player);
                }
            } else {
                if (blockEntity instanceof SoulAnchorBlockEntity soulAnchorBlockEntity && player instanceof ServerPlayer) {
                    player.getCapability(SoulAnchorCapabilityProvider.INSTANCE).ifPresent((soulAnchor) -> {
                            if (soulAnchor.hasSavedInventory() && soulAnchor.canRecoverInventory() &&
                            player.getUUID().equals(soulAnchorBlockEntity.getCachedPlayer())) {
                                // Fill the soul anchor with the saved inventory then clear it
                                soulAnchor.putInventoryInAnchor(soulAnchorBlockEntity);
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
                if (soulAnchorBlockEntity.getCachedPlayer() != null) {
                    Player savedPlayer = level.getPlayerByUUID(soulAnchorBlockEntity.getCachedPlayer());
                    if (savedPlayer == player) { // If the owner broke the soul anchor
                        System.out.println("Same player");
                        player.getCapability(SoulAnchorCapabilityProvider.INSTANCE).ifPresent((soulAnchor) -> {
                            soulAnchor.setChargedAnchor(false);
                            soulAnchor.clearInventory();
                        });
                    } else if (savedPlayer != null) { // If a different player broke the soul anchor
                        System.out.println("Diff player");
                        savedPlayer.getCapability(SoulAnchorCapabilityProvider.INSTANCE).ifPresent((soulAnchor) -> {
                            soulAnchor.setChargedAnchor(false);
                            soulAnchor.clearInventory();
                        });
                    } else { // If the owner is offline save him in world data so he can be removed on next login
                        SoulAnchorLevelData data = SoulAnchorLevelData.get(level);
                        data.addPlayer(soulAnchorBlockEntity.getCachedPlayer());
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
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        return blockState.getValue(CHARGED) ? 15 : 0;
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

    /* Events */
    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity().level().isClientSide) {
            return;
        }
        if (event.getEntity().level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
            return;
        }
        if (event.getEntity() instanceof Player player) {
            player.getCapability(SoulAnchorCapabilityProvider.INSTANCE).ifPresent((soulAnchor) -> {
                if (soulAnchor.hasChargedAnchor()) {
                    soulAnchor.saveInventory(player.getInventory());
                    soulAnchor.setCanRecoverInventory(true);
                }
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerDropInventory(LivingDropsEvent event) {
        if (event.getEntity().level().isClientSide) {
            return;
        }
        if (event.getEntity().level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
            return;
        }
        if (event.getEntity() instanceof Player player) {
            player.getCapability(SoulAnchorCapabilityProvider.INSTANCE).ifPresent((soulAnchor) -> {
                if (soulAnchor.hasChargedAnchor()) {
                    event.setCanceled(true);
                }
            });
        }
    }

    @SubscribeEvent
    public static void onAttachPlayerCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            if (!player.getCapability(SoulAnchorCapabilityProvider.INSTANCE).isPresent()) {
                event.addCapability(FastLoc.modLoc("soul_anchor"), new SoulAnchorCapabilityProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.getEntity().level().isClientSide) {
            return;
        }
        if (event.isWasDeath()) {
            event.getOriginal().reviveCaps();
            event.getOriginal().getCapability(SoulAnchorCapabilityProvider.INSTANCE).ifPresent(
                    (old) -> event.getEntity().getCapability(SoulAnchorCapabilityProvider.INSTANCE).ifPresent(
                            (newClone) -> newClone.copyFrom(old)));
            event.getOriginal().invalidateCaps();
        }

    }

    @SubscribeEvent
    public static void onPlayerJoinLevel(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide) {
            return;
        }
        if (event.getLevel().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
            return;
        }

        if (event.getEntity() instanceof Player player) {
            if (SoulAnchorLevelData.get(event.getLevel()).removePlayer(player)) {
                player.getCapability(SoulAnchorCapabilityProvider.INSTANCE).ifPresent((soulAnchor) -> {
                    soulAnchor.setChargedAnchor(false);
                    soulAnchor.clearInventory();
                });
            }
        }
    }

}
