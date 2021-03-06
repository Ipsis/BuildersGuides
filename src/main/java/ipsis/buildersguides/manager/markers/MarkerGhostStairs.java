package ipsis.buildersguides.manager.markers;

import ipsis.buildersguides.manager.MarkerType;
import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.buildersguides.util.BlockUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Ghost Stairs Marker
 *
 * Displays a set of stairs that depend on the direction that the block is facing.
 * The stairs can be extended up, down and to the sides
 */
public class MarkerGhostStairs extends Marker {

    @Override
    public boolean isMatch(MarkerType t) {
        return t == MarkerType.GHOSTSTAIRS;
    }

    @Override
    public boolean isFaceEnabled(TileEntityMarker te, EnumFacing f) {
        if (f == EnumFacing.UP || f == EnumFacing.DOWN)
            return false;

        return te.hasValidV(f);
    }

    @Override
    public void handleHammer(World worldIn, TileEntityMarker te, EntityPlayer entityPlayer, EnumFacing side) {

        if (entityPlayer.isSneaking()) {
            te.setV(side, te.getV(side) - 1);
            if (te.getV(side) < 0)
                te.setV(side, 0);
        } else {
            te.setV(side, te.getV(side) + 1);
        }
        BlockUtils.markBlockForUpdate(worldIn, te.getPos());
    }

    @Override
    public void handleConfig(World worldIn, TileEntityMarker te, EntityPlayer entityPlayer, EnumFacing side) {

        if (entityPlayer.isSneaking() && (side == EnumFacing.UP || side == EnumFacing.DOWN))
            super.handleConfig(worldIn, te, entityPlayer, side);
    }

    @Override
    public void handleServerUpdate(TileEntityMarker te) {

        te.clearClientData();

        EnumFacing upFacing = te.getFacing().getOpposite();
        EnumFacing downFacing = te.getFacing();
        int upCount = te.getV(upFacing);
        int downCount = te.getV(downFacing);

        for (EnumFacing f : EnumFacing.values()) {
            if (te.hasValidV(f)) {
                if (f == upFacing && upCount > 0) {
                    // Push stairs up
                        for (int i = 1; i <= upCount; i++) {
                            BlockPos p = te.getPos().offset(f, i).offset(EnumFacing.UP, i);
                            te.addToBlockList(p);
                        }
                } else if (f == downFacing && downCount > 0) {
                    // Push Stairs down
                        for (int i = 1; i <= downCount; i++) {
                            BlockPos p = te.getPos().offset(f, i).offset(EnumFacing.DOWN, i);
                            te.addToBlockList(p);
                        }
                } else if (f != EnumFacing.DOWN && f != EnumFacing.UP) {
                    // Sideways
                    for (int i = 1; i <= te.getV(f); i++) {
                        BlockPos p = te.getPos().offset(f, i);
                        te.addToBlockList(p);
                    }

                    // Up
                    if (upCount > 0) {
                        for (int i = 1; i <= te.getV(f); i++) {
                            for (int j = 1; j <= upCount; j++) {
                                BlockPos p = te.getPos().offset(f, i).offset(EnumFacing.UP, j).offset(upFacing, j);
                                te.addToBlockList(p);
                            }
                        }
                    }

                    if (downCount > 0) {
                        for (int i = 1; i <= te.getV(f); i++) {
                            for (int j = 1; j <= downCount; j++) {
                                BlockPos p = te.getPos().offset(f, i).offset(EnumFacing.DOWN, j).offset(downFacing, j);
                                te.addToBlockList(p);
                            }
                        }
                    }
                }
            }
        }

        te.cleanBlockList();
    }
}

