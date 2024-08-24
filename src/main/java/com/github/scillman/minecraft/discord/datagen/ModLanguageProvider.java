package com.github.scillman.minecraft.discord.datagen;

import java.util.concurrent.CompletableFuture;

import com.github.scillman.minecraft.discord.ModConstants;
import com.github.scillman.minecraft.discord.registry.ModBlocks;
import com.github.scillman.minecraft.discord.registry.ModItemGroups;
import com.github.scillman.minecraft.discord.registry.ModItems;
import com.github.scillman.minecraft.discord.registry.ModSounds;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
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
        addSoundSubtitle(translationBuilder, ModSounds.DEMO_SOUND_SUBTITLE, "Demo Sound Subtitle");

        translationBuilder.add(ModBlocks.DEMO_BLOCK, "Demo Block");
        translationBuilder.add(ModItems.DEMO_ITEM, "Demo Item");
    }

    /**
     * Add an item group to the translation.
     * @param translationBuilder The translation builder.
     * @param groupId The identifier of the group.
     * @param groupName The (display) name of the group.
     */
    private void addItemGroup(TranslationBuilder translationBuilder, Identifier groupId, String groupName)
    {
        translationBuilder.add(groupId.toTranslationKey(ModConstants.ITEM_GROUP_TRANSLATION_PREFIX), groupName);
    }

    /**
     * Add a sound subtitle to the translation.
     * @param translationBuilder The translation builder.
     * @param subtitleId The identifier of the subtitle.
     * @param subtitle The subtitle text.
     */
    private void addSoundSubtitle(TranslationBuilder translationBuilder, Identifier subtitleId, String subtitle)
    {
        translationBuilder.add(subtitleId.toTranslationKey(ModConstants.SOUND_SUBTITLE_TRANSLATION_PREFIX), subtitle);
    }
}
