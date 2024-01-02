// User.java
package com.example.myb;

public class User {
    private String username;
    private String password;

    private boolean rememberMe; // Add this line

    public User(String username, String password, boolean rememberMe) {
        this.username = username;
        this.password = password;
        this.rememberMe=rememberMe;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public boolean getRememberMe() {
        //return rememberMe==true;
        return rememberMe;
    }

    @Override
        public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", rememberMe=" + rememberMe +
                '}';
    }

}
