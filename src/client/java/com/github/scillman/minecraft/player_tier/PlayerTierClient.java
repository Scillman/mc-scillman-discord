package com.github.scillman.minecraft.player_tier;

import com.github.scillman.minecraft.player_tier.client.TierTagger;

import net.fabricmc.api.ClientModInitializer;

public class PlayerTierClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        TierTagger.initialize();
    }
}
