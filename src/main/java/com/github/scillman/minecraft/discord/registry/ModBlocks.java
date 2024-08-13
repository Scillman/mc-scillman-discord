package com.github.scillman.minecraft.discord.registry;

import com.github.scillman.minecraft.discord.ModMain;
import com.github.scillman.minecraft.discord.block.RubyBlock;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 * Contains all the blocks inside the mod.
 */
public final class ModBlocks
{
    public static final RubyBlock RUBY_BLOCK = register("ruby_block", new RubyBlock(Block.Settings.create()));

    /**
     * Register a block.
     * @param <T> The type of the block to register.
     * @param id The id to register the block with.
     * @param block The block to register.
     * @return The block as registered with the Minecraft registry.
     */
    private static <T extends Block> T register(String id, T block)
    {
        return register(id, block, true);
    }

    /**
     * Register a block.
     * @param <T> The type of the block to register.
     * @param id The id to register the block with.
     * @param block The block to register.
     * @param registerItem True to also register the {@link net.minecraft.item.BlockItem BlockItem} of the block; otherwise, false.
     * @return The block as registered with the Minecraft registry.
     */
    private static <T extends Block> T register(String id, T block, boolean registerItem)
    {
        Identifier guid = Identifier.of(ModMain.MOD_ID, id);

        T result = Registry.register(Registries.BLOCK, guid, block);

        if (registerItem)
        {
            Registry.register(Registries.ITEM, guid, new BlockItem(result, new Item.Settings()));
        }

        return result;
    }

    /**
     * Handles additional block registration code.
     * @remarks Must be called even when empty!
     */
    public static void register()
    {
    }
}
