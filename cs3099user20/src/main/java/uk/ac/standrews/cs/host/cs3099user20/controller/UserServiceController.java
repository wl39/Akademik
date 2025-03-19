package uk.ac.standrews.cs.host.cs3099user20.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.ac.standrews.cs.host.cs3099user20.database.Database;
import uk.ac.standrews.cs.host.cs3099user20.model.*;

import java.sql.SQLException;
import java.util.Objects;

import static uk.ac.standrews.cs.host.cs3099user20.model.ActiveUserStore.*;
import static uk.ac.standrews.cs.host.cs3099user20.service.InjectionPrevention.injectionPrevention;

/**
 * API Controller for all user requests.
 */
@RestController
public class UserServiceController {
    //Create database object
    Database database = new Database();

    //Strings for needed tokens + request value
    final String generalToken = "cs3099user20ThisIsSecret";
    ActiveUserStore userStore = new ActiveUserStore();

    /**
     * Registers a user
     * @param login: {email:string, password:string}
     * @return 200:User Created, 403: Incorrect API Token, 409: User Already Exists, 500: Server Error
     */
    @Operation(summary = "Registers a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Created", content = @Content(mediaType = "application/json", schema = @Schema(type = "boolean"))),
            @ApiResponse(responseCode = "403", description = "Incorrect API Token", content = @Content),
            @ApiResponse(responseCode = "409", description = "User Already Exists", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)})
    @RequestMapping(value = "/api/v1/users/register", method = RequestMethod.POST)
    public ResponseEntity<Object> registerUser(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN", required = false) String token, @RequestBody Login login) {
        if(!Objects.equals(token, generalToken)) {
            return new ResponseEntity<>("Incorrect API Token", HttpStatus.FORBIDDEN);
        }
        try {
            if (database.userExists(login.getEmail())) {
                return new ResponseEntity<>("User Already Exists", HttpStatus.CONFLICT);
            }
            database.registerUser(login);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Insufficient User Information", HttpStatus.CONFLICT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Logs in a user from our implementation.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully logged in", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "403", description = "Incorrect API Token", content = @Content),
            @ApiResponse(responseCode = "403", description = "Email already in use", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)})
    @RequestMapping(value = "/api/v1/users/login", method = RequestMethod.POST)
    public ResponseEntity<Object> loginUser(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN", required = false) String token, @RequestBody Login login) {
        if (!Objects.equals(token, generalToken)) {
            return new ResponseEntity<>("Incorrect API Token", HttpStatus.FORBIDDEN);
        }
        try {
            // Get supergroup information
            Group group = database.getServer(Integer.parseInt(login.getGroup()));
            group.addAPI("api/v1/supergroup/login");

            // Handle errors
            if (group.getUrl() == null) {
                return new ResponseEntity<>("Group not Found", HttpStatus.NOT_FOUND);
            }
            if (group.getToken() == null) {
                return new ResponseEntity<>("Incorrect Supergroup Token", HttpStatus.CONFLICT);
            }

            // Login
            User ret = database.loginToURL(group, login);

            ret = database.groupify(Integer.parseInt(login.getGroup()), ret);
            userStore.addUser(ret.getUserID());
            return new ResponseEntity<>(ret, HttpStatus.OK);
            //return new ResponseEntity<>("Incorrect Login Credentials", HttpStatus.UNAUTHORIZED);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Incorrect Login Credentials", HttpStatus.UNAUTHORIZED);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Insufficient User Information", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Logs out in a user
     * @param user {ID:string}
     * @param token Security Token
     * @return 200:{userID:string}, 403: incorrect Token, 404: User not found, 409: User Not Logged in, 500: Server Error.
     */
    @Operation(summary = "Logs out a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Logged Out", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "403", description = "Incorrect API Token", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not Found", content = @Content),
            @ApiResponse(responseCode = "409", description = "User not Logged in", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)})
    @RequestMapping(value = "/api/v1/users/logout", method = RequestMethod.POST)
    public ResponseEntity<Object> logoutUser(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN") String token, @RequestBody User user) {
        if(!Objects.equals(token, generalToken)) {
            return new ResponseEntity<>("Incorrect API token", HttpStatus.FORBIDDEN);
        } else {
            try {
                if (!database.userExists(user.getEmail())) {return new ResponseEntity<>("User not Found", HttpStatus.NOT_FOUND);}
                else {
                    userStore.removeUser(user.getUserID());
                    return new ResponseEntity<>(user, HttpStatus.OK);}
                } catch (SQLException e) {
                e.printStackTrace();
                return new ResponseEntity<>("User not Found", HttpStatus.NOT_FOUND);
            } catch (UnsupportedOperationException e) {
                e.printStackTrace();
                return new ResponseEntity<>("User not logged in", HttpStatus.UNAUTHORIZED);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * Checks if user exists
     * @param user {email:string}
     * @param token Security token
     * @return 200: true, 403: incorrect Token, 404: User Not Found, 500: Sever Error.
     */
    @Operation(summary = "Checks if user exists.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Exists.", content = @Content(mediaType = "application/json", schema = @Schema(type = "boolean"))),
            @ApiResponse(responseCode = "403", description = "Incorrect API Token", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Insufficient User Information", content = @Content)})
    @RequestMapping(value = "/api/v1/users/exists", method = RequestMethod.POST)
    public ResponseEntity<Object> userExists(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN") String token, @RequestBody User user) {
        if(!Objects.equals(token, generalToken)) {
            return new ResponseEntity<>("Incorrect API token", HttpStatus.FORBIDDEN);
        } else {
            try {
                if (database.userExists(user.getEmail())) return new ResponseEntity<>(true, HttpStatus.OK);
                else return new ResponseEntity<>("User Not Found.", HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Insufficient User Information", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * Applies a user for a reviewer role
     * @param reviewer {bio:string, institution:string, field of expertise:string}
     * @return 200: true, 403: incorrect Token, 403: User Has Already Applied for/is already a Reviewer, 404: User Not Found.
     */
    @Operation(summary = "Applies a user for a reviewer role for moderator to approve.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User applied for reviewer role", content = @Content(mediaType = "application/json", schema = @Schema(type = "boolean"))),
            @ApiResponse(responseCode = "401", description = "Not Logged in.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Incorrect API Token", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not Found", content = @Content),
            @ApiResponse(responseCode = "409", description = "User Has already Applied for/is already a Reviewer", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)})
    @RequestMapping(value = "/api/v1/{userID}/users/apply/reviewer", method = RequestMethod.POST)
    public ResponseEntity<Object> reviewerApply(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN") String token, @PathVariable String userID,  @RequestBody Reviewer reviewer) {
        userID = injectionPrevention(userID);
        if(!Objects.equals(token, generalToken)) {
            return new ResponseEntity<>("Incorrect API token", HttpStatus.FORBIDDEN);
        } else {
            try {
                if (database.userExists(database.getEmail(reviewer.getReviewerID()))) {
                    userStore.updateUserActivity(userID);
                    if (database.reviewerApply(reviewer)) {
                        return new ResponseEntity<>(true, HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>("User Has already Applied for/is already a Reviewer.", HttpStatus.CONFLICT);
                    }
                }
                return new ResponseEntity<>("User not Found.", HttpStatus.NOT_FOUND);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Empty input", HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * Get email from user ID
     * @param userID String ex: 20:45113161-8c86-4181-b8df-d83828a72856
     * @param token Security Token
     * @return 200: {email:String}, 403: incorrect Token, 404: User Not Found.
     */
    @Operation(summary = "Gets an email from a user ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User email returned", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "403", description = "Incorrect API Token", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)})
    @RequestMapping(value = "/api/v1/users/get/{userID}", method = RequestMethod.GET)
    public ResponseEntity<Object> getEmail(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN") String token, @PathVariable String userID) {
        userID = injectionPrevention(userID);
        if(!Objects.equals(token, generalToken)) {
            return new ResponseEntity<>("Incorrect API token", HttpStatus.FORBIDDEN);
        } else {
            try {
                if(database.userExists(database.getEmail(userID)))  {
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
     * Gets User Role
     * @param userID String ex: 20:45113161-8c86-4181-b8df-d83828a72856
     * @param token Security Token
     * @return 200: {role:int}, 403: Incorrect Token, 404: User Not Found.
     */
    @Operation(summary = "Gets user role from a User IDs.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User role returned.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "403", description = "Incorrect API Token", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)})
    @RequestMapping(value = "/api/v1/{userID}/users/get/role", method = RequestMethod.GET)
    public ResponseEntity<Object> getRole(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN") String token, @PathVariable String userID) {
        userID = injectionPrevention(userID);
        if(!Objects.equals(token, generalToken)) {
            return new ResponseEntity<>("Incorrect API token", HttpStatus.FORBIDDEN);
        } else {
            try {
                if (database.userExists(database.getEmail(userID))) {
                    userStore.updateUserActivity(userID);
                    return new ResponseEntity<>(database.getRole(userID), HttpStatus.OK);
                }
                return new ResponseEntity<>("User not Found", HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * Edits the user
     * @param login {email:string, password:string}
     * @param token Security Token
     * @return 200:User successfully edited!, 403: Incorrect Token, 404: User Not Found.
     */
    @Operation(summary = "Edits user information. Username and password can both be edited.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User information successfully edited", content = @Content(mediaType = "application/json", schema = @Schema(type = "boolean"))),
            @ApiResponse(responseCode = "403", description = "Incorrect API Token", content = @Content),
            @ApiResponse(responseCode = "403", description = "Email already in use", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)})
    @RequestMapping(value = "/api/v1/{userID}/users/update", method = RequestMethod.PUT)
    public ResponseEntity<Object> editUser(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN") String token, @RequestBody Login login, @PathVariable String userID) {
        userID = injectionPrevention(userID);
        if(!Objects.equals(token, generalToken)) {
            return new ResponseEntity<>("Incorrect API token", HttpStatus.FORBIDDEN);
        } else {
            try {
                userStore.updateUserActivity(userID);
                if (database.userExists(database.getEmail(userID)) && userID.substring(0, userID.indexOf(":")).equals("20")) {
                    if((login.getEmail() != null && database.userExists(login.getEmail()))) {
                        return new ResponseEntity<>("Email already in use.", HttpStatus.FORBIDDEN);
                    }
                    database.editUser(userID, login);
                    return new ResponseEntity<>(true, HttpStatus.OK);
                }
                return new ResponseEntity<>("User Not Found.", HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
