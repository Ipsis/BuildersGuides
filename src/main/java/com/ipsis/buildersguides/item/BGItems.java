package com.ipsis.buildersguides.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class BGItems {

    public static void preInit() {

        itemMarker = new ItemMarker();
        itemAllenKey = new ItemAllenKey();
        itemToolBox = new ItemToolbox();

        GameRegistry.registerItem(itemMarker, "item.marker");
        GameRegistry.registerItem(itemAllenKey, "item.allenKey");
        GameRegistry.registerItem(itemToolBox, "item.toolBox");
    }

    public static void initialize() {

        GameRegistry.addRecipe(new ItemStack(itemMarker, 5), "srs", "rsr", "srs", 's', Blocks.stone, 'r', Items.redstone);
        GameRegistry.addShapelessRecipe(new ItemStack(itemAllenKey), new ItemStack(Items.iron_ingot), new ItemStack(Items.iron_ingot), new ItemStack(Items.flint));
        GameRegistry.addRecipe(new ItemStack(itemToolBox), "lll", "lml", "lll", 'l', new ItemStack(Items.leather), 'm', itemMarker);

        /*
        GameRegistry.addShapelessRecipe(new ItemStack(itemManual), new ItemStack(itemMarker), new ItemStack(Items.paper));

         */
    }

    public static void postInit() {

    }

    public static ItemBG itemMarker;
    public static ItemBG itemAllenKey;
    public static ItemBG itemToolBox;

}
