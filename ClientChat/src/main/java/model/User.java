package model;

import java.io.Serializable;

public class User implements Serializable {
    public static final long serialVersionUID = 1L;
    private static String username;
    private static String password;
    private static int id;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password) {
        User.password = password;
    }

    public String getPassword() {
        return password;
    }

    public int getId(){
        return id;
    }
}
