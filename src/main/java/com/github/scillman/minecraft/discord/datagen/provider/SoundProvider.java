package com.github.scillman.minecraft.discord.datagen.provider;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.data.DataOutput.OutputType;
import net.minecraft.registry.RegistryOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.Hashtable;
import java.util.Map;

public abstract class SoundProvider implements DataProvider
{
    final Codec<Map<String, SoundEntry>> CODEC = Codec.unboundedMap(Codec.STRING, SoundEntry.CODEC);

    private final FabricDataOutput output;
    private final CompletableFuture<RegistryWrapper.WrapperLookup> registryLookupFuture;
    private final String namespace;
    private final Hashtable<String, SoundEntry> entries;

    public SoundProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture, String modId)
    {
        this.output = output;
        this.registryLookupFuture = registriesFuture;
        this.namespace = modId;
        this.entries = new Hashtable<>();
    }

    @Override
    public final CompletableFuture<?> run(DataWriter writer)
    {
        return this.registryLookupFuture.thenCompose(registryLookup -> this.run(writer, registryLookup));
    }

    protected CompletableFuture<?> run(DataWriter writer, RegistryWrapper.WrapperLookup registryLookup)
    {
        this.generate();

        final List<CompletableFuture<?>> list = new ArrayList<CompletableFuture<?>>();

        RegistryOps<JsonElement> registryOps = registryLookup.getOps(JsonOps.INSTANCE);
        JsonObject root = CODEC.encodeStart(registryOps, this.entries).getOrThrow(IllegalStateException::new).getAsJsonObject();
        list.add(DataProvider.writeToPath(writer, root, resolvePath()));

        return CompletableFuture.allOf(list.toArray(CompletableFuture[]::new));
    }

    abstract protected void generate();

    @Override
    public String getName()
    {
        return "Sounds";
    }

    /**
     * Get the path to the sounds.json file.
     * @return The path to the sounds.json file.
     * @remarks src/main/generated/assets/mod_id/sounds.json
     */
    private Path resolvePath()
    {
        return this.output.resolvePath(OutputType.RESOURCE_PACK).resolve(this.namespace).resolve("sounds.json");
    }

    protected void addSoundEvent(SoundEvent soundEvent, Builder builder)
    {
        String key = soundEvent.getId().getPath();
        SoundEntry entry = builder.build();

        if (this.entries.containsKey(key))
        {
            this.entries.replace(key, mergeEntries(key, entry));
        }
        else
        {
            this.entries.put(key, entry);
        }
    }

    private SoundEntry mergeEntries(String key, SoundEntry entry)
    {
        SoundEntry original = this.entries.get(key);

        List<Identifier> sounds = original.sounds();
        for (Identifier soundId: entry.sounds())
        {
            if (!sounds.contains(soundId))
            {
                sounds.add(soundId);
            }
        }

        return new SoundEntry(entry.replace, entry.subtitle, sounds);
    }

    public record SoundEntry(boolean replace, String subtitle, List<Identifier> sounds)
    {
        public static final Codec<SoundEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.optionalFieldOf("replace", false).forGetter(SoundEntry::replace),
            Codec.STRING.optionalFieldOf("subtitle", "").forGetter(SoundEntry::subtitle),
            Identifier.CODEC.listOf().fieldOf("sounds").forGetter(SoundEntry::sounds)
        ).apply(instance, SoundEntry::new));
    }

    public class Builder
    {
        private boolean replace;
        private String subtitle;
        private List<Identifier> sounds;

        public Builder()
        {
            this.replace = false;
            this.subtitle = "";
            this.sounds = new ArrayList<Identifier>();
        }

        public Builder setReplace(boolean replace)
        {
            this.replace = replace;
            return this;
        }

        public Builder setSubtitle(Identifier subtitle)
        {
            this.subtitle = subtitle.toTranslationKey("sound");
            return this;
        }

        public Builder addSound(Identifier id)
        {
            if (!this.sounds.contains(id))
            {
                this.sounds.add(id);
            }

            return this;
        }

        public SoundEntry build()
        {
            if (this.sounds.size() <= 1)
            {
                throw new IllegalStateException("Must have at least one sound file specified.");
            }

            return new SoundEntry(this.replace, this.subtitle, this.sounds);
        }
    }
}
