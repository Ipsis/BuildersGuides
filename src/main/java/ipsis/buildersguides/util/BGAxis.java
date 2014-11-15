package ipsis.buildersguides.util;

import net.minecraftforge.common.util.ForgeDirection;

public enum BGAxis {

    X_Y_Z("X,Y,Z"),
    X_Y("X,Y"),
    X_Z("X,Z"),
    Y_Z("Y,Z"),
    X("X"),
    Y("Y"),
    Z("Z");

    private final String value;
    BGAxis(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

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

    public static boolean isValidDir(BGAxis axis, ForgeDirection dir) {

        if (hasX(axis) && DirectionHelper.isX(dir))
            return true;

        if (hasY(axis) && DirectionHelper.isY(dir))
            return true;

        if (hasZ(axis) && DirectionHelper.isZ(dir))
            return true;

        return false;
    }
}
