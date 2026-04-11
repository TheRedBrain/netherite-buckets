package com.github.theredbrain.netheritebuckets.block;

import com.github.theredbrain.netheritebuckets.block.cauldron.NetheriteCauldronBehaviour;
import com.github.theredbrain.netheritebuckets.registry.BlockRegistry;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LevelEvent;
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

    public NetheriteCauldronBlock(Properties settings) {
        super(settings, NetheriteCauldronBehaviour.EMPTY_NETHERITE_CAULDRON_BEHAVIOR);
    }

    @Override
    public boolean isFull(BlockState state) {
        return false;
    }

    public boolean canBeFilledByDripstone(Fluid fluid) {
        return fluid.isSame(Fluids.LAVA);
    }

    protected void fillFromDripstone(BlockState state, Level world, BlockPos pos, Fluid fluid) {
        BlockState blockState;
         if (fluid == Fluids.LAVA) {
            blockState = BlockRegistry.NETHERITE_LAVA_CAULDRON.defaultBlockState();
            world.setBlockAndUpdate(pos, blockState);
            world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(blockState));
            world.levelEvent(LevelEvent.SOUND_DRIP_LAVA_INTO_CAULDRON, pos, 0);
        }

    }
}
