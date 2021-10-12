package net.exceptionmc.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.exceptionmc.DiscordBot;

import java.awt.*;

@SuppressWarnings("all")
public class EmbedUtil {

    EmbedBuilder embedBuilder;
    private void initEmbedBuilder(String title, String description) {

        embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.decode("#5555ff"));
        embedBuilder.setTitle(title);
        embedBuilder.setDescription(description);
        embedBuilder.setFooter("ExceptionMC.net » Your Minecraft Minigames-Network", new DiscordBot().guild.getIconUrl());
    }

    public Message sendEmbed(TextChannel textChannel, String title, String description) {

        initEmbedBuilder(title, description);

        return textChannel.sendMessage(embedBuilder.build()).complete();
    }

    public Message sendEmbed(User user, String title, String description) {

        initEmbedBuilder(title, description);

        PrivateChannel privateChannel = user.openPrivateChannel().complete();
        return privateChannel.sendMessage(embedBuilder.build()).complete();
    }

    public Message sendEmbed(Message message, String title, String description) {

        initEmbedBuilder(title, description);

        return message.editMessage(embedBuilder.build()).complete();
    }
}
