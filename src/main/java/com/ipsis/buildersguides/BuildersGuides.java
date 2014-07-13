package com.ipsis.buildersguides;

import com.ipsis.buildersguides.init.Recipe;
import com.ipsis.buildersguides.item.BGItems;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import com.ipsis.buildersguides.block.BGBlocks;
import com.ipsis.buildersguides.proxy.IProxy;
import com.ipsis.buildersguides.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = BuildersGuides.VERSION)
public class BuildersGuides {

    public static final String VERSION = "0.1a";

    @Mod.Instance("BuildersGuides")
    public static BuildersGuides instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static IProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        BGBlocks.preInit();
        BGItems.preInit();
    }

    @Mod.EventHandler
    public void initialize(FMLInitializationEvent event) {

        BGBlocks.initialize();
        BGItems.initialize();

        proxy.initRenderingAndTexture();
        proxy.initTileEntities();

        Recipe.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        BGBlocks.postInit();
        BGItems.postInit();
    }
}
