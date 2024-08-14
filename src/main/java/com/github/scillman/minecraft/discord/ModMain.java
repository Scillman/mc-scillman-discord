package com.github.scillman.minecraft.discord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.RandomChanceWithEnchantedBonusLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;

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
        LootTableEvents.MODIFY.register(ModMain::modifyLootTable);
    }

    /**
     * Called when a loot table is loading to modify loot tables.
     *
     * @param key              the loot table key
     * @param tableBuilder    a builder of the loot table being loaded
     * @param source          the source of the loot table
     * @param registries      the registry wrapper lookup
     */
    private static void modifyLootTable(RegistryKey<LootTable> key, LootTable.Builder tableBuilder, LootTableSource source, RegistryWrapper.WrapperLookup registries)
    {
        if (EntityType.ZOMBIE.getLootTableId() == key && source.isBuiltin())
        {
            LootPool.Builder poolBuilder = LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .conditionally(RandomChanceWithEnchantedBonusLootCondition.builder(registries, 0.05f, 0.02f))
                .with(ItemEntry.builder(Items.ROTTEN_FLESH)) // ContagionItems.CONTAGIOUS_FLESH
                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 2.0f)).build());
            tableBuilder.pool(poolBuilder);
        }
    }
}
