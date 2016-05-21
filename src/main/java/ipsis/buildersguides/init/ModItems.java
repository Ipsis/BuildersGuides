package ipsis.buildersguides.init;

import ipsis.buildersguides.item.ItemBG;
import ipsis.buildersguides.item.ItemMallet;
import ipsis.buildersguides.item.ItemMarkerCard;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {

    public static void init() {

        GameRegistry.register(itemMallet);
        GameRegistry.register(itemMarkerCard);
    }

    public static ItemBG itemMarkerCard = new ItemMarkerCard();
    public static ItemBG itemMallet = new ItemMallet();
}
