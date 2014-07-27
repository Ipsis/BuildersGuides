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
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TileAdvancedMarker extends TileEntity implements IWrenchable {

    private static final int MAX_DISTANCE = 64;
    private BlockPosition[] targets;
    private AdvancedMode advancedMode;
    private BGColor color;

    /* Client Render Data */
    private int dx;
    private int dy;
    private int dz;
    private StructureMode structureMode;
    private boolean isRenderDataValid;
    private ArrayList<BlockPosition> centers;


    {
        this.targets = new BlockPosition[ForgeDirection.VALID_DIRECTIONS.length];
        this.advancedMode = AdvancedMode.HORIZ_PLANE;
        this.dx = 0;
        this.dy = 0;
        this.dz = 0;
        this.structureMode = StructureMode.NONE;
        this.isRenderDataValid = false;
        this.color = BGColor.BLACK;
    }

    public TileAdvancedMarker() {


    }

    public enum AdvancedMode {

        HORIZ_PLANE,
        VERT_PLANE,
        CUBOID;

        public static AdvancedMode[] VALID_MODES = { HORIZ_PLANE, VERT_PLANE, CUBOID };

        public static AdvancedMode getMode(int id) {

            if (id >= 0 && id < VALID_MODES.length)
                return VALID_MODES[id];

            return HORIZ_PLANE;
        }

        public static AdvancedMode getNext(AdvancedMode mode) {

            int next = mode.ordinal() + 1;
            if (next >= VALID_MODES.length)
                next = 0;

            return VALID_MODES[next];
        }
    }

    public enum StructureMode {

        HORIZ_X_Z,
        VERT_X_Y,
        VERT_Y_Z,
        CUBOID,
        NONE;

        public static StructureMode[] VALID_MODES = { HORIZ_X_Z, VERT_X_Y, VERT_Y_Z, CUBOID, NONE };

        public static StructureMode getMode(int id) {

            if (id >= 0 && id < VALID_MODES.length)
                return VALID_MODES[id];

            return NONE;
        }
    }

    /**
     * Accessors
     */
    public AdvancedMode getAdvancedMode() {
        return this.advancedMode;
    }

    public int getDx() {
        return this.dx;
    }

    public int getDy() {
        return this.dy;
    }

    public int getDz() {
        return this.dz;
    }

    public StructureMode getStructureMode() {
        return this.structureMode;
    }

    public boolean getIsRenderDataValid() {
        return this.isRenderDataValid;
    }

    public BGColor getColor() {

        return this.color;
    }

    public void setColor(BGColor color) {

        this.color = color;
    }

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


    /**
     * IWrenchable
     */
    @Override
    public void shiftLeftWrench() {

        this.advancedMode = AdvancedMode.getNext(this.advancedMode);
        LogHelper.info("shiftLeftWrench: " + this.advancedMode);
        this.findTargets();
    }

    private void scanTargets() {

        clearTargets();
        this.structureMode = StructureMode.NONE;

        if (this.advancedMode == AdvancedMode.HORIZ_PLANE)
            findTargetsHPlane();
        else if (this.advancedMode == AdvancedMode.VERT_PLANE || advancedMode == AdvancedMode.CUBOID)
            findTargetsOther();
    }

    public void findTargets() {

        if (worldObj.isRemote)
            return;

        scanTargets();
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    /**
     * Target Location
     */
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
                setTarget(p.orientation, p);
            else
                valid = false;

            p = locker.getZ();
            if (p != null)
                setTarget(p.orientation, p);
            else
                valid = false;

            if(valid)
                this.structureMode = StructureMode.HORIZ_X_Z;
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

                if (this.advancedMode == AdvancedMode.VERT_PLANE && ((locker.isXLocked() && DirectionHelper.isZ(dir)) || (locker.isZLocked() && DirectionHelper.isX(dir))))
                    continue;

                BlockPosition bp = new BlockPosition(this.xCoord, this.yCoord, this.zCoord, dir);
                bp.moveForwards(i);
                Block b = worldObj.getBlock(bp.x, bp.y, bp.z);
                if (b instanceof BlockTarget) {
                    locker.lock(dir, bp.x, bp.y, bp.z);

                    if (this.advancedMode == AdvancedMode.VERT_PLANE && (locker.isValidXYPlane() || locker.isValidYZPlane()))
                        valid = true;
                    else if (this.advancedMode == AdvancedMode.CUBOID && locker.isValidCuboid())
                        valid = true;
                }
            }

            if (valid) {

                BlockPosition x, y, z;
                x = locker.getX();
                y = locker.getY();
                z = locker.getZ();

                if (this.advancedMode == AdvancedMode.VERT_PLANE) {
                    if (x != null && y != null && z == null) {
                        setTarget(x.orientation, x);
                        setTarget(y.orientation, y);
                        this.structureMode = StructureMode.VERT_X_Y;
                    } else if (x == null && y != null && z != null) {
                        setTarget(y.orientation, y);
                        setTarget(z.orientation, z);
                        this.structureMode = StructureMode.VERT_Y_Z;
                    } else {
                        valid = false;
                    }
                } else if (this.advancedMode == AdvancedMode.CUBOID) {
                    if (x != null && y != null && z != null) {
                        setTarget(x.orientation, x);
                        setTarget(y.orientation, y);
                        setTarget(z.orientation, z);
                        this.structureMode = StructureMode.CUBOID;
                    } else {
                        valid = false;
                    }
                }
            }
        }

        return valid;
    }


    /**
     * Client Render
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

    private void updateRenderData() {

        if (!worldObj.isRemote)
            return;

        this.dx = 0;
        this.dy = 0;
        this.dz = 0;
        this.isRenderDataValid = false;

        if (this.structureMode == StructureMode.NONE)
            return;


        if (getX() != null)
            this.dx = getX().x - this.xCoord;
        if (getY() != null)
            this.dy = getY().y - this.yCoord;
        if (getZ() != null)
            this.dz = getZ().z - this.zCoord;

        if (this.structureMode == StructureMode.HORIZ_X_Z && this.dx != 0 && this.dy == 0 && this.dz != 0)
            this.isRenderDataValid = true;
        else if (this.structureMode == StructureMode.VERT_Y_Z && this.dx == 0 && this.dy != 0 && this.dz != 0)
            this.isRenderDataValid = true;
        else if (this.structureMode == StructureMode.VERT_X_Y && this.dx != 0 && this.dy != 0 && this.dz == 0)
            this.isRenderDataValid = true;
        else if (this.structureMode == StructureMode.CUBOID && this.dx != 0 && this.dy != 0 && this.dz != 0)
            this.isRenderDataValid = true;

        if (this.isRenderDataValid)
            findCenters();
        else
            centers = null;
    }

    public List<BlockPosition> getCenters() {
        return this.centers;
    }

    private void findCenters() {

        centers = new ArrayList<BlockPosition>();

        if (this.structureMode == StructureMode.HORIZ_X_Z) {

            BlockPosition p;
            BlockPosition c = new BlockPosition(this);

            /* X axis */
            p = BlockUtils.getCenterBlock(this.xCoord, this.yCoord, this.zCoord,
                this.getX().x, this.getX().y, this.getX().z, this.getX().orientation);
            if (p != null) {
                centers.add(p);
                BlockPosition ap = p.copy();
                ap.z = this.getZ().z;
                centers.add(ap);
                c.x = p.x;
            }

            /* Z axis */
            p = BlockUtils.getCenterBlock(this.xCoord, this.yCoord, this.zCoord,
                    this.getZ().x, this.getZ().y, this.getZ().z, this.getZ().orientation);
            if (p != null) {
                centers.add(p);
                BlockPosition ap = p.copy();
                ap.x = this.getX().x;
                centers.add(ap);
                c.z = p.z;
            }

            if(!c.equals(new BlockPosition(this)))
                centers.add(c);

        } else if (this.structureMode == StructureMode.VERT_X_Y) {

            BlockPosition p;
            BlockPosition c = new BlockPosition(this);

            /* X axis */
            p = BlockUtils.getCenterBlock(this.xCoord, this.yCoord, this.zCoord,
                    this.getX().x, this.getX().y, this.getX().z, this.getX().orientation);
            if (p != null) {
                centers.add(p);
                BlockPosition ap = p.copy();
                ap.y = this.getY().y;
                centers.add(ap);
                c.x = p.x;
            }

            /* Y axis */
            p = BlockUtils.getCenterBlock(this.xCoord, this.yCoord, this.zCoord,
                    this.getY().x, this.getY().y, this.getY().z, this.getY().orientation);
            if (p != null) {
                centers.add(p);
                BlockPosition ap = p.copy();
                ap.x = this.getX().x;
                centers.add(ap);
                c.y = p.y;
            }

            if(!c.equals(new BlockPosition(this)))
                centers.add(c);

        } else if (this.structureMode == StructureMode.VERT_Y_Z) {

            BlockPosition p;
            BlockPosition c = new BlockPosition(this);

            /* Y Axis */
            p = BlockUtils.getCenterBlock(this.xCoord, this.yCoord, this.zCoord,
                    this.getY().x, this.getY().y, this.getY().z, this.getY().orientation);
            if (p != null) {
                centers.add(p);
                BlockPosition ap = p.copy();
                ap.z = this.getZ().z;
                centers.add(ap);
                c.y = p.y;
            }

            /* Z Axis */
            p = BlockUtils.getCenterBlock(this.xCoord, this.yCoord, this.zCoord,
                    this.getZ().x, this.getZ().y, this.getZ().z, this.getZ().orientation);
            if (p != null) {
                centers.add(p);
                BlockPosition ap = p.copy();
                ap.y = this.getY().y;
                centers.add(ap);
                c.z = p.z;
            }

            if(!c.equals(new BlockPosition(this)))
                centers.add(c);

        } else if (this.structureMode == StructureMode.CUBOID) {

        }
    }

    public void onRedstonePulse() {

        findTargets();
    }

    /**
     * NBT
     */
    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {

        super.writeToNBT(nbttagcompound);

        nbttagcompound.setInteger("Color", this.color.ordinal());
        nbttagcompound.setInteger("AdvancedMode", this.advancedMode.ordinal());
        nbttagcompound.setInteger("StructureMode", this.structureMode.ordinal());

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

        this.color = BGColor.getColor(nbttagcompound.getInteger("Color"));
        //this.facing = ForgeDirection.getOrientation(nbttagcompound.getInteger("Facing"));
        this.advancedMode = AdvancedMode.getMode(nbttagcompound.getInteger("AdvancedMode"));
        this.structureMode = StructureMode.getMode(nbttagcompound.getInteger("StructureMode"));

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
    }

    /**
     * Description Packet
     */
    public Packet getDescriptionPacket() {

        NBTTagCompound nbttagcompound = new NBTTagCompound();
        writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbttagcompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {

        readFromNBT(pkt.func_148857_g());
        updateRenderData();
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
}
