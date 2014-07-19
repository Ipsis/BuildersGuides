package com.ipsis.buildersguides.init;

import com.ipsis.buildersguides.block.BGBlocks;
import com.ipsis.buildersguides.item.BGItems;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class Recipe {

    public static void init() {

        initModRecipes();
    }

    private static void initModRecipes() {

        return;

        /* Base Marker */
       // GameRegistry.addShapedRecipe(new ItemStack(BGItems.itemMarker),
        //        "srs", "rsr", "srs", 's', Blocks.stone, 'r', Items.redstone);

        /* Range Marker */
        //GameRegistry.addShapelessRecipe(new ItemStack(BGBlocks.blockRangeMarker),
        //        BGItems.itemMarker, Blocks.glass_pane);

        /* Multi Range Marker */
        //GameRegistry.addShapelessRecipe(new ItemStack(BGBlocks.blockRangeMarker),
        //        BGItems.itemMarker, Blocks.glass);

        /* Target Marker */
       // GameRegistry.addShapelessRecipe(new ItemStack(BGBlocks.blockTargetMarker),
       //         BGItems.itemMarker, Items.arrow);

        /* Laser Marker */
        //GameRegistry.addShapelessRecipe(new ItemStack(BGBlocks.blockLaserMarker),
        //        BGItems.itemMarker, Blocks.glass_pane, Blocks.torch);

        /* Multi Laser Marker */
        //GameRegistry.addShapelessRecipe(new ItemStack(BGBlocks.blockLaserMarker),
        //        BGItems.itemMarker, Blocks.glass, Blocks.torch);

        /* Location Marker */
        //GameRegistry.addShapelessRecipe(new ItemStack(BGBlocks.blockCoordMarker),
        //        BGItems.itemMarker, Items.dye);

        /* Axis Marker */
        //GameRegistry.addShapelessRecipe(new ItemStack(BGBlocks.blockAxisMarker),
        //        BGItems.itemMarker, Items.stick, Items.stick, Items.stick);

        /* Advanced Marker */
        //GameRegistry.addShapelessRecipe(new ItemStack(BGBlocks.advancedMarker),
        //        BGItems.itemMarker, Blocks.glass, Blocks.redstone_torch);

        /* Back To Main Marker */
        //GameRegistry.addShapelessRecipe(new ItemStack(BGItems.itemMarker),
        //        BGBlocks.blockAxisMarker);
    }
}
