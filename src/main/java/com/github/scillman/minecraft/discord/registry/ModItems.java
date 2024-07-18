package com.github.scillman.minecraft.discord.registry;

import static com.github.scillman.minecraft.discord.Discord.MOD_ID;

import com.github.scillman.minecraft.discord.item.SlimeChunkDetectorItem;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems
{
    public static final Item SLIME_CHUNK_DETECTOR = new SlimeChunkDetectorItem(new Item.Settings());

    private static void register(String id, Item item)
    {
        Registry.register(Registries.ITEM, Identifier.of(MOD_ID, id), item);
    }

    public static void register()
    {
        register("slime_chunk_detector", SLIME_CHUNK_DETECTOR);
    }
}
