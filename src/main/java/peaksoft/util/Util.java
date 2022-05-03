package peaksoft.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД

    private static final String url = "jdbc:postgresql://localhost:5432/example-2";

    private static final String userName = "postgres";
    private static final String password = "ailin154896";

    public static Connection getConnection() {
        Connection connection = null ;
        try {
            connection = DriverManager.getConnection(url, userName, password);
         //   System.out.println("Connected to the PostgresSQL successfully");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }


        // ushul methoddgo connectino kaitarynyzdar
        return connection;
    }
}
