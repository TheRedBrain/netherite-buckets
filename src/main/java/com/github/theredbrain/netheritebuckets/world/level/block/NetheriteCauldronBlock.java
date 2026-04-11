package com.github.theredbrain.netheritebuckets.world.level.block;

import com.github.theredbrain.netheritebuckets.core.netheritecauldron.NetheriteCauldronInteractions;
import com.github.theredbrain.netheritebuckets.registry.BlockRegistry;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class NetheriteCauldronBlock extends AbstractNetheriteCauldronBlock {
	public static final MapCodec<NetheriteCauldronBlock> CODEC = simpleCodec(NetheriteCauldronBlock::new);

	@Override
	public MapCodec<NetheriteCauldronBlock> codec() {
		return CODEC;
	}

	public NetheriteCauldronBlock(final BlockBehaviour.Properties properties) {
		super(properties, NetheriteCauldronInteractions.EMPTY);
	}

	@Override
	public boolean isFull(final BlockState state) {
		return false;
	}

	@Override
	public boolean canReceiveStalactiteDrip(final Fluid fluid) {
		return fluid.isSame(Fluids.LAVA);
	}

	@Override
	public void receiveStalactiteDrip(final BlockState state, final Level level, final BlockPos pos, final Fluid fluid) {
		if (fluid == Fluids.LAVA) {
			BlockState newState = BlockRegistry.NETHERITE_LAVA_CAULDRON.defaultBlockState();
			level.setBlockAndUpdate(pos, newState);
			level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(newState));
			level.levelEvent(1046, pos, 0);
		}
	}

}
