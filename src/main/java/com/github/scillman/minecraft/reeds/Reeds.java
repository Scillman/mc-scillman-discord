package com.github.scillman.minecraft.reeds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.scillman.minecraft.reeds.block.ReedBlock;
import com.github.scillman.minecraft.reeds.feature.ReedsFeature;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.BambooFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.RandomFeature;
import net.minecraft.world.gen.feature.RandomPatchFeature;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;

public class Reeds implements ModInitializer
{
    public static final String MOD_ID = "reeds";
    public static final String MOD_NAME = "Reeds";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final Identifier REED_BLOCK_ID = Identifier.of(MOD_ID, "reed_block");
    public static final Block REED_BLOCK = new ReedBlock(Block.Settings.create());
    public static final Item REED_ITEM = new BlockItem(REED_BLOCK, new Item.Settings());

    public static final Identifier FEATURE_ID = Identifier.of(MOD_ID, "end_reeds");

    //public static final Feature<?> FEATURE_REEDS = new ReedsFeature(ProbabilityConfig.CODEC);
    public static final Feature<?> FEATURE_REEDS = new RandomPatchFeature(RandomPatchFeatureConfig.CODEC);

    @Override
    public void onInitialize()
    {
        Registry.register(Registries.BLOCK, REED_BLOCK_ID, REED_BLOCK);
        Registry.register(Registries.ITEM, REED_BLOCK_ID, REED_ITEM);

        Registry.register(Registries.FEATURE, FEATURE_ID, FEATURE_REEDS);

        BambooFeature feature;
    }
}
