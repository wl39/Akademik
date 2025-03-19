package uk.ac.standrews.cs.host.cs3099user20.model;

public class SuperGroupCommentComment {

    private String timestamp;
    private String userID;
    private String journal;
    private String text;
    private SuperGroupCommentComment[] children;

    public SuperGroupCommentComment(String timestamp, String userID, String journal, int articleID) {
        this.timestamp = timestamp;
        this.userID = userID;
        this.journal = journal;
        this.text = "This is an imported article. View this version from: https://cs3099user20.host.cs.st-andrews.ac.uk/query/articles/get/" + articleID;
        this.children = new SuperGroupCommentComment[0];
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public SuperGroupCommentComment[] getChildren() {
        return children;
    }

    public void setChildren(SuperGroupCommentComment[] children) {
        this.children = children;
    }
}


