package ipsis.buildersguides.tileentity;

import ipsis.buildersguides.MarkerType;
import ipsis.oss.LogHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class TileEntityMarker extends TileEntity {

    private MarkerType type;
    private EnumFacing facing;

    public TileEntityMarker() {

        type = MarkerType.BLANK;
        facing = EnumFacing.SOUTH;
    }

    public void setType(MarkerType type) {
        this.type = type;
    }
    public MarkerType getType() { return this.type; }
    public void setFacing(EnumFacing facing) { this.facing = facing; }
    public EnumFacing getFacing() { return this.facing; }
}
