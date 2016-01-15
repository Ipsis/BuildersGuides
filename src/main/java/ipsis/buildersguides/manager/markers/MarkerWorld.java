package ipsis.buildersguides.manager.markers;

import ipsis.buildersguides.manager.MarkerType;
import ipsis.buildersguides.tileentity.TileEntityMarker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class MarkerWorld extends Marker {

    @Override
    public boolean isMatch(MarkerType t) {
        return t == MarkerType.WORLD;
    }

    @Override
    public boolean isFaceEnabled(TileEntityMarker te, EnumFacing f) {
        return f == EnumFacing.UP || f == EnumFacing.DOWN;
    }

    @Override
    public void handleHammer(World worldIn, TileEntityMarker te, EntityPlayer entityPlayer, EnumFacing side) {
        // NOOP
    }

    @Override
    public void handleConfig(World worldIn, TileEntityMarker te, EntityPlayer entityPlayer, EnumFacing side) {
        // NOOP
    }

    @Override
    public void handleServerUpdate(TileEntityMarker te) {

        te.clearBlocklist();
        te.addToBlockList(new BlockPos(te.getPos().getX(), te.getWorld().getHeight(), te.getPos().getZ()));
        te.addToBlockList(new BlockPos(te.getPos().getX(), 0, te.getPos().getZ()));
    }
}
