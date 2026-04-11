package com.github.theredbrain.netheritebuckets.mixin.world.level.material;

import com.github.theredbrain.netheritebuckets.registry.ItemRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.LavaFluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LavaFluid.class)
public class LavaFluidMixin {

    @Inject(method = "getBucket", at = @At("HEAD"), cancellable = true)
    public void netheritebuckets$getBucket(CallbackInfoReturnable<Item> cir) {
        cir.setReturnValue(ItemRegistry.NETHERITE_LAVA_BUCKET);
    }
}
