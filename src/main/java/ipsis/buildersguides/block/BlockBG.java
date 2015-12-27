package ipsis.buildersguides.block;

import ipsis.buildersguides.BuildersGuides;
import ipsis.buildersguides.reference.Reference;
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
        return String.format("tile.%s%s", Reference.MOD_ID + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName) {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }
}
