package com.mikitellurium.telluriumsrandomstuff.datagen.providers;

import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, FastLoc.modId(), existingFileHelper);
    }

    @Override
    protected void registerModels() {
        this.simpleBlockItem(ModBlocks.HYDRODYNAMIC_RAIL);
        this.simpleBlockItem(ModBlocks.BRIGHT_TORCHFLOWER);
        this.simpleBlockItem(ModBlocks.SOUL_TORCHFLOWER);
        this.simpleItem(ModItems.SOUL_LAVA_BUCKET);
        this.simpleItem(ModItems.MYSTIC_POTATO);
        this.withExistingParent(ModItems.RAW_OPAL_CRYSTAL.getId().getPath(), mcLoc("item/handheld"))
                .texture("layer0", modLoc("item/raw_opal_crystal_layer0"))
                .texture("layer1", modLoc("item/raw_opal_crystal_layer1"));
        this.withExistingParent(ModItems.OPAL_CRYSTAL.getId().getPath(), mcLoc("item/handheld"))
                .texture("layer0", modLoc("item/opal_crystal_layer0"))
                .texture("layer1", modLoc("item/opal_crystal_layer1"));
        this.opalTool(ModItems.OPAL_CRYSTAL_SWORD, "opal_crystal_sword_handle", "opal_crystal_sword_overlay");
        this.opalTool(ModItems.OPAL_CRYSTAL_SHOVEL, "opal_crystal_shovel_handle", "opal_crystal_shovel_overlay");
        this.opalTool(ModItems.OPAL_CRYSTAL_PICKAXE, "opal_crystal_pickaxe_hoe_handle", "opal_crystal_pickaxe_overlay");
        this.opalTool(ModItems.OPAL_CRYSTAL_AXE, "opal_crystal_axe_handle", "opal_crystal_axe_overlay");
        this.opalTool(ModItems.OPAL_CRYSTAL_HOE, "opal_crystal_pickaxe_hoe_handle", "opal_crystal_hoe_overlay");
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
        //this.withExistingParent(ModItems.SOUL_INFUSER_LIT.getId().getPath(), modLoc("block/soul_infuser_on"));
        this.withExistingParent(ModItems.GRAPPLING_HOOK.getId().getPath() + "_string_inventory", mcLoc("item/generated"))
                .texture("layer0", modLoc("item/grappling_hook_string"));
        this.withExistingParent(ModItems.GRAPPLING_HOOK.getId().getPath(), modLoc("item/grappling_hook_handheld"))
                .texture("layer0", modLoc("item/" + ModItems.GRAPPLING_HOOK.getId().getPath()))
                .override()
                .predicate(modLoc("charging"), 1)
                .model(this.getExistingFile(modLoc("grappling_hook_handheld_charging")))
                .end()
                .override()
                .predicate(modLoc("thrown"), 1)
                .model(this.getExistingFile(modLoc("grappling_hook_string")))
                .end();
        this.simpleItem(ModItems.SOUL_INFUSED_IRON_INGOT);
        this.handheldItem(ModItems.SOUL_IRON_ROD);
        this.handheldItem(ModItems.SOUL_INFUSED_IRON_SWORD);
        this.handheldItem(ModItems.SOUL_INFUSED_IRON_SHOVEL);
        this.handheldItem(ModItems.SOUL_INFUSED_IRON_PICKAXE);
        this.handheldItem(ModItems.SOUL_INFUSED_IRON_AXE);
        this.handheldItem(ModItems.SOUL_INFUSED_IRON_HOE);
        this.simpleItem(ModItems.SOUL_INFUSED_IRON_BOOTS);
        this.simpleItem(ModItems.SOUL_INFUSED_IRON_LEGGINGS);
        this.simpleItem(ModItems.SOUL_INFUSED_IRON_CHESTPLATE);
        this.simpleItem(ModItems.SOUL_INFUSED_IRON_HELMET);
        this.withExistingParent(ModBlocks.SOUL_COMPACTOR_LIT.getId().getPath(), modLoc("block/soul_compactor_on"));
        this.withExistingParent(ModBlocks.SOUL_INFUSER_LIT.getId().getPath(), modLoc("block/soul_infuser_on"));
    }

    private void simpleItem(RegistryObject<Item> item) {
        this.withExistingParent(item.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + item.getId().getPath()));
    }

    private void simpleItem(String name) {
        this.withExistingParent(name, mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + name));
    }

    private void handheldItem(RegistryObject<Item> item) {
        this.withExistingParent(item.getId().getPath(), mcLoc("item/handheld"))
                .texture("layer0", modLoc("item/" + item.getId().getPath()));
    }

    private void simpleBlockItem(RegistryObject<Block> block) {
        this.withExistingParent(block.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/" + block.getId().getPath()));
    }

    private void opalTool(RegistryObject<Item> item, String handle, String overlay) {
        this.withExistingParent(item.getId().getPath(), mcLoc("item/handheld"))
                .texture("layer0", modLoc("item/" + overlay + "1"))
                .texture("layer1", modLoc("item/" + overlay + "2"))
                .texture("layer2", modLoc("item/" + handle));
    }

}
