package com.github.scillman.minecraft.discord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.scillman.minecraft.discord.registry.ModItems;

import net.fabricmc.api.ModInitializer;

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

        //UseEntityCallback.EVENT.register(ModMain::onUseOnEntity);
    }

/*
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
*/
}
