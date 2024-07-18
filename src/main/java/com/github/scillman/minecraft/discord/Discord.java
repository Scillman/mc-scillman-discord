package com.github.scillman.minecraft.discord;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.scillman.minecraft.discord.network.WorldSeedPayload;
import com.github.scillman.minecraft.discord.registry.ModItems;

public class Discord implements ModInitializer
{
    public static final String MOD_ID = "discord";
    public static final String MOD_NAME = "Discord";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    @Override
    public void onInitialize()
    {
        PayloadTypeRegistry.playS2C().register(WorldSeedPayload.ID, WorldSeedPayload.CODEC);

        ModItems.register();
    }
}
