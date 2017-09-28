package ipsis.buildersguides.init;

import ipsis.buildersguides.block.BlockContainerBG;
import ipsis.buildersguides.block.BlockMarker;
import ipsis.buildersguides.item.ItemBlockMarker;
import ipsis.buildersguides.reference.Reference;
import ipsis.buildersguides.tileentity.TileEntityMarker;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

    public static BlockContainerBG blockMarker;

    public static void preInit() {

    }

    public static void init() {

        blockMarker = new BlockMarker();
    }

    @SideOnly(Side.CLIENT)
    public static void initClient() {

        ModBlocks.blockMarker.initModel();
    }

    public static void registerTileEntities() {

        GameRegistry.registerTileEntity(TileEntityMarker.class, "tile." + Reference.MOD_ID + "_" + BlockMarker.BASENAME);
    }

}
