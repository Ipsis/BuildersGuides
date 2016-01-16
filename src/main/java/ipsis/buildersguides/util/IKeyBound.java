package ipsis.buildersguides.util;

import ipsis.buildersguides.client.keys.KeyBindingsBG;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IKeyBound {

    void doKeyBindingAction(EntityPlayer entityPlayer, ItemStack itemStack, KeyBindingsBG keyBinding);
}
