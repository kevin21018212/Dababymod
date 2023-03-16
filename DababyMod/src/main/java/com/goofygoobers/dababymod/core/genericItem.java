package com.goofygoobers.dababymod.core;

import com.goofygoobers.dababymod.dababymod;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class genericItem {
   private genericItem() {}
   
   //Differed register
   public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, dababymod.MODID);
   public static final  RegistryObject<Item> DABABY_ENTITY_SPAWN_EGG = ITEMS.register("dababy_entity_spawn_egg", ()->new ForgeSpawnEggItem(dababyentity.DABABY_ENTITY, 0x1CD2FF, 0xFFFFFF, 
		   new Item.Properties().tab((CreativeModeTab.TAB_MISC))));
   public static final  RegistryObject<Item> DABlOON = ITEMS.register("dabloon", ()->new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC).fireResistant()));
}

