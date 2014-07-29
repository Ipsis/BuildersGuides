package com.ipsis.buildersguides.tileentity;

import com.ipsis.buildersguides.util.BlockPosition;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;

public class TileLaserMarker extends TileBaseMarker {

    public TileLaserMarker() {

        super(false);
    }

    @Override
    public void findTargets(EntityPlayer player) {

        BlockPosition b = new BlockPosition(this.xCoord, this.yCoord, this.zCoord, this.getFacing());
        b.moveForwards(this.MAX_DISTANCE);
        setTarget(this.getFacing(), b);
        worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }

    @Override
    protected void findCenter(ForgeDirection dir) {

        /* NOOP */
    }

    @Override
    public void rotateAround(ForgeDirection axis, EntityPlayer player) {

        setFacing(getFacing().getRotation(axis));
        findTargets(player);
    }
}
