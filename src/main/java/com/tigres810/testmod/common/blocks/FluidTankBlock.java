package com.tigres810.testmod.common.blocks;

import java.util.stream.Stream;

import com.tigres810.testmod.core.init.FluidInit;
import com.tigres810.testmod.core.init.ItemInit;
import com.tigres810.testmod.core.init.TileEntityInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
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

public class FluidTankBlock extends Block {

	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	private static final VoxelShape SHAPE_N = Stream.of(
			Block.box(10.7, 0.8, 11, 12, 11.9, 13),Block.box(4, 0.8, 11, 5.3, 11.9, 13),
			Block.box(3, 0, 4, 4, 12, 12),Block.box(4, 0.8, 3, 5.3, 11.9, 5),
			Block.box(3, 0, 3, 13, 1, 4),Block.box(10.7, 0.8, 3, 12, 11.9, 5),
			Block.box(12, 0, 4, 13, 12, 12),Block.box(5.5, 13.21, 5.5, 10.5, 15.5, 10.5),
			Block.box(5.3, 11.4, 3, 10.7, 11.9, 4),Block.box(5.3, 11.4, 12, 10.7, 11.9, 13),
			Block.box(3, 0, 12, 13, 1, 13),Block.box(5.3, 1, 12, 10.7, 1.5, 13),
			Block.box(5.3, 1, 3, 10.7, 1.5, 4),Block.box(4, 11.8, 3.5, 12, 12.7, 12.5),
			Block.box(4, 0, 4, 12, 1, 12),Block.box(12, 1, 12, 12.5, 12.4, 12.5),
			Block.box(12, 1, 3.5, 12.5, 12.4, 4),Block.box(3.5, 1, 3.5, 4, 12.4, 4),
			Block.box(3.5, 1, 12, 4, 12.4, 12.5),Block.box(3.5, 11.9, 4, 4, 12.4, 12),
			Block.box(5, 12.7, 5, 11, 13.2, 11),Block.box(10.5, 15.5, 5, 11, 16, 11),
			Block.box(5, 15.5, 5, 5.5, 16, 11),Block.box(5.5, 15.5, 5, 10.5, 16, 5.5),
			Block.box(5.5, 15.5, 10.5, 10.5, 16, 11),Block.box(12, 11.9, 4, 12.5, 12.4, 12),
			Block.box(2.5, 0, 2.5, 13.5, 0.5, 3),Block.box(2.5, 0, 3, 3, 0.5, 13),
			Block.box(13, 0, 3, 13.5, 0.5, 13),Block.box(2.5, 0, 13, 13.5, 0.5, 13.5),
			Block.box(10, 15.5, 10, 10.5, 16, 10.5),Block.box(9.5, 15.5, 9.5, 10, 16, 10),
			Block.box(9, 15.5, 9, 9.5, 16, 9.5),Block.box(7, 15.5, 8.5, 9, 16, 9),
			Block.box(6.5, 15.5, 9, 7, 16, 9.5),Block.box(6, 15.5, 9.5, 6.5, 16, 10),
			Block.box(5.5, 15.5, 10, 6, 16, 10.5),Block.box(7, 15.5, 7, 9, 16, 7.5),
			Block.box(9, 15.5, 6.5, 9.5, 16, 7),Block.box(9.5, 15.5, 6, 10, 16, 6.5),
			Block.box(10, 15.5, 5.5, 10.5, 16, 6),Block.box(6.5, 15.5, 6.5, 7, 16, 7),
			Block.box(6, 15.5, 6, 6.5, 16, 6.5),Block.box(5.5, 15.5, 5.5, 6, 16, 6)
			).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get();
		private static final VoxelShape SHAPE_S = Stream.of(
			Block.box(10.7, 0.8, 11, 12, 11.9, 13),Block.box(4, 0.8, 11, 5.3, 11.9, 13),
			Block.box(3, 0, 4, 4, 12, 12),Block.box(4, 0.8, 3, 5.3, 11.9, 5),
			Block.box(3, 0, 3, 13, 1, 4),Block.box(10.7, 0.8, 3, 12, 11.9, 5),
			Block.box(12, 0, 4, 13, 12, 12),Block.box(5.5, 13.21, 5.5, 10.5, 15.5, 10.5),
			Block.box(5.3, 11.4, 3, 10.7, 11.9, 4),Block.box(5.3, 11.4, 12, 10.7, 11.9, 13),
			Block.box(3, 0, 12, 13, 1, 13),Block.box(5.3, 1, 12, 10.7, 1.5, 13),
			Block.box(5.3, 1, 3, 10.7, 1.5, 4),Block.box(4, 11.8, 3.5, 12, 12.7, 12.5),
			Block.box(4, 0, 4, 12, 1, 12),Block.box(12, 1, 12, 12.5, 12.4, 12.5),
			Block.box(12, 1, 3.5, 12.5, 12.4, 4),Block.box(3.5, 1, 3.5, 4, 12.4, 4),
			Block.box(3.5, 1, 12, 4, 12.4, 12.5),Block.box(3.5, 11.9, 4, 4, 12.4, 12),
			Block.box(5, 12.7, 5, 11, 13.2, 11),Block.box(10.5, 15.5, 5, 11, 16, 11),
			Block.box(5, 15.5, 5, 5.5, 16, 11),Block.box(5.5, 15.5, 5, 10.5, 16, 5.5),
			Block.box(5.5, 15.5, 10.5, 10.5, 16, 11),Block.box(12, 11.9, 4, 12.5, 12.4, 12),
			Block.box(2.5, 0, 2.5, 13.5, 0.5, 3),Block.box(2.5, 0, 3, 3, 0.5, 13),
			Block.box(13, 0, 3, 13.5, 0.5, 13),Block.box(2.5, 0, 13, 13.5, 0.5, 13.5),
			Block.box(10, 15.5, 10, 10.5, 16, 10.5),Block.box(9.5, 15.5, 9.5, 10, 16, 10),
			Block.box(9, 15.5, 9, 9.5, 16, 9.5),Block.box(7, 15.5, 8.5, 9, 16, 9),
			Block.box(6.5, 15.5, 9, 7, 16, 9.5),Block.box(6, 15.5, 9.5, 6.5, 16, 10),
			Block.box(5.5, 15.5, 10, 6, 16, 10.5),Block.box(7, 15.5, 7, 9, 16, 7.5),
			Block.box(9, 15.5, 6.5, 9.5, 16, 7),Block.box(9.5, 15.5, 6, 10, 16, 6.5),
			Block.box(10, 15.5, 5.5, 10.5, 16, 6),Block.box(6.5, 15.5, 6.5, 7, 16, 7),
			Block.box(6, 15.5, 6, 6.5, 16, 6.5),Block.box(5.5, 15.5, 5.5, 6, 16, 6)
			).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get();
		private static final VoxelShape SHAPE_E = Stream.of(
			Block.box(11, 0.8, 4, 13, 11.9, 5.300000000000001),Block.box(11, 0.8, 10.7, 13, 11.9, 12),
			Block.box(4, 0, 12, 12, 12, 13),Block.box(3, 0.8, 10.7, 5, 11.9, 12),
			Block.box(3, 0, 3, 4, 1, 13),Block.box(3, 0.8, 4, 5, 11.9, 5.300000000000001),
			Block.box(4, 0, 3, 12, 12, 4),Block.box(5.5, 13.21, 5.5, 10.5, 15.5, 10.5),
			Block.box(3, 11.4, 5.300000000000001, 4, 11.9, 10.7),Block.box(12, 11.4, 5.300000000000001, 13, 11.9, 10.7),
			Block.box(12, 0, 3, 13, 1, 13),Block.box(12, 1, 5.300000000000001, 13, 1.5, 10.7),
			Block.box(3, 1, 5.300000000000001, 4, 1.5, 10.7),Block.box(3.5, 11.8, 4, 12.5, 12.7, 12),
			Block.box(4, 0, 4, 12, 1, 12),Block.box(12, 1, 3.5, 12.5, 12.4, 4),
			Block.box(3.5, 1, 3.5, 4, 12.4, 4),Block.box(3.5, 1, 12, 4, 12.4, 12.5),
			Block.box(12, 1, 12, 12.5, 12.4, 12.5),Block.box(4, 11.9, 12, 12, 12.4, 12.5),
			Block.box(5, 12.7, 5, 11, 13.2, 11),Block.box(5, 15.5, 5, 11, 16, 5.5),
			Block.box(5, 15.5, 10.5, 11, 16, 11),Block.box(5, 15.5, 5.5, 5.5, 16, 10.5),
			Block.box(10.5, 15.5, 5.5, 11, 16, 10.5),Block.box(4, 11.9, 3.5, 12, 12.4, 4),
			Block.box(2.5, 0, 2.5, 3, 0.5, 13.5),Block.box(3, 0, 13, 13, 0.5, 13.5),
			Block.box(3, 0, 2.5, 13, 0.5, 3),Block.box(13, 0, 2.5, 13.5, 0.5, 13.5),
			Block.box(10, 15.5, 5.5, 10.5, 16, 6),Block.box(9.5, 15.5, 6, 10, 16, 6.5),
			Block.box(9, 15.5, 6.5, 9.5, 16, 7),Block.box(8.5, 15.5, 7, 9, 16, 9),
			Block.box(9, 15.5, 9, 9.5, 16, 9.5),Block.box(9.5, 15.5, 9.5, 10, 16, 10),
			Block.box(10, 15.5, 10, 10.5, 16, 10.5),Block.box(7, 15.5, 7, 7.5, 16, 9),
			Block.box(6.5, 15.5, 6.5, 7, 16, 7),Block.box(6, 15.5, 6, 6.5, 16, 6.5),
			Block.box(5.5, 15.5, 5.5, 6, 16, 6),Block.box(6.5, 15.5, 9, 7, 16, 9.5),
			Block.box(6, 15.5, 9.5, 6.5, 16, 10),Block.box(5.5, 15.5, 10, 6, 16, 10.5)
			).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get();
		private static final VoxelShape SHAPE_W = Stream.of(
			Block.box(11, 0.8, 4, 13, 11.9, 5.300000000000001),Block.box(11, 0.8, 10.7, 13, 11.9, 12),
			Block.box(4, 0, 12, 12, 12, 13),Block.box(3, 0.8, 10.7, 5, 11.9, 12),
			Block.box(3, 0, 3, 4, 1, 13),Block.box(3, 0.8, 4, 5, 11.9, 5.300000000000001),
			Block.box(4, 0, 3, 12, 12, 4),Block.box(5.5, 13.21, 5.5, 10.5, 15.5, 10.5),
			Block.box(3, 11.4, 5.300000000000001, 4, 11.9, 10.7),Block.box(12, 11.4, 5.300000000000001, 13, 11.9, 10.7),
			Block.box(12, 0, 3, 13, 1, 13),Block.box(12, 1, 5.300000000000001, 13, 1.5, 10.7),
			Block.box(3, 1, 5.300000000000001, 4, 1.5, 10.7),Block.box(3.5, 11.8, 4, 12.5, 12.7, 12),
			Block.box(4, 0, 4, 12, 1, 12),Block.box(12, 1, 3.5, 12.5, 12.4, 4),
			Block.box(3.5, 1, 3.5, 4, 12.4, 4),Block.box(3.5, 1, 12, 4, 12.4, 12.5),
			Block.box(12, 1, 12, 12.5, 12.4, 12.5),Block.box(4, 11.9, 12, 12, 12.4, 12.5),
			Block.box(5, 12.7, 5, 11, 13.2, 11),Block.box(5, 15.5, 5, 11, 16, 5.5),
			Block.box(5, 15.5, 10.5, 11, 16, 11),Block.box(5, 15.5, 5.5, 5.5, 16, 10.5),
			Block.box(10.5, 15.5, 5.5, 11, 16, 10.5),Block.box(4, 11.9, 3.5, 12, 12.4, 4),
			Block.box(2.5, 0, 2.5, 3, 0.5, 13.5),Block.box(3, 0, 13, 13, 0.5, 13.5),
			Block.box(3, 0, 2.5, 13, 0.5, 3),Block.box(13, 0, 2.5, 13.5, 0.5, 13.5),
			Block.box(10, 15.5, 5.5, 10.5, 16, 6),Block.box(9.5, 15.5, 6, 10, 16, 6.5),
			Block.box(9, 15.5, 6.5, 9.5, 16, 7),Block.box(8.5, 15.5, 7, 9, 16, 9),
			Block.box(9, 15.5, 9, 9.5, 16, 9.5),Block.box(9.5, 15.5, 9.5, 10, 16, 10),
			Block.box(10, 15.5, 10, 10.5, 16, 10.5),Block.box(7, 15.5, 7, 7.5, 16, 9),
			Block.box(6.5, 15.5, 6.5, 7, 16, 7),Block.box(6, 15.5, 6, 6.5, 16, 6.5),
			Block.box(5.5, 15.5, 5.5, 6, 16, 6),Block.box(6.5, 15.5, 9, 7, 16, 9.5),
			Block.box(6, 15.5, 9.5, 6.5, 16, 10),Block.box(5.5, 15.5, 10, 6, 16, 10.5)
			).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get();
	
	public FluidTankBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return TileEntityInit.FLUIDTANK_BLOCK_TILE.get().create();
	}
	
	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
		if(!world.isClientSide()) {
			ItemStack heldItem = player.getItemInHand(hand);
            TileEntity tileEntity = world.getBlockEntity(pos);

            if (tileEntity != null) {
                LazyOptional<IFluidHandler> fluidHandlerCap = tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY);

                if (fluidHandlerCap.isPresent()) {
                    IFluidHandler fluidHandler = fluidHandlerCap.orElseThrow(IllegalStateException::new);

                    if (heldItem.getItem() == Items.GLASS_BOTTLE) {
                        if (fluidHandler.drain(500, IFluidHandler.FluidAction.SIMULATE).getAmount() == 500) {
                            fluidHandler.drain(500, IFluidHandler.FluidAction.EXECUTE);

                            player.level.playSound(null, player.blockPosition(), SoundEvents.BOTTLE_FILL, SoundCategory.PLAYERS, 1f, 1f);

                            heldItem.shrink(1);
                            ItemStack itemPotion = PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER);

                            if (!player.addItem(itemPotion)) {
                            	player.addItem(itemPotion);
                            }

                            return ActionResultType.SUCCESS;
                        }
                    } else if (heldItem.getItem() == Items.POTION && heldItem.getTag() != null) {
                        if (heldItem.getTag().getString("Potion").equals("minecraft:water")) {
                            if (fluidHandler.fill(new FluidStack(FluidInit.FLUX_FLUID.get(), 500), IFluidHandler.FluidAction.SIMULATE) == 500) {
                                fluidHandler.fill(new FluidStack(FluidInit.FLUX_FLUID.get(), 500), IFluidHandler.FluidAction.EXECUTE);

                                player.level.playSound(null, player.blockPosition(), SoundEvents.BOTTLE_EMPTY, SoundCategory.PLAYERS, 1f, 1f);

                                heldItem.shrink(1);
                                ItemStack itemBottle = new ItemStack(Items.GLASS_BOTTLE);

                                if (!player.addItem(itemBottle)) {
                                	player.addItem(itemBottle);
                                }

                                return ActionResultType.SUCCESS;
                            }
                        }
                    } else {
                    	if(heldItem.getItem() == Items.BUCKET) {
                    		if(!fluidHandler.drain(1000, IFluidHandler.FluidAction.SIMULATE).isEmpty()) {
                    			player.level.playSound(null, player.blockPosition(), SoundEvents.BUCKET_EMPTY, SoundCategory.PLAYERS, 1f, 1f);
                    		}
                    		return (FluidUtil.interactWithFluidHandler(player, hand, fluidHandler)) ? ActionResultType.SUCCESS : ActionResultType.FAIL;
                    	} else if(heldItem.getItem() == ItemInit.FLUX_FLUID_BUCKET.get()) {
                    		if(fluidHandler.fill(new FluidStack(FluidInit.FLUX_FLUID.get(), 1000), IFluidHandler.FluidAction.SIMULATE) == 1000) {
                    			player.level.playSound(null, player.blockPosition(), SoundEvents.BUCKET_FILL, SoundCategory.PLAYERS, 1f, 1f);
                    		}
                    		return (FluidUtil.interactWithFluidHandler(player, hand, fluidHandler)) ? ActionResultType.SUCCESS : ActionResultType.FAIL;
                    	} else {
                    		return(ActionResultType.FAIL);
                    	}
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
		return 0.8f;
	}
}
