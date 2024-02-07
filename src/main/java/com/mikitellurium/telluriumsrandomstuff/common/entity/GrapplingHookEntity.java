package com.mikitellurium.telluriumsrandomstuff.common.entity;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.common.networking.GrapplingHookManager;
import com.mikitellurium.telluriumsrandomstuff.registry.ModEntities;
import com.mikitellurium.telluriumsrandomstuff.util.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;

public class GrapplingHookEntity extends Projectile {

    private static final EntityDataAccessor<Integer> DATA_HOOKED_ENTITY =
            SynchedEntityData.defineId(GrapplingHookEntity.class, EntityDataSerializers.INT);

    @Nullable
    private BlockState lastState;
    private HookState currentState = HookState.FLYING;
    private Entity hookedEntity;

    public GrapplingHookEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
        this.noCulling = true;
    }

    public GrapplingHookEntity(Player player, Level level) {
        this(ModEntities.GRAPPLING_HOOK.get(), level);
        this.setOwner(player);
        this.setPos(player.getX(), player.getEyeY(), player.getZ());
        this.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.0F, 1.0F);
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_HOOKED_ENTITY, 0);
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (DATA_HOOKED_ENTITY.equals(key)) {
            int i = this.getEntityData().get(DATA_HOOKED_ENTITY);
            this.hookedEntity = i > 0 ? this.level().getEntity(i - 1) : null;
        }
        super.onSyncedDataUpdated(key);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.currentState == HookState.HOOKED_IN_ENTITY) {
            if (this.hookedEntity != null) {
                if (!this.hookedEntity.isRemoved() && this.hookedEntity.level().dimension() == this.level().dimension()) {
                    this.setPos(this.hookedEntity.getX(), this.hookedEntity.getY(0.75D), this.hookedEntity.getZ());
                    return;
                } else {
                    this.setHookedEntity(null);
                    this.currentState = HookState.FLYING;
                }
            }
        }

        Vec3 vec3 = this.getDeltaMovement();
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            double d0 = vec3.horizontalDistance();
            this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI)));
            this.setXRot((float)(Mth.atan2(vec3.y, d0) * (double)(180F / (float)Math.PI)));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }

        BlockPos blockpos = this.blockPosition();
        BlockState blockstate = this.level().getBlockState(blockpos);
        if (!blockstate.isAir()) {
            VoxelShape voxelshape = blockstate.getCollisionShape(this.level(), blockpos);
            if (!voxelshape.isEmpty()) {
                Vec3 vec31 = this.position();

                for(AABB aabb : voxelshape.toAabbs()) {
                    if (aabb.move(blockpos).contains(vec31)) {
                        this.currentState = HookState.STUCK_ON_BLOCK;
                        break;
                    }
                }
            }
        }

        if (this.isInWaterOrRain() || blockstate.is(Blocks.POWDER_SNOW) || this.isInFluidType((fluidType, height) -> this.canFluidExtinguish(fluidType))) {
            this.clearFire();
        }

        if (this.isStuckInBlock()) {
            if (this.lastState != blockstate && this.shouldFall()) {
                this.startFalling();
            }
        } else if (this.currentState == HookState.FLYING) {
            if (this.hookedEntity != null) {
                this.setDeltaMovement(Vec3.ZERO);
                this.currentState = HookState.HOOKED_IN_ENTITY;
                return;
            }
            Vec3 vec32 = this.position();
            Vec3 vec33 = vec32.add(vec3);
            HitResult hitresult = this.level().clip(new ClipContext(vec32, vec33, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
            if (hitresult.getType() != HitResult.Type.MISS) {
                vec33 = hitresult.getLocation();
            }

            EntityHitResult entityhitresult = this.findHitEntity(vec32, vec33);
            if (entityhitresult != null) {
                hitresult = entityhitresult;
            }

            if (hitresult.getType() == HitResult.Type.ENTITY) {
                Entity entity = ((EntityHitResult) hitresult).getEntity();
                Entity entity1 = this.getOwner();
                if (entity instanceof Player && entity1 instanceof Player && !((Player) entity1).canHarmPlayer((Player) entity)) {
                    hitresult = null;
                }
            }

            if (hitresult != null && hitresult.getType() != HitResult.Type.MISS) {
                //if (!ForgeEventFactory.onProjectileImpact(this, hitresult)) {
                this.onHit(hitresult);
                this.hasImpulse = true;
                //}
            }

            vec3 = this.getDeltaMovement();
            double d5 = vec3.x;
            double d6 = vec3.y;
            double d1 = vec3.z;

            double d7 = this.getX() + d5;
            double d2 = this.getY() + d6;
            double d3 = this.getZ() + d1;
            double d4 = vec3.horizontalDistance();

            this.setXRot((float) (Mth.atan2(d6, d4) * (double) (180F / (float) Math.PI)));
            this.setXRot(lerpRotation(this.xRotO, this.getXRot()));
            this.setYRot(lerpRotation(this.yRotO, this.getYRot()));
            float f = 0.99F;
            if (this.isInWater()) {
                for (int j = 0; j < 4; ++j) {
                    this.level().addParticle(ParticleTypes.BUBBLE, d7 - d5 * 0.25D, d2 - d6 * 0.25D, d3 - d1 * 0.25D, d5, d6, d1);
                }

                f = this.getWaterInertia();
            }

            this.setDeltaMovement(vec3.scale(f));
            if (!this.isNoGravity()) {
                Vec3 vec34 = this.getDeltaMovement();
                this.setDeltaMovement(vec34.x, vec34.y - (double) 0.05F, vec34.z);
            }

            this.move(MoverType.SELF, this.getDeltaMovement());
            this.checkInsideBlocks();
        }
    }

    public void retrieve() {
        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        this.lastState = this.level().getBlockState(pResult.getBlockPos());
        super.onHitBlock(pResult);
        Vec3 vec3 = pResult.getLocation().subtract(this.getX(), this.getY(), this.getZ());
        this.setDeltaMovement(vec3);
        Vec3 vec31 = vec3.normalize().scale(0.05F);
        this.setPosRaw(this.getX() - vec31.x, this.getY() - vec31.y, this.getZ() - vec31.z);
        this.currentState = HookState.STUCK_ON_BLOCK;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        Player player = this.getPlayerOwner();
        if (!this.level().isClientSide) {
            if (player != null) {
                if (entity.hurt(this.damageSources().playerAttack(player), 0)) {
                    if (entity instanceof EnderMan) return;
                    this.setHookedEntity(result.getEntity());
                }
            }
        }
    }

    private boolean shouldFall() {
        return this.isStuckInBlock() && this.level().noCollision((new AABB(this.position(), this.position())).inflate(0.06D));
    }

    private void startFalling() {
        this.currentState = HookState.FLYING;
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(vec3.multiply(this.random.nextFloat() * 0.2F, this.random.nextFloat() * 0.2F, this.random.nextFloat() * 0.2F));
    }

    private boolean isStuckInBlock() {
        return this.currentState == HookState.STUCK_ON_BLOCK;
    }

    protected float getWaterInertia() {
        return 0.6F;
    }

    protected EntityHitResult findHitEntity(Vec3 start, Vec3 end) {
        return ProjectileUtil.getEntityHitResult(this.level(), this, start, end,
                this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D), this::canHitEntity);
    }

    private void setHookedEntity(@Nullable Entity entity) {
        this.hookedEntity = entity;
        this.getEntityData().set(DATA_HOOKED_ENTITY, entity == null ? 0 : entity.getId() + 1);
    }

    public Entity getHookedEntity() {
        return this.hookedEntity;
    }

    @Nullable
    public Player getPlayerOwner() {
        Entity entity = this.getOwner();
        return entity instanceof Player player ? player : null;
    }

    @Override
    public void remove(RemovalReason reason) {
        if (!this.level().isClientSide) {
            GrapplingHookManager manager = GrapplingHookManager.get(this.level());
            manager.removeHook(this.getPlayerOwner());
        }
        super.remove(reason);
    }

    @Override
    public boolean canChangeDimensions() {
        return false;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }

    @Override
    public void lerpTo(double pX, double pY, double pZ, float pYRot, float pXRot, int pLerpSteps, boolean pTeleport) {
        // Disable entity moving during ticks
    }

    public void addAdditionalSaveData(CompoundTag nbt) {
        // Disable parent nbt
    }


    public void readAdditionalSaveData(CompoundTag nbt) {
        // Disable parent nbt
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        Entity entity = this.getOwner();
        return new ClientboundAddEntityPacket(this, entity == null ? this.getId() : entity.getId());
    }

    public void recreateFromPacket(ClientboundAddEntityPacket pPacket) {
        super.recreateFromPacket(pPacket);
        if (this.getPlayerOwner() == null) {
            int i = pPacket.getData();
            TelluriumsRandomStuffMod.LOGGER.error("Failed to recreate grappling hook on client. {} (id: {}) is not a valid owner.", this.level().getEntity(i), i);
            this.kill();
        }
    }

    public enum HookState {
        FLYING,
        STUCK_ON_BLOCK,
        HOOKED_IN_ENTITY
    }

}
