package ipsis.buildersguides.init;

import ipsis.buildersguides.item.ItemBG;
import ipsis.buildersguides.item.ItemMallet;
import ipsis.buildersguides.item.ItemMarkerCard;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {

    public static ItemBG itemMarkerCard;
    public static ItemBG itemMallet;

    public static void preInit() {

    }

    public static void init() {

        itemMallet = new ItemMallet();
        itemMarkerCard = new ItemMarkerCard();
    }

    @SideOnly(Side.CLIENT)
    public static void initClient() {

        ModItems.itemMarkerCard.initModel();
        ModItems.itemMallet.initModel();
    }

}
