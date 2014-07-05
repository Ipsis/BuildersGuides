package com.ipsis.buildersguides.tileentity;

import com.ipsis.buildersguides.helper.ColorHelper;
import com.ipsis.buildersguides.util.LogHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileBGMarker extends TileEntity {

    public static final int MAX_DISTANCE = 64;

    public static enum Type {
        AXES,   /* Simple marker that shows the 3 axes */
        PAIR,   /* Part of a pair that shows the length and the middle if an odd length */
        COORD   /* Display the x,y,z above the block */
    }

    public static enum Axis {
        X_Y_Z,
        X_Y,
        X_Z,
        Y_Z,
        X,
        Y,
        Z;

        public static Axis[] VALID_AXIS_MODES = {X_Y_Z, X_Y, X_Z, Y_Z, X, Y, Z};

        public Axis getNext() {

            int next = ordinal();
            next++;
            if (next < 0 || next >= VALID_AXIS_MODES.length)
                return VALID_AXIS_MODES[0];

            return VALID_AXIS_MODES[next];
        }

        public Axis getAxis(int i) {

            if (i < 0 || i >= VALID_AXIS_MODES.length)
                return X_Y_Z;

            return VALID_AXIS_MODES[i];
        }
    }

    private Axis axis;
    private Type type;
    private ColorHelper.Color color;

    public TileBGMarker(Type type) {

        this.type = type;
        this.color = ColorHelper.Color.RED;
        this.axis = Axis.X_Y_Z;
    }

    public Axis getAxis() {

        return axis;
    }

    public void setAxis(Axis axis) {

        this.axis = axis;
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    public void setNextAxis() {

        setAxis(this.axis.getNext());
    }

    public void setColor(ColorHelper.Color color) {

        this.color = color;
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    public ColorHelper.Color getColor() {

        return this.color;
    }

    public void setNextColor() {

        setColor(this.color.getNext());
    }

    public Type getType() {

        return this.type;
    }

    public boolean showX() {

        if (this.axis == Axis.X_Y_Z || this.axis == Axis.X_Y || this.axis == Axis.X_Z || this.axis == Axis.X)
            return true;

        return false;
    }

    public boolean showY() {

        if (this.axis == Axis.X_Y_Z || this.axis == Axis.X_Y || this.axis == Axis.Y_Z || this.axis == Axis.Y)
            return true;

        return false;
    }

    public boolean showZ() {

        if (this.axis == Axis.X_Y_Z || this.axis == Axis.X_Z || this.axis == Axis.Y_Z || this.axis == Axis.Z)
            return true;

        return false;
    }

    /*****
     * NBT
     *****/
    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {

        super.writeToNBT(nbttagcompound);
        nbttagcompound.setInteger("Mode", this.axis.ordinal());
        nbttagcompound.setInteger("Color", this.color.ordinal());
        nbttagcompound.setInteger("Type", this.type.ordinal());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {

        super.readFromNBT(nbttagcompound);
        this.axis = Axis.VALID_AXIS_MODES[nbttagcompound.getInteger("Mode")];
        this.color = ColorHelper.Color.VALID_COLORS[nbttagcompound.getInteger("Color")];

        int ordinal = nbttagcompound.getInteger("Type");
        if (ordinal == 0)
            this.type = Type.AXES;
        else if (ordinal == 1)
            this.type = Type.PAIR;
        else
            this.type = Type.COORD;
    }

    /************
     * Packets
     ************/
    @Override
    public Packet getDescriptionPacket() {

        NBTTagCompound nbttagcompound = new NBTTagCompound();
        writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbttagcompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {

        readFromNBT(pkt.func_148857_g());
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    /**
     * Need to render the block when it is not in view.
     */
    @Override
    public AxisAlignedBB getRenderBoundingBox() {

        return AxisAlignedBB.getBoundingBox(xCoord - MAX_DISTANCE, yCoord - MAX_DISTANCE, zCoord - MAX_DISTANCE, xCoord + MAX_DISTANCE, yCoord + MAX_DISTANCE, zCoord + MAX_DISTANCE);
        //return INFINITE_EXTENT_AABB;
    }
}
