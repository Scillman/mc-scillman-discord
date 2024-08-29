package com.github.scillman.minecraft.discord.config;

import static com.github.scillman.minecraft.discord.ModMain.LOGGER;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.jetbrains.annotations.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

/**
 * A configuration file as stored in storage.
 */
public class ConfigurationFile
{
    /**
     * The GSON type token of member {@link #items}.
     */
    private static final java.lang.reflect.Type STORAGE_TYPE = new TypeToken<HashMap<String, Object>>() {}.getType();

    /**
     * The mod id used as the configuration file name.
     */
    private String modId;

    /**
     * The file that has been opened for reading/writing the configuration to/from.
     */
    @Nullable
    private File file;

    /**
     * A map of all the items inside the configuration file.
     */
    protected HashMap<String, ConfigurationItem> items;

    /**
     * Constructs a new configuration file instance.
     */
    protected ConfigurationFile()
    {
        this.modId = "";
        this.file = null;
        this.items = new HashMap<String, ConfigurationItem>();
    }

    /**
     * Initialize the configuration.
     * @param modId The guid of the mod.
     */
    public void init(String modId)
    {
        this.modId = modId;

        try
        {
            this.file = getFile(modId);
            if (this.file == null)
            {
                LOGGER.warn("Could not init ConfigurationFile");
                return;
            }
        }
        catch (SecurityException ex)
        {
            this.file = null;
            LOGGER.error("Cannot read/write ConfigurationFile from/to drive");
        }
    }

    /**
     * Load the configuration file from storage.
     */
    public void load()
    {
        HashMap<String, Object> data;

        if (this.file == null)
        {
            return;
        }

        try (JsonReader reader = new JsonReader(new FileReader(this.file)))
        {
            Gson gson = new Gson();
            data = gson.fromJson(reader, STORAGE_TYPE);
            data.forEach((key, value) -> {
                if (this.items.containsKey(key))
                {
                    this.items.get(key).setValue(value);
                }
            });
        }
        catch (JsonSyntaxException ex)
        {
            LOGGER.warn("Encountered a Json syntax error while reading configuration file.");
            this.file = null;
        }
        catch (IOException ex)
        {
            // It should not reach here since the conditions to cause the
            // exception have been cleared by the getFile function.
            assert(false);
            this.file = null;
        }
    }

    /**
     * Save the configuration file to storage.
     */
    public void save()
    {
        if (this.file == null)
        {
            // The user may have created the file manually or changed user
            // rights to read/write in between the load and save calls. As
            // such try to open it before saving if not yet opened.
            init(this.modId);
            if (this.file == null)
            {
                LOGGER.warn("Could not save configuration file");
                return;
            }
        }

        HashMap<String, Object> data = new HashMap<String, Object>();
        items.forEach((String key, ConfigurationItem item) -> {
            data.put(key, item.getValue());
        });

        try (FileWriter writer = new FileWriter(this.file))
        {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(data, writer);
        }
        catch (IOException ex)
        {
            // It should not reach here since the conditions to cause the
            // exception have been cleared by the getFile function.
            assert(false);
            LOGGER.warn(ex.getMessage());
        }
    }

    /**
     * Get the file to read/write to.
     * @param modId The guid of the mod.
     * @return A file when opened in read/write mode; otherwise, null.
     * @throws  SecurityException
     *          In the case of the default provider, and a security manager is
     *          installed, the {@link SecurityManager#checkRead(String) checkRead}
     *          is invoked to check read access to the file.
     */
    @Nullable
    private File getFile(String modId) throws SecurityException
    {
        Path directoryPath = Paths.get("config");

        // Create the config directory if it does not exist
        if (!Files.exists(directoryPath))
        {
            LOGGER.info("Creating config directory");

            Path executePath = directoryPath.getParent();

            // Minecraft files (should) reside here, it should be readable by default
            assert(Files.isReadable(executePath));

            // It may not be writable though...
            if (!Files.isWritable(executePath))
            {
                return null;
            }

            try
            {
                directoryPath = Files.createDirectories(directoryPath);
            }
            catch (IOException ex)
            {
                // Generic I/O exception cannot be resolved by us, just assume no directory
                LOGGER.warn(ex.getMessage());
                return null;
            }
        }

        // Ensure config is not a file but a directory
        if (!Files.isDirectory(directoryPath))
        {
            LOGGER.warn("Cannot make config directory");
            return null;
        }

        // Double check we have the user-rights to read/write
        if (!Files.isReadable(directoryPath) || !Files.isWritable(directoryPath))
        {
            LOGGER.warn("Cannot read and/or write to the config directory.");
            return null;
        }

        return new File(directoryPath.toString(), (modId + ".json"));
    }
}
