package com.ipsis.buildersguides.tileentity;

import com.ipsis.buildersguides.util.BlockPosition;
import net.minecraftforge.common.util.ForgeDirection;

public class TileMultiMarker extends TileBaseMarker {

    private BlockPosition[] targets;
    private BlockPosition[] centers;

    public TileMultiMarker(boolean useTargetBlock) {

        super(useTargetBlock);
        targets = new BlockPosition[ForgeDirection.VALID_DIRECTIONS.length];
        centers = new BlockPosition[ForgeDirection.VALID_DIRECTIONS.length];
    }

    /**
     * Accessors
     */

    @Override
    public BlockPosition getTarget(ForgeDirection dir) {

        return targets[dir.ordinal()];
    }

    @Override
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

    @Override
    public BlockPosition getCenter(ForgeDirection dir) {

        return centers[dir.ordinal()];
    }

    @Override
    protected void setCenter(ForgeDirection dir, BlockPosition pos) {

        if (centers[dir.ordinal()] == null) {
            centers[dir.ordinal()] = new BlockPosition(pos);
        } else {
            centers[dir.ordinal()].x = pos.x;
            centers[dir.ordinal()].y = pos.y;
            centers[dir.ordinal()].z = pos.z;
            centers[dir.ordinal()].orientation = pos.orientation;
        }
    }

    @Override
    public boolean hasTarget() {

        for (int i = 0; i < targets.length; i++) {

            if (targets[i] != null)
                return true;
        }

        return false;
    }

    @Override
    public boolean hasTarget(ForgeDirection dir) {

        return targets[dir.ordinal()] != null;
    }

    @Override
    public boolean hasCenter() {

        for (int i = 0; i < centers.length; i++) {

            if (centers[i] != null)
                return true;
        }

        return false;
    }

    @Override
    public boolean hasCenter(ForgeDirection dir) {

        return centers[dir.ordinal()] != null;
    }

    /**
     * Clear
     */
    @Override
    protected void clearCenter(ForgeDirection dir) {

        centers[dir.ordinal()] = null;
    }

    @Override
    protected void clearTarget(ForgeDirection dir) {

        targets[dir.ordinal()] = null;
    }

    /**
     * Find
     */
    @Override
    public boolean isValidDirection(ForgeDirection dir) {

        return true;
    }
}
