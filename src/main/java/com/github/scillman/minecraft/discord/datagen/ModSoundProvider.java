package com.github.scillman.minecraft.discord.datagen;

import java.util.concurrent.CompletableFuture;

import com.github.scillman.minecraft.discord.ModMain;
import com.github.scillman.minecraft.discord.datagen.provider.SoundProvider;
import com.github.scillman.minecraft.discord.registry.ModSounds;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.util.Identifier;

public class ModSoundProvider extends SoundProvider
{
    public ModSoundProvider(FabricDataOutput output, CompletableFuture<WrapperLookup> registriesFuture)
    {
        super(output, registriesFuture, ModMain.MOD_ID);
    }

    @Override
    protected void generate()
    {
        getOrCreateBuilder(ModSounds.DEMO_SOUND)
            .setReplace(false)
            .useDefaultSubtitle()
            .addSound("demo_sound_file_1")
            .addSound("demo_sound_file_2")
            .addSound(Identifier.ofVanilla("block/amethyst/break1"));
    }
}
