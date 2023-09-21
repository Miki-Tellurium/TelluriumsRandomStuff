package com.mikitellurium.telluriumsrandomstuff.datagen.providers;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.common.content.block.GrateMagmaBlock;
import com.mikitellurium.telluriumsrandomstuff.common.content.block.GrateSoulSandBlock;
import com.mikitellurium.telluriumsrandomstuff.common.content.block.HydrodynamicRailBlock;
import com.mikitellurium.telluriumsrandomstuff.common.content.block.SoulFurnaceBlock;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.util.LogUtils;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.RailShape;
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

        this.getVariantBuilder(ModBlocks.GRATE_MAGMA_BLOCK.get())
                .forAllStates((state) -> ConfiguredModel.builder()
                        .modelFile(this.models().cube(ModBlocks.GRATE_MAGMA_BLOCK.getId().getPath(),
                                        mcLoc("block/magma"),
                                        modLoc("block/grate_magma_block_top"),
                                        modLoc("block/grate_magma_block_side_1"),
                                        modLoc("block/grate_magma_block_side_1"),
                                        modLoc("block/grate_magma_block_side_2"),
                                        modLoc("block/grate_magma_block_side_2"))
                                .texture("particle", mcLoc("block/magma")))
                        .rotationY((int) state.getValue(GrateMagmaBlock.FACING).toYRot())
                        .build());
        this.blockItemModelFromParent(ModBlocks.GRATE_MAGMA_BLOCK, modLoc("block/grate_magma_block"));

        this.simpleBlock(ModBlocks.CUSTOM_BUBBLE_COLUMN.get(), this.models().getExistingFile(mcLoc("block/water")));

        this.getVariantBuilder(ModBlocks.HYDRODYNAMIC_RAIL.get())
                .forAllStates((state) -> {
                    String model = "hydrodynamic_rail";
                    String parent = "rail_flat";
                    String texture = model;
                    int yRot = 0;
                    if (state.getValue(HydrodynamicRailBlock.WATERLOGGED)) {
                        model = model + "_on";
                        texture = model;
                    }
                    switch (state.getValue(HydrodynamicRailBlock.SHAPE)) {
                        case ASCENDING_EAST:
                            model = model + "_raised_ne";
                            parent = "template_rail_raised_ne";
                            yRot = 90;
                            break;
                        case ASCENDING_NORTH:
                            model = model + "_raised_ne";
                            parent = "template_rail_raised_ne";
                            break;
                        case ASCENDING_SOUTH:
                            model = model + "_raised_sw";
                            parent = "template_rail_raised_sw";
                            break;
                        case ASCENDING_WEST:
                            model = model + "_raised_sw";
                            parent = "template_rail_raised_sw";
                            yRot = 90;
                            break;
                        case EAST_WEST:
                            yRot = 90;
                            break;
                    }
                    return ConfiguredModel.builder().modelFile(this.models()
                                    .withExistingParent(model, mcLoc("block/" + parent))
                                    .renderType("cutout")
                                    .texture("rail", modLoc("block/" + texture)))
                            .rotationY(yRot)
                            .build();
                });

        this.simpleBlockWithItem(ModBlocks.SOUL_MAGMA_BLOCK.get(), this.cubeAll(ModBlocks.SOUL_MAGMA_BLOCK.get()));

        this.getVariantBuilder(ModBlocks.GRATE_SOUL_MAGMA_BLOCK.get())
                .forAllStates((state) -> ConfiguredModel.builder()
                        .modelFile(this.models().cube(ModBlocks.GRATE_SOUL_MAGMA_BLOCK.getId().getPath(),
                                        modLoc("block/soul_magma_block"),
                                        modLoc("block/grate_soul_magma_block_top"),
                                        modLoc("block/grate_soul_magma_block_side_1"),
                                        modLoc("block/grate_soul_magma_block_side_1"),
                                        modLoc("block/grate_soul_magma_block_side_2"),
                                        modLoc("block/grate_soul_magma_block_side_2"))
                                .texture("particle", modLoc("block/soul_magma_block")))
                        .rotationY((int) state.getValue(GrateMagmaBlock.FACING).toYRot())
                        .build());
        this.blockItemModelFromParent(ModBlocks.GRATE_SOUL_MAGMA_BLOCK, modLoc("block/grate_soul_magma_block"));

        this.getVariantBuilder(ModBlocks.SOUL_FURNACE.get())
                .forAllStates((state) -> {
                    String model = "soul_furnace";
                    String frontState = state.getValue(SoulFurnaceBlock.LIT) ? "on" : "off";
                    return ConfiguredModel.builder().modelFile(this.models()
                                    .orientableWithBottom(state.getValue(SoulFurnaceBlock.LIT) ? model  + "_on" : model,
                                            modLoc("block/" + model + "_side"),
                                            modLoc("block/" + model + "_front_" + frontState),
                                            mcLoc("block/obsidian"),
                                            modLoc("block/" + model + "_top")))
                            .rotationY((int) state.getValue(SoulFurnaceBlock.FACING).toYRot() + 180)
                            .build();
                });
        this.blockItemModelFromParent(ModBlocks.SOUL_FURNACE, modLoc("block/soul_furnace"));

        this.simpleBlockWithItem(ModBlocks.SOUL_MAGMA_BRICKS.get(), this.cubeAll(ModBlocks.SOUL_MAGMA_BRICKS.get()));
        this.slabBlock((SlabBlock) ModBlocks.SOUL_MAGMA_BRICK_SLAB.get(), modLoc("block/soul_magma_bricks"),
                modLoc("block/soul_magma_bricks"));
        this.blockItemModelFromParent(ModBlocks.SOUL_MAGMA_BRICK_SLAB, modLoc("block/soul_magma_brick_slab"));

        this.simpleBlock(ModBlocks.SOUL_LAVA_CAULDRON.get(), this.models()
                .withExistingParent("soul_lava_cauldron", mcLoc("block/template_cauldron_full"))
                .texture("bottom", mcLoc("block/cauldron_bottom"))
                .texture("side", mcLoc("block/cauldron_side"))
                .texture("top", mcLoc("block/cauldron_top"))
                .texture("inside", mcLoc("block/cauldron_inner"))
                .texture("content", modLoc("block/soul_lava_still"))
                .texture("particle", mcLoc("block/cauldron_side")));

        this.simpleBlockWithItem(ModBlocks.OPAL.get(), this.models().withExistingParent("opal", modLoc("block/cube_all_tinted"))
                .texture("all", modLoc("block/opal"))
                .texture("particle", modLoc("block/opal")));

        this.simpleBlockWithItem(ModBlocks.OPAL_COBBLESTONE.get(), this.models().withExistingParent("opal_cobblestone", modLoc("block/cube_all_tinted"))
                .texture("all", modLoc("block/opal_cobblestone"))
                .texture("particle", modLoc("block/opal_cobblestone")));

        this.simpleBlockWithItem(ModBlocks.OPAL_BRICKS.get(), this.models().withExistingParent("opal_bricks", modLoc("block/cube_all_tinted"))
                .texture("all", modLoc("block/opal_bricks"))
                .texture("particle", modLoc("block/opal_bricks")));

        this.simpleBlockWithItem(ModBlocks.CUT_OPAL_BRICKS.get(), this.models().withExistingParent("cut_opal_bricks", modLoc("block/cube_all_tinted"))
                .texture("all", modLoc("block/cut_opal_bricks"))
                .texture("particle", modLoc("block/cut_opal_bricks")));

        this.simpleBlockWithItem(ModBlocks.CHISELED_OPAL_BRICKS.get(), this.models().withExistingParent("chiseled_opal_bricks", modLoc("block/cube_all_tinted"))
                .texture("all", modLoc("block/chiseled_opal_bricks"))
                .texture("particle", modLoc("block/chiseled_opal_bricks")));

        this.simpleBlockWithItem(ModBlocks.CRACKED_OPAL_BRICKS.get(), this.models().withExistingParent("cracked_opal_bricks", modLoc("block/cube_all_tinted"))
                .texture("all", modLoc("block/cracked_opal_bricks"))
                .texture("particle", modLoc("block/cracked_opal_bricks")));

        this.simpleBlockWithItem(ModBlocks.CRACKED_CUT_OPAL_BRICKS.get(), this.models().withExistingParent("cracked_cut_opal_bricks", modLoc("block/cube_all_tinted"))
                .texture("all", modLoc("block/cracked_cut_opal_bricks"))
                .texture("particle", modLoc("block/cracked_cut_opal_bricks")));

    }

    private void blockItemModelFromParent(RegistryObject<Block> block, ResourceLocation parent) {
        this.itemModels().withExistingParent(block.getId().getPath(), parent);
    }

}
