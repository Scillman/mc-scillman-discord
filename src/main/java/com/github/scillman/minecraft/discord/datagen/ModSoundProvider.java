package com.github.scillman.minecraft.discord.datagen;

import java.util.concurrent.CompletableFuture;

import com.github.scillman.minecraft.discord.ModMain;
import com.github.scillman.minecraft.discord.datagen.provider.SoundProvider;
import com.github.scillman.minecraft.discord.registry.ModSounds;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;

public class ModSoundProvider extends SoundProvider
{
    public ModSoundProvider(FabricDataOutput output, CompletableFuture<WrapperLookup> registriesFuture)
    {
        super(output, registriesFuture, ModMain.MOD_ID);
    }

    @Override
    protected void generate()
    {
        addSoundEvent(ModSounds.DEMO_SOUND,
            new Builder()
                .setReplace(false)
                .setSubtitle(ModSounds.DEMO_SOUND_SUBTITLE)
                .addSound(ModSounds.DEMO_SOUND_FILE_1)
                .addSound(ModSounds.DEMO_SOUND_FILE_2)
        );
    }
}
