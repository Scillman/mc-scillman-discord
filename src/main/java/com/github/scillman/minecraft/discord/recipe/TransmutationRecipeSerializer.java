package com.github.scillman.minecraft.discord.recipe;

import com.github.scillman.minecraft.discord.recipe.book.TransmutationRecipeCategory;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.collection.DefaultedList;

/**
 * @see {@link net.minecraft.recipe.ShapedRecipe.Serializer ShapedRecipe.Serializer}
 * @see {@link net.minecraft.recipe.ShapelessRecipe.Serializer ShapelessRecipe.Serializer}
 */
public class TransmutationRecipeSerializer implements RecipeSerializer<TransmutationRecipe>
{
    private static final MapCodec<TransmutationRecipe> CODEC = RecordCodecBuilder.mapCodec(
        instance -> instance.group(
            Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
            TransmutationRecipeCategory.CODEC.fieldOf("category").orElse(TransmutationRecipeCategory.MISC).forGetter(recipe -> recipe.category),
            ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
            Ingredient.DISALLOW_EMPTY_CODEC
                .listOf()
                .fieldOf("ingredients")
                .flatXmap(
                    ingredientsList -> {
                        Ingredient[] ingredients = ingredientsList.stream().filter(ingredient -> !ingredient.isEmpty()).toArray(Ingredient[]::new);
                        if (ingredients.length != 3) {
                            return DataResult.error(() -> "Transmutation required three ingredients per recipe");
                        }
                        else {
                            return DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY, ingredients));
                        }
                    },
                    DataResult::success)
                .forGetter(recipe -> recipe.ingredients)
        )
        .apply(instance, TransmutationRecipe::new)
    );

    public static final PacketCodec<RegistryByteBuf, TransmutationRecipe> PACKET_CODEC = PacketCodec.ofStatic(
        TransmutationRecipeSerializer::write,
        TransmutationRecipeSerializer::read
    );

    @Override
    public MapCodec<TransmutationRecipe> codec()
    {
        return CODEC;
    }

    @Override
    public PacketCodec<RegistryByteBuf, TransmutationRecipe> packetCodec()
    {
        return PACKET_CODEC;
    }

    private static TransmutationRecipe read(RegistryByteBuf buf)
    {
        String group = buf.readString();
        TransmutationRecipeCategory category = buf.readEnumConstant(TransmutationRecipeCategory.class);

        int size = buf.readVarInt();
        assert(size == 3);
        DefaultedList<Ingredient> ingredients = DefaultedList.ofSize(size, Ingredient.EMPTY);
        ingredients.replaceAll(empty -> Ingredient.PACKET_CODEC.decode(buf));

        ItemStack result = ItemStack.PACKET_CODEC.decode(buf);

        return new TransmutationRecipe(group, category, result, ingredients);
    }

    private static void write(RegistryByteBuf buf, TransmutationRecipe recipe)
    {
        buf.writeString(recipe.group);
        buf.writeEnumConstant(recipe.category);

        buf.writeVarInt(recipe.ingredients.size());
        for (Ingredient ingredient: recipe.ingredients)
        {
            Ingredient.PACKET_CODEC.encode(buf, ingredient);
        }

        ItemStack.PACKET_CODEC.encode(buf, recipe.result);
    }
}
