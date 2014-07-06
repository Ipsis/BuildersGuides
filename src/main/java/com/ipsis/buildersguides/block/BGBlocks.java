package com.ipsis.buildersguides.block;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class BGBlocks {

    public static void preInit() {

        blockAxisMarker = new BlockAxisMarker();
        blockTargetMarker = new BlockTargetMarker();
        blockCoordMarker = new BlockCoordMarker();

        GameRegistry.registerBlock(blockAxisMarker, "block.axesMarker");
        GameRegistry.registerBlock(blockTargetMarker, "block.targetMarker");
        GameRegistry.registerBlock(blockCoordMarker, "block.coordMarker");
    }

    public static void initialize() {

    }

    public static void postInit() {

    }

    public static BlockBG blockAxisMarker;
    public static BlockBG blockTargetMarker;
    public static BlockBG blockCoordMarker;
}
