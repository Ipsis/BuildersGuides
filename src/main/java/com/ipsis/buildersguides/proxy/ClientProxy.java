package com.ipsis.buildersguides.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import com.ipsis.buildersguides.tileentity.TileBGMarker;
import com.ipsis.buildersguides.render.BGMarkerRenderer;

public class ClientProxy extends CommonProxy {

    public void initRenderingAndTexture() {

        ClientRegistry.bindTileEntitySpecialRenderer(TileBGMarker.class, new BGMarkerRenderer());
    }
}
