package com.tigres810.testmod.core.init;



import org.lwjgl.glfw.GLFW;

import com.tigres810.testmod.TestMod;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
public class KeybindsInit {
	
	public static KeyBinding changeModeKey;
	
	public static void register(final FMLClientSetupEvent event) {
		changeModeKey = create("change_mode_key", GLFW.GLFW_KEY_C);
		
		ClientRegistry.registerKeyBinding(changeModeKey);
	}
	
	private static KeyBinding create(String name, int key) {
		return new KeyBinding("key." + TestMod.MOD_ID + "." + name, key, "key.category." + TestMod.MOD_ID);
	}

}
