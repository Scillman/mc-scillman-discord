package com.github.scillman.minecraft.discord.datagen;

import com.github.scillman.minecraft.discord.registry.ModBlocks;
import com.github.scillman.minecraft.discord.registry.ModItems;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

/**
 * Generates the model and model states files for the mod's custom items and blocks.
 */
public class ModModelProvider extends FabricModelProvider
{
    public ModModelProvider(FabricDataOutput output)
    {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator stateGenerator)
    {
        stateGenerator.registerSimpleCubeAll(ModBlocks.DEMO_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator modelGenerator)
    {
        modelGenerator.register(ModItems.DEMO_ITEM, Models.GENERATED);
    }
}
