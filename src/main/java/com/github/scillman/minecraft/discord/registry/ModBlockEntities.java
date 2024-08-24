package com.github.scillman.minecraft.discord.registry;

import com.github.scillman.minecraft.discord.ModMain;
import com.github.scillman.minecraft.discord.block.entity.DemoBlockEntity;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.BlockEntityType.BlockEntityFactory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 * Contains all the custom block entities used inside the mod.
 */
public final class ModBlockEntities
{
    public static final BlockEntityType<DemoBlockEntity> DEMO_BLOCK_ENTITY = register("demo_block_entity", DemoBlockEntity::new, ModBlocks.DEMO_BLOCK);

    /**
     * Register a new block entity.
     * @param <T> The type of the block entity to register.
     * @param id The id to register the block entity with.
     * @param factory The function used to create create a new instance of the block entity.
     * @param block The block the block entity is associated with.
     * @return The instance as registered with Minecraft registry.
     */
    private static <T extends BlockEntity> BlockEntityType<T> register(String id, BlockEntityFactory<T> factory, Block block)
    {
        return Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(ModMain.MOD_ID, id),
            BlockEntityType.Builder.create(factory, block).build()
        );
    }

    /**
     * Registers block entity related content.
     * @remarks Must be called even when empty.
     */
    public static void register()
    {
    }
}
