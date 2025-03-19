package uk.ac.standrews.cs.host.cs3099user20.model;

import io.swagger.v3.oas.annotations.media.Schema;
import static uk.ac.standrews.cs.host.cs3099user20.service.InjectionPrevention.injectionPrevention;


public class Article extends SimpleArticle {
    @Schema(example = "20:a2f5689e-a5ae-4a9c-a7fd-6ab5b86ffd0f", description = "ID of article uploader")
    private String uploaderID;
    @Schema(example = "4", description = "Latest version for an article. Used for easy access to latest version.")
    private int topVersionID;
    @Schema(example = "{(0,1)}", description = "Set of all versions of an article.")
    private Version[] versions;
    private FileData files;

    public Article(int id, String name, String description, String[] authors, String license, String uploaderID, int topVersionID, Version[] versions, FileData fileData) {
        super(id, name, description, license, authors);
        this.uploaderID = injectionPrevention(uploaderID);
        this.topVersionID = topVersionID;
        this.versions = versions;
        this.files = fileData;
    }

    public Article(int id, String name, String description, String[] authors, String license, String uploaderID, int topVersionID, Version[] versions) {
        this(id, name, description, authors, license, uploaderID, topVersionID, versions, null);
    }

    public Article(SimpleArticle simpleArticle, String uploaderID, int topVersionID, Version[] versions) {
        super(simpleArticle.getId(), simpleArticle.getName(), simpleArticle.getDescription(), simpleArticle.getLicense(), simpleArticle.getAuthorsArray());
        this.uploaderID = uploaderID;
        this.topVersionID = topVersionID;
        this.versions = versions;
    }

    public Article () {
        super();
    }

    public String getUploaderID() {
        return uploaderID;
    }

    public void setUploaderID(String uploaderID) {
        this.uploaderID = injectionPrevention(uploaderID);
    }

    public int getTopVersionID() {
        return topVersionID;
    }

    public void setTopVersionID(int topVersionID) {
        this.topVersionID = topVersionID;
    }

    public Version[] getVersions() {
        return versions;
    }

    public void setVersions(Version[] versions) {
        this.versions = versions;
    }

    public FileData getFiles() {
        return files;
    }

    public void getFileData(FileData fileData) {
        this.files = fileData;
    }
}
