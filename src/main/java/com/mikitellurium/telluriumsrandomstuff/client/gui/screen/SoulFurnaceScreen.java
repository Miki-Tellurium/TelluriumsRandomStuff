package com.mikitellurium.telluriumsrandomstuff.client.gui.screen;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.client.gui.menu.SoulFurnaceMenu;
import com.mikitellurium.telluriumsrandomstuff.client.gui.render.GuiFluidRenderer;
import com.mikitellurium.telluriumsrandomstuff.util.MouseUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;
import java.util.Optional;

public class SoulFurnaceScreen extends AbstractSoulFurnaceScreen<SoulFurnaceMenu> {

    public static final ResourceLocation GUI_TEXTURE =
            new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, "textures/gui/soul_furnace_gui.png");

    public SoulFurnaceScreen(SoulFurnaceMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, GUI_TEXTURE, title);
    }

}
