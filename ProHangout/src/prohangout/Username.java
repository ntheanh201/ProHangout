package prohangout;

public class Username {
    private static String username;
    private static String password;
    private static int id;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password) {
        Username.password = password;
    }

    public String getPassword() {
        return password;
    }

    public int getId(){
        return id;
    }
}
