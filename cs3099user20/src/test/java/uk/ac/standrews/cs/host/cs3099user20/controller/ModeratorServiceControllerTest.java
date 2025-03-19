package uk.ac.standrews.cs.host.cs3099user20.controller;

//import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import uk.ac.standrews.cs.host.cs3099user20.database.Database;
import uk.ac.standrews.cs.host.cs3099user20.model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ModeratorServiceController.class)
public class ModeratorServiceControllerTest {

    Database database = new Database();

    TestSpecimens specimen = new TestSpecimens();

    final String modToken = "cs3099user20ThisIsSecreter";
    final String invalidToken = "cs3099user20ThisIsAnInvalidTokenButItIsSecretStill";

    final private int isOk = HttpStatus.OK.value();
    final private int badRequest = HttpStatus.BAD_REQUEST.value();
    final private int notFound = HttpStatus.NOT_FOUND.value();
    final private int notAMod = HttpStatus.FORBIDDEN.value();


    @Autowired
    private MockMvc mvc;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONArray asJsonArray(final Object[] objs) {
        try {
            return new JSONArray(objs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONObject stringToJSON(String string) {
        return new JSONObject(string);
    }

    public static JSONArray stringToJSONArray(String string) {
        return new JSONArray(string);
    }


    public MvcResult getPOST(String uri, String uriValue, String token, Object requestBody, ResultMatcher expectedResult) throws Exception {
        return mvc.perform( MockMvcRequestBuilders
                        .post(uri, uriValue).header("X-FOREIGNJOURNAL-SECURITY-TOKEN", token)
                        .content(asJsonString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(expectedResult)
                .andReturn();
    }

    public void getPOSTArray(String uri, String uriValue, String token, Object requestBody, ResultMatcher expectedResult) throws Exception {
        /*return mvc.perform( MockMvcRequestBuilders
                        .post(uri, uriValue).header("X-FOREIGNJOURNAL-SECURITY-TOKEN", token)
                        .content(asJsonArray(requestBody).toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(expectedResult)
                .andReturn();

         */
        mvc.perform( MockMvcRequestBuilders
                        .post(uri, uriValue).header("X-FOREIGNJOURNAL-SECURITY-TOKEN", token)
                        .content(asJsonString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    public MvcResult getGET(String uri, String uriValue, String token, ResultMatcher expectedResult) throws Exception {
        return mvc.perform( MockMvcRequestBuilders
                        .get(uri, uriValue).header("X-FOREIGNJOURNAL-SECURITY-TOKEN", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(expectedResult).andReturn();
    }

    public MvcResult getDELETE(String uri, String uriValue, String token, ResultMatcher expectedResult) throws Exception {
        return mvc.perform( MockMvcRequestBuilders
                        .delete(uri, uriValue).header("X-FOREIGNJOURNAL-SECURITY-TOKEN", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(expectedResult).andReturn();
    }


    public MvcResult getPUT(String uri, String uriValue, String token, Object requestBody, ResultMatcher expectedResult) throws Exception {
        return mvc.perform( MockMvcRequestBuilders
                        .put(uri, uriValue).header("X-FOREIGNJOURNAL-SECURITY-TOKEN", token)
                        .content(asJsonString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(expectedResult).andReturn();
    }

    @BeforeEach
    void init() throws Exception {
        Database database = new Database();
        database.setUpForTesting();
    }

    /**
     * /api/v1/{userID}/moderator/reviewers/add/{versionID}
     * @throws Exception
     */
    @Test
    public void modAssignment() throws Exception {
        Database database = new Database();
        database.setUpForTesting();
        MvcResult result = getPOST("/api/v1/admin/moderator/reviewers/add/24", "", modToken,
                new User[]{specimen.testUsers(7), specimen.testUsers(8), specimen.testUsers(30)}
                ,status().isOk());
        assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    }

    /**
     * /api/v1/{userID}/moderator/rejectReviewer
     * @throws Exception
     */
    @Test
    public void modReject() throws Exception {
        Database database = new Database();
        database.setUpForTesting();
        MvcResult result = getPOST("/api/v1/admin/moderator/rejectReviewer", "", modToken,
               specimen.testRejectReviewerDecision()
                ,status().isOk());
        assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    }

    /**
     * /api/v1/{userID}/moderator/rejectReviewer
     * @throws Exception
     */
    @Test
    public void modAccept() throws Exception {
        Database database = new Database();
        database.setUpForTesting();
        MvcResult result = getPOST("/api/v1/admin/moderator/acceptReviewer", "", modToken,
                specimen.testAcceptReviewerDecision()
                ,status().isOk());
        assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    }

    /**
     * /api/v1/{userID}/moderator/unauthorised/reviewers
     * @throws Exception
     */
    @Test
    public void modGetUnauthReviewers() throws Exception {
        Database database = new Database();
        database.setUpForTesting();
        MvcResult result = getGET("/api/v1/admin/moderator/unauthorised/reviewers", "", modToken ,status().isOk());
        assertEquals(2, stringToJSONArray(result.getResponse().getContentAsString()).toList().size());
    }

    /**
     * /api/v1/{userID}/moderator/reviewers
     * @throws Exception
     */
    @Test
    public void modGetReviewers() throws Exception {
        Database database = new Database();
        database.setUpForTesting();
        MvcResult result = getGET("/api/v1/admin/moderator/reviewers", "", modToken ,status().isOk());
        assertEquals(5, stringToJSONArray(result.getResponse().getContentAsString()).toList().size());
    }

    /**
     * /api/v1/{userID}/moderator/rejectArticle/{versionID}
     * @throws Exception
     */
    @Test
    public void modRejectArticle() throws Exception {
        Database database = new Database();
        database.setUpForTesting();
        MvcResult result = getDELETE("/api/v1/admin/moderator/rejectArticle/19", "", modToken ,status().isOk());
        assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    }

}
