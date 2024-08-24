package com.github.scillman.minecraft.discord.datagen.provider;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.entity.damage.DamageEffects;
import net.minecraft.entity.damage.DamageScaling;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DeathMessageType;
import net.minecraft.data.DataOutput.OutputType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;

public abstract class DamageTypeProvider implements DataProvider
{
    private final FabricDataOutput output;
    private final CompletableFuture<RegistryWrapper.WrapperLookup> registryLookupFuture;
    private final DamageTypeTagProvider tagProvider;

    private final LinkedHashMap<Identifier, Builder> builders;

    public DamageTypeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture)
    {
        this.output = output;
        this.registryLookupFuture = registriesFuture;
        this.builders = new LinkedHashMap<Identifier, Builder>();

        this.tagProvider = new DamageTypeTagProvider(output, registriesFuture) {
            @Override
            protected void generate()
            {
                DamageTypeProvider.this.builders.forEach((id, builder) -> {
                    if (builder.bypassArmor) {
                        this.getOrCreateBuilder(DamageTypeTags.BYPASSES_ARMOR).add(id);
                    }
                });
            }
        };
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

        this.builders.forEach((damageTypeId, builder) -> {

            RegistryOps<JsonElement> registryOps = registryLookup.getOps(JsonOps.INSTANCE);
            JsonObject root = DamageType.CODEC.encodeStart(registryOps, builder.build()).getOrThrow(IllegalStateException::new).getAsJsonObject();
            list.add(DataProvider.writeToPath(writer, root, resolvePath(damageTypeId)));

        });

        list.add(this.tagProvider.run(writer, registryLookup));

        return CompletableFuture.allOf(list.toArray(CompletableFuture[]::new));
    }

    abstract protected void generate();

    @Override
    public String getName()
    {
        return "Damage Types";
    }

    /**
     * Get the path to the sounds.json file.
     * @return The path to the sounds.json file.
     * @remarks src/main/generated/assets/mod_id/sounds.json
     */
    private Path resolvePath(Identifier id)
    {
        return this.output.getResolver(OutputType.DATA_PACK, "damage_type").resolveJson(id);
    }

    public Builder getOrCreateBuilder(RegistryKey<DamageType> damageType)
    {
        return this.builders.computeIfAbsent(damageType.getValue(), id -> new Builder(id.getPath()));
    }

    public class Builder
    {
        private String messageId;
        private DamageScaling damageScaling;
        private float exhaustion;
        private DamageEffects effects;
        private DeathMessageType deathMessageType;

        /* internal */boolean bypassArmor;
        /* internal *///boolean bypassShield;
        /* internal *///boolean bypassInvulnerability;
        /* internal *///boolean bypassResistance;
        /* internal *///boolean bypassWolfArmor;

        /* internal */Builder(String messageId)
        {
            this.messageId = messageId;
            this.damageScaling = DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER;
            this.exhaustion = 0.0f;
            this.effects = DamageEffects.HURT;
            this.deathMessageType = DeathMessageType.DEFAULT;

            this.bypassArmor = false;
        }

        public Builder setMessageId(String messageId)
        {
            this.messageId = messageId;
            return this;
        }

        public Builder setDamageScaling(DamageScaling damageScaling)
        {
            this.damageScaling = damageScaling;
            return this;
        }

        public Builder setExhaustion(float exhaustion)
        {
            this.exhaustion = exhaustion;
            return this;
        }

        public Builder setDamageEffects(DamageEffects damageEffects)
        {
            this.effects = damageEffects;
            return this;
        }

        public Builder setDeathMessageType(DeathMessageType deathMessageType)
        {
            this.deathMessageType = deathMessageType;
            return this;
        }

        public Builder bypassArmor()
        {
            this.bypassArmor = true;
            return this;
        }

        /* internal */DamageType build()
        {
            return new DamageType(
                this.messageId,
                this.damageScaling,
                this.exhaustion,
                this.effects,
                this.deathMessageType
            );
        }
    }
}
