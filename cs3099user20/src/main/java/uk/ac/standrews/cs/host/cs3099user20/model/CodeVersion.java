package uk.ac.standrews.cs.host.cs3099user20.model;
import static uk.ac.standrews.cs.host.cs3099user20.service.InjectionPrevention.injectionPrevention;

public class CodeVersion {

    private final String description = "";
    private String timestamp;
    private FileData[] files;

    public CodeVersion(String timestamp, FileData[] files) {
        this.timestamp = injectionPrevention(timestamp);
        this.files = files;
    }

    public String getDescription() {
        return description;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public FileData[] getFiles() {
        return files;
    }

    public void setFiles(FileData[] files) {
        this.files = files;
    }
}
