package com.ipsis.buildersguides.tileentity;

import com.ipsis.buildersguides.block.BlockTarget;
import com.ipsis.buildersguides.util.*;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;

public class TileBaseMarker extends TileEntity {

    protected static final int MAX_DISTANCE = 64;

    private BGColor color;
    private BlockPosition target;
    private BlockPosition center;
    private boolean useTargetBlock;
    private ForgeDirection facing;

    public TileBaseMarker(boolean useTargetBlock) {

        this.useTargetBlock = useTargetBlock;
        this.facing = ForgeDirection.SOUTH;
        this.color = BGColor.BLACK;
    }

    public BGColor getColor() {

        return this.color;
    }

    public void setColor(BGColor color) {

        this.color = color;
    }

    public void setFacing(ForgeDirection dir) {

        this.facing = dir;
    }

    public ForgeDirection getFacing() {

        return this.facing;
    }

    public BlockPosition getTarget(ForgeDirection dir) {

        if (this.facing == dir)
            return this.target;

        return null;
    }

    protected void setTarget(ForgeDirection dir, BlockPosition pos) {

        if (this.facing != dir)
            return;

        if (this.target == null) {
            this.target = new BlockPosition(pos);
        } else {
            this.target.x = pos.x;
            this.target.y = pos.y;
            this.target.z = pos.z;
            this.target.orientation = pos.orientation;
        }
    }

    public BlockPosition getCenter(ForgeDirection dir) {

        if (isValidDirection(dir))
            return this.center;

        return null;
    }

    protected void setCenter(ForgeDirection dir, BlockPosition pos) {

        if (!isValidDirection(dir))
            return;

        if (this.center == null) {
            this.center = new BlockPosition(pos);
        } else {
            this.center.x = pos.x;
            this.center.y = pos.y;
            this.center.z = pos.z;
            this.center.orientation = pos.orientation;
        }
    }

    public boolean hasTarget() {

        return this.target != null;
    }

    public boolean hasTarget(ForgeDirection dir) {

        return this.facing != null ? hasTarget() : false;
    }

    public boolean hasCenter() {

        return this.center != null;
    }

    public boolean hasCenter(ForgeDirection dir) {

        return this.facing != null ? hasCenter() : false;
    }

    /**
     * Clear
     */
    protected void clearCenter(ForgeDirection dir) {

        if (!isValidDirection(dir))
            return;

        this.center = null;
    }

    protected void clearCenters() {

        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
            clearCenter(dir);
    }

    protected void clearTarget(ForgeDirection dir) {

        if (!isValidDirection(dir))
            return;

        this.target = null;
    }

    protected void clearTargets() {

        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
            clearTarget(dir);
    }

    /**
     * Find
     */
    public boolean isValidDirection(ForgeDirection dir) {

        return this.facing == dir;
    }

    protected void findCenter(ForgeDirection dir) {

        clearCenter(dir);
        if (!isValidDirection(dir))
            return;

        BlockPosition t = getTarget(dir);
        if (t == null)
            return;

        BlockPosition c = BlockUtils.getCenterBlock(
                            this.xCoord, this.yCoord, this.zCoord,
                            t.x, t.y, t.z, dir);

        if (c != null)
            setCenter(dir, c);
    }

    public void findTargets(EntityPlayer player) {

        clearTargets();
        clearCenters();
        for (ForgeDirection d : ForgeDirection.VALID_DIRECTIONS) {

            if (!isValidDirection(d))
                continue;

            BlockPosition b = BlockUtils.getFirstBlock(
                                worldObj, this.xCoord, this.yCoord, this.zCoord,
                                d, MAX_DISTANCE, this.useTargetBlock);

            if (b != null) {
                setTarget(d, b);
                findCenter(d);

                ChatHelper.displayTargetMessage(player, b, d, this.useTargetBlock);
            }
        }

        worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }

    /**
     * NBT
     */
    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {

        super.writeToNBT(nbttagcompound);

        nbttagcompound.setInteger("Color", this.color.ordinal());
        nbttagcompound.setInteger("Facing", this.facing.ordinal());

        NBTTagList nbttaglist = new NBTTagList();
        for (ForgeDirection d : ForgeDirection.VALID_DIRECTIONS) {

            BlockPosition t = getTarget(d);
            if (t != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Direction", (byte)d.ordinal());
                t.writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        nbttagcompound.setTag("Targets", nbttaglist);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {

        super.readFromNBT(nbttagcompound);

        this.color = BGColor.getColor(nbttagcompound.getInteger("Color"));
        this.facing = ForgeDirection.getOrientation(nbttagcompound.getInteger("Facing"));

        NBTTagList nbttaglist = nbttagcompound.getTagList("Targets", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < nbttaglist.tagCount(); i++) {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            int id = nbttagcompound1.getByte("Direction") & 0xff;
            if (id >= 0 && id < ForgeDirection.VALID_DIRECTIONS.length) {

                BlockPosition t = new BlockPosition(nbttagcompound1);
                ForgeDirection d = ForgeDirection.getOrientation(id);
                setTarget(d, t);
                findCenter(d);
            }
        }
    }


    /**
     * Packet Update
     */
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

        return AxisAlignedBB.getBoundingBox(
                xCoord - MAX_DISTANCE, yCoord - MAX_DISTANCE, zCoord - MAX_DISTANCE,
                xCoord + MAX_DISTANCE, yCoord + MAX_DISTANCE, zCoord + MAX_DISTANCE);
    }

    public void rotateAround(ForgeDirection axis, EntityPlayer player) {

        /* NOOP */
    }


}
