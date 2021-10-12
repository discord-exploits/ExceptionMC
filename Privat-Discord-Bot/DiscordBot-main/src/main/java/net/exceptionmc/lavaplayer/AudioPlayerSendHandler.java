package net.exceptionmc.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;
import net.dv8tion.jda.api.audio.AudioSendHandler;

import java.nio.ByteBuffer;

public class AudioPlayerSendHandler implements AudioSendHandler {

    private final AudioPlayer audioPlayer;
    private final ByteBuffer byteBuffer;
    private final MutableAudioFrame mutableAudioFrame;

    public AudioPlayerSendHandler(AudioPlayer audioPlayer) {

        this.audioPlayer = audioPlayer;
        this.byteBuffer = ByteBuffer.allocate(1024);
        this.mutableAudioFrame = new MutableAudioFrame();
        this.mutableAudioFrame.setBuffer(byteBuffer);
    }

    @Override
    public boolean canProvide() {

        return this.audioPlayer.provide(this.mutableAudioFrame);
    }

    @Override
    public ByteBuffer provide20MsAudio() {

        return (ByteBuffer) this.byteBuffer.flip();
    }

    @Override
    public boolean isOpus() {

        return true;
    }
}