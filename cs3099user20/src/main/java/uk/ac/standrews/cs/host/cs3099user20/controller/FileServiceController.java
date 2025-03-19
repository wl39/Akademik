
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
import uk.ac.standrews.cs.host.cs3099user20.model.*;
import java.io.FileNotFoundException;
import java.nio.file.NoSuchFileException;
import java.sql.SQLException;
import java.util.Objects;
import static uk.ac.standrews.cs.host.cs3099user20.service.InjectionPrevention.injectionPrevention;


@RestController
public class FileServiceController {

    Database database = new Database();
    final String generalToken = "cs3099user20ThisIsSecret";
    ActiveUserStore userStore = new ActiveUserStore();

    /**
     * Creates a Comment
     * @param token Security Token
     * @param comment {versionID, filepath, userID, comment, lineStart, lineEnd}
     * @param userID UUID
     * @return 200: Comment created!, 403: Invalid Token, 404: File not found, 404: User not found
     */
    @Operation(summary = "Creates a comment.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment created.", content = @Content(mediaType = "application/json", schema = @Schema(type = "boolean"))),
            @ApiResponse(responseCode = "401", description = "Not Logged in.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Incorrect API Token", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not Found.", content = @Content),
            @ApiResponse(responseCode = "404", description = "File not Found.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)})
    @RequestMapping(value="/api/v1/{userID}/file/create/comment", method = RequestMethod.POST)
    public ResponseEntity<Object> createComment(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN") String token, @RequestBody Comment comment, @PathVariable String userID) {
        userID = injectionPrevention(userID);
        if(!Objects.equals(token, generalToken)) {
            return new ResponseEntity<>("Incorrect API token", HttpStatus.FORBIDDEN);
        } else {
            userStore.updateUserActivity(userID);
            try {
                boolean ret = database.createComment(comment);
                return new ResponseEntity<>(ret, HttpStatus.OK);
            } catch (SQLException e) {
                e.printStackTrace();
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            } catch (NoSuchFileException e) {
                e.printStackTrace();
                return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * Gets comment
     * @param token Security Token
     * @param comment {versionID, filepath}
     * @param userID UUID
     * @return 200: {[userID:string, comment:string, line start:string, line end:string, timestamp:string]}, 403: Invalid Token, 404: File not found
     */
    @Operation(summary = "Get a comment.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comments obtained.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))),
            @ApiResponse(responseCode = "401", description = "Not Logged in.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Incorrect API Token", content = @Content),
            @ApiResponse(responseCode = "404", description = "File not Found.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)})
    @RequestMapping(value="/api/v1/{userID}/file/comments", method = RequestMethod.POST)
    public ResponseEntity<Object> getComment(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN") String token, @RequestBody Comment comment, @PathVariable String userID) {
        userID = injectionPrevention(userID);
        if(!Objects.equals(token, generalToken)) {
            return new ResponseEntity<>("Incorrect API token", HttpStatus.FORBIDDEN);
        } else {
            userStore.updateUserActivity(userID);
            try {
                Comment[] ret = database.getComments(comment);
                return new ResponseEntity<>(ret, HttpStatus.OK);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * Gets file ID at a given path
     * @param token Security Token
     * @param file {filepath}
     * @param userID UUID
     * @return 200: {[userID:string, comment:string, line start:string, line end:string, timestamp:string]}, 403: Invalid Token, 404: File not found
     */
    @Operation(summary = "Gets File ID at given path.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File ID obtained.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SimpleFile.class))),
            @ApiResponse(responseCode = "401", description = "Not Logged in.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Incorrect API Token", content = @Content),
            @ApiResponse(responseCode = "404", description = "File not Found.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)})
    @RequestMapping(value="/api/v1/{userID}/file/get", method = RequestMethod.POST)
    public ResponseEntity<Object> getFileID(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN") String token, @RequestBody SimpleFile file, @PathVariable String userID) {
        userID = injectionPrevention(userID);
        if(!Objects.equals(token, generalToken)) {
            return new ResponseEntity<>("Incorrect API token", HttpStatus.FORBIDDEN);
        } else {
            userStore.updateUserActivity(userID);
            try {
                if (file.isDir()) {
                    System.out.println("Here");
                    SimpleFile[] ret = database.getDir(file.getFilename());
                    return new ResponseEntity<>(ret, HttpStatus.OK);
                } else {
                    String ret = database.getFile(file.getFilename());
                    return new ResponseEntity<>(ret, HttpStatus.OK);
                }

            } catch (SQLException | NoSuchFileException | NumberFormatException e) {
                e.printStackTrace();
                return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * Update Comment
     * @param token Security Token
     * @param comment {versionID, filepath, userID, comment, lineStart, lineEnd}
     * @param userID UUID
     * @return 200: "Comment updated!"
     * DONE
     */
    @Operation(summary = "Updates Comment.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment edited.", content = @Content(mediaType = "application/json", schema = @Schema(type = "boolean"))),
            @ApiResponse(responseCode = "401", description = "Not Logged in.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Incorrect API Token", content = @Content),
            @ApiResponse(responseCode = "404", description = "Comment not Found.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)})
    @RequestMapping(value = "/api/v1/{userID}/file/edit/comment", method = RequestMethod.PUT)
    public ResponseEntity<Object> editArticle(@RequestHeader(value = "X-FOREIGNJOURNAL-SECURITY-TOKEN") String token, @RequestBody Comment comment, @PathVariable String userID) {
        userID = injectionPrevention(userID);

        if(!Objects.equals(token, generalToken)) {
            return new ResponseEntity<>("Incorrect API token", HttpStatus.FORBIDDEN);
        } else {
            userStore.updateUserActivity(userID);
            try {
                boolean ret = database.editComment(comment);
                return new ResponseEntity<>(ret, HttpStatus.OK);
            }
            catch (NoSuchFileException e) {
                e.printStackTrace();
                return new ResponseEntity<>("File not Found", HttpStatus.NOT_FOUND);
            }
            catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
}