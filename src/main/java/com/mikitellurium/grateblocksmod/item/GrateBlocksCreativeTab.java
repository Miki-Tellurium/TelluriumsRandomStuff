package com.mikitellurium.grateblocksmod.item;

import com.mikitellurium.grateblocksmod.block.ModBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class GrateBlocksCreativeTab {

    //Creating a creative mode tab
    public static final CreativeModeTab TAB_GRATEBLOCKS = new CreativeModeTab("grateblocks_creative_tab") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ModBlocks.GRATE_MAGMA_BLOCK.get().asItem());
        }
    };
}