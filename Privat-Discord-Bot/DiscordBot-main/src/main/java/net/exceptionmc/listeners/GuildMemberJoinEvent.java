package net.exceptionmc.listeners;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.GuildManager;
import net.exceptionmc.DiscordBot;
import net.exceptionmc.util.EmbedUtil;
import net.exceptionmc.util.LevelUtil;

public class GuildMemberJoinEvent extends ListenerAdapter {

    EmbedUtil embedUtil = new EmbedUtil();

    @Override
    public void onGuildMemberJoin(net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent guildMemberJoinEvent) {

        Member member = guildMemberJoinEvent.getMember();

        TextChannel textChannel = new DiscordBot().guild.getTextChannelById("842417718673670205");

        assert textChannel != null;
        Message message = embedUtil.sendEmbed(textChannel, "ExceptionMC » Join",
                member.getAsMention() + " just joined the ExceptionMC.net Discord-Server." +
                        "\nWe're now **" + new DiscordBot().guild.getMemberCount() + "** members.");

        message.addReaction("❤").queue();

        Role role = new DiscordBot().guild.getRoleById("842420263068631080");
        GuildManager guildManager = new DiscordBot().guild.getManager();

        assert role != null;
        guildManager.getGuild().addRoleToMember(member, role).queue();

        LevelUtil.createMember(member);
    }
}