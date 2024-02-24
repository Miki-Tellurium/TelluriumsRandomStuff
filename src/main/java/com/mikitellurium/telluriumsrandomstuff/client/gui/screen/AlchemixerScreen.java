package com.mikitellurium.telluriumsrandomstuff.client.gui.screen;

import com.mikitellurium.telluriumsrandomstuff.client.gui.menu.AlchemixerMenu;
import com.mikitellurium.telluriumsrandomstuff.client.gui.util.GuiFluidRenderer;
import com.mikitellurium.telluriumsrandomstuff.registry.ModFluids;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.fluids.FluidStack;

public class AlchemixerScreen extends AbstractSoulFuelScreen<AlchemixerMenu>{

    public static final ResourceLocation GUI_TEXTURE = FastLoc.modLoc("textures/gui/alchemixer_gui.png");

    private static final int[] BUBBLE_LENGTHS = new int[]{0, 6, 11, 16, 20, 24, 29};
    private static final int gray = 0xFF646464;
    private static final int red = 16736352;
    private static final int blue = 0xFF00AFC8;

    public AlchemixerScreen(AlchemixerMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, GUI_TEXTURE, title, 176, 166, 8, 8);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float pPartialTick, int pMouseX, int pMouseY) {
        super.renderBg(graphics, pPartialTick, pMouseX, pMouseY);
        this.renderProgressBubbles(graphics);
        this.renderSoulLavaMixer(graphics);
        this.writeRecipeCost(graphics);
    }

    private void renderProgressBubbles(GuiGraphics graphics) {
        if (this.menu.isCrafting()) {
            int bubbleLength = BUBBLE_LENGTHS[this.menu.getData().get(0) / 2 % 7];
            graphics.blit(AbstractSoulFuelScreen.ELEMENT_TEXTURE, leftPos + 66, topPos + 33 + 29 - bubbleLength,
                    192, 60 - bubbleLength, 11, bubbleLength);
            graphics.blit(AbstractSoulFuelScreen.ELEMENT_TEXTURE, leftPos + 124, topPos + 33 + 29 - bubbleLength,
                    192, 60 - bubbleLength, 11, bubbleLength);
        }
    }

    private void renderSoulLavaMixer(GuiGraphics graphics) {
        if (!this.menu.getFluidStack().isEmpty()) {
            int capacity = 1000;
            int height = 40;
            int progress = this.menu.getScaledProgress(height);
            int fluidAmount = capacity - (capacity / height) * progress;
            GuiFluidRenderer.drawBackground(graphics, leftPos + 95, topPos + 13,
                    new FluidStack(ModFluids.SOUL_LAVA_SOURCE.get(), fluidAmount), capacity, 8, height);
        }
    }

    private void writeRecipeCost(GuiGraphics graphics) {
        int recipeCost = this.menu.getRecipeCost();
        int textColor = recipeCost == 0 ? gray : (recipeCost <= this.menu.getBlockEntity().getFluidTankCapacity() ? blue : red);
        graphics.drawString(this.font, "Cost:", leftPos + 28, topPos + 18, gray, false);
        graphics.drawString(this.font, String.valueOf(recipeCost), leftPos + 28, topPos + 28, textColor, false);
    }

}
