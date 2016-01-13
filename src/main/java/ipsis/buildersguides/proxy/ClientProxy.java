package ipsis.buildersguides.proxy;

import ipsis.buildersguides.client.KeyInputEventHandler;
import ipsis.buildersguides.client.ModelBakeEventHandler;
import ipsis.buildersguides.client.keys.KeyBindingsBG;
import ipsis.buildersguides.client.model.ISBMMarker;
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
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ClientProxy extends CommonProxy {

    @Override
    protected void registerBlockItemModels() {

        StateMapperBase ignoreState = new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return ISBMMarker.modelResourceLocation;
            }
        };
        ModelLoader.setCustomStateMapper(ModBlocks.blockMarker, ignoreState);
    }

    @Override
    protected void registerItemRenderers() {

        ModelHelper.registerItem(Item.getItemFromBlock(ModBlocks.blockMarker), 0, BlockMarker.BASENAME);
        ((ItemMallet)ModItems.itemMallet).initModel();

        for (MarkerType t : MarkerType.values()) {
            ModelHelper.registerItem(ModItems.itemMarkerCard, t.ordinal(), ItemMarkerCard.BASENAME + "." + t);
            ModelBakery.registerItemVariants(ModItems.itemMarkerCard, new ResourceLocation(Reference.MOD_ID + ":" + ItemMarkerCard.BASENAME + "." + t));
        }
    }

    @Override
    protected void registerTESRs() {

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMarker.class, new MarkerRenderer());
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
        MinecraftForge.EVENT_BUS.register(new ModelBakeEventHandler());
    }

    @Override
    public World getClientWorld() {
        return FMLClientHandler.instance().getClient().theWorld;
    }
}
