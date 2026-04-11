package com.github.theredbrain.netheritebuckets.block;

import com.github.theredbrain.netheritebuckets.block.cauldron.NetheriteCauldronBehaviour;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class AbstractNetheriteCauldronBlock extends Block {
	private static final VoxelShape RAYCAST_SHAPE = Block.column(12.0, 4.0, 16.0);
	protected static final VoxelShape OUTLINE_SHAPE = Util.make(
			() -> {
				int i = 4;
				int j = 3;
				int k = 2;
				return Shapes.join(
						Shapes.block(),
						Shapes.or(
								Block.column(16.0, 8.0, 0.0, 3.0), Block.column(8.0, 16.0, 0.0, 3.0), Block.column(12.0, 0.0, 3.0), RAYCAST_SHAPE
						),
						BooleanOp.ONLY_FIRST
				);
			}
	);
	protected final NetheriteCauldronBehaviour.NetheriteCauldronBehaviorMap behaviorMap;

	@Override
	protected abstract MapCodec<? extends AbstractNetheriteCauldronBlock> codec();

	public AbstractNetheriteCauldronBlock(Properties settings, NetheriteCauldronBehaviour.NetheriteCauldronBehaviorMap behaviorMap) {
		super(settings);
		this.behaviorMap = behaviorMap;
	}

	protected double getFluidHeight(BlockState state) {
		return 0.0;
	}

	@Override
	protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		NetheriteCauldronBehaviour netheriteCauldronBehavior = (NetheriteCauldronBehaviour) this.behaviorMap.map().get(stack.getItem());
		return netheriteCauldronBehavior.interact(state, world, pos, player, hand, stack);
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return OUTLINE_SHAPE;
	}

	@Override
	protected VoxelShape getInteractionShape(BlockState state, BlockGetter world, BlockPos pos) {
		return RAYCAST_SHAPE;
	}

	@Override
	protected boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	protected boolean isPathfindable(BlockState state, PathComputationType type) {
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
	protected void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		BlockPos blockPos = PointedDripstoneBlock.findStalactiteTipAboveCauldron(world, pos);
		if (blockPos != null) {
			Fluid fluid = PointedDripstoneBlock.getCauldronFillFluidType(world, blockPos);
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
	protected void fillFromDripstone(BlockState state, Level world, BlockPos pos, Fluid fluid) {
	}
}
