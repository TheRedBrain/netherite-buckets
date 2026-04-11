package com.github.theredbrain.netheritebuckets.world.level.block;

import com.github.theredbrain.netheritebuckets.core.netheritecauldron.NetheriteCauldronInteractions;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.InsideBlockEffectType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class NetheriteLavaCauldronBlock extends AbstractNetheriteCauldronBlock {
    public static final MapCodec<NetheriteLavaCauldronBlock> CODEC = simpleCodec(NetheriteLavaCauldronBlock::new);
    private static final VoxelShape SHAPE_INSIDE = Block.column(12.0, 4.0, 15.0);
    private static final VoxelShape FILLED_SHAPE = Shapes.or(AbstractNetheriteCauldronBlock.SHAPE, SHAPE_INSIDE);

    @Override
    public MapCodec<NetheriteLavaCauldronBlock> codec() {
        return CODEC;
    }

    public NetheriteLavaCauldronBlock(final BlockBehaviour.Properties properties) {
        super(properties, NetheriteCauldronInteractions.LAVA);
    }

    @Override
    protected double getContentHeight(final BlockState state) {
        return 0.9375;
    }

    @Override
    public boolean isFull(final BlockState state) {
        return true;
    }

    @Override
    protected VoxelShape getEntityInsideCollisionShape(final BlockState state, final BlockGetter level, final BlockPos pos, final Entity entity) {
        return FILLED_SHAPE;
    }

    @Override
    protected void entityInside(final BlockState state, final Level level, final BlockPos pos, final Entity entity, final InsideBlockEffectApplier effectApplier, final boolean isPrecise) {
        effectApplier.apply(InsideBlockEffectType.CLEAR_FREEZE);
        effectApplier.apply(InsideBlockEffectType.LAVA_IGNITE);
        effectApplier.runAfter(InsideBlockEffectType.LAVA_IGNITE, Entity::lavaHurt);
    }

    @Override
    protected int getAnalogOutputSignal(final BlockState state, final Level level, final BlockPos pos, final Direction direction) {
        return 3;
    }
}
