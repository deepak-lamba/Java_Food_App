public class MenuItem {
    private int id;
    private String name;
    private int price;


    public MenuItem(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
    public int getid(){
        return this.id;
    }

    public String getname(){
        return this.name;
    }

    public int getprice(){
        return this.price;
    }
}
