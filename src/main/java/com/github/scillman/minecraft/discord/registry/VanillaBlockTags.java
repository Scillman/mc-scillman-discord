package com.github.scillman.minecraft.discord.registry;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class VanillaBlockTags
{
    public static final TagKey<Block> NEEDS_NETHERITE_TOOL = of("needs_netherite_tool");

    private static TagKey<Block> of(String id)
    {
        return TagKey.of(RegistryKeys.BLOCK, Identifier.ofVanilla(id));
    }
}
