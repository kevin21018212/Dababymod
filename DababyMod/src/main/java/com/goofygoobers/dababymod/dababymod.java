package com.goofygoobers.dababymod;


import com.goofygoobers.dababymod.core.dababyentity;
import com.goofygoobers.dababymod.core.entity;
import com.goofygoobers.dababymod.core.genericItem;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(dababymod.MODID)

public class dababymod {
	
	public static final String MODID = "dababymod";
      public dababymod() {
    	 var bus =FMLJavaModLoadingContext.get().getModEventBus();
    	 genericItem.ITEMS.register(bus);
    	 entity.ENTITIES.register(bus);
    	 dababyentity.ENTITIES.register(bus);
    	 
    	
      }
    
}
