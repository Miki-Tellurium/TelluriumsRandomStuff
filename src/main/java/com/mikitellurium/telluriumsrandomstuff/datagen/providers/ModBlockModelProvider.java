package com.mikitellurium.telluriumsrandomstuff.datagen.providers;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.common.content.block.GrateSoulSandBlock;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.util.LogUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockModelProvider extends BlockStateProvider {

    public ModBlockModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TelluriumsRandomStuffMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        LogUtils.consoleLogMessage("Registering block models");
        this.getVariantBuilder(ModBlocks.GRATE_SOUL_SAND.get())
                .forAllStates((state) -> ConfiguredModel.builder()
                        .modelFile(this.models().cube(ModBlocks.GRATE_SOUL_SAND.getId().getPath(),
                                mcLoc("block/soul_sand"),
                                modLoc("block/grate_soul_sand_top"),
                                modLoc("block/grate_soul_sand_side_1"),
                                modLoc("block/grate_soul_sand_side_1"),
                                modLoc("block/grate_soul_sand_side_2"),
                                modLoc("block/grate_soul_sand_side_2"))
                                .texture("particle", mcLoc("block/soul_sand")))
                        .rotationY((int) state.getValue(GrateSoulSandBlock.FACING).toYRot())
                        .build());
        this.blockItemModelFromParent(ModBlocks.GRATE_SOUL_SAND, modLoc("block/grate_soul_sand"));
    }

    private void blockItemModelFromParent(RegistryObject<Block> block, ResourceLocation parent) {
        this.itemModels().withExistingParent(block.getId().getPath(), parent);
    }

}
