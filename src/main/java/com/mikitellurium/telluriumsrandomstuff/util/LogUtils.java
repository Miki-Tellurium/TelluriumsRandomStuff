package com.mikitellurium.telluriumsrandomstuff.util;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class LogUtils {

    public static void sendChatMessage(String message) {
        Minecraft.getInstance().player.sendSystemMessage(Component.literal(message));
    }

    public static void consoleLogMessage(String message) {
        System.out.println(message);
    }

}
