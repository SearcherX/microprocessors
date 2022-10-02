package homework.microprocessors.db;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static String url = "jdbc:mysql://localhost:3306/hardware";
    private static String dbUser = "root";
    private static String dbPassword = "Matrix12_";

    public static Connection openDBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver")
                    .getDeclaredConstructor()
                    .newInstance();
            // 2. создать соединение
            return DriverManager.getConnection(url, dbUser, dbPassword);
        } catch (InstantiationException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                 IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
