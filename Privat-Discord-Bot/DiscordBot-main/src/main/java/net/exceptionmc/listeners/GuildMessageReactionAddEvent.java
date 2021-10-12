package net.exceptionmc.listeners;

import com.sedmelluq.discord.lavaplayer.remote.RemoteNode;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.exceptionmc.util.TicTacToeUtil;
import net.exceptionmc.util.TicketUtil;

public class GuildMessageReactionAddEvent extends ListenerAdapter {

    @Override
    public void onGuildMessageReactionAdd(net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent guildMessageReactionAddEvent) {

        Member member = guildMessageReactionAddEvent.getMember();
        TextChannel textChannel = guildMessageReactionAddEvent.getChannel();
        String messageId = guildMessageReactionAddEvent.getMessageId();
        String reactionEmoji = guildMessageReactionAddEvent.getReactionEmote().getName();

        if (!guildMessageReactionAddEvent.getUser().isBot()) {
            if (textChannel.getId().equals("842726377900081212")) {
                if (messageId.equals("842776277882372116")) {
                    if (reactionEmoji.equals("⛏"))
                        new TicketUtil().createTicket(member, "minecraft");
                    if (reactionEmoji.equals("💬"))
                        new TicketUtil().createTicket(member, "discord");
                    if (reactionEmoji.equals("🗣"))
                        new TicketUtil().createTicket(member, "teamspeak");
                    if (reactionEmoji.equals("❌"))
                        new TicketUtil().createTicket(member, "report");
                    if (reactionEmoji.equals("✔"))
                        new TicketUtil().createTicket(member, "other");
                    guildMessageReactionAddEvent.getReaction().removeReaction(member.getUser()).queue();
                }
            }

            if (textChannel.getId().equals("842418205297344562") || textChannel.getId().equals("842418239447629824")) {


            }

            if (messageId.equals(new TicketUtil().getCloseMessageId(textChannel))) {

                new TicketUtil().closeTicket(textChannel);
            }

            if (TicTacToeUtil.isRequested(member)) {
                if (messageId.equals(TicTacToeUtil.getRequestMessage(member).getId())) {
                    if (reactionEmoji.equals("✔")) {

                        guildMessageReactionAddEvent.getReaction().removeReaction().complete();
                        TicTacToeUtil.acceptRequest(member);
                    }
                }
            }

            if (textChannel.getId().equals("849730482961645578")) {
                if (TicTacToeUtil.isInGame(member)) {
                    if (messageId.equals(TicTacToeUtil.getGameMessage(member).getId())) {
                        if (TicTacToeUtil.getMemberOnTurn(TicTacToeUtil.getGameMessage(member)) == member) {

                            Integer decision = TicTacToeUtil.getPositionDecision(reactionEmoji);
                            TicTacToeUtil.updateMatchField(member, decision, TicTacToeUtil.getColorOfMember(member),
                                    TicTacToeUtil.getGameMessage(member));

                            guildMessageReactionAddEvent.getReaction().removeReaction().queue();
                        }
                    }
                }

                guildMessageReactionAddEvent.getReaction().removeReaction(member.getUser()).queue();
            }
        }
    }
}
