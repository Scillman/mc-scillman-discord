package com.github.scillman.minecraft.discord.datagen;

import java.util.Optional;

import com.github.scillman.minecraft.discord.registry.ModBlocks;
import com.github.scillman.minecraft.discord.registry.ModItems;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.util.Identifier;

/**
 * Generates the model and model states files for the mod's custom items and blocks.
 */
public class ModModelProvider extends FabricModelProvider
{
    private static final Model SPAWN_EGG_MODEL = new Model(Optional.of(Identifier.ofVanilla("item/template_spawn_egg")), Optional.empty());

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
        modelGenerator.register(ModItems.DEMO_SPAWN_EGG_ITEM, SPAWN_EGG_MODEL);
    }
}
