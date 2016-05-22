package ipsis.buildersguides.proxy;

import ipsis.buildersguides.network.PacketHandlerBG;
import ipsis.buildersguides.network.message.MessageKeyPressed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;

import static ipsis.buildersguides.network.PacketHandlerBG.KEY_PRESSED_MSG_ID;

public class CommonProxy implements IGuiHandler {

    public void preInit() {

        registerBlockItemModels();
        registerItemRenderers();
        registerEventHandlers();
        registerKeyBindings();

        PacketHandlerBG.INSTANCE.registerMessage(MessageKeyPressed.Handler.class, MessageKeyPressed.class, KEY_PRESSED_MSG_ID, Side.SERVER);
    }

    public void init() {

        registerTESRs();
    }

    public void postInit() { }

    protected void registerBlockItemModels() { }
    protected void registerItemRenderers() { }
    protected void registerEventHandlers() { }
    protected void registerKeyBindings() { }
    protected void registerTESRs() { }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    public World getClientWorld() {
        return null;
    }
}
