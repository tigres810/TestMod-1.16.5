package com.tigres810.testmod.common.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.tigres810.testmod.client.screens.EnergyMachineChargerEnergyScreen;
import com.tigres810.testmod.common.tileentitys.TileEnergyMachineChargerBlock;
import com.tigres810.testmod.core.init.TileEntityInit;
import com.tigres810.testmod.core.interfaces.IPipeConnect;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class EnergyMachineChargerBlock extends Block implements IPipeConnect {
	
	public static final BooleanProperty UP = BooleanProperty.create("up");
	public static final BooleanProperty DOWN = BooleanProperty.create("down");
	public static final BooleanProperty NORTH = BooleanProperty.create("north");
	public static final BooleanProperty SOUTH = BooleanProperty.create("south");
	public static final BooleanProperty EAST = BooleanProperty.create("east");
	public static final BooleanProperty WEST = BooleanProperty.create("west");
	
	private static final VoxelShape SHAPE = Stream.of(
		Block.box(4, 4.2, 3.5, 12, 11.9, 4),Block.box(3.5, 4.2, 4, 4, 11.9, 12),
		Block.box(4, 4.2, 12, 12, 11.9, 12.5),Block.box(12, 4.2, 4, 12.5, 11.9, 12),
		Block.box(4.1, 12, 4, 11.8, 12.5, 12),Block.box(4, 3.5, 4.199999999999999, 12, 4, 11.9),
		Block.box(11.5, 3.7, 3, 12, 12.4, 3.5),Block.box(3, 3.7, 4, 3.5, 12.4, 4.5),
		Block.box(4, 3.7, 12.5, 4.5, 12.4, 13),Block.box(12.5, 3.7, 11.5, 13, 12.4, 12),
		Block.box(3.5999999999999996, 12.5, 11.5, 12.3, 13, 12),Block.box(4, 3, 3.6999999999999993, 4.5, 3.5, 12.4),
		Block.box(4, 3.7, 3, 4.5, 12.4, 3.5),Block.box(3, 3.7, 11.5, 3.5, 12.4, 12),
		Block.box(11.5, 3.7, 12.5, 12, 12.4, 13),Block.box(12.5, 3.7, 4, 13, 12.4, 4.5),
		Block.box(3.5999999999999996, 12.5, 4, 12.3, 13, 4.5),Block.box(11.5, 3, 3.6999999999999993, 12, 3.5, 12.4),
		Block.box(5, 5, 3, 11, 11, 3.5),Block.box(3, 5, 5, 3.5, 11, 11),
		Block.box(5, 5, 12.5, 11, 11, 13),Block.box(12.5, 5, 5, 13, 11, 11),
		Block.box(5, 12.5, 5, 11, 13, 11),Block.box(5, 3, 5, 11, 3.5, 11),
		Block.box(4, 11.9, 3.5, 12, 12.4, 4),Block.box(3.5, 11.9, 4, 4, 12.4, 12),
		Block.box(4, 11.9, 12, 12, 12.4, 12.5),Block.box(12, 11.9, 4, 12.5, 12.4, 12),
		Block.box(3.5999999999999996, 12, 4, 4.1, 12.5, 12),Block.box(4, 3.5, 11.9, 12, 4, 12.4),
		Block.box(4, 3.7, 3.5, 12, 4.2, 4),Block.box(3.5, 3.7, 4, 4, 4.2, 12),
		Block.box(4, 3.7, 12, 12, 4.2, 12.5),Block.box(12, 3.7, 4, 12.5, 4.2, 12),
		Block.box(11.8, 12, 4, 12.3, 12.5, 12),Block.box(4, 3.5, 3.6999999999999993, 12, 4, 4.199999999999999)
		).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get();
	
	public EnergyMachineChargerBlock(Properties properties) {
		super(properties);
		
		this.registerDefaultState(this.defaultBlockState().setValue(UP, false).setValue(DOWN, false).setValue(NORTH, false).setValue(SOUTH, false).setValue(EAST, false).setValue(WEST, false));
	}
	
	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		TileEntity te = worldIn.getBlockEntity(pos);
		if(te != null && te instanceof TileEnergyMachineChargerBlock) {
			te.getCapability(CapabilityEnergy.ENERGY).ifPresent(h -> {
				EnergyMachineChargerEnergyScreen.open(h.getEnergyStored());
			});
		}
		return ActionResultType.SUCCESS;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		World world = (World) worldIn;
		BlockPos pos = currentPos;
		return super.updateShape(stateIn.setValue(DOWN, this.isSideConnectable(world, pos, Direction.DOWN)).setValue(EAST, this.isSideConnectable(world, pos, Direction.EAST)).setValue(NORTH, this.isSideConnectable(world, pos, Direction.NORTH)).setValue(SOUTH, this.isSideConnectable(world, pos, Direction.SOUTH)).setValue(UP, this.isSideConnectable(world, pos, Direction.UP)).setValue(WEST, this.isSideConnectable(world, pos, Direction.WEST)), facing, facingState, worldIn, currentPos, facingPos);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return super.getStateForPlacement(context).setValue(DOWN, this.isSideConnectable(context.getLevel(), context.getClickedPos(), Direction.DOWN)).setValue(EAST, this.isSideConnectable(context.getLevel(), context.getClickedPos(), Direction.EAST)).setValue(NORTH, this.isSideConnectable(context.getLevel(), context.getClickedPos(), Direction.NORTH)).setValue(SOUTH, this.isSideConnectable(context.getLevel(), context.getClickedPos(), Direction.SOUTH)).setValue(UP, this.isSideConnectable(context.getLevel(), context.getClickedPos(), Direction.UP)).setValue(WEST, this.isSideConnectable(context.getLevel(), context.getClickedPos(), Direction.WEST));
	}
	
	private boolean isSideConnectable (World world, BlockPos pos, Direction side) {
		final BlockState state = world.getBlockState(pos.offset(side.getNormal()));
		if(state == null) return false;
		TileEntity te = world.getBlockEntity(pos.offset(side.getNormal()));
		if(te == null) return false;
		LazyOptional<IEnergyStorage> energyHandlerCap = te.getCapability(CapabilityEnergy.ENERGY);
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
		return TileEntityInit.ENERGYMACHINECHARGER_BLOCK_TILE.get().create();
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
		super.createBlockStateDefinition(builder.add(new BooleanProperty[] {UP, DOWN, NORTH, SOUTH, EAST, WEST}));
	}

	@Override
	public List<Direction> getConnectableSides(BlockState state) {
		List<Direction> faces = new ArrayList<Direction>();
		faces.add(Direction.UP);
		faces.add(Direction.DOWN);
		faces.add(Direction.NORTH);
		faces.add(Direction.SOUTH);
		faces.add(Direction.EAST);
		faces.add(Direction.WEST);
		return faces;
	}
}
