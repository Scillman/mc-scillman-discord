package com.github.scillman.minecraft.player_tier.client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import static com.github.scillman.minecraft.player_tier.PlayerTier.MOD_ID;
import static com.github.scillman.minecraft.player_tier.PlayerTier.LOGGER;

@Environment(EnvType.CLIENT)
public class CustomShaders
{
    public static final Identifier VERT_SHADER_ID = Identifier.of(MOD_ID, "shaders/fontrenderer.vert");
    public static final Identifier FRAG_SHADER_ID = Identifier.of(MOD_ID, "shaders/fontrenderer.frag");
    public static final Identifier SHADER_PROGRAM_ID = Identifier.of(MOD_ID, "shaders/fontrenderer.json");

    public static void loadShaderResources(MinecraftClient client)
    {
        ResourceManager manager = client.getResourceManager();

        try
        {
            Resource resource = manager.getResourceOrThrow(VERT_SHADER_ID);
            InputStream stream = resource.getInputStream();
            byte[] buffer = stream.readAllBytes();
            String data = new String(buffer, StandardCharsets.UTF_8);
            LOGGER.info(data);
        }
        catch (FileNotFoundException ex)
        {
            LOGGER.error("FileNotFoundException: {}", ex.getMessage());
        }
        catch (IOException ex)
        {
            LOGGER.error("IOException: {}", ex.getMessage());
        }
    }
}
