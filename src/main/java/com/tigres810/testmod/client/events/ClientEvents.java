package com.tigres810.testmod.client.events;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tigres810.testmod.TestMod;
import com.tigres810.testmod.core.init.BlockInit;

import net.minecraft.block.Block;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = TestMod.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class ClientEvents {

	@SubscribeEvent
	public static void onEnterFluid(PlayerTickEvent event) {
		if (event.phase == Phase.START && event.player.level.isClientSide) // only proceed if START phase otherwise, will execute twice per tick
        {
			LivingEntity playerEntity = event.player;
			if(playerEntity.isInWater()) {
				World world = playerEntity.level;
				Block blockEntity = world.getBlockState(playerEntity.blockPosition().below()).getBlock();
				Block blockEntity1 = world.getBlockState(playerEntity.blockPosition()).getBlock();
				Block block = BlockInit.FLUX_FLUID_BLOCK.get().getBlock();
				if(blockEntity != null || blockEntity1 != null) {
					if(blockEntity.equals(block) || blockEntity1.equals(block)) {
						double val = 0.028D;
						double underval = 0.02D;
						if(playerEntity.isUnderWater()) { 
							playerEntity.setDeltaMovement(playerEntity.getDeltaMovement().subtract(playerEntity.getDeltaMovement().x, underval, playerEntity.getDeltaMovement().z)); 
						} else {
							playerEntity.setDeltaMovement(playerEntity.getDeltaMovement().subtract(playerEntity.getDeltaMovement().x, val, playerEntity.getDeltaMovement().z)); 
						}
					}
				}
			}
        }
	}
	
	@SubscribeEvent
	public static void onFluidFog(FogColors event) {
		Entity entity = event.getRenderer().getMainCamera().getEntity();
		if(entity.level.isClientSide) {
			FluidState fluidState = event.getRenderer().getMainCamera().getFluidInCamera();
			Block block = BlockInit.FLUX_FLUID_BLOCK.get().getBlock();
			if(fluidState != null) {
				if(fluidState.createLegacyBlock().getBlock().equals(block)) {
					event.setRed(0.1F);
					event.setGreen(0.02F);
					event.setBlue(0.2F);
				}
			}
		}
	}
	
	@SuppressWarnings("resource")
	public static BlockPos lookingAt(){
	    RayTraceResult rt = Minecraft.getInstance().hitResult;

	    double x = (rt.getLocation().x);
	    double y = (rt.getLocation().y);
	    double z = (rt.getLocation().z);

	    double xla = Minecraft.getInstance().player.getLookAngle().x;
	    double yla = Minecraft.getInstance().player.getLookAngle().y;
	    double zla = Minecraft.getInstance().player.getLookAngle().z;

	    if ((x%1==0)&&(xla<0))x-=0.01;
	    if ((y%1==0)&&(yla<0))y-=0.01;
	    if ((z%1==0)&&(zla<0))z-=0.01;

	    BlockPos ps = new BlockPos(x,y,z);
	    //BlockState bl = Minecraft.getInstance().level.getBlockState(ps);

	    return ps;
	}
	
	private static final int WIDTH = 200;
    private static final int HEIGHT = 151;
	
	@SuppressWarnings("resource")
	@SubscribeEvent
	public static void onRenderEnergy(RenderGameOverlayEvent.Post event) {
		if (event.getType() == ElementType.TEXT) {
			FontRenderer renderer = Minecraft.getInstance().font;
			MatrixStack stack = event.getMatrixStack();
			BlockPos pos = lookingAt();
			World worldIn = Minecraft.getInstance().level;
			MainWindow screen = event.getWindow();
			if(!pos.equals(null)) {
				TileEntity te = worldIn.getBlockEntity(pos);
				if(te != null) {
					LazyOptional<IEnergyStorage> energyHandlerCap = te.getCapability(CapabilityEnergy.ENERGY);
					if(energyHandlerCap.isPresent()) {
						IEnergyStorage energyhandler = energyHandlerCap.orElseThrow(IllegalStateException::new);
						
						int relX = (screen.getWidth() - WIDTH) / 2;
				        int relY = (screen.getHeight() - HEIGHT) / 2;
						renderer.draw(stack, "| Energy: " + energyhandler.getEnergyStored() + " |", relX, relY + 10, 0xffffff);
					}
					LazyOptional<IFluidHandler> fluidHandlerCap = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY);
					if(fluidHandlerCap.isPresent()) {
						IFluidHandler fluidHandler = fluidHandlerCap.orElseThrow(IllegalStateException::new);
						
						int relX = (screen.getWidth() - WIDTH) / 2;
				        int relY = (screen.getHeight() - HEIGHT) / 2;
						renderer.draw(stack, "| Fluid: " + fluidHandler.getFluidInTank(fluidHandler.getTanks()).getAmount() + " |", relX, relY + 20, 0xffffff);
					}
				}
			}
		}
	}
}