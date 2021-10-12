package net.exceptionmc.commandmanager;

import java.util.HashMap;

public class CommandHandler {

    public static HashMap<String, CommandManager> commandManager = new HashMap<>();

    public static void handleCommand(CommandContainer commandContainer) {
        if (commandManager.containsKey(commandContainer.invoke)) {

            commandManager.get(commandContainer.invoke).performCommand(commandContainer.guildMessageReceivedEvent, commandContainer.args);
        }
    }
}
