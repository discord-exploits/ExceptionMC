package net.exceptionmc.commandexecutor;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.exceptionmc.commandmanager.CommandManager;
import net.exceptionmc.util.EmbedUtil;
import net.exceptionmc.util.StatsUtil;
import net.exceptionmc.util.VerificationUtil;

public class StatsCommand implements CommandManager {

    @Override
    public void performCommand(GuildMessageReceivedEvent guildMessageReceivedEvent, String[] strings) {

        Member member = guildMessageReceivedEvent.getMember();
        TextChannel textChannel = guildMessageReceivedEvent.getChannel();
        Message message = guildMessageReceivedEvent.getMessage();
        VerificationUtil verificationUtil = new VerificationUtil();

        String gameMode = "noArg";
        if (strings.length > 0)
            gameMode = strings[0];

        assert member != null;
        if (strings.length == 1) {
            if (StatsUtil.gameModeExists(gameMode)) {
                if (verificationUtil.isVerified(member)) {

                    new EmbedUtil().sendEmbed(textChannel, "ExceptionMC » Stats",
                            StatsUtil.getStatsMessage(gameMode, member));
                } else {

                    new EmbedUtil().sendEmbed(textChannel, "ExceptionMC » Stats",
                            "Your Discord account is not linked to our Minecraft network. " +
                                    "You can either use the command **!stats <gameMode> <name>** " +
                                    "or connect your Discord account to our Minecraft network " +
                                    "with the command **!verify <name>**.");
                }
            } else {

                new EmbedUtil().sendEmbed(textChannel, "ExceptionMC » Stats",
                        "The game mode **" + gameMode + "** couldn't be found.");
            }
        } else if (strings.length == 2) {
            if (StatsUtil.gameModeExists(gameMode)) {
                if (!message.getMentionedMembers().isEmpty()) {
                    Member target = message.getMentionedMembers().get(0);
                    if (verificationUtil.isVerified(target)) {

                        new EmbedUtil().sendEmbed(textChannel, "ExceptionMC » Stats",
                                StatsUtil.getStatsMessage(gameMode, target));
                    } else {

                        new EmbedUtil().sendEmbed(textChannel, "ExceptionMC » Stats",
                                "The Discord account of " + target.getAsMention() +
                                        " is not linked to our Minecraft network. " +
                                        "You can only get his stats with the command **!stats <gameMode> <name>**.");
                    }
                } else {

                    new EmbedUtil().sendEmbed(textChannel, "ExceptionMC » Stats",
                            StatsUtil.getStatsMessage(gameMode, strings[1]));
                }
            } else {

                new EmbedUtil().sendEmbed(textChannel, "ExceptionMC » Stats",
                        "The game mode **" + gameMode + "** couldn't be found.");
            }
        } else {

            new EmbedUtil().sendEmbed(textChannel, "ExceptionMC » Stats",
                    "Usage » **!stats <gameMode> [inGameName/Mention]**");
        }
    }
}
