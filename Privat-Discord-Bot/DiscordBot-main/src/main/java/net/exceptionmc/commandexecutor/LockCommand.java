package net.exceptionmc.commandexecutor;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.ChannelManager;
import net.exceptionmc.DiscordBot;
import net.exceptionmc.commandmanager.CommandManager;
import net.exceptionmc.util.EmbedUtil;

import java.util.Objects;

public class LockCommand implements CommandManager {

    @Override
    public void performCommand(GuildMessageReceivedEvent guildMessageReceivedEvent, String[] strings) {

        Member member = guildMessageReceivedEvent.getMember();
        TextChannel textChannel = guildMessageReceivedEvent.getChannel();
        Guild guild = new DiscordBot().guild;
        Role role = guild.getRoleById("842417128559607818");
        assert role != null;

        assert member != null;
        if (member.hasPermission(Permission.MESSAGE_MANAGE)) {
            if (Objects.requireNonNull(
                    textChannel.getPermissionOverride(role)).getAllowed().contains(Permission.MESSAGE_WRITE)) {

                textChannel.upsertPermissionOverride(role).setDeny(Permission.MESSAGE_WRITE).queue();
                new EmbedUtil().sendEmbed(textChannel, "ExceptionMC » Lock",
                        "The text channel (" + textChannel.getAsMention() +
                                ") was locked by " + member.getAsMention() + ".");
            } else {

                textChannel.upsertPermissionOverride(role).setAllow(Permission.MESSAGE_WRITE).queue();
                new EmbedUtil().sendEmbed(textChannel, "ExceptionMC » Lock",
                        "The text channel (" + textChannel.getAsMention() +
                                ") was unlocked by " + member.getAsMention() + ".");
            }
        } else {

            new EmbedUtil().sendEmbed(textChannel, "ExceptionMC » Lock",
                    "You do not have sufficient rights to use this command.");
        }
    }
}
