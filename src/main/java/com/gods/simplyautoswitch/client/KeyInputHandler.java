package com.gods.simplyautoswitch.client;

import com.gods.simplyautoswitch.SimplyAutoSwitch;

import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeyInputHandler {
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		// checking inGameHasFocus prevents your keys from firing when the player is typing a chat message
		// NOTE that the KeyInputEvent will NOT be posted when a gui screen such as the inventory is open
		if (FMLClientHandler.instance().getClient().inGameHasFocus) {
			if (KeyBindings.autoSwitch.isPressed()) {
				SimplyAutoSwitch.enabled = !SimplyAutoSwitch.enabled;
				TextComponentString msg;
				if (SimplyAutoSwitch.enabled)
					msg = new TextComponentString("AutoSwitch: " + TextFormatting.GREEN + "ENABLED");
				else
					msg = new TextComponentString("AutoSwitch: " + TextFormatting.RED + "DISABLED");
				FMLClientHandler.instance().getClientPlayerEntity().sendStatusMessage(msg);
			}
		}
	}
}
