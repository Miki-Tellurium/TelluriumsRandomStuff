package com.mikitellurium.telluriumsrandomstuff.datagen.providers;

import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.BiConsumer;

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
        this.simpleItem(ModItems.FILTER);
        this.simpleItem(ModItems.BRIGHT_TORCHFLOWER_SEEDS);
        this.simpleItem(ModItems.SOUL_TORCHFLOWER_SEEDS);
        this.simpleItem(ModItems.BLUE_GLOWSTONE_DUST);
        this.itemWithLocation(ModItems.LAVA_GOOGLES, modLoc("item/lava_googles_no_color"))
                .override()
                .predicate(modLoc("colored"), 1)
                .model(this.withExistingParent(ModItems.LAVA_GOOGLES.getId().getPath() + "_colored", mcLoc("item/generated"))
                        .texture("layer0", modLoc("item/lava_googles_frame"))
                        .texture("layer1", modLoc("item/lava_googles_color_layer")))
                .end();
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
        ItemModelBuilder builder1 = this.simpleItem(ModItems.SPIRIT_BOTTLE);
        for (int i = 1; i < 11; i++) {
            builder1.override()
                    .predicate(modLoc("storage"), (float) i / 10)
                    .model(this.itemWithLocation(ModItems.SPIRIT_BOTTLE.getId().getPath() + "_full_" + i, modLoc("item/spirit_bottle_full_" + i)))
                    .end();
        }
        this.simpleItem(ModItems.OPAL_COLOR_SHIFTER);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return this.withExistingParent(item.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + item.getId().getPath()));
    }

    private ItemModelBuilder itemWithLocation(RegistryObject<Item> item, ResourceLocation texture) {
        return itemWithLocation(item.getId().getPath(), texture);
    }

    private ItemModelBuilder itemWithLocation(String name, ResourceLocation texture) {
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

    private void coloredItems(String baseName, BiConsumer<String, DyeColor> consumer) {
        for (DyeColor color : DyeColor.values()) {
            consumer.accept(baseName, color);
        }
    }

    private void coloredItem(String name, DyeColor color) {
        String colorName = color.getName();
        this.itemWithLocation(name + "_" + colorName, modLoc("item/" + name + "_" + colorName));
    }
}
