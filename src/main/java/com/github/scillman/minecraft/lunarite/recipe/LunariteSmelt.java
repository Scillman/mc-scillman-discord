package com.github.scillman.minecraft.lunarite.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.CookingRecipeCategory;

public class LunariteSmelt extends AbstractCookingRecipe
{
    public LunariteSmelt(String group, CookingRecipeCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime)
    {
        super(LunariteSmeltRecipe.LUNARITE_RECIPE_TYPE, group, category, ingredient, result, experience, cookingTime);
    }

//    @Override
//    public ItemStack getRecipeKindIcon()
//    {
//        return new ItemStack(Items.BLACKSTONE);
//    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return LunariteSmeltRecipe.LUNARITE_RECIPE_SERIALIZER;
    }
}
