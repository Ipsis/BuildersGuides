package ipsis.buildersguides.client.keys;

import ipsis.buildersguides.reference.Reference;
import ipsis.buildersguides.util.EnumKeys;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

/**
 * Based off MineMaarten's AdvancedMod code for keyhandling
 * https://github.com/MineMaarten/AdvancedMod
 */
public enum KeyBindingsBG {

    KEY_MODE(EnumKeys.KEY_MODE, Keyboard.KEY_EQUALS);

    public static final String CATEGORY = "keys." + Reference.MOD_ID + ".category";
    private final KeyBinding keyBinding;
    private final EnumKeys key;

    KeyBindingsBG(EnumKeys key, int defaultKeyCode) {
        this.key = key;
        keyBinding = new KeyBinding(key.name(), defaultKeyCode, CATEGORY);
    }

    public KeyBinding getKeyBinding() { return keyBinding; }
    public boolean isPressed() { return keyBinding.isPressed(); }
    public EnumKeys getKey() { return key; }
}
