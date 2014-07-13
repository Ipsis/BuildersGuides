package com.ipsis.buildersguides.item;

import cpw.mods.fml.common.registry.GameRegistry;

public class BGItems {

    public static void preInit() {

        itemMarker = new ItemMarker();
        itemMarker.setUnlocalizedName("marker");

        GameRegistry.registerItem(itemMarker, "item.marker");
    }

    public static void initialize() {

    }

    public static void postInit() {

    }

    public static ItemBG itemMarker;

}
