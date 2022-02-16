package com.tigres810.testmod.common.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.tigres810.testmod.core.init.TileEntityInit;
import com.tigres810.testmod.core.interfaces.IPipeConnect;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class PlantEnergizerBlock extends Block implements IPipeConnect {

	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	private static final VoxelShape SHAPE_N = Stream.of(
			Block.box(11, 0.5, 1, 11.5, 11.6, 5),
			Block.box(5, 0.5, 1, 11, 4.5, 5),
			Block.box(5, 11.5, 1, 11, 12, 5),
			Block.box(4.5, 0.5, 1, 5, 11.6, 5),
			Block.box(5, 0.5, 11, 11, 4.5, 15),
			Block.box(11, 0.5, 11, 11.5, 11.6, 15),
			Block.box(5, 11.5, 11, 11, 12, 15),
			Block.box(4.5, 0.5, 11, 5, 11.6, 15),
			Block.box(11, 0.5, 11, 15, 11.6, 11.5),
			Block.box(11, 0.5, 5, 15, 4.5, 11),
			Block.box(11, 11.5, 5, 15, 12, 11),
			Block.box(11, 0.5, 4.5, 15, 11.6, 5),
			Block.box(1, 0.5, 11, 5, 11.6, 11.5),
			Block.box(1, 0.5, 5, 5, 4.5, 11),
			Block.box(1, 11.5, 5, 5, 12, 11),
			Block.box(1, 0.5, 4.5, 5, 11.6, 5),
			Block.box(0.5, 0, 0.5, 15.5, 0.5, 15.5),
			Block.box(0.5, 12.5, 15.5, 15.5, 13, 16),
			Block.box(0, 12.5, 0, 0.5, 13, 16),
			Block.box(15.5, 12.5, 0, 16, 13, 16),
			Block.box(0.5, 12.5, 0, 15.5, 13, 0.5),
			Block.box(0.5, 0, 15.5, 15.5, 0.5, 16),
			Block.box(15.5, 0, 0, 16, 0.5, 16),
			Block.box(0, 0, 0, 0.5, 0.5, 16),
			Block.box(0.5, 0, 0, 15.5, 0.5, 0.5),
			Block.box(5.5, 15.5, 5.5, 6, 16, 6),
			Block.box(6, 15.5, 6, 6.5, 16, 6.5),
			Block.box(6.5, 15.5, 6.5, 7, 16, 7),
			Block.box(10, 15.5, 5.5, 10.5, 16, 6),
			Block.box(9.5, 15.5, 6, 10, 16, 6.5),
			Block.box(9, 15.5, 6.5, 9.5, 16, 7),
			Block.box(7, 15.5, 7, 9, 16, 7.5),
			Block.box(5.5, 15.5, 10, 6, 16, 10.5),
			Block.box(6, 15.5, 9.5, 6.5, 16, 10),
			Block.box(6.5, 15.5, 9, 7, 16, 9.5),
			Block.box(7, 15.5, 8.5, 9, 16, 9),
			Block.box(9, 15.5, 9, 9.5, 16, 9.5),
			Block.box(9.5, 15.5, 9.5, 10, 16, 10),
			Block.box(10, 15.5, 10, 10.5, 16, 10.5),
			Block.box(5, 10.5, 5, 5.5, 12, 5.5),
			Block.box(10.5, 10.5, 5, 11, 12, 5.5),
			Block.box(5, 10.5, 10.5, 5.5, 12, 11),
			Block.box(10.5, 10.5, 10.5, 11, 12, 11),
			Block.box(5, 0.5, 10.5, 5.5, 5, 11),
			Block.box(10.5, 0.5, 10.5, 11, 5, 11),
			Block.box(10.5, 0.5, 5, 11, 5, 5.5),
			Block.box(5, 0.5, 5, 5.5, 5, 5.5),
			Block.box(0, 0.5, 0, 0.5, 12.5, 0.5),
			Block.box(15.5, 0.5, 0, 16, 12.5, 0.5),
			Block.box(15.5, 0.5, 15.5, 16, 12.5, 16),
			Block.box(15, 0.5, 1, 15.5, 12, 5),
			Block.box(15, 0.5, 11, 15.5, 12, 15),
			Block.box(15, 0.5, 5, 15.5, 5, 11),
			Block.box(15, 11, 5, 15.5, 12, 11),
			Block.box(0.5, 12, 0.5, 15.5, 12.5, 15.5),
			Block.box(0.5, 0.5, 11, 1, 12, 15),
			Block.box(0.5, 11, 5, 1, 12, 11),
			Block.box(0.5, 0.5, 1, 1, 12, 5),
			Block.box(0.5, 0.5, 5, 1, 5, 11),
			Block.box(5, 0.5, 0.5, 11, 5, 1),
			Block.box(0.5, 0.5, 0.5, 5, 12, 1),
			Block.box(5, 11, 0.5, 11, 12, 1),
			Block.box(11, 0.5, 0.5, 15.5, 12, 1),
			Block.box(5, 11, 15, 11, 12, 15.5),
			Block.box(11, 0.5, 15, 15.5, 12, 15.5),
			Block.box(5, 0.5, 15, 11, 5, 15.5),
			Block.box(0.5, 0.5, 15, 5, 12, 15.5),
			Block.box(0, 0.5, 15.5, 0.5, 12.5, 16),
			Block.box(5.5, 15.5, 5, 10.5, 16, 5.5),
			Block.box(5.5, 15.5, 10.5, 10.5, 16, 11),
			Block.box(5, 15.5, 5, 5.5, 16, 11),
			Block.box(10.5, 15.5, 5, 11, 16, 11),
			Block.box(5.5, 13, 5.5, 10.5, 15.5, 10.5)
			).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get();
	private static final VoxelShape SHAPE_S = Stream.of(
			Block.box(11, 0.5, 1, 11.5, 11.6, 5),
			Block.box(5, 0.5, 1, 11, 4.5, 5),
			Block.box(5, 11.5, 1, 11, 12, 5),
			Block.box(4.5, 0.5, 1, 5, 11.6, 5),
			Block.box(5, 0.5, 11, 11, 4.5, 15),
			Block.box(11, 0.5, 11, 11.5, 11.6, 15),
			Block.box(5, 11.5, 11, 11, 12, 15),
			Block.box(4.5, 0.5, 11, 5, 11.6, 15),
			Block.box(11, 0.5, 11, 15, 11.6, 11.5),
			Block.box(11, 0.5, 5, 15, 4.5, 11),
			Block.box(11, 11.5, 5, 15, 12, 11),
			Block.box(11, 0.5, 4.5, 15, 11.6, 5),
			Block.box(1, 0.5, 11, 5, 11.6, 11.5),
			Block.box(1, 0.5, 5, 5, 4.5, 11),
			Block.box(1, 11.5, 5, 5, 12, 11),
			Block.box(1, 0.5, 4.5, 5, 11.6, 5),
			Block.box(0.5, 0, 0.5, 15.5, 0.5, 15.5),
			Block.box(0.5, 12.5, 15.5, 15.5, 13, 16),
			Block.box(0, 12.5, 0, 0.5, 13, 16),
			Block.box(15.5, 12.5, 0, 16, 13, 16),
			Block.box(0.5, 12.5, 0, 15.5, 13, 0.5),
			Block.box(0.5, 0, 15.5, 15.5, 0.5, 16),
			Block.box(15.5, 0, 0, 16, 0.5, 16),
			Block.box(0, 0, 0, 0.5, 0.5, 16),
			Block.box(0.5, 0, 0, 15.5, 0.5, 0.5),
			Block.box(5.5, 15.5, 5.5, 6, 16, 6),
			Block.box(6, 15.5, 6, 6.5, 16, 6.5),
			Block.box(6.5, 15.5, 6.5, 7, 16, 7),
			Block.box(10, 15.5, 5.5, 10.5, 16, 6),
			Block.box(9.5, 15.5, 6, 10, 16, 6.5),
			Block.box(9, 15.5, 6.5, 9.5, 16, 7),
			Block.box(7, 15.5, 7, 9, 16, 7.5),
			Block.box(5.5, 15.5, 10, 6, 16, 10.5),
			Block.box(6, 15.5, 9.5, 6.5, 16, 10),
			Block.box(6.5, 15.5, 9, 7, 16, 9.5),
			Block.box(7, 15.5, 8.5, 9, 16, 9),
			Block.box(9, 15.5, 9, 9.5, 16, 9.5),
			Block.box(9.5, 15.5, 9.5, 10, 16, 10),
			Block.box(10, 15.5, 10, 10.5, 16, 10.5),
			Block.box(5, 10.5, 5, 5.5, 12, 5.5),
			Block.box(10.5, 10.5, 5, 11, 12, 5.5),
			Block.box(5, 10.5, 10.5, 5.5, 12, 11),
			Block.box(10.5, 10.5, 10.5, 11, 12, 11),
			Block.box(5, 0.5, 10.5, 5.5, 5, 11),
			Block.box(10.5, 0.5, 10.5, 11, 5, 11),
			Block.box(10.5, 0.5, 5, 11, 5, 5.5),
			Block.box(5, 0.5, 5, 5.5, 5, 5.5),
			Block.box(0, 0.5, 0, 0.5, 12.5, 0.5),
			Block.box(15.5, 0.5, 0, 16, 12.5, 0.5),
			Block.box(15.5, 0.5, 15.5, 16, 12.5, 16),
			Block.box(15, 0.5, 1, 15.5, 12, 5),
			Block.box(15, 0.5, 11, 15.5, 12, 15),
			Block.box(15, 0.5, 5, 15.5, 5, 11),
			Block.box(15, 11, 5, 15.5, 12, 11),
			Block.box(0.5, 12, 0.5, 15.5, 12.5, 15.5),
			Block.box(0.5, 0.5, 11, 1, 12, 15),
			Block.box(0.5, 11, 5, 1, 12, 11),
			Block.box(0.5, 0.5, 1, 1, 12, 5),
			Block.box(0.5, 0.5, 5, 1, 5, 11),
			Block.box(5, 0.5, 0.5, 11, 5, 1),
			Block.box(0.5, 0.5, 0.5, 5, 12, 1),
			Block.box(5, 11, 0.5, 11, 12, 1),
			Block.box(11, 0.5, 0.5, 15.5, 12, 1),
			Block.box(5, 11, 15, 11, 12, 15.5),
			Block.box(11, 0.5, 15, 15.5, 12, 15.5),
			Block.box(5, 0.5, 15, 11, 5, 15.5),
			Block.box(0.5, 0.5, 15, 5, 12, 15.5),
			Block.box(0, 0.5, 15.5, 0.5, 12.5, 16),
			Block.box(5.5, 15.5, 5, 10.5, 16, 5.5),
			Block.box(5.5, 15.5, 10.5, 10.5, 16, 11),
			Block.box(5, 15.5, 5, 5.5, 16, 11),
			Block.box(10.5, 15.5, 5, 11, 16, 11),
			Block.box(5.5, 13, 5.5, 10.5, 15.5, 10.5)
			).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get();
	private static final VoxelShape SHAPE_E = Stream.of(
			Block.box(11, 0.5, 11, 15, 11.6, 11.5),
			Block.box(11, 0.5, 5, 15, 4.5, 11),
			Block.box(11, 11.5, 5, 15, 12, 11),
			Block.box(11, 0.5, 4.5, 15, 11.6, 5),
			Block.box(1, 0.5, 5, 5, 4.5, 11),
			Block.box(1, 0.5, 11, 5, 11.6, 11.5),
			Block.box(1, 11.5, 5, 5, 12, 11),
			Block.box(1, 0.5, 4.5, 5, 11.6, 5),
			Block.box(4.5, 0.5, 11, 5, 11.6, 15),
			Block.box(5, 0.5, 11, 11, 4.5, 15),
			Block.box(5, 11.5, 11, 11, 12, 15),
			Block.box(11, 0.5, 11, 11.5, 11.6, 15),
			Block.box(4.5, 0.5, 1, 5, 11.6, 5),
			Block.box(5, 0.5, 1, 11, 4.5, 5),
			Block.box(5, 11.5, 1, 11, 12, 5),
			Block.box(11, 0.5, 1, 11.5, 11.6, 5),
			Block.box(0.5, 0, 0.5, 15.5, 0.5, 15.5),
			Block.box(0, 12.5, 0.5, 0.5, 13, 15.5),
			Block.box(0, 12.5, 0, 16, 13, 0.5),
			Block.box(0, 12.5, 15.5, 16, 13, 16),
			Block.box(15.5, 12.5, 0.5, 16, 13, 15.5),
			Block.box(0, 0, 0.5, 0.5, 0.5, 15.5),
			Block.box(0, 0, 15.5, 16, 0.5, 16),
			Block.box(0, 0, 0, 16, 0.5, 0.5),
			Block.box(15.5, 0, 0.5, 16, 0.5, 15.5),
			Block.box(10, 15.5, 5.5, 10.5, 16, 6),
			Block.box(9.5, 15.5, 6, 10, 16, 6.5),
			Block.box(9, 15.5, 6.5, 9.5, 16, 7),
			Block.box(10, 15.5, 10, 10.5, 16, 10.5),
			Block.box(9.5, 15.5, 9.5, 10, 16, 10),
			Block.box(9, 15.5, 9, 9.5, 16, 9.5),
			Block.box(8.5, 15.5, 7, 9, 16, 9),
			Block.box(5.5, 15.5, 5.5, 6, 16, 6),
			Block.box(6, 15.5, 6, 6.5, 16, 6.5),
			Block.box(6.5, 15.5, 6.5, 7, 16, 7),
			Block.box(7, 15.5, 7, 7.5, 16, 9),
			Block.box(6.5, 15.5, 9, 7, 16, 9.5),
			Block.box(6, 15.5, 9.5, 6.5, 16, 10),
			Block.box(5.5, 15.5, 10, 6, 16, 10.5),
			Block.box(10.5, 10.5, 5, 11, 12, 5.5),
			Block.box(10.5, 10.5, 10.5, 11, 12, 11),
			Block.box(5, 10.5, 5, 5.5, 12, 5.5),
			Block.box(5, 10.5, 10.5, 5.5, 12, 11),
			Block.box(5, 0.5, 5, 5.5, 5, 5.5),
			Block.box(5, 0.5, 10.5, 5.5, 5, 11),
			Block.box(10.5, 0.5, 10.5, 11, 5, 11),
			Block.box(10.5, 0.5, 5, 11, 5, 5.5),
			Block.box(15.5, 0.5, 0, 16, 12.5, 0.5),
			Block.box(15.5, 0.5, 15.5, 16, 12.5, 16),
			Block.box(0, 0.5, 15.5, 0.5, 12.5, 16),
			Block.box(11, 0.5, 15, 15, 12, 15.5),
			Block.box(1, 0.5, 15, 5, 12, 15.5),
			Block.box(5, 0.5, 15, 11, 5, 15.5),
			Block.box(5, 11, 15, 11, 12, 15.5),
			Block.box(0.5, 12, 0.5, 15.5, 12.5, 15.5),
			Block.box(1, 0.5, 0.5, 5, 12, 1),
			Block.box(5, 11, 0.5, 11, 12, 1),
			Block.box(11, 0.5, 0.5, 15, 12, 1),
			Block.box(5, 0.5, 0.5, 11, 5, 1),
			Block.box(15, 0.5, 5, 15.5, 5, 11),
			Block.box(15, 0.5, 0.5, 15.5, 12, 5),
			Block.box(15, 11, 5, 15.5, 12, 11),
			Block.box(15, 0.5, 11, 15.5, 12, 15.5),
			Block.box(0.5, 11, 5, 1, 12, 11),
			Block.box(0.5, 0.5, 11, 1, 12, 15.5),
			Block.box(0.5, 0.5, 5, 1, 5, 11),
			Block.box(0.5, 0.5, 0.5, 1, 12, 5),
			Block.box(0, 0.5, 0, 0.5, 12.5, 0.5),
			Block.box(10.5, 15.5, 5.5, 11, 16, 10.5),
			Block.box(5, 15.5, 5.5, 5.5, 16, 10.5),
			Block.box(5, 15.5, 5, 11, 16, 5.5),
			Block.box(5, 15.5, 10.5, 11, 16, 11),
			Block.box(5.5, 13, 5.5, 10.5, 15.5, 10.5)
			).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get();
	private static final VoxelShape SHAPE_W = Stream.of(
			Block.box(11, 0.5, 11, 15, 11.6, 11.5),
			Block.box(11, 0.5, 5, 15, 4.5, 11),
			Block.box(11, 11.5, 5, 15, 12, 11),
			Block.box(11, 0.5, 4.5, 15, 11.6, 5),
			Block.box(1, 0.5, 5, 5, 4.5, 11),
			Block.box(1, 0.5, 11, 5, 11.6, 11.5),
			Block.box(1, 11.5, 5, 5, 12, 11),
			Block.box(1, 0.5, 4.5, 5, 11.6, 5),
			Block.box(4.5, 0.5, 11, 5, 11.6, 15),
			Block.box(5, 0.5, 11, 11, 4.5, 15),
			Block.box(5, 11.5, 11, 11, 12, 15),
			Block.box(11, 0.5, 11, 11.5, 11.6, 15),
			Block.box(4.5, 0.5, 1, 5, 11.6, 5),
			Block.box(5, 0.5, 1, 11, 4.5, 5),
			Block.box(5, 11.5, 1, 11, 12, 5),
			Block.box(11, 0.5, 1, 11.5, 11.6, 5),
			Block.box(0.5, 0, 0.5, 15.5, 0.5, 15.5),
			Block.box(0, 12.5, 0.5, 0.5, 13, 15.5),
			Block.box(0, 12.5, 0, 16, 13, 0.5),
			Block.box(0, 12.5, 15.5, 16, 13, 16),
			Block.box(15.5, 12.5, 0.5, 16, 13, 15.5),
			Block.box(0, 0, 0.5, 0.5, 0.5, 15.5),
			Block.box(0, 0, 15.5, 16, 0.5, 16),
			Block.box(0, 0, 0, 16, 0.5, 0.5),
			Block.box(15.5, 0, 0.5, 16, 0.5, 15.5),
			Block.box(10, 15.5, 5.5, 10.5, 16, 6),
			Block.box(9.5, 15.5, 6, 10, 16, 6.5),
			Block.box(9, 15.5, 6.5, 9.5, 16, 7),
			Block.box(10, 15.5, 10, 10.5, 16, 10.5),
			Block.box(9.5, 15.5, 9.5, 10, 16, 10),
			Block.box(9, 15.5, 9, 9.5, 16, 9.5),
			Block.box(8.5, 15.5, 7, 9, 16, 9),
			Block.box(5.5, 15.5, 5.5, 6, 16, 6),
			Block.box(6, 15.5, 6, 6.5, 16, 6.5),
			Block.box(6.5, 15.5, 6.5, 7, 16, 7),
			Block.box(7, 15.5, 7, 7.5, 16, 9),
			Block.box(6.5, 15.5, 9, 7, 16, 9.5),
			Block.box(6, 15.5, 9.5, 6.5, 16, 10),
			Block.box(5.5, 15.5, 10, 6, 16, 10.5),
			Block.box(10.5, 10.5, 5, 11, 12, 5.5),
			Block.box(10.5, 10.5, 10.5, 11, 12, 11),
			Block.box(5, 10.5, 5, 5.5, 12, 5.5),
			Block.box(5, 10.5, 10.5, 5.5, 12, 11),
			Block.box(5, 0.5, 5, 5.5, 5, 5.5),
			Block.box(5, 0.5, 10.5, 5.5, 5, 11),
			Block.box(10.5, 0.5, 10.5, 11, 5, 11),
			Block.box(10.5, 0.5, 5, 11, 5, 5.5),
			Block.box(15.5, 0.5, 0, 16, 12.5, 0.5),
			Block.box(15.5, 0.5, 15.5, 16, 12.5, 16),
			Block.box(0, 0.5, 15.5, 0.5, 12.5, 16),
			Block.box(11, 0.5, 15, 15, 12, 15.5),
			Block.box(1, 0.5, 15, 5, 12, 15.5),
			Block.box(5, 0.5, 15, 11, 5, 15.5),
			Block.box(5, 11, 15, 11, 12, 15.5),
			Block.box(0.5, 12, 0.5, 15.5, 12.5, 15.5),
			Block.box(1, 0.5, 0.5, 5, 12, 1),
			Block.box(5, 11, 0.5, 11, 12, 1),
			Block.box(11, 0.5, 0.5, 15, 12, 1),
			Block.box(5, 0.5, 0.5, 11, 5, 1),
			Block.box(15, 0.5, 5, 15.5, 5, 11),
			Block.box(15, 0.5, 0.5, 15.5, 12, 5),
			Block.box(15, 11, 5, 15.5, 12, 11),
			Block.box(15, 0.5, 11, 15.5, 12, 15.5),
			Block.box(0.5, 11, 5, 1, 12, 11),
			Block.box(0.5, 0.5, 11, 1, 12, 15.5),
			Block.box(0.5, 0.5, 5, 1, 5, 11),
			Block.box(0.5, 0.5, 0.5, 1, 12, 5),
			Block.box(0, 0.5, 0, 0.5, 12.5, 0.5),
			Block.box(10.5, 15.5, 5.5, 11, 16, 10.5),
			Block.box(5, 15.5, 5.5, 5.5, 16, 10.5),
			Block.box(5, 15.5, 5, 11, 16, 5.5),
			Block.box(5, 15.5, 10.5, 11, 16, 11),
			Block.box(5.5, 13, 5.5, 10.5, 15.5, 10.5)
			).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get();
	
	public PlantEnergizerBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return TileEntityInit.PLANTENERGIZER_BLOCK_TILE.get().create();
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch (state.getValue(FACING)) {
		case NORTH:
			return SHAPE_N;
		case SOUTH:
			return SHAPE_S;
		case EAST:
			return SHAPE_E;
		case WEST:
			return SHAPE_W;
		default:
			return SHAPE_N;
		}
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}
	
	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
	
	@Override
	public float getShadeBrightness(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return 0.8f;
	}

	@Override
	public List<Direction> getConnectableSides(BlockState state) {
		List<Direction> faces = new ArrayList<Direction>();
		faces.add(Direction.DOWN);
		return faces;
	}
}