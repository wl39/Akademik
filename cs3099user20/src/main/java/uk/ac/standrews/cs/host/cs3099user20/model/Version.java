package uk.ac.standrews.cs.host.cs3099user20.model;

import io.swagger.v3.oas.annotations.media.Schema;

import static uk.ac.standrews.cs.host.cs3099user20.service.InjectionPrevention.injectionPrevention;

public class Version {

    @Schema(example = "38", description = "Version ID")
    private int versionID;
    @Schema(example = "2", description = "File version")
    private int version;
    @Schema(example = "1", description = "Subversion of a file")
    private int subVersion;
    @Schema(example = "2022-03-23 20:55:01", description = "Time version was made")
    private String time;

    public Version(int versionID, int version, int subVersion, String time) {
        this.versionID = versionID;
        this.version = version;
        this.subVersion = subVersion;
        this.time = injectionPrevention(time);
    }

    public Version(int versionID, int version, int subVersion) {
        this(versionID, version, subVersion, "");
    }

    public int getVersionID() {
        return versionID;
    }

    public void setVersionID(int versionID) {
        this.versionID = versionID;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getSubVersion() {
        return subVersion;
    }

    public void setSubVersion(int subVersion) {
        this.subVersion = subVersion;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = injectionPrevention(time);
    }
}
