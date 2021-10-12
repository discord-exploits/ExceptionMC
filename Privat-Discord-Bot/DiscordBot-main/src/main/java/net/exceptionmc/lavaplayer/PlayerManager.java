package net.exceptionmc.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import net.exceptionmc.DiscordBot;
import net.exceptionmc.util.EmbedUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlayerManager {

    private static PlayerManager INSTANCE;
    public final AudioPlayerManager audioPlayerManager;
    private final Map<Long, GuildMusicManager> musicManagers;

    public PlayerManager() {

        this.musicManagers = new HashMap<>();
        this.audioPlayerManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(this.audioPlayerManager);
        AudioSourceManagers.registerLocalSource(this.audioPlayerManager);
    }

    public static PlayerManager getInstance() {
        if (INSTANCE == null) {

            INSTANCE = new PlayerManager();
        }

        return INSTANCE;
    }

    public GuildMusicManager getGuildMusicManager(Guild guild) {
        return this.musicManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
            final GuildMusicManager guildMusicManager = new GuildMusicManager(this.audioPlayerManager);

            guild.getAudioManager().setSendingHandler(guildMusicManager.getAudioPlayerSendHandler());

            return guildMusicManager;
        });
    }

    public void loadAndPlay(Member member, TextChannel textChannel, String trackUrl) {
        GuildMusicManager guildMusicManager = this.getGuildMusicManager(textChannel.getGuild());
        AudioManager audioManager = new DiscordBot().guild.getAudioManager();

        this.audioPlayerManager.loadItemOrdered(guildMusicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                if (!audioManager.isConnected()) {

                    audioManager.openAudioConnection(Objects.requireNonNull(member.getVoiceState()).getChannel());
                }

                guildMusicManager.trackScheduler.queue(audioTrack);

                new EmbedUtil().sendEmbed(textChannel, "ExceptionMC » Music",
                        "I added **" + audioTrack.getInfo().title + "** to the playlist.");
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
            }

            @Override
            public void noMatches() {
                new EmbedUtil().sendEmbed(textChannel, "ExceptionMC » Music",
                        "I couldn't find a song");
            }

            @Override
            public void loadFailed(FriendlyException e) {
            }
        });
    }

    public void skipTrack(TextChannel textChannel) {

        AudioManager audioManager = new DiscordBot().guild.getAudioManager();
        if (audioManager.isConnected()) {
            if (!TrackScheduler.queue.isEmpty()) {

                new GuildMusicManager(new DefaultAudioPlayerManager()).audioPlayer.stopTrack();
                getGuildMusicManager(new DiscordBot().guild).trackScheduler.nextTrack();

                new EmbedUtil().sendEmbed(textChannel,
                        "ExceptionMC » Music", "\n" + "I'm playing now **"
                                + PlayerManager.getInstance().getGuildMusicManager(new DiscordBot().guild).
                                audioPlayer.getPlayingTrack().getInfo().title + "**.");

            } else {

                audioManager.closeAudioConnection();

                new EmbedUtil().sendEmbed(textChannel,
                        "ExceptionMC » Music",
                        "There are no more songs in the playlist. I am leaving the voice channel");
            }
        } else {

            new EmbedUtil().sendEmbed(textChannel, "ExceptionMC » Music",
                    "I am currently not connected to the voice channel.");
        }
    }
}