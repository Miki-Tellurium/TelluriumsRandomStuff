package com.mikitellurium.telluriumsrandomstuff.datagen.providers;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.common.block.*;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockModelProvider extends BlockStateProvider {

    public ModBlockModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TelluriumsRandomStuffMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
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
        this.tintedBlockWithItem(ModBlocks.OPAL);
        this.tintedBlockWithItem(ModBlocks.OPAL_COBBLESTONE);
        this.tintedBlockWithItem(ModBlocks.OPAL_BRICKS);
        this.tintedBlockWithItem(ModBlocks.CHISELED_OPAL_BRICKS);
        this.tintedBlockWithItem(ModBlocks.CUT_OPAL_BRICKS);
        this.tintedBlockWithItem(ModBlocks.CRACKED_OPAL_BRICKS);
        this.tintedBlockWithItem(ModBlocks.CRACKED_CUT_OPAL_BRICKS);
        this.tintedSlabWithItem(ModBlocks.OPAL_SLAB, modLoc("block/opal"));
        this.tintedSlabWithItem(ModBlocks.OPAL_COBBLESTONE_SLAB, modLoc("block/opal_cobblestone"));
        this.tintedSlabWithItem(ModBlocks.OPAL_BRICK_SLAB, modLoc("block/opal_bricks"));
        this.tintedSlabWithItem(ModBlocks.CUT_OPAL_BRICK_SLAB, modLoc("block/cut_opal_bricks"));
        this.tintedSlabWithItem(ModBlocks.CRACKED_OPAL_BRICK_SLAB, modLoc("block/cracked_opal_bricks"));
        this.tintedSlabWithItem(ModBlocks.CRACKED_CUT_OPAL_BRICK_SLAB, modLoc("block/cracked_cut_opal_bricks"));
        this.tintedStairsWithItem(ModBlocks.OPAL_STAIRS, modLoc("block/opal"));
        this.tintedStairsWithItem(ModBlocks.OPAL_COBBLESTONE_STAIRS, modLoc("block/opal_cobblestone"));
        this.tintedStairsWithItem(ModBlocks.OPAL_BRICK_STAIRS, modLoc("block/opal_bricks"));
        this.tintedStairsWithItem(ModBlocks.CUT_OPAL_BRICK_STAIRS, modLoc("block/cut_opal_bricks"));
        this.tintedWallWithItem(ModBlocks.OPAL_COBBLESTONE_WALL, modLoc("block/opal_cobblestone"));
        this.tintedWallWithItem(ModBlocks.OPAL_BRICK_WALL, modLoc("block/opal_bricks"));
        this.tintedWallWithItem(ModBlocks.CUT_OPAL_BRICK_WALL, modLoc("block/cut_opal_bricks"));
        this.pressurePlateBlock((PressurePlateBlock) ModBlocks.OPAL_PRESSURE_PLATE.get(),
                this.models().withExistingParent(ModBlocks.OPAL_PRESSURE_PLATE.getId().getPath(), modLoc("block/pressure_plate_up_tinted"))
                        .texture("texture", modLoc("block/opal")),
                this.models().withExistingParent(ModBlocks.OPAL_PRESSURE_PLATE.getId().getPath() + "_down", modLoc("block/pressure_plate_down_tinted"))
                        .texture("texture", modLoc("block/opal")));
        this.blockItemModelFromParent(ModBlocks.OPAL_PRESSURE_PLATE, modLoc("block/" + ModBlocks.OPAL_PRESSURE_PLATE.getId().getPath()));
        this.buttonBlock((ButtonBlock) ModBlocks.OPAL_BUTTON.get(),
                this.models().withExistingParent(ModBlocks.OPAL_BUTTON.getId().getPath(), modLoc("block/button_tinted"))
                        .texture("texture", modLoc("block/opal")),
                this.models().withExistingParent(ModBlocks.OPAL_BUTTON.getId().getPath() + "_pressed", modLoc("block/button_pressed_tinted"))
                        .texture("texture", modLoc("block/opal")));
        this.models().withExistingParent(ModBlocks.OPAL_BUTTON.getId().getPath() + "_inventory", modLoc("block/button_inventory_tinted"))
                .texture("texture", modLoc("block/opal"));
        this.blockItemModelFromParent(ModBlocks.OPAL_BUTTON, modLoc("block/" + ModBlocks.OPAL_BUTTON.getId().getPath() + "_inventory"));
        this.simpleBlockWithItem(ModBlocks.OPAL_CRYSTAL_ORE.get(), this.models().getExistingFile(modLoc("block/opal_crystal_ore")));
        this.tintedBlockWithItem(ModBlocks.RAW_OPAL_CRYSTAL_BLOCK);
        this.tintedBlockWithItem(ModBlocks.OPAL_CRYSTAL_BLOCK);
        this.simpleBlockWithItem(ModBlocks.SOUL_OBSIDIAN.get(), this.cubeAll(ModBlocks.SOUL_OBSIDIAN.get()));
        this.getVariantBuilder(ModBlocks.SOUL_ANCHOR.get())
                .forAllStates((state) -> {
                    String blockState = state.getValue(SoulAnchorBlock.CHARGED) ? "on" : "off";
                    return ConfiguredModel.builder().modelFile(this.models().cubeBottomTop(ModBlocks.SOUL_ANCHOR.getId().getPath() + "_" + blockState,
                                    modLoc("block/soul_anchor_side_" + blockState),
                                    modLoc("block/soul_obsidian"),
                                    modLoc("block/soul_anchor_top_" + blockState)))
                            .build();
                });
        this.blockItemModelFromParent(ModBlocks.SOUL_ANCHOR, modLoc("block/soul_anchor_off"));
        ModelFile extractorModel = this.models().withExistingParent(ModBlocks.EXTRACTOR.getId().getPath(), mcLoc("block/block"))
                .texture("bottom", modLoc("block/extractor_back"))
                .texture("top", mcLoc("block/furnace_top"))
                .texture("side", mcLoc("block/furnace_side"))
                .texture("front", modLoc("block/extractor_front"))
                .texture("particle", mcLoc("block/furnace_top"))
                .element()
                .allFaces(((direction, faceBuilder) -> {
                    switch (direction) {
                        case UP -> faceBuilder.uvs(0, 0, 16, 16).texture("#top").cullface(direction);
                        case DOWN -> faceBuilder.uvs( 0, 16, 16, 0).texture("#top").cullface(direction);
                        case NORTH -> faceBuilder.uvs( 0, 0, 16, 16).texture("#front").cullface(direction);
                        case SOUTH -> faceBuilder.uvs( 0, 0, 16, 16).texture("#bottom").cullface(direction);
                        case WEST, EAST -> faceBuilder.uvs( 0, 0, 16, 16).texture("#side").cullface(direction);
                    }
                })).from(0, 0, 0)
                .to(16, 16, 16)
                .end();
        this.getVariantBuilder(ModBlocks.EXTRACTOR.get())
                .forAllStatesExcept((state) -> {
                    int xRot = 0;
                    int yRot = 0;
                    switch (state.getValue(ExtractorBlock.FACING)) {
                        case UP:
                            xRot = 270;
                            break;
                        case DOWN:
                            xRot = 90;
                            break;
                        case NORTH:
                            break;
                        case SOUTH:
                            yRot = 180;
                            break;
                        case WEST:
                            yRot = 270;
                            break;
                        case EAST:
                            yRot = 90;
                            break;
                    }
                    return ConfiguredModel.builder().modelFile(extractorModel)
                            .rotationY(yRot)
                            .rotationX(xRot)
                            .build();}, ExtractorBlock.TRIGGERED);
        this.blockItemModelFromParent(ModBlocks.EXTRACTOR, modLoc("block/extractor"));
        this.torchflower(ModBlocks.BRIGHT_TORCHFLOWER, ModBlocks.BRIGHT_TORCHFLOWER_CROP, ModBlocks.POTTED_BRIGHT_TORCHFLOWER);
        this.torchflower(ModBlocks.SOUL_TORCHFLOWER, ModBlocks.SOUL_TORCHFLOWER_CROP, ModBlocks.POTTED_SOUL_TORCHFLOWER);
        this.simpleBlockWithItem(ModBlocks.BLUE_GLOWSTONE.get(), this.cubeAll(ModBlocks.BLUE_GLOWSTONE.get()));
        this.getVariantBuilder(ModBlocks.BLUE_REDSTONE_LAMP.get())
                .forAllStates((state) -> {
                    String model = ModBlocks.BLUE_REDSTONE_LAMP.getId().getPath();
                    if (state.getValue(RedstoneLampBlock.LIT)) model = model + "_on";
                    return ConfiguredModel.builder()
                            .modelFile(this.models().cubeAll(model, modLoc("block/" + model)))
                            .build();
                });
        this.blockItemModelFromParent(ModBlocks.BLUE_REDSTONE_LAMP, modLoc("block/blue_redstone_lamp"));
        this.getVariantBuilder(ModBlocks.AWAKENED_SCULK_SHRIEKER.get())
                .forAllStatesExcept((state) -> {
                    ResourceLocation innerTopTexture = state.getValue(AwakenedSculkShriekerBlock.CAN_SUMMON) ?
                            mcLoc("block/sculk_shrieker_can_summon_inner_top") : modLoc("block/awakened_sculk_shrieker_inner_top");
                    String model = ModBlocks.AWAKENED_SCULK_SHRIEKER.getId().getPath();
                    if (state.getValue(AwakenedSculkShriekerBlock.CAN_SUMMON)) {
                        model = model + "_can_summon";
                    }
                    return ConfiguredModel.builder()
                            .modelFile(this.models().withExistingParent(model, mcLoc("block/template_sculk_shrieker"))
                                    .renderType("cutout")
                                    .texture("bottom", modLoc("block/awakened_sculk_shrieker_bottom"))
                                    .texture("side", modLoc("block/awakened_sculk_shrieker_side"))
                                    .texture("top", mcLoc("block/sculk_shrieker_top"))
                                    .texture("inner_top", innerTopTexture)
                                    .texture("particle", modLoc("block/awakened_sculk_shrieker_bottom")))
                            .build();
                }, AwakenedSculkShriekerBlock.SHRIEKING, AwakenedSculkShriekerBlock.WATERLOGGED);
        this.blockItemModelFromParent(ModBlocks.AWAKENED_SCULK_SHRIEKER, modLoc("block/awakened_sculk_shrieker_can_summon"));
        this.getVariantBuilder(ModBlocks.SOUL_JACK_O_LANTERN.get())
                .forAllStates((state) -> ConfiguredModel.builder().modelFile(this.models()
                                .orientable(ModBlocks.SOUL_JACK_O_LANTERN.getId().getPath(),
                                        mcLoc("block/pumpkin_side"),
                                        modLoc("block/soul_jack_o_lantern"),
                                        mcLoc("block/pumpkin_top")))
                        .rotationY((int) state.getValue(CarvedPumpkinBlock.FACING).toYRot() + 180)
                        .build());
        this.blockItemModelFromParent(ModBlocks.SOUL_JACK_O_LANTERN, modLoc("block/soul_jack_o_lantern"));
        this.itemPedestalWithItem(ModBlocks.STONE_ITEM_PEDESTAL, mcLoc("block/stone"));
        this.itemPedestalWithItem(ModBlocks.STONE_BRICK_ITEM_PEDESTAL, mcLoc("block/stone_bricks"));
        this.itemPedestalWithItem(ModBlocks.MOSSY_STONE_BRICK_ITEM_PEDESTAL, mcLoc("block/mossy_stone_bricks"));
        this.itemPedestalWithItem(ModBlocks.POLISHED_GRANITE_ITEM_PEDESTAL, mcLoc("block/polished_granite"));
        this.itemPedestalWithItem(ModBlocks.POLISHED_DIORITE_ITEM_PEDESTAL, mcLoc("block/polished_diorite"));
        this.itemPedestalWithItem(ModBlocks.POLISHED_ANDESITE_ITEM_PEDESTAL, mcLoc("block/polished_andesite"));
        this.itemPedestalWithItem(ModBlocks.POLISHED_DEEPSLATE_ITEM_PEDESTAL, mcLoc("block/polished_deepslate"));
        this.itemPedestalWithItem(ModBlocks.DEEPSLATE_BRICK_ITEM_PEDESTAL, mcLoc("block/deepslate_bricks"));
        this.itemPedestalWithItem(ModBlocks.DEEPSLATE_TILE_ITEM_PEDESTAL, mcLoc("block/deepslate_tiles"));
        this.itemPedestalWithItem(ModBlocks.CUT_SANDSTONE_ITEM_PEDESTAL, mcLoc("block/cut_sandstone"));
        this.itemPedestalWithItem(ModBlocks.CUT_RED_SANDSTONE_ITEM_PEDESTAL, mcLoc("block/cut_red_sandstone"));
        this.itemPedestalWithItem(ModBlocks.PRISMARINE_BRICK_ITEM_PEDESTAL, mcLoc("block/prismarine_bricks"));
        this.itemPedestalWithItem(ModBlocks.NETHER_BRICK_ITEM_PEDESTAL, mcLoc("block/nether_bricks"));
        this.itemPedestalWithItem(ModBlocks.RED_NETHER_BRICK_ITEM_PEDESTAL, mcLoc("block/red_nether_bricks"));
        this.itemPedestalWithItem(ModBlocks.POLISHED_BLACKSTONE_ITEM_PEDESTAL, mcLoc("block/polished_blackstone"));
        this.itemPedestalWithItem(ModBlocks.POLISHED_BLACKSTONE_BRICK_ITEM_PEDESTAL, mcLoc("block/polished_blackstone_bricks"));
        this.itemPedestalWithItem(ModBlocks.END_STONE_BRICK_ITEM_PEDESTAL, mcLoc("block/end_stone_bricks"));
        this.itemPedestalWithItem(ModBlocks.PURPUR_ITEM_PEDESTAL, mcLoc("block/purpur_block"));
        this.itemPedestalWithItem(ModBlocks.QUARTZ_ITEM_PEDESTAL, mcLoc("block/quartz_block_top"));
        this.itemPedestalWithItem(ModBlocks.SOUL_MAGMA_BRICK_ITEM_PEDESTAL, modLoc("block/soul_magma_bricks"));
        this.tintedItemPedestalWithItem(ModBlocks.OPAL_ITEM_PEDESTAL, modLoc("block/opal"));
        this.tintedItemPedestalWithItem(ModBlocks.OPAL_BRICK_ITEM_PEDESTAL, modLoc("block/opal_bricks"));
        this.tintedItemPedestalWithItem(ModBlocks.CUT_OPAL_BRICK_ITEM_PEDESTAL, modLoc("block/cut_opal_bricks"));
        this.simpleBlockWithItem(ModBlocks.INFUSED_SOUL_SAND.get(), this.cubeAll(ModBlocks.INFUSED_SOUL_SAND.get()));
    }

    private void blockItemModelFromParent(RegistryObject<Block> block, ResourceLocation parent) {
        this.itemModels().withExistingParent(block.getId().getPath(), parent);
    }

    private void tintedBlockWithItem(RegistryObject<Block> block) {
        String id = block.getId().getPath();
        ResourceLocation model = modLoc("block/" + id);
        this.simpleBlockWithItem(block.get(), this.models().withExistingParent(id, modLoc("block/cube_all_tinted"))
                .texture("all", model)
                .texture("particle", model));
    }

    private void tintedSlabWithItem(RegistryObject<Block> slab, ResourceLocation texture) {
        String id = slab.getId().getPath();
        this.slabBlock((SlabBlock) slab.get(),
                this.models().withExistingParent(id, modLoc("block/slab_tinted"))
                        .texture("bottom", texture)
                        .texture("top", texture)
                        .texture("side", texture),
                this.models().withExistingParent(id + "_top", modLoc("block/slab_top_tinted"))
                        .texture("bottom", texture)
                        .texture("top", texture)
                        .texture("side", texture),
                this.models().getExistingFile(texture));
        this.blockItemModelFromParent(slab, modLoc("block/" + id));
    }

    private void tintedStairsWithItem(RegistryObject<Block> stairs, ResourceLocation texture) {
        String id = stairs.getId().getPath();
        this.stairsBlock((StairBlock) stairs.get(),
                this.models().withExistingParent(id, modLoc("block/stairs_tinted"))
                        .texture("bottom", texture)
                        .texture("top", texture)
                        .texture("side", texture),
                this.models().withExistingParent(id + "_inner", modLoc("block/inner_stairs_tinted"))
                        .texture("bottom", texture)
                        .texture("top", texture)
                        .texture("side", texture),
                this.models().withExistingParent(id + "_outer", modLoc("block/outer_stairs_tinted"))
                        .texture("bottom", texture)
                        .texture("top", texture)
                        .texture("side", texture));
        this.blockItemModelFromParent(stairs, modLoc("block/" + id));
    }

    private void tintedWallWithItem(RegistryObject<Block> wall, ResourceLocation texture) {
        String id = wall.getId().getPath();
        this.wallBlock((WallBlock) wall.get(),
                this.models().withExistingParent(id + "_post", modLoc("block/template_wall_post_tinted"))
                        .texture("wall", texture),
                this.models().withExistingParent(id + "_side", modLoc("block/template_wall_side_tinted"))
                        .texture("wall", texture),
                this.models().withExistingParent(id + "_side_tall", modLoc("block/template_wall_side_tall_tinted"))
                        .texture("wall", texture));
        this.models().withExistingParent(id + "_inventory", modLoc("block/wall_inventory_tinted"))
                .texture("wall", texture);
        this.itemModels().withExistingParent(id, modLoc("block/wall_inventory_tinted"))
                .texture("wall", texture);
    }

    private void torchflower(RegistryObject<Block> flower, RegistryObject<Block> crop, RegistryObject<Block> pot) {
        this.simpleBlock(flower.get(), this.models().withExistingParent(flower.getId().getPath(), mcLoc("block/cross"))
                .renderType("cutout")
                .texture("cross", modLoc("block/" + flower.getId().getPath())));
        this.getVariantBuilder(crop.get())
                .forAllStates((state) -> {
                    ResourceLocation cropTexture =  state.getValue(BrightTorchflowerCropBlock.AGE) == 0 ?
                            mcLoc("block/torchflower_crop_stage0") : modLoc("block/" + crop.getId().getPath() + "_stage1");
                    return ConfiguredModel.builder().modelFile(this.models()
                            .withExistingParent(crop.getId().getPath() + "_stage" + state.getValue(BrightTorchflowerCropBlock.AGE),
                                    mcLoc("block/cross"))
                            .renderType("cutout")
                            .texture("cross", cropTexture)).build();
                });
        this.simpleBlock(pot.get(), this.models().withExistingParent(pot.getId().getPath(), mcLoc("block/flower_pot_cross"))
                .renderType("cutout")
                .texture("plant", modLoc("block/" + flower.getId().getPath())));
    }

    private void itemPedestalWithItem(RegistryObject<Block> pedestal, ResourceLocation texture) {
        this.simpleBlockWithItem(pedestal.get(), this.models()
                .withExistingParent(pedestal.getId().getPath(), modLoc("block/item_pedestal"))
                .texture("texture", texture));
        this.blockItemModelFromParent(pedestal, modLoc("block/" + pedestal.getId().getPath()));
    }

    private void tintedItemPedestalWithItem(RegistryObject<Block> pedestal, ResourceLocation texture) {
        this.simpleBlockWithItem(pedestal.get(), this.models()
                .withExistingParent(pedestal.getId().getPath(), modLoc("block/item_pedestal_tinted"))
                .texture("texture", texture));
        this.blockItemModelFromParent(pedestal, modLoc("block/" + pedestal.getId().getPath()));
    }

}
