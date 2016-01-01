package ipsis.buildersguides.manager.markers;

import ipsis.buildersguides.manager.MarkerType;
import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.oss.LogHelper;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class AxisMarker extends Marker {

    @Override
    public boolean isMatch(MarkerType t) {
        return t == MarkerType.AXIS;
    }

    @Override
    public void handleHammer(World worldIn, TileEntityMarker te, EnumFacing side, boolean isSneaking) {
        /* NOOP */
        LogHelper.info("handleHammer: AXIS");
    }

    @Override
    public void handleConfig(World worldIn, TileEntityMarker te, EnumFacing side, boolean isSneaking) {
        /* NOOP */
        LogHelper.info("handleConfig: AXIS");
    }
}
