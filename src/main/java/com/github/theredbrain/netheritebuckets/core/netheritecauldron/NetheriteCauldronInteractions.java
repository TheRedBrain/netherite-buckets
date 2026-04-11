package com.github.theredbrain.netheritebuckets.core.netheritecauldron;

import com.github.theredbrain.netheritebuckets.registry.BlockRegistry;
import com.github.theredbrain.netheritebuckets.registry.ItemRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;

import java.util.function.Predicate;

public class NetheriteCauldronInteractions {
	private static final ExtraCodecs.LateBoundIdMapper<String, NetheriteCauldronInteraction.Dispatcher> ID_MAPPER = new ExtraCodecs.LateBoundIdMapper<>();
	public static final Codec<NetheriteCauldronInteraction.Dispatcher> CODEC = ID_MAPPER.codec(Codec.STRING);
	public static final NetheriteCauldronInteraction.Dispatcher EMPTY = newDispatcher("empty");
	//	public static final NetheriteCauldronInteraction.Dispatcher WATER = newDispatcher("water");
	public static final NetheriteCauldronInteraction.Dispatcher LAVA = newDispatcher("lava");
//	public static final NetheriteCauldronInteraction.Dispatcher POWDER_SNOW = newDispatcher("powder_snow");

	private static NetheriteCauldronInteraction.Dispatcher newDispatcher(final String name) {
		NetheriteCauldronInteraction.Dispatcher result = new NetheriteCauldronInteraction.Dispatcher();
		ID_MAPPER.put(name, result);
		return result;
	}

	public static void bootStrap() {
		addDefaultInteractions(EMPTY);
//		EMPTY.put(Items.POTION, (var0, level, pos, player, hand, itemInHand) -> {
//			PotionContents potion = itemInHand.get(DataComponents.POTION_CONTENTS);
//			if (potion != null && potion.is(Potions.WATER)) {
//				if (!level.isClientSide()) {
//					Item usedItem = itemInHand.getItem();
//					player.setItemInHand(hand, ItemUtils.createFilledResult(itemInHand, player, new ItemStack(Items.GLASS_BOTTLE)));
//					player.awardStat(Stats.USE_CAULDRON);
//					player.awardStat(Stats.ITEM_USED.get(usedItem));
//					level.setBlockAndUpdate(pos, Blocks.WATER_CAULDRON.defaultBlockState());
//					level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
//					level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
//				}
//
//				return InteractionResult.SUCCESS;
//			} else {
//				return InteractionResult.TRY_WITH_EMPTY_HAND;
//			}
//		});
//		addDefaultInteractions(WATER);
//		WATER.put(
//			Items.BUCKET,
//			(state, level, pos, player, hand, itemInHand) -> fillBucket(
//				state,
//				level,
//				pos,
//				player,
//				hand,
//				itemInHand,
//				new ItemStack(Items.WATER_BUCKET),
//				s -> (Integer)s.getValue(LayeredCauldronBlock.LEVEL) == 3,
//				SoundEvents.BUCKET_FILL
//			)
//		);
//		WATER.put(Items.GLASS_BOTTLE, (state, level, pos, player, hand, itemInHand) -> {
//			if (!level.isClientSide()) {
//				Item usedItem = itemInHand.getItem();
//				player.setItemInHand(hand, ItemUtils.createFilledResult(itemInHand, player, PotionContents.createItemStack(Items.POTION, Potions.WATER)));
//				player.awardStat(Stats.USE_CAULDRON);
//				player.awardStat(Stats.ITEM_USED.get(usedItem));
//				LayeredCauldronBlock.lowerFillLevel(state, level, pos);
//				level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
//				level.gameEvent(null, GameEvent.FLUID_PICKUP, pos);
//			}
//
//			return InteractionResult.SUCCESS;
//		});
//		WATER.put(Items.POTION, (state, level, pos, player, hand, itemInHand) -> {
//			if ((Integer)state.getValue(LayeredCauldronBlock.LEVEL) == 3) {
//				return InteractionResult.TRY_WITH_EMPTY_HAND;
//			} else {
//				PotionContents potion = itemInHand.get(DataComponents.POTION_CONTENTS);
//				if (potion != null && potion.is(Potions.WATER)) {
//					if (!level.isClientSide()) {
//						player.setItemInHand(hand, ItemUtils.createFilledResult(itemInHand, player, new ItemStack(Items.GLASS_BOTTLE)));
//						player.awardStat(Stats.USE_CAULDRON);
//						player.awardStat(Stats.ITEM_USED.get(itemInHand.getItem()));
//						level.setBlockAndUpdate(pos, state.cycle(LayeredCauldronBlock.LEVEL));
//						level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
//						level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
//					}
//
//					return InteractionResult.SUCCESS;
//				} else {
//					return InteractionResult.TRY_WITH_EMPTY_HAND;
//				}
//			}
//		});
//		WATER.put(ItemTags.CAULDRON_CAN_REMOVE_DYE, NetheriteCauldronInteractions::dyedItemIteration);
//		WATER.put(Items.WHITE_BANNER, NetheriteCauldronInteractions::bannerInteraction);
//		WATER.put(Items.GRAY_BANNER, NetheriteCauldronInteractions::bannerInteraction);
//		WATER.put(Items.BLACK_BANNER, NetheriteCauldronInteractions::bannerInteraction);
//		WATER.put(Items.BLUE_BANNER, NetheriteCauldronInteractions::bannerInteraction);
//		WATER.put(Items.BROWN_BANNER, NetheriteCauldronInteractions::bannerInteraction);
//		WATER.put(Items.CYAN_BANNER, NetheriteCauldronInteractions::bannerInteraction);
//		WATER.put(Items.GREEN_BANNER, NetheriteCauldronInteractions::bannerInteraction);
//		WATER.put(Items.LIGHT_BLUE_BANNER, NetheriteCauldronInteractions::bannerInteraction);
//		WATER.put(Items.LIGHT_GRAY_BANNER, NetheriteCauldronInteractions::bannerInteraction);
//		WATER.put(Items.LIME_BANNER, NetheriteCauldronInteractions::bannerInteraction);
//		WATER.put(Items.MAGENTA_BANNER, NetheriteCauldronInteractions::bannerInteraction);
//		WATER.put(Items.ORANGE_BANNER, NetheriteCauldronInteractions::bannerInteraction);
//		WATER.put(Items.PINK_BANNER, NetheriteCauldronInteractions::bannerInteraction);
//		WATER.put(Items.PURPLE_BANNER, NetheriteCauldronInteractions::bannerInteraction);
//		WATER.put(Items.RED_BANNER, NetheriteCauldronInteractions::bannerInteraction);
//		WATER.put(Items.YELLOW_BANNER, NetheriteCauldronInteractions::bannerInteraction);
//		WATER.put(Items.WHITE_SHULKER_BOX, NetheriteCauldronInteractions::shulkerBoxInteraction);
//		WATER.put(Items.GRAY_SHULKER_BOX, NetheriteCauldronInteractions::shulkerBoxInteraction);
//		WATER.put(Items.BLACK_SHULKER_BOX, NetheriteCauldronInteractions::shulkerBoxInteraction);
//		WATER.put(Items.BLUE_SHULKER_BOX, NetheriteCauldronInteractions::shulkerBoxInteraction);
//		WATER.put(Items.BROWN_SHULKER_BOX, NetheriteCauldronInteractions::shulkerBoxInteraction);
//		WATER.put(Items.CYAN_SHULKER_BOX, NetheriteCauldronInteractions::shulkerBoxInteraction);
//		WATER.put(Items.GREEN_SHULKER_BOX, NetheriteCauldronInteractions::shulkerBoxInteraction);
//		WATER.put(Items.LIGHT_BLUE_SHULKER_BOX, NetheriteCauldronInteractions::shulkerBoxInteraction);
//		WATER.put(Items.LIGHT_GRAY_SHULKER_BOX, NetheriteCauldronInteractions::shulkerBoxInteraction);
//		WATER.put(Items.LIME_SHULKER_BOX, NetheriteCauldronInteractions::shulkerBoxInteraction);
//		WATER.put(Items.MAGENTA_SHULKER_BOX, NetheriteCauldronInteractions::shulkerBoxInteraction);
//		WATER.put(Items.ORANGE_SHULKER_BOX, NetheriteCauldronInteractions::shulkerBoxInteraction);
//		WATER.put(Items.PINK_SHULKER_BOX, NetheriteCauldronInteractions::shulkerBoxInteraction);
//		WATER.put(Items.PURPLE_SHULKER_BOX, NetheriteCauldronInteractions::shulkerBoxInteraction);
//		WATER.put(Items.RED_SHULKER_BOX, NetheriteCauldronInteractions::shulkerBoxInteraction);
//		WATER.put(Items.YELLOW_SHULKER_BOX, NetheriteCauldronInteractions::shulkerBoxInteraction);
		LAVA.put(
				ItemRegistry.NETHERITE_BUCKET,
				(state, level, pos, player, hand, itemInHand) -> fillBucket(
						state, level, pos, player, hand, itemInHand, new ItemStack(ItemRegistry.NETHERITE_LAVA_BUCKET), var0x -> true, SoundEvents.BUCKET_FILL_LAVA
				)
		);
		addDefaultInteractions(LAVA);
//		POWDER_SNOW.put(
//			Items.BUCKET,
//			(state, level, pos, player, hand, itemInHand) -> fillBucket(
//				state,
//				level,
//				pos,
//				player,
//				hand,
//				itemInHand,
//				new ItemStack(Items.POWDER_SNOW_BUCKET),
//				s -> (Integer)s.getValue(LayeredCauldronBlock.LEVEL) == 3,
//				SoundEvents.BUCKET_FILL_POWDER_SNOW
//			)
//		);
//		addDefaultInteractions(POWDER_SNOW);
	}

	static void addDefaultInteractions(final NetheriteCauldronInteraction.Dispatcher interactionMap) {
		interactionMap.put(ItemRegistry.NETHERITE_LAVA_BUCKET, NetheriteCauldronInteractions::fillLavaInteraction);
//		interactionMap.put(Items.WATER_BUCKET, NetheriteCauldronInteractions::fillWaterInteraction);
//		interactionMap.put(Items.POWDER_SNOW_BUCKET, NetheriteCauldronInteractions::fillPowderSnowInteraction);
	}

	static InteractionResult fillBucket(
			final BlockState state,
			final Level level,
			final BlockPos pos,
			final Player player,
			final InteractionHand hand,
			final ItemStack itemInHand,
			final ItemStack newItem,
			final Predicate<BlockState> canFill,
			final SoundEvent soundEvent
	) {
		if (!canFill.test(state)) {
			return InteractionResult.TRY_WITH_EMPTY_HAND;
		} else {
			if (!level.isClientSide()) {
				Item itemUsed = itemInHand.getItem();
				player.setItemInHand(hand, ItemUtils.createFilledResult(itemInHand, player, newItem));
				player.awardStat(Stats.USE_CAULDRON);
				player.awardStat(Stats.ITEM_USED.get(itemUsed));
				level.setBlockAndUpdate(pos, BlockRegistry.NETHERITE_CAULDRON.defaultBlockState());
				level.playSound(null, pos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
				level.gameEvent(null, GameEvent.FLUID_PICKUP, pos);
			}

			return InteractionResult.SUCCESS;
		}
	}

	static InteractionResult emptyBucket(
			final Level level,
			final BlockPos pos,
			final Player player,
			final InteractionHand hand,
			final ItemStack itemInHand,
			final BlockState newState,
			final SoundEvent soundEvent
	) {
		if (!level.isClientSide()) {
			Item itemUsed = itemInHand.getItem();
			player.setItemInHand(hand, ItemUtils.createFilledResult(itemInHand, player, new ItemStack(ItemRegistry.NETHERITE_BUCKET)));
//			player.awardStat(Stats.FILL_CAULDRON);
			player.awardStat(Stats.ITEM_USED.get(itemUsed));
			level.setBlockAndUpdate(pos, newState);
			level.playSound(null, pos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
			level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
		}

		return InteractionResult.SUCCESS;
	}

//	private static InteractionResult fillWaterInteraction(
//		final BlockState state, final Level level, final BlockPos pos, final Player player, final InteractionHand hand, final ItemStack itemInHand
//	) {
//		return emptyBucket(
//			level, pos, player, hand, itemInHand, Blocks.WATER_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3), SoundEvents.BUCKET_EMPTY
//		);
//	}

	private static InteractionResult fillLavaInteraction(
			final BlockState state, final Level level, final BlockPos pos, final Player player, final InteractionHand hand, final ItemStack itemInHand
	) {
		return (InteractionResult) (isUnderWater(level, pos)
				? InteractionResult.CONSUME
				: emptyBucket(level, pos, player, hand, itemInHand, BlockRegistry.NETHERITE_LAVA_CAULDRON.defaultBlockState(), SoundEvents.BUCKET_EMPTY_LAVA));
	}

//	private static InteractionResult fillPowderSnowInteraction(
//		final BlockState state, final Level level, final BlockPos pos, final Player player, final InteractionHand hand, final ItemStack itemInHand
//	) {
//		return (InteractionResult)(isUnderWater(level, pos)
//			? InteractionResult.CONSUME
//			: emptyBucket(
//				level,
//				pos,
//				player,
//				hand,
//				itemInHand,
//				Blocks.POWDER_SNOW_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3),
//				SoundEvents.BUCKET_EMPTY_POWDER_SNOW
//			));
//	}
//
//	private static InteractionResult shulkerBoxInteraction(
//		final BlockState state, final Level level, final BlockPos pos, final Player player, final InteractionHand hand, final ItemStack itemInHand
//	) {
//		Block block = Block.byItem(itemInHand.getItem());
//		if (!(block instanceof ShulkerBoxBlock)) {
//			return InteractionResult.TRY_WITH_EMPTY_HAND;
//		} else {
//			if (!level.isClientSide()) {
//				ItemStack cleanedShulkerBox = itemInHand.transmuteCopy(Blocks.SHULKER_BOX, 1);
//				player.setItemInHand(hand, ItemUtils.createFilledResult(itemInHand, player, cleanedShulkerBox, false));
//				player.awardStat(Stats.CLEAN_SHULKER_BOX);
//				LayeredCauldronBlock.lowerFillLevel(state, level, pos);
//			}
//
//			return InteractionResult.SUCCESS;
//		}
//	}
//
//	private static InteractionResult bannerInteraction(
//		final BlockState state, final Level level, final BlockPos pos, final Player player, final InteractionHand hand, final ItemStack itemInHand
//	) {
//		BannerPatternLayers patterns = itemInHand.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY);
//		if (patterns.layers().isEmpty()) {
//			return InteractionResult.TRY_WITH_EMPTY_HAND;
//		} else {
//			if (!level.isClientSide()) {
//				ItemStack cleanedBanner = itemInHand.copyWithCount(1);
//				cleanedBanner.set(DataComponents.BANNER_PATTERNS, patterns.removeLast());
//				player.setItemInHand(hand, ItemUtils.createFilledResult(itemInHand, player, cleanedBanner, false));
//				player.awardStat(Stats.CLEAN_BANNER);
//				LayeredCauldronBlock.lowerFillLevel(state, level, pos);
//			}
//
//			return InteractionResult.SUCCESS;
//		}
//	}
//
//	private static InteractionResult dyedItemIteration(
//		final BlockState state, final Level level, final BlockPos pos, final Player player, final InteractionHand hand, final ItemStack itemInHand
//	) {
//		if (!itemInHand.has(DataComponents.DYED_COLOR)) {
//			return InteractionResult.TRY_WITH_EMPTY_HAND;
//		} else {
//			if (!level.isClientSide()) {
//				itemInHand.remove(DataComponents.DYED_COLOR);
//				player.awardStat(Stats.CLEAN_ARMOR);
//				LayeredCauldronBlock.lowerFillLevel(state, level, pos);
//			}
//
//			return InteractionResult.SUCCESS;
//		}
//	}

	private static boolean isUnderWater(final Level level, final BlockPos pos) {
		FluidState fluidState = level.getFluidState(pos.above());
		return fluidState.is(FluidTags.WATER);
	}
}
