import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;

public class PlaceOrderHandler implements HttpHandler {

    private final OrderDAO orderDAO;
    private final FoodOrderingApp app;

    private final FoodOrderingSystem FS;

    public PlaceOrderHandler(OrderDAO orderDAO, FoodOrderingSystem FS, FoodOrderingApp app) {
        this.orderDAO = orderDAO;
        this.app = app;
        this.FS=FS;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Extract customer name and item name from the request
        String requestMethod = exchange.getRequestMethod();

        if (requestMethod.equalsIgnoreCase("POST")) {
            // Extract data from the request body
            String requestBody = extractRequestBody(exchange.getRequestBody());
            int customerid = Integer.parseInt(parseUserID(requestBody));
            int itemid = Integer.parseInt(parseItemID(requestBody));
            int price = Integer.parseInt(parsePrice(requestBody));
            Timestamp date=new Timestamp(System.currentTimeMillis());

            boolean orderPlaced = placeOrder(customerid, itemid, price, date);

            if (orderPlaced) {
                app.sendResponse(exchange, getOrderPlacementResponse());
            } else {
                app.sendResponse(exchange, "Failed to place the order. Please try again.");
            }
        } else {
            app.sendResponse(exchange, "Invalid request method. Use POST.");
        }
    }

    private String extractRequestBody(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }

    private String parseUserID(String requestBody) {
        // Implement your JSON parsing logic here
        // For simplicity, assuming a JSON structure like {"customerName": "John", "itemName": "Burger"}
        // You might use a JSON parsing library like Jackson in a real application
        return extractValueFromJson(requestBody, "user_id");
    }

    private String parseItemID(String requestBody) {
        // Implement your JSON parsing logic here
        // For simplicity, assuming a JSON structure like {"customerName": "John", "itemName": "Burger"}
        // You might use a JSON parsing library like Jackson in a real application
        return extractValueFromJson(requestBody, "item_id");
    }

    private String parsePrice(String requestBody) {
        // Implement your JSON parsing logic here
        // For simplicity, assuming a JSON structure like {"customerName": "John", "itemName": "Burger"}
        // You might use a JSON parsing library like Jackson in a real application
        return extractValueFromJson(requestBody, "price");
    }

    private String extractValueFromJson(String jsonString, String key) {
        // Find the key in the JSON-like string
        String keyWithQuotes = "\"" + key + "\":";
        int keyIndex = jsonString.indexOf(keyWithQuotes);

        if (keyIndex == -1) {
            // Key not found
            return null;
        }

        // Find the value associated with the key
        int startIndex = keyIndex + keyWithQuotes.length();
        int endIndex = jsonString.indexOf(",", startIndex);
        if (endIndex == -1) {
            endIndex = jsonString.indexOf("}", startIndex);
        }

        if (endIndex == -1) {
            // Value not found
            return null;
        }

        // Extract the value
        return jsonString.substring(startIndex, endIndex).trim();
    }


    private boolean placeOrder(int customerid, int itemid, int price, Timestamp date) {
        // Implement your order placement logic here
        // Use orderDAO.placeOrder() or modify as needed
        return FS.placeOrder(customerid, itemid, price, date);
    }

    private String getOrderPlacementResponse() {
        // You might customize the response based on the result of order placement
        return "Order placed successfully.";
    }
}
