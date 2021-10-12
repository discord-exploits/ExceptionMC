package net.exceptionmc.commandmanager;

import com.google.api.client.util.Lists;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.exceptionmc.DiscordBot;
import net.exceptionmc.util.EmbedUtil;

import java.util.ArrayList;
import java.util.Objects;

public class GuildMessageReceivedEvent extends ListenerAdapter {

    public static ArrayList<String> everywhereAllowedCommands = Lists.newArrayList();

    @Override
    public void onGuildMessageReceived
            (net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent guildMessageReceivedEvent) {

        TextChannel textChannel = guildMessageReceivedEvent.getChannel();
        TextChannel commandChannel = new DiscordBot().guild.getTextChannelById("845329454381531207");

        String[] strings = guildMessageReceivedEvent.getMessage().getContentDisplay().split(" ");

        if (guildMessageReceivedEvent.getMessage().getContentDisplay().startsWith("!")) {
            if (!guildMessageReceivedEvent.getMessage().getAuthor().getId()
                    .equals(guildMessageReceivedEvent.getJDA().getSelfUser().getId())) {
                if (textChannel == commandChannel || everywhereAllowedCommands.contains
                        (strings[0].replace("!", ""))) {

                    CommandHandler.handleCommand(CommandParser.parser(guildMessageReceivedEvent,
                            guildMessageReceivedEvent.getMessage().getContentRaw()));
                } else {

                    assert commandChannel != null;
                    new EmbedUtil().sendEmbed(textChannel, "ExceptionMC » Commands",
                            "Commands can only be executed in " + commandChannel.getAsMention() + ".");
                }
            }
        }

        if (textChannel == commandChannel) {
            if (!CommandHandler.commandManager.containsKey(strings[0].replace("!", ""))) {
                if (!Objects.requireNonNull(guildMessageReceivedEvent.getMember()).getUser().isBot()) {

                    guildMessageReceivedEvent.getMessage().delete().queue();
                }
            }
        }
    }
}
