package com.github.scillman.minecraft.discord.config;

import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;

/**
 * A single configurable item as used by the {@link com.github.scillman.minecraft.discord.config.Configuration Configuration} class.
 */
public class ConfigurationItem
{
    /**
     * The key used to identify the item.
     */
    private String key;

    /**
     * The current value of the item.
     */
    private Object value;

    /**
     * The minimum value of the item.
     */
    @Nullable
    private Object minValue;

    /**
     * The maximum value of the item.
     */
    @Nullable
    private Object maxValue;

    /**
     * The default value of the item.
     */
    private Object defaultValue;

    /**
     * The callback function that has be called when the value of the configuration item has changed.
     */
    private Consumer<ConfigurationItem> callback;

    /**
     * Create a new configuration item instance.
     * @param key The key used to identify the item.
     * @param value The value of the item.
     * @param defaultValue The default value of the item.
     * @param callback The function to call when the value of the item has changed.
     */
    public ConfigurationItem(String key, @Nullable Object value, Object defaultValue, Consumer<ConfigurationItem> callback)
    {
        this(key, value, defaultValue, null, null, callback);
    }

    /**
     * Create a new configuration item instance.
     * @param key The key used to identify the item.
     * @param value The value of the item.
     * @param defaultValue The default value of the item.
     * @param minValue The minimum value of the item.
     * @param maxValue The maximum value of the item.
     * @param callback The function to call when the value of the item has changed.
     */
    public ConfigurationItem(String key, @Nullable Object value, Object defaultValue, @Nullable Object minValue, @Nullable Object maxValue, Consumer<ConfigurationItem> callback)
    {
        this.key = key;
        this.value = (value != null ? value : defaultValue);
        this.defaultValue = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.callback = callback;
    }

    /**
     * Set a new value for the configuration item.
     * @param newValue The new value to set.
     */
    public void setValue(Object newValue)
    {
        Object value = clamp(newValue);
        if (!value.equals(this.value))
        {
            this.value = value;
            this.callback.accept(this);
        }
    }

    /**
     * Get the key used to identify the configuration item.
     * @return The key used to identify the configuration item.
     */
    public String key()
    {
        return this.key;
    }

    /**
     * Get the minimum allowed value for this configuration item.
     * @return The minimum allowed value for this configuration item.
     */
    @Nullable
    public Object min()
    {
        return this.minValue;
    }

    /**
     * Get the maximum allowed value for this configuration item.
     * @return The maximum allowed value for this configuration item.
     */
    @Nullable
    public Object max()
    {
        return this.maxValue;
    }

    /**
     * Get the default value of this configuration item.
     * @return The default value of this configuration item.
     */
    public Object getDefault()
    {
        return this.defaultValue;
    }

    /**
     * Get the value of this configuration item.
     * @return The value of this configuration item.
     */
    public Object getValue()
    {
        return this.value;
    }

    /**
     * Clamp the parameter to be in the range of [min, max] for this configuration item.
     * @param newValue The value to clamp.
     * @return The clamped value.
     * @remarks Values that cannot be clamped, like String values, will remain unchanged.
     */
    private Object clamp(Object newValue)
    {
        if (this.defaultValue instanceof Float)
        {
            return clampFloat(newValue);
        }

        if (this.defaultValue instanceof Integer)
        {
            return clampInteger(newValue);
        }

        return newValue;
    }

    /**
     * Clamps an integral number to be between the minimum and maximum for this configuration item.
     * @param obj The integral value that needs to be clamped.
     * @return A valid value in the range of [min, max] based on the input.
     * @remarks When the provided parameter is invalid the returned value will be the default value of the configuration item.
     */
    private Object clampInteger(Object obj)
    {
        Integer newValue;

        if (obj instanceof String strValue)
        {
            try
            {
                newValue = Integer.valueOf(strValue);
            }
            catch (NumberFormatException ex)
            {
                return this.defaultValue;
            }
        }
        else if (obj.getClass() == Long.class)
        {
            Long lv = ((Long)(obj));
            newValue = lv.intValue();
        }
        else if (obj.getClass() == Integer.class)
        {
            newValue = ((Integer)(obj));
        }
        else
        {
            return defaultValue;
        }

        if (this.minValue instanceof Integer min)
        {
            if (min > newValue)
            {
                return min;
            }
        }

        if (this.maxValue instanceof Integer max)
        {
            if (max < newValue)
            {
                return max;
            }
        }

        return newValue;
    }

    /**
     * Clamps a floating point number to be between the minimum and maximum for this configuration item.
     * @param obj The float that needs to be clamped.
     * @return A valid value in the range of [min, max] based on the input.
     * @remarks When the provided parameter is invalid the returned value will be the default value of the configuration item.
     */
    private Object clampFloat(Object obj)
    {
        Float newValue;

        if (obj instanceof String strValue)
        {
            try
            {
                newValue = Float.valueOf(strValue);
            }
            catch (NumberFormatException ex)
            {
                return this.defaultValue;
            }
        }
        else if (obj.getClass() == Number.class)
        {
            Number nv = ((Number)(obj));
            newValue = nv.floatValue();
        }
        else if (obj.getClass() == Double.class)
        {
            Double dv = ((Double)(obj));
            newValue = dv.floatValue();
        }
        else if (obj.getClass() == Float.class)
        {
            newValue = ((Float)(obj));
        }
        else
        {
            return this.defaultValue;
        }

        if (this.minValue instanceof Float min)
        {
            if (min > newValue)
            {
                return min;
            }
        }

        if (this.maxValue instanceof Float max)
        {
            if (max < newValue)
            {
                return max;
            }
        }

        return newValue;
    }
}
