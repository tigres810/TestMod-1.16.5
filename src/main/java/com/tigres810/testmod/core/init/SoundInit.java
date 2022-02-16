package com.tigres810.testmod.core.init;

import java.util.function.Supplier;

import com.tigres810.testmod.TestMod;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundInit {

	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, TestMod.MOD_ID);
	
	// Discs
	public static final ResourceLocation MIKU_TELL_WORLD_D = new ResourceLocation(TestMod.MOD_ID, "item.miku_tell_world_disc");
	
	// Sounds
	public static final ResourceLocation MIKU_TELL_WORLD_RL = new ResourceLocation(TestMod.MOD_ID, "item.miku_tell_world_sound");
	
	public static final Supplier<SoundEvent> MIKU_TELL_WORLD = () -> new SoundEvent(SoundInit.MIKU_TELL_WORLD_RL);
	public static final Lazy<SoundEvent> MIKU_TELL_WORLD_DISC_LAZY = Lazy.of(() -> new SoundEvent(SoundInit.MIKU_TELL_WORLD_D));
	public static final RegistryObject<SoundEvent> MIKU_TELL_WORLD_DISC = SOUNDS.register("item.miku_tell_world_disc.disc", SoundInit.MIKU_TELL_WORLD_DISC_LAZY);
}
