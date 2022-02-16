package com.tigres810.testmod.common.world;

import com.tigres810.testmod.TestMod;
import com.tigres810.testmod.common.world.gen.OreGeneration;

import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TestMod.MOD_ID)
public class WorldEvents {
	
	@SubscribeEvent
	public static void biomeLoadingEvent(final BiomeLoadingEvent event) {
		OreGeneration.generateOres(event);
	}

}
