package com.goofygoobers.dababymod.sounds;

import java.lang.reflect.Field;

import com.goofygoobers.dababymod.dababymod;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = dababymod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DababySounds{




	   public static final SoundEvent letsgo =  createSoundEvent("letsgo");
	   public static final SoundEvent ha =  createSoundEvent("ha");
	   public static final SoundEvent ipullup =  createSoundEvent("ipullup");
	   public static final SoundEvent nocap =  createSoundEvent("nocap");
	   public static final SoundEvent yeah =  createSoundEvent("yeahyeah");
	
			    
    private static SoundEvent createSoundEvent(final String soundName) {
        final ResourceLocation soundID = new ResourceLocation(dababymod.MODID, soundName);
        return new SoundEvent(soundID).setRegistryName(soundID);
    }

    @SubscribeEvent
    public static void registerSoundEvents(final RegistryEvent.Register<SoundEvent> event) {
        try {
            for (Field f : DababySounds.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof SoundEvent) {
                    event.getRegistry().register((SoundEvent) obj);
                } else if (obj instanceof SoundEvent[]) {
                    for (SoundEvent soundEvent : (SoundEvent[]) obj) {
                        event.getRegistry().register(soundEvent);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}