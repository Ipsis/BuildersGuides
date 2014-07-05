package com.ipsis.buildersguides.block;

import com.ipsis.buildersguides.tileentity.TileBGMarker;

public class BlockCoordMarker extends BlockMarker {

    public BlockCoordMarker() {

        super(TileBGMarker.Type.COORD);
        setBlockName("coordMarker");
    }

    @Override
    public boolean isOpaqueCube() {

        return false;
    }


}
