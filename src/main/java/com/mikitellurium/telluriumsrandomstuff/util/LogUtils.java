package com.mikitellurium.telluriumsrandomstuff.util;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class LogUtils {

    public static void chatMessage(String message) {
        Minecraft.getInstance().player.sendSystemMessage(Component.literal(message));
    }

    public static void consoleLog(Object message) {
        System.out.println(message);
    }

    public static void consoleLogSequence(Object... objs) {
        consoleLog("---");
        for (Object object : objs) {
            consoleLog(object);
        }
    }

    public static void debugIsNull(String prefix, Object object) {
        String s = object == null ? "null" : "NOT null";
        consoleLog(prefix + ": " + s);
    }

    public static void debugIsEqual(String prefix, Object firstObj, Object secondObj) {
        String s = firstObj.equals(secondObj) ? "equal" : "NOT equal";
        consoleLog(prefix + ": " + s);
    }

}
