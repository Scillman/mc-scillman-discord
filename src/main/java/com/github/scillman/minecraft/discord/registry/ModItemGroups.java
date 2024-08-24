package com.github.scillman.minecraft.discord.registry;

import com.github.scillman.minecraft.discord.ModConstants;
import com.github.scillman.minecraft.discord.ModMain;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 * Contains all the item groups used inside the mod.
 */
public final class ModItemGroups
{
    public static final Identifier DEMO_ITEM_GROUP = register("demo_item_group", ModItems.DEMO_ITEM, ModItemGroups::addToCustomItemGroup);

    /**
     * Register a new item group.
     * @param id The id of the item group.
     * @param icon The item to as the group icon.
     * @param listener The function to execute to add items to the group.
     * @return The identifier the group was registered with.
     */
    private static Identifier register(String id, ItemConvertible icon, ItemGroupEvents.ModifyEntries listener)
    {
        Identifier guid = Identifier.of(ModMain.MOD_ID, id);

        ItemGroup itemGroup = FabricItemGroup.builder()
            .icon(() -> new ItemStack(icon))
            .displayName(Text.translatable(guid.toTranslationKey(ModConstants.ITEM_GROUP_TRANSLATION_PREFIX)))
            .build();
        Registry.register(Registries.ITEM_GROUP, guid, itemGroup);

        RegistryKey<ItemGroup> key = RegistryKey.of(RegistryKeys.ITEM_GROUP, guid);
        ItemGroupEvents.modifyEntriesEvent(key).register(listener);

        return guid;
    }

    /**
     *
     */
    public static void register()
    {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(ModItemGroups::addToFunctionalGroup);
    }

    /**
     *
     * @param itemGroup
     */
    private static void addToFunctionalGroup(FabricItemGroupEntries itemGroup)
    {
        itemGroup.add(ModBlocks.DEMO_BLOCK);
    }

    /**
     *
     * @param itemGroup
     */
    private static void addToCustomItemGroup(FabricItemGroupEntries itemGroup)
    {
        itemGroup.add(ModBlocks.DEMO_BLOCK);
        itemGroup.add(ModItems.DEMO_ITEM);
    }
}
