package com.github.scillman.minecraft.discord.mixin;

import org.spongepowered.asm.mixin.Mixin;

import com.github.scillman.minecraft.discord.item.CustomItem;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @WrapOperation(method = "interact", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;interact(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;"))
    public ActionResult onInteractEntity(Entity instance, PlayerEntity player, Hand hand, Operation<ActionResult> original) {
        ActionResult result = onInteractWithEntity(player, player.getWorld(), hand, instance);
        if (result.isAccepted()) {
            return result;
        }

        return original.call(instance, player, hand);
    }

    private ActionResult onInteractWithEntity(PlayerEntity player, World world, Hand hand, Entity entity) {
        if (world.isClient()) {
            return ActionResult.PASS;
        }

        if (hand != Hand.MAIN_HAND) {
            return ActionResult.PASS;
        }

        if (player.isSneaking() && player.getStackInHand(hand).getItem() instanceof CustomItem) {
            player.sendMessage(Text.of("1"), false);
            return ActionResult.SUCCESS;
        }

        player.sendMessage(Text.of("2"), false);
        return ActionResult.PASS;
    }
}
