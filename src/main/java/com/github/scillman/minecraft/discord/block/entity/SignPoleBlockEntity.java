package com.github.scillman.minecraft.discord.block.entity;

import org.jetbrains.annotations.Nullable;

import com.github.scillman.minecraft.discord.ModMain;
import com.github.scillman.minecraft.discord.registry.ModBlockEntities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;

public class SignPoleBlockEntity extends BlockEntity
{
    private static final String ROTATION_KEY = "RotationValue";
    private int rotation_value;

    public SignPoleBlockEntity (BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.SIGN_POLE_BLOCK_ENTITY, pos, state);
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket()
    {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup)
    {
        NbtCompound nbt = super.toInitialChunkDataNbt(registryLookup);
        nbt.putInt(ROTATION_KEY, this.rotation_value);
        return nbt;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup)
    {
        ModMain.LOGGER.info("SignPoleBlockEntity::writeNbt {}", this.rotation_value);
        super.writeNbt(nbt, registryLookup);
        nbt.putInt(ROTATION_KEY, this.rotation_value);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup)
    {
        super.readNbt(nbt, registryLookup);
        this.rotation_value = nbt.getInt(ROTATION_KEY);
        ModMain.LOGGER.info("SignPoleBlockEntity::readNbt {}", this.rotation_value);
    }

    public int getRotationValue()
    {
        ModMain.LOGGER.info("SignPoleBlockEntity::getRotationValue {}", this.rotation_value);
        return this.rotation_value;
    }

    public void setRotationValue(int value)
    {
        if (this.rotation_value != value)
        {
            ModMain.LOGGER.info("SignPoleBlockEntity::setRotationValue {}", value);

            this.rotation_value = value;
            markDirty();
        }
    }

    // public void update()
    // {
    //     markDirty();
    //     if (world != null)
    //     {
    //         BlockState state = world.getBlockState(pos);
    //         world.setBlockState(pos, state, Block.NOTIFY_LISTENERS);
    //         world.updateNeighbors(pos, getCachedState().getBlock());
    //         world.emitGameEvent(GameEvent.BLOCK_CHANGE, this.getPos(), GameEvent.Emitter.of(null, this.getCachedState()));
    //     }

    //     System.out.println(this.rotation_value);
    //     System.out.println("Update!"); //Good here, after finished reset to 0
    // }
}
