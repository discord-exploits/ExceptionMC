package net.exceptionmc.listeners;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.exceptionmc.DiscordBot;
import net.exceptionmc.util.EmbedUtil;
import net.exceptionmc.util.LevelUtil;
import net.exceptionmc.util.TicketUtil;

public class GuildMemberRemoveEvent extends ListenerAdapter {

    EmbedUtil embedUtil = new EmbedUtil();

    @Override
    public void onGuildMemberRemove(net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent guildMemberRemoveEvent) {

        User user = guildMemberRemoveEvent.getUser();

        TextChannel textChannel = new DiscordBot().guild.getTextChannelById("842417718673670205");

        assert textChannel != null;
        Message message = embedUtil.sendEmbed(textChannel, "ExceptionMC » Leave",
                user.getAsMention() + " just left the ExceptionMC.net Discord-Server." +
                        "\nWe're now **" + new DiscordBot().guild.getMemberCount() + "** members.");

        message.addReaction("💔").queue();

        LevelUtil.deleteMember(user);
    }
}
