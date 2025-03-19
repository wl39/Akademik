package uk.ac.standrews.cs.host.cs3099user20.model;

public class SuperGroupComment {

    private String scope = "submission";
    private SuperGroupCommentComment comment;

    public SuperGroupComment(String timeStamp, String userID, int articleID) {
        String journal = "https://cs3099user20.host.cs.st-andrews.ac.uk";
        comment = new SuperGroupCommentComment(timeStamp,userID, journal, articleID);
    }

    public SuperGroupComment() {}

    public String getScope() {
        return scope;
    }

    public SuperGroupCommentComment getComment() {
        return comment;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setComment(SuperGroupCommentComment comment) {
        this.comment = comment;
    }
}
