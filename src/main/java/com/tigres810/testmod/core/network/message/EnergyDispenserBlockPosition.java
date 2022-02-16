package com.tigres810.testmod.core.network.message;

import java.util.function.Supplier;

import com.tigres810.testmod.common.tileentitys.TileEnergyDispenserBlock;
import com.tigres810.testmod.common.tileentitys.TileFluidDispenserBlock;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

public class EnergyDispenserBlockPosition {

	public BlockPos tepos;
	public BlockPos pos;
	
	public EnergyDispenserBlockPosition(BlockPos pos, BlockPos tepos) {
		this.tepos = tepos;
		this.pos = pos;
	}
	
	public static void encode(EnergyDispenserBlockPosition message, PacketBuffer buffer) {
		buffer.writeBlockPos(message.tepos);
		buffer.writeBlockPos(message.pos);
	}
	
	public static EnergyDispenserBlockPosition decode(PacketBuffer buffer) {
		return new EnergyDispenserBlockPosition(buffer.readBlockPos(), buffer.readBlockPos());
	}
	
	public static void handle(EnergyDispenserBlockPosition message, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			ServerPlayerEntity player = context.getSender();
			World world = player.getLevel();
			TileEntity te = world.getBlockEntity(message.tepos);
			TileEntity anotherte = world.getBlockEntity(message.pos);
			
			if(te instanceof TileEnergyDispenserBlock) {
				((TileEnergyDispenserBlock) te).setConnectedTo(message.pos, false, true);
				((TileEnergyDispenserBlock) anotherte).setConnectedTo(message.tepos, true, true);
			} else if(te instanceof TileFluidDispenserBlock) {
				((TileFluidDispenserBlock) te).setConnectedTo(message.pos, false, true);
				((TileFluidDispenserBlock) anotherte).setConnectedTo(message.tepos, true, true);
			}
		});
		context.setPacketHandled(true);
	}
}
