import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.util.List;
import java.util.Map;
import java.io.IOException;

public class MenuHandler implements HttpHandler {

    private final MenuItemDAO menuItemDAO;
    private final FoodOrderingApp app;

    private final FoodOrderingSystem FS;

    public MenuHandler(MenuItemDAO menuItemDAO, FoodOrderingSystem FS, FoodOrderingApp app) {
        this.menuItemDAO = menuItemDAO;
        this.app = app;
        this.FS=FS;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = getMenuResponse();
        app.sendResponse(exchange, response);
    }

    private String getMenuResponse() {
        // Assuming you have a method in MenuItemDAO to fetch all menu items and their prices
        List<MenuItem> menuItems = menuItemDAO.getAllMenuItems();

        StringBuilder menuString = new StringBuilder("Menu:\n");
        for (MenuItem entry : menuItems) {
            menuString.append(entry.getid()).append(" ").append(entry.getname()).append(" ").append(entry.getprice()).append("\n");
        }
        return menuString.toString();
    }
}
