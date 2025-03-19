package uk.ac.standrews.cs.host.cs3099user20.model;
import static uk.ac.standrews.cs.host.cs3099user20.service.InjectionPrevention.injectionPrevention;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public class User {

    @Schema(example = "20:45067c5c-c386-47d8-a381-0822700b9642", description = "UserID")
    @JsonProperty("id")
    private String userID;
    @Schema(example = "MaryWhinter1419@corti.com", description = "User email")
    private String email;

    public User(String userID, String email) {
        this.userID = injectionPrevention(userID);
        this.email = injectionPrevention(email);
    }

    public User() {}

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = injectionPrevention(userID);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = injectionPrevention(email);
    }
}

