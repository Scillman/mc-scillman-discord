package com.github.scillman.minecraft.discord.datagen;

import java.util.concurrent.CompletableFuture;

import com.github.scillman.minecraft.discord.registry.ModBlocks;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;

/**
 * Generates the loot tables for the mod.
 */
public class ModLootTableProvider extends FabricBlockLootTableProvider
{
    protected ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<WrapperLookup> registryLookup)
    {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate()
    {
        addDrop(ModBlocks.RUBY_BLOCK);
    }
}