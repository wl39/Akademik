package uk.ac.standrews.cs.host.cs3099user20.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import static uk.ac.standrews.cs.host.cs3099user20.service.InjectionPrevention.injectionPrevention;

public class MetaData {

    private String creationDate;
    @JsonProperty("abstract")
    private String description;
    private String license;
    private String[] categories;
    private Author[] authors;

    public MetaData(String creationDate, String description, String license, String[] categories, Author[] authors) {
        this.creationDate = injectionPrevention(creationDate);
        this.description = injectionPrevention(description);
        this.license = injectionPrevention(license);
        this.categories = new String[]{"Group 20"};
        this.authors = authors;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = injectionPrevention(creationDate);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = injectionPrevention(description);
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = injectionPrevention(license);
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public Author[] getAuthors() {
        return authors;
    }

    public void setAuthors(Author[] authors) {
        this.authors = authors;
    }
}
