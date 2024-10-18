package com.edgn;

import com.edgn.command.CommandManager;
import net.fabricmc.api.ClientModInitializer;

public class EdrClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		CommandManager.getInstance().registerCommands();
	}
}