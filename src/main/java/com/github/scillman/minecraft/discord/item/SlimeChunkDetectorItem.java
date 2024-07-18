package com.github.scillman.minecraft.discord.item;

import java.util.List;

import com.github.scillman.minecraft.discord.DiscordServer;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.world.World;
import net.minecraft.world.StructureWorldAccess;

public class SlimeChunkDetectorItem extends Item
{
    public SlimeChunkDetectorItem(Settings settings)
    {
        super(settings);
    }

    private boolean isSlimeSpawningArea;
    private boolean slime;

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected)
    {
        if (!world.isClient())
        {
            assert(world instanceof ServerWorld);
            assert(entity instanceof ServerPlayerEntity);

            if (world instanceof ServerWorld serverWorld &&
                entity instanceof ServerPlayerEntity player)
            {
                DiscordServer.sendWorldSeedToPlayer(player, serverWorld);
            }
        }

        if (!world.isClient() && entity instanceof PlayerEntity player)
        {
            ChunkPos chunkPos = player.getChunkPos();
            this.slime = ChunkRandom.getSlimeRandom(chunkPos.x, chunkPos.z, ((StructureWorldAccess)world).getSeed(), 987234911L).nextInt(10) == 0;
        }

        if (world.getBiome(entity.getBlockPos()).isIn(BiomeTags.ALLOWS_SURFACE_SLIME_SPAWNS) || this.slime)
        {
            this.isSlimeSpawningArea = true;
        }
        else
        {
            this.isSlimeSpawningArea = false;
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type)
    {
        tooltip.add(Text.translatable("item.kip.slime_chunk_detector.tooltip", Text.translatable(slimeSpawnString())));
    }

    private String slimeSpawnString()
    {
        if (this.isSlimeSpawningArea)
        {
            return "item.kip.slime_chunk_detector.slime_found";
        }

        return "item.kip.slime_chunk_detector.slime_lost";
    }

    public static boolean hasSlime(ItemStack stack)
    {
        Item item = stack.getItem();
        if (item instanceof SlimeChunkDetectorItem detectorItem)
        {
            return detectorItem.isSlimeSpawningArea;
        }

        return false;
    }
}
