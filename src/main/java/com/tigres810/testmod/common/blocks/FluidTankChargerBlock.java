package com.tigres810.testmod.common.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.tigres810.testmod.core.init.TileEntityInit;
import com.tigres810.testmod.core.interfaces.IMachine;
import com.tigres810.testmod.core.interfaces.IPipeConnect;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FluidTankChargerBlock extends Block implements IPipeConnect, IMachine {
	
	public static final BooleanProperty NORTH = BooleanProperty.create("north");
	public static final BooleanProperty SOUTH = BooleanProperty.create("south");
	public static final BooleanProperty EAST = BooleanProperty.create("east");
	public static final BooleanProperty WEST = BooleanProperty.create("west");
	
	private static final VoxelShape SHAPE = Stream.of(
			Block.box(4, 0.8, 3, 5.3, 11.9, 5),
			Block.box(3, 0, 3, 13, 1, 4),
			Block.box(10.7, 0.8, 3, 12, 11.9, 5),
			Block.box(5.3, 8.4, 3, 10.7, 11.9, 4),
			Block.box(5.3, 1.4, 3, 10.7, 5.9, 4),
			Block.box(10.3, 6.9, 3, 10.7, 8.4, 4),
			Block.box(9.9, 7.4, 3, 10.3, 8.4, 4),
			Block.box(6.1, 7.9, 3, 9.9, 8.4, 4),
			Block.box(5.7, 7.4, 3, 6.1, 8.4, 4),
			Block.box(5.3, 6.9, 3, 5.7, 8.4, 4),
			Block.box(5.3, 5.9, 3, 10.7, 6.9, 4),
			Block.box(5.7, 6.9, 3, 10.3, 7.4, 4),
			Block.box(6.1, 7.4, 3, 9.9, 7.9, 4),
			Block.box(5.3, 1, 3, 10.7, 1.5, 4),
			Block.box(4, 11.8, 3.5, 12, 12.4, 12.5),
			Block.box(4, 0, 4, 12, 1, 12),
			Block.box(2.5, 0, 13, 13.5, 0.5, 13.5),
			Block.box(13, 0, 3, 13.5, 0.5, 13),
			Block.box(2.5, 0, 3, 3, 0.5, 13),
			Block.box(2.5, 0, 2.5, 13.5, 0.5, 3),
			Block.box(12, 11.9, 4, 12.5, 12.4, 12),
			Block.box(3, 13.21, 3, 13, 15.5, 13),
			Block.box(2.5, 12.4, 2.5, 13.5, 13.2, 13.5),
			Block.box(12, 15.5, 3.5, 12.5, 16, 12.5),
			Block.box(4, 15.5, 12, 12, 16, 12.5),
			Block.box(4, 15.5, 3.5, 12, 16, 4),
			Block.box(3.5, 15.5, 3.5, 4, 16, 12.5),
			Block.box(5.5, 15.5, 5.5, 6, 16, 6),
			Block.box(6, 15.5, 6, 6.5, 16, 6.5),
			Block.box(6.5, 15.5, 6.5, 7, 16, 7),
			Block.box(4, 15.5, 11.5, 4.5, 16, 12),
			Block.box(4.5, 15.5, 11, 5, 16, 11.5),
			Block.box(5, 15.5, 10.5, 5.5, 16, 11),
			Block.box(4, 15.5, 4, 4.5, 16, 4.5),
			Block.box(11.5, 15.5, 11.5, 12, 16, 12),
			Block.box(11, 15.5, 11, 11.5, 16, 11.5),
			Block.box(10.5, 15.5, 10.5, 11, 16, 11),
			Block.box(11.5, 15.5, 4, 12, 16, 4.5),
			Block.box(4.5, 15.5, 4.5, 5, 16, 5),
			Block.box(11, 15.5, 4.5, 11.5, 16, 5),
			Block.box(5, 15.5, 5, 5.5, 16, 5.5),
			Block.box(10.5, 15.5, 5, 11, 16, 5.5),
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
			Block.box(3.5, 11.9, 4, 4, 12.4, 12),
			Block.box(3.5, 1, 3.5, 4, 12.4, 4),
			Block.box(12, 1, 3.5, 12.5, 12.4, 4),
			Block.box(12, 1, 12, 12.5, 12.4, 12.5),
			Block.box(3.5, 1, 12, 4, 12.4, 12.5),
			Block.box(3, 0, 4, 4, 5, 12),
			Block.box(3, 11, 4, 4, 12, 12),
			Block.box(3, 5, 4, 4, 11, 5),
			Block.box(3, 5, 11, 4, 11, 12),
			Block.box(4, 0, 12, 12, 5, 13),
			Block.box(11, 5, 12, 12, 11, 13),
			Block.box(4, 5, 12, 5, 11, 13),
			Block.box(4, 11, 12, 12, 12, 13),
			Block.box(12, 0, 4, 13, 5, 12),
			Block.box(12, 5, 4, 13, 11, 5),
			Block.box(12, 11, 4, 13, 12, 12),
			Block.box(12, 5, 11, 13, 11, 12),
			Block.box(5.3, 1, 12, 10.7, 1.5, 13),
			Block.box(3, 0, 12, 13, 1, 13)
			).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();
	
	public FluidTankChargerBlock(Properties properties) {
		super(properties);
		
		this.registerDefaultState(this.defaultBlockState().setValue(NORTH, false).setValue(SOUTH, false).setValue(EAST, false).setValue(WEST, false));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		World world = (World) worldIn;
		BlockPos pos = currentPos;
		return super.updateShape(stateIn.setValue(EAST, this.isSideConnectable(world, pos, Direction.EAST)).setValue(SOUTH, this.isSideConnectable(world, pos, Direction.SOUTH)).setValue(WEST, this.isSideConnectable(world, pos, Direction.WEST)), facing, facingState, worldIn, currentPos, facingPos);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return super.getStateForPlacement(context).setValue(EAST, this.isSideConnectable(context.getLevel(), context.getClickedPos(), Direction.EAST)).setValue(SOUTH, this.isSideConnectable(context.getLevel(), context.getClickedPos(), Direction.SOUTH)).setValue(WEST, this.isSideConnectable(context.getLevel(), context.getClickedPos(), Direction.WEST));
	}
	
	private boolean isSideConnectable (World world, BlockPos pos, Direction side) {
		final BlockState state = world.getBlockState(pos.offset(side.getNormal()));
		if(state == null) return false;
		TileEntity te = world.getBlockEntity(pos.offset(side.getNormal()));
		if(te == null) return false;
		LazyOptional<IFluidHandler> energyHandlerCap = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY);
		if(state.getBlock() instanceof IPipeConnect) {
			List<Direction> faces = ((IPipeConnect)state.getBlock()).getConnectableSides(state); 
            if(!faces.contains(side)) return false;
            return true;
		} else {
			if(energyHandlerCap.isPresent()) return true;
			return false;
		}			
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return TileEntityInit.FLUIDTANKCHARGER_BLOCK_TILE.get().create();
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}
	
	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.MODEL;
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder.add(new BooleanProperty[] {NORTH, SOUTH, EAST, WEST}));
	}

	@Override
	public List<Direction> getConnectableSides(BlockState state) {
		List<Direction> faces = new ArrayList<Direction>();
		faces.add(Direction.NORTH);
		faces.add(Direction.EAST);
		faces.add(Direction.WEST);
		return faces;
	}

	@Override
	public boolean isMachine(boolean ismachine) {
		return true;
	}
}