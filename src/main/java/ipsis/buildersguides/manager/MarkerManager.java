package ipsis.buildersguides.manager;

import ipsis.buildersguides.item.ItemMallet;
import ipsis.buildersguides.manager.markers.AxisMarker;
import ipsis.buildersguides.manager.markers.LaserMarker;
import ipsis.buildersguides.manager.markers.Marker;
import ipsis.buildersguides.manager.markers.SpacingMarker;
import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.oss.LogHelper;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class MarkerManager {

    private static List<Marker> markerList;

    static {
        markerList = new ArrayList<Marker>();
        markerList.add(new AxisMarker());
        markerList.add(new SpacingMarker());
        markerList.add(new LaserMarker());
    }

    public static void handleMalletMode(World worldIn,  TileEntityMarker te, EnumFacing side, ItemMallet.MalletMode mode, boolean isSneaking) {

        for (Marker m : markerList) {
            if (m.isMatch(te.getType())) {
                if (mode == ItemMallet.MalletMode.BCWRENCH)
                    m.handleWrench(worldIn, te, side, isSneaking);
                else if (mode == ItemMallet.MalletMode.HAMMER)
                    m.handleHammer(worldIn, te, side, isSneaking);
                else if (mode == ItemMallet.MalletMode.DECORATE)
                    m.handleDecorate(worldIn, te, side, isSneaking);
                else if (mode == ItemMallet.MalletMode.CONFIG)
                    m.handleConfig(worldIn, te, side, isSneaking);
            }
        }
    }

    public static void handleServerUpdate(TileEntityMarker te) {

        for (Marker m : markerList) {
            if (m.isMatch(te.getType()))
                m.handleServerUpdate(te);
        }
    }
}
