package net.exceptionmc.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;

public class DatabaseUtil {

    private final String database, username, password;
    private Connection connection;

    public DatabaseUtil(String database, String username, String encodedPassword) {

        this.database = database;
        this.username = username;
        this.password = new String(Base64.getDecoder().decode(encodedPassword));
    }

    public void executeUpdate(String query) {

        try {

            connection = DriverManager.getConnection("jdbc:mysql://45.142.114.182:3306/" + database + "?serverTimezone=America/New_York", username, password);

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {

            sqlException.printStackTrace();
        }
    }

    public ResultSet executeQuery(String query) {

        ResultSet resultSet;

        try {

            connection = DriverManager.getConnection("jdbc:mysql://45.142.114.182:3306/" + database + "?serverTimezone=America/New_York", username, password);

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            return resultSet;
        } catch (SQLException sqlException) {

            sqlException.printStackTrace();
        }

        return null;
    }

    // -- //

    public boolean exists(String table, String whereColumn, String value) {
        try {

            ResultSet resultSet = executeQuery("SELECT * FROM `" + table + "` WHERE `"
                    + whereColumn + "` = '" + value + "'");
            assert resultSet != null;
            return (resultSet.next() && resultSet.getString(whereColumn) != null);
        } catch (SQLException sQLException) {

            sQLException.printStackTrace();
            return false;
        }
    }

    public boolean exists(String table, String whereColumn1, String whereValue1, String whereColumn2, String whereValue2) {
        try {

            ResultSet resultSet = executeQuery("SELECT * FROM `" + table + "` WHERE `"
                    + whereColumn1 + "` = '" + whereValue1 + "' AND `"
                    + whereColumn2 + "` = '" + whereValue2 + "'");

            return (
                    resultSet.next()
                            && resultSet.getString(whereColumn1) != null
                            && resultSet.getString(whereColumn2) != null
            );
        } catch (SQLException sQLException) {

            sQLException.printStackTrace();
            return false;
        }
    }

    public void create(String table, String column, String value) {

        executeUpdate("INSERT INTO " + table + " (" + column + ") VALUES ('" + value + "');");
    }

    public void create(String table, String column1, String value1, String column2, String value2) {

        executeUpdate("INSERT INTO " + table + " (" + column1 + ", " + column2 + ") " +
                "VALUES ('" + value1 + "', + '" + value2 + "');");
    }

    public void delete(String table, String whereColumn, String whereValue) {

        executeUpdate("DELETE FROM " + table + " WHERE " + whereColumn + "='" + whereValue + "';");
    }

    public void delete(String table, String whereColumn1, String whereValue1, String whereColumn2, String whereValue2) {

        executeUpdate("DELETE FROM " + table + " WHERE " + whereColumn1 + "='" + whereValue1 +
                "' AND " + whereColumn1 + "='" + whereValue1 + "';");
    }

    public void setString(String table, String column, String value, String whereColumn, String whereValue) {

        executeUpdate("UPDATE " + table + " SET " + column + " = '" + value +
                "' WHERE " + whereColumn + " ='" + whereValue + "';");
    }

    public void setInteger(String table, String column, Integer value, String whereColumn, String whereValue) {

        executeUpdate("UPDATE " + table + " SET " + column + " = '" + value +
                "' WHERE " + whereColumn + " = '" + whereValue + "';");
    }

    public void setLong(String table, String column, Long value, String whereColumn, String whereValue) {

        executeUpdate("UPDATE " + table + " SET " + column + " = '" + value +
                "' WHERE " + whereColumn + " = '" + whereValue + "';");
    }

    public String getString(String table, String column, String whereColumn, String whereValue) {

        ResultSet resultSet = executeQuery("SELECT * FROM " + table + " WHERE "
                + whereColumn + " = '" + whereValue + "'");

        try {
            if (resultSet != null && resultSet.next()) {

                return resultSet.getString(column);
            }
        } catch (java.sql.SQLException sqlException) {

            sqlException.printStackTrace();
        }

        return "null";
    }

    public ArrayList<String> getStrings(String table, String column, String whereColumn, String whereValue) {

        ArrayList<String> arrayList = new ArrayList<>();

        ResultSet resultSet = executeQuery("SELECT * FROM " + table + " WHERE "
                + whereColumn + " = '" + whereValue + "'");

        try {

            while (resultSet.next())
                arrayList.add(resultSet.getString(column));
        } catch (java.sql.SQLException sqlException) {

            sqlException.printStackTrace();
        }

        return arrayList;
    }

    public ArrayList<String> getStrings(String table, String column) {

        ArrayList<String> arrayList = new ArrayList<>();

        ResultSet resultSet = executeQuery("SELECT * FROM " + table);

        try {

            while (resultSet.next())
                arrayList.add(resultSet.getString(column));
        } catch (java.sql.SQLException sqlException) {

            sqlException.printStackTrace();
        }

        return arrayList;
    }

    public Integer getInteger(String table, String column, String whereColumn, String whereValue) {

        ResultSet resultSet = executeQuery("SELECT * FROM " + table + " WHERE "
                + whereColumn + " = '" + whereValue + "'");

        try {
            if (resultSet != null && resultSet.next()) {

                return resultSet.getInt(column);
            }
        } catch (java.sql.SQLException sqlException) {

            sqlException.printStackTrace();
        }

        return 0;
    }

    public Long getLong(String table, String column, String whereColumn, String whereValue) {

        ResultSet resultSet = executeQuery("SELECT * FROM " + table + " WHERE "
                + whereColumn + " = '" + whereValue + "'");

        try {
            if (resultSet != null && resultSet.next()) {

                return resultSet.getLong(column);
            }
        } catch (java.sql.SQLException sqlException) {

            sqlException.printStackTrace();
        }

        return 0L;
    }

    public ArrayList<String> getTop(String tableName, String orderColumn, String where, Integer topType) {

        ArrayList<String> top = new ArrayList<>();
        ResultSet resultSet = executeQuery("SELECT " + where + " FROM " + tableName +
                " ORDER BY " + orderColumn + " DESC LIMIT " + topType + "");

        while (true) {
            try {

                if (!resultSet.next()) break;
                top.add(resultSet.getString(1));
            } catch (SQLException sqlException) {

                sqlException.printStackTrace();
            }
        }

        return top;
    }

    public Integer getRanking(String tableName, String column, String whereColumn, String whereValue) {

        int counter = 0;
        try {

            ResultSet resultSet = executeQuery("SELECT " + whereColumn + " FROM " + tableName +
                    " ORDER BY " + column + " DESC");

            do {

                assert resultSet != null;
                if (!resultSet.next())
                    break;
                counter++;
            } while (!resultSet.getString(whereColumn).equalsIgnoreCase(whereValue));
        } catch (Exception exception) {

            exception.printStackTrace();
        }

        return counter;
    }
}