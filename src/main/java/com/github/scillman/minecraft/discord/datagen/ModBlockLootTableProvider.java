package com.github.scillman.minecraft.discord.datagen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;

/**
 * Generates the loot table for the mod's custom blocks.
 */
public class ModBlockLootTableProvider extends FabricBlockLootTableProvider
{
    protected ModBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<WrapperLookup> registryLookup)
    {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate()
    {
    }
}
