package ipsis.buildersguides.network;

import ipsis.buildersguides.reference.Reference;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class PacketHandlerBG {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
    public static final byte KEY_PRESSED_MSG_ID = 1;
    public static final byte MARKER_TE_MSG_ID = 2;

}
