package uk.ac.standrews.cs.host.cs3099user20.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ArticleServiceController.class)
public class ArticleServiceControllerTest {

    TestSpecimens specimen = new TestSpecimens();

    final String generalToken = "cs3099user20ThisIsSecret";
    final String invalidToken = "cs3099user20ThisIsAnInvalidTokenButItIsSecretStill";

    final private int isOk = HttpStatus.OK.value();
    final private int badRequest = HttpStatus.BAD_REQUEST.value();
    final private int notFound = HttpStatus.NOT_FOUND.value();


    @Autowired
    private MockMvc mvc;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONObject stringToJSON(String string) {
        return new JSONObject(string);
    }


    public MvcResult getPOST(String uri, String uriValue, String token, Object requestBody, ResultMatcher expectedResult) throws Exception {
        return mvc.perform( MockMvcRequestBuilders
                        .post(uri, uriValue).header("X-FOREIGNJOURNAL-SECURITY-TOKEN", token)
                        .content(asJsonString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(expectedResult).andReturn();
    }

    public MvcResult getGET(String uri, String uriValue, String token, ResultMatcher expectedResult) throws Exception {
        return mvc.perform( MockMvcRequestBuilders
                        .get(uri, uriValue).header("X-FOREIGNJOURNAL-SECURITY-TOKEN", token)
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
    public void init() throws Exception {
        Database database = new Database();
        database.setUpForTesting();
    }


//------------------------------------------------------------------------------------------//

    @Test
    /**
     * Test to check that voting for an article to be published to 1 works
     */
    public void voteArticle() throws Exception {
        MvcResult result = getPUT("/api/v1/admin/articles/update/19/vote/0", "", generalToken, specimen.testUsers(30), status().isOk());
        assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    }

    @Test
    public void updateArticle() throws Exception {
        MvcResult result = getPUT("/api/v1/admin/articles/update/23/article", "", generalToken, specimen.articleEdit(), status().isOk());
        assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    }

    /**
     * WILL NOT WORK UNTIL HOSTED
     * @throws Exception
     */
    @Test
    public void searchArticle() throws Exception {
        MvcResult result = getPOST("/api/v1/admin/articles/search", "", generalToken, specimen.testSearch("deconstructing"), status().isOk());
        assertEquals(new JSONObject(result.getResponse().getContentAsString().substring(1)).get("id"), specimen.testArticle(6).getId());
        assertEquals(new JSONObject(result.getResponse().getContentAsString().substring(1)).get("authors"), specimen.testArticle(6).getAuthors());
        assertEquals(new JSONObject(result.getResponse().getContentAsString().substring(1)).get("title"), specimen.testArticle(6).getName());
        assertEquals(new JSONObject(result.getResponse().getContentAsString().substring(1)).get("articleURL"), specimen.testArticle(6).getUrl());

    }



    /**
     * Gets all articles
     * @throws Exception
     */
    @Test
    public void getArticles() throws Exception {
        MvcResult result = getPOST("/api/v1/admin/articles/get", "", generalToken, specimen.testPagination(), status().isOk());
        String[] articleArray = result.getResponse().getContentAsString().substring(1).split(("},").concat("}"));
        for (int i  = 0; i < articleArray.length; i++) {
            JSONObject articleJSON = new JSONObject(articleArray[i]);
            assertEquals(articleJSON.get("id"), specimen.testArticle(i + 1).getId());
            assertEquals(articleJSON.get("license"), specimen.testArticle(i + 1).getLicense());
            assertEquals(articleJSON.get("authors"), specimen.testArticle(i + 1).getAuthors());
            assertEquals(articleJSON.get("title"), specimen.testArticle(i + 1).getName());
            assertEquals(articleJSON.get("id"), specimen.testArticle(i + 1).getId());
            assertEquals(articleJSON.get("articleURL"), specimen.testArticle(i + 1).getUrl());

        }
    }

    /**
     * Creates a new article
     * @throws Exception
     */
    @Test
    public void createArticle() throws Exception {
        MvcResult result = getPOST("/api/v1/admin/articles/create", "", generalToken, specimen.testNewArticle(), status().isOk());
        assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    }

    @Test
    public void createInvalidArticle() throws Exception {
        MvcResult result = getPOST("/api/v1/admin/articles/create", "", generalToken, specimen.testInvalidArticle(), status().isBadRequest());
        assertFalse(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    }

    /**
     * Creates a new Version
     * @throws Exception
     */
    @Test
    public void createVersion() throws Exception {
        MvcResult result = getPOST("/api/v1/admin/articles/create/18/version", "", generalToken, specimen.testFileData(), status().isOk());
        assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    }
    @Test
    public void createInvalidVersion() throws Exception {
        MvcResult result = getPOST("/api/v1/admin/articles/create", "", generalToken, specimen.testFileDataInvalid(), status().isNotAcceptable());
        assertFalse(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    }

    /**
     * Test that creates a discussion comment
     * @throws Exception
     */
    @Test
    public void createDiscussionComment() throws Exception {
        MvcResult result = getPOST("/api/v1/admin/articles/create/18/discussion", "", generalToken, specimen.testNewDiscussionMessage(), status().isOk());
        assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    }

    /**
     * Test that migrates an article
     * @throws Exception
     */
    @Test
    public void migrateArticle() throws Exception {
        MvcResult result = getGET("/api/v1/admin/articles/get/18/20/migrate", "", generalToken, status().isOk());
        assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    }

    /**
     * Gets a role from an article
     * @throws Exception
     */
    @Test
    public void getArticleUserRole() throws Exception {
        MvcResult result = getGET("/api/v1/admin/articles/get/18/2/role", "", generalToken, status().isOk());
        assertEquals(new JSONObject(result.getResponse().getContentAsString()).get("role"), (4));
    }

    /**
     * Exports the Article into JSON
     * @throws Exception
     */
    @Test
    public void exportArticle() throws Exception {
        MvcResult result = getGET("/api/v1/admin/articles/get/18/download", "", generalToken, status().isOk());
        JSONObject downloadResponse = new JSONObject(result.getResponse().getContentAsString());
        assertEquals(downloadResponse.get("name"), specimen.testDownload().get("name"));
        assertEquals(new JSONObject(downloadResponse.get("metadata").toString()).get("license"), new JSONObject(specimen.testDownload().get("metadata").toString()).get("license"));
        assertEquals(new JSONObject(downloadResponse.get("metadata").toString()).get("categories").toString(), new JSONObject(specimen.testDownload().get("metadata").toString()).get("categories"));
        assertEquals(new JSONObject(downloadResponse.get("metadata").toString()).get("authors").toString(), new JSONObject(specimen.testDownload().get("metadata").toString()).get("authors"));
        assertEquals(new JSONObject(downloadResponse.get("metadata").toString()).get("abstract").toString(), new JSONObject(specimen.testDownload().get("metadata").toString()).get("abstract"));

    }

    @Test
    public void getDiscussion() throws Exception {
        MvcResult result = getGET("/api/v1/admin/articles/get/18/discussion", "", generalToken, status().isOk());
        assertEquals(new JSONObject(result.getResponse().getContentAsString().substring(1)).get("messageID"), specimen.testDiscussionMessages(1).getMessageID());
        assertEquals(new JSONObject(result.getResponse().getContentAsString().substring(1)).get("userID"), specimen.testDiscussionMessages(1).getUserID());
        assertEquals(new JSONObject(result.getResponse().getContentAsString().substring(1)).get("text"), specimen.testDiscussionMessages(1).getText());
        assertEquals(new JSONObject(result.getResponse().getContentAsString().substring(1)).get("parentID"), specimen.testDiscussionMessages(1).getParentID());
        assertEquals(new JSONObject(result.getResponse().getContentAsString().substring(1)).get("versionID"), specimen.testDiscussionMessages(1).getVersionID());

    }

    @Test
    public void getArticlefromID() throws Exception {
        MvcResult result = getGET("/api/v1/admin/articles/get/14", "", generalToken, status().isOk());
        assertEquals(new JSONObject(result.getResponse().getContentAsString()).get("id"), specimen.testArticle(13).getId());
        assertEquals(new JSONObject(result.getResponse().getContentAsString()).get("license"), specimen.testArticle(13).getLicense());
        assertEquals(new JSONObject(result.getResponse().getContentAsString()).get("authors"), specimen.testArticle(13).getAuthors());
        assertEquals(new JSONObject(result.getResponse().getContentAsString()).get("uploaderID"), specimen.testArticle(13).getUploaderID());
        assertEquals(new JSONObject(result.getResponse().getContentAsString()).get("topVersionID"), specimen.testArticle(13).getTopVersionID());

    }



}


