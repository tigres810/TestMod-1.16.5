package com.tigres810.testmod.common.blocks;

import java.util.stream.Stream;

import com.tigres810.testmod.core.init.TileEntityInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class CauldronBlock extends Block {

	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	private static final VoxelShape SHAPE_N = Stream.of(
			Block.box(2.9901, 3, 4, 3.9901, 13, 12),Block.box(3, 2, 3, 13, 3, 13),
			Block.box(4, 3, 12, 12, 13, 13),Block.box(3.5, 3, 12, 4, 13.5, 12.5),
			Block.box(3.5, 3, 3.5, 4, 13.5, 4),Block.box(12, 3, 12, 12.5, 13.5, 12.5),
			Block.box(12, 13, 4, 12.5, 13.5, 12),Block.box(3.5, 13, 4, 4, 13.5, 12),
			Block.box(4, 13, 12, 12, 13.5, 12.5),Block.box(4, 13, 3.5, 12, 13.5, 4),
			Block.box(12, 3, 3.5, 12.5, 13.5, 4),Block.box(2.5, 2, 2.5, 13.5, 2.5, 3),
			Block.box(2.5, 2, 13, 13.5, 2.5, 13.5),Block.box(2.5, 2, 3, 3, 2.5, 13),
			Block.box(13, 2, 3, 13.5, 2.5, 13),Block.box(4, 3, 3, 12, 13, 4),
			Block.box(4, 13, 4, 12, 14, 5),Block.box(11, 13, 5, 12, 14, 11),
			Block.box(4, 13, 5, 5, 14, 11),Block.box(4, 13, 11, 12, 14, 12),
			Block.box(12.01, 3, 4, 13.01, 13, 12),Block.box(13, 0, 3, 15, 2, 5),
			Block.box(13, 0, 7, 15, 2, 9),Block.box(13, 0, 11, 15, 2, 13),
			Block.box(3, 0, 3, 13, 2, 13),Block.box(11, 0, 13, 13, 2, 15),
			Block.box(7, 0, 13, 9, 2, 15),Block.box(3, 0, 13, 5, 2, 15),
			Block.box(1, 0, 11, 3, 2, 13),Block.box(1, 0, 7, 3, 2, 9),
			Block.box(1, 0, 3, 3, 2, 5),Block.box(3, 0, 1, 5, 2, 3),
			Block.box(7, 0, 1, 9, 2, 3),Block.box(11, 0, 1, 13, 2, 3)
			).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get();
		private static final VoxelShape SHAPE_S = Stream.of(
			Block.box(2.9901, 3, 4, 3.9901, 13, 12),Block.box(3, 2, 3, 13, 3, 13),
			Block.box(4, 3, 12, 12, 13, 13),Block.box(3.5, 3, 12, 4, 13.5, 12.5),
			Block.box(3.5, 3, 3.5, 4, 13.5, 4),Block.box(12, 3, 12, 12.5, 13.5, 12.5),
			Block.box(12, 13, 4, 12.5, 13.5, 12),Block.box(3.5, 13, 4, 4, 13.5, 12),
			Block.box(4, 13, 12, 12, 13.5, 12.5),Block.box(4, 13, 3.5, 12, 13.5, 4),
			Block.box(12, 3, 3.5, 12.5, 13.5, 4),Block.box(2.5, 2, 2.5, 13.5, 2.5, 3),
			Block.box(2.5, 2, 13, 13.5, 2.5, 13.5),Block.box(2.5, 2, 3, 3, 2.5, 13),
			Block.box(13, 2, 3, 13.5, 2.5, 13),Block.box(4, 3, 3, 12, 13, 4),
			Block.box(4, 13, 4, 12, 14, 5),Block.box(11, 13, 5, 12, 14, 11),
			Block.box(4, 13, 5, 5, 14, 11),Block.box(4, 13, 11, 12, 14, 12),
			Block.box(12.01, 3, 4, 13.01, 13, 12),Block.box(13, 0, 3, 15, 2, 5),
			Block.box(13, 0, 7, 15, 2, 9),Block.box(13, 0, 11, 15, 2, 13),
			Block.box(3, 0, 3, 13, 2, 13),Block.box(11, 0, 13, 13, 2, 15),
			Block.box(7, 0, 13, 9, 2, 15),Block.box(3, 0, 13, 5, 2, 15),
			Block.box(1, 0, 11, 3, 2, 13),Block.box(1, 0, 7, 3, 2, 9),
			Block.box(1, 0, 3, 3, 2, 5),Block.box(3, 0, 1, 5, 2, 3),
			Block.box(7, 0, 1, 9, 2, 3),Block.box(11, 0, 1, 13, 2, 3)
			).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get();
		private static final VoxelShape SHAPE_E = Stream.of(
			Block.box(2.9901, 3, 4, 3.9901, 13, 12),Block.box(3, 2, 3, 13, 3, 13),
			Block.box(4, 3, 12, 12, 13, 13),Block.box(3.5, 3, 12, 4, 13.5, 12.5),
			Block.box(3.5, 3, 3.5, 4, 13.5, 4),Block.box(12, 3, 12, 12.5, 13.5, 12.5),
			Block.box(12, 13, 4, 12.5, 13.5, 12),Block.box(3.5, 13, 4, 4, 13.5, 12),
			Block.box(4, 13, 12, 12, 13.5, 12.5),Block.box(4, 13, 3.5, 12, 13.5, 4),
			Block.box(12, 3, 3.5, 12.5, 13.5, 4),Block.box(2.5, 2, 2.5, 13.5, 2.5, 3),
			Block.box(2.5, 2, 13, 13.5, 2.5, 13.5),Block.box(2.5, 2, 3, 3, 2.5, 13),
			Block.box(13, 2, 3, 13.5, 2.5, 13),Block.box(4, 3, 3, 12, 13, 4),
			Block.box(4, 13, 4, 12, 14, 5),Block.box(11, 13, 5, 12, 14, 11),
			Block.box(4, 13, 5, 5, 14, 11),Block.box(4, 13, 11, 12, 14, 12),
			Block.box(12.01, 3, 4, 13.01, 13, 12),Block.box(13, 0, 3, 15, 2, 5),
			Block.box(13, 0, 7, 15, 2, 9),Block.box(13, 0, 11, 15, 2, 13),
			Block.box(3, 0, 3, 13, 2, 13),Block.box(11, 0, 13, 13, 2, 15),
			Block.box(7, 0, 13, 9, 2, 15),Block.box(3, 0, 13, 5, 2, 15),
			Block.box(1, 0, 11, 3, 2, 13),Block.box(1, 0, 7, 3, 2, 9),
			Block.box(1, 0, 3, 3, 2, 5),Block.box(3, 0, 1, 5, 2, 3),
			Block.box(7, 0, 1, 9, 2, 3),Block.box(11, 0, 1, 13, 2, 3)
			).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get();
		private static final VoxelShape SHAPE_W = Stream.of(
			Block.box(2.9901, 3, 4, 3.9901, 13, 12),Block.box(3, 2, 3, 13, 3, 13),
			Block.box(4, 3, 12, 12, 13, 13),Block.box(3.5, 3, 12, 4, 13.5, 12.5),
			Block.box(3.5, 3, 3.5, 4, 13.5, 4),Block.box(12, 3, 12, 12.5, 13.5, 12.5),
			Block.box(12, 13, 4, 12.5, 13.5, 12),Block.box(3.5, 13, 4, 4, 13.5, 12),
			Block.box(4, 13, 12, 12, 13.5, 12.5),Block.box(4, 13, 3.5, 12, 13.5, 4),
			Block.box(12, 3, 3.5, 12.5, 13.5, 4),Block.box(2.5, 2, 2.5, 13.5, 2.5, 3),
			Block.box(2.5, 2, 13, 13.5, 2.5, 13.5),Block.box(2.5, 2, 3, 3, 2.5, 13),
			Block.box(13, 2, 3, 13.5, 2.5, 13),Block.box(4, 3, 3, 12, 13, 4),
			Block.box(4, 13, 4, 12, 14, 5),Block.box(11, 13, 5, 12, 14, 11),
			Block.box(4, 13, 5, 5, 14, 11),Block.box(4, 13, 11, 12, 14, 12),
			Block.box(12.01, 3, 4, 13.01, 13, 12),Block.box(13, 0, 3, 15, 2, 5),
			Block.box(13, 0, 7, 15, 2, 9),Block.box(13, 0, 11, 15, 2, 13),
			Block.box(3, 0, 3, 13, 2, 13),Block.box(11, 0, 13, 13, 2, 15),
			Block.box(7, 0, 13, 9, 2, 15),Block.box(3, 0, 13, 5, 2, 15),
			Block.box(1, 0, 11, 3, 2, 13),Block.box(1, 0, 7, 3, 2, 9),
			Block.box(1, 0, 3, 3, 2, 5),Block.box(3, 0, 1, 5, 2, 3),
			Block.box(7, 0, 1, 9, 2, 3),Block.box(11, 0, 1, 13, 2, 3)
			).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get();
	
	public CauldronBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return TileEntityInit.CAULDRON_BLOCK_TILE.get().create();
	}
	
	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
		if(!world.isClientSide()) {
			ItemStack heldItem = player.getItemInHand(hand);
            TileEntity tileEntity = world.getBlockEntity(pos);
            
            if(tileEntity != null) {
            	LazyOptional<IFluidHandler> fluidHandlerCap = tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY);

                if (fluidHandlerCap.isPresent()) {
                	IFluidHandler fluidHandler = fluidHandlerCap.orElseThrow(IllegalStateException::new);
                	
                	if(heldItem.getItem() == Items.BUCKET) {
                		if(!fluidHandler.drain(1000, IFluidHandler.FluidAction.SIMULATE).isEmpty()) {
                			player.level.playSound(null, player.blockPosition(), SoundEvents.BUCKET_EMPTY, SoundCategory.PLAYERS, 1f, 1f);
                		}
                		return (FluidUtil.interactWithFluidHandler(player, hand, fluidHandler)) ? ActionResultType.SUCCESS : ActionResultType.FAIL;
                	} else if(heldItem.getItem() == Items.WATER_BUCKET) {
                		if(fluidHandler.fill(new FluidStack(Fluids.WATER, 1000), IFluidHandler.FluidAction.SIMULATE) == 1000) {
                			player.level.playSound(null, player.blockPosition(), SoundEvents.BUCKET_FILL, SoundCategory.PLAYERS, 1f, 1f);
                		}
                		return (FluidUtil.interactWithFluidHandler(player, hand, fluidHandler)) ? ActionResultType.SUCCESS : ActionResultType.FAIL;
                	} else {
                		return ActionResultType.FAIL;
                	}
                }
            }
		}
		return ActionResultType.SUCCESS;
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
		return 1f;
	}
}
