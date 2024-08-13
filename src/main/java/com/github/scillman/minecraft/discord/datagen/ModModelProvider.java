package com.github.scillman.minecraft.discord.datagen;

import com.github.scillman.minecraft.discord.registry.ModBlocks;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;

/**
 * Generates the models for the mod.
 */
public class ModModelProvider extends FabricModelProvider
{
    public ModModelProvider(FabricDataOutput output)
    {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator)
    {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RUBY_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator)
    {
    }
}
