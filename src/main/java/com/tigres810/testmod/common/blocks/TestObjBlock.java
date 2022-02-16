package com.tigres810.testmod.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class TestObjBlock extends Block {
	
	private static final VoxelShape SHAPE = makeShape();
			
	public static VoxelShape makeShape(){
		VoxelShape shape = VoxelShapes.empty();
		shape = VoxelShapes.join(shape, VoxelShapes.box(0.25, 0, 0.3125, 0.75, 1, 0.6875), IBooleanFunction.OR);

		return shape;
	}

	public TestObjBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}
	
}
