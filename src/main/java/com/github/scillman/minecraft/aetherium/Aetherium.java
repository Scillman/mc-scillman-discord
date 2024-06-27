package com.github.scillman.minecraft.aetherium;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.item.PickaxeItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.scillman.minecraft.aetherium.item.AetheriumIngot;
import com.github.scillman.minecraft.aetherium.item.ModToolMaterial;

public class Aetherium implements ModInitializer
{
    public static final String MOD_ID = "aetherium";
    public static final String MOD_NAME = "AEetherium";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

	public static final Item AETHERIUM_INGOT = registerItem("aetherium_ingot",  new AetheriumIngot(new Item.Settings()));

    public static final Item AETHERIUM_PICKAXE = registerItem("aetherium_pickaxe",
        new PickaxeItem(ModToolMaterial.AETHERIUM_INGOT,  new Item.Settings().fireproof().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterial.AETHERIUM_INGOT, 0.0f, -2.8f))));

    private static <T extends Item> T registerItem(String id, T item)
    {
        return Registry.register(Registries.ITEM, Identifier.of(MOD_ID, id), item);
    }

    @Override
    public void onInitialize()
    {
        
    }
}
