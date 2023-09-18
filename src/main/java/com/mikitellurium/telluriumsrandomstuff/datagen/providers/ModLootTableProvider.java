package com.mikitellurium.telluriumsrandomstuff.datagen.providers;

import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.LimitCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Set;

public class ModLootTableProvider extends BlockLootSubProvider {

    private ModLootTableProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    public static LootTableProvider create(PackOutput packOutput) {
        return new LootTableProvider(packOutput, Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(ModLootTableProvider::new, LootContextParamSets.BLOCK)));
    }

    @Override
    protected void generate() {
        this.add(ModBlocks.SOUL_LAVA_BLOCK.get(), noDrop());
        this.add(ModBlocks.BLUE_GLOWSTONE.get(), (block) -> createSilkTouchDispatchTable(
                block, this.applyExplosionDecay(block, LootItem.lootTableItem(ModItems.BLUE_GLOWSTONE_DUST.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F)))
                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))
                                .apply(LimitCount.limitCount(IntRange.range(1, 4))))));
        this.dropSelf(ModBlocks.BRIGHT_TORCHFLOWER.get());
        this.add(ModBlocks.BRIGHT_TORCHFLOWER_CROP.get(), this.applyExplosionDecay(ModBlocks.BRIGHT_TORCHFLOWER_CROP.get(),
                LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(ModItems.BRIGHT_TORCHFLOWER_SEEDS.get())))));
        this.dropSelf(ModBlocks.CHISELED_OPAL_BRICKS.get());
        this.add(ModBlocks.CRACKED_CUT_OPAL_BRICK_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(ModBlocks.CRACKED_CUT_OPAL_BRICKS.get());
        this.add(ModBlocks.CRACKED_OPAL_BRICK_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(ModBlocks.CRACKED_OPAL_BRICKS.get());
        this.add(ModBlocks.CUT_OPAL_BRICK_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(ModBlocks.CUT_OPAL_BRICK_STAIRS.get());
        this.dropSelf(ModBlocks.CUT_OPAL_BRICK_WALL.get());
        this.dropSelf(ModBlocks.CUT_OPAL_BRICKS.get());
        this.dropSelf(ModBlocks.EXTRACTOR.get());
        this.dropSelf(ModBlocks.GRATE_MAGMA_BLOCK.get());
        this.dropSelf(ModBlocks.GRATE_SOUL_MAGMA_BLOCK.get());
        this.dropSelf(ModBlocks.GRATE_SOUL_SAND.get());
        this.dropSelf(ModBlocks.HYDRODYNAMIC_RAIL.get());
        this.add(ModBlocks.OPAL.get(), (block) -> this.createSingleItemTableWithSilkTouch(block, ModBlocks.OPAL_COBBLESTONE.get()));
        this.add(ModBlocks.OPAL_BRICK_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(ModBlocks.OPAL_BRICK_STAIRS.get());
        this.dropSelf(ModBlocks.OPAL_BRICK_WALL.get());
        this.dropSelf(ModBlocks.OPAL_BRICKS.get());
        this.dropSelf(ModBlocks.OPAL_BUTTON.get());
        this.dropSelf(ModBlocks.OPAL_COBBLESTONE.get());
        this.add(ModBlocks.OPAL_COBBLESTONE_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(ModBlocks.OPAL_COBBLESTONE_STAIRS.get());
        this.dropSelf(ModBlocks.OPAL_COBBLESTONE_WALL.get());
        this.dropSelf(ModBlocks.OPAL_CRYSTAL_BLOCK.get());
        this.add(ModBlocks.OPAL_CRYSTAL_ORE.get(), (block) -> this.createOreDrop(block, ModItems.RAW_OPAL_CRYSTAL.get()));
        this.dropSelf(ModBlocks.OPAL_PRESSURE_PLATE.get());
        this.add(ModBlocks.OPAL_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(ModBlocks.OPAL_STAIRS.get());
        this.dropPottedContents(ModBlocks.POTTED_BRIGHT_TORCHFLOWER.get());
        this.dropPottedContents(ModBlocks.POTTED_SOUL_TORCHFLOWER.get());
        this.dropSelf(ModBlocks.RAW_OPAL_CRYSTAL_BLOCK.get());
        this.dropSelf(ModBlocks.SOUL_ANCHOR.get());
        this.dropSelf(ModBlocks.SOUL_FURNACE.get());
        this.dropOther(ModBlocks.SOUL_LAVA_CAULDRON.get(), Blocks.CAULDRON);
        this.dropSelf(ModBlocks.SOUL_MAGMA_BLOCK.get());
        this.add(ModBlocks.SOUL_MAGMA_BRICK_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(ModBlocks.SOUL_MAGMA_BRICKS.get());
        this.dropSelf(ModBlocks.SOUL_OBSIDIAN.get());
        this.dropSelf(ModBlocks.SOUL_TORCHFLOWER.get());
        this.add(ModBlocks.SOUL_TORCHFLOWER_CROP.get(), this.applyExplosionDecay(ModBlocks.SOUL_TORCHFLOWER_CROP.get(),
                LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(ModItems.SOUL_TORCHFLOWER_SEEDS.get())))));
        this.dropSelf(ModBlocks.BLUE_REDSTONE_LAMP.get());
        this.dropWhenSilkTouch(ModBlocks.AWAKENED_SCULK_SHRIEKER.get());
        this.dropSelf(ModBlocks.SOUL_JACK_O_LANTERN.get());
        this.dropSelf(ModBlocks.STONE_ITEM_PEDESTAL.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

}
