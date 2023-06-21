package com.mikitellurium.telluriumsrandomstuff.client.render;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class LavaGooglesOverlay implements IGuiOverlay {

    public static ResourceLocation OVERLAY_TEXTURE =
            new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, "textures/misc/lava_googles_overlay.png");

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.LAVA_GOOGLES.get()) && !player.isSpectator()) {
                RenderSystem.enableBlend();
                guiGraphics.blit(OVERLAY_TEXTURE, 0, 0, -90, 0.0F, 0.0F,
                        screenWidth, screenHeight, screenWidth, screenHeight);
            }
        }
    }

}
