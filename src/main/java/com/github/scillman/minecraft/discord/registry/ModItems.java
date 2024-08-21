package com.github.scillman.minecraft.discord.registry;

import com.github.scillman.minecraft.discord.ModMain;

import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;

/**
 * Contains all the custom items used inside the mod.
 */
public final class ModItems
{
    /**
     * Register a custom item.
     * @param <T> The type of the item to register.
     * @param id The id to register the item with.
     * @param item The item that has to be registered.
     * @return The instance as registered with Minecraft registry.
     */
    private static <T extends Item> T register(String id, T item)
    {
        return Registry.register(Registries.ITEM, Identifier.of(ModMain.MOD_ID, id), item);
    }

    /**
     * Registers item related content.
     * @remarks Must be called even when empty.
     */
    public static void register()
    {
    }
}
