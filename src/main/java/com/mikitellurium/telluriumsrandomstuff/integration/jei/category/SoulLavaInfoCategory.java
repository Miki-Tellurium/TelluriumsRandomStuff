package com.mikitellurium.telluriumsrandomstuff.integration.jei.category;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModFluids;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.integration.jei.JeiIntegration;
import com.mikitellurium.telluriumsrandomstuff.integration.jei.util.FluidBlockRenderer;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import java.util.List;

public class SoulLavaInfoCategory implements IRecipeCategory<SoulLavaInfoCategory.Recipe> {

    public final static ResourceLocation UID =
            new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, "soul_lava_info");
    public final static ResourceLocation GUI_TEXTURE =
            new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, "textures/gui/jei_gui.png");

    private final int suggestionRenderY = -10;

    private final Font font = Minecraft.getInstance().font;
    private final IDrawable icon;
    private final IDrawable background;
    private final IDrawable downArrow;

    public SoulLavaInfoCategory(IGuiHelper guiHelper) {
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModItems.SOUL_LAVA_BUCKET.get()));
        this.background = guiHelper.createBlankDrawable(140, 80);
        this.downArrow = guiHelper.createDrawable(GUI_TEXTURE, 239, 0, 16, 24);
    }

    @Override
    public RecipeType<Recipe> getRecipeType() {
        return JeiIntegration.SOUL_LAVA_INFO_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.telluriumsrandomstuff.category.soul_lava_crafting");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void draw(Recipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        downArrow.draw(graphics, 120, suggestionRenderY + 38);
        drawSplitString(this.font, Component.translatable("jei.telluriumsrandomstuff.category.soul_lava_crafting_description"),
                graphics, 0, 5, 110, 0);
    }

    private List<FormattedText> splitComponent(Font font, FormattedText text, int width) {
        return font.getSplitter().splitLines(text, width, Style.EMPTY);
    }

    public void drawSplitString(Font font, FormattedText text, GuiGraphics graphics, int x, int y, int width, int colour) {
        for (FormattedText s : splitComponent(font, text, width)) {
            graphics.drawString(this.font, Language.getInstance().getVisualOrder(s), x, y , colour, false);
            y += font.lineHeight;
        }
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, Recipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.CATALYST, 120, suggestionRenderY + 21)
                .addItemStack(recipe.getInfusedSoulSand());
        builder.addSlot(RecipeIngredientRole.INPUT, 120, suggestionRenderY + 14)
                .addFluidStack(recipe.getLava(), 1000)
                .setCustomRenderer(ForgeTypes.FLUID_STACK, new FluidBlockRenderer());
        builder.addSlot(RecipeIngredientRole.CATALYST, 120, suggestionRenderY + 63)
                .addIngredient(JeiIntegration.BLOCK_STATE, Blocks.CAULDRON.defaultBlockState());
        builder.addInvisibleIngredients(RecipeIngredientRole.INPUT).addItemStack(new ItemStack(Items.LAVA_BUCKET));
        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addItemStack(new ItemStack(ModItems.SOUL_LAVA_BUCKET.get()));
        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addFluidStack(ModFluids.SOUL_LAVA_SOURCE.get(), 1000);
    }

    public static class Recipe {

        private final Fluid lava;
        private final ItemStack soulSand;
        private final ItemStack cauldron;

        public Recipe() {
            this.lava = Fluids.LAVA;
            this.soulSand = ModBlocks.INFUSED_SOUL_SAND.get().asItem().getDefaultInstance();
            this.cauldron = Items.CAULDRON.getDefaultInstance();
        }

        public Fluid getLava() {
            return lava;
        }

        public ItemStack getInfusedSoulSand() {
            return soulSand;
        }

        public ItemStack getCauldron() {
            return cauldron;
        }

    }

}