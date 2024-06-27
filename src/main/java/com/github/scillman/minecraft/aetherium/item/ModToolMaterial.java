package com.github.scillman.minecraft.aetherium.item;

import net.minecraft.block.Block;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;

import java.util.function.Supplier;

import com.github.scillman.minecraft.aetherium.Aetherium;

public enum ModToolMaterial implements ToolMaterial
{
    AETHERIUM_INGOT(3500, 40.0f, 6.0f, BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 22, () -> Ingredient.ofItems(Aetherium.AETHERIUM_INGOT));

    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final TagKey<Block> inverseTag;
    private final int enchantablity;
    private final Supplier<Ingredient> repairIngredient;

    ModToolMaterial(int itemDurability, float miningSpeed, float attackDamage, TagKey<Block> inverseTag, int enchantablity, Supplier<Ingredient> repairIngredient) {
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.inverseTag = inverseTag;
        this.enchantablity = enchantablity;
        this.repairIngredient = repairIngredient;
    }


    @Override
    public int getDurability() {
        return this.itemDurability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public TagKey<Block> getInverseTag() {
        return this.inverseTag;
    }

    @Override
    public int getEnchantability() {
        return this.enchantablity;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
