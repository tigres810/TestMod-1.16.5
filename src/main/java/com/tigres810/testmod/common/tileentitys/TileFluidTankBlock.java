package com.tigres810.testmod.common.tileentitys;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.tigres810.testmod.core.init.FluidInit;
import com.tigres810.testmod.core.init.TileEntityInit;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class TileFluidTankBlock extends TileEntity {
	protected FluidTank tank = new FluidTank(FluidAttributes.BUCKET_VOLUME * 5) {
		public boolean isFluidValid(net.minecraftforge.fluids.FluidStack stack) {
			return stack.getFluid() == FluidInit.FLUX_FLUID.get();
		};
		
		protected void onContentsChanged() {
			BlockState state = level.getBlockState(worldPosition);
			level.sendBlockUpdated(worldPosition, state, state, 2);
			setChanged();
		};
	};
	
	private final LazyOptional<IFluidHandler> holder = LazyOptional.of(() -> tank);
	
	public TileFluidTankBlock() {
		super(TileEntityInit.FLUIDTANK_BLOCK_TILE.get());
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return holder.cast();
        return super.getCapability(capability, facing);
    }
	
	public FluidTank getTank() {
        return this.tank;
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
        return tag;
	}
	
	@Override
	public void load(BlockState state, CompoundNBT tag) {
		super.load(state, tag);
        getTank().readFromNBT(tag);
	}
}
