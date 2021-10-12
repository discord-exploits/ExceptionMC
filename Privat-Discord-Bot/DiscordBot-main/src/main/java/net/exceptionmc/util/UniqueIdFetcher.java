package net.exceptionmc.util;

import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class UniqueIdFetcher {

    DatabaseUtil databaseUtil =
            new DatabaseUtil("global", "global", "c1lHLkZaVTYuaE8zV3hjZA==");

    public boolean playerExistsUniqueId(String uuid) {

        ResultSet resultSet
                = databaseUtil.executeQuery("SELECT * FROM registeredUniqueIds WHERE uniqueId='" + uuid + "'");

        try {

            assert resultSet != null;
            if (resultSet.next())
                return resultSet.getString("uniqueId") != null;
        } catch (SQLException sqlException) {

            sqlException.printStackTrace();
        }

        return false;
    }

    public boolean playerExistsPlayerName(String playerName) {

        ResultSet resultSet
                = databaseUtil.executeQuery("SELECT * FROM registeredUniqueIds WHERE lastName='" + playerName + "'");

        try {

            assert resultSet != null;
            if (resultSet.next())
                return resultSet.getString("lastName") != null;
        } catch (SQLException sqlException) {

            sqlException.printStackTrace();
        }

        return false;
    }

    public String getLastUsedName(String uuid) {

        String output = null;

        ResultSet resultSet
                = databaseUtil.executeQuery("SELECT * FROM registeredUniqueIds WHERE uniqueId= '" + uuid + "'");

        try {

            assert resultSet != null;
            if (resultSet.next())
                output = resultSet.getString("lastName");
        } catch (SQLException sqlException) {

            sqlException.printStackTrace();
        }

        return output;
    }

    public String getUniqueId(String player) {

        String output = null;

        ResultSet resultSet
                = databaseUtil.executeQuery("SELECT * FROM registeredUniqueIds WHERE lastName= '" + player + "'");

        try {

            assert resultSet != null;
            if (resultSet.next())
                output = resultSet.getString("uniqueId");
        } catch (SQLException sqlException) {

            sqlException.printStackTrace();
        }

        return output;
    }
}
