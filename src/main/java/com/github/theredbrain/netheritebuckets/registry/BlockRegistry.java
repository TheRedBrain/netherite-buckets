package com.github.theredbrain.netheritebuckets.registry;

import com.github.theredbrain.netheritebuckets.NetheriteBuckets;
import com.github.theredbrain.netheritebuckets.block.NetheriteCauldronBlock;
import com.github.theredbrain.netheritebuckets.block.NetheriteLavaCauldronBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.List;

public class BlockRegistry {
	public static RegistryKey<Block> NETHERITE_CAULDRON_BLOCK_KEY = RegistryKey.of(RegistryKeys.BLOCK, NetheriteBuckets.identifier("netherite_cauldron"));
	public static RegistryKey<Item> NETHERITE_CAULDRON_ITEN_KEY = RegistryKey.of(RegistryKeys.ITEM, NetheriteBuckets.identifier("netherite_cauldron"));
	public static RegistryKey<Block> NETHERITE_LAVA_CAULDRON_BLOCK_KEY = RegistryKey.of(RegistryKeys.BLOCK, NetheriteBuckets.identifier("netherite_lava_cauldron"));
	public static final Block NETHERITE_CAULDRON = registerBlock(NETHERITE_CAULDRON_BLOCK_KEY, NETHERITE_CAULDRON_ITEN_KEY, new NetheriteCauldronBlock(AbstractBlock.Settings.create().registryKey(NETHERITE_CAULDRON_BLOCK_KEY).mapColor(MapColor.BLACK).requiresTool().strength(2.0F).nonOpaque()), List.of(ItemGroups.FUNCTIONAL, ItemGroups.REDSTONE));
	public static final Block NETHERITE_LAVA_CAULDRON = registerBlockWithoutItem(NETHERITE_LAVA_CAULDRON_BLOCK_KEY, new NetheriteLavaCauldronBlock(AbstractBlock.Settings.create().registryKey(NETHERITE_LAVA_CAULDRON_BLOCK_KEY).mapColor(MapColor.BLACK).requiresTool().strength(2.0F).nonOpaque().luminance(state -> 15)));

	private static Block registerBlock(RegistryKey<Block> block_key, RegistryKey<Item> item_key, Block block, List<RegistryKey<ItemGroup>> itemGroupList) {
		Registry.register(Registries.ITEM, item_key, new BlockItem(block, new Item.Settings().registryKey(item_key)));
		for (RegistryKey<ItemGroup> itemGroup : itemGroupList) {
			ItemGroupEvents.modifyEntriesEvent(itemGroup).register(content -> content.add(block));
		}
		return Registry.register(Registries.BLOCK, block_key, block);
	}

	private static Block registerBlockWithoutItem(RegistryKey<Block> block_key, Block block) {
		return Registry.register(Registries.BLOCK, block_key, block);
	}

	public static void init() {
	}
}
