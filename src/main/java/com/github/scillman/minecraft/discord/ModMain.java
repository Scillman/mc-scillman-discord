package com.github.scillman.minecraft.discord;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.scillman.minecraft.discord.item.CustomItem;
import com.github.scillman.minecraft.discord.registry.ModItems;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

/**
 * Contains the common code of the mod.
 */
public class ModMain implements ModInitializer
{
    public static final String MOD_ID = "discord";
    public static final String MOD_NAME = "Discord";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    /**
     * The mod's main entry point.
     * @remarks Called by the mod loader.
     */
    @Override
    public void onInitialize()
    {
        ModItems.register();

        UseEntityCallback.EVENT.register(ModMain::onUseOnEntity);
    }

    private static ActionResult onUseOnEntity(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult hitResult)
    {
        if (world.isClient())
        {
            return ActionResult.PASS;
        }

        if (player.isSneaking() && player.getStackInHand(hand).getItem() instanceof CustomItem item)
        {
            player.sendMessage(Text.of("1"), false);
            return ActionResult.SUCCESS;
        }

        player.sendMessage(Text.of("2"), false);
        return ActionResult.PASS;
    }
}
