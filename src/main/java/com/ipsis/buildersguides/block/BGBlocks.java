package com.ipsis.buildersguides.block;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class BGBlocks {

    public static void preInit() {

        blockAxisMarker = new BlockAxisMarker();
        blockTargetMarker = new BlockTargetMarker();
        blockLaserMarker = new BlockLaserMarker();
        blockCoordMarker = new BlockCoordMarker();
        blockRangeMarker = new BlockRangeMarker();

        GameRegistry.registerBlock(blockAxisMarker, "block.axesMarker");
        GameRegistry.registerBlock(blockTargetMarker, "block.targetMarker");
        GameRegistry.registerBlock(blockLaserMarker, "block.laserMarker");
        GameRegistry.registerBlock(blockCoordMarker, "block.coordMarker");
        GameRegistry.registerBlock(blockRangeMarker, "block.rangeMarker");
    }

    public static void initialize() {

    }

    public static void postInit() {

    }

    public static BlockBG blockAxisMarker;
    public static BlockBG blockTargetMarker;
    public static BlockBG blockLaserMarker;
    public static BlockBG blockCoordMarker;
    public static BlockBG blockRangeMarker;
}
