package com.ipsis.buildersguides.block;

import com.ipsis.buildersguides.reference.Reference;
import com.ipsis.buildersguides.tileentity.TileBaseMarker;
import com.ipsis.buildersguides.tileentity.TileChunkMarker;
import com.ipsis.buildersguides.tileentity.TileDireMarker;
import com.ipsis.buildersguides.tileentity.TileSkyMarker;
import com.ipsis.buildersguides.util.DirectionHelper;
import com.ipsis.buildersguides.util.LogHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
import sun.rmi.runtime.Log;

public class BlockDireMarker extends BlockBG implements ITileEntityProvider {

    public BlockDireMarker() {

        this.setBlockName("direMarker");
    }

    /**
     * ITileEntityProvider
     */
    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {

        return new TileDireMarker();
    }

    @Override
    public boolean isOpaqueCube() {

        return false;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack) {

        if (!world.isRemote) {
            if (world.getTileEntity(x, y, z) instanceof TileDireMarker) {

                TileDireMarker te = (TileDireMarker) world.getTileEntity(x, y, z);
                te.setFacing(DirectionHelper.getFacing(entityLiving));
                te.calcBlocks();
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x,	int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ) {

        if (!world.isRemote) {

            TileEntity te = world.getTileEntity(x, y, z);
            if (te != null && te instanceof TileDireMarker) {
                TileDireMarker teMarker = (TileDireMarker) te;
                teMarker.doUse(entityPlayer);
                world.markBlockForUpdate(x, y, z);
                return true;
            }
        }

        return true;
    }

    @SideOnly(Side.CLIENT)
    private IIcon frontIcon;

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {

        String name = this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(":") + 1 );

        this.blockIcon = iconRegister.registerIcon(Reference.MOD_ID + ":base");
        this.frontIcon = iconRegister.registerIcon(Reference.MOD_ID + ":" + name + "_front");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int metadata) {

        /**
         * As we override the IBlockAccess version this will only be called for the toolbar
         * Is is oriented as facing south
         */
        if (side == ForgeDirection.SOUTH.ordinal())
            return this.frontIcon;

        return this.blockIcon;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(IBlockAccess iblockaccess, int x, int y, int z, int side) {

        TileEntity te = iblockaccess.getTileEntity(x, y, z);
        if (te != null && te instanceof TileDireMarker) {
            if (side == ((TileDireMarker)te).getFacing().ordinal())
                return this.frontIcon;
        }

		/* Assume everything else is the same icon */
        return blockIcon;
    }

    @Override
    public boolean rotateBlock(World worldObj, int x, int y, int z, ForgeDirection axis) {

        if (worldObj.isRemote)
            return false;

        if (axis == ForgeDirection.UP || axis == ForgeDirection.DOWN) {

            TileEntity tileEntity = worldObj.getTileEntity(x, y, z);
            if (tileEntity instanceof TileDireMarker) {
                TileDireMarker te = (TileDireMarker) tileEntity;
                te.rotateAround(axis, null);
            }
            return true;
        }
        return false;

    }

    private static final ForgeDirection[] validRotationAxes = new ForgeDirection[]{
            ForgeDirection.UP, ForgeDirection.DOWN
    };

    @Override
    public ForgeDirection[] getValidRotations(World worldObj, int x, int y, int z) {

        return validRotationAxes;
    }


}
