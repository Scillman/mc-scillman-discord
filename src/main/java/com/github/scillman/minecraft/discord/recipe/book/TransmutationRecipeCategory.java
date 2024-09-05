package com.github.scillman.minecraft.discord.recipe.book;

import java.util.function.IntFunction;

import com.mojang.serialization.Codec;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;

/**
 * @see {@link net.minecraft.recipe.book.CraftingRecipeCategory CraftingRecipeCategory}
 */
public enum TransmutationRecipeCategory implements StringIdentifiable
{
    METALS("metals", 0),
    ORGANIC("organic", 1),
    MISC("misc", 2);

    public static final Codec<TransmutationRecipeCategory> CODEC =
        StringIdentifiable.createCodec(TransmutationRecipeCategory::values);
    public static final IntFunction<TransmutationRecipeCategory> INDEX_TO_VALUE =
        ValueLists.createIdToValueFunction(TransmutationRecipeCategory::getIndex, values(), ValueLists.OutOfBoundsHandling.ZERO);
    public static final PacketCodec<ByteBuf, TransmutationRecipeCategory> PACKET_CODEC =
        PacketCodecs.indexed(INDEX_TO_VALUE, TransmutationRecipeCategory::getIndex);

    private final String id;
    private final int index;

    private TransmutationRecipeCategory(final String id, final int index)
    {
        this.id = id;
        this.index = index;
    }

    @Override
    public String asString()
    {
        return this.id;
    }

    private int getIndex()
    {
        return this.index;
    }
}
