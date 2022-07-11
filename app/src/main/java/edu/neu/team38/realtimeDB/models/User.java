package edu.neu.team38.realtimeDB.models;

import androidx.annotation.NonNull;

public class User {

    private String username;
    public User() {}

    public User(String name) {
        username = name;
    }

    public String getUsername() {
        return username;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }
}
