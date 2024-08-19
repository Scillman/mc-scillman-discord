package com.github.scillman.minecraft.discord.registry;

import com.github.scillman.minecraft.discord.ModMain;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ModBlocks
{
    private static <T extends Block> T register(String id, T block)
    {
        return register(id, block, true);
    }

    private static <T extends Block> T register(String id, T block, boolean registerItem)
    {
        Identifier guid = Identifier.of(ModMain.MOD_ID, id);

        T result = Registry.register(Registries.BLOCK, guid, block);

        if (registerItem)
        {
            Registry.register(Registries.ITEM, guid, new BlockItem(block, new Item.Settings()));
        }

        return result;
    }

    public static void register()
    {
    }
}
