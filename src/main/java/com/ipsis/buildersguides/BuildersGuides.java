package ipsis.buildersguides;

import com.ipsis.buildersguides.block.TileBGMarker;
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

    @Mod.Instance("BuildersGuide")
    public static BuildersGuides instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static IProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        BGBlocks.preInit();
    }

    @Mod.EventHandler
    public void initialize(FMLInitializationEvent event) {

        BGBlocks.initialize();

        proxy.initRenderingAndTexture();
        GameRegistry.registerTileEntity(TileBGMarker.class, "tile.bgMarker");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        BGBlocks.postInit();
    }
}
