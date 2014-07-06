package com.ipsis.buildersguides.tileentity;

import com.ipsis.buildersguides.util.AxisHelper;
import com.ipsis.buildersguides.util.ColorHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileAxisMarker extends TileEntity {

    public static final int MAX_DISTANCE = 64;

    private AxisHelper.Axis axis;
    private ColorHelper.Color color;

    public TileAxisMarker() {

        this.axis = AxisHelper.Axis.X_Y_Z;
        this.color = ColorHelper.Color.BLACK;
    }

    public AxisHelper.Axis getAxis() {

        return this.axis;
    }

    public void setAxis(AxisHelper.Axis axis) {

        this.axis = axis;
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    public ColorHelper.Color getColor() {

        return color;
    }

    public void setColor(ColorHelper.Color color) {

        this.color=color;
        worldObj.markBlockForUpdate(xCoord,yCoord,zCoord);
    }

    public void setNextAxis() {

        setAxis(this.axis.getNext());
    }

    public void setNextColor() {

        setColor(this.color.getNext());
    }

    /*****
     * NBT
     *****/
    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {

        super.writeToNBT(nbttagcompound);
        nbttagcompound.setInteger("Mode", this.axis.ordinal());
        nbttagcompound.setInteger("Color", this.color.ordinal());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {

        super.readFromNBT(nbttagcompound);
        this.axis = AxisHelper.Axis.VALID_AXIS_MODES[nbttagcompound.getInteger("Mode")];
        this.color = ColorHelper.Color.VALID_COLORS[nbttagcompound.getInteger("Color")];
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
    }
}
