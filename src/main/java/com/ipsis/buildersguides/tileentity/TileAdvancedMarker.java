package com.ipsis.buildersguides.tileentity;

import com.ipsis.buildersguides.block.BlockTarget;
import com.ipsis.buildersguides.util.*;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileAdvancedMarker extends TileEntity implements IWrenchable {

    private Mode mode;
    private static final int MAX_DISTANCE = 64;
    private BlockPosition[] targets;

    public TileAdvancedMarker() {

        this.mode = Mode.HORIZ_PLANE;
        targets = new BlockPosition[ForgeDirection.VALID_DIRECTIONS.length];
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

    private void clearTarget(ForgeDirection dir) {

        targets[dir.ordinal()] = null;
    }

    private void clearTargets() {

        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
            clearTarget(dir);
    }

    @Override
    public void shiftLeftWrench() {

        /* change the mode */
        if (this.mode == Mode.HORIZ_PLANE)
            this.mode = Mode.VERT_PLANE;
        else if (this.mode == Mode.VERT_PLANE)
            this.mode = Mode.CUBOID;
        else
            this.mode = Mode.HORIZ_PLANE;

        this.findTargets();
    }

    public enum Mode {

        HORIZ_PLANE,
        VERT_PLANE,
        CUBOID
    }

    private void findTargetsHPlane() {

        boolean valid = false;
        TargetLocker locker = new TargetLocker();

        for (int i = 1; i < MAX_DISTANCE && !valid; i++) {
            for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {

                if (dir == ForgeDirection.DOWN || dir == ForgeDirection.UP)
                    continue;

                if (locker.isLocked(dir) || locker.isOppositeLocked(dir))
                    continue;

                BlockPosition bp = new BlockPosition(this.xCoord, this.yCoord, this.zCoord, dir);
                bp.moveForwards(i);
                Block b = worldObj.getBlock(bp.x, bp.y, bp.z);
                if (b instanceof BlockTarget) {
                    locker.lock(dir, bp.x, bp.y, bp.z);

                    if (locker.isValidXZPlane())
                        valid = true;
                }
            }
        }

        if (valid) {

            BlockPosition p;

            p = locker.getX();
            if (p != null)
                this.setTarget(p.orientation, p);

            p = locker.getY();
            if (p != null)
                this.setTarget(p.orientation, p);

            p = locker.getZ();
            if (p != null)
                this.setTarget(p.orientation, p);
        }
    }

    private void findTargetsOther() {

        boolean valid = false;
        TargetLocker locker = new TargetLocker();

        for (int i = 1; i < MAX_DISTANCE && !valid; i++) {
            for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {

                if (locker.isLocked(dir) || locker.isOppositeLocked(dir))
                    continue;

                if (this.mode == Mode.VERT_PLANE && ((locker.isXLocked() && DirectionHelper.isZ(dir)) || (locker.isZLocked() && DirectionHelper.isX(dir))))
                        continue;

                BlockPosition bp = new BlockPosition(this.xCoord, this.yCoord, this.zCoord, dir);
                bp.moveForwards(i);
                Block b = worldObj.getBlock(bp.x, bp.y, bp.z);
                if (b instanceof BlockTarget) {
                    locker.lock(dir, bp.x, bp.y, bp.z);

                    if (this.mode == Mode.VERT_PLANE && (locker.isValidXYPlane() || locker.isValidYZPlane()))
                        valid = true;
                    else if (this.mode == Mode.CUBOID && locker.isValidCuboid())
                        valid = true;
                }
            }
        }

        if (valid) {

            BlockPosition p;

            p = locker.getX();
            if (p != null)
                this.setTarget(p.orientation, p);

            p = locker.getY();
            if (p != null)
                this.setTarget(p.orientation, p);

            p = locker.getZ();
            if (p != null)
                this.setTarget(p.orientation, p);
        }
    }

    public void findTargets() {

        this.clearTargets();

        if (this.mode == Mode.HORIZ_PLANE)
            findTargetsHPlane();
        else if (this.mode == Mode.VERT_PLANE)
            findTargetsOther();
        else if (this.mode == Mode.CUBOID)
            findTargetsOther();

        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            BlockPosition p = this.getTarget(dir);
            if (p != null)
                LogHelper.info("Found target " + p);
        }
    }

}
