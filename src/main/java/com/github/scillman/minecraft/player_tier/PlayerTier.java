package com.github.scillman.minecraft.player_tier;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerTier implements ModInitializer
{
    public static final String MOD_ID = "player_tier";
    public static final String MOD_NAME = "Player Tier";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final Block CUSTOM_BEEHIVE_BLOCK = registerBlock("custom_beehive", new BeehiveBlock(Block.Settings.create().strength(4.0f)));

    @Override
    public void onInitialize()
    {
    }

    private static Block registerBlock(String name, Block block)
    {
        return registerBlock(name, block, new Item.Settings());
    }

    private static Block registerBlock(String name, Block block, Item.Settings settings)
    {
        Block result = Registry.register(Registries.BLOCK, Identifier.of(MOD_ID, name), block);
        registerBlockItem(name, result, settings);
        return result;
    }

    private static Item registerBlockItem(String name, Block block, Item.Settings settings)
    {
        return Registry.register(Registries.ITEM, Identifier.of(MOD_ID, name), new BlockItem(block, settings));
    }
}
