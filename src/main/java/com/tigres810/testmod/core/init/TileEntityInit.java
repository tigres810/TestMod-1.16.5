package com.tigres810.testmod.core.init;

import com.tigres810.testmod.TestMod;
import com.tigres810.testmod.common.tileentitys.TileCauldronBlock;
import com.tigres810.testmod.common.tileentitys.TileCreativeEnergyDispenserBlock;
import com.tigres810.testmod.common.tileentitys.TileEnergyDispenserBlock;
import com.tigres810.testmod.common.tileentitys.TileEnergyMachineChargerBlock;
import com.tigres810.testmod.common.tileentitys.TileFluidDispenserBlock;
import com.tigres810.testmod.common.tileentitys.TileFluidJarBlock;
import com.tigres810.testmod.common.tileentitys.TileFluidTankBlock;
import com.tigres810.testmod.common.tileentitys.TileFluidTankChargerBlock;
import com.tigres810.testmod.common.tileentitys.TilePlantEnergizerBlock;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityInit {

	public static final DeferredRegister<TileEntityType<?>> TILEENTITYS = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, TestMod.MOD_ID);
	
	// TileEntitys
	public static final RegistryObject<TileEntityType<TileFluidTankBlock>> FLUIDTANK_BLOCK_TILE = TILEENTITYS.register("fluidtank_block", () -> TileEntityType.Builder.of(TileFluidTankBlock::new, BlockInit.FLUIDTANK_BLOCK.get()).build(null));
	public static final RegistryObject<TileEntityType<TileCauldronBlock>> CAULDRON_BLOCK_TILE = TILEENTITYS.register("cauldron_block", () -> TileEntityType.Builder.of(TileCauldronBlock::new, BlockInit.CAULDRON_BLOCK.get()).build(null));
	public static final RegistryObject<TileEntityType<TileEnergyDispenserBlock>> ENERGYDISPENSER_BLOCK_TILE = TILEENTITYS.register("energydispenser_block", () -> TileEntityType.Builder.of(TileEnergyDispenserBlock::new, BlockInit.ENERGYDISPENSER_BLOCK.get()).build(null));
	public static final RegistryObject<TileEntityType<TileEnergyMachineChargerBlock>> ENERGYMACHINECHARGER_BLOCK_TILE = TILEENTITYS.register("energymachinecharger_block", () -> TileEntityType.Builder.of(TileEnergyMachineChargerBlock::new, BlockInit.ENERGYMACHINECHARGER_BLOCK.get()).build(null));
	public static final RegistryObject<TileEntityType<TileFluidDispenserBlock>> FLUIDDISPENSER_BLOCK_TILE = TILEENTITYS.register("fluiddispenser_block", () -> TileEntityType.Builder.of(TileFluidDispenserBlock::new, BlockInit.FLUIDDISPENSER_BLOCK.get()).build(null));
	public static final RegistryObject<TileEntityType<TileFluidTankChargerBlock>> FLUIDTANKCHARGER_BLOCK_TILE = TILEENTITYS.register("fluidtankcharger_block", () -> TileEntityType.Builder.of(TileFluidTankChargerBlock::new, BlockInit.FLUIDTANKCHARGER_BLOCK.get()).build(null));
	public static final RegistryObject<TileEntityType<TilePlantEnergizerBlock>> PLANTENERGIZER_BLOCK_TILE = TILEENTITYS.register("plantenergizer_block", () -> TileEntityType.Builder.of(TilePlantEnergizerBlock::new, BlockInit.PLANTENERGIZER_BLOCK.get()).build(null));
	public static final RegistryObject<TileEntityType<TileFluidJarBlock>> FLUIDJAR_BLOCK_TILE = TILEENTITYS.register("fluidjar_block", () -> TileEntityType.Builder.of(TileFluidJarBlock::new, BlockInit.FLUIDJAR_BLOCK.get()).build(null));
	public static final RegistryObject<TileEntityType<TileCreativeEnergyDispenserBlock>> CREATIVEENERGYDISPENSER_BLOCK_TILE = TILEENTITYS.register("creativeenergydispenser_block", () -> TileEntityType.Builder.of(TileCreativeEnergyDispenserBlock::new, BlockInit.CREATIVEENERGYDISPENSER_BLOCK.get()).build(null));
	//public static final RegistryObject<TileEntityType<TileTestObjBlock>> TESTOBJ_BLOCK_TILE = TILEENTITYS.register("testobj_block", () -> TileEntityType.Builder.of(TileTestObjBlock::new, BlockInit.TESTOBJ_BLOCK.get()).build(null));
}