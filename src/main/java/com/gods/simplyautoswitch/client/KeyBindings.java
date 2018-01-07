package com.gods.simplyautoswitch.client;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class KeyBindings {
	public static KeyBinding autoSwitch;

	public static void init() {
		autoSwitch = new KeyBinding("key_autoswitch.toggle", Keyboard.KEY_BACKSLASH, "key.categories.SimplyAutoSwitch");
		ClientRegistry.registerKeyBinding(autoSwitch);
	}
}
