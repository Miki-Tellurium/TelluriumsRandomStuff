package com.mikitellurium.telluriumsrandomstuff.common.event;

import com.mikitellurium.telluriumsrandomstuff.TelluriumsRandomStuffMod;
import com.mikitellurium.telluriumsrandomstuff.registry.ModTags;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Collection;

@Mod.EventBusSubscriber(modid = TelluriumsRandomStuffMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LootEvents {
    // todo decrease small soul fragment drop rate
    private static final ResourceLocation SMALL_SOUL_FRAGMENT_MONSTERS = FastLoc.modLoc("entities/small_soul_fragment_monsters");
    private static final ResourceLocation SMALL_SOUL_FRAGMENT_GENERIC = FastLoc.modLoc("entities/small_soul_fragment_generic");
    private static final ResourceLocation SOUL_FRAGMENT_BOSSES = FastLoc.modLoc("entities/soul_fragments_boss");
    private static final ResourceLocation SOUL_FRAGMENT_WARDEN = FastLoc.modLoc("entities/soul_fragments_warden");

    /* Drop death loot */
    @SubscribeEvent
    public static void addSoulFragmentsToDropLoot(LivingDropsEvent event) {
        if (event.getEntity() instanceof Player || event.isCanceled()) return;
        if (event.getEntity().level().isClientSide) return;
        Entity killedEntity = event.getEntity();
        Vec3 deathPos = new Vec3(killedEntity.getX(), killedEntity.getY(), killedEntity.getZ());
        ServerLevel level = (ServerLevel) killedEntity.level();

        DamageSource damageSource = event.getSource();
        LootParams.Builder lootparams$builder = new LootParams.Builder(level)
                .withParameter(LootContextParams.THIS_ENTITY, killedEntity)
                .withParameter(LootContextParams.ORIGIN, deathPos)
                .withParameter(LootContextParams.DAMAGE_SOURCE, damageSource)
                .withOptionalParameter(LootContextParams.KILLER_ENTITY, damageSource.getEntity())
                .withOptionalParameter(LootContextParams.DIRECT_KILLER_ENTITY, damageSource.getDirectEntity());
        if (damageSource.getEntity() instanceof Player player) {
            lootparams$builder = lootparams$builder.withParameter(LootContextParams.LAST_DAMAGE_PLAYER, player)
                    .withLuck(player.getLuck());
        }

        LootParams lootparams = lootparams$builder.create(LootContextParamSets.ENTITY);
        LootTable lootTable = level.getServer().getLootData().getLootTable(queryLootTable(killedEntity));
        Collection<ItemStack> additionalLoot = lootTable.getRandomItems(lootparams);
        event.getDrops().addAll(asEntities(additionalLoot, level, deathPos));
    }

    private static ResourceLocation queryLootTable(Entity entity) {
        if (entity instanceof Warden) {
            return SOUL_FRAGMENT_WARDEN;
        } else if (entity.getType().is(ModTags.EntityTypes.SOUL_FRAGMENT_DROP_BOSS)) {
            return SOUL_FRAGMENT_BOSSES;
        } else if (entity.getType().getCategory() == MobCategory.MONSTER) {
            return SMALL_SOUL_FRAGMENT_MONSTERS;
        } else {
            return SMALL_SOUL_FRAGMENT_GENERIC;
        }
    }

    private static Collection<? extends ItemEntity> asEntities(Collection<ItemStack> loot, Level level, Vec3 vec3) {
        Collection<ItemEntity> entities = new ArrayList<>();
        loot.forEach((stack -> entities.add(new ItemEntity(level, vec3.x, vec3.y, vec3.z, stack))));
        return entities;
    }

}
