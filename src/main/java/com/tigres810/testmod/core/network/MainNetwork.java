package com.tigres810.testmod.core.network;

import com.tigres810.testmod.TestMod;
import com.tigres810.testmod.core.network.message.EnergyDispenserBlockMode;
import com.tigres810.testmod.core.network.message.EnergyDispenserBlockPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class MainNetwork {

	public static final String NETWORK_VERSION = "0.1.0";
	
	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(TestMod.MOD_ID, "network"), () -> NETWORK_VERSION, version -> version.equals(NETWORK_VERSION), version -> version.equals(NETWORK_VERSION));
	
	public static void init() {
		CHANNEL.registerMessage(0, EnergyDispenserBlockPosition.class, EnergyDispenserBlockPosition::encode, EnergyDispenserBlockPosition::decode, EnergyDispenserBlockPosition::handle);
		CHANNEL.registerMessage(1, EnergyDispenserBlockMode.class, EnergyDispenserBlockMode::encode, EnergyDispenserBlockMode::decode, EnergyDispenserBlockMode::handle);
	}
}