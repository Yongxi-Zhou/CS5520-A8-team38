package edu.neu.team38.realtimeDB.models;


import androidx.annotation.NonNull;

import edu.neu.team38.utils.Utils;

public class Message {
    private String content;
    private String date;
    private String sentByUser;
    private String receivedByUser;

    public Message(){}

    public Message(String content, String sentByUser, String receivedByUser) {
        this.content = content;
        this.date = Utils.date();
        this.sentByUser = sentByUser;
        this.receivedByUser = receivedByUser;
    }

    public String getContent() {
        return content;
    }

    @NonNull
    @Override
    public String toString() {
        return "Message{" +
                "content='" + content + '\'' +
                ", date='" + date + '\'' +
                ", sentByUser='" + sentByUser + '\'' +
                ", receivedByUser='" + receivedByUser + '\'' +
                '}';
    }

    public String getDate() {
        return date;
    }

    public String getSentByUser() {
        return sentByUser;
    }

    public String getReceivedByUser() {
        return receivedByUser;
    }
}
