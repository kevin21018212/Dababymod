package com.goofygoobers.dababymod.core;

import com.goofygoobers.dababymod.dababymod;
import com.goofygoobers.dababymod.common.entity.ExampleEntity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
public final class entity {
	
	private  entity() {
		
	}
   public static final DeferredRegister<EntityType<?>> ENTITIES =  DeferredRegister.create(ForgeRegistries.ENTITIES, dababymod.MODID);
   public static final RegistryObject<EntityType<ExampleEntity>> EXAMPLE_ENTITY = ENTITIES.register("example_entity", 
		   ()-> EntityType.Builder.of(ExampleEntity::new, MobCategory.CREATURE).sized(.8f, .6f)
		   
		   .build(new ResourceLocation(dababymod.MODID,"example_entity").toString()));
		   
}
