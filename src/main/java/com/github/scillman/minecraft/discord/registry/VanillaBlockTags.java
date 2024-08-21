package com.github.scillman.minecraft.discord.registry;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

/**
 * Contains vanilla Minecraft tags that should logically have been present
 * in vanilla, but are not actually present for use.
 */
public final class VanillaBlockTags
{
    public static final TagKey<Block> NEEDS_NETHERITE_TOOL = of("needs_netherite_tool");

    /**
     * Creates a new block tag key.
     * @param id The id fo the block tag key.
     * @return A block tag key for the given id.
     * @remarks tags/blocks
     */
    private static TagKey<Block> of(String id)
    {
        return TagKey.of(RegistryKeys.BLOCK, Identifier.ofVanilla(id));
    }

    /**
     * Registers (vanilla) tags related content.
     * @remarks Must be called even when empty.
     */
    public static void register()
    {
    }
}
