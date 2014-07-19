package com.ipsis.buildersguides.block;

import com.ipsis.buildersguides.reference.Reference;
import com.ipsis.buildersguides.tileentity.TileLaserMarker;
import com.ipsis.buildersguides.tileentity.TileTargetMarker;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockTargetMarker extends BlockBaseMarker {

    public BlockTargetMarker() {

        super();
        this.setBlockName("targetMarker");
    }

    /**
     * ITileEntityProvider
     */
    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {

        return new TileTargetMarker();
    }

    @SideOnly(Side.CLIENT)
    private IIcon frontIcon;

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {

        this.blockIcon = iconRegister.registerIcon(Reference.MOD_ID + ":" + "targetMarker");
        this.frontIcon = iconRegister.registerIcon(Reference.MOD_ID + ":" + "targetMarker_front");
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
        if (te != null && te instanceof TileTargetMarker) {
            if (side == ((TileTargetMarker)te).getFacing().ordinal())
                return this.frontIcon;
        }

		/* Assume everything else is the same icon */
        return blockIcon;
    }
}
