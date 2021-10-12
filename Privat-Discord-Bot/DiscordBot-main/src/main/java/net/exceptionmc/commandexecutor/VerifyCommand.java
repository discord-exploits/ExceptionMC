package net.exceptionmc.commandexecutor;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.exceptionmc.DiscordBot;
import net.exceptionmc.commandmanager.CommandManager;
import net.exceptionmc.util.EmbedUtil;
import net.exceptionmc.util.UniqueIdFetcher;
import net.exceptionmc.util.VerificationUtil;

import java.util.Objects;

public class VerifyCommand implements CommandManager {

    @Override
    public void performCommand(GuildMessageReceivedEvent guildMessageReceivedEvent, String[] strings) {

        Member member = guildMessageReceivedEvent.getMember();
        TextChannel textChannel = guildMessageReceivedEvent.getChannel();

        if (strings.length == 1) {

            String playerName = strings[0];
            if (new UniqueIdFetcher().playerExistsPlayerName(playerName)) {

                String uniqueId = new UniqueIdFetcher().getUniqueId(playerName);

                assert member != null;
                if (!new VerificationUtil().isAwaitingVerification(member)) {
                    if (!new VerificationUtil().isVerified(member)) {
                        if (!new VerificationUtil().isVerified(uniqueId)) {

                            new VerificationUtil().startVerification(member, playerName);

                            new EmbedUtil().sendEmbed(member.getUser(), "ExceptionMC » Verification",
                                    "You have successfully started the verification.\n" +
                                            "\n" +
                                            "DiscordAccount » **" + member.getAsMention() + "**\n" +
                                            "DiscordId » **" + member.getId() + "**\n" +
                                            "MinecraftAccount » **" + playerName + "**\n" +
                                            "MinecraftUniqueId » **" + uniqueId + "**\n" +
                                            "\n" +
                                            "1. Start Minecraft and join the ExceptionMC.net Minecraft network.\n" +
                                            "2. Send the 6-digit verification token which can be read in the inventory that opens when entering the network in this private message.\n" +
                                            "3. If the verification token sent to me was correct, the verification has been successfully completed. You can now use the advantages of verification on our Discord server!\n" +
                                            "\n" +
                                            "If you have any questions, our team is always available. You are welcome to create a ticket (" + Objects.requireNonNull(new DiscordBot().guild.getTextChannelById("842726377900081212")).getAsMention() + ") or report to our TeamSpeak3 (ts.exceptionmc.net) server. Alternatively, we of course offer you support via our dashboard (https://exceptionmc.net/support).");

                            new EmbedUtil().sendEmbed(textChannel, "ExceptionMC » Verification",
                                    "The instructions for connecting the Minecraft account **" + playerName + "** with the Discord account " + member.getAsMention() + " were sent to you by private message.");
                        } else {

                            new EmbedUtil().sendEmbed(textChannel, "ExceptionMC » Verification",
                                    "The Minecraft account **" + playerName +
                                            "** is already linked to a Discord account.");
                        }
                    } else {

                        new EmbedUtil().sendEmbed(textChannel, "ExceptionMC » Verification",
                                "Your Discord account is already linked to a Minecraft account.");
                    }
                } else {

                    new EmbedUtil().sendEmbed(textChannel, "ExceptionMC » Verification",
                            "You have already sent a request to link your Discord account.");
                }
            } else {

                new EmbedUtil().sendEmbed(textChannel, "ExceptionMC » Verification",
                        "You have never been on the ExceptionMC.net Minecraft network. Please connect first and then start the verification again (!verify " + playerName + ")");
            }
        } else {

            new EmbedUtil().sendEmbed(textChannel, "ExceptionMC » Verification",
                    "Wrong usage! » **!help verification**");
        }
    }
}
