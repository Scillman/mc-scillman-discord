package com.github.scillman.minecraft.discord.block;

import com.github.scillman.minecraft.discord.block.entity.SignPoleBlockEntity;
import com.mojang.serialization.MapCodec;

import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SignPoleBlock extends BlockWithEntity
{
    public static final MapCodec<SignPoleBlock> CODEC = createCodec(SignPoleBlock::new);

    public SignPoleBlock(Settings settings)
    {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state)
    {
        return new SignPoleBlockEntity(pos, state);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec()
    {
        return CODEC;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit)
    {
        if (world.isClient())
        {
            return ActionResult.PASS;
        }

        BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof SignPoleBlockEntity signPoleEntity)
        {
            signPoleEntity.setRotationValue(signPoleEntity.getRotationValue() + 1);
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }
}
