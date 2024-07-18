package com.github.scillman.minecraft.discord;

import com.github.scillman.minecraft.discord.network.WorldSeedPayload;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class DiscordServer
{
    public static void sendWorldSeedToPlayer(ServerPlayerEntity player, ServerWorld world)
    {
        ServerPlayNetworking.send(player, new WorldSeedPayload(world.getSeed()));
    }
}
