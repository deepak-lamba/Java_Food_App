import java.awt.*;
import java.sql.Timestamp;
import java.util.List;

public class FoodOrderingSystem {

    private OrderDAO orderDAO;

    public FoodOrderingSystem(OrderDAO orderDAO) {

        this.orderDAO = orderDAO;
    }

    public boolean placeOrder(int customerid, int itemid, int price, Timestamp date) {

        if (price > 0) {
            Order order = new Order(customerid, itemid, price, date);
            boolean success = orderDAO.placeOrder(customerid, itemid, price, date);

            if (success) {
                System.out.println(customerid + " placed an order for " + itemid);
            } else {
                System.out.println("Failed to place the order. Please try again.");
            }
            return success;
        } else {
            System.out.println("Item not found on the menu.");
            return false;
        }
    }

    public List<Order> getOrders() {
        // Assuming you have a method in OrderDAO to fetch all orders
        return orderDAO.getAllOrders();
    }
}
