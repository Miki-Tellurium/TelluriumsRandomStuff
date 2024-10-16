package com.mikitellurium.telluriumsrandomstuff.client.hud.screen;

import com.mikitellurium.telluriumsrandomstuff.client.hud.menu.AbstractSoulFuelMenu;
import com.mikitellurium.telluriumsrandomstuff.client.hud.render.GuiFluidRenderer;
import com.mikitellurium.telluriumsrandomstuff.registry.ModFluids;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import com.mikitellurium.telluriumsrandomstuff.util.MouseUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractSoulFuelScreen<T extends AbstractSoulFuelMenu> extends AbstractContainerScreen<T> {

    private final ResourceLocation guiTexture;
    private final int texWidth;
    private final int texHeight;
    private Rect2i soulLavaStorage;
    private final int tankXPos;
    private final int tankYPos;

    public AbstractSoulFuelScreen(T menu, Inventory inventory, ResourceLocation guiTexture, Component title,
                                  int texWidth, int texHeight, int tankXPos, int tankYPos) {
        super(menu, inventory, title);
        this.guiTexture = guiTexture;
        this.texWidth = texWidth;
        this.texHeight = texHeight;
        this.tankXPos = tankXPos;
        this.tankYPos = tankYPos;
    }

    @Override
    protected void init() {
        super.init();
        soulLavaStorage = new Rect2i(leftPos + tankXPos, topPos + tankYPos, 16, 48);
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
        this.titleLabelY = 4;
        this.inventoryLabelX = 28;
        this.inventoryLabelY = 70;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        graphics.blit(guiTexture, leftPos, topPos, 0, 0, this.texWidth, this.texHeight);

        renderSoulLavaStorage(graphics, soulLavaStorage.getX(), soulLavaStorage.getY());
        renderGlass(graphics);
    }

    @Override
    public void render(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(graphics);
        super.render(graphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(graphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int pMouseX, int pMouseY) {
        super.renderLabels(graphics, pMouseX, pMouseY);
        renderFluidAreaTooltips(graphics, pMouseX, pMouseY);
    }

    private void renderSoulLavaStorage(GuiGraphics graphics, int xPos, int yPos) {
        GuiFluidRenderer.drawBackground(graphics, xPos, yPos, this.menu.getFluidStack(), this.menu.getBlockEntity().getFluidTankCapacity(),
                soulLavaStorage.getWidth(), soulLavaStorage.getHeight());
//        RenderSystem.setShaderTexture(0, guiTexture);
    }

    private void renderGlass(GuiGraphics graphics) {
        graphics.blit(FastLoc.GUI_ELEMENTS_TEXTURE, soulLavaStorage.getX(), soulLavaStorage.getY(), 72, 0,
                soulLavaStorage.getWidth(), soulLavaStorage.getHeight());
    }

    private void renderFluidAreaTooltips(GuiGraphics graphics, int pMouseX, int pMouseY) {
        if(MouseUtils.isAboveArea(pMouseX, pMouseY, soulLavaStorage.getX(), soulLavaStorage.getY(),
                soulLavaStorage.getWidth(), soulLavaStorage.getHeight())) {
            graphics.renderTooltip(this.font, this.getFluidTooltips(),
                    Optional.empty(), pMouseX - leftPos, pMouseY - topPos);
        }
    }

    private List<Component> getFluidTooltips() {
        List<Component> tooltip = new ArrayList<>();
        Fluid fluid = ModFluids.SOUL_LAVA_SOURCE.get();

        Component displayName = Component.translatable("fluid_type.telluriumsrandomstuff.soul_lava");
        tooltip.add(displayName);

        if (this.getMinecraft().options.advancedItemTooltips) {
            ResourceLocation resourceLocation = ForgeRegistries.FLUIDS.getKey(fluid);
            if (resourceLocation != null) {
                MutableComponent advancedId = Component.literal(resourceLocation.toString())
                        .withStyle(ChatFormatting.DARK_GRAY);
                tooltip.add(advancedId);
            }
        }
        MutableComponent amount = Component.literal(menu.getFluidStack().getAmount() + "/" +
                        menu.getBlockEntity().getFluidTankCapacity() + " mB")
                .withStyle(ChatFormatting.GRAY);
        tooltip.add(amount);

        return tooltip;
    }

    public Rect2i getSoulLavaStorage() {
        return soulLavaStorage;
    }

}
