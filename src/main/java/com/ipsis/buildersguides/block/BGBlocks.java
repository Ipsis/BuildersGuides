package com.ipsis.buildersguides.block;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class BGBlocks {

    public static void preInit() {

        blockAxesMarker = new BlockAxesMarker();
        blockPairMarker = new BlockPairMarker();
        blockCoordMarker = new BlockCoordMarker();

        GameRegistry.registerBlock(blockAxesMarker, "block.axesMarker");
        GameRegistry.registerBlock(blockPairMarker, "block.pairMarker");
        GameRegistry.registerBlock(blockCoordMarker, "block.coordMarker");
    }

    public static void initialize() {

    }

    public static void postInit() {

    }

    public static Block blockAxesMarker;
    public static Block blockPairMarker;
    public static Block blockCoordMarker;
}
