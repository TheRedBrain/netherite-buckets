package com.github.theredbrain.netheritebuckets;

import com.github.theredbrain.netheritebuckets.config.ServerConfig;
import com.github.theredbrain.netheritebuckets.registry.BlockRegistry;
import com.github.theredbrain.netheritebuckets.registry.ItemRegistry;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetheriteBuckets implements ModInitializer {
	public static final String MOD_ID = "netheritebuckets";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static ServerConfig SERVER_CONFIG = ConfigApiJava.registerAndLoadConfig(ServerConfig::new);

	@Override
	public void onInitialize() {
		LOGGER.info("Turning netherite into buckets!");
		BlockRegistry.init();
		ItemRegistry.init();
	}

	public static Identifier identifier(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
