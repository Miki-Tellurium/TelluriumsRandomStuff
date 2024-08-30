package com.mikitellurium.telluriumsrandomstuff.common.item;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.common.capability.SoulStorage;
import com.mikitellurium.telluriumsrandomstuff.common.capability.SoulStorageCapabilityProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Mod.EventBusSubscriber(modid = TelluriumsRandomStuffMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SoulStorageItem extends Item {

    private static final Style TOOLTIP_STYLE = Style.EMPTY.withColor(0x969696);

    public SoulStorageItem() {
        super(new Item.Properties());
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        LazyOptional<SoulStorage> optional = itemStack.getCapability(SoulStorageCapabilityProvider.INSTANCE);
        if (!optional.isPresent()) {
            components.add(Component.literal("Soul storage missing").withStyle(TOOLTIP_STYLE));
            return;
        }
        optional.ifPresent((soulStorage) -> {
            if (!soulStorage.isEmpty()) {
                soulStorage.getSouls().forEach((key, i) -> {
                    EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(key));
                    if (entityType != null) {
                        MutableComponent entityName = Component.translatable(entityType.getDescriptionId());
                        Component label = entityName.append(": " + i).withStyle(TOOLTIP_STYLE);
                        components.add(label);
                    }
                });
            } else {
                components.add(Component.literal("No soul stored").withStyle(TOOLTIP_STYLE));
            }
        });
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new SoulStorageCapabilityProvider();
    }

}
