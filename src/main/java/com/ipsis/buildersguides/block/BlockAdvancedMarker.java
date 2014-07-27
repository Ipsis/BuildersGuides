package com.ipsis.buildersguides.block;

import com.ipsis.buildersguides.tileentity.TileAdvancedMarker;
import com.ipsis.buildersguides.tileentity.TileBaseMarker;
import com.ipsis.buildersguides.util.DirectionHelper;
import com.ipsis.buildersguides.util.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAdvancedMarker extends BlockBG implements ITileEntityProvider {

    public BlockAdvancedMarker() {

        super();
        setBlockName("advancedMarker");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {

        return new TileAdvancedMarker();
    }

    @Override
    public boolean isOpaqueCube() {

        return false;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {

        if (!world.isRemote) {
            if (world.getTileEntity(x, y, z) instanceof TileAdvancedMarker) {

                TileAdvancedMarker te = (TileAdvancedMarker) world.getTileEntity(x, y, z);
                te.findTargets();
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x,	int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ) {

        if (!world.isRemote) {

            TileEntity te = te = world.getTileEntity(x, y, z);
            if (te != null && te instanceof TileAdvancedMarker) {
                TileAdvancedMarker teMarker = (TileAdvancedMarker) te;
                if (!entityPlayer.isSneaking()) {
                    teMarker.setColor(teMarker.getColor().getNext());
                    world.markBlockForUpdate(x, y, z);
                }
            }
        }

        return true;
    }

    private boolean wasPowered = false;

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {

        if (world.isRemote)
            return;

        LogHelper.info(wasPowered + "->" + world.isBlockIndirectlyGettingPowered(x, y, z));
        boolean isPowered = world.isBlockIndirectlyGettingPowered(x, y, z);
        if (!wasPowered && isPowered) {
            TileEntity te = world.getTileEntity(x, y, z);
            if (te != null && te instanceof TileBaseMarker)
                ((TileBaseMarker) te).onRedstonePulse();
        }
        wasPowered = isPowered;
    }
}
