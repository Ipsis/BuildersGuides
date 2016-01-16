package ipsis.buildersguides.client.keys;

import ipsis.buildersguides.reference.Reference;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

/**
 * Based off MineMaarten's AdvancedMod code for keyhandling
 * https://github.com/MineMaarten/AdvancedMod
 */
public enum KeyBindingsBG {

    KEY_MODE(Keys.MODE, Keyboard.KEY_EQUALS);

    public class Keys {

        public static final String CATEGORY = "keys." + Reference.MOD_ID + ".category";
        public static final String MODE = "keys." + Reference.MOD_ID + ".mode";
    }

    private final KeyBinding keyBinding;
    KeyBindingsBG(String name, int defaultKeyCode) {
        keyBinding = new KeyBinding(name, defaultKeyCode, Keys.CATEGORY);
    }

    public KeyBinding getKeyBinding() { return keyBinding; }
    public boolean isPressed() { return keyBinding.isPressed(); }
}
