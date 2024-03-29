package com.mikitellurium.telluriumsrandomstuff.common.fluid;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.registry.*;
import com.mikitellurium.telluriumsrandomstuff.util.LogUtils;
import com.mikitellurium.telluriumsrandomstuff.util.ParticleUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

public class SoulLavaFluid extends ForgeFlowingFluid {

    private static final Field jumping = ObfuscationReflectionHelper.findField(LivingEntity.class, "f_20899_");

    public SoulLavaFluid(Properties properties) {
        super(properties);
    }

    @Override
    public Fluid getFlowing() {
        return ModFluids.SOUL_LAVA_FLOWING.get();
    }

    @Override
    public Fluid getSource() {
        return ModFluids.SOUL_LAVA_SOURCE.get();
    }

    @Override
    public boolean canConvertToSource(FluidState state, Level level, BlockPos pos) {
        return level.getGameRules().getBoolean(GameRules.RULE_LAVA_SOURCE_CONVERSION);
    }

    @Override
    public FluidType getFluidType() {
        return ModFluidTypes.SOUL_LAVA_FLUID_TYPE.get();
    }

    public boolean applyMovementLogic(LivingEntity entity, Vec3 vec3, double gravity) {
        boolean flag = entity.getDeltaMovement().y <= 0.0D;
        double dY = entity.getY();

        if ((entity instanceof Mob mob && mob.getNavigation().canFloat())) {
            this.floatMob(entity);
        }

        float speed = 0.02F;
        int soulSpeedLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.SOUL_SPEED, entity);
        if (soulSpeedLevel > 0) {
            speed = speed + (soulSpeedLevel * 0.055f); // Apply soul speed multiplier
            try {
                Vec3 vec32 = this.getSoulSpeedVerticalSpeedAdjustedMovement(entity, entity.getDeltaMovement(), soulSpeedLevel);
                entity.setDeltaMovement(vec32);
            } catch (IllegalAccessException e) {
                TelluriumsRandomStuffMod.LOGGER.error("Could not apply soul lava movement logic to entity " + entity.getName());
            }
        }

        entity.moveRelative(speed, vec3);
        entity.move(MoverType.SELF, entity.getDeltaMovement());

        if (entity.getFluidTypeHeight(ModFluidTypes.SOUL_LAVA_FLUID_TYPE.get()) <= entity.getFluidJumpThreshold()) {
            entity.setDeltaMovement(entity.getDeltaMovement().multiply(0.5D, 0.8F, 0.5D));
            Vec3 fluidVec3 = entity.getFluidFallingAdjustedMovement(gravity, flag, entity.getDeltaMovement());
            entity.setDeltaMovement(fluidVec3);
        } else {
            entity.setDeltaMovement(entity.getDeltaMovement().scale(0.5D));
        }

        if (!entity.isNoGravity()) {
            entity.setDeltaMovement(entity.getDeltaMovement().add(0.0D, - gravity / 4.0D, 0.0D));
        }

        Vec3 entityVec3 = entity.getDeltaMovement();
        if (entity.horizontalCollision && entity.isFree(entityVec3.x, entityVec3.y + (double)0.6F - entity.getY() + dY, entityVec3.z)) {
            entity.setDeltaMovement(entityVec3.x, 0.3F, entityVec3.z);
        }

        return true;
    }

    private Vec3 getSoulSpeedVerticalSpeedAdjustedMovement(LivingEntity entity, Vec3 vec3, int soulSpeed) throws IllegalAccessException {
            if (!entity.isNoGravity() && !entity.isSprinting()) {
                double verticalSpeed = vec3.y;
                if (entity.isCrouching()) {
                    verticalSpeed = -0.08D * soulSpeed;
                } else if (jumping.getBoolean(entity)) {
                    verticalSpeed = 0.06D * soulSpeed;
                }

                return new Vec3(vec3.x, verticalSpeed, vec3.z);
            } else {
                return vec3;
            }
    }

    private void floatMob(LivingEntity entity) {
        if (entity instanceof MagmaCube magmaCube) {
            Vec3 vec3 = magmaCube.getDeltaMovement();
            magmaCube.setDeltaMovement(vec3.x, 0.22F + (float)magmaCube.getSize() * 0.05F, vec3.z);
            entity.hasImpulse = true;
        } else {
            entity.jumpInFluid(ModFluidTypes.SOUL_LAVA_FLUID_TYPE.get());
        }
    }

    public static void hurt(Entity entity) {
        if (!entity.getType().is(ModTags.EntityTypes.SOUL_LAVA_IMMUNE) && !entity.fireImmune()) {
            entity.setSecondsOnFire(15);
            if (entity.hurt(entity.damageSources().lava(), 4.0F)) {
                entity.playSound(SoundEvents.GENERIC_BURN, 0.4F,
                        2.0F + entity.level().getRandom().nextFloat() * 0.4F);
            }
        }
    }

    public static boolean isEntityInSoulLava(Entity entity) {
        return entity.isInFluidType((fluid, d) -> fluid == ModFluidTypes.SOUL_LAVA_FLUID_TYPE.get() && d > 0.0D, false);
    }

    @Override
    protected void animateTick(Level pLevel, BlockPos pPos, FluidState pState, RandomSource pRandom) {
        BlockPos blockpos = pPos.above();
        if (pLevel.getBlockState(blockpos).isAir() && !pLevel.getBlockState(blockpos).isSolidRender(pLevel, blockpos)) {
            if (pRandom.nextInt(100) == 0) {
                double pX = pPos.getX() + pRandom.nextDouble();
                double pY = pPos.above().getY() + (pRandom.nextDouble() * 0.25d);
                double pZ = pPos.getZ() + pRandom.nextDouble();
                pLevel.addParticle(ParticleTypes.SOUL, pX, pY, pZ, 0.0D, 0.0D, 0.0D);
                SoundEvent sound = pRandom.nextInt(3) == 0 ? SoundEvents.SOUL_ESCAPE : SoundEvents.LAVA_POP;
                pLevel.playLocalSound(pX, pY, pZ, sound, SoundSource.BLOCKS, 0.2F + pRandom.nextFloat() * 0.2F, 0.9F + pRandom.nextFloat() * 0.15F, false);
            }

            if (pRandom.nextInt(200) == 0) {
                pLevel.playLocalSound(pPos.getX(), pPos.getY(), pPos.getZ(), SoundEvents.LAVA_AMBIENT, SoundSource.BLOCKS, 0.2F + pRandom.nextFloat() * 0.2F, 0.9F + pRandom.nextFloat() * 0.15F, false);
            }
        }

        if (pLevel.isRaining()) {
            ParticleUtils.spawnRainParticles(pLevel, pPos, pState, pRandom);
        }

    }

    @Override
    protected void randomTick(Level pLevel, BlockPos pPos, FluidState pState, RandomSource pRandom) {
        if (pLevel.getGameRules().getBoolean(GameRules.RULE_DOFIRETICK)) {
            int i = pRandom.nextInt(3);
            if (i > 0) {
                BlockPos blockpos = pPos;

                for(int j = 0; j < i; ++j) {
                    blockpos = blockpos.offset(pRandom.nextInt(3) - 1, 1, pRandom.nextInt(3) - 1);
                    if (!pLevel.isLoaded(blockpos)) {
                        return;
                    }

                    BlockState blockstate = pLevel.getBlockState(blockpos);
                    if (blockstate.isAir()) {
                        if (this.hasFlammableNeighbours(pLevel, blockpos)) {
                            pLevel.setBlockAndUpdate(blockpos, ForgeEventFactory.fireFluidPlaceBlockEvent(pLevel, blockpos, pPos, Blocks.FIRE.defaultBlockState()));
                            return;
                        }
                    } else if (blockstate.blocksMotion()) {
                        return;
                    }
                }
            } else {
                for(int k = 0; k < 3; ++k) {
                    BlockPos blockpos1 = pPos.offset(pRandom.nextInt(3) - 1, 0, pRandom.nextInt(3) - 1);
                    if (!pLevel.isLoaded(blockpos1)) {
                        return;
                    }

                    if (pLevel.isEmptyBlock(blockpos1.above()) && this.isFlammable(pLevel, blockpos1, Direction.UP)) {
                        pLevel.setBlockAndUpdate(blockpos1.above(), ForgeEventFactory.fireFluidPlaceBlockEvent(pLevel, blockpos1.above(), pPos, Blocks.FIRE.defaultBlockState()));
                    }
                }
            }

        }
    }

    private boolean hasFlammableNeighbours(LevelReader pLevel, BlockPos pPos) {
        for(Direction direction : Direction.values()) {
            if (this.isFlammable(pLevel, pPos.relative(direction), direction.getOpposite())) {
                return true;
            }
        }

        return false;
    }

    private boolean isFlammable(LevelReader level, BlockPos pos, Direction face) {
        return (pos.getY() < level.getMinBuildHeight() || pos.getY() >= level.getMaxBuildHeight() ||
                level.hasChunkAt(pos)) && level.getBlockState(pos).isFlammable(level, pos, face);
    }

    @Nullable
    @Override
    protected ParticleOptions getDripParticle() {
        return ModParticles.SOUL_LAVA_HANG.get();
    }

    @Override
    protected void beforeDestroyingBlock(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        this.fizz(pLevel, pPos);
    }

    private void fizz(LevelAccessor pLevel, BlockPos pPos) {
        pLevel.levelEvent(1501, pPos, 0);
    }

    @Override
    public int getTickDelay(LevelReader level) {
        return level.dimensionType().ultraWarm() ? 10 : 30;
    }

    @Override
    protected int getSlopeFindDistance(LevelReader level) {
        return level.dimensionType().ultraWarm() ? 4 : 2;
    }

    @Override
    protected int getDropOff(LevelReader level) {
        return level.dimensionType().ultraWarm() ? 1 : 2;
    }

    @Override
    public Item getBucket() {
        return ModItems.SOUL_LAVA_BUCKET.get();
    }

    @Override
    public BlockState createLegacyBlock(FluidState pState) {
        return ModBlocks.SOUL_LAVA_BLOCK.get().defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(pState));
    }

    @Override
    public boolean isSame(Fluid fluidIn) {
        return fluidIn == ModFluids.SOUL_LAVA_SOURCE.get() || fluidIn == ModFluids.SOUL_LAVA_FLOWING.get();
    }

    @Override
    public boolean canBeReplacedWith(FluidState pFluidState, BlockGetter pBlockReader, BlockPos pPos, Fluid pFluid, Direction pDirection) {
        return pFluidState.getHeight(pBlockReader, pPos) >= 0.44444445F && (pFluid.is(FluidTags.WATER));
    }

    @Override
    protected boolean isRandomlyTicking() {
        return true;
    }

    @Override
    public int getAmount(FluidState pState) {
        return this.isSource(pState) ? 8 : pState.getValue(LEVEL);
    }

    @Override
    public boolean isSource(FluidState pState) {
        return this instanceof Source;
    }

    public static class Flowing extends SoulLavaFluid {

        public Flowing(Properties properties) {
            super(properties);
        }

        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> pBuilder) {
            super.createFluidStateDefinition(pBuilder);
            pBuilder.add(LEVEL);
        }

        @Override
        public boolean isSource(FluidState pState) {
            return false;
        }

        @Override
        public int getAmount(FluidState pState) {
            return pState.getValue(LEVEL);
        }

    }

    public static class Source extends SoulLavaFluid {

        public Source(Properties properties) {
            super(properties);
        }

        public int getAmount(FluidState pState) {
            return 8;
        }

        public boolean isSource(FluidState pState) {
            return true;
        }

    }

}
