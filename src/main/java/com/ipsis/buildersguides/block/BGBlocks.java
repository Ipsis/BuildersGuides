package com.ipsis.buildersguides.block;

import com.ipsis.buildersguides.item.BGItems;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.List;

public class BGBlocks {

    private static List<Item> validMarkers = new ArrayList<Item>();

    public static boolean isValidMarker(ItemStack itemStack) {
        if (itemStack == null) return false;

        return validMarkers.contains(itemStack.getItem());
    }

    public static void preInit() {

        blockTarget = new BlockTarget();

        blockAxisMarker = new BlockAxisMarker();
        blockCoordMarker = new BlockCoordMarker();
        blockLaserMarker = new BlockLaserMarker();
        blockSkyMarker = new BlockSkyMarker();
        blockChunkMarker = new BlockChunkMarker();
        blockDireMarker = new BlockDireMarker();
        blockCenterMarker = new BlockCenterMarker();

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
        GameRegistry.registerBlock(blockChunkMarker, "block.chunkMarker");
        GameRegistry.registerBlock(blockDireMarker, "block.direMarker");
        GameRegistry.registerBlock(blockCenterMarker, "block.centerMarker");

        GameRegistry.registerBlock(blockRangeMarker, "block.rangeMarker");
        GameRegistry.registerBlock(blockMultiRangeMarker, "block.multiRangeMarker");
        GameRegistry.registerBlock(blockTargetMarker, "block.targetMarker");
        GameRegistry.registerBlock(blockMultiTargetMarker, "block.multiTargetMarker");

        GameRegistry.registerBlock(blockAdvancedMarker, "block.advancedMarker");

        validMarkers.add(Item.getItemFromBlock(blockLaserMarker));
        validMarkers.add(Item.getItemFromBlock(blockAxisMarker));
        validMarkers.add(Item.getItemFromBlock(blockCoordMarker));
        validMarkers.add(Item.getItemFromBlock(blockSkyMarker));
        validMarkers.add(Item.getItemFromBlock(blockChunkMarker));
        validMarkers.add(Item.getItemFromBlock(blockDireMarker));
        validMarkers.add(Item.getItemFromBlock(blockCenterMarker));
        validMarkers.add(Item.getItemFromBlock(blockRangeMarker));
        validMarkers.add(Item.getItemFromBlock(blockMultiRangeMarker));
        validMarkers.add(Item.getItemFromBlock(blockTargetMarker));
        validMarkers.add(Item.getItemFromBlock(blockMultiTargetMarker));
        validMarkers.add(Item.getItemFromBlock(blockAdvancedMarker));
    }

    public static void initialize() {

        ItemStack marker = new ItemStack(BGItems.itemMarker);

        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(blockAxisMarker), marker, "stickWood", "stickWood", "stickWood"));
        GameRegistry.addShapelessRecipe(new ItemStack(blockCoordMarker), marker, new ItemStack(Items.flint));
        GameRegistry.addShapelessRecipe(new ItemStack(blockLaserMarker), marker, new ItemStack(Items.redstone));
        GameRegistry.addShapelessRecipe(new ItemStack(blockSkyMarker), marker, new ItemStack(Items.feather));
        GameRegistry.addShapelessRecipe(new ItemStack(blockChunkMarker), marker, new ItemStack(Blocks.fence));
        GameRegistry.addShapelessRecipe(new ItemStack(blockDireMarker), marker, new ItemStack(Blocks.cobblestone));
        GameRegistry.addShapelessRecipe(new ItemStack(blockCenterMarker), marker, new ItemStack(Blocks.stone));

        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(blockRangeMarker), marker, "paneGlass"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(blockMultiRangeMarker), marker, "blockGlass"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(blockTargetMarker), marker, "paneGlass", new ItemStack(Blocks.torch)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(blockMultiTargetMarker), marker, "blockGlass", new ItemStack(Blocks.torch)));

        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(blockAdvancedMarker), marker, "blockGlass", new ItemStack(Blocks.redstone_torch)));
        GameRegistry.addShapelessRecipe(new ItemStack(blockTarget), marker, new ItemStack(Items.arrow));


        /* Allow reversal */
        GameRegistry.addShapelessRecipe(marker, new ItemStack(blockTarget));

        GameRegistry.addShapelessRecipe(marker, new ItemStack(blockAxisMarker));
        GameRegistry.addShapelessRecipe(marker, new ItemStack(blockCoordMarker));
        GameRegistry.addShapelessRecipe(marker, new ItemStack(blockLaserMarker));
        GameRegistry.addShapelessRecipe(marker, new ItemStack(blockSkyMarker));
        GameRegistry.addShapelessRecipe(marker, new ItemStack(blockChunkMarker));
        GameRegistry.addShapelessRecipe(marker, new ItemStack(blockDireMarker));
        GameRegistry.addShapelessRecipe(marker, new ItemStack(blockCenterMarker));

        GameRegistry.addShapelessRecipe(marker, new ItemStack(blockRangeMarker));
        GameRegistry.addShapelessRecipe(marker, new ItemStack(blockMultiRangeMarker));
        GameRegistry.addShapelessRecipe(marker, new ItemStack(blockTargetMarker));
        GameRegistry.addShapelessRecipe(marker, new ItemStack(blockMultiTargetMarker));
        GameRegistry.addShapelessRecipe(marker, new ItemStack(blockAdvancedMarker));
    }

    public static void postInit() {

    }

    public static BlockBG blockTarget;

    public static BlockBG blockAxisMarker;
    public static BlockBG blockCoordMarker;
    public static BlockBG blockLaserMarker;
    public static BlockBG blockSkyMarker;
    public static BlockBG blockChunkMarker;
    public static BlockBG blockDireMarker;
    public static BlockBG blockCenterMarker;

    public static BlockBG blockRangeMarker;
    public static BlockBG blockMultiRangeMarker;
    public static BlockBG blockTargetMarker;
    public static BlockBG blockMultiTargetMarker;

    public static BlockBG blockAdvancedMarker;

}
