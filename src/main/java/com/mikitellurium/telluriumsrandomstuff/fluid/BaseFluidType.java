package com.mikitellurium.telluriumsrandomstuff.fluid;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.function.Consumer;

/**
     * Basic implementation of {@link FluidType} that supports specifying still and flowing textures in the constructor.
     *
     * @author Choonster (<a href="https://github.com/Choonster-Minecraft-Mods/TestMod3/blob/1.19.x/LICENSE.txt">MIT License</a>)
     * <p>
     * Change by: Kaupenjoe
     * Added overlayTexture and tintColor as well. Also converts tint color into fog color
     */
    public class BaseFluidType extends FluidType {

        private final ResourceLocation stillTexture;
        private final ResourceLocation flowingTexture;
        private final ResourceLocation overlayTexture;
        private final ResourceLocation renderOverlayTexture;
        private final int tintColor;
        private float fogStart;
        private float fogEnd;
        private final Vector3f fogColor;

        public BaseFluidType(final ResourceLocation stillTexture, final ResourceLocation flowingTexture, final ResourceLocation overlayTexture,
                             @Nullable final ResourceLocation renderOverlayTexture, final int tintColor, final float fogStart, final float fogEnd,
                             final Vector3f fogColor, final Properties properties) {
            super(properties);
            this.stillTexture = stillTexture;
            this.flowingTexture = flowingTexture;
            this.overlayTexture = overlayTexture;
            this.renderOverlayTexture = renderOverlayTexture;
            this.tintColor = tintColor;
            this.fogStart = fogStart;
            this.fogEnd = fogEnd;
            this.fogColor = fogColor;
        }

        public ResourceLocation getStillTexture() {
            return stillTexture;
        }

        public ResourceLocation getFlowingTexture() {
            return flowingTexture;
        }

        public ResourceLocation getOverlayTexture() {
            return overlayTexture;
        }

        public ResourceLocation getRenderOverlayTexture() {
            return renderOverlayTexture;
        }

        public int getTintColor() {
        return tintColor;
    }

        public Vector3f getFogColor() {
            return fogColor;
        }

        @Override
        public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
            consumer.accept(new IClientFluidTypeExtensions() {
                @Override
                public ResourceLocation getStillTexture() {
                    return stillTexture;
                }

                @Override
                public ResourceLocation getFlowingTexture() {
                    return flowingTexture;
                }

                @Override
                public @Nullable ResourceLocation getOverlayTexture() {
                    return overlayTexture;
                }

                @Override
                public @Nullable ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                    return renderOverlayTexture;
                }

                @Override
                public int getTintColor() {
                    return tintColor;
                }

                @Override
                public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level,
                                                        int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                    return fogColor;
                }
            });
        }

}
