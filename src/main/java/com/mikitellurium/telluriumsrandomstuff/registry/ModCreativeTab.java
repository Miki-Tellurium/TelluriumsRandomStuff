package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = TelluriumsRandomStuffMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeTab {

    public static final RegistryObject<CreativeModeTab> TAB_TELLURIUMSRANDOMSTUFF = registerTab(
            "creative_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("creativemodetab.telluriumsrandomstuff_creative_tab"))
                    .icon(() -> new ItemStack(ModBlocks.SOUL_FURNACE.get()))
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .build());

    private static <T extends CreativeModeTab> RegistryObject<T> registerTab(String name, Supplier<T> tab) {
        return ModRegistries.CREATIVE_TABS.register(name, tab);
    }

    protected static void register(IEventBus eventBus) {
        ModRegistries.CREATIVE_TABS.register(eventBus);
    }

    @Mod.EventBusSubscriber(modid = TelluriumsRandomStuffMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class RegisterCreativeTabs {
        @SubscribeEvent
        public static void addCreativeTab(BuildCreativeModeTabContentsEvent event) {
            List<ItemStack> soulHarvesting = getEnchantedBooks(ModEnchantments.SOUL_HARVESTING.get());
            List<ItemStack> aerodynamics = getEnchantedBooks(ModEnchantments.AERODYNAMICS.get());

            if (event.getTab() == ModCreativeTab.TAB_TELLURIUMSRANDOMSTUFF.get()) {
                event.accept(ModBlocks.GRATE_MAGMA_BLOCK);
                event.accept(ModBlocks.GRATE_SOUL_SAND);
                event.accept(ModBlocks.HYDRODYNAMIC_RAIL);
                event.accept(ModItems.SOUL_LAVA_BUCKET);
                event.accept(ModBlocks.SOUL_MAGMA_BLOCK);
                event.accept(ModBlocks.GRATE_SOUL_MAGMA_BLOCK);
                event.accept(ModBlocks.SOUL_MAGMA_BRICKS);
                event.accept(ModBlocks.SOUL_MAGMA_BRICK_SLAB);
                event.accept(ModBlocks.SOUL_FURNACE);
                event.accept(ModBlocks.SOUL_INFUSER);
                event.accept(ModBlocks.SOUL_OBSIDIAN);
                event.accept(ModBlocks.SOUL_ANCHOR);
                event.accept(ModItems.TOTEM_OF_BINDING);
                event.accept(ModItems.MYSTIC_POTATO);
                event.acceptAll(soulHarvesting);
                event.accept(ModBlocks.OPAL);
                event.accept(ModBlocks.OPAL_COBBLESTONE);
                event.accept(ModBlocks.OPAL_BRICKS);
                event.accept(ModBlocks.CUT_OPAL_BRICKS);
                event.accept(ModBlocks.CRACKED_OPAL_BRICKS);
                event.accept(ModBlocks.CRACKED_CUT_OPAL_BRICKS);
                event.accept(ModBlocks.CHISELED_OPAL_BRICKS);
                event.accept(ModBlocks.OPAL_SLAB);
                event.accept(ModBlocks.OPAL_COBBLESTONE_SLAB);
                event.accept(ModBlocks.OPAL_BRICK_SLAB);
                event.accept(ModBlocks.CUT_OPAL_BRICK_SLAB);
                event.accept(ModBlocks.CRACKED_OPAL_BRICK_SLAB);
                event.accept(ModBlocks.CRACKED_CUT_OPAL_BRICK_SLAB);
                event.accept(ModBlocks.OPAL_STAIRS);
                event.accept(ModBlocks.OPAL_COBBLESTONE_STAIRS);
                event.accept(ModBlocks.OPAL_BRICK_STAIRS);
                event.accept(ModBlocks.CUT_OPAL_BRICK_STAIRS);
                event.accept(ModBlocks.OPAL_COBBLESTONE_WALL);
                event.accept(ModBlocks.OPAL_BRICK_WALL);
                event.accept(ModBlocks.CUT_OPAL_BRICK_WALL);
                event.accept(ModBlocks.OPAL_PRESSURE_PLATE);
                event.accept(ModBlocks.OPAL_BUTTON);
                event.accept(ModBlocks.OPAL_CRYSTAL_ORE);
                event.accept(ModBlocks.OPAL_CRYSTAL_BLOCK);
                event.accept(ModBlocks.RAW_OPAL_CRYSTAL_BLOCK);
                event.accept(ModItems.RAW_OPAL_CRYSTAL);
                event.accept(ModItems.OPAL_CRYSTAL);
                event.accept(ModItems.OPAL_CRYSTAL_SHOVEL);
                event.accept(ModItems.OPAL_CRYSTAL_PICKAXE);
                event.accept(ModItems.OPAL_CRYSTAL_AXE);
                event.accept(ModItems.OPAL_CRYSTAL_HOE);
                event.accept(ModItems.OPAL_CRYSTAL_SWORD);
                event.accept(ModItems.FILTER);
                event.accept(ModBlocks.EXTRACTOR);
                event.accept(ModBlocks.BRIGHT_TORCHFLOWER);
                event.accept(ModItems.BRIGHT_TORCHFLOWER_SEEDS);
                event.accept(ModBlocks.SOUL_TORCHFLOWER);
                event.accept(ModItems.SOUL_TORCHFLOWER_SEEDS);
                event.accept(ModBlocks.BLUE_GLOWSTONE);
                event.accept(ModItems.BLUE_GLOWSTONE_DUST);
                event.accept(ModBlocks.BLUE_REDSTONE_LAMP);
                event.accept(ModItems.MOLTEN_AMETHYST);
                event.accept(ModItems.AMETHYST_LENS);
                event.accept(ModItems.LAVA_GOOGLES);
                event.accept(ModBlocks.AWAKENED_SCULK_SHRIEKER);
                event.accept(ModBlocks.SOUL_JACK_O_LANTERN);
                event.accept(ModBlocks.STONE_ITEM_PEDESTAL);
                event.accept(ModBlocks.STONE_BRICK_ITEM_PEDESTAL);
                event.accept(ModBlocks.MOSSY_STONE_BRICK_ITEM_PEDESTAL);
                event.accept(ModBlocks.POLISHED_GRANITE_ITEM_PEDESTAL);
                event.accept(ModBlocks.POLISHED_DIORITE_ITEM_PEDESTAL);
                event.accept(ModBlocks.POLISHED_ANDESITE_ITEM_PEDESTAL);
                event.accept(ModBlocks.POLISHED_DEEPSLATE_ITEM_PEDESTAL);
                event.accept(ModBlocks.DEEPSLATE_BRICK_ITEM_PEDESTAL);
                event.accept(ModBlocks.DEEPSLATE_TILE_ITEM_PEDESTAL);
                event.accept(ModBlocks.CUT_SANDSTONE_ITEM_PEDESTAL);
                event.accept(ModBlocks.CUT_RED_SANDSTONE_ITEM_PEDESTAL);
                event.accept(ModBlocks.PRISMARINE_BRICK_ITEM_PEDESTAL);
                event.accept(ModBlocks.NETHER_BRICK_ITEM_PEDESTAL);
                event.accept(ModBlocks.RED_NETHER_BRICK_ITEM_PEDESTAL);
                event.accept(ModBlocks.POLISHED_BLACKSTONE_ITEM_PEDESTAL);
                event.accept(ModBlocks.POLISHED_BLACKSTONE_BRICK_ITEM_PEDESTAL);
                event.accept(ModBlocks.END_STONE_BRICK_ITEM_PEDESTAL);
                event.accept(ModBlocks.PURPUR_ITEM_PEDESTAL);
                event.accept(ModBlocks.QUARTZ_ITEM_PEDESTAL);
                event.accept(ModBlocks.OPAL_ITEM_PEDESTAL);
                event.accept(ModBlocks.OPAL_BRICK_ITEM_PEDESTAL);
                event.accept(ModBlocks.CUT_OPAL_BRICK_ITEM_PEDESTAL);
                event.accept(ModBlocks.SOUL_ASSEMBLY_TABLE);
                event.accept(ModItems.SMALL_SOUL_FRAGMENT);
                event.accept(ModItems.SOUL_FRAGMENT);
                event.accept(ModItems.SOUL_CLUSTER);
                event.accept(ModItems.SPIRIT_BOTTLE);
                event.accept(ModBlocks.SOUL_COMPACTOR);
                event.accept(ModBlocks.INFUSED_SOUL_SAND);
                event.accept(ModBlocks.SPIRITED_IRON_BLOCK);
                event.accept(ModItems.SPIRITED_IRON_INGOT);
                event.accept(ModItems.SPIRITED_IRON_SWORD);
                event.accept(ModItems.SPIRITED_IRON_SHOVEL);
                event.accept(ModItems.SPIRITED_IRON_PICKAXE);
                event.accept(ModItems.SPIRITED_IRON_AXE);
                event.accept(ModItems.SPIRITED_IRON_HOE);
                event.accept(ModItems.SPIRITED_IRON_BOOTS);
                event.accept(ModItems.SPIRITED_IRON_LEGGINGS);
                event.accept(ModItems.SPIRITED_IRON_CHESTPLATE);
                event.accept(ModItems.SPIRITED_IRON_HELMET);
                event.accept(ModItems.SPIRITED_IRON_ROD);
                event.accept(ModBlocks.ALCHEMIXER);
                event.accept(ModItems.GRAPPLING_HOOK);
                event.acceptAll(aerodynamics);
                event.accept(ModItems.SPIRITED_ALLAY_SPAWN_EGG);
                event.accept(ModItems.SPIRITED_ALLAY_ITEM);
                event.accept(ModItems.SPIRITED_ECHO_WAND);
            }
        }

        private static List<ItemStack> getEnchantedBooks(Enchantment enchantment) {
            List<ItemStack> books = new ArrayList<>();
            ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
            for (int i = 1; i <= enchantment.getMaxLevel(); i++) {
                ItemStack instance = book.copy();
                EnchantedBookItem.addEnchantment(instance, new EnchantmentInstance(enchantment, i));
                books.add(instance);
            }
            return books;
        }
    }

}