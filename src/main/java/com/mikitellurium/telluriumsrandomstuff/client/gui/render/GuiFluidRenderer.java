package com.mikitellurium.telluriumsrandomstuff.client.gui.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;

/* CREDIT to KingLemming and the Team CoFH for this class.
 * This class is made from copy-pasted code from the CofhCore
 * mod, I just modified some stuff I needed.
 *
 * LICENSE : https://github.com/CoFH/CoFHCore#readme
 */
public class GuiFluidRenderer {

    public static void drawBackground(int xPos, int yPos, FluidStack fluidStack, int capacity, int width, int height) {
        int resourceHeight = height - 2;
        int resourceWidth = width - 2;
        int amount = getScaled(fluidStack, resourceHeight, capacity);
        drawFluid(xPos + 1, yPos + 1 + (resourceHeight - amount), fluidStack, resourceWidth, amount);
    }

    public static void drawFluid(int x, int y, FluidStack fluid, int width, int height) {
        if (!fluid.isEmpty()) {
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            int color = color(fluid);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, new ResourceLocation("textures/atlas/blocks.png"));
            setShaderColorFromInt(color);
            drawTiledTexture(x, y, getTexture(IClientFluidTypeExtensions.of(fluid.getFluid()).getStillTexture(fluid)), width, height);
        }
    }

    public static void drawTiledTexture(int x, int y, TextureAtlasSprite icon, int width, int height) {
        for(int i = 0; i < width; i += 16) {
            for(int j = 0; j < height; j += 16) {
                int drawWidth = Math.min(width - i, 16);
                int drawHeight = Math.min(height - j, 16);
                drawScaledTexturedModalRectFromSprite(x + i, y + j, icon, drawWidth, drawHeight);
            }
        }

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void drawScaledTexturedModalRectFromSprite(int x, int y, TextureAtlasSprite icon, int width, int height) {
        if (icon != null) {
            float minU = icon.getU0();
            float maxU = icon.getU1();
            float minV = icon.getV0();
            float maxV = icon.getV1();
            float u = minU + (maxU - minU) * (float)width / 16.0F;
            float v = minV + (maxV - minV) * (float)height / 16.0F;

            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder buffer = tesselator.getBuilder();
            buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            buffer.vertex(x, y + height, 0.0).uv(minU, v).endVertex();
            buffer.vertex(x + width, y + height, 0.0).uv(u, v).endVertex();
            buffer.vertex(x + width, y, 0.0).uv(u, minV).endVertex();
            buffer.vertex(x, y, 0.0).uv(minU, minV).endVertex();
            tesselator.end();
        }
    }

    public static int color(FluidStack stack) {
        return !stack.isEmpty() && stack.getFluid() != null ? IClientFluidTypeExtensions.of(stack.getFluid()).getTintColor(stack) : 0;
    }

    public static void setShaderColorFromInt(int color) {
        float red = (float)(color >> 16 & 255) / 255.0F;
        float green = (float)(color >> 8 & 255) / 255.0F;
        float blue = (float)(color & 255) / 255.0F;
        RenderSystem.setShaderColor(red, green, blue, 1.0F);
    }

    public static TextureAtlasSprite getTexture(ResourceLocation location) {
        return Minecraft.getInstance().getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS).getSprite(location);
    }

    protected static int getScaled(FluidStack fluidStack, int scale, int capacity) {
        double fraction = (double)fluidStack.getAmount() * (double)scale / (double)capacity;
        int amount = Mth.clamp(round(fraction), 0, scale);
        return fraction > 0.0 ? Math.max(1, amount) : amount;
    }

    public static int round(double d) {
        return (int)(d + 0.5);
    }

}
