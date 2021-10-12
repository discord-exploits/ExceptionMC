package net.exceptionmc.commandmanager;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandParser {

    public static CommandContainer parser(GuildMessageReceivedEvent guildMessageReceivedEvent, String raw) {

        String beheaded = raw.replaceFirst("!", "");
        String[] splitBeheaded = beheaded.split(" ");
        String invoke = splitBeheaded[0];
        ArrayList<String> split = new ArrayList<>(Arrays.asList(splitBeheaded));
        String[] args = new String[split.size() - 1];
        split.subList(1, split.size()).toArray(args);

        return new CommandContainer(raw, beheaded, splitBeheaded, invoke, args, guildMessageReceivedEvent);
    }
}
