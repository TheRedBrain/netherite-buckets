package com.github.theredbrain.netheritebuckets.mixin.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidDrainable;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;

@Mixin(BucketItem.class)
public class BucketItemMixin {

	@WrapOperation(method = "use", constant = @Constant(classValue = FluidDrainable.class, ordinal = 0))
	public boolean netheritebuckets$wrap_use(Object object, Operation<Boolean> original, @Local BlockState blockState) {
		return original.call(object) && !blockState.getFluidState().getFluid().matchesType(Fluids.LAVA);
	}
}