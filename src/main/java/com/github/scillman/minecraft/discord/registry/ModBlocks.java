package com.github.scillman.minecraft.discord.registry;

import com.github.scillman.minecraft.discord.ModMain;
import com.github.scillman.minecraft.discord.block.SignPoleBlock;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ModBlocks
{
    public static final Block SIGN_POLE_BLOCK = register("sign_pole", SignPoleBlock::new);

    private static <T extends Block> T register(String id, BlockCreateFactory<T> factory)
    {
        return Registry.register(
            Registries.BLOCK,
            Identifier.of(ModMain.MOD_ID, id),
            factory.create(AbstractBlock.Settings.create())
        );
    }

    public static void register()
    {
    }

    private interface BlockCreateFactory<T extends Block>
    {
        T create(AbstractBlock.Settings settings);
    }
}
