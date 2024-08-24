package com.github.scillman.minecraft.discord.registry;

import com.github.scillman.minecraft.discord.ModMain;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

/**
 * Contains all the custom sound events used inside the mod.
 */
public final class ModSounds
{
    public static final SoundEvent DEMO_SOUND = register("demo_sound");

    /**
     * Register a sound event.
     * @param id The id of the sound event.
     * @return The instance as registered with Minecraft registry.
     */
    private static SoundEvent register(String id)
    {
        Identifier guid = Identifier.of(ModMain.MOD_ID, id);
        return Registry.register(Registries.SOUND_EVENT, guid, SoundEvent.of(guid));
    }

    /**
     * Registers sound related content.
     * @remarks Must be called even when empty.
     */
    public static void register()
    {
    }
}
