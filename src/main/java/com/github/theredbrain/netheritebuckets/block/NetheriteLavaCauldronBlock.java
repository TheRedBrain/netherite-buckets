package com.github.theredbrain.netheritebuckets.block;

import com.github.theredbrain.netheritebuckets.block.cauldron.NetheriteCauldronBehaviour;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NetheriteLavaCauldronBlock extends AbstractNetheriteCauldronBlock {
    public static final MapCodec<NetheriteLavaCauldronBlock> CODEC = createCodec(NetheriteLavaCauldronBlock::new);

    @Override
    public MapCodec<NetheriteLavaCauldronBlock> getCodec() {
        return CODEC;
    }

    public NetheriteLavaCauldronBlock(Settings settings) {
        super(settings, NetheriteCauldronBehaviour.NETHERITE_LAVA_CAULDRON_BEHAVIOR);
    }

    @Override
    protected double getFluidHeight(BlockState state) {
        return 0.9375;
    }

    @Override
    public boolean isFull(BlockState state) {
        return true;
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (this.isEntityTouchingFluid(state, pos, entity)) {
            entity.setOnFireFromLava();
        }
    }

    @Override
    protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return 3;
    }
}
