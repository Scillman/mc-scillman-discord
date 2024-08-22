package com.github.scillman.minecraft.discord.datagen;

import java.util.concurrent.CompletableFuture;

import com.github.scillman.minecraft.discord.registry.VanillaBlockTags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.BlockTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.BlockTags;

/**
 * Generates the mod's tag/blocks json files.
 */
public class ModBlockTagProvider extends BlockTagProvider
{
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<WrapperLookup> registriesFuture)
    {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup)
    {
        createNeedsNetheriteToolJson(wrapperLookup);
        createMineables(wrapperLookup);
    }

    /**
     * Creates the needs_netherite_tool.json file.
     * @param wrapperLookup
     */
    private void createNeedsNetheriteToolJson(RegistryWrapper.WrapperLookup wrapperLookup)
    {
        getOrCreateTagBuilder(BlockTags.INCORRECT_FOR_DIAMOND_TOOL)
            .setReplace(false)
            .addTag(VanillaBlockTags.NEEDS_NETHERITE_TOOL);

        getOrCreateTagBuilder(BlockTags.INCORRECT_FOR_GOLD_TOOL)
            .setReplace(false)
            .addTag(VanillaBlockTags.NEEDS_NETHERITE_TOOL);

        getOrCreateTagBuilder(BlockTags.INCORRECT_FOR_IRON_TOOL)
            .setReplace(false)
            .addTag(VanillaBlockTags.NEEDS_NETHERITE_TOOL);

        getOrCreateTagBuilder(BlockTags.INCORRECT_FOR_STONE_TOOL)
            .setReplace(false)
            .addTag(VanillaBlockTags.NEEDS_NETHERITE_TOOL);

        getOrCreateTagBuilder(BlockTags.INCORRECT_FOR_WOODEN_TOOL)
            .setReplace(false)
            .addTag(VanillaBlockTags.NEEDS_NETHERITE_TOOL);

        getOrCreateTagBuilder(VanillaBlockTags.NEEDS_NETHERITE_TOOL)
            .setReplace(false);
    }

    /**
     * Creates the mineable jsons files.
     * @param wrapperLookup
     */
    private void createMineables(RegistryWrapper.WrapperLookup wrapperLookup)
    {
        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
            .setReplace(false);

        getOrCreateTagBuilder(BlockTags.HOE_MINEABLE)
            .setReplace(false);

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
            .setReplace(false);

        getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE)
            .setReplace(false);
    }
}
