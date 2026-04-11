package com.github.theredbrain.netheritebuckets.mixin.world.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;

@Mixin(BucketItem.class)
public class BucketItemMixin {

	@WrapOperation(method = "use", constant = @Constant(classValue = BucketPickup.class, ordinal = 0))
	public boolean netheritebuckets$wrap_use(Object object, Operation<Boolean> original, @Local(name = "blockState") BlockState blockState) {
		return original.call(object) && !blockState.getFluidState().getType().isSame(Fluids.LAVA);
	}
}
