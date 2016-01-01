package ipsis.buildersguides.item;

import ipsis.buildersguides.client.keys.KeyBindingsBG;
import ipsis.buildersguides.reference.Reference;
import ipsis.buildersguides.util.IKeyBound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class ItemMallet extends ItemBG implements IKeyBound {

    public static final String BASENAME = "mallet";

    private static final String NBT_MODE = "mode";

    public ItemMallet() {

        super();
        setUnlocalizedName(BASENAME);
        setHasSubtypes(true);
        setMaxStackSize(1);
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
    public boolean doesSneakBypassUse(World world, BlockPos pos, EntityPlayer player) {

        return true;
    }

    public static String getModeTranslation(ItemStack itemStack) {

        return StatCollector.translateToLocal("tooltip." + Reference.MOD_ID + ":" + BASENAME + "." + getMode(itemStack));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {

        tooltip.add(getModeTranslation(stack));
    }

    /* IKeyBound */
    @Override
    public void doKeyBindingAction(EntityPlayer entityPlayer, ItemStack itemStack, KeyBindingsBG keyBinding) {

        cycleMode(itemStack);
        entityPlayer.addChatComponentMessage(new ChatComponentText(getModeTranslation(itemStack)));
    }

    public enum MalletMode {
        BCWRENCH,
        HAMMER,
        CONFIG,
        DECORATE;

        public static MalletMode getMode(int id) {
            return values()[MathHelper.clamp_int(id, 0, values().length - 1)];
        }

        public MalletMode getNext() {
            int next = (this.ordinal() + 1) % values().length;
            return values()[next];
        }
    }
}
