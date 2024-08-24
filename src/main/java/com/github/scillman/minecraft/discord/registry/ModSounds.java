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
    public static final Identifier DEMO_SOUND_SUBTITLE = subtitle("demo_sound_subtitle");
    public static final Identifier DEMO_SOUND_FILE_1 = soundFile("sound_file_ogg_1");
    public static final Identifier DEMO_SOUND_FILE_2 = soundFile("sound_file_ogg_2");

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
     * Create an identifier for the sound's subtitle.
     * @param id The subtitle id.
     * @return The identifier associated with the id.
     */
    private static Identifier subtitle(String id)
    {
        return Identifier.of(ModMain.MOD_ID, id);
    }

    /**
     * Create an identifier for the sound file.
     * @param fileName The name of the sound file.
     * @return The identifier associated with the sound file.
     */
    private static Identifier soundFile(String fileName)
    {
        return Identifier.of(ModMain.MOD_ID, fileName);
    }

    /**
     * Registers sound related content.
     * @remarks Must be called even when empty.
     */
    public static void register()
    {
    }
}
