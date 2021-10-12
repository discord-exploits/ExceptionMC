package net.exceptionmc.util;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.managers.GuildManager;
import net.exceptionmc.DiscordBot;

public class NickNameUtil {

    private final DatabaseUtil databaseUtil =
            new DatabaseUtil("global", "global", "c1lHLkZaVTYuaE8zV3hjZA==");

    public void updateNicknames() {

        GuildManager guildManager = new DiscordBot().guild.getManager();

        for (String currentMember : databaseUtil.getStrings("verifiedAccounts", "discordId")) {
            if (new DiscordBot().guild.getMemberById(currentMember) != null) {

                Member member = new DiscordBot().guild.getMemberById(currentMember);

                assert member != null;
                if (!member.isOwner()) {

                    String playerName = new VerificationUtil().getMinecraftName(member);

                    guildManager.getGuild().modifyNickname(member, playerName).queue();
                }
            }
        }
    }
}
