package uk.ac.standrews.cs.host.cs3099user20.model;

import io.swagger.v3.oas.annotations.media.Schema;
import static uk.ac.standrews.cs.host.cs3099user20.service.InjectionPrevention.injectionPrevention;

public class Search extends Pagination {

    @Schema(example = "Article 5", description = "String being searched")
    private String searchString;
    @Schema(example = "[\"20\", \"05\"]", description = "Groups to be searched")
    private String[] groupNumbers;

    public Search(String searchString, String[] groupNumbers, int startID, int quantity) {
        super(startID, quantity);
        this.searchString = injectionPrevention(searchString);
        this.groupNumbers = groupNumbers;
    }

    public Search(String searchString, String[] groupNumbers) {
        this(searchString, groupNumbers, 1, 4096);
    }

    public Search() {}

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = injectionPrevention(searchString);
    }

    public String[] getGroupNumbers() {
        return groupNumbers;
    }

    public void setGroupNumbers(String[] groupNumbers) {
        this.groupNumbers = groupNumbers;
    }
}
