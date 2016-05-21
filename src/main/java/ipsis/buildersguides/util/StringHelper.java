package ipsis.buildersguides.util;

import ipsis.buildersguides.reference.Reference;
import net.minecraft.util.text.translation.I18n;

public class StringHelper {

    public static String localize(String prefix, String key) {
        return I18n.translateToLocal(prefix + "." + Reference.MOD_ID + ":" + key);
    }
}
