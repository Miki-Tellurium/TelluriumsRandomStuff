package com.mikitellurium.telluriumsrandomstuff.lib;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public abstract class TickingFurnaceBlock extends AbstractFurnaceBlock {

    private final BlockEntityFactory factory;

    protected TickingFurnaceBlock(BlockEntityFactory factory, Properties properties) {
        super(properties);
        this.factory = factory;
    }

    public abstract BlockEntityType<?> getBlockEntityType();

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState blockState) {
        return this.factory.make(pos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return createTickerHelper(type, this.getBlockEntityType(),
                (tickLevel, pos, state, blockEntity) -> {
                    if (blockEntity instanceof TickingBlockEntity) {
                        ((TickingBlockEntity)blockEntity).tick(tickLevel, pos, state);
                    } else {
                        throw new RuntimeException("Block entity is not a TickingBlockEntity");
                    }
                });
    }

    @FunctionalInterface
    public interface BlockEntityFactory {
        BlockEntity make(BlockPos pos, BlockState blockState);
    }

}
