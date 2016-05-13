package ipsis.buildersguides.network.message;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageHandlerMarkerDummy implements IMessageHandler<MessageTileEntityMarker, IMessage> {

    public IMessage onMessage(final MessageTileEntityMarker message, MessageContext ctx) {
        return null;
    }
}