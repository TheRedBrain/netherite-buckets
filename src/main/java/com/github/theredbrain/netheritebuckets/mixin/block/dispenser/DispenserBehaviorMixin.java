package com.github.theredbrain.netheritebuckets.mixin.block.dispenser;

import com.github.theredbrain.netheritebuckets.registry.ItemRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.FluidDrainable;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.FluidModificationItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DispenserBehavior.class)
public interface DispenserBehaviorMixin {

	@Inject(method = "registerDefaults", at = @At("TAIL"))
	private static void netheritebuckets$registerDefaults(CallbackInfo ci) {

		DispenserBlock.registerBehavior(ItemRegistry.NETHERITE_LAVA_BUCKET, new ItemDispenserBehavior() {
			private final ItemDispenserBehavior fallbackBehavior = new ItemDispenserBehavior();

			@Override
			public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
				FluidModificationItem fluidModificationItem = (FluidModificationItem)stack.getItem();
				BlockPos blockPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
				World world = pointer.world();
				if (fluidModificationItem.placeFluid(null, world, blockPos, null)) {
					fluidModificationItem.onEmptied(null, world, stack, blockPos);
					return this.decrementStackWithRemainder(pointer, stack, new ItemStack(ItemRegistry.NETHERITE_BUCKET));
				} else {
					return this.fallbackBehavior.dispense(pointer, stack);
				}
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.NETHERITE_BUCKET, new ItemDispenserBehavior() {
			@Override
			public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
				WorldAccess worldAccess = pointer.world();
				BlockPos blockPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
				BlockState blockState = worldAccess.getBlockState(blockPos);
				if (blockState.getBlock() instanceof FluidDrainable fluidDrainable && blockState.getFluidState().getFluid().matchesType(Fluids.LAVA)) {
					ItemStack itemStack = fluidDrainable.tryDrainFluid(null, worldAccess, blockPos, blockState);
					if (itemStack.isEmpty()) {
						return super.dispenseSilently(pointer, stack);
					} else {
						worldAccess.emitGameEvent(null, GameEvent.FLUID_PICKUP, blockPos);
						Item item = itemStack.getItem();
						return this.decrementStackWithRemainder(pointer, stack, new ItemStack(item));
					}
				} else {
					return super.dispenseSilently(pointer, stack);
				}
			}
		});
		DispenserBlock.registerBehavior(Items.BUCKET, new ItemDispenserBehavior() {
			@Override
			public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
				WorldAccess worldAccess = pointer.world();
				BlockPos blockPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
				BlockState blockState = worldAccess.getBlockState(blockPos);
				if (blockState.getBlock() instanceof FluidDrainable fluidDrainable && !blockState.getFluidState().getFluid().matchesType(Fluids.LAVA)) {
					ItemStack itemStack = fluidDrainable.tryDrainFluid(null, worldAccess, blockPos, blockState);
					if (itemStack.isEmpty()) {
						return super.dispenseSilently(pointer, stack);
					} else {
						worldAccess.emitGameEvent(null, GameEvent.FLUID_PICKUP, blockPos);
						Item item = itemStack.getItem();
						return this.decrementStackWithRemainder(pointer, stack, new ItemStack(item));
					}
				} else {
					return super.dispenseSilently(pointer, stack);
				}
			}
		});
	}
}
