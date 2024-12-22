package com.github.theredbrain.netheritebuckets.mixin.block;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.CauldronBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CauldronBlock.class)
public class CauldronBlockMixin {

	@ModifyReturnValue(
			method = "canBeFilledByDripstone",
			at = @At("RETURN")
	)
	protected boolean netheritebuckets$canBeFilledByDripstone(boolean original, Fluid fluid) {
		if (fluid.matchesType(Fluids.LAVA)) {
			return false;
		}
		return original;
	}

}
