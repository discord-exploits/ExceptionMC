package net.exceptionmc.listeners;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.exceptionmc.DiscordBot;
import net.exceptionmc.util.LevelUtil;

public class ReadyEvent extends ListenerAdapter {

    @Override
    public void onReady(net.dv8tion.jda.api.events.ReadyEvent readyEvent) {

        for (Member member : new DiscordBot().guild.getMembers()) {

            LevelUtil.createMember(member);
        }

        LevelUtil.startCounting();
    }
}
