package com.mikitellurium.grateblocksmod.event;

import com.mikitellurium.grateblocksmod.GrateBlocksMod;
import com.mikitellurium.grateblocksmod.block.ModBlocks;
import com.mikitellurium.grateblocksmod.block.custom.CustomBubbleColumnBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GrateBlocksMod.MOD_ID)
public class ModEvents {
    private static boolean wasInBubbleColumn;
    private static boolean firstTick = true;

    @SubscribeEvent
    public static void onBubbleColumnEnterSoundEvent(TickEvent.PlayerTickEvent event) {
        // Play a sound when entering bubble columns
        if (event.phase != TickEvent.Phase.START || event.player == null) return;
        if (event.player.level.isClientSide) {
            BlockState blockstate = event.player.level.getBlockStatesIfLoaded(event.player.getBoundingBox().inflate(0.0D, -0.4F, 0.0D)
                            .deflate(1.0E-6D)).filter((block) -> block.is(ModBlocks.CUSTOM_BUBBLE_COLUMN.get()))
                    .findFirst().orElse(null);
            if (blockstate != null) {
                if (!wasInBubbleColumn && !firstTick && blockstate.is(ModBlocks.CUSTOM_BUBBLE_COLUMN.get()) && !event.player.isSpectator()) {
                    boolean flag = blockstate.getValue(CustomBubbleColumnBlock.DRAG_DOWN);
                    if (flag) {
                        event.player.playSound(SoundEvents.BUBBLE_COLUMN_WHIRLPOOL_INSIDE, 1.0F, 1.0F);
                    } else {
                        event.player.playSound(SoundEvents.BUBBLE_COLUMN_UPWARDS_INSIDE, 1.0F, 1.0F);
                    }
                }

                wasInBubbleColumn = true;
            } else {
                wasInBubbleColumn = false;
            }

            firstTick = false;
        }

    }

    @SubscribeEvent
    public static void insideBubbleColumnBreathing(LivingEvent.LivingUpdateEvent event) {
        // Increase entity air supply when inside bubble column
        LivingEntity entity = event.getEntityLiving();
        if (entity.level.getBlockState(new BlockPos(entity.getX(), entity.getEyeY(), entity.getZ())).is(ModBlocks.CUSTOM_BUBBLE_COLUMN.get())) {
            if (entity.getAirSupply() < entity.getMaxAirSupply()) {
                entity.setAirSupply(Math.min(entity.getAirSupply() + 5, entity.getMaxAirSupply()));
            }
        }
    }

}
