package com.mikitellurium.telluriumsrandomstuff.common.content.loot;

import com.google.common.base.Suppliers;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class AddSmallSoulFragment extends ItemLootModifier{

    public static final Supplier<Codec<AddSmallSoulFragment>> CODEC = Suppliers.memoize(()
            -> RecordCodecBuilder.create(
            instance -> codecStart(instance).and(instance.group(
                    ForgeRegistries.ITEMS.getCodec().fieldOf("item").forGetter(modifier -> modifier.getItem().asItem()),
                    Codec.INT.fieldOf("chance").forGetter(AddSmallSoulFragment::getChance))
            ).apply(instance, AddSmallSoulFragment::new)));

    public AddSmallSoulFragment(ResourceLocation lootTable, int chance) {
        super(ItemLootModifier.getLootConditions(lootTable), ModItems.SMALL_SOUL_FRAGMENT.get(), chance);
    }

    private AddSmallSoulFragment(LootItemCondition[] conditionsIn, ItemLike itemLike, int baseChance) {
        super(conditionsIn, itemLike, baseChance);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        return super.doApply(generatedLoot, context);
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }

}
