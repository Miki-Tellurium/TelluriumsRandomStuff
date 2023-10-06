package com.mikitellurium.telluriumsrandomstuff.common.blockentity;

import com.mikitellurium.telluriumsrandomstuff.registry.ModBlockEntities;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.GameEventTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.SpawnUtil;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.monster.warden.WardenSpawnTracker;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.OptionalInt;

public class AwakenedSculkShriekerBlockEntity extends BlockEntity implements GameEventListener.Holder<VibrationSystem.Listener>, VibrationSystem {

    private static final Logger LOGGER = LogUtils.getLogger();
//    private static final int WARNING_SOUND_RADIUS = 10;
//    private static final int WARDEN_SPAWN_ATTEMPTS = 20;
//    private static final int WARDEN_SPAWN_RANGE_XZ = 5;
//    private static final int WARDEN_SPAWN_RANGE_Y = 6;
//    private static final int DARKNESS_RADIUS = 40;
//    private static final int SHRIEKING_TICKS = 90;
    private static final Int2ObjectMap<SoundEvent> SOUND_BY_LEVEL = Util.make(new Int2ObjectOpenHashMap<>(), (p_222866_) -> {
        p_222866_.put(1, SoundEvents.WARDEN_NEARBY_CLOSE);
        p_222866_.put(2, SoundEvents.WARDEN_NEARBY_CLOSER);
        p_222866_.put(3, SoundEvents.WARDEN_NEARBY_CLOSEST);
        p_222866_.put(4, SoundEvents.WARDEN_LISTENING_ANGRY);
    });
    private int warningLevel;
    private final VibrationSystem.User vibrationUser = new AwakenedSculkShriekerBlockEntity.VibrationUser();
    private VibrationSystem.Data vibrationData = new VibrationSystem.Data();
    private final VibrationSystem.Listener vibrationListener = new VibrationSystem.Listener(this);
    private final SculkSpreader sculkSpreader;

    public AwakenedSculkShriekerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.AWAKENED_SCULK_SHRIEKER.get(), pPos, pBlockState);
        this.sculkSpreader = SculkSpreader.createLevelSpreader();
    }

    public VibrationSystem.Data getVibrationData() {
        return this.vibrationData;
    }

    public VibrationSystem.User getVibrationUser() {
        return this.vibrationUser;
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("warning_level", 99)) {
            this.warningLevel = tag.getInt("warning_level");
        }

        if (tag.contains("listener", 10)) {
            VibrationSystem.Data.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, tag.getCompound("listener"))).resultOrPartial(LOGGER::error).ifPresent((data) -> {
                this.vibrationData = data;
            });
        }

    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("warning_level", this.warningLevel);
        VibrationSystem.Data.CODEC.encodeStart(NbtOps.INSTANCE, this.vibrationData).resultOrPartial(LOGGER::error).ifPresent((dataTag) -> {
            tag.put("listener", dataTag);
        });
    }

    @Nullable
    public static ServerPlayer tryGetPlayer(@Nullable Entity entity) {
        if (entity instanceof ServerPlayer serverplayer1) {
            return serverplayer1;
        } else {
            if (entity != null) {
                LivingEntity $$6 = entity.getControllingPassenger();
                if ($$6 instanceof ServerPlayer) {
                    ServerPlayer serverplayer = (ServerPlayer)$$6;
                    return serverplayer;
                }
            }

            if (entity instanceof Projectile projectile) {
                Entity ownerEntity = projectile.getOwner();
                if (ownerEntity instanceof ServerPlayer serverplayer3) {
                    return serverplayer3;
                }
            }

            if (entity instanceof ItemEntity itementity) {
                Entity entity1 = itementity.getOwner();
                if (entity1 instanceof ServerPlayer serverplayer2) {
                    return serverplayer2;
                }
            }

            return null;
        }
    }

    public void tryShriek(ServerLevel level, @Nullable ServerPlayer player) {
        if (player != null) {
            BlockState blockstate = this.getBlockState();
            if (!blockstate.getValue(SculkShriekerBlock.SHRIEKING)) {
                this.warningLevel = 0;
                if (!this.canRespond(level) || this.tryToWarn(level, player)) {
                    this.shriek(level, player);
                }
            }
        }
    }

    private boolean tryToWarn(ServerLevel level, ServerPlayer player) {
        OptionalInt optionalInt = WardenSpawnTracker.tryWarn(level, this.getBlockPos(), player);
        optionalInt.ifPresent((i) -> {
            this.warningLevel = i;
        });
        return optionalInt.isPresent();
    }

    private void shriek(ServerLevel level, @Nullable Entity source) {
        BlockPos blockpos = this.getBlockPos();
        BlockState blockstate = this.getBlockState();
        level.setBlock(blockpos, blockstate.setValue(SculkShriekerBlock.SHRIEKING, Boolean.valueOf(true)), 2);
        level.scheduleTick(blockpos, blockstate.getBlock(), 90);
        level.levelEvent(3007, blockpos, 0);
        level.gameEvent(GameEvent.SHRIEK, blockpos, GameEvent.Context.of(source));
    }

    private boolean canRespond(ServerLevel level) {
        return this.getBlockState().getValue(SculkShriekerBlock.CAN_SUMMON) && level.getDifficulty() != Difficulty.PEACEFUL && level.getGameRules().getBoolean(GameRules.RULE_DO_WARDEN_SPAWNING);
    }

    public void tryRespond(ServerLevel level) {
        if (this.canRespond(level) && this.warningLevel > 0) {
            if (!this.trySummonWarden(level)) {
                this.playWardenReplySound(level);
            }

            Warden.applyDarknessAround(level, Vec3.atCenterOf(this.getBlockPos()), null, 40);
        }

    }

    private void playWardenReplySound(Level level) {
        SoundEvent soundevent = SOUND_BY_LEVEL.get(this.warningLevel);
        if (soundevent != null) {
            BlockPos blockpos = this.getBlockPos();
            int i = blockpos.getX() + Mth.randomBetweenInclusive(level.random, -10, 10);
            int j = blockpos.getY() + Mth.randomBetweenInclusive(level.random, -10, 10);
            int k = blockpos.getZ() + Mth.randomBetweenInclusive(level.random, -10, 10);
            level.playSound(null, i, j, k, soundevent, SoundSource.HOSTILE, 5.0F, 1.0F);
        }

    }

    private boolean trySummonWarden(ServerLevel pLevel) {
        if (this.warningLevel < 4) {
            return false;
        }
        Optional<Warden> warden = SpawnUtil.trySpawnMob(EntityType.WARDEN, MobSpawnType.TRIGGERED, pLevel, this.getBlockPos(),
                    20, 5, 6, SpawnUtil.Strategy.ON_TOP_OF_COLLIDER);
        boolean hasSpawned = warden.isPresent();
        if (hasSpawned) sculkSpreader.addCursors(warden.get().blockPosition(), 1);
        return hasSpawned;
    }

    public VibrationSystem.Listener getListener() {
        return this.vibrationListener;
    }

    public SculkSpreader getSculkSpreader() {
        return sculkSpreader;
    }

    class VibrationUser implements VibrationSystem.User {
        //private static final int LISTENER_RADIUS = 8;
        private final PositionSource positionSource = new BlockPositionSource(AwakenedSculkShriekerBlockEntity.this.worldPosition);

        public VibrationUser() {
        }

        public int getListenerRadius() {
            return 8;
        }

        public PositionSource getPositionSource() {
            return this.positionSource;
        }

        public TagKey<GameEvent> getListenableEvents() {
            return GameEventTags.SHRIEKER_CAN_LISTEN;
        }

        public boolean canReceiveVibration(ServerLevel level, BlockPos pos, GameEvent gameEvent, GameEvent.Context context) {
            return !AwakenedSculkShriekerBlockEntity.this.getBlockState().getValue(SculkShriekerBlock.SHRIEKING) && AwakenedSculkShriekerBlockEntity.tryGetPlayer(context.sourceEntity()) != null;
        }

        public void onReceiveVibration(ServerLevel level, BlockPos pos, GameEvent gameEvent, @Nullable Entity entity, @Nullable Entity entity1, float distance) {
            AwakenedSculkShriekerBlockEntity.this.tryShriek(level, AwakenedSculkShriekerBlockEntity.tryGetPlayer(entity1 != null ? entity1 : entity));
        }

        public void onDataChanged() {
            AwakenedSculkShriekerBlockEntity.this.setChanged();
        }

        public boolean requiresAdjacentChunksToBeTicking() {
            return true;
        }
    }

}
