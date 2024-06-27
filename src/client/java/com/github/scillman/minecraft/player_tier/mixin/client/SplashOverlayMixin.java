package com.github.scillman.minecraft.player_tier.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.SplashOverlay;

@Environment(EnvType.CLIENT)
@Mixin(SplashOverlay.class)
public abstract class SplashOverlayMixin
{
    // private void renderProgressBar(DrawContext drawContext, int minX, int minY, int maxX, int maxY, float opacity)
    @Inject(method = "render", at = @At(
        value = "INVOKE",
        target = "Lnet/minecraft/client/gui/screen/SplashOverlay;renderProgressBar(Lnet/minecraft/client/gui/DrawContext;IIIIF)V"
    ))
    private void onRender$1(CallbackInfo ci)
    {
        RenderSystem.clearColor(0.66f, 0.135f, 0.245f, 1.0f);
        RenderSystem.clear(16384, MinecraftClient.IS_SYSTEM_MAC);
    }

    @ModifyArg(method = "render", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/DrawContext;setShaderColor(FFFF)V",
            ordinal = 0 // first occurance
        ),
        index = 3 // 0 = first
    )
    private float onRender$2(float alpha)
    {
        return 0.0f;
    }
}
