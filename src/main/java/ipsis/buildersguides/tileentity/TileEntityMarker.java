package ipsis.buildersguides.tileentity;

import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import ipsis.buildersguides.manager.MarkerType;
import ipsis.buildersguides.network.PacketHandlerBG;
import ipsis.buildersguides.network.message.MessageTileEntityMarker;
import ipsis.buildersguides.util.ColorBG;
import ipsis.oss.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class TileEntityMarker extends TileEntity {

    private MarkerType type;
    private EnumFacing facing;
    private ColorBG color;
    private int[] v; // Generic value per direction
    private BlockPos[] target; // Target position per direction

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

    public BlockPos getTarget(EnumFacing f) {
        if (target[f.ordinal()] == null)
            return new BlockPos(this.getPos());
        else
            return target[f.ordinal()];
    }
    public void setTarget(EnumFacing f, BlockPos p) { target[f.ordinal()] = new BlockPos(p); }
    public boolean hasTarget(EnumFacing f) { return target[f.ordinal()] != null && !getPos().equals(target[f.ordinal()]); }


    public void clearBlocklist() { blockList.clear(); }

    public TileEntityMarker() {

        type = MarkerType.BLANK;
        facing = EnumFacing.SOUTH;
        color = ColorBG.WHITE;

        v = new int[6];
        for (int i = 0; i < 6; i++)
            v[i] = 0;

        target = new BlockPos[6];
        blockList = new ArrayList<BlockPos>();
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

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        /* TODO getRenderBoundingBox */
        return TileEntity.INFINITE_EXTENT_AABB;
    }

    public List<BlockPos> getBlockList() {
        return blockList;
    }

    public void addToBlockList(BlockPos p) {
        blockList.add(p);
    }

    /**
     * Client only data
     */
    private List<BlockPos> blockList;

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("TE Location: ").append(getPos());
        s.append(type).append(":").append(facing).append(":").append(color).append("\n");
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
}
