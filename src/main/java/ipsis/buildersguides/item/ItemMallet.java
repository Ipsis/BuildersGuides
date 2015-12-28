package ipsis.buildersguides.item;

import ipsis.buildersguides.init.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class ItemMallet extends ItemBG {

    public static final String BASENAME = "mallet";

    public ItemMallet() {

        super();
        setUnlocalizedName(BASENAME);
        setNoRepair();
    }

    public static MALLET_MODE getMode(ItemStack itemStack) {

        return MALLET_MODE.getMode(itemStack == null || itemStack.getItem() != ModItems.itemMallet ? 0 : itemStack.getItemDamage() % (MALLET_MODE.values().length - 1));
    }

    public static void setNextMode(ItemStack itemStack) {

        if (itemStack != null && itemStack.getItem() == ModItems.itemMallet) {
            itemStack.setItemDamage(getMode(itemStack).getNext().ordinal());
        }
    }

    public enum MALLET_MODE {
        BCWRENCH,
        HAMMER,
        CONFIG,
        DECORATE;

        public static MALLET_MODE getMode(int id) {
            return values()[MathHelper.clamp_int(id, 0, values().length - 1)];
        }

        public MALLET_MODE getNext() {
            int next = (this.ordinal() + 1) % values().length;
            return values()[next];
        }
    }
}
