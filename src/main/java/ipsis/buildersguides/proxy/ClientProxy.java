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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;


public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {

        super.preInit();
    }

    @Override
    protected void registerBlockItemModels() {

        ModBlocks.blockMarker.initModel();
    }

    @Override
    protected void registerItemRenderers() {

        ModelHelper.registerItem(Item.getItemFromBlock(ModBlocks.blockMarker), 0, BlockMarker.BASENAME);
        ModItems.itemMallet.initModel();
        ModItems.itemMarkerCard.initModel();
    }

    @Override
    protected void registerTESRs() {

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMarker.class, new TESRMarker());
    }

    @Override
    public void registerKeyBindings() {

        for (KeyBindingsBG k : KeyBindingsBG.values()) {
            ClientRegistry.registerKeyBinding(k.getKeyBinding());
        }
    }

    @Override
    protected void registerEventHandlers() {
        MinecraftForge.EVENT_BUS.register(new KeyInputEventHandler());
        MinecraftForge.EVENT_BUS.register(new DrawBlockHighlightEventHandler());
    }

    @Override
    public World getClientWorld() {
        return FMLClientHandler.instance().getClient().theWorld;
    }
}
