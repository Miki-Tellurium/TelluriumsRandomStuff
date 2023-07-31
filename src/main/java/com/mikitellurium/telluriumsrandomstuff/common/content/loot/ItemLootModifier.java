package com.mikitellurium.telluriumsrandomstuff.common.content.loot;

import com.google.common.base.Suppliers;
import com.mikitellurium.telluriumsrandomstuff.common.content.item.LavaGooglesItem;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ItemLootModifier extends LootModifier {

    public static final Supplier<Codec<ItemLootModifier>> CODEC = Suppliers.memoize(()
            -> RecordCodecBuilder.create(
            inst -> codecStart(inst).and(ForgeRegistries.ITEMS.getCodec().fieldOf("item")
                    .forGetter(modifier -> modifier.itemLike.asItem())).apply(inst, ItemLootModifier::new)));

    private static final int lootChance = 8;
    private final ItemLike itemLike;

    public ItemLootModifier(LootItemCondition[] conditionsIn, ItemLike itemLike) {
        super(conditionsIn);
        this.itemLike = itemLike;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        RandomSource random = context.getRandom();
        if(random.nextInt(100) < lootChance) {
            if (itemLike.asItem() == ModItems.LAVA_GOOGLES.get()) {
                ItemStack googles = LavaGooglesItem.setColor(new ItemStack(ModItems.LAVA_GOOGLES.get()),
                        DyeColor.byId(random.nextInt(16)));
                if (random.nextFloat() < 0.25f) {
                    EnchantmentHelper.enchantItem(random, googles, 10 + random.nextInt(20), true);
                }
                generatedLoot.add(googles);
            }
        }

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }

}
