package net.exceptionmc.commandexecutor;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.exceptionmc.commandmanager.CommandManager;
import net.exceptionmc.lavaplayer.PlayerManager;
import net.exceptionmc.util.EmbedUtil;
import net.exceptionmc.util.YouTubeURLUtil;

public class PlayCommand implements CommandManager {

    @Override
    public void performCommand(GuildMessageReceivedEvent guildMessageReceivedEvent, String[] strings) {

        TextChannel textChannel = guildMessageReceivedEvent.getChannel();
        Member member = guildMessageReceivedEvent.getMember();

        assert member != null;
        GuildVoiceState memberVoiceState = member.getVoiceState();

        if (strings.length >= 1) {

            assert memberVoiceState != null;
            if (memberVoiceState.inVoiceChannel()) {
                if (strings[0].startsWith("https://")
                        || strings[0].startsWith("http://")
                        || strings[0].startsWith("www.")
                        || strings[0].startsWith("youtube.")) {

                    PlayerManager.getInstance().loadAndPlay(member, textChannel, strings[0]);
                } else {

                    StringBuilder songName = new StringBuilder();
                    for (String string : strings) {

                        songName.append(string).append(" ");
                    }

                    String songUrl = new YouTubeURLUtil().getYouTubeUniformResourceLocatorByTitle(songName.toString());
                    if (songUrl != null) {

                        PlayerManager.getInstance().loadAndPlay(member, textChannel, songUrl);
                    } else {

                        new EmbedUtil().sendEmbed(textChannel, "ExceptionMC » Music",
                                "I couldn't find a song");
                    }
                }
            }
        } else {

            new EmbedUtil().sendEmbed(textChannel, "ExceptionMC » Music",
                    "Syntax » **!play [SongName / URL (YouTube)]**");
        }
    }
}
