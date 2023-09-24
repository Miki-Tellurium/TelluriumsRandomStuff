package com.mikitellurium.telluriumsrandomstuff.common.content.loot;

import com.google.common.base.Suppliers;
import com.mikitellurium.telluriumsrandomstuff.common.content.item.LavaGooglesItem;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class AddLavaGoogles extends ItemLootModifier{

    public static final Supplier<Codec<AddLavaGoogles>> CODEC = Suppliers.memoize(()
            -> RecordCodecBuilder.create(
            instance -> codecStart(instance).and(instance.group(
                    ForgeRegistries.ITEMS.getCodec().fieldOf("item").forGetter(modifier -> modifier.getItem().asItem()),
                    Codec.INT.fieldOf("chance").forGetter(AddLavaGoogles::getChance))
            ).apply(instance, AddLavaGoogles::new)));

    public AddLavaGoogles(ResourceLocation lootTable) {
        this(ItemLootModifier.getLootConditions(lootTable), ModItems.LAVA_GOOGLES.get(), 20);
    }

    private AddLavaGoogles(LootItemCondition[] conditionsIn, ItemLike itemLike, int chance) {
        super(conditionsIn, itemLike, chance);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        RandomSource random = context.getRandom();
        if(random.nextInt(100) < this.getChance()) {
            ItemStack googles = LavaGooglesItem.setColor(new ItemStack(this.getItem()),
                    DyeColor.byId(random.nextInt(16)));
            if (random.nextFloat() < 0.40f) {
                EnchantmentHelper.enchantItem(random, googles, 10 + random.nextInt(20), true);
            }
            generatedLoot.add(googles);
        }
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }

}
