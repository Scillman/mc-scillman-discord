package com.github.scillman.minecraft.discord.datagen;

import java.util.concurrent.CompletableFuture;

import com.github.scillman.minecraft.discord.registry.ModBlocks;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;

/**
 * Creates the EN_US language file for the mod.
 */
public class ModLanguageProvider extends FabricLanguageProvider
{
    public ModLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<WrapperLookup> registryLookup)
    {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(WrapperLookup registryLookup, TranslationBuilder translationBuilder)
    {
        addBlocks(translationBuilder);
    }

    private void addBlocks(TranslationBuilder builder)
    {
        builder.add(ModBlocks.RUBY_BLOCK, "Ruby Block");
    }
}
