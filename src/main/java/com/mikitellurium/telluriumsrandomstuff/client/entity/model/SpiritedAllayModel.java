package com.mikitellurium.telluriumsrandomstuff.client.entity.model;

import com.mikitellurium.telluriumsrandomstuff.common.entity.SpiritedAllay;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

public class SpiritedAllayModel extends HierarchicalModel<SpiritedAllay> implements ArmedModel {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(FastLoc.modLoc("spirited_allay"), "main");
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart right_arm;
    private final ModelPart left_arm;
    private final ModelPart right_wing;
    private final ModelPart left_wing;
    private static final float FLYING_ANIMATION_X_ROT = ((float)Math.PI / 4F);
    private static final float MAX_HAND_HOLDING_ITEM_X_ROT_RAD = -1.134464F;
    private static final float MIN_HAND_HOLDING_ITEM_X_ROT_RAD = (-(float)Math.PI / 3F);
    private float red = 1.0F;
    private float green = 1.0F;
    private float blue = 1.0F;

    public SpiritedAllayModel(ModelPart pRoot) {
        super(RenderType::entityTranslucent);
        this.root = pRoot.getChild("root");
        this.head = this.root.getChild("head");
        this.body = this.root.getChild("body");
        this.right_arm = this.body.getChild("right_arm");
        this.left_arm = this.body.getChild("left_arm");
        this.right_wing = this.body.getChild("right_wing");
        this.left_wing = this.body.getChild("left_wing");
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    public static LayerDefinition createLayerDefinition() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition partDefinition1 = partDefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 23.5F, 0.0F));
        partDefinition1.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.99F, 0.0F));
        PartDefinition partDefinition2 = partDefinition1.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 16).addBox(-1.5F, 0.0F, -1.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, -4.0F, 0.0F));
        partDefinition2.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(23, 0).addBox(-0.75F, -0.5F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(-0.01F)), PartPose.offset(-1.75F, 0.5F, 0.0F));
        partDefinition2.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(23, 6).addBox(-0.25F, -0.5F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(-0.01F)), PartPose.offset(1.75F, 0.5F, 0.0F));
        partDefinition2.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(16, 14).addBox(0.0F, 1.0F, 0.0F, 0.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 0.0F, 0.6F));
        partDefinition2.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(16, 14).addBox(0.0F, 1.0F, 0.0F, 0.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 0.0F, 0.6F));
        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    public void setColor(float[] rgb) {
        this.setColor(rgb[0], rgb[1], rgb[2]);
    }

    public void setColor(float pR, float pG, float pB) {
        this.red = pR;
        this.green = pG;
        this.blue = pB;
    }

    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
        super.renderToBuffer(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, this.red * pRed, this.green * pGreen, this.blue * pBlue, pAlpha);
    }

    @Override
    public void setupAnim(SpiritedAllay pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float f = pAgeInTicks * 20.0F * ((float)Math.PI / 180F) + pLimbSwing;
        float f1 = Mth.cos(f) * (float)Math.PI * 0.15F + pLimbSwingAmount;
        float f2 = pAgeInTicks - (float)pEntity.tickCount;
        float f3 = pAgeInTicks * 9.0F * ((float)Math.PI / 180F);
        float f4 = Math.min(pLimbSwingAmount / 0.3F, 1.0F);
        float f5 = 1.0F - f4;
        float f6 = pEntity.getHoldingItemAnimationProgress(f2);
//        if (pEntity.isDancing()) {
//            float f7 = pAgeInTicks * 8.0F * ((float)Math.PI / 180F) + pLimbSwingAmount;
//            float f8 = Mth.cos(f7) * 16.0F * ((float)Math.PI / 180F);
//            float f9 = pEntity.getSpinningProgress(f2);
//            float f10 = Mth.cos(f7) * 14.0F * ((float)Math.PI / 180F);
//            float f11 = Mth.cos(f7) * 30.0F * ((float)Math.PI / 180F);
//            this.root.yRot = pEntity.isSpinning() ? 12.566371F * f9 : this.root.yRot;
//            this.root.zRot = f8 * (1.0F - f9);
//            this.head.yRot = f11 * (1.0F - f9);
//            this.head.zRot = f10 * (1.0F - f9);
//        } else {
//            this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
//            this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
//        }

        this.right_wing.xRot = 0.43633232F * (1.0F - f4);
        this.right_wing.yRot = (-(float)Math.PI / 4F) + f1;
        this.left_wing.xRot = 0.43633232F * (1.0F - f4);
        this.left_wing.yRot = ((float)Math.PI / 4F) - f1;
        this.body.xRot = f4 * ((float)Math.PI / 4F);
        float f12 = f6 * Mth.lerp(f4, (-(float)Math.PI / 3F), -1.134464F);
        this.root.y += (float)Math.cos((double)f3) * 0.25F * f5;
        this.right_arm.xRot = f12;
        this.left_arm.xRot = f12;
        float f13 = f5 * (1.0F - f6);
        float f14 = 0.43633232F - Mth.cos(f3 + ((float)Math.PI * 1.5F)) * (float)Math.PI * 0.075F * f13;
        this.left_arm.zRot = -f14;
        this.right_arm.zRot = f14;
        this.right_arm.yRot = 0.27925268F * f6;
        this.left_arm.yRot = -0.27925268F * f6;
    }

    public void translateToHand(HumanoidArm side, PoseStack poseStack) {
        float f = 1.0F;
        float f1 = 3.0F;
        this.root.translateAndRotate(poseStack);
        this.body.translateAndRotate(poseStack);
        poseStack.translate(0.0F, 0.0625F, 0.1875F);
        poseStack.mulPose(Axis.XP.rotation(this.right_arm.xRot));
        poseStack.scale(0.7F, 0.7F, 0.7F);
        poseStack.translate(0.0625F, 0.0F, 0.0F);
    }

}
