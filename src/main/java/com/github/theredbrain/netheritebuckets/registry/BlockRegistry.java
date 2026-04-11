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

import java.util.List;

public class BlockRegistry {
	public static final Block NETHERITE_LAVA_CAULDRON = registerBlockWithoutItem("netherite_lava_cauldron", new NetheriteLavaCauldronBlock(AbstractBlock.Settings.create().mapColor(MapColor.BLACK).requiresTool().strength(2.0F).nonOpaque().luminance(state -> 15)));
	public static final Block NETHERITE_CAULDRON = registerBlock("netherite_cauldron", new NetheriteCauldronBlock(AbstractBlock.Settings.create().mapColor(MapColor.BLACK).requiresTool().strength(2.0F).nonOpaque()), List.of(ItemGroups.FUNCTIONAL, ItemGroups.REDSTONE), NETHERITE_LAVA_CAULDRON);

	private static Block registerBlock(String name, Block block, List<RegistryKey<ItemGroup>> itemGroupList, Block... blocks) {
		Item blockItem = Registry.register(Registries.ITEM, NetheriteBuckets.identifier(name), new BlockItem(block, new Item.Settings().fireproof()));
		for (RegistryKey<ItemGroup> itemGroup : itemGroupList) {
			ItemGroupEvents.modifyEntriesEvent(itemGroup).register(content -> content.add(block));
		}
		for (Block block2 : blocks) {
			Item.BLOCK_ITEMS.put(block2, blockItem);
		}
		return Registry.register(Registries.BLOCK, NetheriteBuckets.identifier(name), block);
	}

	private static Block registerBlockWithoutItem(String name, Block block) {
		return Registry.register(Registries.BLOCK, NetheriteBuckets.identifier(name), block);
	}

	public static void init() {
	}
}
