package com.mikitellurium.telluriumsrandomstuff.util;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.GameShuttingDownEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TelluriumsRandomStuffMod.MOD_ID, value = Dist.CLIENT)
public class KeyEvents {

    private static final float increase = 0.01F;
    private static final float initScale = 1.0F;
    private static final float initX = 0.0F;
    private static final float initY = 0.0F;
    private static final float initZ = 0.0F;
    public static float scale = 1.0F;
    public static float translateX = 0.0F;
    public static float translateY = 0.0F;
    public static float translateZ = 0.0F;

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (KeyBindings.TRANSLATE_X_UP_KEY.consumeClick()) {
            translateX += increase;
        } else if (KeyBindings.TRANSLATE_X_DOWN_KEY.consumeClick()) {
            translateX -= increase;
        } else if (KeyBindings.TRANSLATE_Y_UP_KEY.consumeClick()) {
            translateY += increase;
        } else if (KeyBindings.TRANSLATE_Y_DOWN_KEY.consumeClick()) {
            translateY -= increase;
        } else if (KeyBindings.TRANSLATE_Z_UP_KEY.consumeClick()) {
            translateZ += increase;
        } else if (KeyBindings.TRANSLATE_Z_DOWN_KEY.consumeClick()) {
            translateZ -= increase;
        } else if (KeyBindings.SCALE_UP_KEY.consumeClick()) {
            scale += 0.01F;
        } else if (KeyBindings.SCALE_DOWN_KEY.consumeClick()) {
            scale -= 0.01F;
        } else if (KeyBindings.RESET_KEY.consumeClick()) {
            scale = initScale;
            translateX = initX;
            translateY = initY;
            translateZ = initZ;
        }
        translateX = Mth.clamp(translateX, 0.0F, 1.0F);
        translateY = Mth.clamp(translateY, 0.0F, 1.0F);
        translateZ = Mth.clamp(translateZ, 0.0F, 1.0F);
    }

    @Mod.EventBusSubscriber(modid = TelluriumsRandomStuffMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBindings.SCALE_UP_KEY);
            event.register(KeyBindings.SCALE_DOWN_KEY);
            event.register(KeyBindings.TRANSLATE_X_UP_KEY);
            event.register(KeyBindings.TRANSLATE_X_DOWN_KEY);
            event.register(KeyBindings.TRANSLATE_Y_UP_KEY);
            event.register(KeyBindings.TRANSLATE_Y_DOWN_KEY);
            event.register(KeyBindings.TRANSLATE_Z_UP_KEY);
            event.register(KeyBindings.TRANSLATE_Z_DOWN_KEY);
            event.register(KeyBindings.RESET_KEY);
        }
    }

    @Mod.EventBusSubscriber(modid = TelluriumsRandomStuffMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class GameShutDown {
        @SubscribeEvent
        public static void onGameShutDown(GameShuttingDownEvent event) {
            System.out.println("Scale="+scale);
            System.out.println("TranslateX="+translateX);
            System.out.println("TranslateY="+translateY);
            System.out.println("TranslateZ="+translateZ);
        }
    }

}
