package com.github.scillman.minecraft.discord.registry;

import com.github.scillman.minecraft.discord.ModMain;
import com.github.scillman.minecraft.discord.block.DemoBlock;

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
    public static final DemoBlock DEMO_BLOCK = register("demo_block", DemoBlock::new);

    /**
     * Register a custom block.
     * @param <T> The type of the block to register.
     * @param id The id to register the block with.
     * @param factory The function to call to create an instance of the block.
     * @return The instance as registered with Minecraft registry.
     */
    private static <T extends Block> T register(String id, BlockCreateFactory<T> factory)
    {
        return register(id, factory, true);
    }

    /**
     * Register a custom block.
     * @param <T> The type of the block to register.
     * @param id The id to register the block with.
     * @param factory factory The function to call to create an instance of the block.
     * @param registerItem Set to true to register a {@link net.minecraft.item.BlockItem BlockItem} for the block; otherwise, false.
     * @return The instance as registered with Minecraft registry.
     */
    private static <T extends Block> T register(String id, BlockCreateFactory<T> factory, boolean registerItem)
    {
        return register(id, factory, AbstractBlock.Settings.create(), registerItem);
    }

    /**
     * Register a custom block.
     * @param <T> The type of the block to register.
     * @param id The id to register the block with.
     * @param factory factory The function to call to create an instance of the block.
     * @param settings The block settings to use.
     * @return The instance as registered with Minecraft registry.
     */
    private static <T extends Block> T register(String id, BlockCreateFactory<T> factory, AbstractBlock.Settings settings)
    {
        return register(id, factory, settings, true);
    }

    /**
     * Register a custom block.
     * @param <T> The type of the block to register.
     * @param id The id to register the block with.
     * @param factory factory The function to call to create an instance of the block.
     * @param settings The block settings to use.
     * @param registerItem Set to true to register a {@link net.minecraft.item.BlockItem BlockItem} for the block; otherwise, false.
     * @return The instance as registered with Minecraft registry.
     */
    private static <T extends Block> T register(String id, BlockCreateFactory<T> factory, AbstractBlock.Settings settings, boolean registerItem)
    {
        Identifier guid = Identifier.of(ModMain.MOD_ID, id);

        T block = Registry.register(Registries.BLOCK, guid, factory.create(settings));

        if (registerItem)
        {
            Registry.register(Registries.ITEM, guid, new BlockItem(block, new Item.Settings()));
        }

        return block;
    }

    /**
     * Registers block related content.
     * @remarks Must be called even when empty.
     */
    public static void register()
    {
    }

    /**
     * Interface for block instance creation.
     */
    private interface BlockCreateFactory<T extends Block>
    {
        T create(AbstractBlock.Settings settings);
    }
}
