package net.exceptionmc.util;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.exceptionmc.DiscordBot;

@SuppressWarnings("all")
public class VerificationUtil {

    DatabaseUtil discordDatabaseUtil;
    DatabaseUtil globalDatabaseUtil;

    public VerificationUtil() {

        discordDatabaseUtil =
                new DatabaseUtil("discord", "discord", "OHRoaEx2bFM5QnVoNUZRTHpZaWk=");
        globalDatabaseUtil =
                new DatabaseUtil("global", "global", "c1lHLkZaVTYuaE8zV3hjZA==");
    }

    public void createTables() {

        discordDatabaseUtil.executeUpdate("CREATE TABLE IF NOT EXISTS verificationSessions" +
                "(discordId VARCHAR(255), uniqueId VARCHAR(255), verificationToken VARCHAR(255))");

        globalDatabaseUtil.executeUpdate("CREATE TABLE IF NOT EXISTS verifiedAccounts" +
                "(uniqueId VARCHAR(255), discordId VARCHAR(255), teamspeakIdentity VARCHAR(255), webId VARCHAR(255))");
    }

    public void startVerification(Member member, String player) {

        String uniqueId = new UniqueIdFetcher().getUniqueId(player);
        String verificationToken = new GeneratorUtil().getNumericString(6);

        discordDatabaseUtil.create("verificationSessions", "discordId", member.getId());

        discordDatabaseUtil.setString("verificationSessions", "uniqueId", uniqueId,
                "discordId", member.getId());

        discordDatabaseUtil.setString("verificationSessions", "verificationToken", verificationToken,
                "discordId", member.getId());
    }

    public boolean checkVerification(Member member, String triedToken) {

        String uniqueId = discordDatabaseUtil.getString("verificationSessions", "uniqueId",
                "discordId", member.getId());

        String rightToken = discordDatabaseUtil.getString("verificationSessions", "verificationToken",
                "discordId", member.getId());

        if (triedToken.equals(rightToken)) {
            if (!globalDatabaseUtil.exists("verifiedAccounts", "uniqueId", uniqueId))
                globalDatabaseUtil.create("verifiedAccounts", "uniqueId", uniqueId);

            globalDatabaseUtil.setString("verifiedAccounts", "discordId", member.getId(),
                    "uniqueId", uniqueId);

            discordDatabaseUtil.delete("verificationSessions", "discordId", member.getId());

            member.modifyNickname(new UniqueIdFetcher().getLastUsedName(uniqueId)).queue();

            return true;
        }

        return false;
    }

    public boolean checkVerification(User user, String triedToken) {

        String uniqueId = discordDatabaseUtil.getString("verificationSessions", "uniqueId",
                "discordId", user.getId());

        String rightToken = discordDatabaseUtil.getString("verificationSessions", "verificationToken",
                "discordId", user.getId());

        if (triedToken.equals(rightToken)) {
            if (!globalDatabaseUtil.exists("verifiedAccounts", "uniqueId", uniqueId))
                globalDatabaseUtil.create("verifiedAccounts", "uniqueId", uniqueId);

            globalDatabaseUtil.setString("verifiedAccounts", "discordId", user.getId(),
                    "uniqueId", uniqueId);

            discordDatabaseUtil.delete("verificationSessions", "discordId", user.getId());

            new DiscordBot().guild.getMember(user)
                    .modifyNickname(new UniqueIdFetcher().getLastUsedName(uniqueId)).queue();

            return true;
        }

        return false;
    }

    public boolean isAwaitingVerification(Member member) {

        return discordDatabaseUtil.exists("verificationSessions", "discordId", member.getId());
    }

    public boolean isAwaitingVerification(User user) {

        return discordDatabaseUtil.exists("verificationSessions", "discordId", user.getId());
    }

    public boolean isVerified(Member member) {

        return globalDatabaseUtil.exists("verifiedAccounts", "discordId", member.getId());
    }

    public boolean isVerified(String uniqueId) {

        return globalDatabaseUtil.exists("verifiedAccounts", "uniqueId", uniqueId);
    }

    public String getUniqueId(Member member) {

        return globalDatabaseUtil
                .getString("verifiedAccounts", "uniqueId", "discordId", member.getId());
    }

    public String getUniqueId(User user) {

        return globalDatabaseUtil
                .getString("verifiedAccounts", "uniqueId", "discordId", user.getId());
    }

    public String getMinecraftName(Member member) {

        return new UniqueIdFetcher().getLastUsedName(getUniqueId(member));
    }

    public String getMinecraftName(User user) {

        return new UniqueIdFetcher().getLastUsedName(getUniqueId(user));
    }
}
