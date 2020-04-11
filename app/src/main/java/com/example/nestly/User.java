package com.example.nestly;

import java.util.List;

public class User {
    public String username;
    public String password;
    public List<String> habits_answers;
    public List<String> situations_answers;
    public List<String> long_answers;

    public String name;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
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

    public void setHabits_answers(List<String> habits_answers) { this.habits_answers = habits_answers; }

    public void setSituations_answers(List<String> situations_answers) { this.situations_answers = situations_answers; }

    public void setLong_answers(List<String> long_answers) { this.long_answers = long_answers; }

    public List<String> getHabits_answers() {
        return habits_answers;
    }

    public List<String> getSituations_answers() {
        return situations_answers;
    }

    public List<String> getLong_answers() {
        return long_answers;
    }
}
