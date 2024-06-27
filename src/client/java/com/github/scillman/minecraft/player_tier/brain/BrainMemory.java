package com.github.scillman.minecraft.player_tier.brain;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.math.Vec3d;

public class BrainMemory
{
    public static final Codec<BrainMemory> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("name").forGetter(BrainMemory::getName),
        Vec3d.CODEC.fieldOf("memory").forGetter(BrainMemory::getMemory)
    ).apply(instance, BrainMemory::new));

    private String name;

    @Nullable
    private Vec3d memory;

    public BrainMemory(String name)
    {
        this.name = name;
        this.memory = null;
    }

    public BrainMemory(String name, Vec3d memory)
    {
        this.name = name;
        this.memory = memory;
    }

    public String getName()
    {
        return this.name;
    }

    public Vec3d getMemory()
    {
        return this.memory;
    }
}
