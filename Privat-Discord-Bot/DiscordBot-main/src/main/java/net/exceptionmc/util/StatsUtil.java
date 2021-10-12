package net.exceptionmc.util;

import com.google.common.collect.Maps;
import net.dv8tion.jda.api.entities.Member;

import java.util.Base64;
import java.util.HashMap;

public class StatsUtil {

    private static final HashMap<String, String> gameModes = Maps.newHashMap();
    private static final HashMap<String, String[]> gameModeColumns = Maps.newHashMap();

    public static void addGameMode(String name, String encodedDatabasePassword, String[] columns) {

        gameModes.put(name.toLowerCase(), encodedDatabasePassword);
        gameModeColumns.put(name, columns);
    }

    public static boolean gameModeExists(String name) {

        return gameModes.containsKey(name);
    }

    public static String getDatabasePassword(String gameMode) {

        return gameModes.get(gameMode);
    }

    public static DatabaseUtil getDatabaseUtil(String gameMode) {

       return new DatabaseUtil(gameMode, gameMode, getDatabasePassword(gameMode));
    }

    public static String[] getGameModeColumns(String name) {

        return gameModeColumns.get(name);
    }

    public static boolean lifetimePlayed(String gameMode, String uniqueId) {

        DatabaseUtil databaseUtil = getDatabaseUtil(gameMode);
        return databaseUtil.exists("lifetimeStats", "uniqueId", uniqueId);
    }

    public static boolean monthlyPlayed(String gameMode, String uniqueId) {

        DatabaseUtil databaseUtil = getDatabaseUtil(gameMode);
        return databaseUtil.exists("monthlyStats", "uniqueId", uniqueId);
    }

    public static String getStatsMessage(String name, Member member) {

        DatabaseUtil databaseUtil = getDatabaseUtil(name);
        String[] columns = getGameModeColumns(name);

        String uniqueId = new VerificationUtil().getUniqueId(member);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("**Lifetime**");
        if (lifetimePlayed(name, uniqueId)) {
            for (String currentColumn : columns) {

                Integer result =
                        databaseUtil.getInteger("lifetimeStats", currentColumn.toLowerCase(),
                                "uniqueId", uniqueId);
                stringBuilder.append("\n").append(currentColumn).append(" » **").append(result).append("**");
            }
        } else {

            stringBuilder.append("\n").append("Not Played!");
        }

        stringBuilder.append("\n\n").append("**Monthly**");
        if (monthlyPlayed(name, uniqueId)) {
            for (String currentColumn : columns) {

                Integer result =
                        databaseUtil.getInteger("monthlyStats", currentColumn.toLowerCase(),
                                "uniqueId", uniqueId);
                stringBuilder.append("\n").append(currentColumn).append(" » **").append(result).append("**");
            }
        } else {

            stringBuilder.append("\n").append("Not Played!");
        }

        return stringBuilder.toString();
    }

    public static String getStatsMessage(String name, String playerName) {

        DatabaseUtil databaseUtil = getDatabaseUtil(name);
        String[] columns = getGameModeColumns(name);

        String uniqueId = new UniqueIdFetcher().getUniqueId(playerName);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("**Lifetime**");
        if (lifetimePlayed(name, uniqueId)) {
            for (String currentColumn : columns) {

                Integer result =
                        databaseUtil.getInteger("lifetimeStats", currentColumn.toLowerCase(),
                                "uniqueId", uniqueId);
                stringBuilder.append("\n").append(currentColumn).append(" » **").append(result).append("**");
            }
        } else {

            stringBuilder.append("\n").append("Not Played!");
        }

        stringBuilder.append("\n\n").append("**Monthly**");
        if (monthlyPlayed(name, uniqueId)) {
            for (String currentColumn : columns) {

                Integer result =
                        databaseUtil.getInteger("monthlyStats", currentColumn.toLowerCase(),
                                "uniqueId", uniqueId);
                stringBuilder.append("\n").append(currentColumn).append(" » **").append(result).append("**");
            }
        } else {

            stringBuilder.append("\n").append("Not Played!");
        }

        return stringBuilder.toString();
    }
}
