package ipsis.buildersguides.tileentity;

import ipsis.buildersguides.manager.MarkerManager;
import ipsis.buildersguides.manager.MarkerType;
import ipsis.buildersguides.network.PacketHandlerBG;
import ipsis.buildersguides.network.message.MessageTileEntityMarker;
import ipsis.buildersguides.util.ColorBG;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;

public class TileEntityMarker extends TileEntity {

    private MarkerType type;
    private EnumFacing facing;
    private ColorBG color;
    private int[] v; // Generic value per direction
    private BlockPos[] target; // Target position per direction
    private int mode; // Type specific mode

    public MarkerType getType() {
        return type;
    }

    public void setType(MarkerType type) {
        this.type = type;
    }

    public EnumFacing getFacing() {
        return facing;
    }

    public void setFacing(EnumFacing facing) {
        this.facing = facing;
    }

    public ColorBG getColor() {
        return color;
    }

    public void setColor(ColorBG color) {
        this.color = color;
    }

    public int getV(EnumFacing f) {
        return v[f.ordinal()];
    }

    public void setV(EnumFacing f, int val) {
        v[f.ordinal()] = val;
    }

    public boolean hasValidV(EnumFacing f) {
        return v[f.ordinal()] != 0;
    }

    public int getMode() { return mode; }
    public void setMode(int mode) { this.mode = mode; }

    public BlockPos getTarget(EnumFacing f) {
        if (target[f.ordinal()] == null)
            return new BlockPos(this.getPos());
        else
            return target[f.ordinal()];
    }
    public void setTarget(EnumFacing f, BlockPos p) { target[f.ordinal()] = new BlockPos(p); }
    public boolean hasTarget(EnumFacing f) { return target[f.ordinal()] != null && !getPos().equals(target[f.ordinal()]); }
    public void clearTarget(EnumFacing f) { target[f.ordinal()] = null; }

    public boolean isFaceEnabled(EnumFacing f) {
        return MarkerManager.isFaceEnabled(this, f);
    }


    public TileEntityMarker() {

        facing = EnumFacing.SOUTH;
        color = ColorBG.WHITE;
        clientData = new ClientData();

        resetToType(MarkerType.BLANK);
    }

    public void resetToType(MarkerType type) {

        this.type = type;
        mode = 0;

        v = new int[6];
        for (int i = 0; i < 6; i++)
            v[i] = 0;

        target = new BlockPos[6];
        clientData.reset();

        MarkerManager.initServerMarker(this);
    }

    /**
     * Rotate the tile and the data
     * @param side - the side the player hit
     */
    public void rotateTile(EnumFacing side) {

        int oldV[] = v.clone();
        BlockPos[] oldTarget = target.clone();
        for (EnumFacing f : EnumFacing.VALUES) {

            EnumFacing newF = f.rotateAround(side.getAxis());
            v[f.ordinal()] = oldV[newF.ordinal()];
            target[f.ordinal()] = oldTarget[newF.ordinal()];
        }
        facing = facing.rotateAround(side.getAxis());
    }

    @Override
    public Packet getDescriptionPacket() {

        return PacketHandlerBG.INSTANCE.getPacketFrom(new MessageTileEntityMarker(this));
    }

    private static final int MAX_DISTANCE = 128;
    private static final int MAX_WORLD_HEIGHT = 256;

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {

        return INFINITE_EXTENT_AABB;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {

        return (double)(256 * 256);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("TE Location: ").append(getPos());
        s.append(type).append(":").append(facing).append(":").append(color).append(":").append(mode).append("\n");
        for (EnumFacing f : EnumFacing.VALUES)
            s.append(f).append(":").append(v[f.ordinal()]).append(" ");
        s.append("\n");
        for (EnumFacing f : EnumFacing.VALUES)
            if (hasTarget(f) )
                s.append(f).append(":").append(target[f.ordinal()]).append(" ");
            else
                s.append(f).append(":none ");
        return s.toString();
    }

    /**
     * NBT
     * Blocklist and centerlist are not handled on the server
     */
    public static final String NBT_TYPE = "MarkerType";
    public static final String NBT_FACING = "Facing";
    public static final String NBT_MODE = "Mode";
    public static final String NBT_COLOR = "Color";
    public static final String NBT_DIRECTION = "Direction";
    public static final String NBT_V = "V";
    public static final String NBT_V_LIST = "VList";
    public static final String NBT_TARGET_LIST = "TargetList";

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setByte(NBT_TYPE, (byte)type.ordinal());
        compound.setByte(NBT_FACING, (byte)facing.ordinal());
        compound.setByte(NBT_MODE, (byte)mode);
        compound.setByte(NBT_COLOR, (byte)color.ordinal());

        NBTTagList tagList = new NBTTagList();
        for (EnumFacing f : EnumFacing.values()) {
            NBTTagCompound nbtTagCompound = new NBTTagCompound();
            nbtTagCompound.setByte(NBT_DIRECTION, (byte)f.ordinal());
            nbtTagCompound.setInteger(NBT_V, getV(f));
            tagList.appendTag(nbtTagCompound);
        }
        compound.setTag(NBT_V_LIST, tagList);

        tagList = new NBTTagList();
        for (EnumFacing f : EnumFacing.values()) {
            if (hasTarget(f)) {
                NBTTagCompound nbtTagCompound = new NBTTagCompound();
                nbtTagCompound.setByte(NBT_DIRECTION, (byte)f.ordinal());
                nbtTagCompound.setInteger("X", getTarget(f).getX());
                nbtTagCompound.setInteger("Y", getTarget(f).getY());
                nbtTagCompound.setInteger("Z", getTarget(f).getZ());
                tagList.appendTag(nbtTagCompound);
            }
        }
        compound.setTag(NBT_TARGET_LIST, tagList);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        for (EnumFacing f : EnumFacing.values())
            clearTarget(f);

        type = MarkerType.getMarkerType(compound.getByte(NBT_TYPE));
        facing = EnumFacing.getFront(compound.getByte(NBT_FACING));
        mode = compound.getByte(NBT_MODE);
        color = ColorBG.getColor(compound.getByte(NBT_COLOR));

        NBTTagList tagList = compound.getTagList(NBT_V_LIST, Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
            EnumFacing f = EnumFacing.getFront(tagCompound.getByte(NBT_DIRECTION));
            setV(f, tagCompound.getInteger(NBT_V));
        }

        tagList = compound.getTagList(NBT_TARGET_LIST, Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
            EnumFacing f = EnumFacing.getFront(tagCompound.getByte(NBT_DIRECTION));
            int x = tagCompound.getInteger("X");
            int y = tagCompound.getInteger("Y");
            int z = tagCompound.getInteger("Z");
            BlockPos p = new BlockPos(x, y, z);
            setTarget(f, p);
        }
    }

    /**
     * Client only data
     */
    private ClientData clientData;
    private class ClientData {

        Set<BlockPos> blockList;
        Set<BlockPos> centerList;
        int[] faceData; // per face information

        public ClientData() {
            blockList=new HashSet<BlockPos>();
            centerList=new HashSet<BlockPos>();
            faceData = new int[6];
        }

        public void reset() {
            blockList.clear();
            centerList.clear();
            Arrays.fill(faceData, 0);
        }
    }

    public int getFaceData(EnumFacing f) { return clientData.faceData[f.ordinal()]; }
    public void setFaceData(EnumFacing f, int i) { clientData.faceData[f.ordinal()] = i; }

    public void clearClientData() {
        clientData.reset();
    }

    public Set<BlockPos> getBlockList() { return clientData.blockList; }
    public void addToBlockList(BlockPos p) {
        if (!p.equals(getPos()))
            clientData.blockList.add(p);
    }
    public void addToBlockList(Collection<BlockPos> c) {
        clientData.blockList.addAll(c);
    }

    public Set<BlockPos> getCenterList() { return clientData.centerList; }
    public void addToCenterList(BlockPos p) {
        if (!p.equals(getPos()))
            clientData.centerList.add(p);
    }
    public void addToCenterList(Collection<BlockPos> c) {
        clientData.centerList.addAll(c);
    }

    public void cleanBlockList() {
        /* strip the centers from the block list */
        clientData.blockList.removeAll(clientData.centerList);
    }
}
