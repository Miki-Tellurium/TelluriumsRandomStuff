package com.mikitellurium.telluriumsrandomstuff.datagen.providers;

import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.Util;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class ModItemModelProvider extends ItemModelProvider {

    private static final Map<String, Float> TRIM_MATERIALS = Util.make(new HashMap<>(), (map) -> {
                map.put("quartz", 0.1F);
                map.put("iron", 0.2F);
                map.put("netherite", 0.3F);
                map.put("redstone", 0.4F);
                map.put("copper", 0.5F);
                map.put("gold", 0.6F );
                map.put("emerald", 0.7F);
                map.put("diamond", 0.8F);
                map.put("lapis", 0.9F );
                map.put("amethyst", 1.0F);
    });

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
        this.simpleItem(ModItems.SPIRITED_IRON_INGOT);
        this.handheldItem(ModItems.SPIRITED_IRON_ROD);
        this.handheldItem(ModItems.SPIRITED_IRON_SWORD);
        this.handheldItem(ModItems.SPIRITED_IRON_SHOVEL);
        this.handheldItem(ModItems.SPIRITED_IRON_PICKAXE);
        this.handheldItem(ModItems.SPIRITED_IRON_AXE);
        this.handheldItem(ModItems.SPIRITED_IRON_HOE);
        this.simpleItem(ModItems.SPIRITED_IRON_BOOTS);
        this.simpleItem(ModItems.SPIRITED_IRON_LEGGINGS);
        this.simpleItem(ModItems.SPIRITED_IRON_CHESTPLATE);
        this.simpleItem(ModItems.SPIRITED_IRON_HELMET);
        this.withExistingParent(ModItems.SOUL_COMPACTOR_LIT.getId().getPath(), modLoc("block/soul_compactor_on"));
        this.withExistingParent(ModItems.SOUL_INFUSER_LIT.getId().getPath(), modLoc("block/soul_infuser_on"));
        this.simpleItem(ModItems.TOTEM_OF_BINDING);
        this.itemWithProperties(ModItems.SPIRIT_BOTTLE.getId().getPath(), modLoc("item/spirit_bottle"))
                .override()
                .predicate(modLoc("storage"), 1)
                .model(this.itemWithProperties(ModItems.SPIRIT_BOTTLE.getId().getPath() + "_full", modLoc("item/spirit_bottle_full")))
                .end();
        this.withExistingParent(ModItems.SPIRITED_ALLAY_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        this.simpleItem(ModItems.SPIRITED_ECHO_WAND);
        ItemModelBuilder builder = this.itemWithProperties(ModItems.SPIRITED_ALLAY_ITEM.getId().getPath(), modLoc("item/spirited_allay_item_light_blue"));
        for (DyeColor dyeColor : DyeColor.values()) {
            this.spiritedAllayColor(builder, dyeColor);
        }
    }

    private void simpleItem(RegistryObject<Item> item) {
        this.withExistingParent(item.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + item.getId().getPath()));
    }

    private ItemModelBuilder itemWithProperties(String name, ResourceLocation texture) {
        return this.withExistingParent(name, mcLoc("item/generated"))
                .texture("layer0", texture);
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

    private void spiritedAllayColor(ItemModelBuilder builder, DyeColor color) {
        String name = color.getName();
        builder.override()
                .predicate(modLoc("color"), color.getId())
                .model(this.itemWithProperties(ModItems.SPIRITED_ALLAY_ITEM.getId().getPath() + "_" + name, modLoc("item/spirited_allay_item_" + name)))
                .end();
    }

//    private void trimmableArmor(RegistryObject<Item> armor) {
//        String id = armor.getId().getPath();
//        String type = ((ArmorItem)armor.get()).getType().getName();
//        ItemModelBuilder builder = this.withExistingParent(id, mcLoc("item/generated"))
//                .texture("layer0", modLoc("item/" + id));
//        TRIM_MATERIALS.forEach((key, index) -> {
//            builder.override()
//                    .predicate(mcLoc("trim_type"), index)
//                    .model(this.trimModel(id, type, key))
//                    .end();
//        });
//    }
//
//    private ModelFile trimModel(String id, String type, String trimMaterial) {
//        return this.withExistingParent(id + "_" + trimMaterial + "_trim", mcLoc("item/generated"))
//                .texture("layer0", modLoc("item/" + id))
//                .texture("layer1", mcLoc("trims/items/" + type + "_trim"));
//    }

}
