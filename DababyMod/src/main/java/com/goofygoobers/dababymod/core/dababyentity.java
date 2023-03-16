package com.goofygoobers.dababymod.core;

import com.goofygoobers.dababymod.dababymod;
import com.goofygoobers.dababymod.common.entity.DababyEntity;




import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class dababyentity {
	
	private  dababyentity() {
		
	}
   public static final DeferredRegister<EntityType<?>> ENTITIES =  DeferredRegister.create(ForgeRegistries.ENTITIES, dababymod.MODID);
   public static final RegistryObject<EntityType<DababyEntity>> DABABY_ENTITY = ENTITIES.register("dababy",
		   ()-> EntityType.Builder.of(DababyEntity::new, MobCategory.CREATURE).sized(3f,1f)
		   .build(new ResourceLocation(dababymod.MODID,"dababy").toString()));
}


