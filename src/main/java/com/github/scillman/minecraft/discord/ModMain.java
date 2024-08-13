package com.github.scillman.minecraft.discord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.scillman.minecraft.discord.registry.ModBlocks;

import net.fabricmc.api.ModInitializer;

public class ModMain implements ModInitializer
{
    public static final String MOD_ID = "discord";
    public static final String MOD_NAME = "Discord";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    @Override
    public void onInitialize()
    {
        ModBlocks.register();
    }
}
