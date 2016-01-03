package ipsis.buildersguides.manager.markers;

import ipsis.buildersguides.manager.MarkerType;
import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.oss.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class LaserMarker extends Marker {

    @Override
    public boolean isMatch(MarkerType t) {
        return t == MarkerType.LASER;
    }

    @Override
    public void handleHammer(World worldIn, TileEntityMarker te, EntityPlayer entityPlayer, EnumFacing side) {

        LogHelper.info("handleHammer: LASER");

        // Laser marker face hit is the one to enable
        side = side.getOpposite();
        if (entityPlayer.isSneaking()) {
            te.setV(side, 0);
        } else {
            te.setV(side, 1);
        }
        worldIn.markBlockForUpdate(te.getPos());
    }

    @Override
    public void handleConfig(World worldIn, TileEntityMarker te, EntityPlayer entityPlayer, EnumFacing side) {
        // NOOP
    }
}
