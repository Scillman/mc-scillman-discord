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
import java.util.LinkedHashMap;

public abstract class SoundProvider implements DataProvider
{
    private final Codec<Map<String, SoundEntry>> CODEC = Codec.unboundedMap(Codec.STRING, SoundEntry.CODEC);
    private final FabricDataOutput output;
    private final CompletableFuture<RegistryWrapper.WrapperLookup> registryLookupFuture;
    private final String namespace;

    private final LinkedHashMap<Identifier, Builder> builders;

    public SoundProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture, String namespace)
    {
        this.output = output;
        this.registryLookupFuture = registriesFuture;
        this.namespace = namespace;
        this.builders = new LinkedHashMap<Identifier, Builder>();
    }

    @Override
    public final CompletableFuture<?> run(DataWriter writer)
    {
        return this.registryLookupFuture.thenCompose(registryLookup -> this.run(writer, registryLookup));
    }

    protected CompletableFuture<?> run(DataWriter writer, RegistryWrapper.WrapperLookup registryLookup)
    {
        generate();

        final Hashtable<String, SoundEntry> entries = new Hashtable<String, SoundEntry>();

        this.builders.forEach((soundEventId, builder) -> {
            String key = soundEventId.getPath();
            SoundEntry entry = builder.build(soundEventId);

            if (entries.containsKey(key) == false) {
                entries.put(key, entry);
            }
            else {
                entries.merge(key, entry, (original, current) -> {
                    List<Identifier> sounds = original.sounds();
                    for (Identifier soundId: entry.sounds())
                    {
                        if (!sounds.contains(soundId))
                        {
                            sounds.add(soundId);
                        }
                    }

                    return new SoundEntry(entry.replace, entry.subtitle, sounds);
                });
            }
        });

        final List<CompletableFuture<?>> list = new ArrayList<CompletableFuture<?>>();

        RegistryOps<JsonElement> registryOps = registryLookup.getOps(JsonOps.INSTANCE);
        JsonObject root = CODEC.encodeStart(registryOps, entries).getOrThrow(IllegalStateException::new).getAsJsonObject();
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

    public Builder getOrCreateBuilder(SoundEvent soundEvent)
    {
        return this.builders.computeIfAbsent(soundEvent.getId(), id -> new Builder(this.namespace));
    }

    private record SoundEntry(boolean replace, String subtitle, List<Identifier> sounds)
    {
        public static final Codec<SoundEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.optionalFieldOf("replace", false).forGetter(SoundEntry::replace),
            Codec.STRING.optionalFieldOf("subtitle", "").forGetter(SoundEntry::subtitle),
            Identifier.CODEC.listOf().fieldOf("sounds").forGetter(SoundEntry::sounds)
        ).apply(instance, SoundEntry::new));
    }

    public class Builder
    {
        private String namespace;
        private boolean defaultSubtitle;
        private boolean replace;
        private String subtitle;
        private List<Identifier> sounds;

        /* internal */Builder(String namespace)
        {
            this.namespace = namespace;
            this.defaultSubtitle = false;
            this.replace = false;
            this.subtitle = "";
            this.sounds = new ArrayList<Identifier>();
        }

        public Builder setReplace(boolean replace)
        {
            this.replace = replace;
            return this;
        }

        public Builder useDefaultSubtitle()
        {
            this.defaultSubtitle = true;
            return this;
        }

        public Builder setSubtitle(String subtitle)
        {
            return setSubtitle(Identifier.of(this.namespace, subtitle));
        }

        public Builder setSubtitle(Identifier subtitle)
        {
            this.subtitle = subtitle.toTranslationKey("sound");
            return this;
        }

        public final Builder addSound(String... ids)
        {
            for (String id: ids)
            {
                addSound(id);
            }

            return this;
        }

        public Builder addSound(String id)
        {
            return addSound(Identifier.of(this.namespace, id));
        }

        @SafeVarargs
        public final Builder addSound(Identifier... ids)
        {
            for (Identifier id: ids)
            {
                // NOTE: Due to the nature of varargs we need to create
                //       a new instance of Identifier to be safe.
                addSound(Identifier.of(id.getNamespace(), id.getPath()));
            }

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

        /* internal */SoundEntry build(Identifier soundEventId)
        {
            if (this.sounds.size() < 1)
            {
                throw new IllegalStateException("Must have at least one sound file specified.");
            }

            if (this.subtitle.isEmpty() && this.defaultSubtitle)
            {
                setSubtitle(soundEventId);
            }

            return new SoundEntry(this.replace, this.subtitle, this.sounds);
        }
    }
}
