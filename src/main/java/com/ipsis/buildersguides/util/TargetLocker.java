package com.ipsis.buildersguides.util;

import net.minecraftforge.common.util.ForgeDirection;

public class TargetLocker {

    private boolean isValid;
    private boolean[] locked;
    private BlockPosition[] positions;

    public TargetLocker() {

        isValid = false;
        locked = new boolean[ForgeDirection.VALID_DIRECTIONS.length];
        positions = new BlockPosition[ForgeDirection.VALID_DIRECTIONS.length];
    }

    public void lock(ForgeDirection dir, int x, int y, int z) {

        this.locked[dir.ordinal()] = true;

        if (this.positions[dir.ordinal()] == null) {
            this.positions[dir.ordinal()] = new BlockPosition(x, y, z, dir);
        } else {
            this.positions[dir.ordinal()].x = x;
            this.positions[dir.ordinal()].y = y;
            this.positions[dir.ordinal()].z = z;
            this.positions[dir.ordinal()].orientation = dir;
        }
    }

    public boolean isLocked(ForgeDirection dir) {

        return this.locked[dir.ordinal()];
    }

    public boolean isOppositeLocked(ForgeDirection dir) {

        return this.locked[dir.getOpposite().ordinal()];
    }

    public boolean isXLocked() {

        return this.isLocked(ForgeDirection.EAST) || this.isLocked(ForgeDirection.WEST);
    }

    public boolean isYLocked() {

        return this.isLocked(ForgeDirection.UP) || this.isLocked(ForgeDirection.DOWN);
    }

    public boolean isZLocked() {

        return this.isLocked(ForgeDirection.SOUTH) || this.isLocked(ForgeDirection.NORTH);
    }

    public boolean isValidXZPlane() {

        return this.isXLocked() && this.isZLocked();
    }

    public boolean isValidXYPlane() {

        return this.isXLocked() && this.isYLocked();
    }

    public boolean isValidYZPlane() {

        return this.isYLocked() && this.isZLocked();
    }

    public boolean isValidCuboid() {

        return this.isXLocked() && isYLocked() && isZLocked();
    }

    public BlockPosition getX() {

        if (this.isXLocked()) {

            if (this.isLocked(ForgeDirection.EAST))
                return this.positions[ForgeDirection.EAST.ordinal()].copy();
            else if (this.isLocked(ForgeDirection.WEST))
                return this.positions[ForgeDirection.WEST.ordinal()].copy();
        }

        return null;
    }

    public BlockPosition getY() {

        if (this.isYLocked()) {

            if (this.isLocked(ForgeDirection.UP))
                return this.positions[ForgeDirection.UP.ordinal()].copy();
            else if (this.isLocked(ForgeDirection.DOWN))
                return this.positions[ForgeDirection.DOWN.ordinal()].copy();
        }

        return null;
    }

    public BlockPosition getZ() {

        if (this.isZLocked()) {

            if (this.isLocked(ForgeDirection.SOUTH))
                return this.positions[ForgeDirection.SOUTH.ordinal()].copy();
            else if (this.isLocked(ForgeDirection.NORTH))
                return this.positions[ForgeDirection.NORTH.ordinal()].copy();
        }

        return null;
    }
}
