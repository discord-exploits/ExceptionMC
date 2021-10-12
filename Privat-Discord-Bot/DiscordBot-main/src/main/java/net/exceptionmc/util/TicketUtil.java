package net.exceptionmc.util;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.ChannelManager;
import net.exceptionmc.DiscordBot;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("all")
public class TicketUtil {

    DatabaseUtil databaseUtil;

    public TicketUtil() {

        databaseUtil =
                new DatabaseUtil("discord", "discord", "OHRoaEx2bFM5QnVoNUZRTHpZaWk=");
    }

    public void createTables() {

        databaseUtil.executeUpdate("CREATE TABLE IF NOT EXISTS ticketCounter(ticketType VARCHAR(255), value INT)");
        databaseUtil.executeUpdate("CREATE TABLE IF NOT EXISTS openedTickets(channelId VARCHAR(255), " +
                "discordId VARCHAR(255), closeMessageId VARCHAR(255), ticketId INT, categoryTicketId INT, " +
                "creationTime BIGINT)");
    }

    public void initTables() {

        if (!databaseUtil.exists("ticketCounter", "ticketType", "all"))
            databaseUtil.create("ticketCounter", "ticketType", "all");
        if (!databaseUtil.exists("ticketCounter", "ticketType", "minecraft"))
            databaseUtil.create("ticketCounter", "ticketType", "minecraft");
        if (!databaseUtil.exists("ticketCounter", "ticketType", "discord"))
            databaseUtil.create("ticketCounter", "ticketType", "discord");
        if (!databaseUtil.exists("ticketCounter", "ticketType", "teamspeak"))
            databaseUtil.create("ticketCounter", "ticketType", "teamspeak");
        if (!databaseUtil.exists("ticketCounter", "ticketType", "report"))
            databaseUtil.create("ticketCounter", "ticketType", "report");
        if (!databaseUtil.exists("ticketCounter", "ticketType", "other"))
            databaseUtil.create("ticketCounter", "ticketType", "other");
    }

    public Integer getTicketCount(String ticketType) {

        return databaseUtil.getInteger("ticketCounter", "value", "ticketType", ticketType);
    }

    public void increaseTicketCount(String ticketType) {

        databaseUtil.setInteger("ticketCounter", "value", getTicketCount(ticketType) + 1,
                "ticketType", ticketType);
    }

    public void createTicket(Member member, String ticketType) {

        if (!hasOpenTicket(member)) {
            increaseTicketCount("all");
            increaseTicketCount(ticketType);

            Category category = new DiscordBot().guild.getCategoryById("842726309309317171");

            assert category != null;
            TextChannel textChannel =
                    category.createTextChannel("┃ticket-" + getTicketCount("all")).complete();

            ChannelManager channelManager = textChannel.getManager();
            channelManager.getChannel().createPermissionOverride(member).setAllow(Permission.VIEW_CHANNEL).queue();

            Message message = new EmbedUtil().sendEmbed(textChannel, "ExceptionMC » Support",
                    "Type » **" + ticketType + "**" +
                            "\nCreator » **" + member.getAsMention() + "**" +
                            "\nTicketId » **" + getTicketCount("all") + "**" +
                            "\nCategory-TicketId » **" + getTicketCount(ticketType) + "**" +
                            "\nDiscordId » **" + member.getId() + "**" +
                            "\nMinecraft-Name » **Unknown**" +
                            "\nMinecraft-UniqueId » **Unknown**" +
                            "\nTime » **" + new MillisToStringUtil().formatMillis(System.currentTimeMillis()) + "**" +
                            "\nSupporter » **Unknown**" +
                            "\n" +
                            "\nTo close the ticket react with 🔒");

            databaseUtil.create("openedTickets", "channelId", textChannel.getId());

            databaseUtil.setString("openedTickets", "discordId", member.getId(),
                    "channelId", textChannel.getId());

            databaseUtil.setString("openedTickets", "closeMessageId", message.getId(),
                    "channelId", textChannel.getId());

            databaseUtil.setInteger("openedTickets", "ticketId", getTicketCount("all"),
                    "channelId", textChannel.getId());

            databaseUtil.setInteger("openedTickets", "categoryTicketId", getTicketCount(ticketType),
                    "channelId", textChannel.getId());

            databaseUtil.setLong("openedTickets", "creationTime", System.currentTimeMillis(),
                    "channelId", textChannel.getId());

            message.addReaction("🔒").queue();

            sendTicketCreationMessageToMinecraftProxy(member);
        }
    }

    public void closeTicket(Member member) {

        sendTranscript(member);

        String textChannelId = databaseUtil.getString("openedTickets", "channelId",
                "discordId", member.getId());
        TextChannel textChannel = new DiscordBot().guild.getTextChannelById(textChannelId);

        assert textChannel != null;
        textChannel.delete().queue();

        databaseUtil.delete("openedTickets", "discordId", member.getId());
    }

    public void closeTicket(TextChannel textChannel) {

        sendTranscript(new DiscordBot().guild.getMemberById(getTicketOwnerId(textChannel)));

        textChannel.delete().queue();

        databaseUtil.delete("openedTickets", "channelId", textChannel.getId());
    }

    public boolean hasOpenTicket(Member member) {

        return databaseUtil.exists("openedTickets", "discordId", member.getId());
    }

    public String getCloseMessageId(Member member) {

        return databaseUtil.getString("openedTickets",
                "closeMessageId", "discordId", member.getId());
    }

    public String getCloseMessageId(TextChannel textChannel) {

        return databaseUtil.getString("openedTickets",
                "closeMessageId", "channelId", textChannel.getId());
    }

    public String getTicketOwnerId(TextChannel textChannel) {

        return databaseUtil.getString("openedTickets", "discordId",
                "channelId", textChannel.getId());
    }

    public void sendTicketCreationMessage(TextChannel textChannel) {

        Message message = new EmbedUtil().sendEmbed(textChannel, "ExceptionMC » Support",
                "To create a support ticket, please react with one of the reactions listed below." +
                        "\n⛏ » Minecraft" +
                        "\n💬 » Discord" +
                        "\n🗣 » TeamSpeak" +
                        "\n❌ » Report" +
                        "\n✔ » Other");

        message.addReaction("⛏").queue();
        message.addReaction("💬").queue();
        message.addReaction("🗣").queue();
        message.addReaction("❌").queue();
        message.addReaction("✔").queue();
    }

    public String getTicketChannelId(Member member) {

        return databaseUtil.getString("openedTickets", "channelId",
                "discordId", member.getId());
    }

    public Integer getTicketId(Member member) {

        return databaseUtil.getInteger("openedTickets", "ticketId",
                "discordId", member.getId());
    }

    public void sendTranscript(Member member) {

        String dateFormat = "dd.MM.yyyy HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        TextChannel textChannel = new DiscordBot().guild.getTextChannelById(getTicketChannelId(member));
        int ticketId = getTicketId(member);

        String[] urls = {"https://fonts.gstatic.com", "https://fonts.googleapis.com/css2?family=Mulish&display=swap"};

        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<!DOCTYPE html>\n" + "<html lang=\"en\">\n" + "\t<head>\n" +
                "\t\t<meta charset=\"UTF-8\">\n" + "\t\t<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "\t\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "\t\t<link rel=\"preconnect\" href=\"")
                .append(urls[0]).append("\">\n")
                .append("\t\t<link href=\"")
                .append(urls[1])
                .append("\" rel=\"stylesheet\">\n")
                .append("\t\t<title>ExceptionMC » Support (#")
                .append(ticketId)
                .append(")</title>\n")
                .append("\t</head>\n")
                .append("\t\n")
                .append("\t<body style=\"background-color: #36393f; font-family: 'Mulish', sans-serif; " +
                        "font-size: 12px; color: #dcddde;\">\n");

        assert textChannel != null;
        List<Message> messagesArrayList = textChannel.getIterableHistory().complete();
        Collections.reverse(messagesArrayList);

        for (Message message : messagesArrayList) {
            if (!message.getAuthor().isBot()) {

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(message.getTimeCreated().toInstant().toEpochMilli());

                htmlBuilder.append("<br>\n" + "\t\t<div style=\"background-color: #2f3136; position: relative; " +
                        "border-radius: 5px; width: 480px; margin-left: 20px; display: inline-block; " +
                        "padding-left: 10px; padding-right: 10px; margin-bottom: 12px;\">\n" + "\t\t\t<img src=\"")
                        .append(message.getAuthor().getAvatarUrl())
                        .append("\" alt=\"\" style=\"border-radius: 50%; width: 48px; height: 48px; float: left; " +
                                "padding-top: 10px; padding-bottom: 10px;\">\n");

                htmlBuilder.append("\t\t\t<h1 style=\"float: left; font-size: 16px; margin-left: 10px; " +
                        "padding-top: 16px;\">")
                        .append(message.getAuthor().getAsTag())
                        .append(" - ")
                        .append(simpleDateFormat.format(calendar.getTime()))
                        .append("</h1>\n")
                        .append("\t\t\t<h1 style=\"display: block; font-size: 14px; width: 460px; " +
                                "word-wrap: break-word; margin-top: 80px; padding-left: 10px\">")
                        .append(message.getContentDisplay())
                        .append("</h1>\n");

                for (Message.Attachment attachment : message.getAttachments()) {
                    if (attachment.isImage()) {

                        htmlBuilder.append("<img src=\"")
                                .append(attachment.getUrl())
                                .append("\" alt=\"\" style=\"width: 460px; float: left; padding-top: 16px; " +
                                        "padding-bottom: 16px; padding-left: 10px\">");

                        htmlBuilder.append("\n");
                    }
                }

                htmlBuilder.append("</div>\n");
            }
        }

        htmlBuilder.append("\t</body>\n</html>");

        File file = null;

        try {

            file = File.createTempFile("transcript-" + ticketId, ".html");

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(htmlBuilder.toString());
            bufferedWriter.close();
        } catch (IOException ioException) {

            ioException.printStackTrace();
        }

        assert file != null;
        new EmbedUtil().sendEmbed(member.getUser(), "ExceptionMC » Support",
                "Enclosed you can see the transcript for the ticket (" + ticketId +
                        ") that has just been closed! You can simply download it and view it in any browser. ");
        member.getUser().openPrivateChannel().complete()
                .sendFile(file, "transcript-" + ticketId + ".html").queue();
    }

    public void sendTicketCreationMessageToMinecraftProxy(Member member) {
        try {

            Socket socket = new Socket("45.142.114.182", 9642);

            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            dataOutputStream.writeUTF(member.getUser().getAsTag());
            dataOutputStream.flush();
            dataOutputStream.close();

            socket.close();
        } catch (IOException ioException) {

            ioException.printStackTrace();
        }
    }
}
