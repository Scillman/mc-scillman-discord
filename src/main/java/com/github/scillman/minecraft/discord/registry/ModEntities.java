package com.github.scillman.minecraft.discord.registry;

import com.github.scillman.minecraft.discord.ModMain;
import com.github.scillman.minecraft.discord.entity.DemoEntity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 *
 */
public final class ModEntities
{
    public static final EntityType<DemoEntity> DEMO = Registry.register(
        Registries.ENTITY_TYPE,
        Identifier.of(ModMain.MOD_ID, "demo"),
        EntityType.Builder.create(DemoEntity::new, SpawnGroup.CREATURE)
            .dimensions(0.75f, 0.75f)
            .build("demo")
    );

    /**
     *
     */
    public static void register()
    {
        FabricDefaultAttributeRegistry.register(DEMO, DemoEntity.createMobAttributes());
    }
}
