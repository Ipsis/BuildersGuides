package ipsis.buildersguides.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {

    public static void init() {

        // Blocks
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockMarker),
                "sss",
                "sts",
                "srs",
                's', Blocks.STONE, 't', Blocks.TORCH, 'r', Items.REDSTONE);

        // Items
        GameRegistry.addRecipe(new ItemStack(ModItems.itemMarkerCard, 1, 0),
                "sss",
                "sts",
                "sss",
                's', Items.STICK, 't', Blocks.TORCH);
        GameRegistry.addRecipe(new ItemStack(ModItems.itemMallet),
                "mmm",
                " s ",
                " s ",
                'm', ModItems.itemMarkerCard, 's', Items.STICK);
    }
}
