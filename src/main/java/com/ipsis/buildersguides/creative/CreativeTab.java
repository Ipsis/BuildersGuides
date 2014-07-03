package com.ipsis.buildersguides.creative;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import com.ipsis.buildersguides.reference.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.StatCollector;

public class CreativeTab {

    public static final CreativeTabs BG_TAB = new CreativeTabs(Reference.MOD_ID) {

        @Override
        public Item getTabIconItem() {

            return Items.compass;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public String getTranslatedTabLabel() {

            return StatCollector.translateToLocal("key.categories.BuildersGuides");
        }
    };
}
