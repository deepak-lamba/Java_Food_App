import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodOrderingSystem {

    private Map<String, Double> menu = new HashMap<>();
    private List<Order> orders = new ArrayList<>();

    public FoodOrderingSystem() {
        initializeMenu();
    }

    public Map<String, Double> getMenu() {
        return menu;
    }

    public void placeOrder(String customerName, String itemName) {
        if (menu.containsKey(itemName)) {
            double price = menu.get(itemName);
            Order order = new Order(customerName, itemName, price);
            orders.add(order);
            System.out.println(customerName + " placed an order for " + itemName);
        } else {
            System.out.println("Item not found on the menu.");
        }
    }

    private void initializeMenu() {
        menu.put("Burger", 5.99);
        menu.put("Pizza", 8.99);
        // Add more items to the menu as needed
    }

    public List<Order> getOrders() {
        return orders;
    }
}
