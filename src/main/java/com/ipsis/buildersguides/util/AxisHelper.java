package com.ipsis.buildersguides.util;

public class AxisHelper {

    public static enum Axis {
        X_Y_Z,
        X_Y,
        X_Z,
        Y_Z,
        X,
        Y,
        Z;

        public static Axis[] VALID_AXIS_MODES = {X_Y_Z, X_Y, X_Z, Y_Z, X, Y, Z};

        public Axis getNext() {

            int next = ordinal();
            next++;
            if (next < 0 || next >= VALID_AXIS_MODES.length)
                return VALID_AXIS_MODES[0];

            return VALID_AXIS_MODES[next];
        }

        public Axis getAxis(int i) {

            if (i < 0 || i >= VALID_AXIS_MODES.length)
                return X_Y_Z;

            return VALID_AXIS_MODES[i];
        }

        public static boolean hasX(Axis axis) {

            if (axis == X_Y_Z || axis == X_Y || axis == X_Z || axis == X)
                return true;

            return false;
        }

        public static boolean hasY(Axis axis) {

            if (axis == X_Y_Z || axis == X_Y || axis == Y_Z || axis == Y)
                return true;

            return false;
        }

        public static boolean hasZ(Axis axis) {

            if (axis == X_Y_Z || axis == X_Z || axis == Y_Z || axis == Z)
                return true;

            return false;
        }
    }
}
