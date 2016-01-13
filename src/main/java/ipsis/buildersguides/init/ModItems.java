package ipsis.buildersguides.init;

import ipsis.buildersguides.item.ItemBG;
import ipsis.buildersguides.item.ItemMallet;
import ipsis.buildersguides.item.ItemMarkerCard;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {

    public static void init() {

        GameRegistry.registerItem(itemMarkerCard, ItemMarkerCard.BASENAME);
        GameRegistry.registerItem(itemMallet, ItemMallet.BASENAME);
    }

    public static ItemBG itemMarkerCard = new ItemMarkerCard();
    public static ItemBG itemMallet = new ItemMallet();
}
