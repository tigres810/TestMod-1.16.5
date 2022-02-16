package com.tigres810.testmod.common.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.tigres810.testmod.common.tileentitys.TileFluidDispenserBlock;
import com.tigres810.testmod.common.tileentitys.TileFluidJarBlock;
import com.tigres810.testmod.common.tileentitys.TileFluidTankBlock;
import com.tigres810.testmod.core.init.TileEntityInit;
import com.tigres810.testmod.core.interfaces.IPipeConnect;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
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
import net.minecraft.world.World;

public class FluidDispenserBlock extends Block implements IPipeConnect {
	
	public static final BooleanProperty EXTRACT = BooleanProperty.create("extract");
	public static final BooleanProperty DEPOSIT = BooleanProperty.create("deposit");

	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	private static final VoxelShape SHAPE_N = Stream.of(
			Block.box(5.5, 0.5, 5.5, 10.5, 2.79, 10.5),Block.box(10.5, 0, 5, 11, 0.5, 11),
			Block.box(10.5, 2.79, 5, 11, 3.29, 11),Block.box(5, 0, 5, 5.5, 0.5, 11),
			Block.box(5, 2.79, 5, 5.5, 3.29, 11),Block.box(7, 2.79, 7, 7.5, 3.79, 7.5),
			Block.box(8.5, 2.79, 7, 9, 3.79, 7.5),Block.box(7, 2.79, 8.5, 7.5, 3.79, 9),
			Block.box(8.5, 2.79, 8.5, 9, 3.79, 9),Block.box(5.5, 0, 5, 10.5, 0.5, 5.5),
			Block.box(5.5, 2.79, 5, 10.5, 3.29, 5.5),Block.box(5.5, 0, 10.5, 10.5, 0.5, 11),
			Block.box(5.5, 2.79, 10.5, 10.5, 3.29, 11),Block.box(10, 0, 10, 10.5, 0.5, 10.5),
			Block.box(9.5, 0, 9.5, 10, 0.5, 10),Block.box(9, 0, 9, 9.5, 0.5, 9.5),
			Block.box(7, 0, 8.5, 9, 0.5, 9),Block.box(6.5, 0, 9, 7, 0.5, 9.5),
			Block.box(6, 0, 9.5, 6.5, 0.5, 10),Block.box(5.5, 0, 10, 6, 0.5, 10.5),
			Block.box(7, 0, 7, 9, 0.5, 7.5),Block.box(9, 0, 6.5, 9.5, 0.5, 7),
			Block.box(9.5, 0, 6, 10, 0.5, 6.5),Block.box(10, 0, 5.5, 10.5, 0.5, 6),
			Block.box(9.5, 2.8, 5.5, 10.5, 3.3, 6.5),Block.box(5.5, 2.8, 5.5, 6.5, 3.3, 6.5),
			Block.box(6, 3.3, 6, 7, 3.8, 7),Block.box(6.5, 3.8, 6.5, 7.5, 4.3, 7.5),
			Block.box(7, 4.3, 7, 9, 4.8, 9),Block.box(8.5, 3.8, 6.5, 9.5, 4.3, 7.5),
			Block.box(9, 3.3, 6, 10, 3.8, 7),Block.box(9.5, 2.8, 9.5, 10.5, 3.3, 10.5),
			Block.box(5.5, 2.8, 9.5, 6.5, 3.3, 10.5),Block.box(6, 3.3, 9, 7, 3.8, 10),
			Block.box(6.5, 3.8, 8.5, 7.5, 4.3, 9.5),Block.box(8.5, 3.8, 8.5, 9.5, 4.3, 9.5),
			Block.box(9, 3.3, 9, 10, 3.8, 10),Block.box(6.5, 0, 6.5, 7, 0.5, 7),
			Block.box(6, 0, 6, 6.5, 0.5, 6.5),Block.box(5.5, 0, 5.5, 6, 0.5, 6)
			).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get();
		private static final VoxelShape SHAPE_S = Stream.of(
			Block.box(5.5, 0.5, 5.5, 10.5, 2.79, 10.5),Block.box(10.5, 0, 5, 11, 0.5, 11),
			Block.box(10.5, 2.79, 5, 11, 3.29, 11),Block.box(5, 0, 5, 5.5, 0.5, 11),
			Block.box(5, 2.79, 5, 5.5, 3.29, 11),Block.box(7, 2.79, 7, 7.5, 3.79, 7.5),
			Block.box(8.5, 2.79, 7, 9, 3.79, 7.5),Block.box(7, 2.79, 8.5, 7.5, 3.79, 9),
			Block.box(8.5, 2.79, 8.5, 9, 3.79, 9),Block.box(5.5, 0, 5, 10.5, 0.5, 5.5),
			Block.box(5.5, 2.79, 5, 10.5, 3.29, 5.5),Block.box(5.5, 0, 10.5, 10.5, 0.5, 11),
			Block.box(5.5, 2.79, 10.5, 10.5, 3.29, 11),Block.box(10, 0, 10, 10.5, 0.5, 10.5),
			Block.box(9.5, 0, 9.5, 10, 0.5, 10),Block.box(9, 0, 9, 9.5, 0.5, 9.5),
			Block.box(7, 0, 8.5, 9, 0.5, 9),Block.box(6.5, 0, 9, 7, 0.5, 9.5),
			Block.box(6, 0, 9.5, 6.5, 0.5, 10),Block.box(5.5, 0, 10, 6, 0.5, 10.5),
			Block.box(7, 0, 7, 9, 0.5, 7.5),Block.box(9, 0, 6.5, 9.5, 0.5, 7),
			Block.box(9.5, 0, 6, 10, 0.5, 6.5),Block.box(10, 0, 5.5, 10.5, 0.5, 6),
			Block.box(9.5, 2.8, 5.5, 10.5, 3.3, 6.5),Block.box(5.5, 2.8, 5.5, 6.5, 3.3, 6.5),
			Block.box(6, 3.3, 6, 7, 3.8, 7),Block.box(6.5, 3.8, 6.5, 7.5, 4.3, 7.5),
			Block.box(7, 4.3, 7, 9, 4.8, 9),Block.box(8.5, 3.8, 6.5, 9.5, 4.3, 7.5),
			Block.box(9, 3.3, 6, 10, 3.8, 7),Block.box(9.5, 2.8, 9.5, 10.5, 3.3, 10.5),
			Block.box(5.5, 2.8, 9.5, 6.5, 3.3, 10.5),Block.box(6, 3.3, 9, 7, 3.8, 10),
			Block.box(6.5, 3.8, 8.5, 7.5, 4.3, 9.5),Block.box(8.5, 3.8, 8.5, 9.5, 4.3, 9.5),
			Block.box(9, 3.3, 9, 10, 3.8, 10),Block.box(6.5, 0, 6.5, 7, 0.5, 7),
			Block.box(6, 0, 6, 6.5, 0.5, 6.5),Block.box(5.5, 0, 5.5, 6, 0.5, 6)
			).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get();
		private static final VoxelShape SHAPE_E = Stream.of(
			Block.box(5.5, 0.5, 5.5, 10.5, 2.79, 10.5),Block.box(5, 0, 5, 11, 0.5, 5.5),
			Block.box(5, 2.79, 5, 11, 3.29, 5.5),Block.box(5, 0, 10.5, 11, 0.5, 11),
			Block.box(5, 2.79, 10.5, 11, 3.29, 11),Block.box(7, 2.79, 8.5, 7.5, 3.79, 9),
			Block.box(7, 2.79, 7, 7.5, 3.79, 7.5),Block.box(8.5, 2.79, 8.5, 9, 3.79, 9),
			Block.box(8.5, 2.79, 7, 9, 3.79, 7.5),Block.box(5, 0, 5.5, 5.5, 0.5, 10.5),
			Block.box(5, 2.79, 5.5, 5.5, 3.29, 10.5),Block.box(10.5, 0, 5.5, 11, 0.5, 10.5),
			Block.box(10.5, 2.79, 5.5, 11, 3.29, 10.5),Block.box(10, 0, 5.5, 10.5, 0.5, 6),
			Block.box(9.5, 0, 6, 10, 0.5, 6.5),Block.box(9, 0, 6.5, 9.5, 0.5, 7),
			Block.box(8.5, 0, 7, 9, 0.5, 9),Block.box(9, 0, 9, 9.5, 0.5, 9.5),
			Block.box(9.5, 0, 9.5, 10, 0.5, 10),Block.box(10, 0, 10, 10.5, 0.5, 10.5),
			Block.box(7, 0, 7, 7.5, 0.5, 9),Block.box(6.5, 0, 6.5, 7, 0.5, 7),
			Block.box(6, 0, 6, 6.5, 0.5, 6.5),Block.box(5.5, 0, 5.5, 6, 0.5, 6),
			Block.box(5.5, 2.8, 5.5, 6.5, 3.3, 6.5),Block.box(5.5, 2.8, 9.5, 6.5, 3.3, 10.5),
			Block.box(6, 3.3, 9, 7, 3.8, 10),Block.box(6.5, 3.8, 8.5, 7.5, 4.3, 9.5),
			Block.box(7, 4.3, 7, 9, 4.8, 9),Block.box(6.5, 3.8, 6.5, 7.5, 4.3, 7.5),
			Block.box(6, 3.3, 6, 7, 3.8, 7),Block.box(9.5, 2.8, 5.5, 10.5, 3.3, 6.5),
			Block.box(9.5, 2.8, 9.5, 10.5, 3.3, 10.5),Block.box(9, 3.3, 9, 10, 3.8, 10),
			Block.box(8.5, 3.8, 8.5, 9.5, 4.3, 9.5),Block.box(8.5, 3.8, 6.5, 9.5, 4.3, 7.5),
			Block.box(9, 3.3, 6, 10, 3.8, 7),Block.box(6.5, 0, 9, 7, 0.5, 9.5),
			Block.box(6, 0, 9.5, 6.5, 0.5, 10),Block.box(5.5, 0, 10, 6, 0.5, 10.5)
			).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get();
		private static final VoxelShape SHAPE_W = Stream.of(
			Block.box(5.5, 0.5, 5.5, 10.5, 2.79, 10.5),Block.box(5, 0, 5, 11, 0.5, 5.5),
			Block.box(5, 2.79, 5, 11, 3.29, 5.5),Block.box(5, 0, 10.5, 11, 0.5, 11),
			Block.box(5, 2.79, 10.5, 11, 3.29, 11),Block.box(7, 2.79, 8.5, 7.5, 3.79, 9),
			Block.box(7, 2.79, 7, 7.5, 3.79, 7.5),Block.box(8.5, 2.79, 8.5, 9, 3.79, 9),
			Block.box(8.5, 2.79, 7, 9, 3.79, 7.5),Block.box(5, 0, 5.5, 5.5, 0.5, 10.5),
			Block.box(5, 2.79, 5.5, 5.5, 3.29, 10.5),Block.box(10.5, 0, 5.5, 11, 0.5, 10.5),
			Block.box(10.5, 2.79, 5.5, 11, 3.29, 10.5),Block.box(10, 0, 5.5, 10.5, 0.5, 6),
			Block.box(9.5, 0, 6, 10, 0.5, 6.5),Block.box(9, 0, 6.5, 9.5, 0.5, 7),
			Block.box(8.5, 0, 7, 9, 0.5, 9),Block.box(9, 0, 9, 9.5, 0.5, 9.5),
			Block.box(9.5, 0, 9.5, 10, 0.5, 10),Block.box(10, 0, 10, 10.5, 0.5, 10.5),
			Block.box(7, 0, 7, 7.5, 0.5, 9),Block.box(6.5, 0, 6.5, 7, 0.5, 7),
			Block.box(6, 0, 6, 6.5, 0.5, 6.5),Block.box(5.5, 0, 5.5, 6, 0.5, 6),
			Block.box(5.5, 2.8, 5.5, 6.5, 3.3, 6.5),Block.box(5.5, 2.8, 9.5, 6.5, 3.3, 10.5),
			Block.box(6, 3.3, 9, 7, 3.8, 10),Block.box(6.5, 3.8, 8.5, 7.5, 4.3, 9.5),
			Block.box(7, 4.3, 7, 9, 4.8, 9),Block.box(6.5, 3.8, 6.5, 7.5, 4.3, 7.5),
			Block.box(6, 3.3, 6, 7, 3.8, 7),Block.box(9.5, 2.8, 5.5, 10.5, 3.3, 6.5),
			Block.box(9.5, 2.8, 9.5, 10.5, 3.3, 10.5),Block.box(9, 3.3, 9, 10, 3.8, 10),
			Block.box(8.5, 3.8, 8.5, 9.5, 4.3, 9.5),Block.box(8.5, 3.8, 6.5, 9.5, 4.3, 7.5),
			Block.box(9, 3.3, 6, 10, 3.8, 7),Block.box(6.5, 0, 9, 7, 0.5, 9.5),
			Block.box(6, 0, 9.5, 6.5, 0.5, 10),Block.box(5.5, 0, 10, 6, 0.5, 10.5)
			).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get();
	
	public FluidDispenserBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public void playerWillDestroy(World worldIn, BlockPos pos, BlockState state, PlayerEntity placer) {
		TileEntity te = worldIn.getBlockEntity(pos);
		if(te instanceof TileFluidDispenserBlock) {
			if(((TileFluidDispenserBlock) te).isconnected) {
				TileEntity anotherte = worldIn.getBlockEntity(((TileFluidDispenserBlock) te).getConnectedTo());
				
				if(anotherte instanceof TileFluidDispenserBlock) {
					((TileFluidDispenserBlock) anotherte).setConnectedTo(BlockPos.ZERO, true, false);
				}
			}
			if(((TileFluidDispenserBlock) te).isfakeconnected) {
				TileEntity anotherte = worldIn.getBlockEntity(((TileFluidDispenserBlock) te).getFakeConnected());
				
				if(anotherte instanceof TileFluidDispenserBlock) {
					((TileFluidDispenserBlock) anotherte).setConnectedTo(BlockPos.ZERO, false, false);
				}
			}
		}
		super.playerWillDestroy(worldIn, pos, state, placer);
	}
	
	@Override
	public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		if(!worldIn.isClientSide()) {
			TileEntity te = worldIn.getBlockEntity(pos.below());
			if(te != null) {
				if (te instanceof TileFluidJarBlock || te instanceof TileFluidTankBlock) {
					Direction bl = te.getBlockState().getValue(FACING);
	
	            	if(bl != state.getValue(FACING)) {
	            		if(bl.getOpposite() != state.getValue(FACING)) {
	            			worldIn.destroyBlock(pos, true);
	            		}
	            	}
	            }
			}
		}
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return TileEntityInit.FLUIDDISPENSER_BLOCK_TILE.get().create();
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
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(EXTRACT, true).setValue(DEPOSIT, false);
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
		builder.add(FACING).add(EXTRACT).add(DEPOSIT);
	}
	
	@Override
	public float getShadeBrightness(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return 0.6f;
	}

	@Override
	public List<Direction> getConnectableSides(BlockState state) {
		List<Direction> faces = new ArrayList<Direction>();
		faces.add(Direction.UP);
		return faces;
	}
}
