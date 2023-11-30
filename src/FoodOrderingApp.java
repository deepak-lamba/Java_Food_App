import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class FoodOrderingApp {

    public static void main(String[] args) throws IOException {
        FoodOrderingSystem foodOrderingSystem = new FoodOrderingSystem();
        FoodOrderingApp app = new FoodOrderingApp(); // Create a single instance of FoodOrderingApp
        MenuHandler menu=new MenuHandler(foodOrderingSystem, app);
        PlaceOrderHandler order=new PlaceOrderHandler(foodOrderingSystem, app);

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/menu", menu);
        server.createContext("/place-order", order);
        server.setExecutor(null);
        server.start();

        System.out.println("Server is running on port 8080. Press Enter to stop.");
        new Scanner(System.in).nextLine(); // Wait for user to press Enter
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
