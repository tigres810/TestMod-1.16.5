package com.tigres810.testmod.common.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class BlockFluxOre extends OreBlock {

	public BlockFluxOre(Properties properties) {
		super(properties);
	}
	
	@Override
	public int getExpDrop(BlockState state, IWorldReader reader, BlockPos pos, int fortune, int silktouch) {
		return (int) ((Math.random() * 6) + 3);
	}
}
