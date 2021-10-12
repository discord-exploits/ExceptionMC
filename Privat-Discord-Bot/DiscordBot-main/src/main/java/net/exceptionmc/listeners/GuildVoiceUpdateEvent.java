package net.exceptionmc.listeners;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.exceptionmc.util.PrivateChannelUtil;

public class GuildVoiceUpdateEvent extends ListenerAdapter {

    @Override
    public void onGuildVoiceUpdate(net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent guildVoiceUpdateEvent) {

        Member member = guildVoiceUpdateEvent.getEntity();
        VoiceChannel voiceChannelJoined = null;
        if (guildVoiceUpdateEvent.getChannelJoined() != null)
            voiceChannelJoined = guildVoiceUpdateEvent.getChannelJoined();
        VoiceChannel voiceChannelLeft = null;
        if (guildVoiceUpdateEvent.getChannelLeft() != null)
             voiceChannelLeft = guildVoiceUpdateEvent.getChannelLeft();

        if (voiceChannelJoined != null) {
            if (voiceChannelJoined.getId().equals("842440665869582358")) {
                if (!PrivateChannelUtil.isRequesting(member)) {

                    PrivateChannelUtil.startSetup(member);
                }
            }
        }

        if (voiceChannelLeft != null) {
            if (voiceChannelLeft.getId().equals("842441329694343179")) {
                if (PrivateChannelUtil.setupMembersArrayList.contains(member)) {

                    PrivateChannelUtil.cancelVoiceChannelSetup(member);
                }

                if (PrivateChannelUtil.isRequesting(member)) {

                    PrivateChannelUtil.cancelJoinRequest(member);
                }
            }
        }

        if (voiceChannelLeft != null) {
            if (PrivateChannelUtil.getOwner(voiceChannelLeft) == member) {

                PrivateChannelUtil.deleteVoiceChannel(member);
            }
        }

        if (voiceChannelJoined != null) {
            if (PrivateChannelUtil.isPrivateChannel(voiceChannelJoined)) {
                if (PrivateChannelUtil.getOwner(voiceChannelJoined) != member) {
                    if (!PrivateChannelUtil.isTrusted(voiceChannelJoined, member)) {

                        PrivateChannelUtil.joinVoiceChannel(member, voiceChannelJoined);
                    }
                }
            }
        }

        if (voiceChannelLeft != null) {
            if (PrivateChannelUtil.isPrivateChannel(voiceChannelLeft)) {
                if (PrivateChannelUtil.getOwner(voiceChannelLeft) != member) {
                    if (PrivateChannelUtil.isTrusted(voiceChannelLeft, member)) {

                        PrivateChannelUtil.unTrust(voiceChannelLeft, member);
                    }
                }
            }
        }
    }
}
