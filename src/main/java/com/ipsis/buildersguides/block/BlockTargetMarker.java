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

public class BlockTargetMarker extends BlockFacedMarker {

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
}
