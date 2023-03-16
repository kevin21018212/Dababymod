package com.goofygoobers.dababymod.core.events;

import com.goofygoobers.dababymod.dababymod;
import com.goofygoobers.dababymod.common.entity.DababyEntity;
import com.goofygoobers.dababymod.common.entity.ExampleEntity;
import com.goofygoobers.dababymod.core.dababyentity;
import com.goofygoobers.dababymod.core.entity;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid=dababymod.MODID,bus= Bus.MOD)
public class CommonModEvents {
 @SubscribeEvent
 public static void registerAttributes(EntityAttributeCreationEvent event) {
	 event.put(entity.EXAMPLE_ENTITY.get(), ExampleEntity.createAttributes().build());
	 event.put(dababyentity.DABABY_ENTITY.get(), DababyEntity.createAttributes().build());
	 
 }
}
