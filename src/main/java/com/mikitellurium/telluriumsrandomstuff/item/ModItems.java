package com.mikitellurium.telluriumsrandomstuff.item;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.fluid.ModFluids;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModItems {

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

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
