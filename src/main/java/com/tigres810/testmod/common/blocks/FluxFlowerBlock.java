package com.tigres810.testmod.common.blocks;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FluxFlowerBlock extends FlowerBlock {

	public FluxFlowerBlock(Effect effect, int duration, Properties properties) {
		super(effect, duration, properties);
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
		VoxelShape voxelshape = this.getShape(state, world, pos, ISelectionContext.empty());
	      Vector3d vector3d = voxelshape.bounds().getCenter();
	      double d0 = (double)pos.getX() + vector3d.x;
	      double d1 = (double)pos.getZ() + vector3d.z;

	      for(int i = 0; i < 1; ++i) {
	         if (random.nextBoolean()) {
	            world.addParticle(ParticleTypes.DRIPPING_OBSIDIAN_TEAR, d0 + random.nextDouble() / 5.0D, (double)pos.getY() + (0.5D - random.nextDouble()), d1 + random.nextDouble() / 5.0D, 0.0D, 0.0D, 0.0D);
	         }
	      }
	}

}