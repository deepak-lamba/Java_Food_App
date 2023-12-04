public class User {
    private String username;
    private String password;

    // Constructors, getters, setters...
    public User(String username, String password){
        this.password=password;
        this.username=username;
    }
    public String getname(){
        return username;
    }

    public String getpass(){
        return password;
    }
}
