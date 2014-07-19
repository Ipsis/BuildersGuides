package com.ipsis.buildersguides.tileentity;

import com.ipsis.buildersguides.util.BlockPosition;
import com.ipsis.buildersguides.util.BlockUtils;
import net.minecraftforge.common.util.ForgeDirection;

public class TileAxisMarker extends TileMultiMarker {

    public TileAxisMarker() {

        super(false);
    }

    @Override
    public BlockPosition getCenter(ForgeDirection dir) {

        return null;
    }

    @Override
    protected void setCenter(ForgeDirection dir, BlockPosition pos) {

        /* NOOP */
    }

    @Override
    public boolean hasCenter() {

        return false;
    }

    @Override
    public boolean hasCenter(ForgeDirection dir) {

        return false;
    }

    public void findTargets() {

        setTarget(ForgeDirection.DOWN, new BlockPosition(this.xCoord, this.yCoord - this.MAX_DISTANCE, this.zCoord));
        setTarget(ForgeDirection.UP, new BlockPosition(this.xCoord, this.yCoord + this.MAX_DISTANCE, this.zCoord));
        setTarget(ForgeDirection.EAST, new BlockPosition(this.xCoord + this.MAX_DISTANCE, this.yCoord, this.zCoord));
        setTarget(ForgeDirection.WEST, new BlockPosition(this.xCoord - this.MAX_DISTANCE, this.yCoord, this.zCoord));
        setTarget(ForgeDirection.SOUTH, new BlockPosition(this.xCoord, this.yCoord, this.zCoord + this.MAX_DISTANCE));
        setTarget(ForgeDirection.NORTH, new BlockPosition(this.xCoord, this.yCoord, this.zCoord - this.MAX_DISTANCE));

        worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }
}
