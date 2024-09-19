package com.mikitellurium.telluriumsrandomstuff.client.item;

import com.mikitellurium.telluriumsrandomstuff.common.capability.SoulStorage;
import com.mikitellurium.telluriumsrandomstuff.common.event.RenderingEvents;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraftforge.registries.ForgeRegistries;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import java.util.ArrayList;
import java.util.List;

import static com.mikitellurium.telluriumsrandomstuff.test.KeyEvents.*;

public class SoulStorageClientTooltip implements ClientTooltipComponent {

    private static final Quaternionf DEFAULT_ROT = new Quaternionf().rotationXYZ(3.05F, 5.4F, 0.2F);
    private final SoulStorage soulStorage;
    private final List<EntityType<?>> entities;
    private final int stackCount;

    public SoulStorageClientTooltip(SoulStorageTooltip tooltip) {
        this.soulStorage = SoulStorage.copyFrom(tooltip.itemStack());
        this.entities = this.getEntities();
        this.stackCount = tooltip.itemStack().getCount();
    }

    private List<EntityType<?>> getEntities() {
        List<EntityType<?>> types = Util.make(new ArrayList<>(), (list) -> this.soulStorage.forEach((key, count) -> {
            ResourceLocation id = ResourceLocation.tryParse(key);
            if (ForgeRegistries.ENTITY_TYPES.containsKey(id)) {
                EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(id);
                list.add(entityType);
            }
        }));
        return List.copyOf(types);
    }

    @Override
    public int getHeight() {
        return this.entities.isEmpty() ? 0 : 25 * Mth.ceil((float) this.entities.size() / 8);
    }

    @Override
    public int getWidth(Font font) {
        return 220;
    }

    @Override
    public void renderText(Font font, int mouseX, int mouseY, Matrix4f matrix4f, MultiBufferSource.BufferSource bufferSource) {
    }

    @Override
    public void renderImage(Font font, int xPos, int yPos, GuiGraphics graphics) {
        if (!this.entities.isEmpty()) {
            int xStartPos = xPos + 14;
            int yStartPos = yPos + 16;
            int row = 0;
            int index = 0;
            int offset = 0;
            for (EntityType<?> entityType : this.entities) {
                EntityDimensions dimensions = entityType.getDimensions();
                float f;
                if (dimensions.width > dimensions.height) { // Handle mob that are more wide than tall
                    f = dimensions.height;
                } else {
                    f = dimensions.width * dimensions.height;
                }
                float scale = Mth.invSqrt(f) * 9.0F;
                int width = Mth.floor(dimensions.width);
                float h = (32 * index) + width + (offset * 2);
                if (h + dimensions.width >= this.getWidth(font) - 15) {
                    offset = width;
                    index = 0;
                    row++;
                    h = width;
                } else {
                    offset += width;
                }
                int height = Mth.floor(dimensions.height);
                float v = height <= 0 ? dimensions.height * -3.0F : 0;
                int rowY = 25 * row;
                float finalX = xStartPos + h;
                float finalY = yStartPos + rowY + v;
                this.renderEntity(graphics, entityType, finalX, finalY, scale);
                int amount = this.soulStorage.getCount(EntityType.getKey(entityType)) * this.stackCount;
                Component text = Component.literal("x" + amount);
                float textOffset = (float) font.width(text) / 2;
                this.renderText(font, graphics, text, finalX - textOffset - 1.0F, finalY - v - 2.0F);
                index++;
            }
        }
    }
    // todo mob client manager
    @SuppressWarnings("deprecation")
    private void renderEntity(GuiGraphics graphics, EntityType<?> entityType, double x, double y, float scale) {
        ClientLevel level = Minecraft.getInstance().level;
        Entity entity;
        if (entityType == EntityType.PLAYER) {
            entity = Minecraft.getInstance().player;
        } else {
            entity = entityType.create(level);
        }
        PoseStack poseStack = graphics.pose();
        poseStack.pushPose();
        poseStack.translate(x, y, 50.0D);
        poseStack.mulPoseMatrix(new Matrix4f().scaling(scale, scale, -scale));
        poseStack.mulPose(DEFAULT_ROT);
        if (entity instanceof EnderDragon) { // Flip ender dragon model
            poseStack.mulPose(Axis.YP.rotationDegrees(180));
        }
        poseStack.mulPose(Axis.YN.rotation(RenderingEvents.rotation));
        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher entityRenderer = Minecraft.getInstance().getEntityRenderDispatcher();
        entityRenderer.setRenderShadow(false);
        RenderSystem.runAsFancy(() -> {
            entityRenderer.render(entity, 0, 0, 0, 0, 0, poseStack, graphics.bufferSource(), 15728880);
        });
        graphics.flush();
        entityRenderer.setRenderShadow(true);
        poseStack.popPose();
        Lighting.setupFor3DItems();
    }

    private void renderText(Font font, GuiGraphics graphics, Component text, float x, float y) {
        PoseStack poseStack = graphics.pose();
        poseStack.pushPose();
        poseStack.translate(0, 0, 100);
        graphics.drawString(font, text.getVisualOrderText(), x, y, 0xF3F3F3, true);
        graphics.flush();
        poseStack.popPose();
    }

}
