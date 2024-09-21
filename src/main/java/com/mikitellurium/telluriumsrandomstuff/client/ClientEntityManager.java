package com.mikitellurium.telluriumsrandomstuff.client;

import com.google.common.collect.Maps;
import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.mixin.MobAccessor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

@Mod.EventBusSubscriber(modid = TelluriumsRandomStuffMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientEntityManager {

    private static final Map<EntityType<?>, Entity> ENTITIES = Maps.newHashMap();
    private static boolean FROZEN = false;

    public static Entity getEntityForType(EntityType<?> entityType) {
        return ENTITIES.get(entityType);
    }

    private static void populate(Level level) {
        ForgeRegistries.ENTITY_TYPES.getValues().stream()
                .filter(DefaultAttributes::hasSupplier)
                .filter((entityType) -> !entityType.equals(EntityType.PLAYER))
                .forEach((entityType) -> {
                    Entity entity = entityType.create(level);
                     if (finalize(level, entity)) {
                         ENTITIES.put(entityType, entity);
                     }
                });
        TelluriumsRandomStuffMod.LOGGER.info("Created client entities.");
    }

    private static boolean finalize(Level level, Entity entity) {
        if (entity instanceof Mob mob) {
            ((MobAccessor)mob).invokePopulateDefaultEquipmentSlots(level.random, level.getCurrentDifficultyAt(BlockPos.ZERO));
            removeArmor(mob);
            setCustomProperties(mob);
            return true;
        }
        return false;
    }

    private static void setCustomProperties(Mob mob) {
        if (mob instanceof Axolotl axolotl) {
            axolotl.setVariant(Axolotl.Variant.CYAN);
        } else if (mob instanceof Frog frog) {
            frog.setVariant(FrogVariant.COLD);
        } else if (mob instanceof Piglin piglin) {
            piglin.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.GOLDEN_SWORD));
        } else if (mob instanceof EnderDragon enderDragon) {
            setupPose(enderDragon, 2);
        } else if (mob instanceof Guardian guardian) {
            setupPose(guardian, 3);
        }
    }

    private static void setupPose(Entity entity, int ticks) {
        for (int i = 0; i < ticks; i++) {
            entity.tick(); // Set correct pose
        }
    }

    private static void removeArmor(Mob mob) {
        for(EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() == EquipmentSlot.Type.ARMOR) {
                mob.setItemSlot(slot, ItemStack.EMPTY);
            }
        }
    }

    private static void clear() {
        ENTITIES.clear();
        TelluriumsRandomStuffMod.LOGGER.info("Cleared client entities.");
    }

    @SubscribeEvent
    public static void onClientLevelLoad(LevelEvent.Load event) {
        if (event.getLevel() instanceof ClientLevel && !FROZEN) {
            populate((ClientLevel) event.getLevel());
            FROZEN = true;
        }
    }

    @SubscribeEvent
    public static void onServedStopping(ServerStoppingEvent event) {
        clear();
        FROZEN = false;
    }

}
