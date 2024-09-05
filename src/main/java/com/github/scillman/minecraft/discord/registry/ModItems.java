package com.github.scillman.minecraft.discord.registry;

import com.github.scillman.minecraft.discord.ModMain;
import com.github.scillman.minecraft.discord.item.DemoItem;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.registry.Registries;

/**
 * Contains all the custom items used inside the mod.
 */
public final class ModItems
{
    public static final DemoItem DEMO_ITEM = create("demo_item", DemoItem::new); // String, ItemCreateFactory<DemoItem> == String, DemoItem

    // (EntityType<? extends MobEntity> type, int primaryColor, int secondaryColor, Item.Settings settings)
    public static final Item DEMO_SPAWN_EGG_ITEM = register("demo_spawn_egg", new SpawnEggItem(
        EntityType.ALLAY,
        ColorHelper.Argb.getArgb(255, 0, 0),
        ColorHelper.Argb.getArgb(0, 0, 255),
        new Item.Settings()
    ));

    /**
     * Register a custom item.
     * @param <T> The type of the item to register.
     * @param id The id to register the item with.
     * @param factory The function to call to create an instance of the item.
     * @return The instance as registered with Minecraft registry.
     */
    private static <T extends Item> T create(String id, ItemCreateFactory<T> factory)
    {
        return create(id, factory, new Item.Settings());
    }

    /**
     * Register a custom item.
     * @param <T> The type of the item to register.
     * @param id The id to register the item with.
     * @param factory The function to call to create an instance of the item.
     * @param settings The item settings to use for the item.
     * @return The instance as registered with Minecraft registry.
     */
    private static <T extends Item> T create(String id, ItemCreateFactory<T> factory, Item.Settings settings)
    {
        return register(id, factory.create(settings));
    }

    /**
     * Register a custom item.
     * @param <T> The type of the item to register.
     * @param id The id to register the item with.
     * @param item The item to register.
     * @return The instance as registered with Minecraft registry.
     */
    private static <T extends Item> T register(String id, T item)
    {
        return Registry.register(Registries.ITEM, Identifier.of(ModMain.MOD_ID, id), item);
    }

    /**
     * Registers item related content.
     * @remarks Must be called even when empty.
     */
    public static void register()
    {
    }

    /**
     * Interface for item instance creation.
     */
    private interface ItemCreateFactory<T extends Item>
    {
        T create(Item.Settings settings);
    }
}
