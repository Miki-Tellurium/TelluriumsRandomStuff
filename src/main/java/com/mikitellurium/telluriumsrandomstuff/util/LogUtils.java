package com.mikitellurium.telluriumsrandomstuff.util;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class LogUtils {

    public static void chatMessage(String message) {
        Minecraft.getInstance().player.sendSystemMessage(Component.literal(message));
    }

    public static <T> void consoleLogMessage(T message) {
        System.out.println(message);
    }

}
