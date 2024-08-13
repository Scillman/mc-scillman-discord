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
 *
 */
public final class ModBlocks
{
    public static final RubyBlock RUBY_BLOCK = register("ruby_block", new RubyBlock(Block.Settings.create()));

    /**
     *
     * @param <T>
     * @param id
     * @param block
     * @return
     */
    private static <T extends Block> T register(String id, T block)
    {
        return register(id, block, true);
    }

    /**
     *
     * @param <T>
     * @param id
     * @param block
     * @param registerItem
     * @return
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
     *
     */
    public static void register()
    {
    }
}
