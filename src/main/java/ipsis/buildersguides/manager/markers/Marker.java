package ipsis.buildersguides.manager.markers;

import ipsis.buildersguides.item.ItemMarkerCard;
import ipsis.buildersguides.manager.MarkerType;
import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.buildersguides.util.ItemStackHelper;
import ipsis.oss.LogHelper;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public abstract class Marker {

    public abstract boolean isMatch(MarkerType t);
    public abstract void handleHammer(World worldIn, TileEntityMarker te, EnumFacing side, boolean isSneaking);
    public abstract void handleConfig(World worldIn, TileEntityMarker te, EnumFacing side, boolean isSneaking);
    public void handleServerUpdate(TileEntityMarker te) { }

    public void handleDecorate(World worldIn, TileEntityMarker te, EnumFacing side, boolean isSneaking) {

        LogHelper.info("handleDecorate:");

        if (isSneaking) {
        } else {
            te.setColor(te.getColor().getNext());
            worldIn.markBlockForUpdate(te.getPos());
        }
    }

    public void handleWrench(World worldIn, TileEntityMarker te, EnumFacing side, boolean isSneaking) {

        LogHelper.info("handleWrench:");

        if (isSneaking) {
            MarkerType t = te.getType();
            if (t != MarkerType.BLANK) {
                te.setType(MarkerType.BLANK);
                ItemStackHelper.spawnInWorld(worldIn, te.getPos(), ItemMarkerCard.getItemStack(t));
                worldIn.markBlockForUpdate(te.getPos());
            }
        } else {
            // rotate around side == axis
            te.rotateTile(side);
            worldIn.markBlockForUpdate(te.getPos());
        }
    }
}
