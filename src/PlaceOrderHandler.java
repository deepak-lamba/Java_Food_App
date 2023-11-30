import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;

public class PlaceOrderHandler implements HttpHandler {

    private final FoodOrderingSystem foodOrderingSystem;
    private final FoodOrderingApp app;

    public PlaceOrderHandler(FoodOrderingSystem foodOrderingSystem, FoodOrderingApp app) {
        this.foodOrderingSystem = foodOrderingSystem;
        this.app = app;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Extract customer name and item name from the request (in a real app, you would parse JSON or form data)
        String customerName = "John";
        String itemName = "Burger";

        placeOrder(customerName, itemName);
        app.sendResponse(exchange, getOrderPlacementResponse());
    }

    private void placeOrder(String customerName, String itemName) {
        foodOrderingSystem.placeOrder(customerName, itemName);
    }

    private String getOrderPlacementResponse() {
        // You might customize the response based on the result of order placement
        return "Order placed successfully.";
    }
}
