package com.github.theredbrain.netheritebuckets.mixin.world.level.block;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CauldronBlock.class)
public class CauldronBlockMixin {

	@ModifyReturnValue(
			method = "canReceiveStalactiteDrip",
			at = @At("RETURN")
	)
	protected boolean netheritebuckets$canReceiveStalactiteDrip(boolean original, Fluid fluid) {
		if (fluid.isSame(Fluids.LAVA)) {
			return false;
		}
		return original;
	}

}
