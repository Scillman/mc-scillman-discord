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
import net.minecraft.entity.damage.DamageType;
import net.minecraft.data.DataOutput.OutputType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;

/**
 * @remarks The class cannot check for the existence of the damage type.
 */
public abstract class DamageTypeTagProvider implements DataProvider
{
    private final FabricDataOutput output;
    private final CompletableFuture<RegistryWrapper.WrapperLookup> registryLookupFuture;

    private final LinkedHashMap<Identifier, Builder> builders;

    public DamageTypeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture)
    {
        this.output = output;
        this.registryLookupFuture = registriesFuture;
        this.builders = new LinkedHashMap<Identifier, Builder>();
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer)
    {
        return this.registryLookupFuture.thenCompose(registryLookup -> this.run(writer, registryLookup));
    }

    public CompletableFuture<?> run(DataWriter writer, RegistryWrapper.WrapperLookup registryLookup)
    {
        this.generate();

        final List<CompletableFuture<?>> list = new ArrayList<CompletableFuture<?>>();

        this.builders.forEach((id, builder) -> {
            RegistryOps<JsonElement> registryOps = registryLookup.getOps(JsonOps.INSTANCE);
            JsonObject root = DamageTypeTagEntry.CODEC.encodeStart(registryOps, builder.build()).getOrThrow(IllegalStateException::new).getAsJsonObject();
            list.add(DataProvider.writeToPath(writer, root, resolvePath(id)));
        });

        return CompletableFuture.allOf(list.toArray(CompletableFuture[]::new));
    }

    abstract protected void generate();

    @Override
    public String getName()
    {
        return "Damage Type Tags";
    }

    private Path resolvePath(Identifier id)
    {
        return this.output.resolvePath(OutputType.DATA_PACK)
            .resolve(id.getNamespace())
            .resolve("tags")
            .resolve("damage_type") // RegistryKeys.DAMAGE_TYPE.getValue().getPath()
            .resolve(id.getPath() + ".json");
    }

    public Builder getOrCreateBuilder(TagKey<DamageType> damageType)
    {
        return this.builders.computeIfAbsent(damageType.id(), id -> new Builder());
    }

    private record DamageTypeTagEntry(boolean replace, List<Identifier> values)
    {
        public static final Codec<DamageTypeTagEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.optionalFieldOf("replace", false).forGetter(DamageTypeTagEntry::replace),
            Identifier.CODEC.listOf().fieldOf("values").forGetter(DamageTypeTagEntry::values)
        ).apply(instance, DamageTypeTagEntry::new));
    }

    public class Builder
    {
        private boolean replace;
        private List<Identifier> entries;

        /* internal */Builder()
        {
            this.replace = false;
            this.entries = new ArrayList<Identifier>();
        }

        public Builder setReplace(boolean replace)
        {
            this.replace = replace;
            return this;
        }

        @SafeVarargs
        public final Builder add(RegistryKey<DamageType>... damageTypes)
        {
            for (RegistryKey<DamageType> damageType: damageTypes)
            {
                Identifier id = damageType.getValue();

                // NOTE: Due to the nature of varargs we need to create
                //       a new instance of Identifier to be safe.
                add(Identifier.of(id.getNamespace(), id.getPath()));
            }

            return this;
        }

        public Builder add(Identifier id)
        {
            if (!this.entries.contains(id))
            {
                this.entries.add(id);
            }

            return this;
        }

        /* internal */DamageTypeTagEntry build()
        {
            return new DamageTypeTagEntry(replace, entries);
        }
    }
}
