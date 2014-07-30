package com.ipsis.buildersguides.block;

import com.ipsis.buildersguides.reference.Reference;
import com.ipsis.buildersguides.tileentity.TileBaseMarker;
import com.ipsis.buildersguides.tileentity.TileTargetMarker;
import com.ipsis.buildersguides.util.DirectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class BlockFacedMarker extends BlockBaseMarker {

    public BlockFacedMarker() {

        super();
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
        if (te != null && te instanceof TileBaseMarker) {
            if (side == ((TileBaseMarker)te).getFacing().ordinal())
                return this.frontIcon;
        }

		/* Assume everything else is the same icon */
        return blockIcon;
    }

    @Override
    public boolean rotateBlock(World worldObj, int x, int y, int z, ForgeDirection axis) {

        if (worldObj.isRemote)
            return false;

        if (axis == ForgeDirection.UP || axis == ForgeDirection.DOWN ||
                axis == ForgeDirection.SOUTH || axis == ForgeDirection.NORTH ||
                axis == ForgeDirection.EAST || axis == ForgeDirection.WEST) {

            TileEntity tileEntity = worldObj.getTileEntity(x, y, z);
            if (tileEntity instanceof TileBaseMarker) {
                TileBaseMarker te = (TileBaseMarker) tileEntity;
                te.rotateAround(axis, null);
            }
            return true;
        }
        return false;

    }

    private static final ForgeDirection[] validRotationAxes = new ForgeDirection[] {
            ForgeDirection.UP, ForgeDirection.DOWN,
            ForgeDirection.EAST, ForgeDirection.WEST,
            ForgeDirection.SOUTH, ForgeDirection.NORTH };

    @Override
    public ForgeDirection[] getValidRotations(World worldObj, int x, int y, int z) {

        return validRotationAxes;
    }
}
