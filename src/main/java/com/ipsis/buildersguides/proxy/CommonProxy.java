package com.ipsis.buildersguides.proxy;

import com.ipsis.buildersguides.tileentity.TileBGMarker;
import cpw.mods.fml.common.registry.GameRegistry;

public abstract class CommonProxy implements IProxy {

    public void initTileEntities() {

        GameRegistry.registerTileEntity(TileBGMarker.class, "tile.bgMarker");
    }
}
