package com.github.scillman.minecraft.discord.datagen;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;

/**
 * Extends the fabric recipe provider by adding more of the standard recipe formats.
 * @see {@link net.minecraft.data.server.recipe.VanillaRecipeProvider}
 */
public abstract class ExtendedRecipeProvider extends FabricRecipeProvider
{
    public ExtendedRecipeProvider(FabricDataOutput output, CompletableFuture<WrapperLookup> registriesFuture)
    {
        super(output, registriesFuture);
    }

    /**
     * Add a smelting and blasting recipe.
     * @param exporter Recipe exporter used for writing the output json files.
     * @param inputs A list of input item that can be converted into the output item. (e.g. Iron Hoe, Iron Shovel)
     * @param category The recipe category. (e.g. MISC)
     * @param output The item produced when using the recipe. (e.g. Iron Nugget)
     * @param experience The experience given when smelting/blasting finished.
     * @param smeltTime The time it takes to smelt a single item in a furnace.
     * @param blastTime The time it takes to smelt a single item in a blast furnace.
     * @param group The group id.
     */
    protected void offerBlastSmelting(RecipeExporter exporter, List<ItemConvertible> inputs, RecipeCategory category, ItemConvertible output, float experience, int smeltTime, int blastTime, String group)
    {
        offerSmelting(exporter, inputs, category, output, experience, smeltTime, group);
        offerBlasting(exporter, inputs, category, output, experience, blastTime, group);
    }

    /**
     * Compacting for {@code  Nugget <--> Ingot <--> Block} reversibles.
     * @param exporter Recipe exporter used for writing the output json files.
     * @param nugget The nugget item. (e.g. Iron Nugget)
     * @param ingot The ingot item. (e.g. Iron Ingot)
     * @param block The block item. (e.g. Iron Block)
     * @param group The group id. (e.g. iron)
     */
    protected void offerNuggetIngotBlock(RecipeExporter exporter, ItemConvertible nugget, ItemConvertible ingot, ItemConvertible block, String group)
    {
        offerReversibleCompactingRecipesWithReverseRecipeGroup(exporter,
            RecipeCategory.MISC, ingot,
            RecipeCategory.BUILDING_BLOCKS, block,
            (group + "_ingot_from_" + group + "_block"),
            (group + "_ingot")
        );
        offerReversibleCompactingRecipesWithCompactingRecipeGroup(exporter,
            RecipeCategory.MISC, nugget,
            RecipeCategory.MISC, ingot,
            (group + "_ingot_from_nuggets"),
            (group + "_ingot")
        );
    }

    /**
     * Adds a shovel item.
     * @param exporter Recipe exporter used for writing the output json files.
     * @param shovel The shovel item the recipe is for.
     * @param ingot The ingots used to create the shovel.
     */
    protected void offerShovel(RecipeExporter exporter, ItemConvertible shovel, ItemConvertible ingot)
    {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, shovel)
            .input('#', Items.STICK)
            .input('X', ingot)
            .pattern("X")
            .pattern("#")
            .pattern("#")
            .criterion("has_ingot", conditionsFromItem(ingot))
            .offerTo(exporter);
    }

    /**
     * Adds a hoe item recipe.
     * @param exporter Recipe exporter used for writing the output json files.
     * @param hoe The hoe item the recipe is for.
     * @param ingot The ingots used to create the hoe.
     */
    protected void offerHoe(RecipeExporter exporter, ItemConvertible hoe, ItemConvertible ingot)
    {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, hoe)
            .input('#', Items.STICK)
            .input('X', ingot)
            .pattern("XX")
            .pattern(" #")
            .pattern(" #")
            .criterion("has_ingot", conditionsFromItem(ingot))
            .offerTo(exporter);
    }

    /**
     * Adds an axe item recipe.
     * @param exporter Recipe exporter used for writing the output json files.
     * @param axe The axe item the recipe is for.
     * @param ingot The ingots used to create the axe.
     */
    protected void offerAxe(RecipeExporter exporter, ItemConvertible axe, ItemConvertible ingot)
    {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, axe)
            .input('#', Items.STICK)
            .input('X', ingot)
            .pattern("XX")
            .pattern("X#")
            .pattern(" #")
            .criterion("has_ingot", conditionsFromItem(ingot))
            .offerTo(exporter);
    }

    /**
     * Adds a pickaxe item recipe.
     * @param exporter Recipe exporter used for writing the output json files.
     * @param pickaxe The pickaxe item the recipe is for.
     * @param ingot The ingots used to create the pickaxe.
     */
    protected void offerPickaxe(RecipeExporter exporter, ItemConvertible pickaxe, ItemConvertible ingot)
    {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, pickaxe)
            .input('#', Items.STICK)
            .input('X', ingot)
            .pattern("XXX")
            .pattern(" # ")
            .pattern(" # ")
            .criterion("has_ingot", conditionsFromItem(ingot))
            .offerTo(exporter);
    }

    /**
     * Adds a sword item recipe.
     * @param exporter Recipe exporter used for writing the output json files.
     * @param sword The sword item the recipe is for.
     * @param ingot The ingots used to create the sword.
     */
    protected void offerSword(RecipeExporter exporter, ItemConvertible sword, ItemConvertible ingot)
    {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, sword)
            .input('#', Items.STICK)
            .input('X', ingot)
            .pattern("X")
            .pattern("X")
            .pattern("#")
            .criterion("has_ingot", conditionsFromItem(ingot))
            .offerTo(exporter);
    }

    /**
     * Adds a helmet item recipe.
     * @param exporter Recipe exporter used for writing the output json files.
     * @param helmet The helmet item the recipe is for.
     * @param ingot The ingots used to create the helmet.
     */
    protected void offerHelmet(RecipeExporter exporter, ItemConvertible helmet, ItemConvertible ingot)
    {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, helmet)
            .input('X', ingot)
            .pattern("XXX")
            .pattern("X X")
            .criterion("has_ingot", conditionsFromItem(ingot))
            .offerTo(exporter);
    }

    /**
     * Adds a chestplate item recipe.
     * @param exporter Recipe exporter used for writing the output json files.
     * @param chestplate The chestplate item the recipe is for.
     * @param ingot The ingots used to create the chestplate.
     */
    protected void offerChestplate(RecipeExporter exporter, ItemConvertible chestplate, ItemConvertible ingot)
    {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, chestplate)
            .input('X', ingot)
            .pattern("X X")
            .pattern("XXX")
            .pattern("XXX")
            .criterion("has_ingot", conditionsFromItem(ingot))
            .offerTo(exporter);
    }

    /**
     * Adds a leggings item recipe.
     * @param exporter Recipe exporter used for writing the output json files.
     * @param leggings The leggins item the recipe is for.
     * @param ingot The ingots used to create the leggings.
     */
    protected void offerLeggings(RecipeExporter exporter, ItemConvertible leggings, ItemConvertible ingot)
    {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, leggings)
            .input('X', ingot)
            .pattern("XXX")
            .pattern("X X")
            .pattern("X X")
            .criterion("has_ingot", conditionsFromItem(ingot))
            .offerTo(exporter);
    }

    /**
     * Adds a boots item recipe.
     * @param exporter Recipe exporter used for writing the output Json files.
     * @param boots The boots item the recipe is for.
     * @param ingot The ingots used to create the boots.
     */
    protected void offerBoots(RecipeExporter exporter, ItemConvertible boots, ItemConvertible ingot)
    {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, boots)
            .input('X', ingot)
            .pattern("X X")
            .pattern("X X")
            .criterion("has_ingot", conditionsFromItem(ingot))
            .offerTo(exporter);
    }

    /**
     * Helper for smelting items into nuggets, for both furnaces and blast furnaces.
     * @param exporter Recipe exporter used for writing the output Json files.
     * @param category The category of the recipe.
     * @param output The nugget produces by smelting down the items.
     * @param experience The experience gained per smelted item.
     * @param smeltTime The time to smelt a single item inside a furnace.
     * @param blastTime The time to smelt a single item inside a blast furnace.
     * @param group The group id. (e.g. wooden, stone, iron, golden, diamond, etc.)
     * @param shovel The shovel item.
     * @param hoe The hoe item.
     * @param axe The axe item.
     * @param pickaxe The pickaxe item.
     * @param sword The sword item.
     * @param helmet The helmet item.
     * @param chestplate The chestplate item.
     * @param leggings The leggings item.
     * @param boots The boots item.
     * @param horseArmor The horse armor item.
     */
    protected void offerNuggetSmelting(RecipeExporter exporter, RecipeCategory category, ItemConvertible output, float experience, int smeltTime, int blastTime, String group,
            ItemConvertible shovel,
            ItemConvertible hoe,
            ItemConvertible axe,
            ItemConvertible pickaxe,
            ItemConvertible sword,
            ItemConvertible helmet,
            ItemConvertible chestplate,
            ItemConvertible leggings,
            ItemConvertible boots,
            ItemConvertible horseArmor
    )
    {
        Ingredient ingredient = Ingredient.ofItems(
            shovel,
            hoe,
            axe,
            pickaxe,
            sword,
            helmet,
            chestplate,
            leggings,
            boots,
            horseArmor
        );

        CookingRecipeJsonBuilder smelting = CookingRecipeJsonBuilder.createSmelting(ingredient, category, output, experience, smeltTime);
        smelting = addNuggetSmeltingCriterion(smelting, group, shovel, hoe, axe, pickaxe, sword, helmet, chestplate, leggings, boots, horseArmor);
        smelting.offerTo(exporter, getSmeltingItemPath(output));

        CookingRecipeJsonBuilder blasting = CookingRecipeJsonBuilder.createBlasting(ingredient, category, output, experience, blastTime);
        blasting = addNuggetSmeltingCriterion(blasting, group, shovel, hoe, axe, pickaxe, sword, helmet, chestplate, leggings, boots, horseArmor);
        blasting.offerTo(exporter, getBlastingItemPath(output));
    }

    /**
     * Adds the criterion for a smelting/blasting nugget recipe.
     * @param builder The recipe json builder.
     * @param group The group id. (e.g. wooden, stone, iron, golden, diamond, etc.)
     * @param shovel The shovel item.
     * @param hoe The hoe item.
     * @param axe The axe item.
     * @param pickaxe The pickaxe item.
     * @param sword The sword item.
     * @param helmet The helmet item.
     * @param chestplate The chestplate item.
     * @param leggings The leggings item.
     * @param boots The boots item.
     * @param horseArmor The horse armor item.
     * @return The recipe json builder with the criterion added.
     */
    private CookingRecipeJsonBuilder addNuggetSmeltingCriterion(CookingRecipeJsonBuilder builder, String group,
        ItemConvertible shovel,
        ItemConvertible hoe,
        ItemConvertible axe,
        ItemConvertible pickaxe,
        ItemConvertible sword,
        ItemConvertible helmet,
        ItemConvertible chestplate,
        ItemConvertible leggings,
        ItemConvertible boots,
        ItemConvertible horseArmor
    )
    {
        return builder
            .criterion("has_" + group + "_shovel",      conditionsFromItem(shovel))
            .criterion("has_" + group + "_hoe",         conditionsFromItem(hoe))
            .criterion("has_" + group + "_axe",         conditionsFromItem(axe))
            .criterion("has_" + group + "_pickaxe",     conditionsFromItem(pickaxe))
            .criterion("has_" + group + "_sword",       conditionsFromItem(sword))
            .criterion("has_" + group + "_helmet",      conditionsFromItem(helmet))
            .criterion("has_" + group + "_chestplate",  conditionsFromItem(chestplate))
            .criterion("has_" + group + "_leggings",    conditionsFromItem(leggings))
            .criterion("has_" + group + "_boots",       conditionsFromItem(boots))
            .criterion("has_" + group + "_horse_armor", conditionsFromItem(horseArmor))
        ;
    }
}
