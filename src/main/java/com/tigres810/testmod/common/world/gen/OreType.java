package com.tigres810.testmod.common.world.gen;

import com.tigres810.testmod.common.blocks.BlockFluxOre;
import com.tigres810.testmod.core.init.BlockInit;

import net.minecraft.block.Block;
import net.minecraftforge.common.util.Lazy;

public enum OreType {
	
	FLUX_ORE(Lazy.of(BlockInit.FLUX_ORE_BLOCK), 15, 25, 70, 40);
	
	private final Lazy<BlockFluxOre> block;
	private final int maxVeinSize;
	private final int minHeight;
	private final int maxHeight;
	private final int oreCount;
	
	private OreType(Lazy<BlockFluxOre> lazy, int maxVeinSize, int minHeight, int maxHeight, int oreCount) {
		this.block = lazy;
		this.maxVeinSize = maxVeinSize;
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		this.oreCount = oreCount;
	}
	
	public Lazy<BlockFluxOre> getBlock() {
		return block;
	}
	
	public int getMaxVeinSize() {
		return maxVeinSize;
	}
	
	public int getMinHeight() {
		return minHeight;
	}
	
	public int getMaxHeight() {
		return maxHeight;
	}
	
	public int getOreCount() {
		return oreCount;
	}
	
	public static OreType get(Block block) {
		for(OreType ore : values()) {
			if(block == ore.block) {
				return ore;
			}
		}
		return null;
	}

}
