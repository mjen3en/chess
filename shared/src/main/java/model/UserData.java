package model;

public class UserData {

    public UserData(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }


    public String username;

    public String password;

    public String email;
}
