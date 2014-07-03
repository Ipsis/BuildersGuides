package com.ipsis.buildersguides.block;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class BGBlocks {

    public static void preInit() {

        blockAxesMarker = new BlockAxesMarker();
        blockPairMarker = new BlockPairMarker();

        GameRegistry.registerBlock(blockAxesMarker, "block.axesMarker");
        GameRegistry.registerBlock(blockPairMarker, "block.pairMarker");
    }

    public static void initialize() {

    }

    public static void postInit() {

    }

    public static Block blockAxesMarker;
    public static Block blockPairMarker;
}
