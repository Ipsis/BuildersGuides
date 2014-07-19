package com.ipsis.buildersguides.item;

import com.ipsis.buildersguides.block.BGBlocks;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class BGItems {

    public static void preInit() {

        itemMarker = new ItemMarker();
        itemAllenKey = new ItemAllenKey();

        GameRegistry.registerItem(itemMarker, "item.marker");
        GameRegistry.registerItem(itemAllenKey, "item.allenKey");
    }

    public static void initialize() {

    }

    public static void postInit() {

        GameRegistry.addRecipe(new ItemStack(itemMarker, 5), "srs", "rsr", "srs", 's', Blocks.stone, 'r', Items.redstone);
        GameRegistry.addShapelessRecipe(new ItemStack(itemAllenKey), new ItemStack(Items.iron_ingot), new ItemStack(Items.iron_ingot), new ItemStack(Items.flint));

        /*
        GameRegistry.addShapelessRecipe(new ItemStack(itemManual), new ItemStack(itemMarker), new ItemStack(Items.paper));
        GameRegistry.addRecipe(new ItemStack(itemBag), "lll", "lml", "lll", 'l', new ItemStack(Items.leather), 'm', itemMarker); */
    }

    public static ItemBG itemMarker;
    public static ItemBG itemAllenKey;

}
