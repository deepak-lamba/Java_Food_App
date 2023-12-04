import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDAO {

    private Connection connection;

    public OrderDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean placeOrder(int userId, int menuItemId, int price, Timestamp date) {
        String query = "INSERT INTO orders (user_id, item_id, order_date, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, menuItemId);
            statement.setTimestamp(3, date);
            statement.setInt(4, price);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Order> getAllOrders() {
        List<Order> order = new ArrayList<>();
        String query = "SELECT * FROM orders";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                int itemId = resultSet.getInt("item_id");
                Timestamp date = resultSet.getTimestamp("order_date");
                int price = resultSet.getInt("price");
                order.add(new Order(userId, itemId, price, date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }
}
