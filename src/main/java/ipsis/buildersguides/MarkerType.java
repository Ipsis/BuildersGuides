package ipsis.buildersguides;

import net.minecraft.util.MathHelper;

public enum MarkerType {

    BLANK,
    AXIS,
    LASER,
    WORLD,
    SPACING,
    RANGE;

    public static final int count = MarkerType.values().length;

    public static MarkerType getMarkerType(int ordinal) {

        ordinal = MathHelper.clamp_int(ordinal, 0, MarkerType.count - 1);
        return MarkerType.values()[ordinal];
    }
}
