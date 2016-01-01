package ipsis.buildersguides.network;

import ipsis.buildersguides.network.message.MessageKeyPressed;
import ipsis.buildersguides.network.message.MessageTileEntityMarker;
import ipsis.buildersguides.reference.Reference;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandlerBG {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);

    private enum ModMessage {

        KEY_PRESSED(MessageKeyPressed.Handler.class, MessageKeyPressed.class, Side.SERVER),
        TE_MARKER(MessageTileEntityMarker.Handler.class, MessageTileEntityMarker.class, Side.CLIENT);

        private Class handlerClazz;
        private Class messageClazz;
        private Side side;
        ModMessage(Class handlerClazz, Class messageClazz, Side side) {

            this.handlerClazz = handlerClazz;
            this.messageClazz = messageClazz;
            this.side = side;
        }

        public Class getHandlerClazz() {

            return this.handlerClazz;
        }

        public Class getMessageClazz() {

            return this.messageClazz;
        }

        public Side getSide() {

            return this.side;
        }
    }

    public static void init() {

        for (ModMessage m : ModMessage.values())
            INSTANCE.registerMessage(m.getHandlerClazz(), m.getMessageClazz(), m.ordinal(), m.getSide());
    }
}
