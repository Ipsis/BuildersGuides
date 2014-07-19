package com.ipsis.buildersguides.tileentity;

import net.minecraftforge.common.util.ForgeDirection;

public class TileRangeMarker extends TileBaseMarker {

    public TileRangeMarker() {

        super(false);
    }

    @Override
    public void rotateAround(ForgeDirection axis) {

        setFacing(getFacing().getRotation(axis));
        findTargets();
    }
}
