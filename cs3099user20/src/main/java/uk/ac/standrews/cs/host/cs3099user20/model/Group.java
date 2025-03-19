package uk.ac.standrews.cs.host.cs3099user20.model;

import io.swagger.v3.oas.annotations.media.Schema;

import static uk.ac.standrews.cs.host.cs3099user20.service.InjectionPrevention.injectionPrevention;

public class Group {

    @Schema(example = "cs3099user09.host.cs.st-andrews.ac.uk/api/v1/supergroup", description = "Login URL for a supergroup")
    private String url;
    @Schema(example = "ThisIsASecurityToken", description = "Security token needed for supergroup login")
    private String token;

    public Group(String url, String token) {
        this.url = injectionPrevention(url);
        this.token = injectionPrevention(token);
    }

    public Group() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = injectionPrevention(url);
    }

    public void addAPI(String api) {url = url + api;}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = injectionPrevention(token);
    }
}
