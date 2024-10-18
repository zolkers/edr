package com.edgn.command;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public abstract class Command {
    public abstract void register(CommandDispatcher<FabricClientCommandSource> dispatcher);
}
