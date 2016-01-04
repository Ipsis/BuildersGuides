package ipsis.buildersguides.proxy;

import ipsis.buildersguides.client.KeyInputEventHandler;
import ipsis.buildersguides.client.keys.KeyBindingsBG;
import ipsis.buildersguides.manager.MarkerType;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.oredict.OreDictionary;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderInformation() {

        ModelHelper.registerBlock(ModBlocks.blockMarker, 0, BlockMarker.BASENAME);
        ModelHelper.registerItem(ModItems.itemMallet, 0, ItemMallet.BASENAME);

        for (MarkerType t : MarkerType.values()) {
            ModelHelper.registerItem(ModItems.itemMarkerCard, t.ordinal(), ItemMarkerCard.BASENAME + "." + t);
            ModelBakery.registerItemVariants(ModItems.itemMarkerCard, new ResourceLocation(Reference.MOD_ID + ":" + ItemMarkerCard.BASENAME + "." + t));
        }
    }

    @Override
    public void registerTileEntitySpecialRenderer() {

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMarker.class, new MarkerRenderer());
    }

    @Override
    public void registerKeyBindings() {

        MinecraftForge.EVENT_BUS.register(new KeyInputEventHandler());
        for (KeyBindingsBG k : KeyBindingsBG.values()) {
            ClientRegistry.registerKeyBinding(k.getKeyBinding());
        }
    }

    @Override
    public World getClientWorld() {
        return FMLClientHandler.instance().getClient().theWorld;
    }
}
