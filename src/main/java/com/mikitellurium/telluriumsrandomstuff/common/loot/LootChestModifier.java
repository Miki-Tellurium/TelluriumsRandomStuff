package com.mikitellurium.telluriumsrandomstuff.common.loot;

import com.google.common.base.Suppliers;
import com.mikitellurium.telluriumsrandomstuff.lib.ModLootModifier;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("all")
public class LootChestModifier extends ModLootModifier {

    public static final Supplier<Codec<LootChestModifier>> CODEC = Suppliers.memoize(()
            -> RecordCodecBuilder.create(
            instance -> codecStart(instance).and(
                    ResourceLocation.CODEC.fieldOf("loot_table").forGetter(LootChestModifier::getLootTable)
            ).apply(instance, LootChestModifier::new)));

    public LootChestModifier(ResourceLocation chestLootTable, ResourceLocation lootTable) {
        this(ModLootModifier.getLootConditions(chestLootTable), lootTable);
    }

    private LootChestModifier(LootItemCondition[] lootConditions, ResourceLocation lootTable) {
        super(lootConditions, lootTable);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        LootTable lootTable = context.getLevel().getServer().getLootData().getLootTable(this.getLootTable());
        LootParams.Builder lootparams$builder = new LootParams.Builder(context.getLevel())
                .withParameter(LootContextParams.ORIGIN, context.getParam(LootContextParams.ORIGIN));
        Entity entity = context.getParamOrNull(LootContextParams.THIS_ENTITY);
        if (entity != null && entity instanceof Player player) {
            lootparams$builder.withLuck(context.getLuck()).withParameter(LootContextParams.THIS_ENTITY, player);
        }
        LootParams lootparams = lootparams$builder.create(LootContextParamSets.CHEST);
        List<ItemStack> randomLoot = lootTable.getRandomItems(lootparams);
        generatedLoot.addAll(randomLoot);

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }

}
