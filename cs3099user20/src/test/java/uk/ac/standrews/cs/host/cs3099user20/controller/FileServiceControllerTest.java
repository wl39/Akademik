package uk.ac.standrews.cs.host.cs3099user20.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import uk.ac.standrews.cs.host.cs3099user20.database.Database;
import uk.ac.standrews.cs.host.cs3099user20.model.TestSpecimens;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({FileServiceController.class})
public class FileServiceControllerTest {


    Database database = new Database();
    TestSpecimens specimen = new TestSpecimens();
    final String generalToken = "cs3099user20ThisIsSecret";
    final String invalidToken = "cs3099user20ThisIsAnInvalidTokenButItIsSecretStill";
    @Autowired
    private MockMvc mvc;

    public FileServiceControllerTest() throws Exception {
        Database database = new Database();
        database.setUpForTesting();
    }

    public static String asJsonString(final Object obj) {
        try {
            return (new ObjectMapper()).writeValueAsString(obj);
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    public MvcResult getPOST(String uri, String uriValue, String token, Object requestBody, ResultMatcher expectedResult) throws Exception {
        return this.mvc.perform(MockMvcRequestBuilders.post(uri, new Object[]{uriValue}).header("X-FOREIGNJOURNAL-SECURITY-TOKEN", new Object[]{token}).content(asJsonString(requestBody)).contentType(MediaType.APPLICATION_JSON).accept(new MediaType[]{MediaType.APPLICATION_JSON})).andDo(MockMvcResultHandlers.print()).andExpect(expectedResult).andReturn();
    }

    public MvcResult getGET(String uri, String uriValue, String token, ResultMatcher expectedResult) throws Exception {
        return this.mvc.perform(MockMvcRequestBuilders.get(uri, new Object[]{uriValue}).header("X-FOREIGNJOURNAL-SECURITY-TOKEN", new Object[]{token}).accept(new MediaType[]{MediaType.APPLICATION_JSON})).andDo(MockMvcResultHandlers.print()).andExpect(expectedResult).andReturn();
    }

    public MvcResult getPUT(String uri, String uriValue, String token, Object requestBody, ResultMatcher expectedResult) throws Exception {
        return this.mvc.perform(MockMvcRequestBuilders.put(uri, new Object[]{uriValue}).header("X-FOREIGNJOURNAL-SECURITY-TOKEN", new Object[]{token}).content(asJsonString(requestBody)).contentType(MediaType.APPLICATION_JSON).accept(new MediaType[]{MediaType.APPLICATION_JSON})).andDo(MockMvcResultHandlers.print()).andExpect(expectedResult).andReturn();
    }

    @BeforeEach
    public void init() throws Exception {
        Database database = new Database();
        database.setUpForTesting();
    }

    /**
     * Test that successfully edits a comment
     * @throws Exception
     */
    @Test
    public void editComment() throws Exception {
        MvcResult result = getPUT("/api/v1/admin/file/edit/comment", "", generalToken, specimen.editComment(), status().isOk());
        assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    }

    @Test
    public void editCommentInvalid() throws Exception {
        MvcResult result = getPUT("/api/v1/admin/file/edit/comment", "", generalToken, specimen.editCommentInvalid(), status().isNotFound());
        assertFalse(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    }

    /**
     * Test that successfully gets a file
     * @throws Exception
     */
    @Test
    public void getFile() throws Exception {
        MvcResult result = getPOST("/api/v1/admin/file/get", "", generalToken, specimen.testFile(), status().isOk());
        assertEquals("console.log(\"hello\")\n",(result.getResponse().getContentAsString()));
    }

    @Test
    public void getFileInvalid() throws Exception {
        MvcResult result = getPOST("/api/v1/admin/file/get", "", generalToken, specimen.testFileDataInvalid(), status().isNotFound());
        assertFalse(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    }

    /**
     * Test that successfully creates a comment
     */
    @Test
    public void createComment() throws Exception {
        MvcResult result = getPOST("/api/v1/admin/file/create/comment", "", generalToken, specimen.newComment(), status().isOk());
        assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    }

    /**
     * Test that successfully creates a comment
     */
    @Test
    public void createCommentInvalid() throws Exception {
        MvcResult result = getPOST("/api/v1/admin/file/create/comment", "", generalToken, specimen.newCommentInvalid(), status().isNotFound());
        assertFalse(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    }
    /**
     * Test that successfully gets a comment
     * @throws Exception
     */
    @Test
    public void getComment() throws Exception {
        MvcResult result = getPOST("/api/v1/admin/file/comments", "", generalToken, specimen.comment(3), status().isOk());
        assertEquals(new JSONObject(result.getResponse().getContentAsString().substring(1)).get("messageID"), specimen.comment(1).getMessageID());
        assertEquals(new JSONObject(result.getResponse().getContentAsString().substring(1)).getString("text"), specimen.comment(1).getText());
        assertEquals(new JSONObject(result.getResponse().getContentAsString().substring(1)).get("versionID"), specimen.comment(1).getVersionID());
        assertEquals(new JSONObject(result.getResponse().getContentAsString().substring(1)).get("filename"), specimen.comment(1).getFilename());
        assertEquals(new JSONObject(result.getResponse().getContentAsString().substring(1)).get("userID"), specimen.comment(1).getUserID());
        assertEquals(new JSONObject(result.getResponse().getContentAsString().substring(1)).get("lineStart"), specimen.comment(1).getLineStart());
        assertEquals(new JSONObject(result.getResponse().getContentAsString().substring(1)).get("lineEnd"), specimen.comment(1).getLineEnd());
    }




}