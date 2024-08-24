package com.github.scillman.minecraft.discord.block;

import org.jetbrains.annotations.Nullable;

import com.github.scillman.minecraft.discord.block.entity.DemoBlockEntity;
import com.github.scillman.minecraft.discord.registry.ModBlockEntities;
import com.mojang.serialization.MapCodec;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DemoBlock extends BlockWithEntity
{
    public static final MapCodec<DemoBlock> CODEC = Block.createCodec(DemoBlock::new);

    public DemoBlock(AbstractBlock.Settings settings)
    {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        return new DemoBlockEntity(blockPos, blockState);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec()
    {
        return CODEC;
    }

    @Override
	protected BlockRenderType getRenderType(BlockState blockState)
    {
		return BlockRenderType.MODEL;
	}

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type)
    {
        if (world.isClient())
        {
            return null;
        }

        return validateTicker(type, ModBlockEntities.DEMO_BLOCK_ENTITY, DemoBlockEntity::tick);
    }
}
