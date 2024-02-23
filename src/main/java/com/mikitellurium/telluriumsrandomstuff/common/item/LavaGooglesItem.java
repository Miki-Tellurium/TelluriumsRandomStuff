package com.mikitellurium.telluriumsrandomstuff.common.item;

import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class LavaGooglesItem extends Item implements Equipable, Vanishable {

    public static ResourceLocation OVERLAY_TEXTURE = FastLoc.modLoc("textures/misc/lava_googles_overlay.png");
    public static final String TAG_COLOR = "color";

    public LavaGooglesItem() {
        super(new Item.Properties()
                .defaultDurability(64)
                .fireResistant());
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return this.swapWithEquipmentSlot(this, level, player, hand);
    }

    public void hurtGoogles(ItemStack googles, Player player, DamageSource source, float damage) {
        if (!source.is(DamageTypeTags.IS_FIRE) && googles.getItem() instanceof LavaGooglesItem) {
            googles.hurtAndBreak((int)damage, player, (player1) -> {
                player1.broadcastBreakEvent(EquipmentSlot.HEAD);
            });
        }
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.equals(Enchantments.VANISHING_CURSE) ||
                enchantment.equals(Enchantments.BINDING_CURSE) ||
                enchantment.equals(Enchantments.FIRE_PROTECTION) ||
                enchantment.equals(Enchantments.UNBREAKING) ||
                enchantment.equals(Enchantments.MENDING);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components,
                                TooltipFlag isAdvanced) {
        DyeColor dyeColor = getColor(itemStack);
        if (dyeColor != null) {
            MutableComponent colorString = Component.literal(dyeColor.getName())
                    .withStyle((style -> style.withColor(dyeColor.getTextColor())));
            components.add(Component.translatable("item.telluriumsrandomstuff.lava_googles.tooltip.color")
                    .append(": ").append(colorString));
        }
    }

    public static ItemStack setColor(ItemStack itemStack, DyeColor dyeColor) {
        itemStack.getOrCreateTag().putString(TAG_COLOR, dyeColor.getSerializedName());
        return itemStack;
    }

    public static ItemStack setRandomColor(ItemStack itemStack, RandomSource random) {
        return setColor(itemStack, DyeColor.byId(random.nextInt(16)));
    }

    public static DyeColor getColor(ItemStack itemStack) {
        CompoundTag tag = itemStack.getTag();
        if (tag != null && tag.contains(TAG_COLOR)) {
            String colorName = tag.getString(TAG_COLOR);
            return DyeColor.byName(colorName, DyeColor.byId(tag.getInt(TAG_COLOR)));
        } else {
            return null;
        }
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public void renderHelmetOverlay(ItemStack stack, Player player, int width, int height, float partialTick) {
                Minecraft minecraft = Minecraft.getInstance();
                boolean isFirstPerson = minecraft.options.getCameraType().isFirstPerson();
                if (player.getItemBySlot(EquipmentSlot.HEAD).is(stack.getItem()) && !player.isSpectator() && isFirstPerson) {
                    GuiGraphics graphics = new GuiGraphics(minecraft, minecraft.renderBuffers().bufferSource());
                    RenderSystem.enableBlend();
                    graphics.blit(OVERLAY_TEXTURE, 0, 0, -90, 0.0F, 0.0F,
                            width, height, width, height);
                    RenderSystem.disableBlend();
                    graphics.flush();
                }
            }
        });
    }

    /* Events */
    private static final int spawnWithGooglesChance = 256;

    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            ItemStack itemStack = player.getItemBySlot(EquipmentSlot.HEAD);
            if (itemStack.getItem() instanceof LavaGooglesItem googles) {
                googles.hurtGoogles(itemStack, player, event.getSource(), event.getAmount());
            }
        }
    }

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide) {
            return;
        }
        RandomSource random = event.getLevel().getRandom();
        if (random.nextInt(spawnWithGooglesChance) == 0) {
            Entity entity = event.getEntity();
            if (entity instanceof Zombie || entity instanceof AbstractSkeleton || entity instanceof AbstractPiglin) {
                ItemStack googles = new ItemStack(ModItems.LAVA_GOOGLES.get());
                LavaGooglesItem.setRandomColor(googles, random);
                if (random.nextFloat() < 0.40f) {
                    EnchantmentHelper.enchantItem(random, googles, 10 + random.nextInt(20), true);
                }
                entity.setItemSlot(EquipmentSlot.HEAD, googles);
            }
        }
    }

}
