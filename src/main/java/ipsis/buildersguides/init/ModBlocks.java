package ipsis.buildersguides.init;

import ipsis.buildersguides.block.BlockContainerBG;
import ipsis.buildersguides.block.BlockMarker;
import ipsis.buildersguides.reference.Reference;
import ipsis.buildersguides.tileentity.TileEntityMarker;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {

    public static void init() {

        GameRegistry.register(blockMarker);
        GameRegistry.register(new ItemBlock(blockMarker).setRegistryName(Reference.MOD_ID_LOWER, BlockMarker.BASENAME));
    }

    public static void registerTileEntities() {

        GameRegistry.registerTileEntity(TileEntityMarker.class, "tile." + BlockMarker.BASENAME);
    }

    public static BlockContainerBG blockMarker = new BlockMarker();
}
