package com.mikitellurium.telluriumsrandomstuff.datagen.providers;

import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.registry.ModRegistries;
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

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    private ModBlockLootTableProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    public static LootTableProvider create(PackOutput packOutput) {
        return new LootTableProvider(packOutput, Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK)));
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
        this.dropSelf(ModBlocks.EXTRACTOR.get());
        this.dropSelf(ModBlocks.GRATE_MAGMA_BLOCK.get());
        this.dropSelf(ModBlocks.GRATE_SOUL_MAGMA_BLOCK.get());
        this.dropSelf(ModBlocks.GRATE_SOUL_SAND.get());
        this.dropSelf(ModBlocks.HYDRODYNAMIC_RAIL.get());
        this.dropSelf(ModBlocks.OPAL.get());
        this.dropPottedContents(ModBlocks.POTTED_BRIGHT_TORCHFLOWER.get());
        this.dropPottedContents(ModBlocks.POTTED_SOUL_TORCHFLOWER.get());
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
        this.dropSelf(ModBlocks.STONE_BRICK_ITEM_PEDESTAL.get());
        this.dropSelf(ModBlocks.MOSSY_STONE_BRICK_ITEM_PEDESTAL.get());
        this.dropSelf(ModBlocks.POLISHED_GRANITE_ITEM_PEDESTAL.get());
        this.dropSelf(ModBlocks.POLISHED_DIORITE_ITEM_PEDESTAL.get());
        this.dropSelf(ModBlocks.POLISHED_ANDESITE_ITEM_PEDESTAL.get());
        this.dropSelf(ModBlocks.POLISHED_DEEPSLATE_ITEM_PEDESTAL.get());
        this.dropSelf(ModBlocks.DEEPSLATE_BRICK_ITEM_PEDESTAL.get());
        this.dropSelf(ModBlocks.DEEPSLATE_TILE_ITEM_PEDESTAL.get());
        this.dropSelf(ModBlocks.CUT_SANDSTONE_ITEM_PEDESTAL.get());
        this.dropSelf(ModBlocks.CUT_RED_SANDSTONE_ITEM_PEDESTAL.get());
        this.dropSelf(ModBlocks.PRISMARINE_BRICK_ITEM_PEDESTAL.get());
        this.dropSelf(ModBlocks.NETHER_BRICK_ITEM_PEDESTAL.get());
        this.dropSelf(ModBlocks.RED_NETHER_BRICK_ITEM_PEDESTAL.get());
        this.dropSelf(ModBlocks.POLISHED_BLACKSTONE_ITEM_PEDESTAL.get());
        this.dropSelf(ModBlocks.POLISHED_BLACKSTONE_BRICK_ITEM_PEDESTAL.get());
        this.dropSelf(ModBlocks.END_STONE_BRICK_ITEM_PEDESTAL.get());
        this.dropSelf(ModBlocks.PURPUR_ITEM_PEDESTAL.get());
        this.dropSelf(ModBlocks.QUARTZ_ITEM_PEDESTAL.get());
        this.dropSelf(ModBlocks.INFUSED_SOUL_SAND.get());
        this.dropSelf(ModBlocks.SOUL_INFUSER.get());
        this.dropSelf(ModBlocks.ALCHEMIXER.get());
        this.dropSelf(ModBlocks.SOUL_COMPACTOR.get());
        this.dropSelf(ModBlocks.SPIRITED_IRON_BLOCK.get());
        this.dropSelf(ModBlocks.SOUL_ASSEMBLY_TABLE.get());
        this.dropSelf(ModBlocks.OAK_FLOATING_WALKWAY.get());
        this.dropSelf(ModBlocks.BIRCH_FLOATING_WALKWAY.get());
        this.dropSelf(ModBlocks.SPRUCE_FLOATING_WALKWAY.get());
        this.dropSelf(ModBlocks.JUNGLE_FLOATING_WALKWAY.get());
        this.dropSelf(ModBlocks.ACACIA_FLOATING_WALKWAY.get());
        this.dropSelf(ModBlocks.DARK_OAK_FLOATING_WALKWAY.get());
        this.dropSelf(ModBlocks.MANGROVE_FLOATING_WALKWAY.get());
        this.dropSelf(ModBlocks.CHERRY_FLOATING_WALKWAY.get());
        this.dropSelf(ModBlocks.BAMBOO_FLOATING_WALKWAY.get());
        this.dropSelf(ModBlocks.CRIMSON_FLOATING_WALKWAY.get());
        this.dropSelf(ModBlocks.WARPED_FLOATING_WALKWAY.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModRegistries.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
