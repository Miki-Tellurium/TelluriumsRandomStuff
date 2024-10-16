package com.mikitellurium.telluriumsrandomstuff.lib;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface TickingBlockEntity {
    // todo implement in every block entity
    default void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (level.isClientSide) {
            this.clientTick((ClientLevel) level, blockPos, blockState);
        } else {
            this.serverTick((ServerLevel) level, blockPos, blockState);
        }
    }

    default void serverTick(ServerLevel level, BlockPos blockPos, BlockState blockState) {
    }

    default void clientTick(ClientLevel level, BlockPos blockPos, BlockState blockState) {
    }

}
