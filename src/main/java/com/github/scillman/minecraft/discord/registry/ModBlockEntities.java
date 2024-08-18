package com.github.scillman.minecraft.discord.registry;

import com.github.scillman.minecraft.discord.ModMain;
import com.github.scillman.minecraft.discord.block.entity.SignPoleBlockEntity;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.BlockEntityType.BlockEntityFactory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ModBlockEntities
{
    public static final BlockEntityType<SignPoleBlockEntity> SIGN_POLE_BLOCK_ENTITY =
        register("sign_pole", SignPoleBlockEntity::new, ModBlocks.SIGN_POLE_BLOCK);

    private static <T extends BlockEntity> BlockEntityType<T> register(String id, BlockEntityFactory<T> factory, Block block)
    {
        return Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(ModMain.MOD_ID, id),
            BlockEntityType.Builder.create(factory, block).build()
        );
    }

    public static void register()
    {
    }
}
