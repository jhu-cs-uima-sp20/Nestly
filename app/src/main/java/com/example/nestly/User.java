package com.example.nestly;

import java.util.List;

class User {
    final private String username;
    final private String password;
    private String year;
    private String major;
    private String gender;
    private List<String> habits_answers;
    private List<String> situations_answers;
    private List<String> long_answers;
    private List<String> blocked;
    private boolean hidden;

    private String name;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        hidden=false;
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

    public void setYear(String year) { this.year=year; }

    public String getYear() { return year; }

    public String getMajor() { return major; }

    public String getGender() { return gender; }

    public void setMajor(String major) { this.major=major; }

    public void setGender(String gender) { this.gender=gender; }

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

    public boolean isHidden() {return hidden; }

    public void setHidden() { hidden = true; }
}
