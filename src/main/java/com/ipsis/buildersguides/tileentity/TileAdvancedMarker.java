package com.ipsis.buildersguides.tileentity;

import com.ipsis.buildersguides.block.BlockTarget;
import com.ipsis.buildersguides.util.BlockPosition;
import com.ipsis.buildersguides.util.BlockUtils;
import com.ipsis.buildersguides.util.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileAdvancedMarker extends TileEntity {

    private Mode mode;
    private static final int MAX_DISTANCE = 64;
    private boolean locked[];
    private BlockPosition[] targets;

    public TileAdvancedMarker() {

        this.mode = Mode.HORIZ_PLANE;
        this.locked = new boolean[ForgeDirection.VALID_DIRECTIONS.length];
        targets = new BlockPosition[ForgeDirection.VALID_DIRECTIONS.length];
        clearLocked();
    }

    /**
     * Accessors
     */
    public BlockPosition getTarget(ForgeDirection dir) {

        return targets[dir.ordinal()];
    }

    protected void setTarget(ForgeDirection dir, BlockPosition pos) {

        if (targets[dir.ordinal()] == null) {
            targets[dir.ordinal()] = new BlockPosition(pos);
        } else {
            targets[dir.ordinal()].x = pos.x;
            targets[dir.ordinal()].y = pos.y;
            targets[dir.ordinal()].z = pos.z;
            targets[dir.ordinal()].orientation = pos.orientation;
        }
    }

    private void clearLocked() {

        for (int i = 0; i < this.locked.length; i++)
            this.locked[i] = false;
    }

    private boolean isLocked(ForgeDirection dir) {

        return this.locked[dir.ordinal()];
    }

    private void setLocked(ForgeDirection dir) {

        this.locked[dir.ordinal()] = true;
    }

    private void setUnlocked(ForgeDirection dir) {

        this.locked[dir.ordinal()] = false;
    }

    public enum Mode {

        HORIZ_PLANE,
        VERT_PLANE,
        CUBOID;
    }

    private void findTargetsHPlane() {

        /* Expand outwards in the x and z axes */
        for (int i = 1; i < MAX_DISTANCE; i++) {
            for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {

                /* skip if this or the opposite direction has already been found */
                if (isLocked(dir) || isLocked(dir.getOpposite()))
                    continue;

                BlockPosition bp = new BlockPosition(this.xCoord, this.yCoord, this.zCoord, dir);
                bp.moveForwards(i);
                Block b = worldObj.getBlock(bp.x, bp.y, bp.z);
                if (b instanceof BlockTarget) {

                    setLocked(dir);
                    setTarget(dir, bp);
                }
            }
        }
    }

    private void findTargetsVPlane() {

    }

    private void findTargetsCuboid() {

    }

    public void findTargets() {

        if (this.mode == Mode.HORIZ_PLANE)
            findTargetsHPlane();
        else if (this.mode == Mode.VERT_PLANE)
            findTargetsVPlane();
        else if (this.mode == Mode.CUBOID)
            findTargetsCuboid();
    }
}
