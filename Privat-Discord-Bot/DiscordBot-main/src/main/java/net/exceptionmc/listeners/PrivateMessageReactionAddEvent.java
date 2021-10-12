package net.exceptionmc.listeners;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.exceptionmc.DiscordBot;
import net.exceptionmc.util.PrivateChannelUtil;

public class PrivateMessageReactionAddEvent extends ListenerAdapter {

    @Override
    public void onPrivateMessageReactionAdd(net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent privateMessageReactionAddEvent) {

        User user = privateMessageReactionAddEvent.getUser();
        assert user != null;
        Member member = new DiscordBot().guild.getMember(user);
        String messageId = privateMessageReactionAddEvent.getMessageId();
        String reactionEmoji = privateMessageReactionAddEvent.getReactionEmote().getName();

        if (!user.isBot()) {

            if (PrivateChannelUtil.isVoiceChannelTypeToggle(member)) {
                if (PrivateChannelUtil.getVoiceChannelTypeToggleMessage(member).equals(messageId)) {

                    if (reactionEmoji.equals("🔓")) {

                        PrivateChannelUtil.setVoiceChannelType(member, "public");
                        assert member != null;
                        PrivateChannelUtil.createVoiceChannel(member);
                    } else if (reactionEmoji.equals("🔒")) {

                        PrivateChannelUtil.setVoiceChannelType(member, "private");
                        assert member != null;
                        PrivateChannelUtil.sendVoiceChannelPasswordToggleMessage(member);
                    }
                }
            }

            if (PrivateChannelUtil.isVoiceChannelPasswordToggle(member)) {
                if (PrivateChannelUtil.getVoiceChannelPasswordToggleMessage(member).equals(messageId)) {
                    if (reactionEmoji.equals("✔")) {

                        assert member != null;
                        PrivateChannelUtil.sendVoiceChannelPasswordSetupMessage(member);
                    } else if (reactionEmoji.equals("❌")) {

                        assert member != null;
                        PrivateChannelUtil.createVoiceChannel(member);
                    }
                }
            }

            if (PrivateChannelUtil.isRequestMessage(messageId)) {

                if (reactionEmoji.equals("✔")) {

                    assert member != null;
                    PrivateChannelUtil.acceptRequest(member, messageId);
                } else if (reactionEmoji.equals("❌")) {

                    assert member != null;
                    PrivateChannelUtil.rejectRequest(member, messageId);
                }
            }
        }
    }
}
