package com.tigres810.testmod.common.tileentitys;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.tigres810.testmod.common.blocks.CreativeEnergyDispenserBlock;
import com.tigres810.testmod.common.blocks.EnergyDispenserBlock;
import com.tigres810.testmod.core.energy.CustomEnergyStorage;
import com.tigres810.testmod.core.init.TileEntityInit;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;

public class TileCreativeEnergyDispenserBlock extends TileEntity implements ITickableTileEntity {
	
	protected CustomEnergyStorage storage = new CustomEnergyStorage(5, 1, 1) { 
		protected void onEnergyChanged() { 
			BlockState state = level.getBlockState(worldPosition); 
			level.sendBlockUpdated(worldPosition, state, state, Constants.BlockFlags.BLOCK_UPDATE); 
			setChanged(); 
		}; 
	};
	
	protected LazyOptional<CustomEnergyStorage> energyHandler = LazyOptional.of(() -> storage);
	
	private int ticks = 0;

	public TileCreativeEnergyDispenserBlock() {
		super(TileEntityInit.CREATIVEENERGYDISPENSER_BLOCK_TILE.get());
	}

	@Override
	public void tick() {
		if(!level.isClientSide()) {
			if(ticks >= 100) {
				storage.receiveEnergy(1, false);
				if(!getBlockState().getValue(EnergyDispenserBlock.EXTRACT)) {
					sendEnergy();
				}
				ticks = 0;
			} else {
				ticks++;
			}
		}
	}
	
	private void sendEnergy() {
		AtomicInteger capacity = new AtomicInteger(storage.getEnergyStored());
		if (capacity.get() <= 0) return;
		
		TileEntity te = level.getBlockEntity(worldPosition.relative(Direction.DOWN));
		if(te != null) {
			boolean canContinue = te.getCapability(CapabilityEnergy.ENERGY, Direction.UP).map(handler -> {
				if (handler.canReceive()) {
					int received = handler.receiveEnergy(capacity.get(), false);
					capacity.addAndGet(-received);
					storage.extractEnergy(received, false);
					return capacity.get() > 0;
				}
				return true;
			}).orElse(true);
			if(!canContinue) return;
		}
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
        if (capability == CapabilityEnergy.ENERGY)
            return energyHandler.cast();
        return super.getCapability(capability, facing);
    }
	
	public void setMode() {
		if(getBlockState().getValue(CreativeEnergyDispenserBlock.EXTRACT)) {
		    level.setBlock(getBlockPos(), getBlockState().setValue(CreativeEnergyDispenserBlock.EXTRACT, false).setValue(CreativeEnergyDispenserBlock.DEPOSIT, true), Constants.BlockFlags.BLOCK_UPDATE);
		} else {
			level.setBlock(getBlockPos(), getBlockState().setValue(CreativeEnergyDispenserBlock.EXTRACT, true).setValue(CreativeEnergyDispenserBlock.DEPOSIT, false), Constants.BlockFlags.BLOCK_UPDATE);
		}
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
	public CompoundNBT save(CompoundNBT nbt) {
		nbt = super.save(nbt);
		nbt = storage.writeToNBT(nbt);
		return nbt;
	}
	
	@Override
	public void load(BlockState state, CompoundNBT nbt) {
		super.load(state, nbt);
		storage.readFromNBT(nbt);
	}

}