package uk.ac.standrews.cs.host.cs3099user20.model;

import io.swagger.v3.oas.annotations.media.Schema;

public class Role {

    @Schema(example = "1", description = "User role")
    private int role;

    public Role(String roleDescription) {
        switch (roleDescription) {
            case("moderator") : role = 1; break;
            case("reviewer") : role = 3; break;
            case("general") : role = 4; break;
        }
    }

    public Role() {
    }

    public int getRole() {
        return role;
    }

    public void setModerator() {
        this.role = 1;
    }

    public void setUploader() { this.role = 2; }

    public void setReviewer() {
        this.role = 3;
    }

    public void setGeneral() {
        this.role = 4;
    }
}
