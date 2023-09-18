package com.mikitellurium.telluriumsrandomstuff;

import com.mikitellurium.telluriumsrandomstuff.common.command.LavaGooglesCommand;
import com.mikitellurium.telluriumsrandomstuff.registry.*;
import com.mikitellurium.telluriumsrandomstuff.common.content.block.interaction.ModCauldronInteractions;
import com.mikitellurium.telluriumsrandomstuff.common.content.block.interaction.ModDispenserBehaviours;
import com.mikitellurium.telluriumsrandomstuff.common.content.block.interaction.ModFluidInteractions;
import com.mikitellurium.telluriumsrandomstuff.common.command.SoulAnchorCommand;
import com.mojang.logging.LogUtils;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(TelluriumsRandomStuffMod.MOD_ID)
public class TelluriumsRandomStuffMod {

    public static final String MOD_ID = "telluriumsrandomstuff";
    public static final Logger LOGGER = LogUtils.getLogger();

    public TelluriumsRandomStuffMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addCreativeTab);
        MinecraftForge.EVENT_BUS.register(this);

        Registries.init(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ModFluidInteractions.register();
        ModCauldronInteractions.register();
        ModDispenserBehaviours.register();
        ((FlowerPotBlock)Blocks.FLOWER_POT).addPlant(ModBlocks.BRIGHT_TORCHFLOWER.getId(), ModBlocks.POTTED_BRIGHT_TORCHFLOWER);
        ((FlowerPotBlock)Blocks.FLOWER_POT).addPlant(ModBlocks.SOUL_TORCHFLOWER.getId(), ModBlocks.POTTED_SOUL_TORCHFLOWER);
    }

    private void addCreativeTab(BuildCreativeModeTabContentsEvent event) {
        ItemStack soulHarvestingI = new ItemStack(Items.ENCHANTED_BOOK);
        ItemStack soulHarvestingII = soulHarvestingI.copy();
        ItemStack soulHarvestingIII = soulHarvestingI.copy();
        EnchantedBookItem.addEnchantment(soulHarvestingI, new EnchantmentInstance(ModEnchantments.SOUL_HARVESTING.get(), 1));
        EnchantedBookItem.addEnchantment(soulHarvestingII, new EnchantmentInstance(ModEnchantments.SOUL_HARVESTING.get(), 2));
        EnchantedBookItem.addEnchantment(soulHarvestingIII, new EnchantmentInstance(ModEnchantments.SOUL_HARVESTING.get(), 3));

        if (event.getTab() == ModCreativeTab.TAB_TELLURIUMSRANDOMSTUFF.get()) {
            event.accept(ModBlocks.GRATE_MAGMA_BLOCK);
            event.accept(ModBlocks.GRATE_SOUL_SAND);
            event.accept(ModBlocks.HYDRODYNAMIC_RAIL);
            event.accept(ModItems.SOUL_LAVA_BUCKET);
            event.accept(ModBlocks.SOUL_MAGMA_BLOCK);
            event.accept(ModBlocks.GRATE_SOUL_MAGMA_BLOCK);
            event.accept(ModBlocks.SOUL_FURNACE);
            event.accept(ModBlocks.SOUL_MAGMA_BRICKS);
            event.accept(ModBlocks.SOUL_MAGMA_BRICK_SLAB);
            event.accept(ModBlocks.SOUL_OBSIDIAN);
            event.accept(ModBlocks.SOUL_ANCHOR);
            event.accept(ModItems.MYSTIC_POTATO);
            event.accept(soulHarvestingI);
            event.accept(soulHarvestingII);
            event.accept(soulHarvestingIII);
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
        }
    }

    @SubscribeEvent
    public void registerCommands(RegisterCommandsEvent event){
        SoulAnchorCommand.register(event.getDispatcher());
        LavaGooglesCommand.register(event.getDispatcher());
    }

}

