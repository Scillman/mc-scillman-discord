package com.github.scillman.minecraft.discord.datagen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.ItemTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;

/**
 * Generates the mod's tag/items json files.
 */
public class ModItemTagProvider extends ItemTagProvider
{

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<WrapperLookup> completableFuture)
    {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup)
    {
    }
}
