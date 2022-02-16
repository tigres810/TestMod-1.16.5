package com.tigres810.testmod.client.events;

import com.tigres810.testmod.TestMod;
import com.tigres810.testmod.core.init.ItemInit;
import com.tigres810.testmod.core.init.KeybindsInit;
import com.tigres810.testmod.core.network.MainNetwork;
import com.tigres810.testmod.core.network.message.EnergyDispenserBlockMode;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = TestMod.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class InputEvents {
	
	@SubscribeEvent
	public static void onKeyPress(InputEvent.KeyInputEvent event) {
		Minecraft mc = Minecraft.getInstance();
		if(mc.level == null) return;
		onInput(mc, event.getKey(), event.getAction());
	}
	
	@SubscribeEvent
	public static void onMouseClick(InputEvent.MouseInputEvent event) {
		Minecraft mc = Minecraft.getInstance();
		if(mc.level == null) return;
		onInput(mc, event.getButton(), event.getAction());
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
	
	private static void onInput(Minecraft mc, int key, int action) {
		PlayerEntity player = mc.player;
		if(mc.screen == null && KeybindsInit.changeModeKey.isDown() && player.getMainHandItem().getItem() == ItemInit.MAGIC_STICK.get()) {
			BlockPos pos = lookingAt();
			if(!pos.equals(null)) {
				World world = mc.level;
				TileEntity te = world.getBlockEntity(pos);
				if(te != null) {
					MainNetwork.CHANNEL.sendToServer(new EnergyDispenserBlockMode(pos));
					player.playSound(SoundEvents.CONDUIT_ACTIVATE, 1f, 1f);
				}
			}
		}
	}

}
