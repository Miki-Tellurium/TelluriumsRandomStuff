package com.mikitellurium.telluriumsrandomstuff.common.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.level.Level;

public class SpiritedAllay extends PathfinderMob {

    public SpiritedAllay(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public AttributeMap getAttributes() {
        return super.getAttributes();
    }

    public float getHoldingItemAnimationProgress(float ticks) {
        return 0;
    }

}
