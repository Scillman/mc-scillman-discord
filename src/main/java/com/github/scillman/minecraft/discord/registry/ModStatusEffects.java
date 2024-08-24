package com.github.scillman.minecraft.discord.registry;

import com.github.scillman.minecraft.discord.ModMain;
import com.github.scillman.minecraft.discord.entity.effect.DemoStatusEffect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

/**
 * Contains all the custom status effects used inside the mod.
 */
public final class ModStatusEffects
{
    public static final RegistryEntry<StatusEffect> DEMO_STATUS_EFFECT = register("demo_status_effect", new DemoStatusEffect());

    /**
     * Register a status effect.
     * @param id The id to register the status effect with.
     * @param effect The status effect to register.
     * @return The entry as registered with Minecraft registry.
     */
    private static RegistryEntry<StatusEffect> register(String id, StatusEffect effect)
    {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(ModMain.MOD_ID, id), effect);
    }

    /**
     * Registers status effect related content.
     * @remarks Must be called even when empty.
     */
    public static void register()
    {
    }
}
