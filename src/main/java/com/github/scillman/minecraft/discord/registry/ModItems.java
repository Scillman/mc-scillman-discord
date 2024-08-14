package com.github.scillman.minecraft.discord.registry;

import com.github.scillman.minecraft.discord.ModMain;
import com.github.scillman.minecraft.discord.item.CustomItem;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ModItems
{
    public static final Item CUSTOM_ITEM =
        Registry.register(Registries.ITEM, Identifier.of(ModMain.MOD_ID, "custom_item"), new CustomItem(new Item.Settings()));

    public static void register()
    {
    }
}
