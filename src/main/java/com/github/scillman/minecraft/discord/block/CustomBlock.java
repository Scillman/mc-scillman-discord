package com.github.scillman.minecraft.discord.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class CustomBlock extends Block
{
    private static final VoxelShape BLOCK_SHAPE = Block.createCuboidShape(
        0.0, 0.0,
        0.0, 16.0,
        16.0, 3.0
    );

    public CustomBlock(Settings settings)
    {
        super(settings);
    }

    @Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return BLOCK_SHAPE;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return BLOCK_SHAPE;
    }
}
