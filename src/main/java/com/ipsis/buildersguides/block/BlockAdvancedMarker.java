package com.ipsis.buildersguides.block;

import com.ipsis.buildersguides.tileentity.TileAdvancedMarker;
import com.ipsis.buildersguides.tileentity.TileBaseMarker;
import com.ipsis.buildersguides.util.DirectionHelper;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.EntityLivingBase;
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
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {

        if (!world.isRemote) {
            if (world.getTileEntity(x, y, z) instanceof TileAdvancedMarker) {

                TileAdvancedMarker te = (TileAdvancedMarker) world.getTileEntity(x, y, z);
                te.findTargets();
            }
        }
    }
}
