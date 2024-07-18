package com.github.scillman.minecraft.discord;

import com.github.scillman.minecraft.discord.network.WorldSeedPayload;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class DiscordClient implements ClientModInitializer
{
    public static long worldSeed = 0;

    @Override
    public void onInitializeClient()
    {
        ClientPlayNetworking.registerGlobalReceiver(WorldSeedPayload.ID, DiscordClient::onWorldSeedReceived);
    }

    private static void onWorldSeedReceived(WorldSeedPayload payload, ClientPlayNetworking.Context context)
    {
        context.client().execute(() -> {
            DiscordClient.setWorldSeed(payload.getSeed());
        });
    }

    public static void setWorldSeed(long seed)
    {
        Discord.LOGGER.info("Client-side received seed of {}", seed);
        DiscordClient.worldSeed = seed;
    }
}
