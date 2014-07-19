package com.ipsis.buildersguides.tileentity;

import com.ipsis.buildersguides.util.BlockPosition;
import com.ipsis.buildersguides.util.BlockUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;

public class TileRangeMarker2 extends TileEntity {

    public static final int DISTANCE = 64;
    private BlockPosition[] targets;
    private BlockPosition[] centers;
    private boolean scannedForTargets;

    public TileRangeMarker2() {

        targets = new BlockPosition[6];
        centers = new BlockPosition[6];
        scannedForTargets = false;
    }

    public BlockPosition getTarget(int id) {
        return getTarget(ForgeDirection.getOrientation(id));
    }

    public BlockPosition getTarget(ForgeDirection facing) {

        if (facing == ForgeDirection.UNKNOWN)
            return null;

        return targets[facing.ordinal()];
    }

    public BlockPosition getCenter(int id) {
        return getCenter(ForgeDirection.getOrientation(id));
    }

    public BlockPosition getCenter(ForgeDirection facing) {

        if (facing == ForgeDirection.UNKNOWN)
            return null;

        return centers[facing.ordinal()];
    }

    public void setTarget(int id, int x, int y, int z) {
        setTarget(ForgeDirection.getOrientation(id), x, y, z);
    }

    public void setTarget(ForgeDirection facing, int x, int y, int z) {

        if (facing == ForgeDirection.UNKNOWN)
            return;

        BlockPosition p = targets[facing.ordinal()];
        if (targets[facing.ordinal()] == null) {
            targets[facing.ordinal()] = new BlockPosition(x, y, z);
        } else {
            targets[facing.ordinal()].x = x;
            targets[facing.ordinal()].y = y;
            targets[facing.ordinal()].z = z;
        }

        scannedForTargets = true;
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    public void setCenter(int id, int x, int y, int z) {
        setCenter(ForgeDirection.getOrientation(id), x, y, z);
    }

    public void setCenter(ForgeDirection facing, int x, int y, int z) {

        if (facing == ForgeDirection.UNKNOWN)
            return;

        if (centers[facing.ordinal()] == null) {
            centers[facing.ordinal()] = new BlockPosition(x, y, z);
        } else {
            centers[facing.ordinal()].x = x;
            centers[facing.ordinal()].y = y;
            centers[facing.ordinal()].z = z;
        }
    }

    public void clearTargets() {

        for (int i = 0; i < targets.length; i++) {
            targets[i] = null;
            centers[i] = null;
        }

        scannedForTargets = false;
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    private void findCenter(int id, int x, int y, int z) {
        findCenter(ForgeDirection.getOrientation(id), x, y, z);
    }

    private void findCenter(ForgeDirection dir, int x, int y, int z) {

        BlockPosition c = BlockUtils.getCenterBlock(this.xCoord, this.yCoord, this.zCoord, x, y, z, dir);
        if (c != null)
            setCenter(dir, c.x, c.y, c.z);
    }

    public void findTargets() {

        /* One for each direction */
        for (int i = 0; i < targets.length; i++) {

            BlockPosition t = BlockUtils.getFirstBlock(worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.getOrientation(i), DISTANCE, false);
            if (t != null) {

                setTarget(i, t.x, t.y, t.z);
                findCenter(i, t.x, t.y, t.z);
            }
        }
    }

    @Override
    public void updateEntity() {

        if (worldObj.isRemote)
            return;

        if (scannedForTargets == false)
            findTargets();
    }

    /*****
     * NBT
     *****/
    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {

        super.writeToNBT(nbttagcompound);

        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < targets.length; i++) {

            BlockPosition t = getTarget(i);
            if (t != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Direction", (byte) i);
                t.writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        nbttagcompound.setTag("Targets", nbttaglist);
    }


    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {

        super.readFromNBT(nbttagcompound);

        NBTTagList nbttaglist = nbttagcompound.getTagList("Targets", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < nbttaglist.tagCount(); i++) {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            int id = nbttagcompound1.getByte("Direction") & 0xff;
            if (id >= 0 && id < targets.length) {
                BlockPosition t = new BlockPosition(nbttagcompound1);
                setTarget(id, t.x, t.y, t.z);
                findCenter(id, t.x, t.y, t.z);
            }
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
}
