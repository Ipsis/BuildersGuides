package ipsis.buildersguides.util;

import ipsis.buildersguides.reference.Reference;

public enum EnumKeys {

    KEY_MODE("keys." + Reference.MOD_ID + ".mode");

    private String name;
    public String getName() {
        return this.name;
    }

    EnumKeys(String name) {
        this.name = name;
    }

}
