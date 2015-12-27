package ipsis.buildersguides.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public abstract class CommonProxy implements IGuiHandler {

    public void registerRenderInformation() {
    }

    public void registerTileEntitySpecialRenderer() {
    }

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
