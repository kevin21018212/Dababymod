package com.goofygoobers.dababymod.client.renderer;



import com.goofygoobers.dababymod.dababymod;
import com.goofygoobers.dababymod.client.renderer.model.DababyModel;
import com.goofygoobers.dababymod.common.entity.DababyEntity;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;











public class DababyEntityRenderer <Type extends DababyEntity> extends MobRenderer<Type, DababyModel<Type>>{

	private static final ResourceLocation TEXTURE = new ResourceLocation(dababymod.MODID,"textures/entities/dababy.png");
	public DababyEntityRenderer(Context context) {
		super(context, new DababyModel<>(context.bakeLayer(DababyModel.LAYER_LOCATION)),0.5f);
		
	}	

	@Override
	public ResourceLocation getTextureLocation(Type entity) {
		
		return TEXTURE;
	}
}