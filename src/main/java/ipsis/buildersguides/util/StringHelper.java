package ipsis.buildersguides.util;

import ipsis.buildersguides.reference.Reference;
import net.minecraft.util.StatCollector;

public class StringHelper {

    public static String localize(String prefix, String key) {
        return StatCollector.translateToLocal(prefix + "." + Reference.MOD_ID + ":" + key);
    }
}
