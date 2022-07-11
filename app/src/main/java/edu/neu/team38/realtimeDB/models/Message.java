package edu.neu.team38.realtimeDB.models;


import edu.neu.team38.utils.Utils;

public class Message {
    private String content;
    private String date;
    private User sentByUser;
    private User receivedByUser;

    public Message(){}

    public Message(String content, User sentByUser, User receivedByUser) {
        this.content = content;
        this.date = Utils.date();
        this.sentByUser = sentByUser;
        this.receivedByUser = receivedByUser;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public User getSentByUser() {
        return sentByUser;
    }

    public User getReceivedByUser() {
        return receivedByUser;
    }
}
