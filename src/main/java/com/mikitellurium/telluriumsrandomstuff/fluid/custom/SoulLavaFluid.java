package com.mikitellurium.telluriumsrandomstuff.fluid.custom;

import com.mikitellurium.telluriumsrandomstuff.block.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.fluid.ModFluidTypes;
import com.mikitellurium.telluriumsrandomstuff.fluid.ModFluids;
import com.mikitellurium.telluriumsrandomstuff.item.ModItems;
import com.mikitellurium.telluriumsrandomstuff.particle.ModParticles;
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
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import org.jetbrains.annotations.Nullable;

public class SoulLavaFluid extends ForgeFlowingFluid {

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

    public static boolean applyMovementLogic(LivingEntity entity, Vec3 vec3, double gravity) {
        boolean flag = entity.getDeltaMovement().y <= 0.0D;;
        double d8 = entity.getY();
        entity.moveRelative(0.02F, vec3);
        entity.move(MoverType.SELF, entity.getDeltaMovement());
        if (entity.getFluidHeight(FluidTags.LAVA) <= entity.getFluidJumpThreshold()) {
            entity.setDeltaMovement(entity.getDeltaMovement().multiply(0.5D, (double)0.8F, 0.5D));
            Vec3 vec33 = entity.getFluidFallingAdjustedMovement(gravity, flag, entity.getDeltaMovement());
            entity.setDeltaMovement(vec33);
        } else {
            entity.setDeltaMovement(entity.getDeltaMovement().scale(0.5D));
        }

        if (!entity.isNoGravity()) {
            entity.setDeltaMovement(entity.getDeltaMovement().add(0.0D, - gravity / 4.0D, 0.0D));
        }

        Vec3 vec34 = entity.getDeltaMovement();
        if (entity.horizontalCollision && entity.isFree(vec34.x, vec34.y + (double)0.6F - entity.getY() + d8, vec34.z)) {
            entity.setDeltaMovement(vec34.x, (double)0.3F, vec34.z);
        }

        return true;
    }

    public static void soulLavaHurt(LivingEntity entity) {
        entity.lavaHurt();
//        if (entity.fireImmune()) {
//            return false;
//        } else {
//            entity.setSecondsOnFire(15);
//            if (entity.hurt(entity.damageSources().lava(), 4.0F)) {
//                entity.playSound(SoundEvents.GENERIC_BURN, 0.4F, 2.0F + entity.getRandom().nextFloat() * 0.4F);
//                return true;
//            }
//        }
//
//        return false;
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
            ParticleUtils.handleRainParticles(pLevel, pPos, pState, pRandom);
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
        return (pos.getY() < level.getMinBuildHeight() || pos.getY() >= level.getMaxBuildHeight() || level.hasChunkAt(pos)) && level.getBlockState(pos).isFlammable(level, pos, face);
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
        return pFluidState.getHeight(pBlockReader, pPos) >= 0.44444445F && (pFluid.is(FluidTags.WATER) || pFluid.is(FluidTags.LAVA));
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
