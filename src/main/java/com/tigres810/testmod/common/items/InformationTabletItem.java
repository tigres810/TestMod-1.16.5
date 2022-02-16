package com.tigres810.testmod.common.items;

import com.tigres810.testmod.client.screens.TestScreen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class InformationTabletItem extends Item {

	public InformationTabletItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if(!worldIn.isClientSide()) {
			TestScreen.open();
		}
		return super.use(worldIn, playerIn, handIn);
	}
}
