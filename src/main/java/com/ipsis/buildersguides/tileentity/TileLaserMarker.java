package com.ipsis.buildersguides.tileentity;

import com.ipsis.buildersguides.block.BGBlocks;
import com.ipsis.buildersguides.block.BlockTargetMarker;
import com.ipsis.buildersguides.util.AxisHelper;
import com.ipsis.buildersguides.util.BlockPosition;
import com.ipsis.buildersguides.util.ColorHelper;
import com.ipsis.buildersguides.util.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileLaserMarker extends TileEntity {

    public static final int DISTANCE = 64;
    private static final int UPDATE_FREQ = 20;
    private ForgeDirection facing;
    private BlockPosition target;
    private BlockPosition center;

    public TileLaserMarker() {

        facing = ForgeDirection.NORTH;
        target = null;
        center = null;
    }

    public void setFacing(ForgeDirection facing) {

        /* We you change the facing, need to recalculate the target */
        this.facing = facing;
        this.target = null;
        this.center = null;
        worldObj.markBlockForUpdate(xCoord,yCoord,zCoord);
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

    public void setTarget(int x, int y, int z) {

        if (target == null) {
            target = new BlockPosition(x, y, z);
        } else {
            target.x = x;
            target.y = y;
            target.z = z;
        }
        worldObj.markBlockForUpdate(xCoord,yCoord,zCoord);
    }

    /**
     * Update
     */
    private void findTarget() {

        if (target != null)
            return;

        /* Must be at least one block away */
        BlockPosition curr = new BlockPosition(this.xCoord, this.yCoord, this.zCoord, this.facing);
        curr.moveForwards(1);

        /* Must be at least one block away */
        for (int i = 1; i <= DISTANCE; i++) {

            curr.moveForwards(1);
            Block b = worldObj.getBlock(curr.x, curr.y, curr.z);
            if (b != null && b instanceof BlockTargetMarker) {

                setTarget(curr.x, curr.y, curr.z);
                findCenter();
                break;
            }
        }
    }

    private void findCenter() {

        if (target == null)
            return;

        /* We only have a center block when the space between the two is odd */
        int dx = Math.abs(this.xCoord - target.x);
        int dy = Math.abs(this.yCoord - target.y);
        int dz = Math.abs(this.zCoord - target.z);

        center = new BlockPosition(this.xCoord, this.yCoord, this.zCoord, this.facing);
        if (this.facing == ForgeDirection.UP || this.facing == ForgeDirection.DOWN) {

            if ((dy - 1) % 2 != 0)
                center.moveForwards(((dy - 1) / 2) + 1);

        } else if (this.facing == ForgeDirection.EAST || this.facing == ForgeDirection.WEST) {

            if ((dx - 1) % 2 != 0)
                center.moveForwards(((dx - 1) / 2) + 1);

        } else if (this.facing == ForgeDirection.NORTH || this.facing == ForgeDirection.SOUTH) {

            if ((dz - 1) % 2 != 0)
                center.moveForwards(((dz - 1) / 2) + 1);
        }

        /* If there was no center then clear it so we dont try and render it */
        if (center.equals(new BlockPosition(this.xCoord, this.yCoord, this.zCoord, this.facing)))
            center = null;

    }

    @Override
    public void updateEntity() {

        if (worldObj.isRemote)
            return;

        /* TODO slow down the update */
        if (target == null)
            findTarget();
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
