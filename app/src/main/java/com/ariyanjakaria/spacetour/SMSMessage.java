package com.ariyanjakaria.spacetour;

public class SMSMessage {
    private String sender;
    private String message;

    // Default constructor (required by Firestore)
    public SMSMessage() {
        // Default constructor is needed for Firestore to deserialize the data.
    }

    public SMSMessage(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

