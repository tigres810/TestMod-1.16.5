package com.tigres810.testmod.common.items;

import com.tigres810.testmod.common.tileentitys.TileEnergyDispenserBlock;
import com.tigres810.testmod.common.tileentitys.TileFluidDispenserBlock;
import com.tigres810.testmod.core.network.MainNetwork;
import com.tigres810.testmod.core.network.message.EnergyDispenserBlockPosition;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class MagicStickItem extends Item {

	public BlockPos pos1;
	public BlockPos pos2;
	
	public MagicStickItem(Properties properties) {
		super(properties);
	}
	
	@SuppressWarnings("resource")
	public BlockPos lookingAt(){
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
	
	// Client side function
	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if(worldIn.isClientSide()) {
			if(playerIn.isShiftKeyDown()) {
				if(pos1 != null) {
					pos1 = null;
					pos2 = null;
					playerIn.playSound(SoundEvents.BEACON_DEACTIVATE, 1f, 1f);
				}
			} else {
				BlockPos pos = lookingAt();
				if(!pos.equals(null)) {
					if(pos1 == null) {
						pos1 = pos;
						
						worldIn.addParticle(ParticleTypes.WITCH, pos1.getX() + 0.5d, pos1.getY(), pos1.getZ() + 0.5d, 0, 1, 0);
						playerIn.playSound(SoundEvents.BEACON_ACTIVATE, 1f, 1f);
					} else if(pos2 == null) {
						pos2 = pos;
						
						worldIn.addParticle(ParticleTypes.WITCH, pos2.getX() + 0.5d, pos2.getY(), pos2.getZ() + 0.5d, 0, 1, 0);
						playerIn.playSound(SoundEvents.BEACON_POWER_SELECT, 1f, 1f);
						
						TileEntity te1 = worldIn.getBlockEntity(pos1);
						TileEntity te2 = worldIn.getBlockEntity(pos2);
						
						if(te1 != null && te2 != null) {
							if(te1 instanceof TileEnergyDispenserBlock && te2 instanceof TileEnergyDispenserBlock) {
								MainNetwork.CHANNEL.sendToServer(new EnergyDispenserBlockPosition(pos1, pos2));
							} else if(te1 instanceof TileFluidDispenserBlock && te2 instanceof TileFluidDispenserBlock) {
								MainNetwork.CHANNEL.sendToServer(new EnergyDispenserBlockPosition(pos1, pos2));
							}
						}
						
						pos1 = null;
						pos2 = null;
					}
				}
			}
		}
		return super.use(worldIn, playerIn, handIn);
	}

}
