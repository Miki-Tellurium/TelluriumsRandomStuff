package com.mikitellurium.telluriumsrandomstuff.datagen.providers;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.util.LogUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TelluriumsRandomStuffMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        this.simpleBlockItem(ModBlocks.HYDRODYNAMIC_RAIL);
        this.simpleBlockItem(ModBlocks.BRIGHT_TORCHFLOWER);
        this.simpleBlockItem(ModBlocks.SOUL_TORCHFLOWER);
        this.simpleItem(ModItems.SOUL_LAVA_BUCKET);
        this.simpleItem(ModItems.MYSTIC_POTATO);
        this.simpleItem(ModItems.RAW_OPAL_CRYSTAL);
        this.simpleItem(ModItems.OPAL_CRYSTAL);
        this.opalTool(ModItems.OPAL_CRYSTAL_SWORD, "opal_crystal_sword_overlay", "opal_crystal_sword_handle");
        this.opalTool(ModItems.OPAL_CRYSTAL_SHOVEL, "opal_crystal_shovel_overlay", "opal_crystal_shovel_handle");
        this.opalTool(ModItems.OPAL_CRYSTAL_PICKAXE, "opal_crystal_pickaxe_overlay", "opal_crystal_pickaxe_hoe_handle");
        this.opalTool(ModItems.OPAL_CRYSTAL_AXE, "opal_crystal_axe_overlay", "opal_crystal_axe_handle");
        this.opalTool(ModItems.OPAL_CRYSTAL_HOE, "opal_crystal_hoe_overlay", "opal_crystal_pickaxe_hoe_handle");
        this.simpleItem(ModItems.FILTER);
        this.simpleItem(ModItems.BRIGHT_TORCHFLOWER_SEEDS);
        this.simpleItem(ModItems.SOUL_TORCHFLOWER_SEEDS);
        this.simpleItem(ModItems.BLUE_GLOWSTONE_DUST);
        this.withExistingParent(ModItems.LAVA_GOOGLES.getId().getPath(), mcLoc("item/handheld"))
                .texture("layer0", modLoc("item/lava_googles_frame"))
                .texture("layer1", modLoc("item/lava_googles_color_layer"));
        this.simpleItem(ModItems.MOLTEN_AMETHYST);
        this.simpleItem(ModItems.AMETHYST_LENS);
        this.simpleItem(ModItems.SMALL_SOUL_FRAGMENT);
        this.simpleItem(ModItems.SOUL_FRAGMENT);
        this.simpleItem(ModItems.SOUL_CLUSTER);
    }

    private void simpleItem(RegistryObject<Item> item) {
        this.withExistingParent(item.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + item.getId().getPath()));
    }

    private void handheldItem(RegistryObject<Item> item) {
        this.withExistingParent(item.getId().getPath(), mcLoc("item/handheld"))
                .texture("layer0", modLoc("item/" + item.getId().getPath()));
    }

    private void simpleBlockItem(RegistryObject<Block> block) {
        this.withExistingParent(block.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/" + block.getId().getPath()));
    }

    private void opalTool(RegistryObject<Item> item, String overlay, String handle) {
        this.withExistingParent(item.getId().getPath(), mcLoc("item/handheld"))
                .texture("layer0", modLoc("item/" + overlay))
                .texture("layer1", modLoc("item/" + handle));
    }

}
