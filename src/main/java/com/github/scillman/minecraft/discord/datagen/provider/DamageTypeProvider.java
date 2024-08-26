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
import net.minecraft.registry.RegistryKeys;
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
        return this.output.getResolver(OutputType.DATA_PACK, RegistryKeys.getPath(RegistryKeys.DAMAGE_TYPE)).resolveJson(id);
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

        /**
         * Set the id of the death message.
         * @param messageId The id of the death message.
         * @return The builder.
         * @remarks Parameter is without prefixes and namespace. (For example, death.attack.fire would be just fire)
         */
        public Builder setMessageId(String messageId)
        {
            this.messageId = messageId;
            return this;
        }

        /**
         * Sets the damage scaling for the damage type.
         * @param damageScaling The damage scaling to use.
         * @return The builder.
         */
        public Builder setDamageScaling(DamageScaling damageScaling)
        {
            this.damageScaling = damageScaling;
            return this;
        }

        /**
         * Set the amount of exhaustion/hunger the player takes when taking this damage.
         * @param exhaustion The amount of exhaustion.
         * @return The builder.
         */
        public Builder setExhaustion(float exhaustion)
        {
            this.exhaustion = exhaustion;
            return this;
        }

        /**
         * Set the damage effect to use when the player takes this damage.
         * @param damageEffects The damage effects.
         * @return The builder.
         */
        public Builder setDamageEffects(DamageEffects damageEffects)
        {
            this.effects = damageEffects;
            return this;
        }

        /**
         * Set the type of the death message that will be displayed on a player's death.
         * @param deathMessageType The type of the death message.
         * @return The builder.
         */
        public Builder setDeathMessageType(DeathMessageType deathMessageType)
        {
            this.deathMessageType = deathMessageType;
            return this;
        }

        /**
         * Causes damage to the helmet worn by entities taking damage of this type.
         * @return The builder.
         * @remarks Examples: falling anvil, falling block and falling stalactite.
         */
        public Builder doDamgeToHelmets()
        {
            this.tags.add(DamageTypeTags.DAMAGES_HELMET);
            return this;
        }

        /**
         * Bypasses protection provided by worn armors.
         * @return The builder.
         * @remarks Examples: fire, entity cramming, wither, freeze, etc.
         * @remarks This function will implicitly add it to {@link #bypassesShield()}.
         */
        public Builder bypassArmor()
        {
            this.tags.add(DamageTypeTags.BYPASSES_ARMOR);
            return this;
        }

        /**
         * Bypasses protection provided by shields.
         * @return The builder.
         * @remarks Examples: falling anvil and falling stalactite.
         */
        public Builder bypassShield()
        {
            this.tags.add(DamageTypeTags.BYPASSES_SHIELD);
            return this;
        }

        /**
         * Bypasses any invulnerability protection.
         * @return The builder.
         * @remarks Examples: out-of-world.
         * @remarks Totem of Undying is a form of invulnerability protection.
         */
        public Builder bypassInvulnerability()
        {
            this.tags.add(DamageTypeTags.BYPASSES_INVULNERABILITY);
            return this;
        }

        /**
         * This damage is allowed to bypass the default cooldown.
         * @return The builder.
         * @remarks Not to be used in official releases and seems to be purely for testing inside development environments.
         * @remarks By default damage is accumulated up to a maximum over a given time delta, calling this function allows the damage to bypass this. It can be best envisioned as being similar to the hearts in-game which only take damage every 1s with status effects like Withering.
         */
        public Builder bypassCooldown()
        {
            this.tags.add(DamageTypeTags.BYPASSES_COOLDOWN);
            return this;
        }

        /**
         * This damage is not affected by status effects.
         * @return The builder.
         * @remarks Examples: starvation.
         */
        public Builder bypassStatusEffects()
        {
            this.tags.add(DamageTypeTags.BYPASSES_EFFECTS);
            return this;
        }

        /**
         * This damage bypasses all resistances.
         * @return The builder.
         * @remarks Examples: out-of-world.
         */
        public Builder bypassResistance()
        {
            this.tags.add(DamageTypeTags.BYPASSES_RESISTANCE);
            return this;
        }

        /**
         * This damage bypasses enchantments.
         * @return The builder.
         * @remarks Examples: sonic boom. (aka warden sound attack.)
         */
        public Builder bypassEnchantments()
        {
            this.tags.add(DamageTypeTags.BYPASSES_ENCHANTMENTS);
            return this;
        }

        /**
         * Makes it so that this damage type is counted as fire damage.
         * @return The builder.
         * @remarks Examples: fire, campfire, lave, magma blocks and fireballs.
         */
        public Builder isFireDamage()
        {
            this.tags.add(DamageTypeTags.IS_FIRE);
            return this;
        }

        /**
         * Makes it so that this damage type is counted as projectile damage.
         * @return The builder.
         * @remarks Examples: arrows, tridents, fireball, wither skulls and wind charges.
         */
        public Builder isProjectileDamage()
        {
            this.tags.add(DamageTypeTags.IS_PROJECTILE);
            return this;
        }

        /**
         * Makes it so that this damage type does not affect witches.
         * @return The builder.
         * @remarks Examples: magic and thorns.
         */
        public Builder makeWitchResistantTo()
        {
            this.tags.add(DamageTypeTags.WITCH_RESISTANT_TO);
            return this;
        }

        /**
         * Makes it so that this damage type is counted as explosion damage.
         * @return The builder.
         * @remarks Examples: fireworks and explosions.
         */
        public Builder isExplosionDamage()
        {
            this.tags.add(DamageTypeTags.IS_EXPLOSION);
            return this;
        }

        /**
         * Makes it so that this damage type is counted as fall damage.
         * @return The builder.
         * @remarks Examples: fall and stalagmite. (e.g. roof pointed dripstone falls onto entity)
         */
        public Builder isFallDamage()
        {
            this.tags.add(DamageTypeTags.IS_FALL);
            return this;
        }

        /**
         * Makes it so that this damage type is counted as drown damage.
         * @return The builder.
         * @remarks Examples: drowning.
         */
        public Builder isDrownDamage()
        {
            this.tags.add(DamageTypeTags.IS_DROWNING);
            return this;
        }

        /**
         * Makes it so that this damage type is counted as freeze damage.
         * @return The builder.
         * @remarks Examples: freeze.
         */
        public Builder isFreezeDamage()
        {
            this.tags.add(DamageTypeTags.IS_FREEZING);
            return this;
        }

        /**
         * Makes it so that this damage type is counted as lightning damage.
         * @return The builder.
         * @remarks Examples: lightning.
         */
        public Builder isLightningDamage()
        {
            this.tags.add(DamageTypeTags.IS_LIGHTNING);
            return this;
        }

        /**
         * Makes it so that this damage does not cause entities to become angry to the source entity.
         * @return The builder.
         * @remarks Examples: ??? (mob_attack_no_agro)
         */
        public Builder noAnger()
        {
            this.tags.add(DamageTypeTags.NO_ANGER);
            return this;
        }

        /**
         * Makes it so that this damage does not have an impact. (aka not changes the entity velocity)
         * @return The builder.
         * @remarks Examples: drown.
         */
        public Builder noImpact()
        {
            this.tags.add(DamageTypeTags.NO_IMPACT);
            return this;
        }

        /**
         * Makes it so that this damage is always a lethal fall.
         * @return The builder.
         * @remarks Examples: out-of-world. (aka void)
         * @remarks Cranks the done damage to the abosulte maximum possible.
         */
        public Builder alwaysMostSignificantFall()
        {
            this.tags.add(DamageTypeTags.ALWAYS_MOST_SIGNIFICANT_FALL);
            return this;
        }

        /**
         * Causes the Wither boss to become immune to this type of damage.
         * @return The builder.
         * @remarks Examples: drowning.
         */
        public Builder makeWitherImmuneTo()
        {
            this.tags.add(DamageTypeTags.WITHER_IMMUNE_TO);
            return this;
        }

        /**
         * Makes it so that this damage type can cause an armor stand to catch fire.
         * @return The builder.
         * @remarks Examples: fire and campfires.
         */
        public Builder ignitesArmorStands()
        {
            this.tags.add(DamageTypeTags.IGNITES_ARMOR_STANDS);
            return this;
        }

        /**
         * Makes it so that this damage type causes an armor stand to burn.
         * @return The builder.
         * @remarks Examples: fire.
         */
        public Builder burnsArmorStands()
        {
            this.tags.add(DamageTypeTags.BURNS_ARMOR_STANDS);
            return this;
        }

        /**
         * ???
         * @return The builder.
         * @remarks Examples: thorns and explosions.
         */
        public Builder avoidsGuardianThorns()
        {
            this.tags.add(DamageTypeTags.AVOIDS_GUARDIAN_THORNS);
            return this;
        }

        /**
         * Always triggers silverfish to spawn.
         * @return The builder.
         * @remarks Examples: ??? (magic)
         */
        public Builder alwaysTriggersSilverfish()
        {
            this.tags.add(DamageTypeTags.ALWAYS_TRIGGERS_SILVERFISH);
            return this;
        }

        /**
         * This damage will always cause damage to an ender dragon.
         * @return The builder.
         * @remarks Examples: explosions.
         */
        public Builder alwaysHurtsEnderDragons()
        {
            this.tags.add(DamageTypeTags.ALWAYS_HURTS_ENDER_DRAGONS);
            return this;
        }

        /**
         * This damage does not cause entity knockback.
         * @return The builder.
         * @remarks Examples: fire, lave, drowning, starving, falling, etc.
         */
        public Builder noKnockback()
        {
            this.tags.add(DamageTypeTags.NO_KNOCKBACK);
            return this;
        }

        /**
         * This damage will always cause an armor stand to break.
         * @return The builder.
         * @remarks Examples: arrows, tridents, fireballs, wither skull/attack and wind charge.
         */
        public Builder alwaysKillsArmorStands()
        {
            this.tags.add(DamageTypeTags.ALWAYS_KILLS_ARMOR_STANDS);
            return this;
        }

        /**
         * This damage can cause an armor stand to break.
         * @return The builder.
         * @remarks Examples: player attacks and explosions.
         */
        public Builder canBreakArmorStand()
        {
            this.tags.add(DamageTypeTags.CAN_BREAK_ARMOR_STAND);
            return this;
        }

        /**
         * This damage bypasses a wolf's armor protection.
         * @return The builder.
         * @remarks Examples: fire, drowning, falling, etc.
         */
        public Builder bypassesWolfArmor()
        {
            this.tags.add(DamageTypeTags.BYPASSES_WOLF_ARMOR);
            return this;
        }

        /**
         * This damage is caused by an attacking player.
         * @return The builder.
         */
        public Builder isPlayerAttack()
        {
            this.tags.add(DamageTypeTags.IS_PLAYER_ATTACK);
            return this;
        }

        /**
         * This damage is caused by an entity stepping onto a block/entity.
         * @return The builder.
         * @remarks Examples: campfires and magma blocks.
         */
        public Builder burnFromStepping()
        {
            this.tags.add(DamageTypeTags.BURN_FROM_STEPPING);
            return this;
        }

        /**
         * Causes entities to enter a state of panic when receiving damage.
         * @return The builder.
         * @seealso {@link #panicEnvironmentalCauses()}
         * @remarks Examples: arrows, explosions, bee sting and thrown weapons/projectiles.
         */
        public Builder panicCauses()
        {
            this.tags.add(DamageTypeTags.PANIC_CAUSES);
            return this;
        }

        /**
         * Cause entities to enter a state of panic when receiving damage from a natural/environment source.
         * @return The builder.
         * @remarks Examples: cactus, lava and lightning.
         * @remarks This function will implicitly add it to {@link #panicCauses()}.
         * @seealso {@link #panicCauses()}
         */
        public Builder panicEnvironmentalCauses()
        {
            this.tags.add(DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES);
            return this;
        }

        /**
         * Build the damage type instance.
         * @return The build damage type instance.
         */
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
