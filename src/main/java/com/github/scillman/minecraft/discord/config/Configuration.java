package com.github.scillman.minecraft.discord.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.github.scillman.minecraft.discord.ModMain;

import net.minecraft.util.Identifier;

/**
 * A class for storing mod related settings.
 */
public class Configuration extends ConfigurationFile
{
    /**
     * Indicates whether the contents of the configuration have changed.
     */
    private boolean hasChanged;

    /**
     * Creates a configuration instance.
     */
    public Configuration()
    {
        hasChanged = false;
    }

    /**
     * Load the configuration from storage.
     */
    @Override
    public void load()
    {
        boolean hasChanged = this.hasChanged;
        super.load();
        this.hasChanged = hasChanged;
    }

    /**
     * Save the configuration to storage.
     */
    @Override
    public void save()
    {
        if (hasChanged)
        {
            super.save();
            hasChanged = false;
        }
    }

    /**
     * Register a configuration option.
     * @param key The key to use for the option.
     * @param value The current value.
     * @param defaultValue The default value.
     */
    public void register(Identifier key, @Nullable Object value, Object defaultValue)
    {
        String id = key.getPath();
        assert(!items.containsKey(id));

        items.put(id, new ConfigurationItem(id, value, defaultValue, (ConfigurationItem item) -> {
            hasChanged = true;
        }));
    }

    /**
     * Register a configuration option.
     * @param key The key to use for the option.
     * @param value The current value.
     * @param defaultValue The default value.
     * @param minValue The minimum value.
     * @param maxValue The maximum value.
     */
    public void register(Identifier key, @Nullable Object value, Object defaultValue, Object minValue, Object maxValue)
    {
        String id = key.getPath();
        assert(!items.containsKey(id));

        items.put(id, new ConfigurationItem(id, value, defaultValue, minValue, maxValue, (ConfigurationItem item) -> {
            hasChanged = true;
        }));
    }

    /**
     * Get the maximum allowed value of the key.
     * @param <T> The type of the returned value.
     * @param key The key of whom to get its maximum value.
     * @return The maximum allowed value of the key; otherwise, null.
     * @remarks Maximum is inclusive, e.g. [min, max]
     */
    @SuppressWarnings("unchecked")
    public <T extends Object> @Nullable T max(Identifier key)
    {
        String id = key.getPath();
        assert(items.containsKey(id));
        return ((T)(items.get(id).max()));
    }

    /**
     * Get the minimum allowed value of the key.
     * @param <T> The type of the returned value.
     * @param key The key of whom to get its minimum value.
     * @return The minimum allowed value of the key; otherwise, null.
     * @remarks Minimum is inclusive, e.g. [min, max]
     */
    @SuppressWarnings("unchecked")
    public <T extends Object> @Nullable T min(Identifier key)
    {
        String id = key.getPath();
        assert(items.containsKey(id));
        return ((T)(items.get(id).min()));
    }

    /**
     * Get the value of the key.
     * @param <T> The type of the returned value.
     * @param key The key whoms value to get.
     * @return The value of the key; otherwise, null.
     */
    @SuppressWarnings("unchecked")
    public <T extends Object> @Nullable T get(Identifier key)
    {
        String id = key.getPath();
        assert(items.containsKey(id));
        return ((T)(items.get(id).getValue()));
    }

    /**
     * Set the value of a key.
     * @param key The key whoms value to change.
     * @param value The new value.
     */
    public void set(Identifier key, Object value)
    {
        String id = key.getPath();
        assert(items.containsKey(id));
        items.get(id).setValue(value);
    }

    /**
     * Reset the value of the given key.
     * @param key The key whoms value to reset.
     */
    public void reset(Identifier key)
    {
        String id = key.getPath();
        assert(items.containsKey(id));

        ConfigurationItem item = items.get(id);
        item.setValue(item.getDefault());
    }

    /**
     * Get a list of registered keys.
     * @return A list of all the registered keys.
     */
    public List<Identifier> keys()
    {
        ArrayList<String> keys = new ArrayList<String>(items.keySet());
        Collections.sort(keys);

        ArrayList<Identifier> result = new ArrayList<>(keys.size());
        for (String key: keys)
        {
            result.add(Identifier.of(ModMain.MOD_ID, key));
        }

        return result;
    }
}
