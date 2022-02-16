package com.tigres810.testmod.common.tileentitys;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.tigres810.testmod.core.energy.CustomEnergyStorage;
import com.tigres810.testmod.core.init.TileEntityInit;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TilePlantEnergizerBlock extends TileEntity implements ITickableTileEntity {
	protected CustomEnergyStorage storage = new CustomEnergyStorage(5, 1, 1) { 
		protected void onEnergyChanged() { 
			BlockState state = level.getBlockState(worldPosition); 
			level.sendBlockUpdated(worldPosition, state, state, Constants.BlockFlags.BLOCK_UPDATE);  
			setChanged(); 
		}; 
	};
	
	protected LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> storage);
	
	private int ticks = 0;
	
	public TilePlantEnergizerBlock() {
		super(TileEntityInit.PLANTENERGIZER_BLOCK_TILE.get());
	}

	@Override
	public void tick() {
		if(!level.isClientSide()) {
			if(ticks >= 500) {
				
				if(storage.getEnergyStored() > 0) {
					AxisAlignedBB searchArea = new AxisAlignedBB(worldPosition.getX() - 5, worldPosition.getY() - 1, worldPosition.getZ() - 5, worldPosition.getX() + 5, worldPosition.getY() + 1, worldPosition.getZ() + 5);

	                Set<BlockPos> set = BlockPos.betweenClosedStream(searchArea)
	                        .map(pos -> new BlockPos(pos))
	                        .filter(pos -> (level.getBlockState(pos).getBlock() instanceof IGrowable && level.getBlockState(pos).getBlock() != Blocks.GRASS_BLOCK && level.getBlockState(pos).getBlock() != Blocks.GRASS))
	                        .collect(Collectors.toSet());
	                Iterator<BlockPos> it = set.iterator();
	                
	                ServerWorld serverWorld = (ServerWorld) level;
	                while (it.hasNext()) {
	                    BlockPos iterationPos = it.next();
	                    BlockState iterationState = level.getBlockState(iterationPos);
	                    
	                    if (((IGrowable) iterationState.getBlock()).isValidBonemealTarget(serverWorld, iterationPos, iterationState, true)) {
							((IGrowable) iterationState.getBlock()).performBonemeal(serverWorld, level.random, iterationPos, iterationState);
						}
	                }
	                storage.extractEnergy(1, false);
					ticks = 0;
				}
			} else {
				ticks++;
			}
		}
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
        if (capability == CapabilityEnergy.ENERGY)
            return energy.cast();
        return super.getCapability(capability, facing);
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