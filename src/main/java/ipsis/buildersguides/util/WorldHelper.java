package ipsis.buildersguides.util;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class WorldHelper {

    public static boolean isClient(World world) {

        return world != null && world.isRemote;
    }

    public static boolean isServer(World world) {

        return world != null && !world.isRemote;
    }

    public static void updateClient(World world, TileEntity te) {

        world.markBlockForUpdate(te.getPos());
        te.markDirty();
    }
}
