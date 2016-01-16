package ipsis.buildersguides.manager.markers;

import ipsis.buildersguides.manager.MarkerType;
import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.buildersguides.util.BlockUtils;
import ipsis.oss.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.List;

public class MarkerCenter extends Marker {
    @Override
    public boolean isMatch(MarkerType t) {
        return t == MarkerType.CENTER;
    }

    @Override
    public boolean isFaceEnabled(TileEntityMarker te, EnumFacing f) {

        return te.hasValidV(f);
    }

    @Override
    public void handleHammer(World worldIn, TileEntityMarker te, EntityPlayer entityPlayer, EnumFacing side) {

        LogHelper.info("handleHammer: CENTER");

        if (entityPlayer.isSneaking()) {
            te.setV(side, te.getV(side) - 1);
            if (te.getV(side) < 0)
                te.setV(side, 0);
        } else {
            te.setV(side, te.getV(side) + 1);
        }

        findTarget(worldIn, te, side);
        worldIn.markBlockForUpdate(te.getPos());
    }

    @Override
    public void findTargets(World worldIn, TileEntityMarker te) {

        for (EnumFacing f : EnumFacing.VALUES)
            findTarget(worldIn, te, f);
    }

    private void findTarget(World worldIn, TileEntityMarker te, EnumFacing side) {

        /* Set target to be the v'th block in the direction */
        if (te.getV(side) == 0)
            te.setTarget(side, new BlockPos(te.getPos()));
        else
            te.setTarget(side, BlockUtils.getNthBlock(worldIn, te.getPos(), side, te.getV(side)));
    }

    @Override
    public void handleConfig(World worldIn, TileEntityMarker te, EntityPlayer entityPlayer, EnumFacing side) {

    }

    @Override
    public void handleServerUpdate(TileEntityMarker te) {

        // calculate the center points and add them to the center list
        te.clearBlocklist();
        te.clearCenterList();

        for (EnumFacing f : EnumFacing.VALUES) {
            if (te.hasTarget(f)) {
                List<BlockPos> centerList = BlockUtils.getCenterBlockList(te.getPos(), te.getTarget(f), f);
                for (BlockPos p : centerList)
                    te.addToCenterList(p);
            }
        }
    }
}