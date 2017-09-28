package ipsis.buildersguides.proxy;

import ipsis.buildersguides.client.DrawBlockHighlightEventHandler;
import ipsis.buildersguides.client.KeyInputEventHandler;
import ipsis.buildersguides.client.keys.KeyBindingsBG;
import ipsis.buildersguides.client.renderer.marker.TESRMarker;
import ipsis.buildersguides.block.BlockMarker;
import ipsis.buildersguides.init.ModBlocks;
import ipsis.buildersguides.init.ModItems;
import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.buildersguides.oss.client.ModelHelper;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {

        super.preInit();

        MinecraftForge.EVENT_BUS.register(this);

        MinecraftForge.EVENT_BUS.register(new KeyInputEventHandler());
        MinecraftForge.EVENT_BUS.register(new DrawBlockHighlightEventHandler());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMarker.class, new TESRMarker());

        for (KeyBindingsBG k : KeyBindingsBG.values()) {
            ClientRegistry.registerKeyBinding(k.getKeyBinding());
        }
    }

    public void init() {

        super.init();

    }

    public void postInit() {

        super.postInit();
    }

    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event) {

        ModItems.initClient();
        ModBlocks.initClient();
    }


    @Override
    public World getClientWorld() {
        return FMLClientHandler.instance().getClient().world;
    }
}
