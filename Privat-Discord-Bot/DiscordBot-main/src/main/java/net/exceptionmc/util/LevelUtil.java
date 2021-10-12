package net.exceptionmc.util;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.exceptionmc.DiscordBot;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class LevelUtil {

    private static final HashMap<Member, Integer> members = new HashMap<>();
    private static final HashMap<Integer, Long> level = new HashMap<>();
    private static final DatabaseUtil databaseUtil =
            new DatabaseUtil("discord", "discord", "OHRoaEx2bFM5QnVoNUZRTHpZaWk=");

    public static void createTable() {

        databaseUtil.executeUpdate("CREATE TABLE IF NOT EXISTS level(discordId VARCHAR(255), xp BIGINT)");
    }

    public static boolean memberExists(Member member) {

        return databaseUtil.exists("level", "discordId", member.getId());
    }

    public static void createMember(Member member) {

        if (!memberExists(member)) {

            databaseUtil.create("level", "discordId", member.getId());
            databaseUtil.setLong("level", "xp", 0L, "discordId", member.getId());
        }
    }

    public static void deleteMember(Member member) {

        databaseUtil.delete("level", "discordId", member.getId());
    }

    public static void deleteMember(User user) {

        databaseUtil.delete("level", "discordId", user.getId());
    }

    public static void initLevel() {

        level.put(0, 0L);
        level.put(1, 75L);
        level.put(2, 250L);
        level.put(3, 525L);
        level.put(4, 900L);
        level.put(5, 1375L);
        level.put(6, 1950L);
        level.put(7, 2625L);
        level.put(8, 3400L);
        level.put(9, 4275L);
        level.put(10, 5250L);
        level.put(11, 6325L);
        level.put(12, 7500L);
        level.put(13, 8775L);
        level.put(14, 10150L);
        level.put(15, 11625L);
        level.put(16, 13200L);
        level.put(17, 14875L);
        level.put(18, 16650L);
        level.put(19, 18525L);
        level.put(20, 20500L);
        level.put(21, 22575L);
        level.put(22, 24750L);
        level.put(23, 27025L);
        level.put(24, 29400L);
        level.put(25, 31875L);
        level.put(26, 34450L);
        level.put(27, 37125L);
        level.put(28, 39900L);
        level.put(29, 42775L);
        level.put(30, 45750L);
        level.put(31, 48825L);
        level.put(32, 52000L);
        level.put(33, 55275L);
        level.put(34, 58650L);
        level.put(35, 62125L);
        level.put(36, 65700L);
        level.put(37, 69375L);
        level.put(38, 73150L);
        level.put(39, 77025L);
        level.put(40, 81000L);
        level.put(41, 85075L);
        level.put(42, 89250L);
        level.put(43, 93525L);
        level.put(44, 97900L);
        level.put(45, 102375L);
        level.put(46, 106950L);
        level.put(47, 111625L);
        level.put(48, 116400L);
        level.put(49, 121275L);
        level.put(50, 126250L);
        level.put(51, 131325L);
        level.put(52, 136500L);
        level.put(53, 141775L);
        level.put(54, 147150L);
        level.put(55, 152625L);
        level.put(56, 158200L);
        level.put(57, 163875L);
        level.put(58, 169650L);
        level.put(59, 175525L);
        level.put(60, 181500L);
        level.put(61, 187575L);
        level.put(62, 193750L);
        level.put(63, 200025L);
        level.put(64, 206400L);
        level.put(65, 212875L);
        level.put(66, 219450L);
        level.put(67, 226125L);
        level.put(68, 232900L);
        level.put(69, 239775L);
        level.put(70, 246750L);
        level.put(71, 253825L);
        level.put(72, 261000L);
        level.put(73, 268275L);
        level.put(74, 275650L);
        level.put(75, 283125L);
    }

    public static Integer getLevel(Member member) {

        long xp = databaseUtil.getLong("level", "xp", "discordId", member.getId());

        int levelResult = 0;
        for (int i = 0; i < level.size(); i++) {
            if (level.get(i) != 0) {
                if (xp >= level.get(i)) {

                    levelResult++;
                }
            }
        }

        return levelResult;
    }

    public static void setLevel(Member member, Integer amount) {

        long xp = level.get(amount);
        databaseUtil.setLong("level", "xp", xp, "discordId", member.getId());
    }

    public static Long getXp(Member member) {

        return databaseUtil.getLong("level", "xp", "discordId", member.getId());
    }

    public static Long getXpOfLevel(Integer amount) {

        return level.get(amount);
    }

    public static void startCounting() {

        for (Member member : new DiscordBot().guild.getMembers()) {
            if (members.containsKey(member)) {

                members.replace(member, members.get(member) + 1);
                if (members.get(member) == 60) {

                    members.remove(member);
                }
            }
        }
    }

    public static void addMessageXP(Member member, TextChannel textChannel) {

        if (!members.containsKey(member)) {

            members.put(member, 0);

            long oldLevel = getLevel(member);
            Double randomLong = new GeneratorUtil().getRandomDouble(25D, 50D);
            databaseUtil.setLong("level", "xp",
                    (long) (databaseUtil.getLong("level", "xp", "discordId", member.getId()) +
                            randomLong), "discordId", member.getId());

            long newLevel = getLevel(member);
            if (oldLevel < newLevel) {

                assert textChannel != null;
                new EmbedUtil().sendEmbed(textChannel, "ExceptionMC » Level",
                        "Congratulations " + member.getAsMention() +
                                ". You have just risen to level " + getLevel(member) + ".");
            }
        }
    }

    public static void sendTop10(Member member, TextChannel textChannel) {

        ArrayList<String> arrayList = databaseUtil.getTop("level", "xp", "discordId", 10);

        StringBuilder stringBuilder = new StringBuilder();
        int counter = 1;
        for (String discordId : arrayList) {

            System.out.println(discordId);

            Member memberOnPosition = new DiscordBot().guild.getMemberById(discordId);
            assert memberOnPosition != null;
            stringBuilder
                    .append("\n#")
                    .append(counter)
                    .append(". » ")
                    .append(memberOnPosition.getAsMention())
                    .append(" | Level » **")
                    .append(getLevel(member))
                    .append("**");

            counter++;
        }

        if (!arrayList.contains(member.getId())) {

            int ranking = databaseUtil.getRanking("level", "xp",
                    "discordId", member.getId());
            stringBuilder
                    .append("\n\n...\n\n#")
                    .append(ranking)
                    .append(". » ")
                    .append(member.getAsMention())
                    .append(" | Level » **")
                    .append(getLevel(member))
                    .append("**");
        }

        new EmbedUtil().sendEmbed(textChannel, "ExceptionMC » Level",
                stringBuilder.toString());
    }
}
