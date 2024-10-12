package com.mikitellurium.telluriumsrandomstuff.common.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class SpiritedAllay extends PathfinderMob {

    private static final EntityDataAccessor<Byte> COLOR_ID = SynchedEntityData.defineId(SpiritedAllay.class, EntityDataSerializers.BYTE);
    private static final String COLOR_TAG = "Color";

    public SpiritedAllay(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType,
                                        @Nullable SpawnGroupData spawnData, @Nullable CompoundTag tag) {
        if (tag != null && tag.contains(COLOR_TAG)) {
            DyeColor color = DyeColor.byId(tag.getByte(COLOR_TAG));
            this.setColor(color);
        }
        return super.finalizeSpawn(level, difficulty, spawnType, spawnData, tag);
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        DyeColor dyeColor = DyeColor.getColor(itemStack);
        if (this.isAlive() && dyeColor != null && this.getColor() != dyeColor) {
                this.level().playSound(player, this, SoundEvents.DYE_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
                if (!this.level().isClientSide) {
                    this.setColor(dyeColor);
                    itemStack.shrink(1);
                }

                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }

        return InteractionResult.PASS;
    }

    public DyeColor getColor() {
        return DyeColor.byId(this.entityData.get(COLOR_ID));
    }

    public void setColor(DyeColor color) {
        this.entityData.set(COLOR_ID, (byte)color.getId());
    }

    public float getHoldingItemAnimationProgress(float ticks) {
        return 0;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(COLOR_ID, (byte) DyeColor.LIGHT_BLUE.getId());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putByte(COLOR_TAG, (byte)this.getColor().getId());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setColor(DyeColor.byId(tag.getByte(COLOR_TAG)));
    }

}
