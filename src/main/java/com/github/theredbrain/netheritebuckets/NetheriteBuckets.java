package com.github.theredbrain.netheritebuckets;

import com.github.theredbrain.netheritebuckets.registry.BlockRegistry;
import com.github.theredbrain.netheritebuckets.registry.ItemRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetheriteBuckets implements ModInitializer {
	public static final String MOD_ID = "netheritebuckets";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Turning netherite into buckets!");
		ItemRegistry.init();
		BlockRegistry.init();
		FuelRegistryEvents.BUILD.register((builder, context) -> {
			builder.add(ItemRegistry.NETHERITE_LAVA_BUCKET, 20000);
		});
	}

	public static ResourceLocation identifier(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}
}
