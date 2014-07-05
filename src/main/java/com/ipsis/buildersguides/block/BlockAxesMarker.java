package com.ipsis.buildersguides.block;

import com.ipsis.buildersguides.tileentity.TileBGMarker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

/**
 * Shows the 3 axes as lines.
 * Shift-click on the appropriate axis changed it from line to grid mode
 */
public class BlockAxesMarker extends BlockMarker {

    public BlockAxesMarker() {

        super(TileBGMarker.Type.AXES);
        setBlockName("axesMarker");
    }

    @Override
    public boolean isOpaqueCube() {

        return false;
    }

    @Override
    public boolean onBlockActivated(World world, int x,	int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ) {

        if (world.isRemote)
            return false;

        TileEntity te = world.getTileEntity(x, y, z);
        if (te != null && te instanceof TileBGMarker) {
            TileBGMarker teMarker = (TileBGMarker)te;
            if (entityPlayer.isSneaking()) {

                /* Change the mode */
                teMarker.setNextAxis();
                entityPlayer.addChatComponentMessage(new ChatComponentText(((TileBGMarker) te).getAxis().toString()));
            } else {

                /* Change the color */
                teMarker.setNextColor();
            }
        }

        return false;
    }
}
