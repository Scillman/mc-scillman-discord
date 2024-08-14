package com.github.scillman.minecraft.discord.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.scillman.minecraft.discord.ModMain;
import com.github.scillman.minecraft.discord.item.CustomItem;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin
{
    @Inject(method = "useOnEntity", at = @At("HEAd"), cancellable = true)
    private void onUseOnEntity(PlayerEntity player, LivingEntity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir)
    {
        ModMain.LOGGER.info("onUseOnEntity");

        World world = player.getWorld();
        if (world.isClient())
        {
            return;
        }

        if (player.isSneaking() && player.getStackInHand(hand).getItem() instanceof CustomItem item) {
            ModMain.LOGGER.info("1");
            player.sendMessage(Text.of("1"), false);
            cir.setReturnValue(ActionResult.SUCCESS);
        }

        ModMain.LOGGER.info("2");
        player.sendMessage(Text.of("2"), false);
        //ActionResult.PASS;
    }
}
