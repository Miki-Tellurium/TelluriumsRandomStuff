package com.mikitellurium.telluriumsrandomstuff.client.entity;
// Made with Blockbench 4.9.3

import com.mikitellurium.telluriumsrandomstuff.util.FastLoc;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.Entity;

public class GrapplingHookModel<T extends Entity> extends Model {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			FastLoc.modLoc("grappling_hook"), "main");
	private final ModelPart root;

	public GrapplingHookModel(ModelPart root) {
		super(RenderType::entitySolid);
		this.root = root.getChild("main");
	}

	public static LayerDefinition createLayerDefinition() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -6.0F, -3.0F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(0, 10).addBox(0.0F, -5.0F, -4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 10).addBox(0.0F, -8.0F, -4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 8).addBox(-2.0F, -6.0F, -4.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition up_hook = main.addOrReplaceChild("up_hook", CubeListBuilder.create().texOffs(9, 3).addBox(7.7F, 0.0F, 2.08F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.6981F, -1.5708F));
		PartDefinition down_hook = main.addOrReplaceChild("down_hook", CubeListBuilder.create().texOffs(9, 3).mirror().addBox(-0.27F, 0.0F, -4.99F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.6981F, -1.5708F));
		PartDefinition right_hook = main.addOrReplaceChild("right_hook", CubeListBuilder.create().texOffs(9, 3).mirror().addBox(-4.1F, -6.0F, -1.78F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.6981F, 0.0F));
		PartDefinition left_hook = main.addOrReplaceChild("left_hook", CubeListBuilder.create().texOffs(9, 3).addBox(3.86F, -6.0F, -1.14F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.6981F, 0.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

}