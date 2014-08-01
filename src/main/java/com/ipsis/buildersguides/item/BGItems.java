package com.ipsis.buildersguides.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class BGItems {

    public static void preInit() {

        itemMarker = new ItemMarker();
        itemAllenKey = new ItemAllenKey();

        GameRegistry.registerItem(itemMarker, "item.marker");
        GameRegistry.registerItem(itemAllenKey, "item.allenKey");
    }

    public static void initialize() {

        GameRegistry.addRecipe(new ItemStack(itemMarker, 5), "srs", "rsr", "srs", 's', Blocks.stone, 'r', Items.redstone);
        GameRegistry.addShapelessRecipe(new ItemStack(itemAllenKey), new ItemStack(Items.iron_ingot), new ItemStack(Items.iron_ingot), new ItemStack(Items.flint));

        /*
        GameRegistry.addShapelessRecipe(new ItemStack(itemManual), new ItemStack(itemMarker), new ItemStack(Items.paper));
        GameRegistry.addRecipe(new ItemStack(itemBag), "lll", "lml", "lll", 'l', new ItemStack(Items.leather), 'm', itemMarker);
         */
    }

    public static void postInit() {

    }

    public static ItemBG itemMarker;
    public static ItemBG itemAllenKey;

}
