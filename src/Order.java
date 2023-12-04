import java.sql.Timestamp;

public class Order {

    private int user_id;
    private int item_id;
    private int price;
    private Timestamp date;

    public Order(int user_id, int item_id, int price, Timestamp date) {
        this.user_id=user_id;
        this.item_id = item_id;
        this.price = price;
        this.date=date;
    }
}
