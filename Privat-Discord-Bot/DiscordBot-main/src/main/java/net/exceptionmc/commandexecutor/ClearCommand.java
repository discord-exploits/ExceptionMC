package net.exceptionmc.commandexecutor;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.exceptionmc.commandmanager.CommandManager;
import net.exceptionmc.util.EmbedUtil;

import java.util.List;

public class ClearCommand implements CommandManager {

    @Override
    public void performCommand(GuildMessageReceivedEvent guildMessageReceivedEvent, String[] strings) {

        Member member = guildMessageReceivedEvent.getMember();
        TextChannel textChannel = guildMessageReceivedEvent.getChannel();
        assert member != null;

        if (member.hasPermission(Permission.MESSAGE_MANAGE)) {
            if (strings.length == 1) {
                try {

                    int amountOfMessages = Integer.parseInt(strings[0]);

                    List<Message> messagesArrayList = textChannel.getIterableHistory().complete();
                    for (int messageCount = 0; messageCount < amountOfMessages; messageCount++) {

                        messagesArrayList.get(messageCount).delete().queue();
                    }

                    new EmbedUtil().sendEmbed(textChannel, "ScopeRS » Clear",
                            "I successfully deleted **" + amountOfMessages +
                                    "** messages on behalf of " + member.getAsMention() + ".");
                } catch (NumberFormatException numberFormatException) {

                    new EmbedUtil().sendEmbed(textChannel, "ScopeRS » Clear",
                            "Usage » **!clear <amount>**");
                }
            } else {

                new EmbedUtil().sendEmbed(textChannel, "ScopeRS » Clear",
                        "Usage » **!clear <amount>**");
            }
        } else {

            new EmbedUtil().sendEmbed(textChannel, "ScopeRS » Clear",
                    "You do not have sufficient rights to use this command.");
        }
    }
}
