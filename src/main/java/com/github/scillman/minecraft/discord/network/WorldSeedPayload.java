package com.github.scillman.minecraft.discord.network;

import static com.github.scillman.minecraft.discord.Discord.MOD_ID;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record WorldSeedPayload(long seed) implements CustomPayload
{
    public static final CustomPayload.Id<WorldSeedPayload> ID = new WorldSeedPayload.Id<>(Identifier.of(MOD_ID, "world_seed_payload"));
    public static final PacketCodec<RegistryByteBuf, WorldSeedPayload> CODEC = PacketCodec.tuple(
        PacketCodecs.VAR_LONG, WorldSeedPayload::seed,
        WorldSeedPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId()
    {
        return ID;
    }

    public long getSeed()
    {
        return this.seed;
    }
}
