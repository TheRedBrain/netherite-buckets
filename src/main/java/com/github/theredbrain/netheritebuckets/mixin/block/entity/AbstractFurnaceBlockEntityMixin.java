package com.github.theredbrain.netheritebuckets.mixin.block.entity;

import com.github.theredbrain.netheritebuckets.registry.ItemRegistry;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin {

	@Shadow
	private static void addFuel(Map<Item, Integer> fuelTimes, ItemConvertible item, int fuelTime) {
		throw new AssertionError();
	}

	@Inject(method = "createFuelTimeMap", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/AbstractFurnaceBlockEntity;addFuel(Ljava/util/Map;Lnet/minecraft/item/ItemConvertible;I)V", ordinal = 0))
	private static void createFuelTimeMap(CallbackInfoReturnable<Map<Item, Integer>> cir, @Local(ordinal = 1) Map<Item, Integer> map2) {
		addFuel(map2, ItemRegistry.NETHERITE_LAVA_BUCKET, 20000);
	}
}
