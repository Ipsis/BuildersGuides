package ipsis.buildersguides.manager;

import ipsis.buildersguides.item.ItemMallet;
import net.minecraft.util.MathHelper;

/**
 * Types
 *
 * AXIS - display three axes - can disable per side
 * CENTER - display odd/even centers
 * GHOST - display of configurable single, plane and cuboids
 * GHOSTSTAIRS - as above but stairs
 * LASER - display in facing direction
 * RANGE - displays range information
 * SPACING - display blocks spaced at intervals
 * WORLD - display full world height
 *
 */

public enum MarkerType {

    BLANK,
    AXIS,
    CENTER,
    GHOST,
    GHOSTSTAIRS,
    LASER,
    RANGE,
    SPACING,
    WORLD;

    public static final int count = MarkerType.values().length;

    public static MarkerType getMarkerType(int ordinal) {

        ordinal = MathHelper.clamp_int(ordinal, 0, MarkerType.count - 1);
        return MarkerType.values()[ordinal];
    }
}
