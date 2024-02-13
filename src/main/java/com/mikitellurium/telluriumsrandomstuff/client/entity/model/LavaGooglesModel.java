package com.mikitellurium.telluriumsrandomstuff.client.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.Drowned;

public class LavaGooglesModel<T extends LivingEntity> extends AgeableListModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation("minecraft:player"), "lava_googles");
    private final ModelPart googles;
    public float swimAmount;

    public LavaGooglesModel(ModelPart modelPart) {
        super(true, 16.0f, 0.0f);
        this.googles = modelPart.getChild("head");
    }

    public static LayerDefinition createLayerDefinition() {
        return LayerDefinition.create(HumanoidArmorModel.createBodyLayer(
                new CubeDeformation(1.0f)), 64, 32);
    }

    @Override
    public void prepareMobModel(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick) {
        this.swimAmount = pEntity.getSwimAmount(pPartialTick);
        super.prepareMobModel(pEntity, pLimbSwing, pLimbSwingAmount, pPartialTick);
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
        if (entity instanceof ArmorStand armorStand) {
            this.googles.xRot = ((float)Math.PI / 180F) * armorStand.getHeadPose().getX();
            this.googles.yRot = ((float)Math.PI / 180F) * armorStand.getHeadPose().getY();
            this.googles.zRot = ((float)Math.PI / 180F) * armorStand.getHeadPose().getZ();
            return;
        }

        boolean isElytraFlying = entity.getFallFlyingTicks() > 4;
        boolean isSwimming = entity.isVisuallySwimming();
        this.googles.yRot = headYaw * ((float)Math.PI / 180F);
        if (isElytraFlying) {
            this.googles.xRot = (-(float)Math.PI / 4F);
        } else if (this.swimAmount > 0.0F) {
            if (isSwimming) {
                this.googles.xRot = this.rotlerpRad(this.swimAmount, this.googles.xRot, (-(float)Math.PI / 4F));
            } else {
                this.googles.xRot = this.rotlerpRad(this.swimAmount, this.googles.xRot, headPitch * ((float)Math.PI / 180F));
            }

            if (entity instanceof Drowned) this.googles.xRot = 0.0F; // Fix drowned swim animation

        } else {
            this.googles.xRot = headPitch * ((float)Math.PI / 180F);
        }

        if (entity.isCrouching()) {
            this.googles.y = 4.2F;
        } else {
            this.googles.y = 0.0F;
        }
    }

    protected float rotlerpRad(float angle, float maxAngle, float mul) {
        float f = (mul - maxAngle) % ((float)Math.PI * 2F);
        if (f < -(float)Math.PI) {
            f += ((float)Math.PI * 2F);
        }

        if (f >= (float)Math.PI) {
            f -= ((float)Math.PI * 2F);
        }

        return maxAngle + angle * f;
    }

}
