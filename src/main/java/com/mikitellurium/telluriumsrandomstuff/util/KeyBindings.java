package com.mikitellurium.telluriumsrandomstuff.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {

    public static final String KEY_CATEGORY_TUTORIAL = FastLoc.modId();

    public static final String KEY_SCALE_UP = "Scale up";
    public static final String KEY_SCALE_DOWN = "Scale down";
    public static final String KEY_TRANSLATE_X_UP = "Translate x up";
    public static final String KEY_TRANSLATE_X_DOWN = "Translate x down";
    public static final String KEY_TRANSLATE_Y_UP = "Translate y up";
    public static final String KEY_TRANSLATE_Y_DOWN = "Translate y down";
    public static final String KEY_TRANSLATE_Z_UP = "Translate z up";
    public static final String KEY_TRANSLATE_Z_DOWN = "Translate z down";

    public static final String KEY_RESET = "Reset";

    public static final KeyMapping SCALE_UP_KEY = new KeyMapping(KEY_SCALE_UP, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UP, KEY_CATEGORY_TUTORIAL);
    public static final KeyMapping SCALE_DOWN_KEY = new KeyMapping(KEY_SCALE_DOWN, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_DOWN, KEY_CATEGORY_TUTORIAL);

    public static final KeyMapping TRANSLATE_X_UP_KEY = new KeyMapping(KEY_TRANSLATE_X_UP, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_KP_4, KEY_CATEGORY_TUTORIAL);
    public static final KeyMapping TRANSLATE_X_DOWN_KEY = new KeyMapping(KEY_TRANSLATE_X_DOWN, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_KP_1, KEY_CATEGORY_TUTORIAL);
    public static final KeyMapping TRANSLATE_Y_UP_KEY = new KeyMapping(KEY_TRANSLATE_Y_UP, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_KP_5, KEY_CATEGORY_TUTORIAL);
    public static final KeyMapping TRANSLATE_Y_DOWN_KEY = new KeyMapping(KEY_TRANSLATE_Y_DOWN, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_KP_2, KEY_CATEGORY_TUTORIAL);
    public static final KeyMapping TRANSLATE_Z_UP_KEY = new KeyMapping(KEY_TRANSLATE_Z_UP, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_KP_6, KEY_CATEGORY_TUTORIAL);
    public static final KeyMapping TRANSLATE_Z_DOWN_KEY = new KeyMapping(KEY_TRANSLATE_Z_DOWN, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_KP_3, KEY_CATEGORY_TUTORIAL);
    public static final KeyMapping RESET_KEY = new KeyMapping(KEY_RESET, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_KP_0, KEY_CATEGORY_TUTORIAL);

}
