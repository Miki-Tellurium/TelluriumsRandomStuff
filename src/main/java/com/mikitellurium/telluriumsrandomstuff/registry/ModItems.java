package com.mikitellurium.telluriumsrandomstuff.registry;

import com.mikitellurium.telluriumsrandomstuff.common.item.GrapplingHookItem;
import com.mikitellurium.telluriumsrandomstuff.common.item.LavaGooglesItem;
import com.mikitellurium.telluriumsrandomstuff.common.item.MoltenAmethystItem;
import com.mikitellurium.telluriumsrandomstuff.common.item.SoulStorageItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class ModItems {

    public static final RegistryObject<Item> SOUL_LAVA_BUCKET = registerItem("soul_lava_bucket",
            () -> new BucketItem(ModFluids.SOUL_LAVA_SOURCE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)) {
                @Override
                public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
                    return 20000;
                }

                @Override
                public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
                    return new FluidBucketWrapper(stack);
                }
            });
    public static final RegistryObject<Item> MYSTIC_POTATO = registerItem("mystic_potato",
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
    public static final RegistryObject<Item> RAW_OPAL_CRYSTAL = registerItem("raw_opal_crystal", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> OPAL_CRYSTAL = registerItem("opal_crystal", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> OPAL_CRYSTAL_SWORD = registerItem("opal_crystal_sword",
            () -> new SwordItem(ModToolTiers.OPAL_CRYSTAL, 3, -2.4F, new Item.Properties()
                    .defaultDurability(250)) {
                @Override
                public boolean isValidRepairItem(ItemStack itemStack, ItemStack repairStack) {
                    return repairStack.is(ModItems.OPAL_CRYSTAL.get());
                }
            });
    public static final RegistryObject<Item> OPAL_CRYSTAL_SHOVEL = registerItem("opal_crystal_shovel",
            () -> new ShovelItem(ModToolTiers.OPAL_CRYSTAL, 1.5F, -3.0F, new Item.Properties()
                    .defaultDurability(250)) {
                @Override
                public boolean isValidRepairItem(ItemStack itemStack, ItemStack repairStack) {
                    return repairStack.is(ModItems.OPAL_CRYSTAL.get());
                }
            });
    public static final RegistryObject<Item> OPAL_CRYSTAL_PICKAXE = registerItem("opal_crystal_pickaxe",
            () -> new PickaxeItem(ModToolTiers.OPAL_CRYSTAL, 1, -2.8F, new Item.Properties()
                    .defaultDurability(250)) {
                @Override
                public boolean isValidRepairItem(ItemStack itemStack, ItemStack repairStack) {
                    return repairStack.is(ModItems.OPAL_CRYSTAL.get());
                }
            });
    public static final RegistryObject<Item> OPAL_CRYSTAL_AXE = registerItem("opal_crystal_axe",
            () -> new AxeItem(ModToolTiers.OPAL_CRYSTAL, 5.0F, -3.0F, new Item.Properties()
                    .defaultDurability(250)) {
                @Override
                public boolean isValidRepairItem(ItemStack itemStack, ItemStack repairStack) {
                    return repairStack.is(ModItems.OPAL_CRYSTAL.get());
                }
            });
    public static final RegistryObject<Item> OPAL_CRYSTAL_HOE = registerItem("opal_crystal_hoe",
            () -> new HoeItem(ModToolTiers.OPAL_CRYSTAL, -3, 0.0F, new Item.Properties()
                    .defaultDurability(250)) {
                @Override
                public boolean isValidRepairItem(ItemStack itemStack, ItemStack repairStack) {
                    return repairStack.is(ModItems.OPAL_CRYSTAL.get());
                }
            });
    public static final RegistryObject<Item> FILTER = registerItem("filter",
            () -> new Item(new Item.Properties()) {
                @Override
                public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
                    return 100;
                }
            });
    public static final RegistryObject<Item> BRIGHT_TORCHFLOWER_SEEDS = registerItem("bright_torchflower_seeds", () -> new ItemNameBlockItem(ModBlocks.BRIGHT_TORCHFLOWER_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> SOUL_TORCHFLOWER_SEEDS = registerItem("soul_torchflower_seeds", () -> new ItemNameBlockItem(ModBlocks.SOUL_TORCHFLOWER_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLUE_GLOWSTONE_DUST = registerItem("blue_glowstone_dust", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LAVA_GOOGLES = registerItem("lava_googles", LavaGooglesItem::new);
    public static final RegistryObject<Item> MOLTEN_AMETHYST = registerItem("molten_amethyst", MoltenAmethystItem::new);
    public static final RegistryObject<Item> AMETHYST_LENS = registerItem("amethyst_lens", () -> new Item(new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> SMALL_SOUL_FRAGMENT = registerItem("small_soul_fragment", () -> new SoulStorageItem(1, 1, new Item.Properties()));
    public static final RegistryObject<Item> SOUL_FRAGMENT = registerItem("soul_fragment", () -> new SoulStorageItem(1, 8, new Item.Properties()));
    public static final RegistryObject<Item> SOUL_CLUSTER = registerItem("soul_cluster", () -> new SoulStorageItem(1, 64, new Item.Properties()));
    public static final RegistryObject<Item> GRAPPLING_HOOK = registerItem("grappling_hook", GrapplingHookItem::new);
    public static final RegistryObject<Item> SPIRITED_IRON_INGOT = registerItem("spirited_iron_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SPIRITED_IRON_ROD = registerItem("spirited_iron_rod", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SPIRITED_IRON_SWORD = registerItem("spirited_iron_sword",
            () -> new SwordItem(ModToolTiers.SPIRITED_IRON, 3, -2.4F, new Item.Properties()
                    .defaultDurability(500)) {
                @Override
                public boolean isValidRepairItem(ItemStack itemStack, ItemStack repairStack) {
                    return repairStack.is(ModItems.SPIRITED_IRON_INGOT.get());
                }
            });
    public static final RegistryObject<Item> SPIRITED_IRON_SHOVEL = registerItem("spirited_iron_shovel",
            () -> new ShovelItem(ModToolTiers.SPIRITED_IRON, 1.5F, -3.0F, new Item.Properties()
                    .defaultDurability(500)) {
                @Override
                public boolean isValidRepairItem(ItemStack itemStack, ItemStack repairStack) {
                    return repairStack.is(ModItems.SPIRITED_IRON_INGOT.get());
                }
            });
    public static final RegistryObject<Item> SPIRITED_IRON_PICKAXE = registerItem("spirited_iron_pickaxe",
            () -> new PickaxeItem(ModToolTiers.SPIRITED_IRON, 1, -2.8F, new Item.Properties()
                    .defaultDurability(500)) {
                @Override
                public boolean isValidRepairItem(ItemStack itemStack, ItemStack repairStack) {
                    return repairStack.is(ModItems.SPIRITED_IRON_INGOT.get());
                }
            });
    public static final RegistryObject<Item> SPIRITED_IRON_AXE = registerItem("spirited_iron_axe",
            () -> new AxeItem(ModToolTiers.SPIRITED_IRON, 5.0F, -3.0F, new Item.Properties()
                    .defaultDurability(500)) {
                @Override
                public boolean isValidRepairItem(ItemStack itemStack, ItemStack repairStack) {
                    return repairStack.is(ModItems.SPIRITED_IRON_INGOT.get());
                }
            });
    public static final RegistryObject<Item> SPIRITED_IRON_HOE = registerItem("spirited_iron_hoe",
            () -> new HoeItem(ModToolTiers.SPIRITED_IRON, -3, 0.0F, new Item.Properties()
                    .defaultDurability(500)) {
                @Override
                public boolean isValidRepairItem(ItemStack itemStack, ItemStack repairStack) {
                    return repairStack.is(ModItems.SPIRITED_IRON_INGOT.get());
                }
            });
    public static final RegistryObject<Item> SPIRITED_IRON_BOOTS = registerItem("spirited_iron_boots", () -> new ArmorItem(ModArmorMaterials.SPIRITED_IRON, ArmorItem.Type.BOOTS, new Item.Properties()));
    public static final RegistryObject<Item> SPIRITED_IRON_LEGGINGS = registerItem("spirited_iron_leggings", () -> new ArmorItem(ModArmorMaterials.SPIRITED_IRON, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> SPIRITED_IRON_CHESTPLATE = registerItem("spirited_iron_chestplate", () -> new ArmorItem(ModArmorMaterials.SPIRITED_IRON, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> SPIRITED_IRON_HELMET = registerItem("spirited_iron_helmet", () -> new ArmorItem(ModArmorMaterials.SPIRITED_IRON, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> TOTEM_OF_BINDING = registerItem("totem_of_binding", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> SPIRIT_BOTTLE = registerItem("spirit_bottle", () -> new SoulStorageItem(16, 8192, new Item.Properties().stacksTo(1)));

    // Items of block states used only for rendering purposes
    public static final RegistryObject<Item> SOUL_INFUSER_LIT = registerItem("soul_infuser_lit", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SOUL_COMPACTOR_LIT = registerItem("soul_compactor_lit", () -> new Item(new Item.Properties()));

    private static <I extends Item> RegistryObject<I> registerItem(String name, Supplier<I> item) {
        return ModRegistries.ITEMS.register(name, item);
    }
    
    protected static void register(IEventBus eventBus) {
        ModRegistries.ITEMS.register(eventBus);
    }

}
