package com.mikitellurium.telluriumsrandomstuff.setup;

import com.mikitellurium.telluriumsrandomstuff.api.ModDispenserBehaviours;
import com.mikitellurium.telluriumsrandomstuff.api.potionmixing.PotionMixingManager;
import com.mikitellurium.telluriumsrandomstuff.common.block.AlchemixerBlock;
import com.mikitellurium.telluriumsrandomstuff.common.block.CustomBubbleColumnBlock;
import com.mikitellurium.telluriumsrandomstuff.common.block.SoulAnchorBlock;
import com.mikitellurium.telluriumsrandomstuff.common.block.interaction.ModCauldronInteractions;
import com.mikitellurium.telluriumsrandomstuff.common.block.interaction.ModFluidInteractions;
import com.mikitellurium.telluriumsrandomstuff.common.command.LavaGooglesCommand;
import com.mikitellurium.telluriumsrandomstuff.common.command.SoulAnchorCommand;
import com.mikitellurium.telluriumsrandomstuff.test.bin.SoulStorageCommand;
import com.mikitellurium.telluriumsrandomstuff.common.event.LootEvents;
import com.mikitellurium.telluriumsrandomstuff.common.item.GrapplingHookItem;
import com.mikitellurium.telluriumsrandomstuff.common.item.LavaGooglesItem;
import com.mikitellurium.telluriumsrandomstuff.lib.TickingMenu;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModEntities;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import com.mikitellurium.telluriumsrandomstuff.util.RecipeHelper;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class CommonSetup {

    public static void registerForgeBusEvents() {
        MinecraftForge.EVENT_BUS.addListener(CommonSetup::setup);
        MinecraftForge.EVENT_BUS.addListener(CommonSetup::registerCommands);
        MinecraftForge.EVENT_BUS.register(CustomBubbleColumnBlock.class);
        MinecraftForge.EVENT_BUS.register(SoulAnchorBlock.class);
        MinecraftForge.EVENT_BUS.register(LavaGooglesItem.class);
        MinecraftForge.EVENT_BUS.register(GrapplingHookItem.class);
        MinecraftForge.EVENT_BUS.register(LootEvents.class);
        MinecraftForge.EVENT_BUS.register(AlchemixerBlock.class);
        MinecraftForge.EVENT_BUS.addListener(PotionMixingManager::registerListener);
        MinecraftForge.EVENT_BUS.addListener(CommonSetup::tickMenus);
    }

    public static void registerModBusEvents(IEventBus eventBus) {
        eventBus.addListener(CommonSetup::registerMobAttributes);
    }

    private static void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModFluidInteractions.register();
            ModCauldronInteractions.register();
            ModDispenserBehaviours.register();
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.BRIGHT_TORCHFLOWER.getId(), ModBlocks.POTTED_BRIGHT_TORCHFLOWER);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.SOUL_TORCHFLOWER.getId(), ModBlocks.POTTED_SOUL_TORCHFLOWER);
            BrewingRecipeRegistry.addRecipe(RecipeHelper.WATER_BOTTLE, Ingredient.of(ModItems.BLUE_GLOWSTONE_DUST.get()), RecipeHelper.THICK_POTION);
        });
    }

    private static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal(FastLoc.modId())
                        .requires((sourceStack) -> sourceStack.hasPermission(2));
        dispatcher.register(LavaGooglesCommand.build(builder));
        dispatcher.register(SoulAnchorCommand.build(builder));
        dispatcher.register(SoulStorageCommand.build(builder));
    }

    private static void tickMenus(TickEvent.PlayerTickEvent event) {
        if (event.player instanceof ServerPlayer && event.player.containerMenu instanceof TickingMenu menu && event.phase == TickEvent.Phase.END) {
            menu.tickMenu((ServerPlayer) event.player);
        }
    }

    private static void registerMobAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.SPIRITED_ALLAY.get(), Allay.createAttributes().build());
    }

}
