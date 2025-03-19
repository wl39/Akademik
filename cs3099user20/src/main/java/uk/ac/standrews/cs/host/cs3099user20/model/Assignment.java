package uk.ac.standrews.cs.host.cs3099user20.model;
import static uk.ac.standrews.cs.host.cs3099user20.service.InjectionPrevention.injectionPrevention;

import io.swagger.v3.oas.annotations.media.Schema;

public class Assignment {
    @Schema(example = "20:d5e9e911-a1fd-4620-b3db-12e7f76521bd", description = "UserID of voter")
    private String userID;
    @Schema(example = "5", description = "Article ID Voted on")
    private int articleID;
    @Schema(example = "true", description = "Vote decided")
    private Boolean voted;

    public Assignment(String userID, int articleID, Boolean voted) {
        this.userID = injectionPrevention(userID);
        this.articleID = articleID;
        this.voted = voted;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getArticleID() {
        return articleID;
    }

    public void setArticleID(int articleID) {
        this.articleID = articleID;
    }

    public Boolean getVoted() {
        return voted;
    }

    public void setVoted(Boolean voted) {
        this.voted = voted;
    }
}
