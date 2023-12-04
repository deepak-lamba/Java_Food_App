import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class LoginHandler implements HttpHandler {

    private final UserDAO userDAO;
    private final FoodOrderingApp app;

    public LoginHandler(UserDAO userDAO, FoodOrderingApp app) {
        this.userDAO = userDAO;
        this.app = app;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            // Extract and validate login credentials from the request
            String requestBody = extractRequestBody(exchange.getRequestBody());
            String username = extractValueFromJson(requestBody, "username");
            String password = extractValueFromJson(requestBody, "password");

            // Check if the user exists and the password is correct
            boolean loginSuccess = userDAO.authenticateUser(username, password);

            // Respond based on the result
            if (loginSuccess) {
                app.sendResponse(exchange, "Login successful.");
            } else {
                app.sendResponse(exchange, "Login failed. Please check your credentials.");
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

}
