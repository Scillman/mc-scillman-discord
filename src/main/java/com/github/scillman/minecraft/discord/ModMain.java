package com.github.scillman.minecraft.discord;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;

import net.fabricmc.api.ModInitializer;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.RegistryEntryLookup.RegistryLookup;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntry.Reference;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

/**
 * Contains the common code of the mod.
 */
public class ModMain implements ModInitializer
{
    public static final String MOD_ID = "discord";
    public static final String MOD_NAME = "Discord";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    /**
     * The mod's main entry point.
     * @remarks Called by the mod loader.
     */
    @Override
    public void onInitialize()
    {
        World world;

        ItemStack eBook = new ItemStack(Items.ENCHANTED_BOOK);
        eBook = enchantBook(eBook, world, Identifier.ofVanilla("sharpness"), 1);
    }

    public static ItemStack enchantBook(ItemStack eBook, World world, Identifier enchantmentId, int level)
    {
        DynamicRegistryManager manager = world.getRegistryManager();
        Optional<Reference<Enchantment>> reference = manager.get(RegistryKeys.ENCHANTMENT).getEntry(enchantmentId);
        if (reference.isEmpty()) {
            return eBook;
        }

        Optional<RegistryKey<Enchantment>> key = reference.get().getKey();
        if (key.isEmpty()) {
            return eBook;
        }

        RegistryLookup registryLookup = manager.createRegistryLookup();
        Enchantment enchantment = registryLookup.getOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(key.get()).value();
        RegistryEntry<Enchantment> entry = RegistryEntry.of(enchantment);

        ItemEnchantmentsComponent comp = eBook.getEnchantments();
        ItemEnchantmentsComponent.Builder builder = new ItemEnchantmentsComponent.Builder(comp);
        builder.add(entry, level);
        EnchantmentHelper.set(eBook, comp);

        return eBook;
    }
}
