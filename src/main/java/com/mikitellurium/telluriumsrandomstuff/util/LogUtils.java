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

    public static void debugNonNull(String prefix, Object object) {
        String s = object == null ? "null" : "NOT null";
        consoleLogMessage(prefix + ": " + s);
    }

    public static void debugIsEqual(String prefix, Object firstObj, Object secondObj) {
        String s = firstObj.equals(secondObj) ? "equal" : "NOT equal";
        consoleLogMessage(prefix + ": " + s);
    }

}
