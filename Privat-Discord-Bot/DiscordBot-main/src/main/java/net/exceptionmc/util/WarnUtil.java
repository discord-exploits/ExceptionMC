package net.exceptionmc.util;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.exceptionmc.DiscordBot;

public class WarnUtil {

    DatabaseUtil databaseUtil;

    public WarnUtil() {

        databaseUtil =
                new DatabaseUtil("discord", "discord", "OHRoaEx2bFM5QnVoNUZRTHpZaWk=");
    }

    public void createTable() {

        databaseUtil.executeUpdate("CREATE TABLE IF NOT EXISTS warns" +
                "(discordId VARCHAR(255), warnId VARCHAR(255), expiration BIGINT, reason VARCHAR(2040))");
    }

    public Integer getWarnCount(Member member) {

        return databaseUtil.getStrings("warns", "reason", "discordId", member.getId()).size();
    }

    public void warn(TextChannel textChannel, Member member, String reason) {

        String warnId = new GeneratorUtil().getAlphaNumericString(8);
        Long expirationLong = System.currentTimeMillis() + 1000L * 60L * 60L * 24L * 30L;
        String[] expirationStringArray = String.valueOf(expirationLong).split("");
        StringBuilder stringBuilder = new StringBuilder();
        for (int count = 0; count < expirationStringArray.length - 4; count++) {

            stringBuilder.append(expirationStringArray[count]);
        }

        long expiration = Long.parseLong(stringBuilder.toString());

        databaseUtil.create("warns", "discordId", member.getId(), "warnId", warnId);
        databaseUtil.setLong("warns", "expiration", expiration, "warnId", warnId);
        databaseUtil.setString("warns", "reason", reason, "warnId", warnId);

        new EmbedUtil().sendEmbed(textChannel, "ExceptionMC » Warn",
                "Who » **" + member.getAsMention() + "**" +
                        "\nReason » **" + reason + "**" +
                        "\nExpiration » **" + new MillisToStringUtil().formatMillis(System.currentTimeMillis()) + "**" +
                        "\nId » **" + warnId + "**" +
                        "\nWarnCount » **" + getWarnCount(member) + "**");
    }

    public void checkForRevoke() {

        Long currentTimeMillisLong = System.currentTimeMillis();
        String[] currentTimeMillisStringArray = String.valueOf(currentTimeMillisLong).split("");
        StringBuilder stringBuilder = new StringBuilder();
        for (int count = 0; count < currentTimeMillisStringArray.length - 4; count++) {

            stringBuilder.append(currentTimeMillisStringArray[count]);
        }

        long currentTimeMillis = Long.parseLong(stringBuilder.toString());

        for (String warnId :
                databaseUtil.getStrings("warns", "warnId",
                        "expiration", Long.toString(currentTimeMillis))) {

            User user =
                    DiscordBot.jda.getUserById(databaseUtil.getString("warns", "discordId",
                            "warnId", warnId));
            assert user != null;
            new EmbedUtil().sendEmbed(user, "ExceptionMC » Warn",
                    "Your warning with the ID **" + warnId + "** has just expired.");

            databaseUtil.delete("warns", "warnId", warnId);
        }
    }
}
