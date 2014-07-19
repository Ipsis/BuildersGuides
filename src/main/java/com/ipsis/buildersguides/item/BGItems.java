package com.ipsis.buildersguides.item;

import cpw.mods.fml.common.registry.GameRegistry;

public class BGItems {

    public static void preInit() {

        itemMarker = new ItemMarker();
        allenKey = new ItemAllenKey();

        GameRegistry.registerItem(itemMarker, "item.marker");
        GameRegistry.registerItem(allenKey, "item.allenKey");
    }

    public static void initialize() {

    }

    public static void postInit() {

    }

    public static ItemBG itemMarker;
    public static ItemBG allenKey;

}
