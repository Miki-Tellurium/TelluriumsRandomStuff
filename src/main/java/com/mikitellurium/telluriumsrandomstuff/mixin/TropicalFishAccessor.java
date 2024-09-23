package com.mikitellurium.telluriumsrandomstuff.mixin;

import net.minecraft.world.entity.animal.TropicalFish;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TropicalFish.class)
public interface TropicalFishAccessor {

    @Invoker("setPackedVariant")
    void invokeSetPackedVariant(int packedVariant);

}
