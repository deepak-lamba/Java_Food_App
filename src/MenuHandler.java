import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.util.Map;

public class MenuHandler implements HttpHandler {

    private final FoodOrderingSystem foodOrderingSystem;
    private final FoodOrderingApp app;

    public MenuHandler(FoodOrderingSystem foodOrderingSystem, FoodOrderingApp app) {
        this.foodOrderingSystem = foodOrderingSystem;
        this.app = app;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = getMenuResponse();
        app.sendResponse(exchange, response);
    }

    private String getMenuResponse() {
        Map<String, Double> menuCopy = foodOrderingSystem.getMenu();

        StringBuilder menuString = new StringBuilder("Menu:\n");
        for (String entry : menuCopy.keySet()) {
            menuString.append(entry).append(": $").append(menuCopy.get(entry)).append("\n");
        }
        return menuString.toString();
    }
}
