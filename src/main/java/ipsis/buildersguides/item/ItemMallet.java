package ipsis.buildersguides.item;

import ipsis.buildersguides.client.keys.KeyBindingsBG;
import ipsis.buildersguides.init.ModItems;
import ipsis.buildersguides.oss.client.ModelHelper;
import ipsis.buildersguides.reference.Names;
import ipsis.buildersguides.reference.Reference;
import ipsis.buildersguides.util.EnumKeys;
import ipsis.buildersguides.util.IKeyBound;
import ipsis.buildersguides.util.StringHelper;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemMallet extends ItemBG implements IKeyBound {

    public static final String BASENAME = "mallet";

    private static final String NBT_MODE = "mode";

    public ItemMallet() {

        super();
        setHasSubtypes(true);
        setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void initModel() {

        for (int i = 0; i < MalletMode.values().length; i++) {
            ModelHelper.registerItem(ModItems.itemMallet, i, BASENAME + "." + MalletMode.getMode(i).toString().toLowerCase());
            ModelBakery.registerItemVariants(ModItems.itemMallet,
                    new ResourceLocation(Reference.MOD_ID + ":" + BASENAME + "." +
                    MalletMode.getMode(i).toString().toLowerCase()));

        }
    }

    public static MalletMode getMode(ItemStack itemStack) {

        if (!itemStack.hasTagCompound())
            itemStack.setTagCompound(new NBTTagCompound());

        NBTTagCompound tag = itemStack.getTagCompound();
        return MalletMode.getMode(tag.getInteger(NBT_MODE));
    }

    public static void cycleMode(ItemStack itemStack) {

        if (!itemStack.hasTagCompound())
            itemStack.setTagCompound(new NBTTagCompound());

        NBTTagCompound tag = itemStack.getTagCompound();
        MalletMode m = MalletMode.getMode(tag.getInteger(NBT_MODE));
        tag.setInteger(NBT_MODE, m.getNext().ordinal());
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {
        return true;
    }

    public static String getModeTranslation(ItemStack itemStack) {

        return TextFormatting.YELLOW + String.format("%s: %s", StringHelper.localize(Names.NAME, Names.MODE),
                StringHelper.localize(Names.NAME, BASENAME + "." + getMode(itemStack).toString().toLowerCase()));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

        tooltip.add(String.format(StringHelper.localize(Names.TOOLTIP, BASENAME),
                GameSettings.getKeyDisplayString(KeyBindingsBG.KEY_MODE.getKeyBinding().getKeyCode())));
        tooltip.add(getModeTranslation(stack));

        MalletMode m = getMode(stack);
        boolean right = false;
        boolean sright = false;
        if (m == MalletMode.BCWRENCH) {
            right = true;
            sright = true;
        } else if (m == MalletMode.CONFIG) {
            right = true;
            sright = true;
        } else if (m == MalletMode.DECORATE) {
            right = true;
        } else if (m == MalletMode.HAMMER) {
            right = true;
            sright = true;
        }

        if (right)
            tooltip.add(TextFormatting.RED + String.format("%s: %s", StringHelper.localize(Names.NAME, Names.RCLICK), StringHelper.localize(Names.TOOLTIP, BASENAME + "." + m.toString().toLowerCase() + "." + Names.RCLICK)));
        if (sright)
            tooltip.add(TextFormatting.GREEN + String.format("%s: %s", StringHelper.localize(Names.NAME, Names.SRCLICK), StringHelper.localize(Names.TOOLTIP, BASENAME + "." + m.toString().toLowerCase() + "." + Names.SRCLICK)));

    }

    /* IKeyBound */
    @Override
    public void doKeyBindingAction(EntityPlayer entityPlayer, ItemStack itemStack, EnumKeys key) {

        cycleMode(itemStack);
        entityPlayer.sendStatusMessage(new TextComponentString(getModeTranslation(itemStack)), false);
    }

    public enum MalletMode {
        HAMMER,
        BCWRENCH,
        CONFIG,
        DECORATE;

        public static MalletMode getMode(int id) {
            return values()[MathHelper.clamp(id, 0, values().length - 1)];
        }

        public MalletMode getNext() {
            int next = (this.ordinal() + 1) % values().length;
            return values()[next];
        }
    }
}
