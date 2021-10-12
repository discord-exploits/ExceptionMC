package net.exceptionmc.listeners;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.exceptionmc.DiscordBot;
import net.exceptionmc.util.EmbedUtil;
import net.exceptionmc.util.PrivateChannelUtil;
import net.exceptionmc.util.UniqueIdFetcher;
import net.exceptionmc.util.VerificationUtil;

import java.util.Objects;

public class PrivateMessageReceivedEvent extends ListenerAdapter {

    @Override
    public void onPrivateMessageReceived(net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent privateMessageReceivedEvent) {

        User user = privateMessageReceivedEvent.getAuthor();
        Message message = privateMessageReceivedEvent.getMessage();
        Member member = new DiscordBot().guild.getMember(user);

        if (!user.isBot()) {

            //  Private Channel System
            if (PrivateChannelUtil.isVoiceChannelPasswordSetup(member)) {

                String password = message.getContentDisplay();
                PrivateChannelUtil.setVoiceChannelPassword(member, password);
                assert member != null;
                PrivateChannelUtil.createVoiceChannel(member);
            }

            if (PrivateChannelUtil.isRequesting(member)) {

                System.out.println("Debug1");

                if (PrivateChannelUtil.isPasswordEnabled(PrivateChannelUtil.getOwner(PrivateChannelUtil.getRequestingChannel(member)))) {

                    System.out.println("Debug2");

                    PrivateChannelUtil.tryPasswordRequest(member, message.getContentDisplay());
                }
            }

            //  Verification System
            assert member != null;
            if (new VerificationUtil().isAwaitingVerification(user)) {

                boolean rightToken = new VerificationUtil().checkVerification(user, message.getContentDisplay());
                if (rightToken) {

                    String playerName = new VerificationUtil().getMinecraftName(user);

                    new EmbedUtil().sendEmbed(user, "ExceptionMC » Verification",
                            "Congratulations, the verification token you entered was valid. Your accounts have been linked successfully.\n" +
                                    "\n" +
                                    "DiscordAccount » **" + user.getAsMention() + "**\n" +
                                    "DiscordId » **" + user.getId() + "**\n" +
                                    "MinecraftAccount » **" + playerName + "**\n" +
                                    "MinecraftUniqueId » **" + new UniqueIdFetcher().getUniqueId(playerName) + "**\n" +
                                    "\n" +
                                    "You can unlink your account at any time by entering the command **!verify " + playerName + "**.\n" +
                                    "\n" +
                                    "If you have any questions, our team is always available. You are welcome to create a ticket (" + Objects.requireNonNull(new DiscordBot().guild.getTextChannelById("842726377900081212")).getAsMention() + ") or report to our TeamSpeak3 (ts.exceptionmc.net) server. Alternatively, we of course offer you support via our dashboard (https://exceptionmc.net/support).");
                } else {

                    new EmbedUtil().sendEmbed(user, "ExceptionMC » Verification",
                            "The verification token entered was unfortunately incorrect.\n" +
                                    "\n" +
                                    "If you have any questions, our team is always available. " +
                                    "You are welcome to create a ticket (" +
                                    Objects.requireNonNull(new DiscordBot().guild.
                                            getTextChannelById("842726377900081212")).getAsMention() +
                                    ") or report to our TeamSpeak3 (ts.exceptionmc.net) server. " +
                                    "Alternatively, we of course offer you support via our dashboard " +
                                    "(https://exceptionmc.net/support).");
                }
            }
        }
    }
}
