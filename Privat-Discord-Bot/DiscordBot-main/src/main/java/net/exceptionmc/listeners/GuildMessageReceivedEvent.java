package net.exceptionmc.listeners;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.exceptionmc.util.LevelUtil;

public class GuildMessageReceivedEvent extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent guildMessageReceivedEvent) {

        Member member = guildMessageReceivedEvent.getMember();
        TextChannel textChannel = guildMessageReceivedEvent.getChannel();

        //  Level System
        assert member != null;
        if (!member.getUser().isBot()) {
            if (textChannel.getId().equals("845329357320093717") || textChannel.getId().equals("842418239447629824")) {

                LevelUtil.addMessageXP(member, textChannel);
            }
        }
    }
}
