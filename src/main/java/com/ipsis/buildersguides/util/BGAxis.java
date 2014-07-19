package com.ipsis.buildersguides.util;

import net.minecraftforge.common.util.ForgeDirection;

public enum BGAxis {

    X_Y_Z,
    X_Y,
    X_Z,
    Y_Z,
    X,
    Y,
    Z;

    public static BGAxis[] VALID_AXIS_MODES = { X_Y_Z, X_Y, X_Z, Y_Z, X, Y, Z };

    public BGAxis getNext() {

        int next = ordinal();
        next++;
        if (next < 0 || next >= VALID_AXIS_MODES.length)
            return VALID_AXIS_MODES[0];

        return VALID_AXIS_MODES[next];
    }

    public static BGAxis getAxis(int i) {

        if (i < 0 || i >= VALID_AXIS_MODES.length)
            return X_Y_Z;

        return VALID_AXIS_MODES[i];
    }

    public static boolean hasX(BGAxis axis) {

        if (axis == X_Y_Z || axis == X_Y || axis == X_Z || axis == X)
            return true;

        return false;
    }

    public static boolean hasY(BGAxis axis) {

        if (axis == X_Y_Z || axis == X_Y || axis == Y_Z || axis == Y)
            return true;

        return false;
    }

    public static boolean hasZ(BGAxis axis) {

        if (axis == X_Y_Z || axis == X_Z || axis == Y_Z || axis == Z)
            return true;

        return false;
    }

    private static boolean isY(ForgeDirection dir) {

        if (dir == ForgeDirection.UP || dir == ForgeDirection.DOWN)
            return true;

        return false;
    }

    private static boolean isZ(ForgeDirection dir) {

        if (dir == ForgeDirection.NORTH || dir == ForgeDirection.SOUTH)
            return true;

        return false;
    }

    private static boolean isX(ForgeDirection dir) {

        if (dir == ForgeDirection.EAST || dir == ForgeDirection.WEST)
            return true;

        return false;
    }

    public static boolean isValidDir(BGAxis axis, ForgeDirection dir) {

        if (hasX(axis) && isX(dir))
            return true;

        if (hasY(axis) && isY(dir))
            return true;

        if (hasZ(axis) && isZ(dir))
            return true;

        return false;
    }
}
