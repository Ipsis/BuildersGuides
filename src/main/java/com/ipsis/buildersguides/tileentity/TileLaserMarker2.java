package com.ipsis.buildersguides.tileentity;

import com.ipsis.buildersguides.util.*;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileLaserMarker2 extends TileEntity {

    public static final int DISTANCE = 64;
    private static final int UPDATE_FREQ = 20;
    private int currTicks;
    private ForgeDirection facing;
    private BlockPosition target;
    private BlockPosition center;

    public TileLaserMarker2() {

        facing = ForgeDirection.NORTH;
        target = null;
        center = null;
        currTicks = UPDATE_FREQ;
    }

    public void setFacing(ForgeDirection facing) {

        /* When you change the facing, need to recalculate the target */
        this.facing = facing;
        this.target = null;
        this.center = null;
        findTarget();
    }

    public ForgeDirection getFacing() {

        return this.facing;
    }

    public BlockPosition getTarget() {

        return target;
    }

    public BlockPosition getCenter() {

        return center;
    }

    public boolean hasValidTarget() {

        return target != null;
    }

    public void setTarget(int x, int y, int z) {

        if (!hasValidTarget()) {
            target = new BlockPosition(x, y, z);
        } else {
            target.x = x;
            target.y = y;
            target.z = z;
        }
        worldObj.markBlockForUpdate(xCoord,yCoord,zCoord);
    }

    private void clearTarget() {

        target = null;
        center = null;
        worldObj.markBlockForUpdate(xCoord,yCoord,zCoord);
    }

    /**
     * Update
     */
    private void findTarget() {

        clearTarget();
        BlockPosition t = BlockUtils.getFirstBlock(worldObj, this.xCoord, this.yCoord, this.zCoord, this.facing, DISTANCE, true);
        if (t != null) {

            setTarget(t.x, t.y, t.z);
            findCenter();
        }
    }

    private void findCenter() {

        if (!hasValidTarget())
            return;

        center = BlockUtils.getCenterBlock(this.xCoord, this.yCoord, this.zCoord, target.x, target.y, target.z, this.facing);
    }

    public void onRedstonePulse() {

        findTarget();
    }

    @Override
    public void updateEntity() {


    }

    /*****
     * NBT
     *****/
    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {

        super.writeToNBT(nbttagcompound);
        nbttagcompound.setInteger("Facing", this.facing.ordinal());

        if (target != null) {
            NBTTagCompound nbt2 = new NBTTagCompound();
            target.writeToNBT(nbt2);
            nbttagcompound.setTag("target", nbt2);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {

        super.readFromNBT(nbttagcompound);
        this.facing = ForgeDirection.getOrientation(nbttagcompound.getInteger("Facing"));

        if (nbttagcompound.hasKey("target")) {
            target = new BlockPosition(nbttagcompound.getCompoundTag("target"));
            findCenter();
        } else {
            clearTarget();
        }
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

        return AxisAlignedBB.getBoundingBox(xCoord - DISTANCE, yCoord - DISTANCE, zCoord - DISTANCE, xCoord + DISTANCE, yCoord + DISTANCE, zCoord + DISTANCE);
    }
}
