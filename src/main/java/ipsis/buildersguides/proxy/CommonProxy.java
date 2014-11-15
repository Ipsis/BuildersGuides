package ipsis.buildersguides.proxy;

import ipsis.buildersguides.tileentity.*;
import cpw.mods.fml.common.registry.GameRegistry;

public abstract class CommonProxy implements IProxy {

    public void initTileEntities() {

        GameRegistry.registerTileEntity(TileCoordMarker.class, "tile.coordMarker");
        GameRegistry.registerTileEntity(TileAxisMarker.class, "tile.axisMarker");
        GameRegistry.registerTileEntity(TileLaserMarker.class, "tile.laserMarker");
        GameRegistry.registerTileEntity(TileSkyMarker.class, "tile.skyMarker");
        GameRegistry.registerTileEntity(TileChunkMarker.class, "tile.chunkMarker");
        GameRegistry.registerTileEntity(TileDireMarker.class, "tile.direMarker");
        GameRegistry.registerTileEntity(TileCenterMarker.class, "tile.centerMarker");

        GameRegistry.registerTileEntity(TileRangeMarker.class, "tile.rangeMarker");
        GameRegistry.registerTileEntity(TileMultiRangeMarker.class, "tile.multiRangeMarker");
        GameRegistry.registerTileEntity(TileTargetMarker.class, "tile.targetMarker");
        GameRegistry.registerTileEntity(TileMultiTargetMarker.class, "tile.multiTargetMarker");

        GameRegistry.registerTileEntity(TileAdvancedMarker.class, "tile.advancedMarker");
    }
}
