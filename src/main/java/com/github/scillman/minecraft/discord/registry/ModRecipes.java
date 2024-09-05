package com.github.scillman.minecraft.discord.registry;

import com.github.scillman.minecraft.discord.ModMain;
import com.github.scillman.minecraft.discord.recipe.TransmutationRecipe;
import com.github.scillman.minecraft.discord.recipe.TransmutationRecipeSerializer;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 *
 */
public final class ModRecipes
{
    public static final RecipeType<TransmutationRecipe> TRANSMUTATION = register("transmutation");
    public static final RecipeSerializer<TransmutationRecipe> TRANSUMATION_SERIALIZER =
        serializer("transmutation", new TransmutationRecipeSerializer());

    /**
     *
     * @param <T>
     * @param id
     * @return
     */
    private static <T extends Recipe<?>> RecipeType<T> register(String id)
    {
        return Registry.register(Registries.RECIPE_TYPE, Identifier.of(ModMain.MOD_ID, id), new RecipeType<T>() {
            @Override
            public String toString()
            {
                return id;
            }
        });
    }

    /**
     *
     * @param <S>
     * @param <T>
     * @param id
     * @param serializer
     * @return
     */
    private static <S extends RecipeSerializer<T>, T extends Recipe<?>> S serializer(String id, S serializer)
    {
        return Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(ModMain.MOD_ID, id), serializer);
    }

    /**
     *
     */
    public static void register()
    {
    }
}
