package ipsis.buildersguides.init;

import ipsis.buildersguides.item.ItemMallet;
import ipsis.buildersguides.item.ItemMarkerCard;
import ipsis.buildersguides.reference.Names;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {

    public static void initialize() {

        itemMarkerCard = new ItemMarkerCard();
        GameRegistry.registerItem(itemMarkerCard, ItemMarkerCard.BASENAME);

        itemMallet = new ItemMallet();
        GameRegistry.registerItem(itemMallet, ItemMallet.BASENAME);
    }

    public static Item itemMarkerCard;
    public static Item itemMallet;
}
