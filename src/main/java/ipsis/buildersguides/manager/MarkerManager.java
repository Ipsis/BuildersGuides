package ipsis.buildersguides.manager;

import ipsis.buildersguides.item.ItemMallet;
import ipsis.buildersguides.manager.markers.*;
import ipsis.buildersguides.tileentity.TileEntityMarker;
import net.minecraft.entity.player.EntityPlayer;
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
        markerList.add(new WorldMarker());
        markerList.add(new CenterMarker());
        markerList.add(new GhostMarker());
        markerList.add(new GhostStairsMarker());
    }

    public static void handleMalletMode(World worldIn, TileEntityMarker te, EntityPlayer entityPlayer, EnumFacing side, ItemMallet.MalletMode mode) {

        for (Marker m : markerList) {
            if (m.isMatch(te.getType())) {
                if (mode == ItemMallet.MalletMode.BCWRENCH)
                    m.handleWrench(worldIn, te, entityPlayer, side);
                else if (mode == ItemMallet.MalletMode.HAMMER)
                    m.handleHammer(worldIn, te, entityPlayer, side);
                else if (mode == ItemMallet.MalletMode.DECORATE)
                    m.handleDecorate(worldIn, te, entityPlayer, side);
                else if (mode == ItemMallet.MalletMode.CONFIG)
                    m.handleConfig(worldIn, te, entityPlayer, side);
            }
        }
    }

    public static void handleServerUpdate(TileEntityMarker te) {

        for (Marker m : markerList) {
            if (m.isMatch(te.getType()))
                m.handleServerUpdate(te);
        }
    }

    public static boolean isFaceEnabled(TileEntityMarker te, EnumFacing f) {

        for (Marker m : markerList) {
            if (m.isMatch(te.getType()))
                return m.isFaceEnabled(te, f);
        }

        return false;
    }
}
