package com.github.scillman.minecraft.discord.datagen;

import java.util.concurrent.CompletableFuture;

import com.github.scillman.minecraft.discord.ModConstants;
import com.github.scillman.minecraft.discord.registry.ModBlocks;
import com.github.scillman.minecraft.discord.registry.ModDamageTypes;
import com.github.scillman.minecraft.discord.registry.ModItemGroups;
import com.github.scillman.minecraft.discord.registry.ModItems;
import com.github.scillman.minecraft.discord.registry.ModSounds;
import com.github.scillman.minecraft.discord.registry.ModStatusEffects;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

/**
 * Generates the English/US json language file.
 */
public class ModLanguageProvider extends FabricLanguageProvider
{
    protected ModLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<WrapperLookup> registryLookup)
    {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder)
    {
        addItemGroup(translationBuilder, ModItemGroups.DEMO_ITEM_GROUP, "Mod Items");
        addSoundEvent(translationBuilder, ModSounds.DEMO_SOUND, "Demo Sound Subtitle");
        addStatusEffect(translationBuilder, ModStatusEffects.DEMO_STATUS_EFFECT, "Demo Status Effect");

        addDamageType(translationBuilder, ModDamageTypes.DEMO_DAMAGE_TYPE_1, "Demo Damage Type");
        addDamageType(translationBuilder, ModDamageTypes.DEMO_DAMAGE_TYPE_2, "Demo Damage Type (2)");

        translationBuilder.add(ModBlocks.DEMO_BLOCK, "Demo Block");
        translationBuilder.add(ModItems.DEMO_ITEM, "Demo Item");
    }

    /**
     * Add an item group to the translation.
     * @param translationBuilder The translation builder.
     * @param itemGroup The item group to create a name for.
     * @param groupName The (display) name of the group.
     */
    private void addItemGroup(TranslationBuilder translationBuilder, RegistryKey<ItemGroup> itemGroup, String groupName)
    {
        Identifier id = itemGroup.getValue();
        translationBuilder.add(id.toTranslationKey(ModConstants.ITEM_GROUP_TRANSLATION_PREFIX), groupName);
    }

    /**
     * Add a sound subtitle to the translation.
     * @param translationBuilder The translation builder.
     * @param soundEvent The sound event to create the subtitle for.
     * @param subtitle The subtitle text.
     */
    private void addSoundEvent(TranslationBuilder translationBuilder, SoundEvent soundEvent, String subtitle)
    {
        Identifier id = soundEvent.getId();
        translationBuilder.add(id.toTranslationKey(ModConstants.SOUND_SUBTITLE_TRANSLATION_PREFIX), subtitle);
    }

    /**
     * Add a status effect to the translation.
     * @param translationBuilder The translation builder.
     * @param statusEffect The status effect to create a name for.
     * @param name The name of the status effect.
     */
    private void addStatusEffect(TranslationBuilder translationBuilder, RegistryEntry<StatusEffect> statusEffect, String name)
    {
        if (statusEffect.getKey().isPresent() == false)
        {
            throw new IllegalStateException("Could not get registry key from status effect entry.");
        }

        Identifier id = statusEffect.getKey().get().getValue();
        translationBuilder.add(id.toTranslationKey("effect"), name);
    }

    private void addDamageType(TranslationBuilder translationBuilder, RegistryKey<DamageType> damageType, String name)
    {
        Identifier id = damageType.getValue();
        translationBuilder.add(String.format("death.attack.%s", id.getPath()), name);
    }
}
