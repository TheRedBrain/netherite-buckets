package com.github.theredbrain.netheritebuckets.mixin.item;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidDrainable;
import net.minecraft.block.FluidFillable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BucketItem.class)
public abstract class BucketItemMixin extends Item {

	@Shadow
	@Final
	private Fluid fluid;

	public BucketItemMixin(Settings settings) {
		super(settings);
	}

	@Shadow
	public static ItemStack getEmptiedStack(ItemStack stack, PlayerEntity player) {
		throw new AssertionError();
	}

	@Shadow
	public void onEmptied(@Nullable LivingEntity user, World world, ItemStack stack, BlockPos pos) {
		throw new AssertionError();
	}

	@Shadow
	public boolean placeFluid(@Nullable LivingEntity user, World world, BlockPos pos, @Nullable BlockHitResult hitResult) {
		throw new AssertionError();
	}

	/**
	 * @author TheRedBrain
	 * @reason WIP
	 */
	@Overwrite
	public ActionResult use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		BlockHitResult blockHitResult = raycast(
				world, user, this.fluid == Fluids.EMPTY ? RaycastContext.FluidHandling.SOURCE_ONLY : RaycastContext.FluidHandling.NONE
		);
		if (blockHitResult.getType() == HitResult.Type.MISS) {
			return ActionResult.PASS;
		} else if (blockHitResult.getType() != HitResult.Type.BLOCK) {
			return ActionResult.PASS;
		} else {
			BlockPos blockPos = blockHitResult.getBlockPos();
			Direction direction = blockHitResult.getSide();
			BlockPos blockPos2 = blockPos.offset(direction);
			if (!world.canEntityModifyAt(user, blockPos) || !user.canPlaceOn(blockPos2, direction, itemStack)) {
				return ActionResult.FAIL;
			} else if (this.fluid == Fluids.EMPTY) {
				BlockState blockState = world.getBlockState(blockPos);
				if (blockState.getBlock() instanceof FluidDrainable fluidDrainable && !blockState.getFluidState().getFluid().matchesType(Fluids.LAVA)) {
					ItemStack itemStack2 = fluidDrainable.tryDrainFluid(user, world, blockPos, blockState);
					if (!itemStack2.isEmpty()) {
						user.incrementStat(Stats.USED.getOrCreateStat(this));
						fluidDrainable.getBucketFillSound().ifPresent(sound -> user.playSound(sound, 1.0F, 1.0F));
						world.emitGameEvent(user, GameEvent.FLUID_PICKUP, blockPos);
						ItemStack itemStack3 = ItemUsage.exchangeStack(itemStack, user, itemStack2);
						if (!world.isClient) {
							Criteria.FILLED_BUCKET.trigger((ServerPlayerEntity) user, itemStack2);
						}

						return ActionResult.SUCCESS.withNewHandStack(itemStack3);
					}
				}

				return ActionResult.FAIL;
			} else {
				BlockState blockState = world.getBlockState(blockPos);
				BlockPos blockPos3 = blockState.getBlock() instanceof FluidFillable && this.fluid == Fluids.WATER ? blockPos : blockPos2;
				if (this.placeFluid(user, world, blockPos3, blockHitResult)) {
					this.onEmptied(user, world, itemStack, blockPos3);
					if (user instanceof ServerPlayerEntity) {
						Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity) user, blockPos3, itemStack);
					}

					user.incrementStat(Stats.USED.getOrCreateStat(this));
					ItemStack itemStack2 = ItemUsage.exchangeStack(itemStack, user, getEmptiedStack(itemStack, user));
					return ActionResult.SUCCESS.withNewHandStack(itemStack2);
				} else {
					return ActionResult.FAIL;
				}
			}
		}
	}
}
