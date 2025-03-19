package uk.ac.standrews.cs.host.cs3099user20.model;

import io.swagger.v3.oas.annotations.media.Schema;

import static uk.ac.standrews.cs.host.cs3099user20.service.InjectionPrevention.injectionPrevention;

public class Reviewer {

    @Schema(example = "20:b82cda19-e36b-4f63-82ea-e407b15c510a", description = "userID of reviewer")
    private String reviewerID;
    @Schema(example = "1", description = "Value of 1/0 describing whether character has/has not been approved as a reviewer")
    Boolean authorised;
    @Schema(example = "Percy fell in love with computers when he was 5 years old. He does 10 jumping jacks every day and then codes for 18 hours straight.", description = "Reviewer biography")
    String bio;
    @Schema(example = "University of Edinburgh", description = "Reviewer institution")
    String institution;
    @Schema(example = "Databases", description = "Areas of expertise for reviewers")
    String expertise;

    public Reviewer(String reviewerID, Boolean authorised, String bio, String institution, String expertise) {
        this.reviewerID = injectionPrevention(reviewerID);
        this.authorised = authorised;
        this.bio = injectionPrevention(bio);
        this.institution = injectionPrevention(institution);
        this.expertise = injectionPrevention(expertise);
    }

    public String getReviewerID() {
        return reviewerID;
    }

    public void setReviewerID(String reviewerID) {
        this.reviewerID = injectionPrevention(reviewerID);
    }

    public Boolean getAuthorised() {
        return authorised;
    }

    public void setAuthorised(Boolean authorised) {
        this.authorised = authorised;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = injectionPrevention(bio);
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = injectionPrevention(institution);
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = injectionPrevention(expertise);
    }
}
