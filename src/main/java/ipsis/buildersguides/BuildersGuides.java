package ipsis.buildersguides;

import ipsis.buildersguides.init.ModBlocks;
import ipsis.buildersguides.init.ModItems;
import ipsis.buildersguides.proxy.CommonProxy;
import ipsis.buildersguides.reference.Reference;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION)
public class BuildersGuides {

    @Mod.Instance(Reference.MOD_ID)
    public static BuildersGuides instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    }

    @Mod.EventHandler
    public void initialize(FMLInitializationEvent event) {

        ModItems.initialize();
        ModBlocks.initialize();

        proxy.registerTileEntitySpecialRenderer();
        proxy.registerRenderInformation();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
}
