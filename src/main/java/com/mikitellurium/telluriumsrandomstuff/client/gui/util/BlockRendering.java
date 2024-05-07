package com.mikitellurium.telluriumsrandomstuff.client.gui.util;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.fluids.FluidStack;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public final class BlockRendering {

    public static void renderBlock(GuiGraphics graphics, BlockState blockState, float xPos, float yPos, int scale) {
        BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
        PoseStack poseStack = graphics.pose();
        BakedModel model = blockRenderer.getBlockModel(blockState);
        RenderType renderType = ItemBlockRenderTypes.getRenderType(blockState, false);
        boolean blockLight = !model.usesBlockLight();

        poseStack.pushPose();

        poseStack.translate(xPos, yPos, 7);
        poseStack.mulPoseMatrix(new Matrix4f().scaling(1, -1, 1));
        poseStack.scale(scale, scale, scale);
        poseStack.mulPose(new Quaternionf().rotationXYZ(30 * ((float)Math.PI / 180), 225 * ((float)Math.PI / 180), 0 * ((float)Math.PI / 180)));
        poseStack.translate(-0.5f, -0.5f, -0.5f);

        if (blockLight) Lighting.setupForFlatItems();
        blockRenderer.renderSingleBlock(blockState, poseStack, graphics.bufferSource(),
                LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, renderType);

        graphics.flush();
        if (blockLight) Lighting.setupFor3DItems();

        poseStack.popPose();
    }

    public static void renderFluid(GuiGraphics guiGraphics, FluidStack fluidStack) {
        renderFluid(guiGraphics, fluidStack.getFluid());
    }

    public static void renderFluid(GuiGraphics guiGraphics, Fluid fluid) {
        renderFluid(guiGraphics, fluid, 0, 0);
    }

    public static void renderFluid(GuiGraphics guiGraphics, FluidStack fluidStack, float xPos, float yPos) {
        renderFluid(guiGraphics, fluidStack.getFluid(), xPos, yPos);
    }

    public static void renderFluid(GuiGraphics guiGraphics, Fluid fluid, float xPos, float yPos) {
        PoseStack poseStack = guiGraphics.pose();
        Minecraft minecraft = Minecraft.getInstance();
        BlockRenderDispatcher blockRenderDispatcher = minecraft.getBlockRenderer();
        FluidState fluidState = fluid.defaultFluidState();
        RenderType renderType = ItemBlockRenderTypes.getRenderLayer(fluidState);

        poseStack.pushPose();
        // todo check if JEI render correctly
        poseStack.translate(xPos, yPos, 0.0F);
        poseStack.translate(15.0F, 11.33F, 10.0F);
        poseStack.scale(-9.9F, -11.0F, -9.9F);
        poseStack.mulPose(Axis.XP.rotationDegrees(-30.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(45.0F));
        PoseStack worldStack = RenderSystem.getModelViewStack();

        renderType.setupRenderState();

        worldStack.pushPose();
        worldStack.mulPoseMatrix(poseStack.last().pose());
        RenderSystem.applyModelViewMatrix();

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder builder = tesselator.getBuilder();
        builder.begin(renderType.mode(), renderType.format());
        blockRenderDispatcher.renderLiquid(BlockPos.ZERO, new FakeWorld(fluidState), builder, fluidState.createLegacyBlock(),
                fluidState);
        if (builder.building()) {
            tesselator.end();
        }

        renderType.clearRenderState();
        worldStack.popPose();


        poseStack.popPose();
        RenderSystem.applyModelViewMatrix();
    }

    private static class FakeWorld implements BlockAndTintGetter {
        private final FluidState fluidState;

        public FakeWorld(FluidState fluidState) {
            this.fluidState = fluidState;
        }

        @Override
        public float getShade(Direction direction, boolean bl) {
            return 1.0f;
        }

        @Override
        public LevelLightEngine getLightEngine() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getBrightness(LightLayer lightLayer, BlockPos blockPos) {
            return 15;
        }

        @Override
        public int getRawBrightness(BlockPos blockPos, int i) {
            return 15;
        }

        @Override
        public int getBlockTint(BlockPos blockPos, ColorResolver colorResolver) {
            var level = Minecraft.getInstance().level;
            if (level != null) {
                var biome = Minecraft.getInstance().level.getBiome(blockPos);
                return colorResolver.getColor(biome.value(), blockPos.getX(), blockPos.getZ());
            } else {
                return -1;
            }
        }

        @Override
        public BlockEntity getBlockEntity(BlockPos blockPos) {
            return null;
        }

        @Override
        public BlockState getBlockState(BlockPos blockPos) {
            if (blockPos.equals(BlockPos.ZERO)) {
                return this.fluidState.createLegacyBlock();
            } else {
                return Blocks.AIR.defaultBlockState();
            }
        }

        @Override
        public FluidState getFluidState(BlockPos blockPos) {
            if (blockPos.equals(BlockPos.ZERO)) {
                return this.fluidState;
            } else {
                return Fluids.EMPTY.defaultFluidState();
            }
        }

        @Override
        public int getHeight() {
            return 0;
        }

        @Override
        public int getMinBuildHeight() {
            return 0;
        }
    }

}

