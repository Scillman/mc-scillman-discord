package com.github.scillman.minecraft.discord.datagen.provider;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
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
import net.minecraft.registry.tag.TagKey;
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
                    builder.tags.forEach((tag) -> {
                        this.getOrCreateBuilder(tag).add(id);
                    });
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

        /* internal */HashSet<TagKey<DamageType>> tags;

        /* internal */Builder(String messageId)
        {
            this.messageId = messageId;
            this.damageScaling = DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER;
            this.exhaustion = 0.0f;
            this.effects = DamageEffects.HURT;
            this.deathMessageType = DeathMessageType.DEFAULT;

            this.tags = new HashSet<TagKey<DamageType>>();
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

        public Builder damagesHelmet()
        {
            this.tags.add(DamageTypeTags.DAMAGES_HELMET);
            return this;
        }

        public Builder bypassesArmor()
        {
            this.tags.add(DamageTypeTags.BYPASSES_ARMOR);
            return this;
        }

        public Builder bypassesShield()
        {
            this.tags.add(DamageTypeTags.BYPASSES_SHIELD);
            return this;
        }

        public Builder bypassesInvulnerability()
        {
            this.tags.add(DamageTypeTags.BYPASSES_INVULNERABILITY);
            return this;
        }

        public Builder bypassesCooldown()
        {
            this.tags.add(DamageTypeTags.BYPASSES_COOLDOWN);
            return this;
        }

        public Builder bypassesEffects()
        {
            this.tags.add(DamageTypeTags.BYPASSES_EFFECTS);
            return this;
        }

        public Builder bypassesResistance()
        {
            this.tags.add(DamageTypeTags.BYPASSES_RESISTANCE);
            return this;
        }

        public Builder bypassesEnchantments()
        {
            this.tags.add(DamageTypeTags.BYPASSES_ENCHANTMENTS);
            return this;
        }

        public Builder isFire()
        {
            this.tags.add(DamageTypeTags.IS_FIRE);
            return this;
        }

        public Builder isProjectile()
        {
            this.tags.add(DamageTypeTags.IS_PROJECTILE);
            return this;
        }

        public Builder witchResistantTo()
        {
            this.tags.add(DamageTypeTags.WITCH_RESISTANT_TO);
            return this;
        }

        public Builder isExplosion()
        {
            this.tags.add(DamageTypeTags.IS_EXPLOSION);
            return this;
        }

        public Builder isFall()
        {
            this.tags.add(DamageTypeTags.IS_FALL);
            return this;
        }

        public Builder isDrowning()
        {
            this.tags.add(DamageTypeTags.IS_DROWNING);
            return this;
        }

        public Builder isFreezing()
        {
            this.tags.add(DamageTypeTags.IS_FREEZING);
            return this;
        }

        public Builder isLightning()
        {
            this.tags.add(DamageTypeTags.IS_LIGHTNING);
            return this;
        }

        public Builder noAnger()
        {
            this.tags.add(DamageTypeTags.NO_ANGER);
            return this;
        }

        public Builder noImpact()
        {
            this.tags.add(DamageTypeTags.NO_IMPACT);
            return this;
        }

        public Builder alwaysMostSignificantFall()
        {
            this.tags.add(DamageTypeTags.ALWAYS_MOST_SIGNIFICANT_FALL);
            return this;
        }

        public Builder witherImmuneTo()
        {
            this.tags.add(DamageTypeTags.WITHER_IMMUNE_TO);
            return this;
        }

        public Builder ignitesArmorStands()
        {
            this.tags.add(DamageTypeTags.IGNITES_ARMOR_STANDS);
            return this;
        }

        public Builder burnsArmorStands()
        {
            this.tags.add(DamageTypeTags.BURNS_ARMOR_STANDS);
            return this;
        }

        public Builder avoidsGuardianThorns()
        {
            this.tags.add(DamageTypeTags.AVOIDS_GUARDIAN_THORNS);
            return this;
        }

        public Builder alwaysTriggersSilverfish()
        {
            this.tags.add(DamageTypeTags.ALWAYS_TRIGGERS_SILVERFISH);
            return this;
        }

        public Builder alwaysHurtsEnderDragons()
        {
            this.tags.add(DamageTypeTags.ALWAYS_HURTS_ENDER_DRAGONS);
            return this;
        }

        public Builder noKnockback()
        {
            this.tags.add(DamageTypeTags.NO_KNOCKBACK);
            return this;
        }

        public Builder alwaysKillsArmorStands()
        {
            this.tags.add(DamageTypeTags.ALWAYS_KILLS_ARMOR_STANDS);
            return this;
        }

        public Builder canBreakArmorStand()
        {
            this.tags.add(DamageTypeTags.CAN_BREAK_ARMOR_STAND);
            return this;
        }

        public Builder bypassesWolfArmor()
        {
            this.tags.add(DamageTypeTags.BYPASSES_WOLF_ARMOR);
            return this;
        }

        public Builder isPlayerAttack()
        {
            this.tags.add(DamageTypeTags.IS_PLAYER_ATTACK);
            return this;
        }

        public Builder burnFromStepping()
        {
            this.tags.add(DamageTypeTags.BURN_FROM_STEPPING);
            return this;
        }

        public Builder panicCauses()
        {
            this.tags.add(DamageTypeTags.PANIC_CAUSES);
            return this;
        }

        public Builder panicEnvironmentalCauses()
        {
            this.tags.add(DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES);
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
