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

/**
 * Center Marker
 *
 * Identifies the nearest target block in each direction
 * The more directions with target blocks the more center points
 * can be calculated.
 * Defaults to finding the nearest in all directions
 */
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

    @Override
    public void initServerMarker(TileEntityMarker te) {

        for (EnumFacing f : EnumFacing.values())
            te.setV(f, 1);

        findTargets(te.getWorld(), te);
        te.getWorld().markBlockForUpdate(te.getPos());
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

        if (te.hasTarget(EnumFacing.DOWN) && te.hasTarget(EnumFacing.UP)) {
            List<BlockPos> centerList = BlockUtils.getCenterBlockList(te.getTarget(EnumFacing.DOWN), te.getTarget(EnumFacing.UP), EnumFacing.UP);
            for (BlockPos p : centerList)
                te.addToCenterList(p);
        }

        if (te.hasTarget(EnumFacing.EAST) && te.hasTarget(EnumFacing.WEST)) {
            List<BlockPos> centerList = BlockUtils.getCenterBlockList(te.getTarget(EnumFacing.EAST), te.getTarget(EnumFacing.WEST), EnumFacing.WEST);
            for (BlockPos p : centerList)
                te.addToCenterList(p);
        }

        if (te.hasTarget(EnumFacing.SOUTH) && te.hasTarget(EnumFacing.NORTH)) {
            List<BlockPos> centerList = BlockUtils.getCenterBlockList(te.getTarget(EnumFacing.SOUTH), te.getTarget(EnumFacing.NORTH), EnumFacing.NORTH);
            for (BlockPos p : centerList)
                te.addToCenterList(p);
        }
    }
}
