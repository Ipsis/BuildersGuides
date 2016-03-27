package ipsis.buildersguides.network.message;

import io.netty.buffer.ByteBuf;
import ipsis.buildersguides.client.keys.KeyBindingsBG;
import ipsis.buildersguides.util.IKeyBound;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageKeyPressed implements IMessage {

    public MessageKeyPressed() { }

    private byte key;
    public MessageKeyPressed(KeyBindingsBG key) {

        if (key == KeyBindingsBG.KEY_MODE)
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

            final EntityPlayerMP fromPlayer = ctx.getServerHandler().playerEntity;
            if (fromPlayer == null)
                return null;

            final WorldServer playerWorldServer = fromPlayer.getServerForPlayer();
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
                if (message.key == KeyBindingsBG.KEY_MODE.ordinal())
                    ((IKeyBound) itemStack.getItem()).doKeyBindingAction(fromPlayer, itemStack, KeyBindingsBG.KEY_MODE);
            }
        }
    }
}
