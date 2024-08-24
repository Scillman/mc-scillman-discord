package com.github.scillman.minecraft.discord.datagen;

import java.util.concurrent.CompletableFuture;

import com.github.scillman.minecraft.discord.datagen.provider.ExtendedRecipeProvider;
import com.github.scillman.minecraft.discord.registry.ModBlocks;
import com.github.scillman.minecraft.discord.registry.ModItems;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.recipe.book.RecipeCategory;
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
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.DEMO_ITEM, RecipeCategory.MISC, ModBlocks.DEMO_BLOCK);
    }
}
