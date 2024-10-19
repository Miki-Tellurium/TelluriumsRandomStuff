package com.mikitellurium.telluriumsrandomstuff.datagen.providers;

import com.mikitellurium.telluriumsrandomstuff.registry.ModParticles;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ParticleDescriptionProvider;

public class ModParticleProvider extends ParticleDescriptionProvider {

    public ModParticleProvider(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, fileHelper);
    }

    @Override
    protected void addDescriptions() {
        this.sprite(ModParticles.SOUL_LAVA_FALL.get(), FastLoc.mcLoc("drip_fall"));
        this.sprite(ModParticles.SOUL_LAVA_HANG.get(), FastLoc.mcLoc("drip_hang"));
        this.sprite(ModParticles.SOUL_LAVA_LAND.get(), FastLoc.mcLoc("drip_land"));
    }

}
