package com.github.scillman.minecraft.discord.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;

public class DemoEntity extends PathAwareEntity
{
    public DemoEntity(EntityType<? extends PathAwareEntity> entityType, World world)
    {
        super(entityType, world);
    }
}
