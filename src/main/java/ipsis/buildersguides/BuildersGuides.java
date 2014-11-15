package ipsis.buildersguides;

import ipsis.buildersguides.gui.GuiHandler;
import ipsis.buildersguides.handler.ConfigurationHandler;
import ipsis.buildersguides.item.BGItems;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import ipsis.buildersguides.block.BGBlocks;
import ipsis.buildersguides.proxy.IProxy;
import ipsis.buildersguides.reference.Reference;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION)
public class BuildersGuides {

    @Mod.Instance(Reference.MOD_ID)
    public static BuildersGuides instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static IProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        ConfigurationHandler.init(event.getSuggestedConfigurationFile());

        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

        BGBlocks.preInit();
        BGItems.preInit();
    }

    @Mod.EventHandler
    public void initialize(FMLInitializationEvent event) {

        BGBlocks.initialize();
        BGItems.initialize();

        proxy.initRenderingAndTexture();
        proxy.initTileEntities();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        BGBlocks.postInit();
        BGItems.postInit();
    }
}
