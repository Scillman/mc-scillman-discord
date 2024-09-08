package com.github.scillman.minecraft.discord;

import com.github.scillman.minecraft.discord.entity.model.DemoEntityModel;
import com.github.scillman.minecraft.discord.registry.ModEntities;
import com.github.scillman.minecraft.discord.render.entity.DemoEntityRenderer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

/**
 * Contains the mod code that is intended for client-side only.
 */
public class ModClient implements ClientModInitializer
{
    public static final EntityModelLayer MODEL_DEMO_LAYER = new EntityModelLayer(Identifier.of(ModMain.MOD_ID, "demo"), "main");

    /**
     * Client-side mod entry point.
     * @remarks Called by the mod loader.
     */
    @Override
    public void onInitializeClient()
    {
        EntityRendererRegistry.register(ModEntities.DEMO, DemoEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(MODEL_DEMO_LAYER, DemoEntityModel::getTexturedModelData);
    }
}
