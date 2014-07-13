package com.ipsis.buildersguides.proxy;

import com.ipsis.buildersguides.render.AxisMarkerRenderer;
import com.ipsis.buildersguides.render.CoordMarkerRenderer;
import com.ipsis.buildersguides.render.LaserMarkerRenderer;
import com.ipsis.buildersguides.tileentity.TileAxisMarker;
import com.ipsis.buildersguides.tileentity.TileCoordMarker;
import com.ipsis.buildersguides.tileentity.TileLaserMarker;
import com.ipsis.buildersguides.tileentity.TileRangeMarker;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {

    public void initRenderingAndTexture() {

        ClientRegistry.bindTileEntitySpecialRenderer(TileCoordMarker.class, new CoordMarkerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileAxisMarker.class, new AxisMarkerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileLaserMarker.class, new LaserMarkerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileRangeMarker.class, new LaserMarkerRenderer());
    }
}
