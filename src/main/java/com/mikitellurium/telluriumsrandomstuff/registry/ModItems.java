package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.common.item.GrapplingHookItem;
import com.mikitellurium.telluriumsrandomstuff.common.item.LavaGooglesItem;
import com.mikitellurium.telluriumsrandomstuff.common.item.MoltenAmethystItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
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

    private static final int opaliumToolsDurability = 250;

    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TelluriumsRandomStuffMod.MOD_ID);

    public static final RegistryObject<Item> SOUL_LAVA_BUCKET = ITEMS.register("soul_lava_bucket",
            () -> new BucketItem(ModFluids.SOUL_LAVA_SOURCE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)) {
                @Override
                public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
                    return 20000;
                }
            });

    public static final RegistryObject<Item> MYSTIC_POTATO = ITEMS.register("mystic_potato",
            () -> new Item(new Item.Properties().fireResistant().food(ModFoods.MYSTIC_POTATO)) {
                @Override
                public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
                    if (Screen.hasShiftDown()) {
                        components.add(Component.translatable("item.telluriumsrandomstuff.mystic_potato.tooltip")
                                .withStyle(ChatFormatting.DARK_AQUA));
                    } else {
                        components.add(Component.literal("<Shift>"));
                    }
                }

                @Override
                public boolean isFoil(ItemStack itemStack) {
                    return true;
                }
            });

    public static final RegistryObject<Item> RAW_OPAL_CRYSTAL = ITEMS.register("raw_opal_crystal",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> OPAL_CRYSTAL = ITEMS.register("opal_crystal",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> OPAL_CRYSTAL_SWORD = ITEMS.register("opal_crystal_sword",
            () -> new SwordItem(ModToolTiers.OPAL_CRYSTAL, 3, -2.4F, new Item.Properties()
                    .defaultDurability(opaliumToolsDurability)) {
                @Override
                public boolean isValidRepairItem(ItemStack itemStack, ItemStack repairStack) {
                    return repairStack.is(ModItems.OPAL_CRYSTAL.get());
                }
            });

    public static final RegistryObject<Item> OPAL_CRYSTAL_SHOVEL = ITEMS.register("opal_crystal_shovel",
            () -> new ShovelItem(ModToolTiers.OPAL_CRYSTAL, 1.5F, -3.0F, new Item.Properties()
                    .defaultDurability(opaliumToolsDurability)) {
                @Override
                public boolean isValidRepairItem(ItemStack itemStack, ItemStack repairStack) {
                    return repairStack.is(ModItems.OPAL_CRYSTAL.get());
                }
            });

    public static final RegistryObject<Item> OPAL_CRYSTAL_PICKAXE = ITEMS.register("opal_crystal_pickaxe",
            () -> new PickaxeItem(ModToolTiers.OPAL_CRYSTAL, 1, -2.8F, new Item.Properties()
                    .defaultDurability(opaliumToolsDurability)) {
                @Override
                public boolean isValidRepairItem(ItemStack itemStack, ItemStack repairStack) {
                    return repairStack.is(ModItems.OPAL_CRYSTAL.get());
                }
            });

    public static final RegistryObject<Item> OPAL_CRYSTAL_AXE = ITEMS.register("opal_crystal_axe",
            () -> new AxeItem(ModToolTiers.OPAL_CRYSTAL, 5.0F, -3.0F, new Item.Properties()
                    .defaultDurability(opaliumToolsDurability)) {
                @Override
                public boolean isValidRepairItem(ItemStack itemStack, ItemStack repairStack) {
                    return repairStack.is(ModItems.OPAL_CRYSTAL.get());
                }
            });

    public static final RegistryObject<Item> OPAL_CRYSTAL_HOE = ITEMS.register("opal_crystal_hoe",
            () -> new HoeItem(ModToolTiers.OPAL_CRYSTAL, -3, 0.0F, new Item.Properties()
                    .defaultDurability(opaliumToolsDurability)) {
                @Override
                public boolean isValidRepairItem(ItemStack itemStack, ItemStack repairStack) {
                    return repairStack.is(ModItems.OPAL_CRYSTAL.get());
                }
            });

    public static final RegistryObject<Item> FILTER = ITEMS.register("filter",
            () -> new Item(new Item.Properties()) {
                @Override
                public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
                    return 100;
                }
            });

    public static final RegistryObject<Item> BRIGHT_TORCHFLOWER_SEEDS = ITEMS.register("bright_torchflower_seeds",
            () -> new ItemNameBlockItem(ModBlocks.BRIGHT_TORCHFLOWER_CROP.get(), new Item.Properties()));

    public static final RegistryObject<Item> SOUL_TORCHFLOWER_SEEDS = ITEMS.register("soul_torchflower_seeds",
            () -> new ItemNameBlockItem(ModBlocks.SOUL_TORCHFLOWER_CROP.get(), new Item.Properties()));

    public static final RegistryObject<Item> BLUE_GLOWSTONE_DUST = ITEMS.register("blue_glowstone_dust",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> LAVA_GOOGLES = ITEMS.register("lava_googles", LavaGooglesItem::new);

    public static final RegistryObject<Item> MOLTEN_AMETHYST = ITEMS.register("molten_amethyst", MoltenAmethystItem::new);

    public static final RegistryObject<Item> AMETHYST_LENS = ITEMS.register("amethyst_lens",
            () -> new Item(new Item.Properties().fireResistant()));

    public static final RegistryObject<Item> SMALL_SOUL_FRAGMENT = ITEMS.register("small_soul_fragment",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SOUL_FRAGMENT = ITEMS.register("soul_fragment",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SOUL_CLUSTER = ITEMS.register("soul_cluster",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SOUL_INFUSER_LIT = ITEMS.register("soul_infuser_lit",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> GRAPPLING_HOOK = ITEMS.register("grappling_hook", GrapplingHookItem::new);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
