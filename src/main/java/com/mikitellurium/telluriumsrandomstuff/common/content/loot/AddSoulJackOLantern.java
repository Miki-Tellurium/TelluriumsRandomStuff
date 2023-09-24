package com.mikitellurium.telluriumsrandomstuff.common.content.loot;

import com.google.common.base.Suppliers;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

@SuppressWarnings("all")
public class AddSoulJackOLantern extends ItemLootModifier {

    public static final Supplier<Codec<AddSoulJackOLantern>> CODEC = Suppliers.memoize(()
            -> RecordCodecBuilder.create(
            instance -> codecStart(instance).and(instance.group(
                    ForgeRegistries.ITEMS.getCodec().fieldOf("item").forGetter(modifier -> modifier.getItem().asItem()),
                    Codec.INT.fieldOf("chance").forGetter(AddSoulJackOLantern::getChance))
            ).apply(instance, AddSoulJackOLantern::new)));

    private final int addLootAttempts = 3;

    public AddSoulJackOLantern(ResourceLocation lootTable) {
        this(ItemLootModifier.getLootConditions(lootTable), ModBlocks.SOUL_JACK_O_LANTERN.get(), 10);
    }

    private AddSoulJackOLantern(LootItemCondition[] conditionsIn, ItemLike itemLike, int baseChance) {
        super(conditionsIn, itemLike, baseChance);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        RandomSource random = context.getRandom();
        for (int i = 0; i < addLootAttempts ;i++) {
            if (random.nextInt(100) < this.getChance() + random.nextInt(25)) {
                ItemStack lantern = new ItemStack(ModBlocks.SOUL_JACK_O_LANTERN.get());
                generatedLoot.add(lantern);
            }
        }
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }

}
