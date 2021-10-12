package net.exceptionmc.commandmanager;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface CommandManager {

    void performCommand(GuildMessageReceivedEvent guildMessageReceivedEvent, String[] strings);
}
