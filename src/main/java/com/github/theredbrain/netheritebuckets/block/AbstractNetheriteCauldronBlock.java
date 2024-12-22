package com.github.theredbrain.netheritebuckets.block;

import com.github.theredbrain.netheritebuckets.block.cauldron.NetheriteCauldronBehaviour;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PointedDripstoneBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public abstract class AbstractNetheriteCauldronBlock extends Block {
	private static final VoxelShape RAYCAST_SHAPE = createCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 16.0D, 14.0D);
	protected static final VoxelShape OUTLINE_SHAPE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.union(createCuboidShape(0.0D, 0.0D, 4.0D, 16.0D, 3.0D, 12.0D), new VoxelShape[]{createCuboidShape(4.0D, 0.0D, 0.0D, 12.0D, 3.0D, 16.0D), createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D), RAYCAST_SHAPE}), BooleanBiFunction.ONLY_FIRST);
	protected final NetheriteCauldronBehaviour.NetheriteCauldronBehaviorMap behaviorMap;

	@Override
	protected abstract MapCodec<? extends AbstractNetheriteCauldronBlock> getCodec();

	public AbstractNetheriteCauldronBlock(Settings settings, NetheriteCauldronBehaviour.NetheriteCauldronBehaviorMap behaviorMap) {
		super(settings);
		this.behaviorMap = behaviorMap;
	}

	protected double getFluidHeight(BlockState state) {
		return 0.0;
	}

	protected boolean isEntityTouchingFluid(BlockState state, BlockPos pos, Entity entity) {
		return entity.getY() < (double) pos.getY() + this.getFluidHeight(state) && entity.getBoundingBox().maxY > (double) pos.getY() + 0.25;
	}

	@Override
	protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		NetheriteCauldronBehaviour netheriteCauldronBehavior = (NetheriteCauldronBehaviour) this.behaviorMap.map().get(stack.getItem());
		return netheriteCauldronBehavior.interact(state, world, pos, player, hand, stack);
	}

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return OUTLINE_SHAPE;
	}

	@Override
	protected VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
		return RAYCAST_SHAPE;
	}

	@Override
	protected boolean hasComparatorOutput(BlockState state) {
		return true;
	}

	@Override
	protected boolean canPathfindThrough(BlockState state, NavigationType type) {
		return false;
	}

	/**
	 * {@return {@code true} if the specified cauldron state is completely full,
	 * {@code false} otherwise}
	 *
	 * @param state the cauldron state to check
	 */
	public abstract boolean isFull(BlockState state);

	@Override
	protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		BlockPos blockPos = PointedDripstoneBlock.getDripPos(world, pos);
		if (blockPos != null) {
			Fluid fluid = PointedDripstoneBlock.getDripFluid(world, blockPos);
			if (fluid != Fluids.EMPTY && this.canBeFilledByDripstone(fluid)) {
				this.fillFromDripstone(state, world, pos, fluid);
			}
		}
	}

	/**
	 * Checks if this cauldron block can be filled with the specified fluid by dripstone.
	 *
	 * @param fluid the fluid to check
	 * @return {@code true} if this block can be filled, {@code false} otherwise
	 */
	public boolean canBeFilledByDripstone(Fluid fluid) {
		return false;
	}

	/**
	 * Fills a cauldron with one level of the specified fluid if possible.
	 *
	 * @param pos   the cauldron's position
	 * @param world the world where the cauldron is located
	 * @param fluid the fluid to fill the cauldron with
	 * @param state the current cauldron state
	 */
	protected void fillFromDripstone(BlockState state, World world, BlockPos pos, Fluid fluid) {
	}
}
