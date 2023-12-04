import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class RegistrationHandler implements HttpHandler {

    private final UserDAO userDAO;
    private final FoodOrderingApp app;

    // Constructor...
    public RegistrationHandler(UserDAO userDAO, FoodOrderingApp app) {
        this.userDAO = userDAO;
        this.app = app;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Extract and validate registration data from the request
        // ...
        String requestMethod = exchange.getRequestMethod();

        // Create a new user and add to the database
        if (requestMethod.equalsIgnoreCase("POST")) {
            // Extract data from the request body
            String requestBody = extractRequestBody(exchange.getRequestBody());
            String username = parseUsername(requestBody);
            String password = parsepassword(requestBody);

            User newUser = new User(username, password); // Assuming appropriate constructors in User class
            boolean registrationSuccess = userDAO.addUser(newUser);

            if (registrationSuccess) {
                app.sendResponse(exchange, useraddResponse());
            } else {
                app.sendResponse(exchange, "Registration failed. Please try again.");
            }
        } else {
            app.sendResponse(exchange, "Invalid request method. Use POST.");
        }
    }

    private String parsepassword(String requestBody) {
        // Implement your JSON parsing logic here
        // For simplicity, assuming a JSON structure like {"customerName": "John", "itemName": "Burger"}
        // You might use a JSON parsing library like Jackson in a real application
        return extractValueFromJson(requestBody, "password");
    }

    private String parseUsername(String requestBody) {
        // Implement your JSON parsing logic here
        // For simplicity, assuming a JSON structure like {"customerName": "John", "itemName": "Burger"}
        // You might use a JSON parsing library like Jackson in a real application
        return extractValueFromJson(requestBody, "username");
    }

    private String extractRequestBody(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stringBuilder.toString();
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

    private String useraddResponse() {
        return "User Registered";
    }
}
