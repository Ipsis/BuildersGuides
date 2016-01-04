package ipsis.buildersguides.block;

import ipsis.buildersguides.BuildersGuides;
import ipsis.buildersguides.util.UnlocalizedName;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBG extends Block {

    public BlockBG(Material m, String name) {

        super(m);
        setCreativeTab(BuildersGuides.tabBG);
        setHardness(1.5F);
        setUnlocalizedName(name);
    }

    @Override
    public String getUnlocalizedName() {
        return UnlocalizedName.getUnlocalizedNameBlock(super.getUnlocalizedName());
    }
}
