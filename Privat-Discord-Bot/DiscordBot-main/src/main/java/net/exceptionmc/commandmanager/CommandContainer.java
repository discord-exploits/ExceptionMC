package net.exceptionmc.commandmanager;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class CommandContainer {

    public final String raw;
    public final String beheaded;
    public final String[] splitBeheaded;
    public final String invoke;
    public final String[] args;
    public final GuildMessageReceivedEvent guildMessageReceivedEvent;

    public CommandContainer(String rw, String beheaded, String[] splitBeheaded, String invoke, String[] args,
                            GuildMessageReceivedEvent guildMessageReceivedEvent) {

        this.raw = rw;
        this.beheaded = beheaded;
        this.splitBeheaded = splitBeheaded;
        this.invoke = invoke;
        this.args = args;
        this.guildMessageReceivedEvent = guildMessageReceivedEvent;
    }
}
