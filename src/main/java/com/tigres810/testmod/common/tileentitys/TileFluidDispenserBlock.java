package com.tigres810.testmod.common.tileentitys;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.tigres810.testmod.common.blocks.EnergyDispenserBlock;
import com.tigres810.testmod.common.blocks.FluidDispenserBlock;
import com.tigres810.testmod.core.init.FluidInit;
import com.tigres810.testmod.core.init.TileEntityInit;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class TileFluidDispenserBlock extends TileEntity implements ITickableTileEntity {
	
	protected FluidTank tank = new FluidTank(FluidAttributes.BUCKET_VOLUME * 5) {
		public boolean isFluidValid(net.minecraftforge.fluids.FluidStack stack) {
			return stack.getFluid() == FluidInit.FLUX_FLUID.get();
		};
		
		protected void onContentsChanged() {
			BlockState state = level.getBlockState(worldPosition);
			level.sendBlockUpdated(worldPosition, state, state, Constants.BlockFlags.BLOCK_UPDATE);
			setChanged();
		};
	};
	
	protected final LazyOptional<IFluidHandler> holder = LazyOptional.of(() -> tank);
	
	private int ticks = 0;
	public BlockPos connectedto;
	public Boolean isconnected = false;
	public BlockPos fakeconnected;
	public boolean isfakeconnected;
	
	public Boolean sendEnergy = false;
	
	public TileFluidDispenserBlock() {
		super(TileEntityInit.FLUIDDISPENSER_BLOCK_TILE.get());
	}
	
	double distToTarget;

	@Override
	public void tick() {
		if(!level.isClientSide()) {
			if(ticks >= 200) {
				if(sendEnergy == false) {
					if(getBlockState().getValue(EnergyDispenserBlock.EXTRACT)) {
						TileEntity tank = level.getBlockEntity(worldPosition.below());
						
						if(tank != null) {
							LazyOptional<IFluidHandler> fluidHandlerCap = tank.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY);
							
							if(fluidHandlerCap.isPresent()) {
								IFluidHandler fluidHandler = fluidHandlerCap.orElseThrow(IllegalStateException::new);
								
								if (fluidHandler.drain(1000, IFluidHandler.FluidAction.SIMULATE).getAmount() == 1000) {
									fluidHandler.drain(1000, IFluidHandler.FluidAction.EXECUTE);
									if(getTank().fill(new FluidStack(FluidInit.FLUX_FLUID.get(), 1000), IFluidHandler.FluidAction.EXECUTE) == 1000) {
									}
								}
								ticks = 0;
								sendEnergy = true;
							} else {
								ticks = 0;
								sendEnergy = true;
							}
						} else {
							ticks = 0;
							sendEnergy = true;
						}
					} else {
						ticks = 0;
						sendEnergy = true;
					}
				} else {
					if(getBlockState().getValue(EnergyDispenserBlock.EXTRACT)) {
						if(getConnectedTo() != null && !getConnectedTo().equals(BlockPos.ZERO)) {
							sendFluid();
							sendEnergy = false;
							ticks = 0;
						} else {
							ticks = 0;
							sendEnergy = false;
						}
					} else {
						sendFluidDown();
						sendEnergy = false;
						ticks = 0;
					}
				}
			} else {
				ticks++;
			}
		}
	}
	
	private void sendFluid() {
		AtomicInteger capacity = new AtomicInteger(tank.getFluidAmount());
		if (capacity.get() <= 0) return;
		
		TileEntity te = level.getBlockEntity(connectedto);
		if(te != null ) {
			boolean canContinue = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, Direction.UP).map(handler -> {
				if(handler.fill(new FluidStack(FluidInit.FLUX_FLUID.get(), 1000), IFluidHandler.FluidAction.EXECUTE) == 1000) {
					capacity.addAndGet(-1000);
					getTank().drain(new FluidStack(FluidInit.FLUX_FLUID.get(), 1000), IFluidHandler.FluidAction.EXECUTE);
					return capacity.get() > 0;
				}
				return true;
			}).orElse(true);
			if(!canContinue) return;
		}
	}
	
	private void sendFluidDown() {
		AtomicInteger capacity = new AtomicInteger(tank.getFluidAmount());
		if (capacity.get() <= 0) return;
		
		TileEntity te = level.getBlockEntity(worldPosition.relative(Direction.DOWN));
		if(te != null ) {
			boolean canContinue = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, Direction.UP).map(handler -> {
				if(handler.fill(new FluidStack(FluidInit.FLUX_FLUID.get(), 1000), IFluidHandler.FluidAction.EXECUTE) == 1000) {
					capacity.addAndGet(-1000);
					getTank().drain(new FluidStack(FluidInit.FLUX_FLUID.get(), 1000), IFluidHandler.FluidAction.EXECUTE);
					return capacity.get() > 0;
				}
				return true;
			}).orElse(true);
			if(!canContinue) return;
		}
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        	return holder.cast();
        return super.getCapability(capability, facing);
    }
	
	public void setMode() {
		if(getBlockState().getValue(FluidDispenserBlock.EXTRACT)) {
		    level.setBlock(getBlockPos(), getBlockState().setValue(FluidDispenserBlock.EXTRACT, false).setValue(FluidDispenserBlock.DEPOSIT, true), Constants.BlockFlags.BLOCK_UPDATE);
		} else {
			level.setBlock(getBlockPos(), getBlockState().setValue(FluidDispenserBlock.EXTRACT, true).setValue(FluidDispenserBlock.DEPOSIT, false), Constants.BlockFlags.BLOCK_UPDATE);
		}
	}
	
	public BlockPos getConnectedTo() {
		return connectedto;
	}
	
	public void setConnectedTo(BlockPos pos, boolean fakepos, boolean state) {
		if(!fakepos) {
			this.connectedto = pos;
			this.isconnected = state;
		} else {
			this.fakeconnected = pos;
			this.isfakeconnected = state;
		}
		this.level.sendBlockUpdated(pos, getBlockState(), getBlockState(), 2);
		this.setChanged();
	}
	
	public BlockPos getFakeConnected() {
		return fakeconnected;
	}
	
	public FluidTank getTank() {
        return tank;
    }
	
	@Override
	public SUpdateTileEntityPacket getUpdatePacket()
	{
		CompoundNBT nbtTagCompound = new CompoundNBT();
		save(nbtTagCompound);
		int tileEntityType = 42;  // arbitrary number; only used for vanilla TileEntities.  You can use it, or not, as you want.
		return new SUpdateTileEntityPacket(worldPosition, tileEntityType, nbtTagCompound);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		BlockState blockState = level.getBlockState(worldPosition);
	    load(blockState, pkt.getTag());   // read from the nbt in the packet
	}
	
	@Override
	public CompoundNBT getUpdateTag()
	{
		CompoundNBT nbtTagCompound = new CompoundNBT();
	    save(nbtTagCompound);
	    return nbtTagCompound;
	}
	
	@Override
	public void handleUpdateTag(BlockState blockState, CompoundNBT parentNBTTagCompound)
	{
		load(blockState, parentNBTTagCompound);
	}
	
	@Override
	public CompoundNBT save(CompoundNBT tag) {
		tag = super.save(tag);
		getTank().writeToNBT(tag);
		if(getConnectedTo() != null) {
			tag.putInt("ConnectedToX", getConnectedTo().getX());
			tag.putInt("ConnectedToY", getConnectedTo().getY());
			tag.putInt("ConnectedToZ", getConnectedTo().getZ());
		}
		if(getFakeConnected() != null) {
			tag.putInt("FakeConnectedX", getFakeConnected().getX());
			tag.putInt("FakeConnectedY", getFakeConnected().getY());
			tag.putInt("FakeConnectedZ", getFakeConnected().getZ());
		}
		tag.putBoolean("Connected", isconnected);
		tag.putBoolean("FakeConnected", isfakeconnected);
        return tag;
	}
	
	@Override
	public void load(BlockState state, CompoundNBT tag) {
		super.load(state, tag);
		getTank().readFromNBT(tag);
		connectedto = new BlockPos(tag.getInt("ConnectedToX"), tag.getInt("ConnectedToY"), tag.getInt("ConnectedToZ"));
		isconnected = tag.getBoolean("Connected");
		fakeconnected = new BlockPos(tag.getInt("FakeConnectedX"), tag.getInt("FakeConnectedY"), tag.getInt("FakeConnectedZ"));
		isfakeconnected = tag.getBoolean("FakeConnected");
	}
}
