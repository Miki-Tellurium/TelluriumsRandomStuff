package com.mikitellurium.telluriumsrandomstuff.common.entity;

import com.mikitellurium.telluriumsrandomstuff.registry.ModEntities;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;

public class SpiritedAllay extends PathfinderMob {

    private static final EntityDataAccessor<Byte> COLOR_ID = SynchedEntityData.defineId(SpiritedAllay.class, EntityDataSerializers.BYTE);
    private static final String COLOR_TAG = "Color";

    public SpiritedAllay(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    public SpiritedAllay(Level level, DyeColor color) {
        this(ModEntities.SPIRITED_ALLAY.get(), level);
        this.setColor(color);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Allay.createMobAttributes().add(Attributes.FOLLOW_RANGE, 64.0D);
    }

    @SuppressWarnings("deprecation")
    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType,
                                        @Nullable SpawnGroupData spawnData, @Nullable CompoundTag tag) {
        if (tag != null) {
            
            this.load(tag);
//            if (tag.contains("CustomName")) {
//                this.setCustomName(Component.Serializer.fromJson(tag.getString("CustomName")));
//            }
//            if (tag.contains(COLOR_TAG)) {
//                DyeColor color = DyeColor.byId(tag.getByte(COLOR_TAG));
//                this.setColor(color);
//            }
        }
        return super.finalizeSpawn(level, difficulty, spawnType, spawnData, tag);
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(Tags.Items.DYES)) {
            DyeColor dyeColor = DyeColor.getColor(itemStack);
            if (this.isAlive() && dyeColor != null && this.getColor() != dyeColor) {
                this.level().playSound(player, this, SoundEvents.DYE_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
                if (!this.level().isClientSide) {
                    this.setColor(dyeColor);
                    itemStack.shrink(1);
                }
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        } else if (itemStack.is(ModItems.SPIRITED_ECHO_WAND.get())) {
            if (!this.level().isClientSide) {
                ItemStack itemStack1 = new ItemStack(ModItems.SPIRITED_ALLAY_ITEM.get());
                CompoundTag entityTag = new CompoundTag();
                this.save(entityTag);
                CompoundTag tag = itemStack1.getOrCreateTag();
                tag.put("SavedEntity", entityTag);
                this.spawnAtLocation(itemStack1, 0.4F);
                this.discard();
                return InteractionResult.SUCCESS;
            }
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public boolean hurt(DamageSource damageSource, float amount) {
        if (damageSource.getDirectEntity() instanceof Player) {
            this.kill();
        }
        return super.hurt(damageSource, amount);
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

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ALLAY_AMBIENT_WITHOUT_ITEM;
    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ALLAY_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ALLAY_DEATH;
    }

    protected float getSoundVolume() {
        return 0.4F;
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
