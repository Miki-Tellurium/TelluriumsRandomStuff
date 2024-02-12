package com.mikitellurium.telluriumsrandomstuff.common.event;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.registry.ModFluidTypes;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderBlockScreenEffectEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Vector3f;

import java.util.Arrays;

@Mod.EventBusSubscriber(modid = TelluriumsRandomStuffMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RenderingEvents {

    /* Soul lava fog */
    @SubscribeEvent
    public static void setFogPlane(ViewportEvent.RenderFog event) {
        if (event.getCamera().getEntity() instanceof LivingEntity entity) {
            if (entity.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.LAVA_GOOGLES.get())) {
                event.setCanceled(true);
                setLavaGooglesFogPlane(event, event.getCamera(), event.getCamera().getEntity().isSpectator());
            } else if (isCameraInSoulLava(event.getCamera())) {
                event.setCanceled(true);
                setSoulLavaFogPlane(event);
            }
        }
    }

    @SubscribeEvent
    public static void setFogColor(ViewportEvent.ComputeFogColor event) {
        if (isCameraInSoulLava(event.getCamera())) {
            Vector3f soulLavaFogColor = new Vector3f(0f / 255f, 210f / 255f, 225f / 255f);
            event.setRed(soulLavaFogColor.x);
            event.setGreen(soulLavaFogColor.y);
            event.setBlue(soulLavaFogColor.z);
        }
    }

    private static void setSoulLavaFogPlane(ViewportEvent.RenderFog event) {
        if (event.isCanceled()) {
            Entity entity = event.getCamera().getEntity();

            if (entity.isSpectator()) {
                event.setNearPlaneDistance(-8.0F);
                event.setFarPlaneDistance(event.getRenderer().getRenderDistance() * 0.5F);
            } else if (entity instanceof LivingEntity && ((LivingEntity) entity).hasEffect(MobEffects.FIRE_RESISTANCE)) {
                event.setNearPlaneDistance(0.0F);
                event.setFarPlaneDistance(3.0F);
            } else {
                event.setNearPlaneDistance(0.25f);
                event.setFarPlaneDistance(1.0f);
            }
        }
    }

    /* Lava googles */
    @SubscribeEvent
    public static void onOverlayRender(RenderBlockScreenEffectEvent event) {
        if (event.getPlayer().getItemBySlot(EquipmentSlot.HEAD).is(ModItems.LAVA_GOOGLES.get()) &&
                event.getOverlayType() == RenderBlockScreenEffectEvent.OverlayType.FIRE) {
            PoseStack poseStack = event.getPoseStack();
            poseStack.translate(0.0f, -0.3f, 0.0f);
        }
    }

    private static void setLavaGooglesFogPlane(ViewportEvent.RenderFog event, Camera camera, boolean isSpectator) {
        if (event.isCanceled()) {
            if ((camera.getFluidInCamera() == FogType.LAVA || isCameraInSoulLava(camera)) && !isSpectator) {
                event.setNearPlaneDistance(-8.0F);
                event.setFarPlaneDistance(event.getRenderer().getRenderDistance() * 0.25F);
            }
        }
    }

    private static boolean isCameraInSoulLava(Camera camera) {
        Camera.NearPlane nearPlane = camera.getNearPlane();
        BlockGetter blockGetter = Minecraft.getInstance().level;
        for(Vec3 vec3 : Arrays.asList(new Vec3(camera.getLookVector()).scale(0.05F), nearPlane.getTopLeft(),
                nearPlane.getTopRight(), nearPlane.getBottomLeft(), nearPlane.getBottomRight())) {
            Vec3 cameraPos = camera.getPosition().add(vec3);
            BlockPos blockpos = BlockPos.containing(cameraPos);
            FluidState fluidstate = blockGetter.getFluidState(blockpos);
            if (fluidstate.getFluidType() == ModFluidTypes.SOUL_LAVA_FLUID_TYPE.get()) {
                if (cameraPos.y <= (double)(fluidstate.getHeight(blockGetter, blockpos) + (float)blockpos.getY())) {
                    return true;
                }
            }
        }

        return false;
    }

}
