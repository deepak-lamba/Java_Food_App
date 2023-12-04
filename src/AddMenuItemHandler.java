import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class AddMenuItemHandler implements HttpHandler {

    private final MenuItemDAO menuItemDAO;
    private final FoodOrderingApp app;

    public AddMenuItemHandler(MenuItemDAO menuItemDAO, FoodOrderingApp app) {
        this.menuItemDAO = menuItemDAO;
        this.app = app;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Extract data from the request (similar to PlaceOrderHandler)
        // Extract and validate registration data from the request
        // ...
        String requestMethod = exchange.getRequestMethod();

        if (requestMethod.equalsIgnoreCase("POST")) {
        // For simplicity, assuming a JSON structure like {"itemName": "New Item", "price": 9.99}
        String requestBody = extractRequestBody(exchange.getRequestBody());
        String itemName = extractValueFromJson(requestBody, "name");
        int price = Integer.parseInt(extractValueFromJson(requestBody, "price"));

        // Add the new item to the menu
        boolean itemAdded = addItemToMenu(itemName, price);

        if (itemAdded) {
            app.sendResponse(exchange, "Item added to the menu successfully.");
        } else {
            app.sendResponse(exchange, "Failed to add the item to the menu. Please try again.");
        }
        } else {
            app.sendResponse(exchange, "Invalid request method. Use POST.");
        }
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

    private boolean addItemToMenu(String itemName, int price) {
        // Implement your logic to add the new item to the menu
        // Use menuItemDAO.addItem() or modify as needed
        return menuItemDAO.addItem(itemName, price);
    }

    // Rest of the class remains unchanged...
}
