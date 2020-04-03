package com.example.nestly;

public class User {

    private static long idTracker = 0;

    public String username;
    public long id;

    public User(String name) {
        this.username = name;
        this.id = idTracker;
        idTracker++;
    }

    public String getUsername() {
        return this.username;
    }

    public long getId() {
        return this.id;
    }
}
