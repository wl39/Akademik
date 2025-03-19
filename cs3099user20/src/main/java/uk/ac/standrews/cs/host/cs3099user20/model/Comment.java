package uk.ac.standrews.cs.host.cs3099user20.model;
import static uk.ac.standrews.cs.host.cs3099user20.service.InjectionPrevention.injectionPrevention;
import io.swagger.v3.oas.annotations.media.Schema;

public class Comment extends Message {

    @Schema(example = "18", description = "Version ID")
    private int versionID;
    @Schema(example = "3", description = "Line where inline comment begins")
    private int lineStart;
    @Schema(example = "4", description = "Line where inline comment ends")
    private int lineEnd;
    @Schema(example = "Animal.java", description = "Filepath of code where comment is written. Identifies where comment is.")
    private String filename;

    public Comment(int messageID, int versionID, String filename, String authorID, String authorEmail, String text, int lineStart, int lineEnd, String timestamp) {
        super(messageID, authorID, authorEmail, text, timestamp);
        this.versionID = versionID;
        this.filename = injectionPrevention(filename);
        this.lineStart = lineStart;
        this.lineEnd = lineEnd;
    }

    public Comment(int messageID, int versionID, String filename, String authorID, String text, int lineStart, int lineEnd, String timestamp) {
        this(messageID, versionID,filename, authorID, null, text, lineStart, lineEnd, timestamp);
    }

    public Comment(int versionID, String filename, String authorID, String text, int lineStart, int lineEnd) {
        this(0, versionID, filename, authorID, null, text, lineStart, lineEnd, null);
    }

    public Comment(Comment comment) {
        this(comment.getMessageID(), comment.getVersionID(), comment.getFilename(), comment.getUserID(), null, comment.getText(), comment.getLineStart(), comment.getLineEnd(), comment.getTimeStamp());
    }

    public Comment() {
        this(0,0,"","","",0,0, "");
    }

    public Comment(int versionID, String filename) {
        this(versionID, filename, null, null, -1, -1);
    }

    public int getVersionID() {
        return versionID;
    }

    public void setVersionID(int versionID) {
        this.versionID = versionID;
    }

    public int getLineStart() {
        return lineStart;
    }

    public void setLineStart(int lineStart) {
        this.lineStart = lineStart;
    }

    public int getLineEnd() {
        return lineEnd;
    }

    public void setLineEnd(int lineEnd) {
        this.lineEnd = lineEnd;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = injectionPrevention(filename);
    }
}
