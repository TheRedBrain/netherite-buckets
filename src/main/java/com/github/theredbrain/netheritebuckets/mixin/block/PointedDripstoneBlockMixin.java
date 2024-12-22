package com.github.theredbrain.netheritebuckets.mixin.block;

import com.github.theredbrain.netheritebuckets.block.AbstractNetheriteCauldronBlock;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.PointedDripstoneBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

@Mixin(PointedDripstoneBlock.class)
public class PointedDripstoneBlockMixin {

	@Shadow
	private static Optional<BlockPos> searchInDirection(WorldAccess world, BlockPos pos, Direction.AxisDirection direction, BiPredicate<BlockPos, BlockState> continuePredicate, Predicate<BlockState> stopPredicate, int range) {
		throw new AssertionError();
	}

	@Shadow
	private static boolean canDripThrough(BlockView world, BlockPos pos, BlockState state) {
		throw new AssertionError();
	}

	/**
	 * @author TheRedBrain
	 * @reason WPI
	 */
	@Overwrite
	private static @Nullable BlockPos getCauldronPos(World world, BlockPos pos, Fluid fluid) {
		Predicate<BlockState> predicate = (state) -> {
			return ((state.getBlock() instanceof AbstractCauldronBlock && ((AbstractCauldronBlockInvoker) state.getBlock()).canBeFilledByDripstone(fluid)) || (state.getBlock() instanceof AbstractNetheriteCauldronBlock && ((AbstractNetheriteCauldronBlock) state.getBlock()).canBeFilledByDripstone(fluid)));
		};
		BiPredicate<BlockPos, BlockState> biPredicate = (posx, state) -> {
			return canDripThrough(world, posx, state);
		};
		return (BlockPos) searchInDirection(world, pos, Direction.DOWN.getDirection(), biPredicate, predicate, 11).orElse(null);
	}
}
