package com.github.scillman.minecraft.discord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.scillman.minecraft.discord.registry.ModBlockEntities;
import com.github.scillman.minecraft.discord.registry.ModBlocks;
import com.github.scillman.minecraft.discord.registry.ModItemGroups;
import com.github.scillman.minecraft.discord.registry.ModItems;
import com.github.scillman.minecraft.discord.registry.ModSounds;
import com.github.scillman.minecraft.discord.registry.VanillaBlockTags;

import net.fabricmc.api.ModInitializer;

/**
 * Contains the common code of the mod.
 */
public class ModMain implements ModInitializer
{
    public static final String MOD_ID = "discord";
    public static final String MOD_NAME = "Discord";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    /**
     * The mod's main entry point.
     * @remarks Called by the mod loader.
     */
    @Override
    public void onInitialize()
    {
        VanillaBlockTags.register();

        ModSounds.register();
        ModBlocks.register();
        ModBlockEntities.register();
        ModItems.register();
        ModItemGroups.register();
    }
}
