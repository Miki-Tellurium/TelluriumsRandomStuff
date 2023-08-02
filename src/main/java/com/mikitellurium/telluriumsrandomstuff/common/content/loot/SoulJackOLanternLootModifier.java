package com.mikitellurium.telluriumsrandomstuff.common.content.loot;

import com.google.common.base.Suppliers;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class SoulJackOLanternLootModifier extends LootModifier {

    public static final Supplier<Codec<SoulJackOLanternLootModifier>> CODEC = Suppliers.memoize(()
            -> RecordCodecBuilder.create(
            inst -> codecStart(inst).and(ForgeRegistries.ITEMS.getCodec().fieldOf("item")
                    .forGetter(modifier -> modifier.itemLike.asItem())).apply(inst, SoulJackOLanternLootModifier::new)));

    private final int addLootAttempts = 3;
    private final int baseLootChance = 10;
    private final ItemLike itemLike;

    public SoulJackOLanternLootModifier(LootItemCondition[] conditionsIn, ItemLike itemLike) {
        super(conditionsIn);
        this.itemLike = itemLike;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        RandomSource random = context.getRandom();
        for (int i = 0; i < addLootAttempts ;i++) {
            if (random.nextInt(100) < baseLootChance + random.nextInt(25)) {
                ItemStack lantern = new ItemStack(ModBlocks.SOUL_JACK_O_LANTERN.get());
                if (itemLike.asItem() == ModBlocks.SOUL_JACK_O_LANTERN.get().asItem()) {
                    generatedLoot.add(lantern);
                }
            }
        }

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }

}
