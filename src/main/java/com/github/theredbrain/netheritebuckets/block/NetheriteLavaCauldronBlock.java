package com.github.theredbrain.netheritebuckets.block;

import com.github.theredbrain.netheritebuckets.block.cauldron.NetheriteCauldronBehaviour;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.InsideBlockEffectType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class NetheriteLavaCauldronBlock extends AbstractNetheriteCauldronBlock {
    public static final MapCodec<NetheriteLavaCauldronBlock> CODEC = simpleCodec(NetheriteLavaCauldronBlock::new);
    private static final VoxelShape LAVA_SHAPE = Block.column(12.0, 4.0, 15.0);
    private static final VoxelShape INSIDE_COLLISION_SHAPE = Shapes.or(AbstractNetheriteCauldronBlock.OUTLINE_SHAPE, LAVA_SHAPE);

    @Override
    public MapCodec<NetheriteLavaCauldronBlock> codec() {
        return CODEC;
    }

    public NetheriteLavaCauldronBlock(Properties settings) {
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
    protected VoxelShape getEntityInsideCollisionShape(BlockState state, BlockGetter world, BlockPos pos, Entity entity) {
        return INSIDE_COLLISION_SHAPE;
    }

    @Override
    protected void entityInside(BlockState state, Level world, BlockPos pos, Entity entity, InsideBlockEffectApplier handler, boolean bl) {
        handler.apply(InsideBlockEffectType.CLEAR_FREEZE);
        handler.apply(InsideBlockEffectType.LAVA_IGNITE);
        handler.runAfter(InsideBlockEffectType.LAVA_IGNITE, Entity::lavaHurt);
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos, Direction direction) {
        return 3;
    }
}
