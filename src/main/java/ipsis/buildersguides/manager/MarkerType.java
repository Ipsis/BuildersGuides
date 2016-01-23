package ipsis.buildersguides.manager;

import ipsis.buildersguides.block.BlockMarker;
import ipsis.buildersguides.reference.Names;
import ipsis.buildersguides.util.StringHelper;
import net.minecraft.util.MathHelper;

public enum MarkerType {

    BLANK,
    AXIS,
    CENTER,
    CHUNK,
    GHOST,
    GHOSTSTAIRS,
    /* LASER, */
    RANGE,
    SPACING,
    WORLD;

    public static final int count = MarkerType.values().length;

    public static MarkerType getMarkerType(int ordinal) {

        ordinal = MathHelper.clamp_int(ordinal, 0, MarkerType.count - 1);
        return MarkerType.values()[ordinal];
    }

    public MarkerType getNext() {

        int next = ordinal();
        next++;

        if (next < 0 || next >= count)
            next = 0;

        return values()[next];
    }

    public String getTranslatedType() {
        return StringHelper.localize(Names.NAME, BlockMarker.BASENAME + "." + this.toString());
    }
}
