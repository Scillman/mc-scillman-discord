package com.github.scillman.minecraft.discord.registry;

import com.github.scillman.minecraft.discord.ModMain;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

/**
 *
 */
public final class ModDamageTypes
{

    /**
     * Registers a custom damage type.
     * @param id The id of the damage type.
     * @return The damage type's registry key.
     */
    private static RegistryKey<DamageType> register(String id)
    {
        return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(ModMain.MOD_ID, id));
    }

    /**
     *
     */
    public static void register()
    {
    }
}
