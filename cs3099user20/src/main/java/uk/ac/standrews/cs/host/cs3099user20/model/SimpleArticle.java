package uk.ac.standrews.cs.host.cs3099user20.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import static uk.ac.standrews.cs.host.cs3099user20.service.InjectionPrevention.injectionPrevention;

public class SimpleArticle {

    // base url for articles page
    //TODO: Change to hosted server
    private String baseURL = "https://cs3099user20.host.cs.st-andrews.ac.uk/articles/";
    @Schema(example = "12", description = "Article ID")
    private int id;
    @Schema(example = "Symmetric Locality for Functions", description = "Article Title")
    @JsonProperty("title")
    private String name;
    @Schema(example = "Recently, there has been much interest in the characterization of commutative scalars", description = "Description of article contents")
    @JsonProperty("abstract")
    private String description;
    @Schema(example = "YALE", description = "Article publishing license")
    private String license;
    @Schema(example = "{\"Chadwick Weatcroft\"", description = "Array of article authors")
    private String[] authorsArray;
    @JsonProperty("articleURL")
    private String url;
    private String authors;

    public SimpleArticle(String description, String name, String url, String authors) {
        if(url.contains(baseURL)) {
            id = Integer.parseInt(url.replace(baseURL, ""));
        }
        this.description = injectionPrevention(description);
        this.name = injectionPrevention(name);
        this.url = injectionPrevention(url);
        this.authors = injectionPrevention(authors);
        this.authorsArray = authors.split(",");
    }

    public SimpleArticle(int id, String name, String description, String license, String[] authorsArray) {
        this.id = id;
        this.name = injectionPrevention(name);
        this.description = injectionPrevention(description);
        this.authorsArray = authorsArray;
        this.license = injectionPrevention(license);
        this.url = baseURL + id;
        this.authors = String.join(",", authorsArray);
    }

    public SimpleArticle() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = injectionPrevention(name);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = injectionPrevention(description);
    }

    public String getLicense() {return license;}

    public void setLicense(String license) {this.license = injectionPrevention(license);}

    public String[] getAuthorsArray() {
        return authorsArray;
    }

    public void setAuthorsArray(String[] authorsArray) {
        this.authorsArray = authorsArray;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = injectionPrevention(url);
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = injectionPrevention(authors);
    }
}
