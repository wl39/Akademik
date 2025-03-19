package uk.ac.standrews.cs.host.cs3099user20.model;

import io.swagger.v3.oas.annotations.media.Schema;

import static uk.ac.standrews.cs.host.cs3099user20.service.InjectionPrevention.injectionPrevention;

public class Login {
    @Schema(example = "test@test.com", description = "User email")
    private String email;
    @Schema(example = "test", description = "User password")
    private String password;
    @Schema(example = "20", description = "Supergroup number")
    private String group;

    public Login(String email, String password, String group) {
        this.email = injectionPrevention(email);
        this.password = injectionPrevention(password);
        this.group = group;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = injectionPrevention(email);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = injectionPrevention(password);
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}