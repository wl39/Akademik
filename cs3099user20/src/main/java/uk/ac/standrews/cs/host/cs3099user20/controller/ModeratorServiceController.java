package uk.ac.standrews.cs.host.cs3099user20.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.ac.standrews.cs.host.cs3099user20.database.Database;
import uk.ac.standrews.cs.host.cs3099user20.model.ActiveUserStore;
import uk.ac.standrews.cs.host.cs3099user20.model.ReviewerDecision;
import uk.ac.standrews.cs.host.cs3099user20.model.User;

import java.nio.file.FileSystemException;
import static uk.ac.standrews.cs.host.cs3099user20.service.InjectionPrevention.injectionPrevention;
import java.sql.SQLException;
import java.util.Objects;


@RestController
public class ModeratorServiceController {

    Database database = new Database();
    ActiveUserStore userStore = new ActiveUserStore();
    final String modToken = "cs3099user20ThisIsSecreter";

    /**
     * accepts a reviewer
     * @param token Security Token
     * @param reviewerDecision {userID:string, reviewerID:string}
     * @param userID UUID
     * @return 200:User Created; 403: Invalid API Token, 403: Not a moderator, 409: User Already Exists
     */
    @Operation(summary = "Accept a reviewer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reviewer accepted.", content = @Content(mediaType = "application/json", schema = @Schema(type = "boolean"))),
            @ApiResponse(responseCode = "401", description = "Not Logged in.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Incorrect API Token", content = @Content),
            @ApiResponse(responseCode = "403", description = "User is not a moderator.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Reviewer Not Found.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error.", content = @Content)})
    @RequestMapping(value = "/api/v1/{userID}/moderator/acceptReviewer", method = RequestMethod.POST)
    public ResponseEntity<Object> acceptReviewer(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN", required = false) String token, @RequestBody ReviewerDecision reviewerDecision, @PathVariable String userID) {
        //403 Invalid API token
        if(!Objects.equals(token, modToken)) {
            return new ResponseEntity<>("Incorrect API Token", HttpStatus.FORBIDDEN);
        }
        else if (!userStore.userActive(userID)) {
            return new ResponseEntity<>("Not Logged in", HttpStatus.UNAUTHORIZED);
        }
        else {
            try {
                userStore.updateUserActivity(userID);
                // if not a moderator, return 403
                if (!database.isUserModerator(reviewerDecision.getUserID())) {
                    return new ResponseEntity<>("User is not a moderator", HttpStatus.FORBIDDEN);
                }
                if (database.acceptReviewer(reviewerDecision)) {
                    return new ResponseEntity<>(true, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Reviewer Not Found", HttpStatus.NOT_FOUND);
                }

            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * rejects a reviewer
     * @param token Security Token
     * @param reviewerDecision {userID:string, reviewerID:string}
     * @param userID UUID
     * @return 200:User Created; 403: Invalid API Token, 403: Not a moderator, 409: User Already Exists
     */
    @Operation(summary = "Rejects a Reviewer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reviewer rejected.", content = @Content(mediaType = "application/json", schema = @Schema(type = "boolean"))),
            @ApiResponse(responseCode = "401", description = "Not Logged in.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Incorrect API Token", content = @Content),
            @ApiResponse(responseCode = "403", description = "User is not a moderator.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Reviewer Not Found.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error.", content = @Content)})
    @RequestMapping(value = "/api/v1/{userID}/moderator/rejectReviewer", method = RequestMethod.POST)
    public ResponseEntity<Object> rejectReviewer(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN", required = false) String token, @RequestBody ReviewerDecision reviewerDecision, @PathVariable String userID) {
        userID = injectionPrevention(userID);
        if(!Objects.equals(token, modToken)) {
            return new ResponseEntity<>("Incorrect API Token", HttpStatus.FORBIDDEN);
        }
        else if (!userStore.userActive(userID)) {
            return new ResponseEntity<>("Not Logged in", HttpStatus.UNAUTHORIZED);
        }
        else {
            try {
                userStore.updateUserActivity(userID);
                // if not a moderator, return 403
                if (!database.isUserModerator(reviewerDecision.getUserID())) {
                    return new ResponseEntity<>("User is not a moderator", HttpStatus.FORBIDDEN);
                }
                if (database.rejectReviewer(reviewerDecision)) {
                    return new ResponseEntity<>(true, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Reviewer Not Found", HttpStatus.NOT_FOUND);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * Adds users as reviewers of an article
     * @param token Security Token
     * @param versionID Current VersionID
     * @param users {[userID],[userID],[userID]}
     * @param userID UUID
     * @return 200: true, 400: Bad Request - Not 3 users, 403: Invalid Token, 403: Not a moderator, 404: Article not Found, 404: User not Found, 500: Server Error
     */
    @Operation(summary = "Adds users as reviewers of an article.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reviewer added to article.", content = @Content(mediaType = "application/json", schema = @Schema(type = "boolean"))),
            @ApiResponse(responseCode = "401", description = "Not Logged in.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Incorrect API Token", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request: Please only send us three(3) users to add for review.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error.", content = @Content)})
    @RequestMapping(value = "/api/v1/{userID}/moderator/reviewers/add/{versionID}", method = RequestMethod.POST)
    public ResponseEntity<Object> addReviewer(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN") String token, @RequestBody User[] users, @PathVariable int versionID, @PathVariable String userID) {
        userID = injectionPrevention(userID);
        if(!Objects.equals(token, modToken)) {
            return new ResponseEntity<>("Incorrect API token", HttpStatus.FORBIDDEN);
        } else if (users.length != 3) {
            // Check number of users for review
            return new ResponseEntity<>("Bad Request: Please only send us three(3) users to add for review", HttpStatus.BAD_REQUEST);
        }
        else if (!userStore.userActive(userID)) {
            return new ResponseEntity<>("Not Logged in", HttpStatus.UNAUTHORIZED);
        }
        else {
            try {
                userStore.updateUserActivity(userID);
                if (database.addReviewers(versionID, users)) {
                    return new ResponseEntity<>(true, HttpStatus.OK);
                } else {
                    throw new Exception();
                }
            }catch (FileSystemException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Article files could not be removed", HttpStatus.BAD_REQUEST);
            } catch (SQLException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * Reject an article
     * @param token Security Token
     * @param versionID version ID
     * @param  userID UUID
     * @return 200: true, 400: Bad Request - Not 3 users, 403: Invalid Token, 403: Not a moderator, 404: Article not Found, 404: User not Found, 500: Server Error
     */
    @Operation(summary = "Reject an article.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article is rejected.", content = @Content(mediaType = "application/json", schema = @Schema(type = "boolean"))),
            @ApiResponse(responseCode = "401", description = "Not Logged in.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Incorrect API Token", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error.", content = @Content)})
    @RequestMapping(value = "/api/v1/{userID}/moderator/rejectArticle/{versionID}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> rejectArticle(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN") String token, @PathVariable int versionID, @PathVariable String userID) {
        userID = injectionPrevention(userID);
        if(!Objects.equals(token, modToken)) {
            return new ResponseEntity<>("Incorrect API token", HttpStatus.FORBIDDEN);
        } else if (!userStore.userActive(userID)) {
            return new ResponseEntity<>("Not Logged in", HttpStatus.UNAUTHORIZED);
        }
        else {
            try {
                userStore.updateUserActivity(userID);
                if (database.rejectArticle(versionID)) {
                    return new ResponseEntity<>(true, HttpStatus.OK);
                } else {
                    throw new Exception();
                }
            }catch (FileSystemException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Article files could not be removed", HttpStatus.BAD_REQUEST);
            } catch (SQLException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * Gets all reviewers
     * @param token Security Token
     * @param userID UUID
     * @return 200: Reviewers Obtained; 401: Not logged in; 403: Incorrect API Token; 500: Server Error
     */
    @Operation(summary = "Gets all reviewers.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reviewers obtained.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "401", description = "Not Logged in.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Incorrect API Token", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error.", content = @Content)})
    @RequestMapping(value = "/api/v1/{userID}/moderator/reviewers", method = RequestMethod.GET)
    public ResponseEntity<Object> getReviewers(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN", required = false) String token, @PathVariable String userID) {
        userID = injectionPrevention(userID);
        if(!Objects.equals(token, modToken)) {
            return new ResponseEntity<>("Incorrect API Token", HttpStatus.FORBIDDEN);
        }
        else if (!userStore.userActive(userID)) {
            return new ResponseEntity<>("Not Logged in", HttpStatus.UNAUTHORIZED);
        }
        else {
            try {
                userStore.updateUserActivity(userID);
                return new ResponseEntity<>(database.getReviewers(), HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * Gets unauthorised reviewers
     * @param token Security Token
     * @param userID UUID
     * @return 200: Unauthorised reviewers obtained; 401: Not logged in; 403: Incorrect API Token; 500: Server Error
     */
    @Operation(summary = "Gets unauthorised reviewers.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Unauthorised Reviewers obtained.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "401", description = "Not Logged in.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Incorrect API Token", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error.", content = @Content)})
    @RequestMapping(value = "/api/v1/{userID}/moderator/unauthorised/reviewers", method = RequestMethod.GET)
    public ResponseEntity<Object> getUnauthorisedReviewers(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN", required = false) String token, @PathVariable String userID) {
        userID = injectionPrevention(userID);
        if(!Objects.equals(token, modToken)) {
            return new ResponseEntity<>("Incorrect API Token", HttpStatus.FORBIDDEN);
        }
        else if (!userStore.userActive(userID)) {
            return new ResponseEntity<>("Not Logged in", HttpStatus.UNAUTHORIZED);
        }
        else {
            try {
                userStore.updateUserActivity(userID);
                return new ResponseEntity<>(database.getUnauthorisedReviewers(), HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
