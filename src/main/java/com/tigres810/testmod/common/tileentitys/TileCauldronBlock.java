package com.tigres810.testmod.common.tileentitys;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.tigres810.testmod.core.init.TileEntityInit;
import com.tigres810.testmod.core.interfaces.ICauldronRecipes;

import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class TileCauldronBlock extends TileEntity implements ITickableTileEntity, ICauldronRecipes {
	protected FluidTank tank = new FluidTank(FluidAttributes.BUCKET_VOLUME * 5) {
		public boolean isFluidValid(net.minecraftforge.fluids.FluidStack stack) {
			return stack.getFluid() == Fluids.WATER;
		};
		
		protected void onContentsChanged() {
			BlockState state = level.getBlockState(worldPosition);
			level.sendBlockUpdated(worldPosition, state, state, 2);
			setChanged();
		};
	};
	
	private final LazyOptional<IFluidHandler> holder = LazyOptional.of(() -> tank);
	private List<ItemStack> recipe = new ArrayList<ItemStack>();
    private ItemStack[][] selectedrecipe;
    private int selectedrecipesize;
    private int ticks;

	public TileCauldronBlock() {
		super(TileEntityInit.CAULDRON_BLOCK_TILE.get());
	}

	@Override
	public void tick() {
		if(!this.level.isClientSide()) {
			ticks++;
            if(ticks >= 300) {
            	if(this.level.isRaining()) {
            		if(this.level.canSeeSky(worldPosition)) {
	                    if(getTank().fill(new FluidStack(Fluids.WATER, 1000), IFluidHandler.FluidAction.EXECUTE) == 1000) {
	                    }
            		}
            	}
                ticks = 0;
            }
            if(getTank().getFluidAmount() > 0) {
				List<ItemEntity> stuff = this.level.getEntitiesOfClass(ItemEntity.class, new AxisAlignedBB(this.worldPosition, this.worldPosition.offset(1, 1, 1)));
				
				for (ItemEntity item : stuff) {
					recipe.add(item.getItem().copy());
					item.getItem().setCount(0);
					this.level.playSound(null, this.worldPosition, SoundEvents.AMBIENT_UNDERWATER_ENTER, SoundCategory.PLAYERS, 1f, 1f);
				}
				
				//Recipes reworked
				if(recipe.size() > 0) {
					if(selectedrecipesize != recipe.size()) {
						selectedrecipe = null;
						selectedrecipesize = recipe.size();
					}
					if(selectedrecipe == null) {
						selectedrecipe = checkRecipe(recipe);
						if(matchingRecipe(selectedrecipe, recipe)) {
							PlayerEntity player = this.level.getNearestPlayer(this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), 2f, false);
							
							if(player != null) {
								for(int o = 0; o < selectedrecipe[1].length; o++) {
									player.addItem(selectedrecipe[1][o].copy());
									if(o >= selectedrecipe[1].length - 1) {
										recipe.clear();
										selectedrecipesize = 0;
										this.level.playSound(null, this.worldPosition, SoundEvents.EVOKER_CAST_SPELL, SoundCategory.PLAYERS, 1f, 1f);
									}
								}
							}
						}
					}
				}
            }
		}
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
