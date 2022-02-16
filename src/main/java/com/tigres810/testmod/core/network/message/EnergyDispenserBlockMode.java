package com.tigres810.testmod.core.network.message;

import java.util.function.Supplier;

import com.tigres810.testmod.common.tileentitys.TileCreativeEnergyDispenserBlock;
import com.tigres810.testmod.common.tileentitys.TileEnergyDispenserBlock;
import com.tigres810.testmod.common.tileentitys.TileFluidDispenserBlock;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

public class EnergyDispenserBlockMode {
	
	public BlockPos pos;
	
	public EnergyDispenserBlockMode(BlockPos pos) {
		this.pos = pos;
	}
	
	public static void encode(EnergyDispenserBlockMode message, PacketBuffer buffer) {
		buffer.writeBlockPos(message.pos);
	}
	
	public static EnergyDispenserBlockMode decode(PacketBuffer buffer) {
		return new EnergyDispenserBlockMode(buffer.readBlockPos());
	}
	
	public static void handle(EnergyDispenserBlockMode message, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			ServerPlayerEntity player = context.getSender();
			World world = player.getLevel();
			TileEntity te = world.getBlockEntity(message.pos);
			
			if(te instanceof TileCreativeEnergyDispenserBlock) {
				((TileCreativeEnergyDispenserBlock) te).setMode();
			} else if (te instanceof TileEnergyDispenserBlock) {
				((TileEnergyDispenserBlock) te).setMode();
			} else if (te instanceof TileFluidDispenserBlock) {
				((TileFluidDispenserBlock) te).setMode();
			}
		});
		context.setPacketHandled(true);
	}

}
