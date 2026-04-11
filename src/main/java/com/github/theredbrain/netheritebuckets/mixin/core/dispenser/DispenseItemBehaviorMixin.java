package com.github.theredbrain.netheritebuckets.mixin.core.dispenser;

import com.github.theredbrain.netheritebuckets.registry.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DispenseItemBehavior.class)
public interface DispenseItemBehaviorMixin {

	@Inject(method = "bootStrap", at = @At("TAIL"))
	private static void netheritebuckets$bootStrap(CallbackInfo ci) {

		DispenserBlock.registerBehavior(ItemRegistry.NETHERITE_LAVA_BUCKET, new DefaultDispenseItemBehavior() {
			private final DefaultDispenseItemBehavior fallbackBehavior = new DefaultDispenseItemBehavior();

			@Override
			public ItemStack execute(BlockSource pointer, ItemStack stack) {
				DispensibleContainerItem fluidModificationItem = (DispensibleContainerItem)stack.getItem();
				BlockPos blockPos = pointer.pos().relative(pointer.state().getValue(DispenserBlock.FACING));
				Level world = pointer.level();
				if (fluidModificationItem.emptyContents(null, world, blockPos, null)) {
					fluidModificationItem.checkExtraContent(null, world, stack, blockPos);
					return this.consumeWithRemainder(pointer, stack, new ItemStack(ItemRegistry.NETHERITE_BUCKET));
				} else {
					return this.fallbackBehavior.dispense(pointer, stack);
				}
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.NETHERITE_BUCKET, new DefaultDispenseItemBehavior() {
			@Override
			public ItemStack execute(BlockSource pointer, ItemStack stack) {
				LevelAccessor worldAccess = pointer.level();
				BlockPos blockPos = pointer.pos().relative(pointer.state().getValue(DispenserBlock.FACING));
				BlockState blockState = worldAccess.getBlockState(blockPos);
				if (blockState.getBlock() instanceof BucketPickup fluidDrainable && blockState.getFluidState().getType().isSame(Fluids.LAVA)) {
					ItemStack itemStack = fluidDrainable.pickupBlock(null, worldAccess, blockPos, blockState);
					if (itemStack.isEmpty()) {
						return super.execute(pointer, stack);
					} else {
						worldAccess.gameEvent(null, GameEvent.FLUID_PICKUP, blockPos);
						Item item = itemStack.getItem();
						return this.consumeWithRemainder(pointer, stack, new ItemStack(item));
					}
				} else {
					return super.execute(pointer, stack);
				}
			}
		});
		DispenserBlock.registerBehavior(Items.BUCKET, new DefaultDispenseItemBehavior() {
			@Override
			public ItemStack execute(BlockSource pointer, ItemStack stack) {
				LevelAccessor worldAccess = pointer.level();
				BlockPos blockPos = pointer.pos().relative(pointer.state().getValue(DispenserBlock.FACING));
				BlockState blockState = worldAccess.getBlockState(blockPos);
				if (blockState.getBlock() instanceof BucketPickup fluidDrainable && !blockState.getFluidState().getType().isSame(Fluids.LAVA)) {
					ItemStack itemStack = fluidDrainable.pickupBlock(null, worldAccess, blockPos, blockState);
					if (itemStack.isEmpty()) {
						return super.execute(pointer, stack);
					} else {
						worldAccess.gameEvent(null, GameEvent.FLUID_PICKUP, blockPos);
						Item item = itemStack.getItem();
						return this.consumeWithRemainder(pointer, stack, new ItemStack(item));
					}
				} else {
					return super.execute(pointer, stack);
				}
			}
		});
	}
}
