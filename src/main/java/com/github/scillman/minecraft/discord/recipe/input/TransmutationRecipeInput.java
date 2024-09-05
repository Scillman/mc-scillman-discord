package com.github.scillman.minecraft.discord.recipe.input;

import java.security.InvalidParameterException;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.input.RecipeInput;

/**
 * An unordered recipe input used for the transmutation recipe.
 *
 * @see {@link net.minecraft.recipe.input.CraftingRecipeInput CraftingRecipeInput}
 * @see {@link net.minecraft.recipe.input.SingleStackRecipeInput SingleStackRecipeInput}
 * @see {@link net.minecraft.recipe.input.SmithingRecipeInput SmithingRecipeInput}
 */
public class TransmutationRecipeInput implements RecipeInput
{
    public static final TransmutationRecipeInput EMPTY = new TransmutationRecipeInput(List.of());

    private final List<ItemStack> stacks;
    private final RecipeMatcher matcher;
    private final int stackCount;

    private TransmutationRecipeInput(List<ItemStack> stacks) throws InvalidParameterException
    {
        this.stacks = stacks;
        this.matcher = new RecipeMatcher();

        int stackCount = 0;
        for (ItemStack itemStack: stacks)
        {
            if (!itemStack.isEmpty())
            {
                stackCount += 1;
                this.matcher.addInput(itemStack, 1);
            }
        }

        if (stackCount != 3)
        {
            throw new InvalidParameterException("The stacks requires three valid item stacks");
        }

        this.stackCount = stackCount;
    }

    public static TransmutationRecipeInput create(List<ItemStack> stacks) throws InvalidParameterException
    {
        return new TransmutationRecipeInput(stacks);
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        assert(slot < this.stackCount);
        return this.stacks.get(slot);
    }

    @Override
    public int getSize()
    {
        return this.stacks.size();
    }

    @Override
    public boolean isEmpty()
    {
        return this.stackCount == 0;
    }

    public RecipeMatcher getRecipeMatcher()
    {
        return this.matcher;
    }

    public List<ItemStack> getStacks()
    {
        return this.stacks;
    }

    public int getStackCount()
    {
        return this.stackCount;
    }

    public boolean equals(Object other)
    {
        if (other == this)
        {
            return true;
        }

        if (other instanceof TransmutationRecipeInput otherRecipeInput)
        {
            if (this.stackCount != otherRecipeInput.stackCount)
            {
                return false;
            }

            for (int i = 0; i < this.stackCount; ++i)
            {
                if (!ItemStack.areEqual(this.stacks.get(i), otherRecipeInput.stacks.get(i)))
                {
                    return false;
                }
            }

            return true;
        }

        return false;
    }
}
