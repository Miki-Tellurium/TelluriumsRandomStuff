package com.mikitellurium.telluriumsrandomstuff.util;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import net.minecraft.resources.ResourceLocation;

public class FastLoc {

    public static final ResourceLocation JEI_GUI_TEXTURE = FastLoc.modLoc("textures/gui/jei_gui.png");
    public static final ResourceLocation GUI_ELEMENTS_TEXTURE = FastLoc.modLoc("textures/gui/gui_elements.png");

    public static ResourceLocation modLoc(String id) {
        return new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, id);
    }

    public static ResourceLocation mcLoc(String id) {
        return new ResourceLocation(id);
    }

    public static String modId() {
        return TelluriumsRandomStuffMod.MOD_ID;
    }

}
