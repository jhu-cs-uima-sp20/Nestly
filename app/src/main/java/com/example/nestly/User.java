package com.example.nestly;

public class User {

    private static long idTracker = 0;
    public long id;
    public String username;
    public String password;

    public String name;

    public User(String username, String password) {
        this.username = username;
        this.id = idTracker;
        idTracker++;
    }

    public long getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() { return this.password; }

    public void setName(String name) {
        this.name = name;
    }
}
