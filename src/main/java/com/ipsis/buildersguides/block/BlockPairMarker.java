package com.ipsis.buildersguides.block;

import com.ipsis.buildersguides.tileentity.TileBGMarker;

public class BlockPairMarker extends BlockMarker {

    public BlockPairMarker() {

        super(TileBGMarker.Type.PAIR);
        setBlockName("pairMarker");
    }
}
