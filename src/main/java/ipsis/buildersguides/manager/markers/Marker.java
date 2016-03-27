package ipsis.buildersguides.manager.markers;

import ipsis.buildersguides.item.ItemMarkerCard;
import ipsis.buildersguides.manager.MarkerType;
import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.buildersguides.util.BlockUtils;
import ipsis.buildersguides.util.ItemStackHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public abstract class Marker {

    public abstract boolean isMatch(MarkerType t);
    public abstract void handleHammer(World worldIn, TileEntityMarker te, EntityPlayer entityPlayer, EnumFacing side);
    public void handleServerUpdate(TileEntityMarker te) { te.clearClientData(); }
    public void findTargets(World worldIn, TileEntityMarker te) { }
    public void initServerMarker(TileEntityMarker te) { }
    public String getMode(TileEntityMarker te) { return ""; }

    public boolean isFaceEnabled(TileEntityMarker te, EnumFacing f) {
        return false;
    }

    public void handleConfig(World worldIn, TileEntityMarker te, EntityPlayer entityPlayer, EnumFacing side) {

        if (entityPlayer.isSneaking()) {
            findTargets(worldIn, te);
            BlockUtils.markBlockForUpdate(worldIn, te.getPos());
        }
    }

    public void handleDecorate(World worldIn, TileEntityMarker te, EntityPlayer entityPlayer, EnumFacing side) {

        if (entityPlayer.isSneaking()) {
        } else {
            te.setColor(te.getColor().getNext());
            BlockUtils.markBlockForUpdate(worldIn, te.getPos());
        }
    }

    public void handleWrench(World worldIn, TileEntityMarker te, EntityPlayer entityPlayer, EnumFacing side) {

        if (entityPlayer.isSneaking()) {
            MarkerType t = te.getType();
            if (t != MarkerType.BLANK) {
                te.setType(MarkerType.BLANK);
                ItemStackHelper.spawnInWorld(worldIn, te.getPos(), ItemMarkerCard.getItemStack(t));
                BlockUtils.markBlockForUpdate(worldIn, te.getPos());
            }
        } else {
            // rotate around side == axis
            te.rotateTile(side);
            findTargets(worldIn, te);
            BlockUtils.markBlockForUpdate(worldIn, te.getPos());
        }
    }
}
