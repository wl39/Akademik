package uk.ac.standrews.cs.host.cs3099user20.controller;

//import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.AssertionErrors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StringUtils;
import uk.ac.standrews.cs.host.cs3099user20.dao.DatabaseInitialisationRepository;
import uk.ac.standrews.cs.host.cs3099user20.database.Database;
import uk.ac.standrews.cs.host.cs3099user20.model.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SupergroupServiceController.class)
public class SupergroupServiceControllerTest {

    TestSpecimens specimen = new TestSpecimens();
    final String superToken = "cs3099user20ThisIsSecretPlzDontShare:)";

    @Autowired
    private MockMvc mvc;

    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper om = new ObjectMapper();
            om.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

            String objString = om.writeValueAsString(obj);
            return objString;
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

    /**
     * /api/v1/supergroup/submission
     * @throws Exception
     */
    /*
    @Test
    public void submissionTest() throws Exception {
        MvcResult result = getPOST("/api/v1/supergroup/submission", "", superToken, specimen.testSubmission(), status().isOk());
        assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    }
    */

    /**
     * /api/v1/supergroup/search --> substring = ""
     * @throws Exception
     */
    @Test
    public void searchTestAll() throws Exception {
        MvcResult result = getPOST("/api/v1/supergroup/search", "", superToken, specimen.testSearch(""), status().isOk());
        assertEquals(8, stringToJSONArray(result.getResponse().getContentAsString()).toList().size());
    }

    /**
     * /api/v1/supergroup/search --> substring = "Root"
     * @throws Exception
     */
    @Test
    public void searchTestRoot() throws Exception {
        MvcResult result = getPOST("/api/v1/supergroup/search", "", superToken, specimen.testSearch("Root"), status().isOk());
        assertEquals(1, stringToJSONArray(result.getResponse().getContentAsString()).toList().size());
    }

    /**
     * /api/v1/supergroup/search --> substring = "XXXXXX"
     * @throws Exception
     */
    @Test
    public void searchTestNone() throws Exception {
        MvcResult result = getPOST("/api/v1/supergroup/search", "", superToken, specimen.testSearch("XXXXXX"), status().isOk());
        assertEquals(0, stringToJSONArray(result.getResponse().getContentAsString()).toList().size());
    }

    /**
     * /api/v1/supergroup/login
     * @throws Exception
     */
    @Test
    public void supergroupLogin() throws Exception {
        MvcResult result = getPOST("/api/v1/supergroup/login", "", superToken, specimen.testLogin(), status().isOk());
        assertEquals("20:a2f5689e-a5ae-4a9c-a7fd-6ab5b86ffd0f", stringToJSON(result.getResponse().getContentAsString()).getString("id"));
    }

    /**
     * /api/v1/supergroup/user
     * @throws Exception
     */
    @Test
    public void supergroupUser() throws Exception {
        MvcResult result = getGET("/api/v1/supergroup/user", "", superToken, status().isOk());
        assertEquals(32, stringToJSONArray(result.getResponse().getContentAsString()).toList().size());
    }

    /**
     * /api/v1/supergroup/user/{userID} --> userID = "20:a2f5689e-a5ae-4a9c-a7fd-6ab5b86ffd0f"
     * @throws Exception
     */
    @Test
    public void supergroupSpecificUser() throws Exception {
        MvcResult result = getGET("/api/v1/supergroup/user/20:a2f5689e-a5ae-4a9c-a7fd-6ab5b86ffd0f", "", superToken, status().isOk());
        assertEquals("Chadwick_Weatcroft5348@grannar.com", stringToJSON(result.getResponse().getContentAsString()).getString("email"));

    }


}
