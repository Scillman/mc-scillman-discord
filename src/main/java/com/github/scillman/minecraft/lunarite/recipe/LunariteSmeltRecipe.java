package com.github.scillman.minecraft.lunarite.recipe;

import net.minecraft.recipe.CookingRecipeSerializer;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.github.scillman.minecraft.lunarite.Lunarite.MOD_ID;

public class LunariteSmeltRecipe
{
    public static final String LUNARITE_RECIPE_ID = "lunarite_smelt_recipe";

    public static final RecipeType<LunariteSmelt> LUNARITE_RECIPE_TYPE;
    public static final RecipeSerializer<LunariteSmelt> LUNARITE_RECIPE_SERIALIZER;

    static {

        LUNARITE_RECIPE_TYPE = Registry.register(
            Registries.RECIPE_TYPE,
            Identifier.of(MOD_ID, LUNARITE_RECIPE_ID),
            new RecipeType<LunariteSmelt>() {
                @Override
                public String toString() {
                    return LUNARITE_RECIPE_ID;
                }
            }
        );

        LUNARITE_RECIPE_SERIALIZER = Registry.register(
            Registries.RECIPE_SERIALIZER,
            Identifier.of(MOD_ID, LUNARITE_RECIPE_ID),
            new CookingRecipeSerializer<>(LunariteSmelt::new, 200)
        );
    }
}
