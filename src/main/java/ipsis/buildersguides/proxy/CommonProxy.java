package ipsis.buildersguides.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {

    public void preInit() {

        registerBlockItemModels();
        registerItemRenderers();
        registerEventHandlers();
        registerKeyBindings();
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
