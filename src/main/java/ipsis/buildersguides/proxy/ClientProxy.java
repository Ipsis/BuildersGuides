package ipsis.buildersguides.proxy;

import ipsis.buildersguides.render.*;
import ipsis.buildersguides.tileentity.*;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {

    public void initRenderingAndTexture() {

        ClientRegistry.bindTileEntitySpecialRenderer(TileBaseMarker.class, new MarkerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileCoordMarker.class, new CoordMarkerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileChunkMarker.class, new ChunkMarkerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileDireMarker.class, new DireMarkerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileAdvancedMarker.class, new AdvancedMarkerRenderer());

     }
}
