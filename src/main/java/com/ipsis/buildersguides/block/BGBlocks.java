package com.ipsis.buildersguides.block;

import cpw.mods.fml.common.registry.GameRegistry;

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
