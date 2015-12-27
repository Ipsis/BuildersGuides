package ipsis.buildersguides.proxy;

import ipsis.buildersguides.MarkerType;
import ipsis.buildersguides.block.BlockMarker;
import ipsis.buildersguides.client.renderer.MarkerRenderer;
import ipsis.buildersguides.init.ModBlocks;
import ipsis.buildersguides.init.ModItems;
import ipsis.buildersguides.item.ItemMallet;
import ipsis.buildersguides.item.ItemMarkerCard;
import ipsis.buildersguides.reference.Reference;
import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.oss.client.ModelHelper;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderInformation() {

        ModelHelper.registerBlock(ModBlocks.blockMarker, 0, BlockMarker.BASENAME);

        ModelHelper.registerItem(ModItems.itemMallet, ItemMallet.BASENAME);

        for (MarkerType t : MarkerType.values()) {
            ModelHelper.registerItem(ModItems.itemMarkerCard, t.ordinal(), ItemMarkerCard.BASENAME + "." + t);
            ModelBakery.addVariantName(ModItems.itemMarkerCard, Reference.MOD_ID + ":" + ItemMarkerCard.BASENAME + "." + t);
        }
    }

    @Override
    public void registerTileEntitySpecialRenderer() {

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMarker.class, new MarkerRenderer());
    }

    @Override
    public World getClientWorld() {
        return FMLClientHandler.instance().getClient().theWorld;
    }
}
