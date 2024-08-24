package com.github.scillman.minecraft.discord.datagen;

import java.util.concurrent.CompletableFuture;

import com.github.scillman.minecraft.discord.datagen.provider.DamageTypeProvider;
import com.github.scillman.minecraft.discord.registry.ModDamageTypes;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;

public class ModDamageTypeProvider extends DamageTypeProvider
{
    public ModDamageTypeProvider(FabricDataOutput output, CompletableFuture<WrapperLookup> registriesFuture)
    {
        super(output, registriesFuture);
    }

    @Override
    protected void generate()
    {
        getOrCreateBuilder(ModDamageTypes.DEMO_DAMAGE_TYPE_1);

        getOrCreateBuilder(ModDamageTypes.DEMO_DAMAGE_TYPE_2)
            .bypassArmor();
    }
}
