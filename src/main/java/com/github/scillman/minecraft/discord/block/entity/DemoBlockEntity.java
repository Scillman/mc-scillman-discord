package com.github.scillman.minecraft.discord.block.entity;

import com.github.scillman.minecraft.discord.registry.ModBlockEntities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.ComponentMap;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DemoBlockEntity extends BlockEntity
{
    public DemoBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        super(ModBlockEntities.DEMO_BLOCK_ENTITY, blockPos, blockState);
    }

    @Override
	public Packet<ClientPlayPacketListener> toUpdatePacket()
    {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup)
    {
		return new NbtCompound();
	}

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup)
    {
        super.readNbt(nbt, registryLookup);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup)
    {
        super.writeNbt(nbt, registryLookup);
    }

    @Override
    protected void readComponents(BlockEntity.ComponentsAccess components)
    {
        super.readComponents(components);
    }

    @Override
    protected void addComponents(ComponentMap.Builder componentMapBuilder)
    {
        super.addComponents(componentMapBuilder);
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, DemoBlockEntity entity)
    {
    }
}
