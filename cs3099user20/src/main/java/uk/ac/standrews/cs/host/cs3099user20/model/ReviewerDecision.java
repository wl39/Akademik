package uk.ac.standrews.cs.host.cs3099user20.model;

import io.swagger.v3.oas.annotations.media.Schema;

import static uk.ac.standrews.cs.host.cs3099user20.service.InjectionPrevention.injectionPrevention;

public class ReviewerDecision {

    @Schema(example = "80493f58-2ffa-49c8-a2e9-98343ac656d3", description = "Moderator ID accepting/rejecting a reviewer")
    private String userID;
    @Schema(example = "89dfd717-e315-4f6f-a704-a3ee9d09d876", description = "Reviewer ID to be accepted/rejected")
    private String reviewerID;

    public ReviewerDecision(String userID, String reviewerID) {
        this.userID = injectionPrevention(userID);
        this.reviewerID = injectionPrevention(reviewerID);
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = injectionPrevention(userID);
    }

    public String getReviewerID() {
        return reviewerID;
    }

    public void setReviewerID(String reviewerID) {
        this.reviewerID = injectionPrevention(reviewerID);
    }
}
