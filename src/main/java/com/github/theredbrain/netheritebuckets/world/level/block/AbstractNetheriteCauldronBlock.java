package com.github.theredbrain.netheritebuckets.world.level.block;

import com.github.theredbrain.netheritebuckets.core.netheritecauldron.NetheriteCauldronInteraction;
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
	private static final VoxelShape SHAPE_INSIDE = Block.column(12.0, 4.0, 16.0);
	protected static final VoxelShape SHAPE = Util.make(
			() -> {
				int i = 4;
				int j = 3;
				int k = 2;
				return Shapes.join(
						Shapes.block(),
						Shapes.or(
								Block.column(16.0, 8.0, 0.0, 3.0), Block.column(8.0, 16.0, 0.0, 3.0), Block.column(12.0, 0.0, 3.0), SHAPE_INSIDE
						),
						BooleanOp.ONLY_FIRST
				);
			}
	);
	protected final NetheriteCauldronInteraction.Dispatcher interactions;

	@Override
	protected abstract MapCodec<? extends AbstractNetheriteCauldronBlock> codec();

	public AbstractNetheriteCauldronBlock(Properties settings, NetheriteCauldronInteraction.Dispatcher interactions) {
		super(settings);
		this.interactions = interactions;
	}

	protected double getContentHeight(BlockState state) {
		return 0.0;
	}

	@Override
	protected InteractionResult useItemOn(
			final ItemStack itemStack,
			final BlockState state,
			final Level level,
			final BlockPos pos,
			final Player player,
			final InteractionHand hand,
			final BlockHitResult hitResult
	) {
		NetheriteCauldronInteraction behavior = this.interactions.get(itemStack);
		return behavior.interact(state, level, pos, player, hand, itemStack);
	}

	@Override
	protected VoxelShape getShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {
		return SHAPE;
	}

	@Override
	protected VoxelShape getInteractionShape(final BlockState state, final BlockGetter level, final BlockPos pos) {
		return SHAPE_INSIDE;
	}

	@Override
	protected boolean hasAnalogOutputSignal(final BlockState state) {
		return true;
	}

	@Override
	protected boolean isPathfindable(final BlockState state, final PathComputationType type) {
		return false;
	}

	public abstract boolean isFull(final BlockState state);

	@Override
	protected void tick(final BlockState cauldronState, final ServerLevel level, final BlockPos pos, final RandomSource random) {
		BlockPos stalactitePos = PointedDripstoneBlock.findStalactiteTipAboveCauldron(level, pos);
		if (stalactitePos != null) {
			Fluid fluid = PointedDripstoneBlock.getCauldronFillFluidType(level, stalactitePos);
			if (fluid != Fluids.EMPTY && this.canReceiveStalactiteDrip(fluid)) {
				this.receiveStalactiteDrip(cauldronState, level, pos, fluid);
			}
		}
	}

	public boolean canReceiveStalactiteDrip(final Fluid fluid) {
		return false;
	}

	public void receiveStalactiteDrip(final BlockState state, final Level level, final BlockPos pos, final Fluid fluid) {
	}
}
