package com.ipsis.buildersguides.proxy;

import com.ipsis.buildersguides.render.AdvancedMarkerRenderer;
import com.ipsis.buildersguides.render.ChunkMarkerRenderer;
import com.ipsis.buildersguides.render.CoordMarkerRenderer;
import com.ipsis.buildersguides.render.MarkerRenderer;
import com.ipsis.buildersguides.tileentity.*;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {

    public void initRenderingAndTexture() {

        ClientRegistry.bindTileEntitySpecialRenderer(TileBaseMarker.class, new MarkerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileCoordMarker.class, new CoordMarkerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileChunkMarker.class, new ChunkMarkerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileAdvancedMarker.class, new AdvancedMarkerRenderer());

     }
}
