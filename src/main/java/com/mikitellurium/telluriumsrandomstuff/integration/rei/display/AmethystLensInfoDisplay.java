package com.mikitellurium.telluriumsrandomstuff.integration.rei.display;

import com.mikitellurium.telluriumsrandomstuff.integration.rei.util.BlockStateEntryType;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.util.FluidBlockEntryType;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.util.ModDisplayCategories;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class AmethystLensInfoDisplay extends BasicDisplay {

    public AmethystLensInfoDisplay(Recipe recipe) {
        this(List.of(EntryIngredients.of(recipe.getInput()),
                        EntryIngredient.of(recipe.getTransmutationBlock()),
                        EntryIngredients.of(Blocks.CAULDRON),
                        EntryIngredients.of(Items.WATER_BUCKET),
                        EntryIngredients.of(Fluids.WATER)),
                List.of(EntryIngredients.of(recipe.getOutput())));
    }

    public AmethystLensInfoDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return ModDisplayCategories.AMETHYST_LENS_INFO;
    }

    public static DisplaySerializer<AmethystLensInfoDisplay> serializer() {
        return BasicDisplay.Serializer.ofSimple(((input, output, location) -> new AmethystLensInfoDisplay(input, output)));
    }

    public static class Recipe {

        private final ItemStack moltenAmethyst;
        private final ItemStack amethystLens;
        private final EntryStack<?> transmutationBlock;

        public Recipe(boolean useCauldron) {
            this.moltenAmethyst = new ItemStack(ModItems.MOLTEN_AMETHYST.get());
            this.amethystLens = new ItemStack(ModItems.AMETHYST_LENS.get());
            this.transmutationBlock = useCauldron ?
                    EntryStack.of(BlockStateEntryType.TYPE, Blocks.WATER_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, LayeredCauldronBlock.MAX_FILL_LEVEL)) :
                    EntryStack.of(FluidBlockEntryType.TYPE, new FluidStack(Fluids.WATER, 1000));
        }

        public ItemStack getInput() {
            return moltenAmethyst;
        }

        public ItemStack getOutput() {
            return amethystLens;
        }

        public EntryStack<?> getTransmutationBlock() {
            return transmutationBlock;
        }
    }

}
