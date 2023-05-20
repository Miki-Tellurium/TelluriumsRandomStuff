package com.mikitellurium.telluriumsrandomstuff.item;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.fluid.ModFluids;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModItems {

    private static final int opaliumToolsDurability = 2000;

    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TelluriumsRandomStuffMod.MOD_ID);

    public static final RegistryObject<Item> SOUL_LAVA_BUCKET = ITEMS.register("soul_lava_bucket",
            () -> new BucketItem(ModFluids.SOUL_LAVA_SOURCE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final RegistryObject<Item> MYSTIC_POTATO = ITEMS.register("mystic_potato",
            () -> new Item(new Item.Properties().fireResistant().food(ModFoods.MYSTIC_POTATO)) {
                @Override
                public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
                    components.add(Component.translatable("item.description.telluriumsrandomstuff.mystic_potato")
                            .withStyle(ChatFormatting.DARK_AQUA));
                }
            });

    public static final RegistryObject<Item> RAW_OPALIUM = ITEMS.register("raw_opalium",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> OPALIUM_INGOT = ITEMS.register("opalium_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> OPALIUM_SWORD = ITEMS.register("opalium_sword",
            () -> new SwordItem(Tiers.DIAMOND, 3, -2.4F, new Item.Properties()
                    .defaultDurability(opaliumToolsDurability)) {
                @Override
                public boolean isValidRepairItem(ItemStack itemStack, ItemStack repairStack) {
                    return repairStack.is(ModItems.OPALIUM_INGOT.get());
                }
            });

    public static final RegistryObject<Item> OPALIUM_SHOVEL = ITEMS.register("opalium_shovel",
            () -> new ShovelItem(Tiers.DIAMOND, 1.5F, -3.0F, new Item.Properties()
                    .defaultDurability(opaliumToolsDurability)) {
                @Override
                public boolean isValidRepairItem(ItemStack itemStack, ItemStack repairStack) {
                    return repairStack.is(ModItems.OPALIUM_INGOT.get());
                }
            });

    public static final RegistryObject<Item> OPALIUM_PICKAXE = ITEMS.register("opalium_pickaxe",
            () -> new PickaxeItem(Tiers.DIAMOND, 1, -2.8F, new Item.Properties()
                    .defaultDurability(opaliumToolsDurability)) {
                @Override
                public boolean isValidRepairItem(ItemStack itemStack, ItemStack repairStack) {
                    return repairStack.is(ModItems.OPALIUM_INGOT.get());
                }
            });

    public static final RegistryObject<Item> OPALIUM_AXE = ITEMS.register("opalium_axe",
            () -> new AxeItem(Tiers.DIAMOND, 5.0F, -3.0F, new Item.Properties()
                    .defaultDurability(opaliumToolsDurability)) {
                @Override
                public boolean isValidRepairItem(ItemStack itemStack, ItemStack repairStack) {
                    return repairStack.is(ModItems.OPALIUM_INGOT.get());
                }
            });

    public static final RegistryObject<Item> OPALIUM_HOE = ITEMS.register("opalium_hoe",
            () -> new HoeItem(Tiers.DIAMOND, -3, 0.0F, new Item.Properties()
                    .defaultDurability(opaliumToolsDurability)) {
                @Override
                public boolean isValidRepairItem(ItemStack itemStack, ItemStack repairStack) {
                    return repairStack.is(ModItems.OPALIUM_INGOT.get());
                }
            });

    public static final RegistryObject<Item> FILTER = ITEMS.register("filter",
            () -> new Item(new Item.Properties()) {
                @Override
                public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
                    return 100;
                }
            });

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
