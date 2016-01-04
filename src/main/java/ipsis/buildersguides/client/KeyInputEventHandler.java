package ipsis.buildersguides.client;

import ipsis.buildersguides.client.keys.KeyBindingsBG;
import ipsis.buildersguides.network.PacketHandlerBG;
import ipsis.buildersguides.network.message.MessageKeyPressed;
import ipsis.buildersguides.util.IKeyBound;
import ipsis.buildersguides.util.WorldHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

/**
 * Based off MineMaarten's AdvancedMod code for keyhandling and Pahimar's
 * https://github.com/MineMaarten/AdvancedMod
 * https://github.com/pahimar/Equivalent-Exchange-3
 *
 */
public class KeyInputEventHandler {

    private KeyBindingsBG getPressedKey() {

        for (KeyBindingsBG key : KeyBindingsBG.values()) {
            if (key.isPressed())
                return key;
        }

        return null;
    }

    @SubscribeEvent
    public void handleKeyInputEvent(InputEvent.KeyInputEvent event) {

        /* You get the pressed and release event */
        KeyBindingsBG key = getPressedKey();

        if (key != null && FMLClientHandler.instance().getClient().inGameHasFocus && FMLClientHandler.instance().getClientPlayerEntity() != null) {
            EntityPlayer entityPlayer = FMLClientHandler.instance().getClientPlayerEntity();
            if (entityPlayer.getCurrentEquippedItem() != null && entityPlayer.getCurrentEquippedItem().getItem() instanceof IKeyBound) {
                if (WorldHelper.isClient(entityPlayer.worldObj)) {
                    PacketHandlerBG.INSTANCE.sendToServer(new MessageKeyPressed(key));
                }
            }
        }
    }
}
