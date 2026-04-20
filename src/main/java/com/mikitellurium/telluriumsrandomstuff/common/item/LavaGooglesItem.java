package com.mikitellurium.telluriumsrandomstuff.common.item;

import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.util.ColorsUtil;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
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
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class LavaGooglesItem extends Item implements Equipable, DyeableLeatherItem, Vanishable {
    public static ResourceLocation OVERLAY_TEXTURE = FastLoc.modLoc("textures/misc/lava_googles_overlay.png");

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

    public void hurtGoogles(ItemStack itemStack, Player player, DamageSource source, float damage) {
        if (!source.is(DamageTypeTags.IS_FIRE) && itemStack.getItem() instanceof LavaGooglesItem) {
            itemStack.hurtAndBreak((int)damage, player, (player1) -> {
                player1.broadcastBreakEvent(EquipmentSlot.HEAD);
            });
        }
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack itemStack, Enchantment enchantment) {
        return enchantment.equals(Enchantments.VANISHING_CURSE) ||
                enchantment.equals(Enchantments.BINDING_CURSE) ||
                enchantment.equals(Enchantments.FIRE_PROTECTION) ||
                enchantment.equals(Enchantments.UNBREAKING) ||
                enchantment.equals(Enchantments.MENDING);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag isAdvanced) {
        if (this.hasCustomColor(itemStack)) {
            int color = getColor(itemStack);
            MutableComponent colorString = Component.literal(String.format(Locale.ROOT, "#%06X", 0xFFFFFF & color)).withStyle((style) -> style.withColor(color));
            MutableComponent textString = Component.translatable("item.telluriumsrandomstuff.tooltip.color").withStyle(ChatFormatting.GRAY);
            components.add(textString.append(": ").append(colorString));
        }
    }

    @Override
    public int getColor(ItemStack itemStack) {
        CompoundTag tag = itemStack.getTagElement("display");
        return tag != null && tag.contains("color", 99) ? tag.getInt("color") : 16777215;
    }

    public static int getItemTintColor(ItemStack itemStack, int tintIndex) {
        if (tintIndex == 1 && itemStack.getItem() instanceof DyeableLeatherItem dyeable) {
            if (itemStack.getHoverName().getString().equals("tellurio_")) {
                return 0x0080FD;
            }
            return dyeable.hasCustomColor(itemStack) ? dyeable.getColor(itemStack) : ColorsUtil.ALPHA_0;
        } else {
            return ColorsUtil.BLANK;
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
                    graphics.blit(OVERLAY_TEXTURE, 0, 0, -90, 0.0F, 0.0F, width, height, width, height);
                    RenderSystem.disableBlend();
                    graphics.flush();
                }
            }
        });
    }

    /* Events */
    @SubscribeEvent
    public static void modifyTooltip(ItemTooltipEvent event) {
        ItemStack itemStack = event.getItemStack();
        if (itemStack.is(ModItems.LAVA_GOOGLES.get())) {
            List<Component> list = event.getToolTip();
            list.stream()
                    .filter((c) -> c.getContents() instanceof TranslatableContents contents && contents.getKey().equals("item.color"))
                    .findAny()
                    .ifPresent(list::remove);
        }
    }


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
    public static void onMobSpawn(MobSpawnEvent.FinalizeSpawn event) {
        if (event.getLevel().isClientSide()) {
            return;
        }
        RandomSource random = event.getLevel().getRandom();
        if (random.nextInt(spawnWithGooglesChance) == 0) {
            Entity entity = event.getEntity();
            if (entity instanceof Zombie || entity instanceof AbstractSkeleton || entity instanceof AbstractPiglin) {
                ItemStack googles = new ItemStack(ModItems.LAVA_GOOGLES.get());
                int color = ColorsUtil.getRandomRgb(random);
                ((LavaGooglesItem)googles.getItem()).setColor(googles, color);
                if (random.nextFloat() < 0.40f) {
                    EnchantmentHelper.enchantItem(random, googles, 10 + random.nextInt(20), true);
                }
                entity.setItemSlot(EquipmentSlot.HEAD, googles);
            }
        }
    }
}
