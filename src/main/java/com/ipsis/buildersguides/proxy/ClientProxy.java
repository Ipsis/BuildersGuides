package com.ipsis.buildersguides.proxy;

import com.ipsis.buildersguides.render.CoordMarkerRenderer;
import com.ipsis.buildersguides.render.MarkerRenderer;
import com.ipsis.buildersguides.tileentity.*;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {

    public void initRenderingAndTexture() {

        /* TODO Do I only need 1 renderer */
        ClientRegistry.bindTileEntitySpecialRenderer(TileCoordMarker.class, new CoordMarkerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileAxisMarker.class, new MarkerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileLaserMarker.class, new MarkerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileSkyMarker.class, new MarkerRenderer());

        ClientRegistry.bindTileEntitySpecialRenderer(TileRangeMarker.class, new MarkerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileMultiRangeMarker.class, new MarkerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileTargetMarker.class, new MarkerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileMultiTargetMarker.class, new MarkerRenderer());
     }
}
