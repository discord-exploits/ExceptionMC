package net.exceptionmc.commandexecutor;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.exceptionmc.commandmanager.CommandManager;
import net.exceptionmc.lavaplayer.PlayerManager;
import net.exceptionmc.util.EmbedUtil;

public class SkipCommand implements CommandManager {

    @Override
    public void performCommand(GuildMessageReceivedEvent guildMessageReceivedEvent, String[] strings) {

        if (strings.length == 0) {

            PlayerManager.getInstance().skipTrack(guildMessageReceivedEvent.getChannel());;
        } else {

            new EmbedUtil().sendEmbed(guildMessageReceivedEvent.getChannel(), "ExceptionMC » Music",
                    "Syntax » **!skip**");
        }
    }
}
