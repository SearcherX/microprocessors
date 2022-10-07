package homework.microprocessors.db;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static String DRIVER = "com.mysql.cj.jdbc.Driver";
    //для хоста нужно указать localhost:8080, для докера - название сервиса база данных
//    private static String URL = "jdbc:mysql://app-db/hardware";
    private static String URL = "jdbc:mysql://localhost:3306/hardware";
    private static String USER = "root";
    private static String PASSWORD = "Matrix12_";
//    private static final String DRIVER = System.getenv("dbDriver");
//    private static final String URL = System.getenv("dbConnectionUrl");
//    private static final String USER = System.getenv("dbUserName");
//    private static final String PASSWORD = System.getenv("dpPassword");

    public static Connection openDBConnection() {
        try {
            Class.forName(DRIVER)
                    .getDeclaredConstructor()
                    .newInstance();
            // 2. создать соединение
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (InstantiationException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                 IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
