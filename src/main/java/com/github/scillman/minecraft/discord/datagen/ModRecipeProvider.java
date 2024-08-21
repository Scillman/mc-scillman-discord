package com.github.scillman.minecraft.discord.datagen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;

/**
 * Generates the json recipe files of the mod.
 */
public class ModRecipeProvider extends ExtendedRecipeProvider
{
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<WrapperLookup> registriesFuture)
    {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter)
    {
    }
}
