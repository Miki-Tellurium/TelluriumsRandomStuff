package com.mikitellurium.telluriumsrandomstuff.client.entity.render;

import com.mikitellurium.telluriumsrandomstuff.client.entity.model.SpiritedAllayModel;
import com.mikitellurium.telluriumsrandomstuff.common.entity.SpiritedAllay;
import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class SpiritedAllayRenderer extends MobRenderer<SpiritedAllay, SpiritedAllayModel> {

    private static final ResourceLocation ALLAY_TEXTURE = FastLoc.mcLoc("textures/entity/allay/allay.png");

    public SpiritedAllayRenderer(EntityRendererProvider.Context context) {
        super(context, new SpiritedAllayModel(context.bakeLayer(SpiritedAllayModel.LAYER_LOCATION)), 0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(SpiritedAllay pEntity) {
        return ALLAY_TEXTURE;
    }

    @Override
    protected int getBlockLightLevel(SpiritedAllay spiritedAllay, BlockPos pos) {
        return 15;
    }

}
