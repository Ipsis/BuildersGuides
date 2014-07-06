package com.ipsis.buildersguides.block;

import com.ipsis.buildersguides.tileentity.TileAxisMarker;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class BlockAxisMarker extends BlockBG implements ITileEntityProvider {

    public BlockAxisMarker() {

        setBlockName("axisMarker");
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
        if (te != null && te instanceof TileAxisMarker) {
            TileAxisMarker teMarker = (TileAxisMarker)te;
            if (entityPlayer.isSneaking()) {

                /* Change the mode */
                teMarker.setNextAxis();
                entityPlayer.addChatComponentMessage(
                        new ChatComponentText(((TileAxisMarker) te).getAxis().toString()));
            } else {

                /* Change the color */
                teMarker.setNextColor();
            }
        }

        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {

        return new TileAxisMarker();
    }
}
