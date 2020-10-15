package World.Persons;

import java.sql.*;
import java.util.ArrayList;

public class DBSQL {
    private final static String DB_DRIVER = "com.mysql.jdbc.Driver";
    private final static String DB_CONNECTION = "jdbc:mysql://localhost:3306/test?" +
            "useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private final static String DB_USER = "root";
    private final static String DB_PASSWORD = "";

    private static Connection getDBConnection() {
        Connection dbConection = null;
        try {
            Class.forName(DB_DRIVER);
            dbConection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConection;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dbConection;
    }

    /**
     * поиск существует пользователь в базе или нет, если нет то проходит регистрация
     *
     * @param log
     * @param psw
     * @return возвращает id зарегистрированного пользователя
     * @throws SQLException
     */
    public static String validate(String log, String psw) throws SQLException {
        String sqlSelect = "SELECT COUNT(id) as count, id, password FROM `game` WHERE `login` = '" + log + "';";

        Connection dbConnection = null;
        Statement statement = null;
        dbConnection = getDBConnection();
        ResultSet rs = null;
        try {
            statement = dbConnection.createStatement();
            rs = statement.executeQuery(sqlSelect);
            if (rs.next()) {
               // int i = rs.getInt("count");
                if (rs.getInt("count") > 0) {
                    if (rs.getString("password").equals(psw)) {
                        System.out.println("Entered user: " + log);
                        return rs.getString("id") + " 1"; //при успешной валидации возвращается id пользователя
                    }
                } else {
                    registration(log, psw, dbConnection,statement);
                    rs = (statement.executeQuery(sqlSelect));
                    if (rs.next())
                    return rs.getString("id") + " 0";
                }
            }
        } catch (SQLException e) {
            return "-1";
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
        return "-1"; //если валидация не пройдена, то возарвщает значение -1
    }

    /**
     * добавление пользователя в базу
     *
     * @param log
     * @param psw
     * @throws SQLException
     */
    private static void registration(String log, String psw, Connection dbConnection, Statement statement) {
        String sqlInsert = "INSERT INTO `game` (`login`, `password`) " +
                "VALUES ('" + log + "', '" + psw + "' );";

        try {
            statement = dbConnection.createStatement();
            statement.execute(sqlInsert);
            System.out.println("Registered new user login: " + log);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * функция создание персонажа
     *
     * @param idPerson
     * @param namePerson
     * @param classPerson
     * @param x
     * @param y
     */
    public static void createCharacter(String idPerson, char namePerson, String classPerson, int x, int y) {
        String sqlUpdate = "UPDATE `game` SET `name` = '" + namePerson + "', `class` ='" + classPerson + "'" +
                ", `x` = " + x + ", `y` = " + y + " WHERE `id` = " + idPerson + ";";
        Connection dbConnection = null;
        Statement statement = null;
        dbConnection = getDBConnection();
        try {
            statement = dbConnection.createStatement();
            statement.execute(sqlUpdate);
            System.out.println("Create new character name: " + namePerson + ", class: " + classPerson);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (dbConnection != null) {
                try {
                    dbConnection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * сохранение данных при дисконекте
     *
     * @param idPerson
     * @param x
     * @param y
     */
    public static void saveCharacter(String idPerson, int x, int y) {
        String sqlUpdate = "UPDATE `game` SET `x` = " + x + ", `y` = " + y + " WHERE `id` = " + idPerson + ";";
        Connection dbConnection = null;
        Statement statement = null;
        dbConnection = getDBConnection();
        try {
            statement = dbConnection.createStatement();
            statement.execute(sqlUpdate);
        } catch (SQLException e) {
            System.out.println("erroor in DBBB");
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("erroor in DBBB");
                }
            }
            if (dbConnection != null) {
                try {
                    dbConnection.close();
                } catch (SQLException e) {
                    System.out.println("erroor in DBBB");

                }
            }
        }
    }

    /**
     * получение данных при повторном входе
     *
     * @param log
     * @param psw
     * @throws SQLException
     */
    public static ArrayList getCharacter(String log, String psw) throws SQLException {
        String sqlSelect = "SELECT * FROM `game` WHERE `login` = '" + log + "' AND `password` = '" + psw + "';";
        Connection dbConnection = null;
        Statement statement = null;
        dbConnection = getDBConnection();
        ArrayList data = new ArrayList();
        ResultSet rs = null;
        try {
            statement = dbConnection.createStatement();
            rs = statement.executeQuery(sqlSelect);
            while (rs.next()) {
                data.add(rs.getString("id"));
                data.add(rs.getString("name").charAt(0));
                data.add(rs.getString("class"));
                data.add(rs.getInt("x"));
                data.add(rs.getInt("y"));
            }
        } catch (SQLException e) {
            System.out.println("error in DBBB");
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
        return data;
    }

    /**
     * очистка всей базы
     *
     * @throws SQLException
     */
    public static void delTable() throws SQLException {
        String sqlDrop = "DELETE FROM `game`;";
        Connection dbConnection = null;
        Statement statement = null;
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            int dp = statement.executeUpdate(sqlDrop);
            System.out.println("Delete data tables");
        } catch (SQLException e) {
            System.out.println("Not delete data tables");
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }
}
