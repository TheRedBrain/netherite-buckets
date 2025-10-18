package com.github.theredbrain.netheritebuckets.block;

import com.github.theredbrain.netheritebuckets.block.cauldron.NetheriteCauldronBehaviour;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CollisionEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCollisionHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class NetheriteLavaCauldronBlock extends AbstractNetheriteCauldronBlock {
    public static final MapCodec<NetheriteLavaCauldronBlock> CODEC = createCodec(NetheriteLavaCauldronBlock::new);
    private static final VoxelShape LAVA_SHAPE = Block.createColumnShape(12.0, 4.0, 15.0);
    private static final VoxelShape INSIDE_COLLISION_SHAPE = VoxelShapes.union(AbstractNetheriteCauldronBlock.OUTLINE_SHAPE, LAVA_SHAPE);

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
    protected VoxelShape getInsideCollisionShape(BlockState state, BlockView world, BlockPos pos, Entity entity) {
        return INSIDE_COLLISION_SHAPE;
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, EntityCollisionHandler handler, boolean bl) {
        handler.addEvent(CollisionEvent.CLEAR_FREEZE);
        handler.addEvent(CollisionEvent.LAVA_IGNITE);
        handler.addPostCallback(CollisionEvent.LAVA_IGNITE, Entity::setOnFireFromLava);
    }

    @Override
    protected int getComparatorOutput(BlockState state, World world, BlockPos pos, Direction direction) {
        return 3;
    }
}
