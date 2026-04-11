package com.github.theredbrain.netheritebuckets.mixin.server;

import com.github.theredbrain.netheritebuckets.core.netheritecauldron.NetheriteCauldronInteractions;
import net.minecraft.server.Bootstrap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Bootstrap.class)
public class BootstrapMixin {

	@Inject(method = "bootStrap", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/dispenser/DispenseItemBehavior;bootStrap()V", shift = At.Shift.AFTER))
	private static void netheritebuckets$bootStrap(CallbackInfo info) {
		NetheriteCauldronInteractions.bootStrap();
	}
}
