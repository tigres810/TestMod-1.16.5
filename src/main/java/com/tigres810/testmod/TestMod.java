package com.tigres810.testmod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tigres810.testmod.client.renders.RenderCauldronBlock;
import com.tigres810.testmod.client.renders.RenderEnergyDispenserBlock;
import com.tigres810.testmod.client.renders.RenderFluidDispenserBlock;
import com.tigres810.testmod.client.renders.RenderFluidJarBlock;
import com.tigres810.testmod.client.renders.RenderFluidTankBlock;
import com.tigres810.testmod.client.renders.RenderPlantEnergizer;
import com.tigres810.testmod.core.init.BlockInit;
import com.tigres810.testmod.core.init.FluidInit;
import com.tigres810.testmod.core.init.ItemInit;
import com.tigres810.testmod.core.init.KeybindsInit;
import com.tigres810.testmod.core.init.SoundInit;
import com.tigres810.testmod.core.init.TileEntityInit;
import com.tigres810.testmod.core.network.MainNetwork;

import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TestMod.MOD_ID)
public class TestMod
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "tmod";

    public TestMod() {
    	IEventBus Bus = FMLJavaModLoadingContext.get().getModEventBus();
        Bus.addListener(this::setup);
        Bus.addListener(this::doClientStuff);
        Bus.addListener(this::commonSetup);
        
        ItemInit.ITEMS.register(Bus);
        BlockInit.BLOCKS.register(Bus);
        TileEntityInit.TILEENTITYS.register(Bus);
        FluidInit.FLUIDS.register(Bus);
        SoundInit.SOUNDS.register(Bus);
        
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
    	((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(BlockInit.FLUX_FLOWER_BLOCK.get().getRegistryName(), BlockInit.POTTED_FLUX_FLOWER);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    	KeybindsInit.register(event);
    	ClientRegistry.bindTileEntityRenderer(TileEntityInit.FLUIDTANK_BLOCK_TILE.get(), RenderFluidTankBlock::new);
    	ClientRegistry.bindTileEntityRenderer(TileEntityInit.CAULDRON_BLOCK_TILE.get(), RenderCauldronBlock::new);
    	ClientRegistry.bindTileEntityRenderer(TileEntityInit.ENERGYDISPENSER_BLOCK_TILE.get(), RenderEnergyDispenserBlock::new);
    	ClientRegistry.bindTileEntityRenderer(TileEntityInit.FLUIDDISPENSER_BLOCK_TILE.get(), RenderFluidDispenserBlock::new);
    	ClientRegistry.bindTileEntityRenderer(TileEntityInit.PLANTENERGIZER_BLOCK_TILE.get(), RenderPlantEnergizer::new);
    	ClientRegistry.bindTileEntityRenderer(TileEntityInit.FLUIDJAR_BLOCK_TILE.get(), RenderFluidJarBlock::new);
    	
    	event.enqueueWork(() -> {
    		RenderTypeLookup.setRenderLayer(BlockInit.FLUX_FLOWER_BLOCK.get(), RenderType.cutout());
    		RenderTypeLookup.setRenderLayer(BlockInit.POTTED_FLUX_FLOWER.get(), RenderType.cutout());
    		RenderTypeLookup.setRenderLayer(BlockInit.FLUIDJAR_BLOCK.get(), RenderType.cutout());
    		RenderTypeLookup.setRenderLayer(BlockInit.TESTOBJ_BLOCK.get(), RenderType.translucent());
    	});
    }
    
    public static final ItemGroup TAB = new ItemGroup("testTab") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(ItemInit.FLUIDTANK_BLOCK_ITEM.get());
		}
    };
    
    public void commonSetup(final FMLCommonSetupEvent event) {
    	MainNetwork.init();
    }
}
