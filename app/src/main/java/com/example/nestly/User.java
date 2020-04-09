package com.example.nestly;

public class User {

    private static long idTracker = 0;
    public long id;
    public String username;
    public String password;
    public String [] habits_answers;
    public String [] situations_answers;
    public String [] long_answers;

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

    public void setHabits_answers(String[] habits_answers) { this.habits_answers = habits_answers; }

    public void setSituations_answers(String[] situations_answers) { this.situations_answers = situations_answers; }

    public void setLong_answers(String[] long_answers) { this.long_answers = long_answers; }

    public String[] getHabits_answers() {
        return habits_answers;
    }

    public String[] getSituations_answers() {
        return situations_answers;
    }

    public String[] getLong_answers() {
        return long_answers;
    }
}
