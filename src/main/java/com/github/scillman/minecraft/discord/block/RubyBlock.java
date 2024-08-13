package com.github.scillman.minecraft.discord.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RubyBlock extends Block
{
    public RubyBlock(Settings settings)
    {
        super(settings);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity)
    {
        super.onSteppedOn(world, pos, state, entity);

        if (world.isClient)
        {
            return;
        }

        if (entity instanceof PlayerEntity player)
        {
            // Apply swiftness effect (Speed) to the player
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 1)); // 200 ticks duration (10 seconds), amplifier 1 (Speed II)
        }
    }
}
