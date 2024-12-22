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
import org.jetbrains.annotations.Nullable;

public class ItemRegistry {

	public static final Item NETHERITE_BUCKET = registerItem("netherite_bucket", new NetheriteBucketItem(Fluids.EMPTY, new Item.Settings().maxCount(16)), ItemGroups.TOOLS);
	public static final Item NETHERITE_LAVA_BUCKET = registerItem("netherite_lava_bucket", new NetheriteBucketItem(Fluids.LAVA, new Item.Settings().recipeRemainder(NETHERITE_BUCKET).maxCount(1)), ItemGroups.TOOLS);

	private static Item registerItem(String name, Item item, @Nullable RegistryKey<ItemGroup> itemGroup) {

		if (itemGroup != null) {
			ItemGroupEvents.modifyEntriesEvent(itemGroup).register(content -> {
				content.add(item);
			});
		}
		return Registry.register(Registries.ITEM, NetheriteBuckets.identifier(name), item);
	}

	public static void init() {
	}
}
