package com.mikitellurium.telluriumsrandomstuff.common.event;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.registry.ModFluidTypes;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
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
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Vector3f;

import java.util.Arrays;

@Mod.EventBusSubscriber(modid = TelluriumsRandomStuffMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GameplayClientEvents {

    // Soul lava fog
    @SubscribeEvent
    public static void setFogPlane(ViewportEvent.RenderFog event) {
        if (Minecraft.getInstance().player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.LAVA_GOOGLES.get())) {
            event.setCanceled(true);
            setLavaGooglesFogPlane(event, event.getCamera(), event.getCamera().getEntity().isSpectator());
        } else if (isCameraInSoulLava(event.getCamera())) {
            event.setCanceled(true);
            setSoulLavaFogPlane(event);
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
            Vec3 vec31 = camera.getPosition().add(vec3);
            BlockPos blockpos = BlockPos.containing(vec31);
            FluidState fluidstate1 = blockGetter.getFluidState(blockpos);
            if (fluidstate1.getFluidType() == ModFluidTypes.SOUL_LAVA_TYPE) {
                if (vec31.y <= (double)(fluidstate1.getHeight(blockGetter, blockpos) + (float)blockpos.getY())) {
                    return true;
                }
            }
        }

        return false;
    }

}
