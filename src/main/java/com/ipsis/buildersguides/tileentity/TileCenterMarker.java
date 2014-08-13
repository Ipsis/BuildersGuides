package com.ipsis.buildersguides.tileentity;

import com.ipsis.buildersguides.util.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;

public class TileCenterMarker extends TileMultiMarker {

    public TileCenterMarker() {

        super(false);
    }

    @Override
    protected void findCenter(ForgeDirection dir) {

        return;
    }

    @Override
    public void findCenters() {

        BlockPosition cx = null;
        BlockPosition cy = null;
        BlockPosition cz = null;

        if (hasTarget(ForgeDirection.EAST) && hasTarget(ForgeDirection.WEST)) {
            BlockPosition c;
            BlockPosition t1 = getTarget(ForgeDirection.WEST);
            BlockPosition t2 = getTarget(ForgeDirection.EAST);
            c = BlockUtils.getCenterBlock(t1.x, t1.y, t1.z, t2.x, t2.y, t2.z, t2.orientation);
            if (c != null)
                setCenter(ForgeDirection.EAST, c);
        }

        if (hasTarget(ForgeDirection.UP) && hasTarget(ForgeDirection.DOWN)) {
            BlockPosition c;
            BlockPosition t1 = getTarget(ForgeDirection.DOWN);
            BlockPosition t2 = getTarget(ForgeDirection.UP);
            c = BlockUtils.getCenterBlock(t1.x, t1.y, t1.z, t2.x, t2.y, t2.z, t2.orientation);
            if (c != null)
                setCenter(ForgeDirection.UP, c);
        }

        if (hasTarget(ForgeDirection.NORTH) && hasTarget(ForgeDirection.SOUTH)) {
            BlockPosition c;
            BlockPosition t1 = getTarget(ForgeDirection.NORTH);
            BlockPosition t2 = getTarget(ForgeDirection.SOUTH);
            c = BlockUtils.getCenterBlock(t1.x, t1.y, t1.z, t2.x, t2.y, t2.z, t2.orientation);
            if (c != null)
                setCenter(ForgeDirection.NORTH, c);
        }
    }

    @Override
    public void findTargets(EntityPlayer player) {

        clearTargets();
        clearCenters();
        for (ForgeDirection d : ForgeDirection.VALID_DIRECTIONS) {

            if (!isValidDirection(d))
                continue;

            BlockPosition b = BlockUtils.getFirstBlock(0,
                    worldObj, this.xCoord, this.yCoord, this.zCoord,
                    d, MAX_DISTANCE, this.useTargetBlock);

            if (b != null) {
                setTarget(d, b);
                ChatHelper.displayTargetMessage(player, b, d, this.useTargetBlock);
            }
        }

        findCenters();

        worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }

    @Override
    public void doSneakWrench(EntityPlayer player) {
        findTargets(player);
    }
}
