package com.mikitellurium.telluriumsrandomstuff.client.render;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;

public class LavaGooglesModel<T extends LivingEntity> extends AgeableListModel<T> {

    public static final ModelLayerLocation LAVA_GOOGLES = new ModelLayerLocation(
            new ResourceLocation("minecraft:player"), "lava_googles");
    private final ModelPart googles;

    public LavaGooglesModel(ModelPart modelPart) {
        super(true, 16.0f, 0.0f);
        this.googles = modelPart.getChild("head");
    }

    public static LayerDefinition createLayerDefinition() {
        return LayerDefinition.create(HumanoidArmorModel.createBodyLayer(
                new CubeDeformation(1.0f)), 64, 32);
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(googles);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of();
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw,
                          float headPitch) {
        boolean isArmorStand = entity instanceof ArmorStand;
        boolean isElytraFliyng = entity.getFallFlyingTicks() > 4;
        boolean isSwimming = entity.getSwimAmount(ageInTicks) > 0.0f && entity.isVisuallySwimming();

        if (!isArmorStand) { // Googles always point south on armor stand otherwise
            this.googles.yRot = headYaw * ((float) Math.PI / 180F);
        }
        if (isElytraFliyng || isSwimming) {
            this.googles.xRot = (-(float) Math.PI / 4F);
        } else {
            this.googles.xRot = headPitch * ((float) Math.PI / 180F);
        }

        if (entity.isCrouching()) {
            this.googles.y = 4.2F;
        } else {
            this.googles.y = 0.0F;
        }
    }

}
