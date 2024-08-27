package com.mikitellurium.telluriumsrandomstuff.common.blockentity;

import com.mikitellurium.telluriumsrandomstuff.registry.ModBlockEntities;
import com.mikitellurium.telluriumsrandomstuff.util.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.SpawnUtil;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nullable;
import java.util.Optional;

public class AwakenedSculkShriekerBlockEntity extends BlockEntity {

    private final SculkSpreader sculkSpreader = SculkSpreader.createLevelSpreader();
    public static ForgeConfigSpec.IntValue SPAWN_DELAY;
    private int spawnDelay = SPAWN_DELAY.get();

    public AwakenedSculkShriekerBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.AWAKENED_SCULK_SHRIEKER.get(), pos, blockState);
    }

    public void tick(Level level, BlockPos pos, BlockState blockState) {
        if (level.isClientSide) return;
        this.sculkSpreader.updateCursors(level, pos, level.random, true);
        if (!blockState.getValue(SculkShriekerBlock.CAN_SUMMON)) {
            if (--spawnDelay <= 0) {
                level.setBlockAndUpdate(pos, blockState.setValue(SculkShriekerBlock.CAN_SUMMON, true));
                spawnDelay = SPAWN_DELAY.get();
            }
            setChanged(level, pos, blockState);
        }
    }

    public void tryShriek(ServerLevel level, @Nullable ServerPlayer player) {
        BlockState blockstate = this.getBlockState();
        if (!blockstate.getValue(SculkShriekerBlock.SHRIEKING)) {
            if (this.canRespond(level)) {
                this.shriek(level, player);
            }
        }
    }

    private void shriek(ServerLevel level, @Nullable Entity source) {
        BlockPos pos = this.getBlockPos();
        BlockState blockState = this.getBlockState();
        level.setBlock(pos, blockState.setValue(SculkShriekerBlock.SHRIEKING, true), 2);
        level.scheduleTick(pos, blockState.getBlock(), 90);
        level.levelEvent(3007, pos, 0);
        level.gameEvent(GameEvent.SHRIEK, pos, GameEvent.Context.of(source));
    }

    private boolean canRespond(ServerLevel level) {
        return this.getBlockState().getValue(SculkShriekerBlock.CAN_SUMMON) && level.getDifficulty() != Difficulty.PEACEFUL && level.getGameRules().getBoolean(GameRules.RULE_DO_WARDEN_SPAWNING);
    }

    public void tryRespond(ServerLevel level) {
        if (this.canRespond(level)) {
            if (!this.trySummonWarden(level)) {
                this.playWardenReplySound(level);
            }

            Warden.applyDarknessAround(level, Vec3.atCenterOf(this.getBlockPos()), null, 40);
        }
    }

    private void playWardenReplySound(Level level) {
            BlockPos blockpos = this.getBlockPos();
            int i = blockpos.getX() + Mth.randomBetweenInclusive(level.random, -10, 10);
            int j = blockpos.getY() + Mth.randomBetweenInclusive(level.random, -10, 10);
            int k = blockpos.getZ() + Mth.randomBetweenInclusive(level.random, -10, 10);
            level.playSound(null, i, j, k, SoundEvents.WARDEN_ANGRY, SoundSource.HOSTILE, 5.0F, 1.0F);
    }

    private boolean trySummonWarden(ServerLevel level) {
        Optional<Warden> warden = SpawnUtil.trySpawnMob(EntityType.WARDEN, MobSpawnType.TRIGGERED, level, this.getBlockPos(),
                    20, 5, 6, SpawnUtil.Strategy.ON_TOP_OF_COLLIDER);
        boolean hasSpawned = warden.isPresent();
        if (hasSpawned) {
            sculkSpreader.addCursors(warden.get().blockPosition(), 1);
            BlockState blockState = this.getBlockState();
            level.setBlockAndUpdate(this.getBlockPos(), blockState.setValue(SculkShriekerBlock.CAN_SUMMON, false));
        }
        return hasSpawned;
    }

    public SculkSpreader getSculkSpreader() {
        return sculkSpreader;
    }

    // NBT
    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.putInt("awakened_shrieker.spawnDelay", this.spawnDelay);
        LogUtils.consoleLog("Saved spawn delay="+tag.getInt("awakened_shrieker.spawnDelay"));
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.spawnDelay = Math.min(tag.getInt("awakened_shrieker.spawnDelay"), SPAWN_DELAY.get());
        LogUtils.consoleLog("Loaded spawn delay="+spawnDelay);
    }

}
