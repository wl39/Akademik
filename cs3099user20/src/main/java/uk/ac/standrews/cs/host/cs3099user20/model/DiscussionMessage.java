package uk.ac.standrews.cs.host.cs3099user20.model;

import io.swagger.v3.oas.annotations.media.Schema;

public class DiscussionMessage extends Message{

    @Schema(example = "3", description = "ID of parent discussion message. Equal to message ID if not a threaded comment.")
    private int parentID;
    @Schema(example = "18", description = "Version ID of article where discussion message is written.")
    private int versionID;


    public DiscussionMessage(int messageID, int parentID, int versionID, String userID, String email, String text, String timestamp) {
        super(messageID, userID, email, text, timestamp);
        this.parentID = parentID;
        this.versionID = versionID;
    }

    public DiscussionMessage(int messageID, int parentID, int versionID, String userID, String text, String timestamp) {
        this(messageID, parentID, versionID, userID, null, text, timestamp);
    }

    public DiscussionMessage(int parentID, int versionID, String authorID, String text) {
        this(0, parentID, versionID, authorID, null, text, null);
    }

    public DiscussionMessage(int versionID, String authorID, String text) {
        this(0, 0, versionID, authorID, null, text, null);
    }

    public DiscussionMessage(String authorID, String text) {
        this(0, 0, 0, authorID, null, text, null);
    }

    public DiscussionMessage(DiscussionMessage dM) {
        this(dM.getMessageID(), dM.getParentID(), dM.getVersionID(), dM.getUserID(), dM.getEmail(), dM.getText(), dM.getTimeStamp());
    }

    public DiscussionMessage() {};

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public int getVersionID() {
        return versionID;
    }

    public void setVersionID(int versionID) {
        this.versionID = versionID;
    }

}
