package net.exceptionmc.listeners;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.exceptionmc.DiscordBot;
import net.exceptionmc.util.EmbedUtil;

public class GuildMemberUpdateBoostTimeEvent extends ListenerAdapter {

    EmbedUtil embedUtil = new EmbedUtil();

    @Override
    public void onGuildMemberUpdateBoostTime(net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateBoostTimeEvent guildMemberUpdateBoostTimeEvent) {

        Member member = guildMemberUpdateBoostTimeEvent.getMember();

        TextChannel textChannel = new DiscordBot().guild.getTextChannelById("842442242308309072");

        assert textChannel != null;
        Message message = embedUtil.sendEmbed(textChannel, "ExceptionMC » Boost",
                member.getAsMention() + " just boosted the ExceptionMC.net Discord-Server.");

        message.addReaction("❤").queue();
    }
}
