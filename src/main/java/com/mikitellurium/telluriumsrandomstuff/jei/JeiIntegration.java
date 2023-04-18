package com.mikitellurium.telluriumsrandomstuff.jei;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.block.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.gui.SoulFurnaceGui;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class JeiIntegration implements IModPlugin {

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation(TelluriumsRandomStuffMod.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        // TODO: add soul furnace gui to jei
        //IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        //registration.addRecipeCategories(new SoulFurnaceRecipeCategory(guiHelper));
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(SoulFurnaceGui.class, 78, 28, 27, 22, RecipeTypes.SMELTING);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.SOUL_FURNACE.get()), RecipeTypes.SMELTING);
    }

}
