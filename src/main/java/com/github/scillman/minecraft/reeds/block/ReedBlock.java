package com.github.scillman.minecraft.reeds.block;

import org.jetbrains.annotations.Nullable;

import com.github.scillman.minecraft.reeds.Reeds;

import net.minecraft.block.BambooBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class ReedBlock extends BambooBlock
{
    public static final BooleanProperty TOP = BooleanProperty.of("top");

    public ReedBlock(Settings settings)
    {
        super(settings);
        setDefaultState(getDefaultState().with(TOP, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        super.appendProperties(builder);
        builder.add(TOP);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random)
    {
        // Update the block state first
        boolean isTop = world.getBlockState(pos.up()).isAir();
        world.setBlockState(pos, state.with(TOP, isTop));

        // Then do the growth check
        if (isTop && random.nextInt(3) == 0)
        {
            int i = this.countBambooBelow(world, pos) + 1;
            if (i < 20)
            {
                this.updateLeaves(state, world, pos, random, i);
            }
        }

        // And finally, the removal check
        if (!canSurvive(state, world, pos))
        {
            world.breakBlock(pos, true);
        }
    }

    public boolean canSurvive(BlockState state, ServerWorld world, BlockPos pos)
    {
        return isValidBottomBlock(world, pos);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext context)
    {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);

        if (canPlaceAt(state, world, pos))
        {
            return this.getDefaultState().with(TOP, true);
        }

        return this.getDefaultState();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
    {
        return VoxelShapes.empty();
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos)
    {
        return isValidBottomBlock(world, pos);
    }

    @Override
	protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos)
    {
        if (!state.canPlaceAt(world, pos))
        {
            world.scheduleBlockTick(pos, this, 1);
        }

        if (direction == Direction.UP)
        {
            Block upBlock = world.getBlockState(pos.up()).getBlock();
            if (upBlock == Reeds.REED_BLOCK)
            {
                world.setBlockState(pos, state.with(TOP, false), Block.NOTIFY_LISTENERS);
            }
            else if (upBlock == Blocks.AIR)
            {
                world.setBlockState(pos, state.with(TOP, true), Block.NOTIFY_LISTENERS);
            }
            else
            {
                // You have not specified the desired behavior
                assert(false);
            }
        }

		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

    private boolean isValidBottomBlock(BlockView world, BlockPos pos)
    {
        Block block = world.getBlockState(pos.down()).getBlock();
        return
            block == Blocks.END_STONE ||
            block == Blocks.GRASS_BLOCK ||
            //block == ModBlocks.SADDENED_END_STONE ||
            //block == ModBlocks.MOSSY_END_STONE ||
            block == Reeds.REED_BLOCK
        ;
    }
}
