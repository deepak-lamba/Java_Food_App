import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    static {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading MySQL JDBC driver");
        }
    }


    public static Connection connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/Food_App_DB";
        String user = "root";
        String password = "qwerty123";

        return DriverManager.getConnection(url, user, password);
    }
}
