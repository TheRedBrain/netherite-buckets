package com.github.theredbrain.netheritebuckets.core.netheritecauldron;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@FunctionalInterface
public interface NetheriteCauldronInteraction {
	NetheriteCauldronInteraction DEFAULT = (var0, var1, var2, var3, var4, var5) -> InteractionResult.TRY_WITH_EMPTY_HAND;

	InteractionResult interact(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack itemInHand);

	public static class Dispatcher {
		private final Map<TagKey<Item>, NetheriteCauldronInteraction> tags = new HashMap();
		private final Map<Item, NetheriteCauldronInteraction> items = new HashMap();

		void put(final Item item, final NetheriteCauldronInteraction interaction) {
			this.items.put(item, interaction);
		}

		void put(final TagKey<Item> tag, final NetheriteCauldronInteraction interaction) {
			this.tags.put(tag, interaction);
		}

		public NetheriteCauldronInteraction get(final ItemStack itemStack) {
			for (Entry<TagKey<Item>, NetheriteCauldronInteraction> e : this.tags.entrySet()) {
				if (itemStack.is((TagKey<Item>)e.getKey())) {
					return (NetheriteCauldronInteraction)e.getValue();
				}
			}

			return (NetheriteCauldronInteraction)this.items.getOrDefault(itemStack.getItem(), NetheriteCauldronInteraction.DEFAULT);
		}
	}
}
