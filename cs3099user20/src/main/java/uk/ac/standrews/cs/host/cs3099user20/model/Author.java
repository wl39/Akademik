package uk.ac.standrews.cs.host.cs3099user20.model;
import static uk.ac.standrews.cs.host.cs3099user20.service.InjectionPrevention.injectionPrevention;

public class Author {


    private String userId;
    private String journal;

    public Author(String userId, String journal) {
        this.userId = injectionPrevention(userId);
        this.journal = injectionPrevention(journal);
    }

    public Author(String userId) {
        this(userId, "https://cs3099user20.host.cs.st-andrews.ac.uk");
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = injectionPrevention(userId);
    }

    public String getJournal() {
        return journal;
    }
}
