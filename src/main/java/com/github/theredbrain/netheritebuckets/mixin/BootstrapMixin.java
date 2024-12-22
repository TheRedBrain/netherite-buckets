package com.github.theredbrain.netheritebuckets.mixin;

import com.github.theredbrain.netheritebuckets.block.cauldron.NetheriteCauldronBehaviour;
import net.minecraft.Bootstrap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Bootstrap.class)
public class BootstrapMixin {

    @Inject(method = "initialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/dispenser/DispenserBehavior;registerDefaults()V", shift = At.Shift.AFTER))
    private static void netheritebuckets$initialize(CallbackInfo info) {
        NetheriteCauldronBehaviour.registerBehavior();
    }
}
