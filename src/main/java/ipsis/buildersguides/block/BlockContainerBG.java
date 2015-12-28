package ipsis.buildersguides.block;

import ipsis.buildersguides.BuildersGuides;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;

public abstract class BlockContainerBG extends BlockContainer {

    public BlockContainerBG(Material m, String name) {
        super(m);

        setCreativeTab(BuildersGuides.tabBG);
        setHardness(1.5F);
        setUnlocalizedName(name);
    }
}
