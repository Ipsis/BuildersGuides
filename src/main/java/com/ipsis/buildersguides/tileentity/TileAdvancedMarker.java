package com.ipsis.buildersguides.tileentity;

import com.ipsis.buildersguides.block.BlockTarget;
import com.ipsis.buildersguides.util.*;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;

public class TileAdvancedMarker extends TileEntity implements IWrenchable {

    private Mode mode;
    private static final int MAX_DISTANCE = 64;
    private BlockPosition[] targets;
    public boolean canRender;
    public int dx;
    public int dy;
    public int dz;


    public TileAdvancedMarker() {

        this.mode = Mode.HORIZ_PLANE;
        targets = new BlockPosition[ForgeDirection.VALID_DIRECTIONS.length];
        this.canRender = false;
    }

    /**
     * Accessors
     */
    public BlockPosition getTarget(ForgeDirection dir) {

        return targets[dir.ordinal()];
    }

    protected void setTarget(ForgeDirection dir, BlockPosition pos) {

        if (targets[dir.ordinal()] == null) {
            targets[dir.ordinal()] = new BlockPosition(pos);
        } else {
            targets[dir.ordinal()].x = pos.x;
            targets[dir.ordinal()].y = pos.y;
            targets[dir.ordinal()].z = pos.z;
            targets[dir.ordinal()].orientation = pos.orientation;
        }
    }

    private void clearTarget(ForgeDirection dir) {

        targets[dir.ordinal()] = null;
    }

    private void clearTargets() {

        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
            clearTarget(dir);
    }

    @Override
    public void shiftLeftWrench() {

        /* change the mode */
        if (this.mode == Mode.HORIZ_PLANE)
            this.mode = Mode.VERT_PLANE;
        else if (this.mode == Mode.VERT_PLANE)
            this.mode = Mode.CUBOID;
        else
            this.mode = Mode.HORIZ_PLANE;

        this.findTargets();
    }

    public Mode getMode() {

        return this.mode;
    }

    public enum Mode {

        HORIZ_PLANE,
        VERT_PLANE,
        CUBOID;

        public static Mode getMode(int id) {

            if (id == HORIZ_PLANE.ordinal())
                return HORIZ_PLANE;
            else if (id == VERT_PLANE.ordinal())
                return VERT_PLANE;
            else
                return CUBOID;
        }
    }

    private boolean findTargetsHPlane() {

        boolean valid = false;
        TargetLocker locker = new TargetLocker();

        for (int i = 1; i < MAX_DISTANCE && !valid; i++) {
            for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {

                if (dir == ForgeDirection.DOWN || dir == ForgeDirection.UP)
                    continue;

                if (locker.isLocked(dir) || locker.isOppositeLocked(dir))
                    continue;

                BlockPosition bp = new BlockPosition(this.xCoord, this.yCoord, this.zCoord, dir);
                bp.moveForwards(i);
                Block b = worldObj.getBlock(bp.x, bp.y, bp.z);
                if (b instanceof BlockTarget) {
                    locker.lock(dir, bp.x, bp.y, bp.z);

                    if (locker.isValidXZPlane())
                        valid = true;
                }
            }
        }

        if (valid) {

            BlockPosition p;

            p = locker.getX();
            if (p != null)
                this.setTarget(p.orientation, p);

            p = locker.getY();
            if (p != null)
                this.setTarget(p.orientation, p);

            p = locker.getZ();
            if (p != null)
                this.setTarget(p.orientation, p);
        }

        return valid;
    }

    private boolean findTargetsOther() {

        boolean valid = false;
        TargetLocker locker = new TargetLocker();

        for (int i = 1; i < MAX_DISTANCE && !valid; i++) {
            for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {

                if (locker.isLocked(dir) || locker.isOppositeLocked(dir))
                    continue;

                if (this.mode == Mode.VERT_PLANE && ((locker.isXLocked() && DirectionHelper.isZ(dir)) || (locker.isZLocked() && DirectionHelper.isX(dir))))
                        continue;

                BlockPosition bp = new BlockPosition(this.xCoord, this.yCoord, this.zCoord, dir);
                bp.moveForwards(i);
                Block b = worldObj.getBlock(bp.x, bp.y, bp.z);
                if (b instanceof BlockTarget) {
                    locker.lock(dir, bp.x, bp.y, bp.z);

                    if (this.mode == Mode.VERT_PLANE && (locker.isValidXYPlane() || locker.isValidYZPlane()))
                        valid = true;
                    else if (this.mode == Mode.CUBOID && locker.isValidCuboid())
                        valid = true;
                }
            }
        }

        if (valid) {

            BlockPosition p;

            p = locker.getX();
            if (p != null)
                this.setTarget(p.orientation, p);

            p = locker.getY();
            if (p != null)
                this.setTarget(p.orientation, p);

            p = locker.getZ();
            if (p != null)
                this.setTarget(p.orientation, p);
        }

        return valid;
    }

    public void findTargets() {

        this.clearTargets();

        boolean valid = false;
        if (this.mode == Mode.HORIZ_PLANE)
            valid = findTargetsHPlane();
        else if (this.mode == Mode.VERT_PLANE)
            valid = findTargetsOther();
        else if (this.mode == Mode.CUBOID)
            valid = findTargetsOther();

        if (valid == true) {

            for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
                BlockPosition p = this.getTarget(dir);
                if (p != null)
                    LogHelper.info("Found target " + p);
            }

            createRenderData();

        } else {
            this.clearTargets();
        }
    }

    /**
     * Targets to render positions
     */

    private BlockPosition getX() {

        if (this.getTarget(ForgeDirection.EAST) != null)
            return this.getTarget(ForgeDirection.EAST);
        else
            return this.getTarget(ForgeDirection.WEST);
    }

    private BlockPosition getY() {

        if (this.getTarget(ForgeDirection.UP) != null)
            return this.getTarget(ForgeDirection.UP);
        else
            return this.getTarget(ForgeDirection.DOWN);
    }

    private BlockPosition getZ() {

        if (this.getTarget(ForgeDirection.SOUTH) != null)
            return this.getTarget(ForgeDirection.SOUTH);
        else
            return this.getTarget(ForgeDirection.NORTH);
    }

    private void createRenderData() {

        this.dx = 0;
        this.dy = 0;
        this.dz = 0;

        this.canRender = false;

        if (getX() != null)
            this.dx = getX().x - this.xCoord;

        if (getY() != null)
            this.dy = getY().y - this.yCoord;

        if (getZ() != null)
            this.dz = getZ().z - this.zCoord;

        if (this.dx != 0 || this.dy != 0 || this.dz != 0)
            this.canRender = true;
    }

    /**
     * NBT
     */
    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {

        super.writeToNBT(nbttagcompound);

        //nbttagcompound.setInteger("Color", this.color.ordinal());
        //nbttagcompound.setInteger("Facing", this.facing.ordinal());
        nbttagcompound.setInteger("Mode", this.mode.ordinal());

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

        this.clearTargets();

        //this.color = BGColor.getColor(nbttagcompound.getInteger("Color"));
        //this.facing = ForgeDirection.getOrientation(nbttagcompound.getInteger("Facing"));
        this.mode = Mode.getMode(nbttagcompound.getInteger("Mode"));

        NBTTagList nbttaglist = nbttagcompound.getTagList("Targets", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < nbttaglist.tagCount(); i++) {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            int id = nbttagcompound1.getByte("Direction") & 0xff;
            if (id >= 0 && id < ForgeDirection.VALID_DIRECTIONS.length) {

                BlockPosition t = new BlockPosition(nbttagcompound1);
                ForgeDirection d = ForgeDirection.getOrientation(id);
                setTarget(d, t);
            }
        }

        createRenderData();
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

}
