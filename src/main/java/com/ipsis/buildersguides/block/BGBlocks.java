package com.ipsis.buildersguides.block;

import com.ipsis.buildersguides.item.BGItems;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BGBlocks {

    public static void preInit() {

        blockTarget = new BlockTarget();

        blockAxisMarker = new BlockAxisMarker();
        blockCoordMarker = new BlockCoordMarker();
        blockLaserMarker = new BlockLaserMarker();
        blockSkyMarker = new BlockSkyMarker();

        blockRangeMarker = new BlockRangeMarker();
        blockMultiRangeMarker = new BlockMultiRangeMarker();
        blockTargetMarker = new BlockTargetMarker();
        blockMultiTargetMarker = new BlockMultiTargetMarker();

        blockAdvancedMarker = new BlockAdvancedMarker();

        GameRegistry.registerBlock(blockTarget, "block.target");

        GameRegistry.registerBlock(blockLaserMarker, "block.laserMarker");
        GameRegistry.registerBlock(blockAxisMarker, "block.axisMarker");
        GameRegistry.registerBlock(blockCoordMarker, "block.coordMarker");
        GameRegistry.registerBlock(blockSkyMarker, "block.skyMarker");

        GameRegistry.registerBlock(blockRangeMarker, "block.rangeMarker");
        GameRegistry.registerBlock(blockMultiRangeMarker, "block.multiRangeMarker");
        GameRegistry.registerBlock(blockTargetMarker, "block.targetMarker");
        GameRegistry.registerBlock(blockMultiTargetMarker, "block.multiTargetMarker");

        GameRegistry.registerBlock(blockAdvancedMarker, "block.advancedMarker");

    }

    public static void initialize() {

    }

    public static void postInit() {

        /* TODO Use the ore dictionary */
        ItemStack marker = new ItemStack(BGItems.itemMarker);

        GameRegistry.addShapelessRecipe(new ItemStack(blockAxisMarker), marker, new ItemStack(Items.stick), new ItemStack(Items.stick), new ItemStack(Items.stick));
        GameRegistry.addShapelessRecipe(new ItemStack(blockCoordMarker), marker, new ItemStack(Items.flint));
        GameRegistry.addShapelessRecipe(new ItemStack(blockLaserMarker), marker, new ItemStack(Items.redstone));
        GameRegistry.addShapelessRecipe(new ItemStack(blockSkyMarker), marker, new ItemStack(Items.feather));

        //GameRegistry.addShapelessRecipe(new ItemsStack(blockChunkMarker), marker, new ItemStack(Items.something));


        GameRegistry.addShapelessRecipe(new ItemStack(blockRangeMarker), marker, new ItemStack(Blocks.glass_pane));
        GameRegistry.addShapelessRecipe(new ItemStack(blockMultiRangeMarker), marker, new ItemStack(Blocks.glass));
        GameRegistry.addShapelessRecipe(new ItemStack(blockTargetMarker), marker, new ItemStack(Blocks.glass_pane), new ItemStack(Blocks.torch));
        GameRegistry.addShapelessRecipe(new ItemStack(blockMultiTargetMarker), marker, new ItemStack(Blocks.glass), new ItemStack(Blocks.torch));

        GameRegistry.addShapelessRecipe(new ItemStack(blockAdvancedMarker), marker, new ItemStack(Blocks.glass), new ItemStack(Blocks.redstone_torch));

        GameRegistry.addShapelessRecipe(new ItemStack(blockTarget), marker, new ItemStack(Items.arrow));

        /* Allow reversal */
        GameRegistry.addShapelessRecipe(marker, new ItemStack(blockTarget));

        GameRegistry.addShapelessRecipe(marker, new ItemStack(blockAxisMarker));
        GameRegistry.addShapelessRecipe(marker, new ItemStack(blockCoordMarker));
        GameRegistry.addShapelessRecipe(marker, new ItemStack(blockLaserMarker));
        GameRegistry.addShapelessRecipe(marker, new ItemStack(blockSkyMarker));

        GameRegistry.addShapelessRecipe(marker, new ItemStack(blockRangeMarker));
        GameRegistry.addShapelessRecipe(marker, new ItemStack(blockMultiRangeMarker));
        GameRegistry.addShapelessRecipe(marker, new ItemStack(blockTargetMarker));
        GameRegistry.addShapelessRecipe(marker, new ItemStack(blockMultiTargetMarker));
        GameRegistry.addShapelessRecipe(marker, new ItemStack(blockAdvancedMarker));
    }

    public static BlockBG blockTarget;

    public static BlockBG blockAxisMarker;
    public static BlockBG blockCoordMarker;
    public static BlockBG blockLaserMarker;
    public static BlockBG blockSkyMarker;

    public static BlockBG blockRangeMarker;
    public static BlockBG blockMultiRangeMarker;
    public static BlockBG blockTargetMarker;
    public static BlockBG blockMultiTargetMarker;

    public static BlockBG blockAdvancedMarker;

}
