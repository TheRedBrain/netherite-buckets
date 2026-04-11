package com.github.theredbrain.netheritebuckets.mixin.world.level.block;

import com.github.theredbrain.netheritebuckets.world.level.block.AbstractNetheriteCauldronBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

@Mixin(PointedDripstoneBlock.class)
public abstract class PointedDripstoneBlockMixin {

	@Shadow
	private static boolean canDripThrough(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState) {
		throw new AssertionError();
	}

	@Shadow
	private static Optional<BlockPos> findBlockVertical(LevelAccessor levelAccessor, BlockPos blockPos, Direction.AxisDirection axisDirection, BiPredicate<BlockPos, BlockState> biPredicate, Predicate<BlockState> predicate, int i) {
		throw new AssertionError();
	}

	/**
	 * @author TheRedBrain
	 * @reason WIP
	 */
	@Overwrite
	@Nullable
	private static BlockPos findFillableCauldronBelowStalactiteTip(Level level, BlockPos blockPos, Fluid fluid) {
		Predicate<BlockState> predicate = (state) -> {
			return ((state.getBlock() instanceof AbstractCauldronBlock && ((AbstractCauldronBlockInvoker) state.getBlock()).canReceiveStalactiteDrip(fluid)) || (state.getBlock() instanceof AbstractNetheriteCauldronBlock && ((AbstractNetheriteCauldronBlock) state.getBlock()).canReceiveStalactiteDrip(fluid)));
		};
		BiPredicate<BlockPos, BlockState> biPredicate = (blockPosx, blockState) -> canDripThrough(level, blockPosx, blockState);
		return (BlockPos) findBlockVertical(level, blockPos, Direction.DOWN.getAxisDirection(), biPredicate, predicate, 11).orElse(null);
	}


}
