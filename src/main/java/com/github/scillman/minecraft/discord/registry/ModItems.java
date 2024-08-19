package com.github.scillman.minecraft.discord.registry;

import com.github.scillman.minecraft.discord.ModMain;

import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;

public final class ModItems
{
    private static <T extends Item> T register(String id, T item)
    {
        return Registry.register(Registries.ITEM, Identifier.of(ModMain.MOD_ID, id), item);
    }

    public static void register()
    {
    }
}
