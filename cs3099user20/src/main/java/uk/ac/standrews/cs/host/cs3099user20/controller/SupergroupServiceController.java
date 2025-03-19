package uk.ac.standrews.cs.host.cs3099user20.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.ac.standrews.cs.host.cs3099user20.database.Database;
import uk.ac.standrews.cs.host.cs3099user20.model.*;

import java.sql.SQLException;
import java.util.Objects;
import static uk.ac.standrews.cs.host.cs3099user20.service.InjectionPrevention.injectionPrevention;


@RestController
public class SupergroupServiceController {

    private final String supergroupToken = "cs3099user20ThisIsSecretPlzDontShare:)";

    Database database = new Database();
    ActiveUserStore userStore = new ActiveUserStore();

    /**
     * Logs a user in from supergroup
     * @param token Security Token
     * @param login {email, password}
     * @return {userID: string, email: string}
     */
    @Operation(summary = "Logs a user in.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reviewer accepted.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "401", description = "Incorrect Login Credentials.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Incorrect API Token", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error.", content = @Content)})
    @RequestMapping(value = {"/api/v1/supergroup/login"}, method = RequestMethod.POST)
    public ResponseEntity<Object> loginSupergroupUser(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN", required = false) String token, @RequestBody Login login) {
        if (!Objects.equals(token, supergroupToken)) {
            return new ResponseEntity<>("Incorrect API token", HttpStatus.FORBIDDEN);
        } else {
            try {
                if (database.userExists(login.getEmail())) {
                    User user = database.login(login);
                    if (user != null) {
                        userStore.addUser(user.getUserID());
                        return new ResponseEntity<>(user, HttpStatus.OK);
                    }
                    else return new ResponseEntity<>("Incorrect Login Credentials", HttpStatus.UNAUTHORIZED);
                }
                return new ResponseEntity<>("User not Found", HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * Searches for articles from substring
     * @param token Security Token
     * @param submission {name, metadata, codeVersions, comments}
     * @return {userID: string, email: string}
     */
    @Operation(summary = "Takes an article submission and creates a new article.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article searched.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = JSONObject.class))),
            @ApiResponse(responseCode = "401", description = "Incorrect Login Credentials.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Incorrect API Token", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error.", content = @Content)})
    @RequestMapping(value = {"/api/v1/supergroup/submission"}, method = RequestMethod.POST)
    public ResponseEntity<Object> supergroupSubmission(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN", required = false) String token, @RequestBody Submission submission) {
        if(!Objects.equals(token, supergroupToken)) {
            return new ResponseEntity<>("Incorrect API token", HttpStatus.FORBIDDEN);
        } else {
            try {
                //boolean ret = database.migrateArticle(jo);
                return new ResponseEntity<>(database.migrateArticle(submission), HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * Return a user for supergroup
     * @param token Security Token
     * @param userID UUID
     * @return {userID:string, email:string}
     */
    @Operation(summary = "Returns a user for supergroup.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns a user for supergroup.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "Incorrect Login Credentials.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Incorrect API Token", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error.", content = @Content)})
    @RequestMapping(value = {"/api/v1/supergroup/user/{userID}"}, method = RequestMethod.GET)
    //TODO: Change hardcoded user email in groupify to actual email
    public ResponseEntity<Object> getSupergroupUser(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN", required = false) String token, @PathVariable String userID) {
        userID = injectionPrevention(userID);
        if(!Objects.equals(token, supergroupToken)) {
            return new ResponseEntity<>("Incorrect API token", HttpStatus.FORBIDDEN);
        } else {
            try {
                if (database.userExists(database.getEmail(userID))) {
                    return new ResponseEntity<>(database.getUser(userID), HttpStatus.OK);
                }
                return new ResponseEntity<>("User not Found", HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * Returns all users
     * @param token Security Token
     * @return [{userID:string, email:string}]
     */
    @Operation(summary = "Returns all supergroup users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users returned.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "401", description = "Incorrect Login Credentials.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Incorrect API Token", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error.", content = @Content)})
    @RequestMapping(value = {"/api/v1/supergroup/user"}, method = RequestMethod.GET)
    public ResponseEntity<Object> getSupergroupUsers(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN", required = false) String token) {
        if(!Objects.equals(token, supergroupToken)) {
            return new ResponseEntity<>("Incorrect API token", HttpStatus.FORBIDDEN);
        } else {
            try {
                return new ResponseEntity<>(database.getSupergroupUsers(), HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * Searches for articles from substring for supergroup
     * @param token Security Token
     * @param search {searchString}
     * @return {userID: string, email: string}
     */
    @Operation(summary = "Searches for articles from substring.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search success.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Article.class))),
            @ApiResponse(responseCode = "401", description = "Incorrect Login Credentials.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Incorrect API Token", content = @Content),
            @ApiResponse(responseCode = "403", description = "User is not a moderator", content = @Content),
            @ApiResponse(responseCode = "404", description = "Article not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error.", content = @Content)})
    @RequestMapping(value = {"/api/v1/supergroup/search"}, method = RequestMethod.POST)
    public ResponseEntity<Object> supergroupSearch(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN", required = false) String token, @RequestBody Search search) {
        System.out.println("Token: " + token);
        if(!Objects.equals(token, supergroupToken)) {
            return new ResponseEntity<>("Incorrect API token", HttpStatus.FORBIDDEN);
        } else {
            try {
                return new ResponseEntity<>(database.getArticles(4, search.getStartID(), search.getQuantity(), search.getUserID(), search.getSearchString()), HttpStatus.OK);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return new ResponseEntity<>("User is not a moderator", HttpStatus.FORBIDDEN);
            } catch (SQLException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Article Not Found", HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

}
