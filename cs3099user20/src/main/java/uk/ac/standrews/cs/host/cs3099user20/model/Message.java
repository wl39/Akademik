package uk.ac.standrews.cs.host.cs3099user20.model;

import io.swagger.v3.oas.annotations.media.Schema;

import static uk.ac.standrews.cs.host.cs3099user20.service.InjectionPrevention.injectionPrevention;

public class Message {
    @Schema(example = "9", description = "ID of message")
    private int messageID;
    @Schema(example = "20:29f472f2-bcfd-4564-bd7a-92e50983ccbc", description = "User ID of message author")
    private String userID;
    @Schema(example = "Nice spaces", description ="Comment text")
    private String text;
    @Schema(example = "4:40:19", description = "Time when comment/message was written")
    private String timeStamp;
    private String email;

    public Message(String userID,String text) {
        this(0, userID, null, text, null);
    }

    public Message(String userID, String email, String text) {
        this(0, userID, email, text, null);
    }

    public Message(int id, String userID, String email, String text, String timestamp) {
        this.messageID = id;
        this.userID = injectionPrevention(userID);
        this.text = injectionPrevention(text);
        this.timeStamp = injectionPrevention(timestamp);
        this.email = email;
    }

    public Message() {}

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = injectionPrevention(userID);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = injectionPrevention(text);
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = injectionPrevention(timeStamp);
    }
}
