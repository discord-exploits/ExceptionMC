package net.exceptionmc.commandexecutor;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.exceptionmc.commandmanager.CommandManager;
import net.exceptionmc.util.EmbedUtil;
import net.exceptionmc.util.TicTacToeUtil;

public class TicTacToeCommand implements CommandManager {

    @Override
    public void performCommand(GuildMessageReceivedEvent guildMessageReceivedEvent, String[] strings) {

        Member member = guildMessageReceivedEvent.getMember();
        Message message = guildMessageReceivedEvent.getMessage();

        TextChannel textChannel = guildMessageReceivedEvent.getChannel();

        if (strings.length == 1) {
            if (!message.getMentionedMembers().isEmpty()) {
                assert member != null;
                if (!TicTacToeUtil.isInGame(member) &&
                        !TicTacToeUtil.isRequesting(member) &&
                        !TicTacToeUtil.isRequested(member)) {
                    Member receiver = message.getMentionedMembers().get(0);
                    if (!TicTacToeUtil.isInGame(receiver) &&
                            !TicTacToeUtil.isRequesting(receiver) &&
                            !TicTacToeUtil.isRequested(receiver)) {

                        TicTacToeUtil.sendRequest(member, receiver);
                    } else {

                        new EmbedUtil().sendEmbed(textChannel, "ExceptionMC » TicTacToe",
                                receiver.getAsMention() + " is already in a game.");
                    }
                } else {

                    new EmbedUtil().sendEmbed(textChannel, "ExceptionMC » TicTacToe",
                            "You are already in a game.");
                }
            }
        }
    }
}
