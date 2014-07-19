package com.ipsis.buildersguides.tileentity;

import com.ipsis.buildersguides.util.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileAxisMarker extends TileMultiMarker implements IWrenchable {

    public TileAxisMarker() {

        super(false);
        mode = BGAxis.X_Y_Z;
    }

    private BGAxis mode;

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

    @Override
    public BlockPosition getTarget(ForgeDirection dir) {

        if (!this.mode.isValidDir(this.mode, dir))
            return null;

        return super.getTarget(dir);
    }

    @Override
    public void shiftLeftWrench() {

        /* Change the axis */
        this.mode = this.mode.getNext();
        worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {

        super.readFromNBT(nbttagcompound);

        this.mode = BGAxis.getAxis(nbttagcompound.getInteger("Mode"));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {

        super.writeToNBT(nbttagcompound);

        nbttagcompound.setInteger("Mode", this.mode.ordinal());
    }
}
