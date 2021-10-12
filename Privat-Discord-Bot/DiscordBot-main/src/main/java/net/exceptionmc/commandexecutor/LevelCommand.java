package net.exceptionmc.commandexecutor;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.exceptionmc.commandmanager.CommandManager;
import net.exceptionmc.util.EmbedUtil;
import net.exceptionmc.util.LevelUtil;

public class LevelCommand implements CommandManager {

    @Override
    public void performCommand(GuildMessageReceivedEvent guildMessageReceivedEvent, String[] strings) {

        Member member = guildMessageReceivedEvent.getMember();
        TextChannel textChannel = guildMessageReceivedEvent.getChannel();
        Message message = guildMessageReceivedEvent.getMessage();

        if (strings.length == 0) {

            assert member != null;
            int level = LevelUtil.getLevel(member);
            long needed = LevelUtil.getXpOfLevel(level + 1) - LevelUtil.getXp(member);
            new EmbedUtil().sendEmbed(textChannel, "ScopeRS » Level",
                    "You are currently level " + level + ". You still need " + needed +
                            " xp to move up to the next level.");
        } else if (strings.length == 1) {
            if (!message.getMentionedMembers().isEmpty()) {

                Member target = message.getMentionedMembers().get(0);
                int level = LevelUtil.getLevel(target);
                long needed = LevelUtil.getXpOfLevel(level + 1) - LevelUtil.getXp(target);
                new EmbedUtil().sendEmbed(textChannel, "ExceptionMC » Level",
                        target.getAsMention() + " is currently level " + level + ". " + target.getAsMention() +
                                " still needs " + needed + " xp to move up to the next level.");
            } else {
                if (strings[0].equalsIgnoreCase("top")) {

                    assert member != null;
                    LevelUtil.sendTop10(member, textChannel);
                }
            }
        }
    }
}
