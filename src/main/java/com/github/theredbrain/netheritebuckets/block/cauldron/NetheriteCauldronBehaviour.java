package com.github.theredbrain.netheritebuckets.block.cauldron;

import com.github.theredbrain.netheritebuckets.registry.BlockRegistry;
import com.github.theredbrain.netheritebuckets.registry.ItemRegistry;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Map;
import java.util.function.Predicate;

public interface NetheriteCauldronBehaviour {
	Map<String, NetheriteCauldronBehaviour.NetheriteCauldronBehaviorMap> BEHAVIOR_MAPS = new Object2ObjectArrayMap<>();
	Codec<NetheriteCauldronBehaviour.NetheriteCauldronBehaviorMap> CODEC = Codec.stringResolver(NetheriteCauldronBehaviour.NetheriteCauldronBehaviorMap::name, BEHAVIOR_MAPS::get);
	NetheriteCauldronBehaviour.NetheriteCauldronBehaviorMap EMPTY_NETHERITE_CAULDRON_BEHAVIOR = createMap("empty");
	NetheriteCauldronBehaviour.NetheriteCauldronBehaviorMap NETHERITE_LAVA_CAULDRON_BEHAVIOR = createMap("lava");
	NetheriteCauldronBehaviour FILL_WITH_LAVA = (state, world, pos, player, hand, stack) -> {
		return fillCauldron(world, pos, player, hand, stack, BlockRegistry.NETHERITE_LAVA_CAULDRON.getDefaultState(), SoundEvents.ITEM_BUCKET_EMPTY_LAVA);
	};

	static NetheriteCauldronBehaviour.NetheriteCauldronBehaviorMap createMap(String name) {
		Object2ObjectOpenHashMap<Item, NetheriteCauldronBehaviour> object2ObjectOpenHashMap = new Object2ObjectOpenHashMap<>();
		object2ObjectOpenHashMap.defaultReturnValue((state, world, pos, player, hand, stack) -> ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION);
		NetheriteCauldronBehaviour.NetheriteCauldronBehaviorMap cauldronBehaviorMap = new NetheriteCauldronBehaviour.NetheriteCauldronBehaviorMap(name, object2ObjectOpenHashMap);
		BEHAVIOR_MAPS.put(name, cauldronBehaviorMap);
		return cauldronBehaviorMap;
	}

	ActionResult interact(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack);

	static void registerBehavior() {
		Map<Item, NetheriteCauldronBehaviour> map = EMPTY_NETHERITE_CAULDRON_BEHAVIOR.map();
		registerBucketBehavior(map);
		Map<Item, NetheriteCauldronBehaviour> map3 = NETHERITE_LAVA_CAULDRON_BEHAVIOR.map();
		map3.put(
				ItemRegistry.NETHERITE_BUCKET,
				(NetheriteCauldronBehaviour) (state, world, pos, player, hand, stack) -> emptyCauldron(
						state, world, pos, player, hand, stack, new ItemStack(ItemRegistry.NETHERITE_LAVA_BUCKET), statex -> true, SoundEvents.ITEM_BUCKET_FILL_LAVA
				)
		);
		registerBucketBehavior(map3);
	}

	static void registerBucketBehavior(Map<Item, NetheriteCauldronBehaviour> behavior) {
		behavior.put(ItemRegistry.NETHERITE_LAVA_BUCKET, FILL_WITH_LAVA);
	}

	static ActionResult emptyCauldron(
			BlockState state,
			World world,
			BlockPos pos,
			PlayerEntity player,
			Hand hand,
			ItemStack stack,
			ItemStack output,
			Predicate<BlockState> fullPredicate,
			SoundEvent soundEvent
	) {
		if (!fullPredicate.test(state)) {
			return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
		} else {
			if (!world.isClient) {
				Item item = stack.getItem();
				player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, output));
				player.incrementStat(Stats.USE_CAULDRON); // TODO custom stats
				player.incrementStat(Stats.USED.getOrCreateStat(item));
				world.setBlockState(pos, BlockRegistry.NETHERITE_CAULDRON.getDefaultState());
				world.playSound(null, pos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.emitGameEvent(null, GameEvent.FLUID_PICKUP, pos);
			}

			return ActionResult.SUCCESS;
		}
	}


	static ActionResult fillCauldron(World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack, BlockState state, SoundEvent soundEvent) {
		if (!world.isClient) {
			Item item = stack.getItem();
			player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(ItemRegistry.NETHERITE_BUCKET)));
			player.incrementStat(Stats.FILL_CAULDRON); // TODO custom stats
			player.incrementStat(Stats.USED.getOrCreateStat(item));
			world.setBlockState(pos, state);
			world.playSound(null, pos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
			world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
		}

		return ActionResult.SUCCESS;
	}

	public static record NetheriteCauldronBehaviorMap(String name, Map<Item, NetheriteCauldronBehaviour> map) {
	}
}
