package com.mikitellurium.telluriumsrandomstuff.client.item;

import com.mikitellurium.telluriumsrandomstuff.util.LogUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

public class GrapplingHookItemExtension implements IClientItemExtensions {

    private final HumanoidModel.ArmPose GRAPPLING_HOOK = HumanoidModel.ArmPose.create("GRAPPLING_HOOK", false,
            (model, entity, arm) -> {
                ItemStack itemInHand = arm == entity.getMainArm() ? entity.getMainHandItem() : entity.getOffhandItem();
                if (entity.isUsingItem() && entity.getUseItem() == itemInHand) {
                    ModelPart modelArm = arm == HumanoidArm.RIGHT ? model.rightArm : model.leftArm;
                    modelArm.xRot = modelArm.xRot * 0.5F - (float) Math.PI;
                    modelArm.yRot = 0.0F;
                }
            });

    @Nullable
    @Override
    public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
        return GRAPPLING_HOOK;
    }

    @Override
    public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand,
                                           float partialTick, float equipProcess, float swingProcess) {
        if (player.isUsingItem() && player.getUseItem() == itemInHand) {
            float side = arm == HumanoidArm.RIGHT ? 1 : -1;
            poseStack.translate(side * 0.56F, -0.52F + equipProcess * -0.6F, -0.72F);
            poseStack.translate(side * -0.2F, 0.6F, 0.35F);
            poseStack.mulPose(Axis.XP.rotationDegrees(-72.0F));
            poseStack.mulPose(Axis.YP.rotationDegrees(side * 35.0F));
            poseStack.mulPose(Axis.ZP.rotationDegrees(side * -6.0F));
            float remainingUseTicks = (float) itemInHand.getUseDuration() - ((float) player.getUseItemRemainingTicks() - partialTick + 1.0F);
            float progress = Math.min(remainingUseTicks / this.getChargeDuration(itemInHand), 1.0F);

            if (progress > 0.1F) {
                float sin = Mth.sin((remainingUseTicks - 0.1F) * 1.2F);
                float f = progress - 0.1F;
                float f1 = sin * f;
                poseStack.translate(f1 * 0.0F, f1 * 0.004F, f1 * 0.0F);
            }

            poseStack.translate(0.0F, progress * -0.2F, 0.0F);
            poseStack.scale(1.0F, 1.0F + progress * 0.15F, 1.0F);
            poseStack.mulPose(Axis.YN.rotationDegrees(side * 45.0F));
            return true;
        }
        return false;
    }

    private float getChargeDuration(ItemStack itemStack) {
        int i = itemStack.getEnchantmentLevel(Enchantments.QUICK_CHARGE);
        return i == 0 ? 16.5F : 16.5F - 3.5F * i;
    }

    @Override
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return GrapplingHookHandRenderer.INSTANCE;
    }

}
