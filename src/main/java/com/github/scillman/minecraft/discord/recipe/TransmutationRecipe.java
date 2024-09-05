package com.github.scillman.minecraft.discord.recipe;

import com.github.scillman.minecraft.discord.recipe.book.TransmutationRecipeCategory;
import com.github.scillman.minecraft.discord.recipe.input.TransmutationRecipeInput;
import com.github.scillman.minecraft.discord.registry.ModRecipes;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;;

/**
 * @see {@link net.minecraft.recipe.BlastingRecipe BlastingRecipe}
 * @see {@link net.minecraft.recipe.CraftingRecipe CraftingRecipe}
 * @see {@link net.minecraft.recipe.CuttingRecipe CuttingRecipe}
 * @see {@link net.minecraft.recipe.SmeltingRecipe SmeltingRecipe}
 * @see {@link net.minecraft.recipe.SmithingRecipe SmithingRecipe}
 *
 * @see {@link net.minecraft.recipe.ShapedRecipe ShapedRecipe}
 * @see {@link net.minecraft.recipe.ShapelessRecipe ShapelessRecipe}
 */
public class TransmutationRecipe implements Recipe<TransmutationRecipeInput>
{
    /* internal */final String group;
    /* internal */final TransmutationRecipeCategory category;
    /* internal */final ItemStack result;
    /* internal */final DefaultedList<Ingredient> ingredients;

    public TransmutationRecipe(String group, TransmutationRecipeCategory category, ItemStack result, DefaultedList<Ingredient> ingredients)
    {
        this.group = group;
        this.category = category;
        this.result = result;
        this.ingredients = ingredients;
    }

    @Override
    public boolean matches(TransmutationRecipeInput input, World world)
    {
        if (input.getStackCount() != 3)
        {
            return false;
        }

        return input.getRecipeMatcher().match(this, null);
    }

    @Override
    public ItemStack craft(TransmutationRecipeInput input, RegistryWrapper.WrapperLookup registriesLookup)
    {
        return this.result.copy();
    }

    @Override
    public boolean fits(int width, int height)
    {
        return (width * height) >= 3;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup)
    {
        return this.result;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ModRecipes.TRANSUMATION_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType()
    {
        return ModRecipes.TRANSMUTATION;
    }
}
