package com.github.theredbrain.netheritebuckets.registry;

import com.github.theredbrain.netheritebuckets.NetheriteBuckets;
import com.github.theredbrain.netheritebuckets.world.item.NetheriteBucketItem;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluids;

import java.util.List;

public class ItemRegistry {

	public static ResourceKey<Item> NETHERITE_BUCKET_KEY = ResourceKey.create(Registries.ITEM, NetheriteBuckets.identifier("netherite_bucket"));
	public static ResourceKey<Item> NETHERITE_LAVA_BUCKET_KEY = ResourceKey.create(Registries.ITEM, NetheriteBuckets.identifier("netherite_lava_bucket"));
	public static final Item NETHERITE_BUCKET = registerItem(NETHERITE_BUCKET_KEY, new NetheriteBucketItem(Fluids.EMPTY, new Item.Properties().setId(NETHERITE_BUCKET_KEY).stacksTo(16).fireResistant()), List.of(CreativeModeTabs.TOOLS_AND_UTILITIES));
	public static final Item NETHERITE_LAVA_BUCKET = registerItem(NETHERITE_LAVA_BUCKET_KEY, new NetheriteBucketItem(Fluids.LAVA, new Item.Properties().setId(NETHERITE_LAVA_BUCKET_KEY).craftRemainder(NETHERITE_BUCKET).stacksTo(1).fireResistant()), List.of(CreativeModeTabs.TOOLS_AND_UTILITIES));

	private static Item registerItem(ResourceKey<Item> key, Item item, List<ResourceKey<CreativeModeTab>> creativeModeTabList) {

		for (ResourceKey<CreativeModeTab> creativeModeTab : creativeModeTabList) {
			CreativeModeTabEvents.modifyOutputEvent(creativeModeTab).register(content -> {
				content.accept(item);
			});
		}

		return Registry.register(BuiltInRegistries.ITEM, key, item);
	}

	public static void init() {
	}
}
