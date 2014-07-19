package com.ipsis.buildersguides.tileentity;

import com.ipsis.buildersguides.util.BlockPosition;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Ipsis on 16/07/2014.
 */
public class TileSkyMarker extends TileMultiMarker {

    public TileSkyMarker() {

        super(false);
    }

    @Override
    public void findTargets() {

        /* TODO this should be to world height and world depth */
        BlockPosition b = new BlockPosition(this.xCoord, this.yCoord, this.zCoord, ForgeDirection.UP);
        b.moveForwards(this.MAX_DISTANCE);
        setTarget(ForgeDirection.UP, b);

        b.moveBackwards(this.MAX_DISTANCE);
        b.orientation = ForgeDirection.DOWN;
        b.moveForwards(this.MAX_DISTANCE);
        setTarget(ForgeDirection.DOWN, b);
    }

    @Override
    protected void findCenter(ForgeDirection dir) {

        /* NOOP */
    }
}
