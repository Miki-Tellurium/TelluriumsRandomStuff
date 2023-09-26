package com.mikitellurium.telluriumsrandomstuff.common.content.loot;

import com.google.common.base.Suppliers;
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

import java.util.function.Supplier;

public class EntityLootModifier extends ModLootModifier {

    public static final Supplier<Codec<EntityLootModifier>> CODEC = Suppliers.memoize(()
            -> RecordCodecBuilder.create(
            instance -> codecStart(instance).and(
                    ResourceLocation.CODEC.fieldOf("loot_table").forGetter(EntityLootModifier::getLootTable)
            ).apply(instance, EntityLootModifier::new)));

    public EntityLootModifier(ResourceLocation entityLootTable, ResourceLocation lootTable) {
        this(ModLootModifier.getLootConditions(entityLootTable), lootTable);
    }

    private EntityLootModifier(LootItemCondition[] lootConditions, ResourceLocation lootTable) {
        super(lootConditions, lootTable);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        LootTable lootTable = context.getLevel().getServer().getLootData().getLootTable(this.getLootTable());
        Entity killerEntity = context.getParamOrNull(LootContextParams.KILLER_ENTITY);
        LootParams.Builder lootparams$builder = (new LootParams.Builder(context.getLevel()))
                .withParameter(LootContextParams.THIS_ENTITY, context.getParam(LootContextParams.THIS_ENTITY))
                .withParameter(LootContextParams.ORIGIN, context.getParam(LootContextParams.ORIGIN))
                .withParameter(LootContextParams.DAMAGE_SOURCE, context.getParam(LootContextParams.DAMAGE_SOURCE))
                .withOptionalParameter(LootContextParams.KILLER_ENTITY, killerEntity)
                .withOptionalParameter(LootContextParams.DIRECT_KILLER_ENTITY, context.getParamOrNull(LootContextParams.DIRECT_KILLER_ENTITY));
        if (killerEntity instanceof Player player) {
            lootparams$builder = lootparams$builder.withParameter(LootContextParams.LAST_DAMAGE_PLAYER, player)
                    .withLuck(context.getLuck());
        }

        LootParams lootparams = lootparams$builder.create(LootContextParamSets.ENTITY);
        generatedLoot.addAll(lootTable.getRandomItems(lootparams));

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }

}
