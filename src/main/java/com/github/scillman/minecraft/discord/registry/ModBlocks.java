package com.github.scillman.minecraft.discord.registry;

import com.github.scillman.minecraft.discord.ModMain;
import com.github.scillman.minecraft.discord.block.CustomBlock;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 * Contains all the custom blocks used inside the mod.
 */
public final class ModBlocks
{
    private static final CustomBlock CUSTOM_BLOCK = register("custom_block", new CustomBlock(AbstractBlock.Settings.create()));

    /**
     * Register a custom block.
     * @param <T> The type of the block to register.
     * @param id The id to register the block with.
     * @param block The block that has to be registered.
     * @return The instance as registered with Minecraft registry.
     */
    private static <T extends Block> T register(String id, T block)
    {
        return register(id, block, true);
    }

    /**
     * Register a custom block.
     * @param <T> The type of the block to register.
     * @param id The id to register the block with.
     * @param block The block that has to be registered.
     * @param registerItem Set to true to register a {@link net.minecraft.item.BlockItem BlockItem} for the block; otherwise, false.
     * @return The instance as registered with Minecraft registry.
     */
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

    /**
     * Registers block related content.
     * @remarks Must be called even when empty.
     */
    public static void register()
    {
    }
}
