package com.ipsis.buildersguides.gui;

import com.ipsis.buildersguides.gui.container.ContainerToolBox;
import com.ipsis.buildersguides.gui.container.InventoryToolBox;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {

        if (id == 0) {
            return new ContainerToolBox(player, new InventoryToolBox(player.getHeldItem()));
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {

        if (id == 0) {
            return new GuiToolBox(player, new InventoryToolBox(player.getHeldItem()));
        }

        return null;
    }
}
