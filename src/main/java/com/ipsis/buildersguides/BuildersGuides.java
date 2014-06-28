package ipsis.buildersguides;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import ipsis.buildersguides.reference.Reference;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = BuildersGuides.VERSION)
public class BuildersGuides {

    public static final String VERSION = "0.1a";

    @Mod.Instance("BuildersGuide")
    public static BuildersGuides instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Mod.EventHandler
    public void initialize(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}
