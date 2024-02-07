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
import net.minecraft.world.entity.item.ItemEntity;
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
        if (this.getPlayerOwner() == null) this.discard();
        if (this.isHookedOnEntity()) {
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

        Vec3 movVec = this.getDeltaMovement();
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            double d0 = movVec.horizontalDistance();
            this.setYRot((float)(Mth.atan2(movVec.x, movVec.z) * (double)(180F / (float)Math.PI)));
            this.setXRot((float)(Mth.atan2(movVec.y, d0) * (double)(180F / (float)Math.PI)));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }

        BlockPos pos = this.blockPosition();
        BlockState blockState = this.level().getBlockState(pos);
        if (!blockState.isAir()) {
            VoxelShape shape = blockState.getCollisionShape(this.level(), pos);
            if (!shape.isEmpty()) {
                Vec3 v = this.position();

                for(AABB aabb : shape.toAabbs()) {
                    if (aabb.move(pos).contains(v)) {
                        this.currentState = HookState.STUCK_ON_BLOCK;
                        break;
                    }
                }
            }
        }

        if (this.isInWaterOrRain() || blockState.is(Blocks.POWDER_SNOW) || this.isInFluidType((fluidType, height) -> this.canFluidExtinguish(fluidType))) {
            this.clearFire();
        }

        if (this.hookedEntity != null) {
            this.setDeltaMovement(Vec3.ZERO);
            this.currentState = HookState.HOOKED_IN_ENTITY;
            return;
        }

        if (this.isStuckInBlock()) {
            if (this.lastState != blockState && this.shouldFall()) {
                this.startFalling();
            }
        } else if (this.currentState == HookState.FLYING) {
            Vec3 posVec = this.position();
            Vec3 vec3 = posVec.add(movVec);
            HitResult hitResult = this.level().clip(new ClipContext(posVec, vec3, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
            if (hitResult.getType() != HitResult.Type.MISS) {
                vec3 = hitResult.getLocation();
            }

            EntityHitResult entityHitResult = this.findHitEntity(posVec, vec3);
            if (entityHitResult != null) {
                hitResult = entityHitResult;
            }

            if (hitResult.getType() == HitResult.Type.ENTITY) {
                Entity hitEntity = ((EntityHitResult) hitResult).getEntity();
                Entity owner = this.getOwner();
                if (hitEntity instanceof Player && owner instanceof Player &&
                        !((Player) owner).canHarmPlayer((Player) hitEntity)) {
                    hitResult = null;
                }
            }

            if (hitResult != null && hitResult.getType() != HitResult.Type.MISS) {
                if (!ForgeEventFactory.onProjectileImpact(this, hitResult)) {
                    this.onHit(hitResult);
                    this.hasImpulse = true;
                }
            }

            movVec = this.getDeltaMovement();
            double vecX = movVec.x;
            double vecY = movVec.y;
            double vecZ = movVec.z;

            double newX = this.getX() + vecX;
            double newY = this.getY() + vecY;
            double newZ = this.getZ() + vecZ;
            double distance = movVec.horizontalDistance();

            this.setXRot((float) (Mth.atan2(vecY, distance) * (double) (180F / (float) Math.PI)));
            this.setXRot(lerpRotation(this.xRotO, this.getXRot()));
            this.setYRot(lerpRotation(this.yRotO, this.getYRot()));
            float f = 0.99F;
            if (this.isInWater()) {
                for (int j = 0; j < 4; ++j) {
                    this.level().addParticle(ParticleTypes.BUBBLE, newX - vecX * 0.25D, newY - vecY * 0.25D,
                            newZ - vecZ * 0.25D, vecX, vecY, vecZ);
                }

                f = this.getWaterInertia();
            }

            this.setDeltaMovement(movVec.scale(f));
            if (!this.isNoGravity()) {
                Vec3 vec31 = this.getDeltaMovement();
                this.setDeltaMovement(vec31.x, vec31.y - (double) 0.05F, vec31.z);
            }

            this.setPos(newX, newY, newZ);
            this.checkInsideBlocks();
        }
    }

    public void retrieve() {
        if (this.isHookedOnEntity()) {
            this.pullEntity(this.hookedEntity);
        }
        this.discard();
    }

    private void pullEntity(Entity entity) {
        Entity owner = this.getOwner();
        if (owner != null) {
            double vecX = owner.getX() - this.getX();
            double vecY = owner.getY() - this.getY();
            double vecZ = owner.getZ() - this.getZ();
            Vec3 vec3;
            if (entity instanceof ItemEntity) {
                vec3 = new Vec3(vecX * 0.1D, vecY * 0.1D +
                        Math.sqrt(Math.sqrt(vecX * vecX + vecY * vecY + vecZ * vecZ)) * 0.08D, vecZ * 0.1D);
            } else {
                vec3 = new Vec3(vecX, vecY, vecZ).scale(0.18D);
            }
            entity.setDeltaMovement(vec3);
        }
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
            if (entity.hurt(this.damageSources().playerAttack(player), 0)) {
                if (entity instanceof EnderMan) return;
                this.setHookedEntity(entity);
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

    public boolean isStuckInBlock() {
        return this.currentState == HookState.STUCK_ON_BLOCK;
    }

    public boolean isHookedOnEntity() {
        return this.currentState == HookState.HOOKED_IN_ENTITY;
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

    protected boolean canHitEntity(Entity entity) {
        return super.canHitEntity(entity) || entity.isAlive() && entity instanceof ItemEntity;
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
