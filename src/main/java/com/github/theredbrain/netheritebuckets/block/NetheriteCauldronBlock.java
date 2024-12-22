package com.github.theredbrain.netheritebuckets.block;

import com.github.theredbrain.netheritebuckets.block.cauldron.NetheriteCauldronBehaviour;
import com.github.theredbrain.netheritebuckets.registry.BlockRegistry;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;

public class NetheriteCauldronBlock extends AbstractNetheriteCauldronBlock {
    public static final MapCodec<NetheriteCauldronBlock> CODEC = createCodec(NetheriteCauldronBlock::new);

    @Override
    public MapCodec<NetheriteCauldronBlock> getCodec() {
        return CODEC;
    }

    public NetheriteCauldronBlock(Settings settings) {
        super(settings, NetheriteCauldronBehaviour.EMPTY_NETHERITE_CAULDRON_BEHAVIOR);
    }

    @Override
    public boolean isFull(BlockState state) {
        return false;
    }

    public boolean canBeFilledByDripstone(Fluid fluid) {
        return fluid.matchesType(Fluids.LAVA);
    }

    protected void fillFromDripstone(BlockState state, World world, BlockPos pos, Fluid fluid) {
        BlockState blockState;
         if (fluid == Fluids.LAVA) {
            blockState = BlockRegistry.NETHERITE_LAVA_CAULDRON.getDefaultState();
            world.setBlockState(pos, blockState);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(blockState));
            world.syncWorldEvent(WorldEvents.POINTED_DRIPSTONE_DRIPS_LAVA_INTO_CAULDRON, pos, 0);
        }

    }
}
