package net.exceptionmc.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.managers.AudioManager;
import net.exceptionmc.DiscordBot;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackScheduler extends AudioEventAdapter {

    public static final BlockingQueue<AudioTrack> queue = new LinkedBlockingQueue<>();;
    private final AudioPlayer audioPlayer;

    public TrackScheduler(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
    }

    public void queue(AudioTrack audioTrack) {
        if (!this.audioPlayer.startTrack(audioTrack, true)) {

            queue.offer(audioTrack);
        }
    }

    public void nextTrack() {

        this.audioPlayer.stopTrack();
        this.audioPlayer.startTrack(queue.poll(), false);
    }

    @Override
    public void onTrackEnd(AudioPlayer audioPlayer, AudioTrack audioTrack, AudioTrackEndReason audioTrackEndReason) {

        AudioManager audioManager = new DiscordBot().guild.getAudioManager();
        if (audioTrackEndReason.mayStartNext) {
            if (!queue.isEmpty()) {

                nextTrack();
            } else {

                audioManager.closeAudioConnection();
            }
        }
    }
}