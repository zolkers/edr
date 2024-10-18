package com.edgn.command;

import com.edgn.command.commands.ShowRaidsCompletionsCommand;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

import java.util.ArrayList;

public class CommandManager {

    private static CommandManager commandManager;
    private final ArrayList<Command> commands =  new ArrayList<>();

    private CommandManager() {}


    public static CommandManager getInstance() {
        if (commandManager == null) {
            commandManager = new CommandManager();
        }
        return commandManager;
    }
    public void registerCommands() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> registerClientCommands(dispatcher));
    }
    private void registerClientCommands(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        commands.add(new ShowRaidsCompletionsCommand());

        registerCommandsInDispatcher(dispatcher);
    }
    private void registerCommandsInDispatcher(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        for (Command command : commands) {
            command.register(dispatcher);
        }
    }
    public ArrayList<Command> getCommands() {
        return commands;
    }
}
