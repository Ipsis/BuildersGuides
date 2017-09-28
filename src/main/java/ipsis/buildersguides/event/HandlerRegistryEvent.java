package ipsis.buildersguides.event;

import ipsis.buildersguides.block.BlockMarker;
import ipsis.buildersguides.init.ModBlocks;
import ipsis.buildersguides.init.ModItems;
import ipsis.buildersguides.item.ItemBlockMarker;
import ipsis.buildersguides.item.ItemMallet;
import ipsis.buildersguides.item.ItemMarkerCard;
import ipsis.buildersguides.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerRegistryEvent {

    @SubscribeEvent
    public void onRegisterBlocks(RegistryEvent.Register<Block> event) {

        ModBlocks.init();

        event.getRegistry().register(
                ModBlocks.blockMarker.
                        setUnlocalizedName(Reference.MOD_ID + "." + BlockMarker.BASENAME).
                        setRegistryName(Reference.MOD_ID, BlockMarker.BASENAME)
        );
    }

    @SubscribeEvent
    public void onRegisterItems(RegistryEvent.Register<Item> event) {

        ModItems.init();

        event.getRegistry().register(
                ModItems.itemMallet.
                        setUnlocalizedName(Reference.MOD_ID + "." + ItemMallet.BASENAME).
                        setRegistryName(Reference.MOD_ID, ItemMallet.BASENAME)
        );

        event.getRegistry().register(
                ModItems.itemMarkerCard.
                        setUnlocalizedName(Reference.MOD_ID + "." + ItemMarkerCard.BASENAME).
                        setRegistryName(Reference.MOD_ID, ItemMarkerCard.BASENAME)
        );

        event.getRegistry().register(
                new ItemBlockMarker(ModBlocks.blockMarker).
                        setRegistryName(ModBlocks.blockMarker.getRegistryName())
        );
    }
}
