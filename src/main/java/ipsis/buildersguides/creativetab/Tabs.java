package ipsis.buildersguides.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Tabs {

    public static class CreativeTabsBG extends CreativeTabs {

        public CreativeTabsBG() {
            super("CreativeTabsBG");
        }

        @Override
        public boolean hasSearchBar() {
            return false;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            return Item.getItemFromBlock(Blocks.diamond_block);
        }
    }

    public static final CreativeTabs BG_TAB = new CreativeTabsBG();
}
