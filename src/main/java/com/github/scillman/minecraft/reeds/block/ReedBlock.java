package com.github.scillman.minecraft.reeds.block;

import net.minecraft.block.BambooBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

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

    private boolean canSurvive(BlockState state, ServerWorld world, BlockPos pos)
    {
        return true;
    }
}
