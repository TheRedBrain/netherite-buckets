package com.github.theredbrain.netheritebuckets.registry;

import com.github.theredbrain.netheritebuckets.NetheriteBuckets;
import com.github.theredbrain.netheritebuckets.item.NetheriteBucketItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import org.jetbrains.annotations.Nullable;

public class ItemRegistry {

	public static RegistryKey<Item> NETHERITE_BUCKET_KEY = RegistryKey.of(RegistryKeys.ITEM, NetheriteBuckets.identifier("netherite_bucket"));
	public static RegistryKey<Item> NETHERITE_LAVA_BUCKET_KEY = RegistryKey.of(RegistryKeys.ITEM, NetheriteBuckets.identifier("netherite_lava_bucket"));
	public static final Item NETHERITE_BUCKET = registerItem(NETHERITE_BUCKET_KEY, new NetheriteBucketItem(Fluids.EMPTY, new Item.Settings().registryKey(NETHERITE_BUCKET_KEY).maxCount(16)), ItemGroups.TOOLS);
	public static final Item NETHERITE_LAVA_BUCKET = registerItem(NETHERITE_LAVA_BUCKET_KEY, new NetheriteBucketItem(Fluids.LAVA, new Item.Settings().registryKey(NETHERITE_LAVA_BUCKET_KEY).recipeRemainder(NETHERITE_BUCKET).maxCount(1)), ItemGroups.TOOLS);

	private static Item registerItem(RegistryKey<Item> key, Item item, @Nullable RegistryKey<ItemGroup> itemGroup) {

		if (itemGroup != null) {
			ItemGroupEvents.modifyEntriesEvent(itemGroup).register(content -> {
				content.add(item);
			});
		}
		return Registry.register(Registries.ITEM, key, item);
	}

	public static void init() {
	}
}
