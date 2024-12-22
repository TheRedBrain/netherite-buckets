package com.github.theredbrain.netheritebuckets.mixin.fluid;

import com.github.theredbrain.netheritebuckets.registry.ItemRegistry;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LavaFluid.class)
public class LavaFluidMixin {

    @Inject(method = "getBucketItem", at = @At("HEAD"), cancellable = true)
    public void netheritebuckets$getBucketItem(CallbackInfoReturnable<Item> cir) {
        cir.setReturnValue(ItemRegistry.NETHERITE_LAVA_BUCKET);
    }
}
