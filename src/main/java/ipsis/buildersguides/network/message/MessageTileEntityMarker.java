package ipsis.buildersguides.network.message;

import io.netty.buffer.ByteBuf;
import ipsis.buildersguides.manager.MarkerManager;
import ipsis.buildersguides.manager.MarkerType;
import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.buildersguides.util.ColorBG;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageTileEntityMarker implements IMessage {

    public MessageTileEntityMarker() {
        v = new int[6];
        target = new BlockPos[6];
    }

    private int x, y, z;
    public byte facing;
    public byte type;
    public byte color;
    public int v[];
    public BlockPos target[];
    public byte mode;

    public MessageTileEntityMarker(TileEntityMarker te) {

        this();
        this.x = te.getPos().getX();
        this.y = te.getPos().getY();
        this.z = te.getPos().getZ();
        this.facing = (byte)te.getFacing().ordinal();
        this.type = (byte)te.getType().ordinal();
        this.color = (byte)te.getColor().ordinal();
        this.mode = (byte)te.getMode();

        for (EnumFacing f : EnumFacing.values())
            this.v[f.ordinal()] = te.getV(f);

        for (EnumFacing f : EnumFacing.values())
            this.target[f.ordinal()] = te.getTarget(f);
    }

    @Override
    public String toString() {
        return "MessageTileEntityMarker: " + EnumFacing.getFront(facing) + " " + MarkerType.getMarkerType(type) + " " + ColorBG.getColor(color) + " " + mode +
                " " + v[0] + " " + v[1] + " " + v[2] + " " + v[3] + " "  + v[4] + " " + v[5] +
                " " + target[0] + " " + target[1] + " " + target[2] + " " + target[3] + " " + target[4] + " " + target[5];
    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeByte(facing);
        buf.writeByte(type);
        buf.writeByte(color);
        buf.writeByte(mode);

        for (int i = 0; i < 6; i++)
            buf.writeInt(v[i]);

        for (int i = 0; i < 6; i++) {
            buf.writeInt(target[i].getX());
            buf.writeInt(target[i].getY());
            buf.writeInt(target[i].getZ());
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.facing = buf.readByte();
        this.type = buf.readByte();
        this.color = buf.readByte();
        this.mode = buf.readByte();

        for (int i = 0; i < 6; i++)
            this.v[i] = buf.readInt();

        for (int i = 0; i < 6; i++)
            target[i] = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static class Handler implements IMessageHandler<MessageTileEntityMarker, IMessage> {

        @Override
        public IMessage onMessage(final MessageTileEntityMarker message, MessageContext ctx) {

            if (ctx.side != Side.CLIENT)
                return null;

            Minecraft minecraft = Minecraft.getMinecraft();
            final WorldClient worldClient = minecraft.theWorld;
            minecraft.addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    processMessage(worldClient, message);
                }
            });

            /* No response */
            return null;
        }

        private void processMessage(WorldClient worldClient, MessageTileEntityMarker message) {

            TileEntity te = worldClient.getTileEntity(new BlockPos(message.x, message.y, message.z));
            if (te != null && te instanceof TileEntityMarker) {

                ((TileEntityMarker) te).setFacing(EnumFacing.getFront(message.facing));
                ((TileEntityMarker) te).setType(MarkerType.getMarkerType(message.type));
                ((TileEntityMarker) te).setColor(ColorBG.getColor(message.color));
                ((TileEntityMarker) te).setMode(message.mode);

                for (EnumFacing f : EnumFacing.values()) {
                    ((TileEntityMarker) te).setV(f, message.v[f.ordinal()]);
                    ((TileEntityMarker) te).setTarget(f, message.target[f.ordinal()]);
                }

                MarkerManager.handleServerUpdate((TileEntityMarker)te);
                worldClient.markBlockForUpdate(te.getPos());
            }
        }
    }
}
