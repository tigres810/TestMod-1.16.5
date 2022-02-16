package com.tigres810.testmod.core.init;

import com.tigres810.testmod.TestMod;
import com.tigres810.testmod.common.blocks.BlockFluxOre;
import com.tigres810.testmod.common.blocks.CauldronBlock;
import com.tigres810.testmod.common.blocks.CreativeEnergyDispenserBlock;
import com.tigres810.testmod.common.blocks.EnergyDispenserBlock;
import com.tigres810.testmod.common.blocks.EnergyMachineChargerBlock;
import com.tigres810.testmod.common.blocks.FluidDispenserBlock;
import com.tigres810.testmod.common.blocks.FluidJarBlock;
import com.tigres810.testmod.common.blocks.FluidTankBlock;
import com.tigres810.testmod.common.blocks.FluidTankChargerBlock;
import com.tigres810.testmod.common.blocks.FluxFlowerBlock;
import com.tigres810.testmod.common.blocks.PlantEnergizerBlock;
import com.tigres810.testmod.common.blocks.TestObjBlock;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.potion.Effects;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TestMod.MOD_ID);
	
	// Blocks
	public static final RegistryObject<Block> TEST_BLOCK = BLOCKS.register("test_block", () -> new Block(AbstractBlock.Properties.of(Material.METAL).strength(1.5f, 6f).sound(SoundType.STONE).harvestLevel(0).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()));
	public static final RegistryObject<FluidTankBlock> FLUIDTANK_BLOCK = BLOCKS.register("fluidtank_block", () -> new FluidTankBlock(AbstractBlock.Properties.of(Material.METAL).strength(5.0f, 2.000f).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()));
	public static final RegistryObject<CauldronBlock> CAULDRON_BLOCK = BLOCKS.register("cauldron_block", () -> new CauldronBlock(AbstractBlock.Properties.of(Material.METAL).strength(10.0f, 6.000f).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()));
	public static final RegistryObject<EnergyDispenserBlock> ENERGYDISPENSER_BLOCK = BLOCKS.register("energydispenser_block", () -> new EnergyDispenserBlock(AbstractBlock.Properties.of(Material.METAL).strength(5.0f, 3.000f).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()));
	public static final RegistryObject<EnergyMachineChargerBlock> ENERGYMACHINECHARGER_BLOCK = BLOCKS.register("energymachinecharger_block", () -> new EnergyMachineChargerBlock(AbstractBlock.Properties.of(Material.METAL).strength(8.0f, 4.000f).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()));
	public static final RegistryObject<FluidDispenserBlock> FLUIDDISPENSER_BLOCK = BLOCKS.register("fluiddispenser_block", () -> new FluidDispenserBlock(AbstractBlock.Properties.of(Material.METAL).strength(5.0f, 3.000f).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()));
	public static final RegistryObject<FluidTankChargerBlock> FLUIDTANKCHARGER_BLOCK = BLOCKS.register("fluidtankcharger_block", () -> new FluidTankChargerBlock(AbstractBlock.Properties.of(Material.METAL).strength(5.0f, 2.000f).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()));
	public static final RegistryObject<PlantEnergizerBlock> PLANTENERGIZER_BLOCK = BLOCKS.register("plantenergizer_block", () -> new PlantEnergizerBlock(AbstractBlock.Properties.of(Material.STONE).strength(1.5f, 6f).sound(SoundType.STONE).harvestLevel(0).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()));
	public static final RegistryObject<FluidJarBlock> FLUIDJAR_BLOCK = BLOCKS.register("fluidjar_block", () -> new FluidJarBlock(AbstractBlock.Properties.copy(BlockInit.FLUIDTANK_BLOCK.get())));
	public static final RegistryObject<CreativeEnergyDispenserBlock> CREATIVEENERGYDISPENSER_BLOCK = BLOCKS.register("creativeenergydispenser_block", () -> new CreativeEnergyDispenserBlock(AbstractBlock.Properties.copy(BlockInit.ENERGYDISPENSER_BLOCK.get())));
	public static final RegistryObject<TestObjBlock> TESTOBJ_BLOCK = BLOCKS.register("testobj_block", () -> new TestObjBlock(AbstractBlock.Properties.copy(BlockInit.FLUIDJAR_BLOCK.get())));
	
	// Fluid Blocks
	public static final RegistryObject<FlowingFluidBlock> FLUX_FLUID_BLOCK = BLOCKS.register("flux_fluid_block", () -> new FlowingFluidBlock(() -> FluidInit.FLUX_FLUID.get(), Block.Properties.of(Material.WATER).noCollission().strength(100.0f).noDrops()));
	
	// Mineral Blocks
	public static final RegistryObject<BlockFluxOre> FLUX_ORE_BLOCK = BLOCKS.register("flux_ore", () -> new BlockFluxOre(AbstractBlock.Properties.of(Material.STONE).strength(8.0F, 4.000f).sound(SoundType.STONE).harvestLevel(1).requiresCorrectToolForDrops()));
	
	// Flower Blocks
	public static final RegistryObject<FluxFlowerBlock> FLUX_FLOWER_BLOCK = BLOCKS.register("flux_flower", () -> new FluxFlowerBlock(Effects.CONDUIT_POWER, 2, AbstractBlock.Properties.copy(Blocks.CORNFLOWER)));
	
	// Flower Pot Blocks
	public static final RegistryObject<FlowerPotBlock> POTTED_FLUX_FLOWER = BLOCKS.register("potted_flux_flower", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BlockInit.FLUX_FLOWER_BLOCK, Properties.of(Material.DECORATION).instabreak().noOcclusion()));
}