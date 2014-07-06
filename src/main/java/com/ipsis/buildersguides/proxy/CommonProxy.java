package com.ipsis.buildersguides.proxy;

import com.ipsis.buildersguides.tileentity.TileAxisMarker;
import com.ipsis.buildersguides.tileentity.TileCoordMarker;
import com.ipsis.buildersguides.tileentity.TileTargetMarker;
import cpw.mods.fml.common.registry.GameRegistry;

public abstract class CommonProxy implements IProxy {

    public void initTileEntities() {

        GameRegistry.registerTileEntity(TileCoordMarker.class, "tile.coordMarker");
        GameRegistry.registerTileEntity(TileAxisMarker.class, "tile.axisMarker");
        GameRegistry.registerTileEntity(TileTargetMarker.class, "tile.targetMarker");
    }
}
