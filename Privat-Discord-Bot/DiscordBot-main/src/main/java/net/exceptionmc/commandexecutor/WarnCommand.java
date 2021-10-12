package net.exceptionmc.commandexecutor;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.exceptionmc.commandmanager.CommandManager;
import net.exceptionmc.util.WarnUtil;

public class WarnCommand implements CommandManager {

    @Override
    public void performCommand(GuildMessageReceivedEvent guildMessageReceivedEvent, String[] strings) {

        // !warn <@Mention>(0) <Reason . . .>(1 - ~);
        Message message = guildMessageReceivedEvent.getMessage();
        TextChannel textChannel = guildMessageReceivedEvent.getChannel();
        Member member = guildMessageReceivedEvent.getMember();
        assert member != null;

        if (strings.length > 1) {
            if (!message.getMentionedMembers().isEmpty()) {

                Member target = message.getMentionedMembers().get(0);

                StringBuilder stringBuilder = new StringBuilder();
                for (int count = 1; count < strings.length; count++) {

                    stringBuilder.append(strings[count]).append(" ");
                }
                new WarnUtil().warn(textChannel, target, stringBuilder.toString());
            }
        }
    }
}
