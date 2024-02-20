package com.mikitellurium.telluriumsrandomstuff.common.block;

import com.mikitellurium.telluriumsrandomstuff.common.blockentity.AwakenedSculkShriekerBlockEntity;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlockEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.FastColor;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;

public class AwakenedSculkShriekerBlock extends SculkShriekerBlock {

    private static final int orange = FastColor.ABGR32.color(255, 0xFD7E00);

    public AwakenedSculkShriekerBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.SCULK_SHRIEKER));
        super.registerDefaultState(this.stateDefinition.any()
                .setValue(SHRIEKING, false)
                .setValue(WATERLOGGED, false)
                .setValue(CAN_SUMMON, true));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState blockState) {
        return new AwakenedSculkShriekerBlockEntity(pos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState,
                                                                  BlockEntityType<T> blockEntityType) {
        return !level.isClientSide ? BaseEntityBlock.createTickerHelper(blockEntityType,
                ModBlockEntities.AWAKENED_SCULK_SHRIEKER.get(),
                (tickLevel, pos, blockState1, shrieker) -> {
                    VibrationSystem.Ticker.tick(tickLevel, shrieker.getVibrationData(), shrieker.getVibrationUser());
                    shrieker.getSculkSpreader().updateCursors(tickLevel, pos, tickLevel.random, true);
                }) : null;
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState blockState, Entity entity) {
        if (level instanceof ServerLevel serverlevel) {
            ServerPlayer serverplayer = AwakenedSculkShriekerBlockEntity.tryGetPlayer(entity);
            if (serverplayer != null) {
                serverlevel.getBlockEntity(pos, ModBlockEntities.AWAKENED_SCULK_SHRIEKER.get())
                        .ifPresent((shrieker) -> shrieker.tryShriek(serverlevel, serverplayer));
            }
        }

        super.stepOn(level, pos, blockState, entity);
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos pos, BlockState newState,
                         boolean isMoving) {
        if (level instanceof ServerLevel serverlevel) {
            if (blockState.getValue(SHRIEKING) && !blockState.is(newState.getBlock())) {
                serverlevel.getBlockEntity(pos, ModBlockEntities.AWAKENED_SCULK_SHRIEKER.get())
                        .ifPresent((shrieker) -> shrieker.tryRespond(serverlevel));
            }
        }

        super.onRemove(blockState, level, pos, newState, isMoving);
    }

    @Override
    public void tick(BlockState blockState, ServerLevel level, BlockPos pos, RandomSource random) {
        if (blockState.getValue(SHRIEKING)) {
            level.setBlock(pos, blockState.setValue(SHRIEKING, Boolean.valueOf(false)), 3);
            level.getBlockEntity(pos, ModBlockEntities.AWAKENED_SCULK_SHRIEKER.get())
                    .ifPresent((shrieker) -> shrieker.tryRespond(level));
        }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable BlockGetter level,
                                List<Component> components, TooltipFlag flag) {
        Component shift = Component.literal(" <Shift>").withStyle(ChatFormatting.WHITE);
        Component warning = Component.translatable("block.telluriumsrandomstuff.awakened_sculk_shrieker.tooltip.warning")
                .withStyle((style) -> style.withColor(orange)).append(shift);
        components.add(warning);
        if (Screen.hasShiftDown()) {
            components.add(Component.translatable("block.telluriumsrandomstuff.awakened_sculk_shrieker.tooltip.message")
                    .withStyle((style) -> style.withColor(Color.ORANGE.getRGB())));
        }
    }

}
