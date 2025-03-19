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

import java.nio.file.FileSystemException;
import java.sql.SQLException;
import java.util.Objects;

import static uk.ac.standrews.cs.host.cs3099user20.service.InjectionPrevention.injectionPrevention;

@RestController
public class ArticleServiceController {
    // Create database object
    Database database = new Database();
    ActiveUserStore userStore = new ActiveUserStore();
    //Strings for needed tokens + request value
    final String generalToken = "cs3099user20ThisIsSecret";

    /**
     * Creates Article
     * @param token Security Token
     * @param article {{name, description, license, uploaderID, authors}:comma-list}}
     * @param userID UUID
     * @return 200: true, 403: Incorrect Token, 400: Bad Request, 500: Server Error
     */
    @Operation(summary = "Creates an Article.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article created.", content = @Content(mediaType = "application/json", schema = @Schema(type = "boolean"))),
            @ApiResponse(responseCode = "401", description = "Not Logged in.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Incorrect API Token", content = @Content),
            @ApiResponse(responseCode = "400", description = "Not all information present to create a record", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)})
    @RequestMapping(value = "/api/v1/{userID}/articles/create", method = RequestMethod.POST)

    public ResponseEntity<Object> createArticle(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN") String token, @RequestBody Article article, @PathVariable String userID) {
        userID = injectionPrevention(userID);
        if(!Objects.equals(token, generalToken)) {
            return new ResponseEntity<>("Incorrect API token", HttpStatus.FORBIDDEN);
        } else {
            userStore.updateUserActivity(userID);
            try {
                // get if creation successful
                if (database.createArticleFromZip(article) > 0) {
                    return new ResponseEntity<>(true, HttpStatus.OK);
                } else {
                    throw new Exception();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Not all information present to create a record", HttpStatus.BAD_REQUEST);
            } catch (NullPointerException e) {
                e.printStackTrace();
                return new ResponseEntity<>("No authors were added", HttpStatus.BAD_REQUEST);
            } catch (FileSystemException e) {
            e.printStackTrace();
            return new ResponseEntity<>("No files attatched", HttpStatus.NOT_ACCEPTABLE);
            }catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * Creates a Version
     * @param token Security Token
     * @param versionID versionID number
     * @param userID UUID
     * @param fileData {{file: {mime, data}}}
     * @return 200: true, 400: Bad Request, 403: Incorrect Token, 404: Article not Found
     */
    @Operation(summary = "Creates a Version for an Article.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Version Created.", content = @Content(mediaType = "application/json", schema = @Schema(type = "boolean"))),
            @ApiResponse(responseCode = "401", description = "Not Logged in.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Incorrect API Token", content = @Content),
            @ApiResponse(responseCode = "406", description = "File contents missing", content = @Content),
            @ApiResponse(responseCode = "406", description = "Incorrect file type sent.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Article not found.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)})
    @RequestMapping(value = "/api/v1/{userID}/articles/create/{versionID}/version", method = RequestMethod.POST)
    public ResponseEntity<Object> createVersion(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN") String token, @RequestBody FileData fileData, @PathVariable int versionID, @PathVariable String userID) {
        userID = injectionPrevention(userID);
        if(!Objects.equals(token, generalToken)) {
            return new ResponseEntity<>("Incorrect API token", HttpStatus.FORBIDDEN);
        } else {
            userStore.updateUserActivity(userID);
            // check that a base64encoding exists
            // Check if mime is zip, if not reject brutally
            if(fileData.getBase64Value() == null){return new ResponseEntity<>("File contents missing", HttpStatus.NOT_ACCEPTABLE);}
            if(!fileData.getMime().equals("application/zip")){return new ResponseEntity<>("Incorrect file type: " + fileData.getMime(), HttpStatus.NOT_ACCEPTABLE);}
            try {
                database.createVersion(versionID, fileData);
                return new ResponseEntity<>(true, HttpStatus.OK);
            } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Article not found", HttpStatus.NOT_FOUND);
        }    catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Server Error. Likely: Base64 of the zip is not correct.", HttpStatus.BAD_REQUEST);
            }
        }
    }

    /**
     * Creates a Discussion Comment
     * @param token Security Token
     * @param discussionMessage {userID, comment}
     * @param versionID versionID number
     * @param userID UUID
     * @return 200: true, 403: Incorrect Token, 404: Article not Found, 500: Server Error
     */
    @Operation(summary = "Creates a Discussion Comment.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Discussion comment created.", content = @Content(mediaType = "application/json", schema = @Schema(type = "boolean"))),
            @ApiResponse(responseCode = "401", description = "Not Logged in.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Incorrect API Token", content = @Content),
            @ApiResponse(responseCode = "400", description = "Either a user doesn't exist, or two of the same user has been sent.", content = @Content),
            @ApiResponse(responseCode = "404", description = "No comment made.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)})
    @RequestMapping(value = "/api/v1/{userID}/articles/create/{versionID}/discussion", method = RequestMethod.POST)
    public ResponseEntity<Object> createDiscussionComment(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN") String token, @RequestBody DiscussionMessage discussionMessage, @PathVariable int versionID, @PathVariable String userID) {
        userID = injectionPrevention(userID);
        if(!Objects.equals(token, generalToken)) {
            return new ResponseEntity<>("Incorrect API token", HttpStatus.FORBIDDEN);
        }
        else {
            userStore.updateUserActivity(userID);
            try {
                discussionMessage.setVersionID(versionID);
                if (discussionMessage.getText() == null) {
                    return new ResponseEntity<>("Either a user doesn't exist, or two of the same user has been sent", HttpStatus.BAD_REQUEST);
                }
                if(database.createDiscussion(discussionMessage)) {
                    return new ResponseEntity<>(true, HttpStatus.OK);
                } else {
                    throw new Exception();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * Gets articles to (1) assign, (3) all
     * @param token Security Token
     * @param pagination {userID, role, startID, quantity}
     *          -> if userID and role are not there then userID=null, roleID=4 (to get all articles are assumed)
     * @param userID UUID
     * @return 200: {[articleID, name, description, authors as a comma list]}, 403: Incorrect Token, 404: User not Found, 500: Server Error
     */
    @Operation(summary = "Gets articles to either assign, review, or view.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Articles obtained.", content = @Content(mediaType = "application/json", schema = @Schema(type = "boolean"))),
            @ApiResponse(responseCode = "401", description = "Not Logged in.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Incorrect API Token", content = @Content),
            @ApiResponse(responseCode = "403", description = "User is not a Moderator", content = @Content),
            @ApiResponse(responseCode = "400", description = "Role does not Exist.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Article not Found.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)})
    @RequestMapping(value = "/api/v1/{userID}/articles/get", method = RequestMethod.POST)
    public ResponseEntity<Object> getArticles(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN") String token, @RequestBody Pagination pagination, @PathVariable String userID) {
        userID = injectionPrevention(userID);
        if(!Objects.equals(token, generalToken)) {
            return new ResponseEntity<>("Incorrect API token", HttpStatus.FORBIDDEN);
        }
        else {
            userStore.updateUserActivity(userID);
            try {
                System.out.println(userID);
                System.out.println("UserID: " + pagination.getUserID());
                System.out.println("Role: " + pagination.getRole());
                System.out.println("StartID: " + pagination.getStartID());
                System.out.println("Quantity: " + pagination.getQuantity());
                // ASSUMING ROLE IS 4.
                SimpleArticle[] res = database.getArticles(pagination.getRole(), pagination.getStartID(), pagination.getQuantity(), pagination.getUserID());
                return new ResponseEntity<>(res, HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Role does not exist.", HttpStatus.BAD_REQUEST);
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

    /**
     * Gets article from Article ID
     * @param token Security Token
     * @param articleID {articleID:int}
     * @param userID UUID
     * @return 200: {versionID, name, description, license, authors as a comma list, versions as a comma list}, 403: Incorrect Token, 404: Article not Found, 500: Server Error
     * DONE
     */
    @Operation(summary = "Gets article from article ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article obtained.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Article.class))),
            @ApiResponse(responseCode = "401", description = "Not Logged in.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Incorrect API Token", content = @Content),
            @ApiResponse(responseCode = "404", description = "Article not Found.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)})
    @RequestMapping(value = "/api/v1/{userID}/articles/get/{articleID}", method = RequestMethod.GET)
    public ResponseEntity<Object> getArticle(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN") String token, @PathVariable int articleID, @PathVariable String userID) {
        userID = injectionPrevention(userID);
        if(!Objects.equals(token, generalToken)) {
            return new ResponseEntity<>("Incorrect API token", HttpStatus.FORBIDDEN);
        } else {
            userStore.updateUserActivity(userID);
            try {
                Article res = database.getArticle(articleID);
                System.out.println(res);
                return new ResponseEntity<>(res, HttpStatus.OK);
            } catch (SQLException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Article not found", HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * Gets discussion from an article
     * @param token Security Token
     * @param versionID versionID int
     * @param userID UUID
     * @return 200: {userID:string, comment:string, timestamp:string}, 403: Incorrect Token, 404: Article not Found, 500: Server Error
     */
    @Operation(summary = "Gets the discussion of article from the version ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Discussion obtained.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DiscussionMessage.class, type = "array"))),
            @ApiResponse(responseCode = "401", description = "Not Logged in.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Incorrect API Token", content = @Content),
            @ApiResponse(responseCode = "404", description = "Article not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error.", content = @Content)})
    @RequestMapping(value = "/api/v1/{userID}/articles/get/{versionID}/discussion", method = RequestMethod.GET)
    public ResponseEntity<Object> getDiscussion(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN") String token, @PathVariable int versionID, @PathVariable String userID) {
        userID = injectionPrevention(userID);
        if(!Objects.equals(token, generalToken)) {
            return new ResponseEntity<>("Incorrect API token", HttpStatus.FORBIDDEN);
        } else {
            userStore.updateUserActivity(userID);
            try {
                DiscussionMessage[] res = database.getDiscussionMessages(versionID);
                return new ResponseEntity<>(res, HttpStatus.OK);
            }catch (SQLException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Article not found", HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }


    /**
     * exports the article in JSON
     * @param token Security Token
     * @param versionID versionID
     * @param userID UUID
     * @return 200: the article in JSON, 403: Incorrect Token, 404: Article not Found, 500: Server Error
     */
    @Operation(summary = "Exports the article in JSON.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article exported.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = JSONObject.class))),
            @ApiResponse(responseCode = "401", description = "Not Logged in.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Incorrect API Token", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error.", content = @Content)})
    @RequestMapping(value = "/api/v1/{userID}/articles/get/{versionID}/download", method = RequestMethod.GET)
    public ResponseEntity<Object> downloadArticle(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN") String token, @PathVariable int versionID, @PathVariable String userID) {
        userID = injectionPrevention(userID);
        if(!Objects.equals(token, generalToken)) {
            return new ResponseEntity<>("Incorrect API token", HttpStatus.FORBIDDEN);
        } else {
            userStore.updateUserActivity(userID);
            try {
                return new ResponseEntity<>(database.download(versionID), HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * exports the article in JSON
     * @param token Security Token
     * @param versionID Version ID
     * @param groupNumber Supergroup number for exporting
     * @param userID UUID
     * @return 200: the article in JSON, 403: Invalid Token, 404: Article not Found, 500: Server Error
     * NOT DONE
     */
    @RequestMapping(value = "/api/v1/{userID}/articles/get/{versionID}/{groupNumber}/migrate", method = RequestMethod.GET)
    public ResponseEntity<Object> downloadArticle(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN") String token, @PathVariable int versionID, @PathVariable String groupNumber, @PathVariable String userID) {
        userID = injectionPrevention(userID);
        groupNumber = injectionPrevention(groupNumber);
        if(!Objects.equals(token, generalToken)) {
            return new ResponseEntity<>("Incorrect API token", HttpStatus.FORBIDDEN);
        } else {
            userStore.updateUserActivity(userID);
            try {
                boolean ret = database.migrateToJournal(groupNumber, versionID);
                return new ResponseEntity<>(ret, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * gets role of User from an article
     * @param token Security token
     * @param versionID Version ID of article
     * @param userID UUID
     * @param articleUserID userID of article uploader
     * @return 200: {role:string} [1(mod), 2(uploader), 3(reviewer), 4(general)], 403: Incorrect Token, 404: Article not Found, 404: User not found, 500: Server Error
     */
    @Operation(summary = "Gets role of user from an article.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role obtained.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "401", description = "Not Logged in.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Incorrect API Token", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not Found", content = @Content),
            @ApiResponse(responseCode = "404", description = "Version not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error.", content = @Content)})
    @RequestMapping(value = "/api/v1/{userID}/articles/get/{versionID}/{articleUserID}/role", method = RequestMethod.GET)
    public ResponseEntity<Object> getRole(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN") String token, @PathVariable("versionID") int versionID, @PathVariable("userID") String userID, @PathVariable("articleUserID") String articleUserID) {
        userID = injectionPrevention(userID);
        articleUserID = injectionPrevention(articleUserID);
        if(!Objects.equals(token, generalToken)) {
            return new ResponseEntity<>("Incorrect API token", HttpStatus.FORBIDDEN);
        }  else {
            userStore.updateUserActivity(userID);
            try {
                if (database.versionExist(versionID)) {
                    Role role = new Role();
                    switch (database.getRole(articleUserID).getRole()) {
                        case (1) : role.setModerator(); break;
                        case (2) : role = database.getRole(versionID, articleUserID); break;
                        case (3) : role.setReviewer(); break;
                        case (4) : role = database.getRole(versionID, articleUserID); break;
                        default:
                            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
                    }
                    return new ResponseEntity<>(role, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Version Not Found", HttpStatus.NOT_FOUND);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * Update Article
     * @param token Security Token
     * @param article {(name):string, (description):string, (authors as a comma list):string, (license):string}
     * @param versionID current versionID
     * @param userID UUID
     * @return 200: "Article updated!", 403: Incorrect Token, 404: "Article not found"
     * DONE
     */
    @Operation(summary = "Updates an article.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article updated!", content = @Content(mediaType = "application/json", schema = @Schema(type = "boolean"))),
            @ApiResponse(responseCode = "401", description = "Not Logged in.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Incorrect API Token", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error.", content = @Content)})
    @RequestMapping(value = "/api/v1/{userID}/articles/update/{versionID}/article", method = RequestMethod.PUT)
    public ResponseEntity<Object> editArticle(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN") String token, @RequestBody SimpleArticle article, @PathVariable int versionID, @PathVariable String userID) {
        userID = injectionPrevention(userID);
        if(!Objects.equals(token, generalToken)) {
            return new ResponseEntity<>("Incorrect API token", HttpStatus.FORBIDDEN);
        } else {
            userStore.updateUserActivity(userID);
            try {
                database.updateArticle(versionID, article);
                return new ResponseEntity<>(true, HttpStatus.OK);
            } catch (SQLException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Article not found", HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * Vote on Article
     * @param token Security Token
     * @param user {UUID: id, Email: Email}
     * @param versionID current versionID
     * @param userID UUID
     * @param vote 0 = reject, 1 = accept
     * @return 200: "Article updated!", 403: Incorrect Token, 404: User not found, 409: User already voted
     * NOT DONE
     */
    @Operation(summary = "Vote on an article.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article voted on!", content = @Content(mediaType = "application/json", schema = @Schema(type = "boolean"))),
            @ApiResponse(responseCode = "401", description = "Not Logged in.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Incorrect API Token", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found!", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error.", content = @Content)})
    @RequestMapping(value = "/api/v1/{userID}/articles/update/{versionID}/vote/{vote}", method = RequestMethod.PUT)
    public ResponseEntity<Object> voteArticle(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN") String token, @RequestBody User user, @PathVariable int versionID, @PathVariable String userID, @PathVariable int vote) {
        userID = injectionPrevention(userID);
        if(!Objects.equals(token, generalToken)) {
            return new ResponseEntity<>("Incorrect API token", HttpStatus.FORBIDDEN);
        } else {
            System.out.println(user.getEmail());
            userStore.updateUserActivity(userID);
            try {
                database.vote(versionID, user, vote);
                System.out.println("Here");
                return new ResponseEntity<>(true, HttpStatus.OK);
            } catch (SQLException e){

                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     *
     * @param token Security Token
     * @param search {searchString: substring being searched, groupNumbers: groups being searched}
     * @return 200: Search result, 403: Incorrect token, 404: Group not a part of the Federation, 500: Server Error
     */
    @Operation(summary = "Searches for an article.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article searched!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Article.class))),
            @ApiResponse(responseCode = "401", description = "Not Logged in.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Incorrect API Token", content = @Content),
            @ApiResponse(responseCode = "404", description = "Group Not A Part Of the Federation!", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error.", content = @Content)})
    @RequestMapping(value = "/api/v1/{userID}/articles/search", method = RequestMethod.POST)
    public ResponseEntity<Object> searchArticle(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN") String token, @RequestBody Search search) {
        if(!Objects.equals(token, generalToken)) {
            return new ResponseEntity<>("Incorrect API token", HttpStatus.FORBIDDEN);
        } else {
            try {
                return new ResponseEntity<>(database.searchInGroups(search), HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Group Not A Part Of the Federation", HttpStatus.NOT_FOUND);
            }catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

}
