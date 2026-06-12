package com.mikitellurium.telluriumsrandomstuff.datagen.providers;

import com.mikitellurium.telluriumsrandomstuff.common.block.*;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockModelProvider extends BlockStateProvider {

    public ModBlockModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, FastLoc.modId(), existingFileHelper);
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
                            modLoc("block/awakened_sculk_shrieker_can_summon_inner_top") : modLoc("block/awakened_sculk_shrieker_inner_top");
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
        this.itemPedestal(ModBlocks.STONE_ITEM_PEDESTAL, mcLoc("block/stone"));
        this.itemPedestal(ModBlocks.STONE_BRICK_ITEM_PEDESTAL, mcLoc("block/stone_bricks"));
        this.itemPedestal(ModBlocks.MOSSY_STONE_BRICK_ITEM_PEDESTAL, mcLoc("block/mossy_stone_bricks"));
        this.itemPedestal(ModBlocks.POLISHED_GRANITE_ITEM_PEDESTAL, mcLoc("block/polished_granite"));
        this.itemPedestal(ModBlocks.POLISHED_DIORITE_ITEM_PEDESTAL, mcLoc("block/polished_diorite"));
        this.itemPedestal(ModBlocks.POLISHED_ANDESITE_ITEM_PEDESTAL, mcLoc("block/polished_andesite"));
        this.itemPedestal(ModBlocks.POLISHED_DEEPSLATE_ITEM_PEDESTAL, mcLoc("block/polished_deepslate"));
        this.itemPedestal(ModBlocks.DEEPSLATE_BRICK_ITEM_PEDESTAL, mcLoc("block/deepslate_bricks"));
        this.itemPedestal(ModBlocks.DEEPSLATE_TILE_ITEM_PEDESTAL, mcLoc("block/deepslate_tiles"));
        this.itemPedestal(ModBlocks.CUT_SANDSTONE_ITEM_PEDESTAL, mcLoc("block/cut_sandstone"));
        this.itemPedestal(ModBlocks.CUT_RED_SANDSTONE_ITEM_PEDESTAL, mcLoc("block/cut_red_sandstone"));
        this.itemPedestal(ModBlocks.PRISMARINE_BRICK_ITEM_PEDESTAL, mcLoc("block/prismarine_bricks"));
        this.itemPedestal(ModBlocks.NETHER_BRICK_ITEM_PEDESTAL, mcLoc("block/nether_bricks"));
        this.itemPedestal(ModBlocks.RED_NETHER_BRICK_ITEM_PEDESTAL, mcLoc("block/red_nether_bricks"));
        this.itemPedestal(ModBlocks.POLISHED_BLACKSTONE_ITEM_PEDESTAL, mcLoc("block/polished_blackstone"));
        this.itemPedestal(ModBlocks.POLISHED_BLACKSTONE_BRICK_ITEM_PEDESTAL, mcLoc("block/polished_blackstone_bricks"));
        this.itemPedestal(ModBlocks.END_STONE_BRICK_ITEM_PEDESTAL, mcLoc("block/end_stone_bricks"));
        this.itemPedestal(ModBlocks.PURPUR_ITEM_PEDESTAL, mcLoc("block/purpur_block"));
        this.itemPedestal(ModBlocks.QUARTZ_ITEM_PEDESTAL, mcLoc("block/quartz_block_top"));
        this.simpleBlockWithItem(ModBlocks.INFUSED_SOUL_SAND.get(), this.cubeAll(ModBlocks.INFUSED_SOUL_SAND.get()));
        this.getVariantBuilder(ModBlocks.SOUL_INFUSER.get())
                .forAllStates((state) -> {
                    String model = "soul_infuser";
                    String lit = state.getValue(SoulInfuserBlock.LIT) ? "on" : "off";
                    return ConfiguredModel.builder().modelFile(this.models()
                                    .orientableWithBottom(state.getValue(SoulInfuserBlock.LIT) ? model  + "_on" : model,
                                            modLoc("block/" + model + "_side"),
                                            modLoc("block/" + model + "_front_" + lit),
                                            modLoc("block/" + model + "_top"),
                                            modLoc("block/" + model + "_top")))
                            .rotationY((int) state.getValue(SoulFurnaceBlock.FACING).toYRot() + 180)
                            .build();
                });
        this.blockItemModelFromParent(ModBlocks.SOUL_INFUSER, modLoc("block/soul_infuser"));
        MultiPartBlockStateBuilder multiPartBuilder = this.getMultipartBuilder(ModBlocks.ALCHEMIXER.get());
        for (Direction direction : Direction.values()) {
            if (direction == Direction.UP || direction == Direction.DOWN) {
                continue;
            }
            final int yRot = (int) direction.toYRot() + 180;
            multiPartBuilder
                    .part().modelFile(this.models().getExistingFile(modLoc("block/alchemixer"))).rotationY(yRot).addModel().condition(AlchemixerBlock.FACING, direction).end()
                    .part().modelFile(this.models().getExistingFile(modLoc("block/alchemixer_empty0"))).rotationY(yRot).addModel().condition(AlchemixerBlock.HAS_BOTTLE[0], false).condition(AlchemixerBlock.FACING, direction).end()
                    .part().modelFile(this.models().getExistingFile(modLoc("block/alchemixer_empty1"))).rotationY(yRot).addModel().condition(AlchemixerBlock.HAS_BOTTLE[1], false).condition(AlchemixerBlock.FACING, direction).end()
                    .part().modelFile(this.models().getExistingFile(modLoc("block/alchemixer_empty2"))).rotationY(yRot).addModel().condition(AlchemixerBlock.HAS_BOTTLE[2], false).condition(AlchemixerBlock.FACING, direction).end()
                    .part().modelFile(this.models().getExistingFile(modLoc("block/alchemixer_bottle0"))).rotationY(yRot).addModel().condition(AlchemixerBlock.HAS_BOTTLE[0], true).condition(AlchemixerBlock.FACING, direction).end()
                    .part().modelFile(this.models().getExistingFile(modLoc("block/alchemixer_bottle1"))).rotationY(yRot).addModel().condition(AlchemixerBlock.HAS_BOTTLE[1], true).condition(AlchemixerBlock.FACING, direction).end()
                    .part().modelFile(this.models().getExistingFile(modLoc("block/alchemixer_bottle2"))).rotationY(yRot).addModel().condition(AlchemixerBlock.HAS_BOTTLE[2], true).condition(AlchemixerBlock.FACING, direction).end();
        }
        this.itemModels().basicItem(ModBlocks.ALCHEMIXER.get().asItem());
        this.getVariantBuilder(ModBlocks.SOUL_COMPACTOR.get())
                .forAllStates((state) -> {
                    String model = "soul_compactor";
                    String lit = state.getValue(SoulCompactorBlock.LIT) ? "on" : "off";
                    return ConfiguredModel.builder().modelFile(this.models()
                                    .orientableWithBottom(state.getValue(SoulCompactorBlock.LIT) ? model  + "_on" : model,
                                            modLoc("block/" + model + "_side_" + lit),
                                            modLoc("block/" + model + "_front_" + lit),
                                            modLoc("block/" + model + "_side_" + lit),
                                            modLoc("block/" + model + "_side_" + lit)))
                            .rotationY((int) state.getValue(SoulCompactorBlock.FACING).toYRot() + 180)
                            .build();
                });
        this.blockItemModelFromParent(ModBlocks.SOUL_COMPACTOR, modLoc("block/soul_compactor"));
        this.simpleBlockWithItem(ModBlocks.SPIRITED_IRON_BLOCK.get(), this.cubeAll(ModBlocks.SPIRITED_IRON_BLOCK.get()));
        this.simpleBlockWithItem(ModBlocks.SOUL_ASSEMBLY_TABLE.get(), this.models().withExistingParent("soul_assembly_table", mcLoc("block/crafting_table")));
        this.floatingWalkway(ModBlocks.OAK_FLOATING_WALKWAY);
        this.floatingWalkway(ModBlocks.BIRCH_FLOATING_WALKWAY);
        this.floatingWalkway(ModBlocks.SPRUCE_FLOATING_WALKWAY);
        this.floatingWalkway(ModBlocks.JUNGLE_FLOATING_WALKWAY);
        this.floatingWalkway(ModBlocks.ACACIA_FLOATING_WALKWAY);
        this.floatingWalkway(ModBlocks.DARK_OAK_FLOATING_WALKWAY);
        this.floatingWalkway(ModBlocks.CHERRY_FLOATING_WALKWAY);
        this.floatingWalkway(ModBlocks.MANGROVE_FLOATING_WALKWAY);
        this.floatingWalkway(ModBlocks.BAMBOO_FLOATING_WALKWAY);
        this.floatingWalkway(ModBlocks.CRIMSON_FLOATING_WALKWAY);
        this.floatingWalkway(ModBlocks.WARPED_FLOATING_WALKWAY);
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

    private void itemPedestal(RegistryObject<Block> pedestal, ResourceLocation texture) {
        this.simpleBlockWithItem(pedestal.get(), this.models()
                .withExistingParent(pedestal.getId().getPath(), modLoc("block/item_pedestal"))
                .texture("texture", texture));
        this.blockItemModelFromParent(pedestal, modLoc("block/" + pedestal.getId().getPath()));
    }

    private void floatingWalkway(RegistryObject<Block> walkway) {
        this.getVariantBuilder(walkway.get()).forAllStates((state) -> {
            String id = walkway.getId().getPath();
            String model = "block/floating_walkway";
            if (state.getValue(FloatingWalkwayBlock.SUNKEN)) {
                model = model + "_sunken";
                id = id + "_sunken";
            }
            return ConfiguredModel.builder().modelFile(
                            this.models().withExistingParent(id, modLoc(model))
                                    .texture("texture", modLoc("block/" + walkway.getId().getPath())))
                    .rotationY((int) state.getValue(FloatingWalkwayBlock.FACING).toYRot())
                    .build();
        });
        this.blockItemModelFromParent(walkway, modLoc("block/" + walkway.getId().getPath()));
    }
}
