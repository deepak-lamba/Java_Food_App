import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.Scanner;

public class FoodOrderingApp {

    public static void main(String[] args) throws IOException, SQLException {

        MenuItemDAO menuItemDAO = new MenuItemDAO(DatabaseConnection.connect()); // Assuming DatabaseConnection provides the connection
        OrderDAO orderDAO = new OrderDAO(DatabaseConnection.connect());
        UserDAO userDAO = new UserDAO(DatabaseConnection.connect());

        FoodOrderingSystem foodOrderingSystem = new FoodOrderingSystem(orderDAO);

        // Create a single instance of FoodOrderingApp
        FoodOrderingApp app = new FoodOrderingApp();

        // Pass the FoodOrderingSystem instance to MenuHandler and PlaceOrderHandler
        MenuHandler menu = new MenuHandler(menuItemDAO, foodOrderingSystem, app);
        PlaceOrderHandler order = new PlaceOrderHandler(orderDAO, foodOrderingSystem, app);
        RegistrationHandler registration = new RegistrationHandler(userDAO, app);
        LoginHandler login = new LoginHandler(userDAO, app);
        AddMenuItemHandler addMenuItem = new AddMenuItemHandler(menuItemDAO, app);

        // Create the HTTP server
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Assign contexts to handlers
        server.createContext("/menu", menu);
        server.createContext("/placeorder", order);
        server.createContext("/register", registration);
        server.createContext("/login", login);
        server.createContext("/additem", addMenuItem);

        // Set executor and start the server
        server.setExecutor(null);
        server.start();

        System.out.println("Server is running on port 8080. Press Enter to stop.");
        new Scanner(System.in).nextLine(); // Wait for the user to press Enter
        server.stop(0);
        System.out.println("Server stopped.");
    }

    // ... rest of the class remains unchanged

    // Make sendResponse non-static
    public void sendResponse(HttpExchange exchange, String response) throws IOException {
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
