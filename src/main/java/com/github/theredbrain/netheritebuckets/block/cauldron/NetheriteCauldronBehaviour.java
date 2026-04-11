package com.github.theredbrain.netheritebuckets.block.cauldron;

import com.github.theredbrain.netheritebuckets.registry.BlockRegistry;
import com.github.theredbrain.netheritebuckets.registry.ItemRegistry;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Map;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public interface NetheriteCauldronBehaviour {
	Map<String, NetheriteCauldronBehaviorMap> BEHAVIOR_MAPS = new Object2ObjectArrayMap<>();
	Codec<NetheriteCauldronBehaviorMap> CODEC = Codec.stringResolver(NetheriteCauldronBehaviorMap::name, BEHAVIOR_MAPS::get);
	NetheriteCauldronBehaviorMap EMPTY_NETHERITE_CAULDRON_BEHAVIOR = createMap("empty");
	NetheriteCauldronBehaviorMap NETHERITE_LAVA_CAULDRON_BEHAVIOR = createMap("lava");
	NetheriteCauldronBehaviour FILL_WITH_LAVA = (state, world, pos, player, hand, stack) -> {
		return fillCauldron(world, pos, player, hand, stack, BlockRegistry.NETHERITE_LAVA_CAULDRON.defaultBlockState(), SoundEvents.BUCKET_EMPTY_LAVA);
	};

	static NetheriteCauldronBehaviorMap createMap(String name) {
		Object2ObjectOpenHashMap<Item, NetheriteCauldronBehaviour> object2ObjectOpenHashMap = new Object2ObjectOpenHashMap<>();
		object2ObjectOpenHashMap.defaultReturnValue((state, world, pos, player, hand, stack) -> InteractionResult.TRY_WITH_EMPTY_HAND);
		NetheriteCauldronBehaviorMap cauldronBehaviorMap = new NetheriteCauldronBehaviorMap(name, object2ObjectOpenHashMap);
		BEHAVIOR_MAPS.put(name, cauldronBehaviorMap);
		return cauldronBehaviorMap;
	}

	InteractionResult interact(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, ItemStack stack);

	static void registerBehavior() {
		Map<Item, NetheriteCauldronBehaviour> map = EMPTY_NETHERITE_CAULDRON_BEHAVIOR.map();
		registerBucketBehavior(map);
		Map<Item, NetheriteCauldronBehaviour> map3 = NETHERITE_LAVA_CAULDRON_BEHAVIOR.map();
		map3.put(
				ItemRegistry.NETHERITE_BUCKET,
				(NetheriteCauldronBehaviour) (state, world, pos, player, hand, stack) -> emptyCauldron(
						state, world, pos, player, hand, stack, new ItemStack(ItemRegistry.NETHERITE_LAVA_BUCKET), statex -> true, SoundEvents.BUCKET_FILL_LAVA
				)
		);
		registerBucketBehavior(map3);
	}

	static void registerBucketBehavior(Map<Item, NetheriteCauldronBehaviour> behavior) {
		behavior.put(ItemRegistry.NETHERITE_LAVA_BUCKET, FILL_WITH_LAVA);
	}

	static InteractionResult emptyCauldron(
			BlockState state,
			Level world,
			BlockPos pos,
			Player player,
			InteractionHand hand,
			ItemStack stack,
			ItemStack output,
			Predicate<BlockState> fullPredicate,
			SoundEvent soundEvent
	) {
		if (!fullPredicate.test(state)) {
			return InteractionResult.TRY_WITH_EMPTY_HAND;
		} else {
			if (!world.isClientSide()) {
				Item item = stack.getItem();
				player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, output));
				player.awardStat(Stats.USE_CAULDRON); // TODO custom stats
				player.awardStat(Stats.ITEM_USED.get(item));
				world.setBlockAndUpdate(pos, BlockRegistry.NETHERITE_CAULDRON.defaultBlockState());
				world.playSound(null, pos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
				world.gameEvent(null, GameEvent.FLUID_PICKUP, pos);
			}

			return InteractionResult.SUCCESS;
		}
	}


	static InteractionResult fillCauldron(Level world, BlockPos pos, Player player, InteractionHand hand, ItemStack stack, BlockState state, SoundEvent soundEvent) {
		if (!world.isClientSide()) {
			Item item = stack.getItem();
			player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(ItemRegistry.NETHERITE_BUCKET)));
			player.awardStat(Stats.FILL_CAULDRON); // TODO custom stats
			player.awardStat(Stats.ITEM_USED.get(item));
			world.setBlockAndUpdate(pos, state);
			world.playSound(null, pos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
			world.gameEvent(null, GameEvent.FLUID_PLACE, pos);
		}

		return InteractionResult.SUCCESS;
	}

	public static record NetheriteCauldronBehaviorMap(String name, Map<Item, NetheriteCauldronBehaviour> map) {
	}
}
