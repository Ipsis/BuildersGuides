package ipsis.buildersguides.network.message;

import io.netty.buffer.ByteBuf;
import ipsis.buildersguides.util.EnumKeys;
import ipsis.buildersguides.util.IKeyBound;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageKeyPressed implements IMessage {

    public MessageKeyPressed() { }

    private byte key;
    public MessageKeyPressed(EnumKeys key) {

        this.key = (byte)key.ordinal();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(key);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        key = buf.readByte();
    }

    public static class Handler implements IMessageHandler<MessageKeyPressed, IMessage> {

        @Override
        public IMessage onMessage(final MessageKeyPressed message, MessageContext ctx) {

            if (ctx.side != Side.SERVER)
                return null;

            final EntityPlayerMP fromPlayer = ctx.getServerHandler().player;
            if (fromPlayer == null)
                return null;

            final WorldServer playerWorldServer = fromPlayer.getServerWorld();
            playerWorldServer.addScheduledTask(new Runnable() {
                public void run() {
                    processMessage(message, fromPlayer);
                }
            });

            /* No response */
            return null;
        }

        private void processMessage(MessageKeyPressed message, EntityPlayerMP fromPlayer) {

            ItemStack itemStack = fromPlayer.getHeldItemMainhand();
            if (itemStack != null && itemStack.getItem() instanceof IKeyBound) {
                if (message.key == EnumKeys.KEY_MODE.ordinal())
                    ((IKeyBound) itemStack.getItem()).doKeyBindingAction(fromPlayer, itemStack, EnumKeys.KEY_MODE);
            }
        }
    }
}
