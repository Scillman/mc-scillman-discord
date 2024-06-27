package com.github.scillman.minecraft.player_tier.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.scillman.minecraft.player_tier.client.CustomShaders;

import org.spongepowered.asm.mixin.injection.At;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin
{
    @Inject(method = "onFinishedLoading", at = @At("TAIL"))
    private void onFinishedLoading$2(CallbackInfo ci)
    {
        MinecraftClient client = (MinecraftClient) (Object) this;
        CustomShaders.loadShaderResources(client);
    }
}
