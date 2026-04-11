package com.github.theredbrain.netheritebuckets.registry;

import com.github.theredbrain.netheritebuckets.NetheriteBuckets;
import com.github.theredbrain.netheritebuckets.world.level.block.NetheriteCauldronBlock;
import com.github.theredbrain.netheritebuckets.world.level.block.NetheriteLavaCauldronBlock;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import java.util.List;

public class BlockRegistry {
	public static ResourceKey<Block> NETHERITE_CAULDRON_BLOCK_KEY = ResourceKey.create(Registries.BLOCK, NetheriteBuckets.identifier("netherite_cauldron"));
	public static ResourceKey<Item> NETHERITE_CAULDRON_ITEM_KEY = ResourceKey.create(Registries.ITEM, NetheriteBuckets.identifier("netherite_cauldron"));
	public static ResourceKey<Block> NETHERITE_LAVA_CAULDRON_BLOCK_KEY = ResourceKey.create(Registries.BLOCK, NetheriteBuckets.identifier("netherite_lava_cauldron"));
	public static final Block NETHERITE_LAVA_CAULDRON = registerBlockWithoutItem(NETHERITE_LAVA_CAULDRON_BLOCK_KEY, new NetheriteLavaCauldronBlock(BlockBehaviour.Properties.of().setId(NETHERITE_LAVA_CAULDRON_BLOCK_KEY).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(2.0F).noOcclusion().lightLevel(state -> 15)));
	public static final Block NETHERITE_CAULDRON = registerBlock(NETHERITE_CAULDRON_BLOCK_KEY, NETHERITE_CAULDRON_ITEM_KEY, new NetheriteCauldronBlock(BlockBehaviour.Properties.of().setId(NETHERITE_CAULDRON_BLOCK_KEY).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(2.0F).noOcclusion()), List.of(CreativeModeTabs.FUNCTIONAL_BLOCKS, CreativeModeTabs.REDSTONE_BLOCKS), NETHERITE_LAVA_CAULDRON);

	private static Block registerBlock(ResourceKey<Block> block_key, ResourceKey<Item> item_key, Block block, List<ResourceKey<CreativeModeTab>> creativeModeTabList, Block... alternatives) {
		Item blockItem = Registry.register(BuiltInRegistries.ITEM, item_key, new BlockItem(block, new Item.Properties().setId(item_key).fireResistant()));
		for (ResourceKey<CreativeModeTab> creativeModeTab : creativeModeTabList) {
			CreativeModeTabEvents.modifyOutputEvent(creativeModeTab).register(content -> content.accept(block));
		}
		for (Block alternative : alternatives) {
			Item.BY_BLOCK.put(alternative, blockItem);
		}
		return Registry.register(BuiltInRegistries.BLOCK, block_key, block);
	}

	private static Block registerBlockWithoutItem(ResourceKey<Block> block_key, Block block) {
		return Registry.register(BuiltInRegistries.BLOCK, block_key, block);
	}

	public static void init() {
	}
}
