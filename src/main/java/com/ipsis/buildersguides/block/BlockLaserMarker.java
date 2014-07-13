package com.ipsis.buildersguides.block;

import com.ipsis.buildersguides.reference.Reference;
import com.ipsis.buildersguides.tileentity.TileLaserMarker;
import com.ipsis.buildersguides.util.DirectionHelper;
import com.ipsis.buildersguides.util.LogHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockLaserMarker extends BlockBG implements ITileEntityProvider {

    public BlockLaserMarker() {

        super();
        setBlockName("laserMarker");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {

        return new TileLaserMarker();
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {

        if (world.getTileEntity(x, y, z) instanceof TileLaserMarker) {

            TileEntity te = world.getTileEntity(x, y, z);
            ForgeDirection facing = DirectionHelper.getFacing(entity);
            ((TileLaserMarker) te).setFacing(facing);
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x,	int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ) {

        if (entityPlayer.isSneaking())
            return false;

        if (!world.isRemote) {

            TileEntity te = world.getTileEntity(x, y, z);
            if (te != null && te instanceof TileLaserMarker) {

                /* TODO fix the rotate */
                TileLaserMarker tlm = (TileLaserMarker)te;
                LogHelper.info("ROTATE ME");
                tlm.setFacing(ForgeDirection.SOUTH.getRotation(tlm.getFacing()));
            }
        }

        return true;
    }

    private boolean wasPowered = false;

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {

        if (world.isRemote)
            return;

        /* SearedBlock code from TinkersConstruct */
        LogHelper.info(wasPowered + "->" + world.isBlockIndirectlyGettingPowered(x, y, z));
        boolean isPowered = world.isBlockIndirectlyGettingPowered(x, y, z);
        if (!wasPowered && isPowered) {
            TileEntity te = world.getTileEntity(x, y, z);
            if (te != null && te instanceof TileLaserMarker)
                ((TileLaserMarker) te).onRedstonePulse();
        }
        wasPowered = isPowered;
    }

    /**
     * Icons
     */
    @SideOnly(Side.CLIENT)
    private IIcon icons[];

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {

        icons = new IIcon[2];

        icons[0] = iconRegister.registerIcon(Reference.MOD_ID + ":" + iconName + "_laser");
        icons[1] = iconRegister.registerIcon(Reference.MOD_ID + ":" + iconName + "_side");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int metadata) {

        /**
         * As we override the IBlockAccess version this will only be called for the toolbar
         * Is is oriented as facing south
         */

        ForgeDirection f = ForgeDirection.getOrientation(side);
        if (f == ForgeDirection.SOUTH)
            return icons[0];

        return icons[1];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(IBlockAccess iblockaccess, int x, int y, int z, int side) {

        TileEntity te = iblockaccess.getTileEntity(x, y, z);
        if (te != null && te instanceof TileLaserMarker) {

            ForgeDirection f = ((TileLaserMarker)te).getFacing();
            if (f.ordinal() == side)
                return icons[0];
        }

        return icons[1];
    }


}
