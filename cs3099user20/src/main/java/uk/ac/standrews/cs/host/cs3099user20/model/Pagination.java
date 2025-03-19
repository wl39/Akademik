package uk.ac.standrews.cs.host.cs3099user20.model;

import io.swagger.v3.oas.annotations.media.Schema;

import static uk.ac.standrews.cs.host.cs3099user20.service.InjectionPrevention.injectionPrevention;

public class Pagination {
    @Schema(example = "b6256a1d-a492-4861-9279-b404cde3a2d9", description = "User ID")
    private String userID;
    @Schema(example = "1 = moderator, 2 = uploader, 3 = reviewer, 4 = general user", description = "User role")
    private int role = 4;
    @Schema(example = "4", description = "Starting ID of page location")
    private int startID;
    @Schema(example = "4096", description = "Amount of pages to display")
    private int quantity = 4096;

    public Pagination(String userID, int role, int startID, int quantity) {
        this.userID = injectionPrevention(userID);
        this.role = role;
        this.startID = startID;
        this.quantity = quantity;
    }

    public Pagination(int startID, int quantity) {
        this.startID = startID;
        this.quantity = quantity;
    }

    public Pagination() {}

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = injectionPrevention(userID);
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getStartID() {
        return startID;
    }

    public void setStartID(int startID) {
        this.startID = startID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
