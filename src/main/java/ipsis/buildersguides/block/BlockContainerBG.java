package ipsis.buildersguides.block;

import ipsis.buildersguides.BuildersGuides;
import ipsis.buildersguides.util.UnlocalizedName;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockContainerBG extends BlockContainer {

    public BlockContainerBG(Material m, String name) {
        super(m);

        setCreativeTab(BuildersGuides.tabBG);
        setHardness(1.5F);
        setUnlocalizedName(name);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
    }

    @Override
    public String getUnlocalizedName() {
        return UnlocalizedName.getUnlocalizedNameBlock(super.getUnlocalizedName());
    }
}
