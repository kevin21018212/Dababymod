package com.goofygoobers.dababymod.client.event;

import com.goofygoobers.dababymod.dababymod;
import com.goofygoobers.dababymod.client.renderer.DababyEntityRenderer;
import com.goofygoobers.dababymod.client.renderer.ExampleEntityRenderer;
import com.goofygoobers.dababymod.client.renderer.model.DababyModel;
import com.goofygoobers.dababymod.client.renderer.model.ExampleEntityModel;
import com.goofygoobers.dababymod.core.dababyentity;
import com.goofygoobers.dababymod.core.entity;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = dababymod.MODID,bus = Bus.MOD,value = Dist.CLIENT)
public class ClientModEvents {
   private ClientModEvents() {
	   }
   @SubscribeEvent
   public static void clientSetup(EntityRenderersEvent.RegisterLayerDefinitions event) {
	   event.registerLayerDefinition(ExampleEntityModel.LAYER_LOCATION, ExampleEntityModel::createBodyLayer);
	   event.registerLayerDefinition(DababyModel.LAYER_LOCATION, DababyModel::createBodyLayer);
	
   
   }

  
 @SubscribeEvent
 public static void registerRendereds(EntityRenderersEvent.RegisterRenderers event) {
	 event.registerEntityRenderer(entity.EXAMPLE_ENTITY.get(), ExampleEntityRenderer::new);
	 event.registerEntityRenderer(dababyentity.DABABY_ENTITY.get(), DababyEntityRenderer::new);
	
 }
}