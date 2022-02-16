package com.tigres810.testmod.core.init;

import com.tigres810.testmod.TestMod;

import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidInit {

	public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, TestMod.MOD_ID);
	
	// Fluids
	public static final ResourceLocation FLUX_FLUID_ST = new ResourceLocation(TestMod.MOD_ID, "blocks/flux_fluid_still");
	public static final ResourceLocation FLUX_FLUID_FL = new ResourceLocation(TestMod.MOD_ID, "blocks/flux_fluid_flow");
	public static final ResourceLocation FLUX_FLUID_OV = new ResourceLocation(TestMod.MOD_ID, "blocks/flux_fluid_overlay");
	
	public static final RegistryObject<FlowingFluid> FLUX_FLUID = FLUIDS.register("flux_fluid_still", () -> new ForgeFlowingFluid.Source(FluidInit.FLUX_FLUID_PROPERTIES));
	public static final RegistryObject<FlowingFluid> FLUX_FLUID_FLOWING = FLUIDS.register("flux_fluid_flow", () -> new ForgeFlowingFluid.Flowing(FluidInit.FLUX_FLUID_PROPERTIES));
	public static final ForgeFlowingFluid.Properties FLUX_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(() -> FluidInit.FLUX_FLUID.get(), () -> FluidInit.FLUX_FLUID_FLOWING.get(), FluidAttributes.builder(FluidInit.FLUX_FLUID_ST, FluidInit.FLUX_FLUID_FL).density(5).luminosity(3).rarity(Rarity.RARE).overlay(FluidInit.FLUX_FLUID_OV)).block(() -> BlockInit.FLUX_FLUID_BLOCK.get()).bucket(() -> ItemInit.FLUX_FLUID_BUCKET.get());
}