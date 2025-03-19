package uk.ac.standrews.cs.host.cs3099user20.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.implementation.bind.annotation.Super;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import uk.ac.standrews.cs.host.cs3099user20.controller.UserServiceController;
import uk.ac.standrews.cs.host.cs3099user20.database.Database;
import uk.ac.standrews.cs.host.cs3099user20.model.ActiveUserStore;
import uk.ac.standrews.cs.host.cs3099user20.model.Login;
import uk.ac.standrews.cs.host.cs3099user20.model.TestSpecimens;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.ac.standrews.cs.host.cs3099user20.controller.ArticleServiceControllerTest.stringToJSON;

@RunWith(SpringRunner.class)
@WebMvcTest({UserServiceController.class, SupergroupServiceController.class})
public class UserServiceControllerTest {

    Database database = new Database();
    ActiveUserStore userStore = new ActiveUserStore();
    TestSpecimens testSpecimen = new TestSpecimens();

    final String generalToken = "cs3099user20ThisIsSecret";
    final String invalidToken = "cs3099user20ThisIsAnInvalidTokenButItIsSecretStill";
    final String superToken = "cs3099user20ThisIsSecretPlzDontShare:)";


    @Autowired
    private MockMvc mvc;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        database.setUpForTesting();
    }

    //      /api/v1/users/register

    @Test
    public void userRegisterTest() throws Exception {
        MvcResult result = getPOST("/api/v1/users/register", "", generalToken, testSpecimen.testNewLogin(), status().isOk());
        assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    }

    @Test
    public void userRegisterTestAlreadyExists() throws Exception {
        MvcResult result = getPOST("/api/v1/users/register", "", generalToken, testSpecimen.testLogin(), status().isConflict());
        assertFalse(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    }


    @Test
    public void userRegisterTestInsufficientUserInformation() throws Exception {
        Login login = testSpecimen.testLogin();
        login.setEmail(null);
        MvcResult result = getPOST("/api/v1/users/register", "", generalToken, login, status().isConflict());
        assertFalse(Boolean.parseBoolean(result.getResponse().getContentAsString()));

        login = testSpecimen.testLogin();
        login.setPassword(null);
        result = getPOST("/api/v1/users/register", "", generalToken, login, status().isConflict());
        assertFalse(Boolean.parseBoolean(result.getResponse().getContentAsString()));

    }



    //      /api/v1/users/logout

    @Test
    public void userLogoutTest() throws Exception {
        MvcResult result = getPOST("/api/v1/users/logout", "", generalToken, testSpecimen.testUsers(1), status().isOk());
        assertEquals(testSpecimen.testUsers(1).getEmail(), stringToJSON(result.getResponse().getContentAsString()).get("email"));
        assertEquals(testSpecimen.testUsers(1).getUserID(), stringToJSON(result.getResponse().getContentAsString()).get("id"));
    }



    @Test
    public void userLogoutTestDoesNotExist() throws Exception {
        MvcResult result = getPOST("/api/v1/users/logout", "", generalToken, testSpecimen.testFakeUser(), status().isNotFound());
        assertFalse(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    }



    //      /api/v1/users/exists
    @Test
    public void userExistsTest() throws Exception {
        MvcResult result = getPOST("/api/v1/users/exists", "", generalToken, testSpecimen.testUsers(1), status().isOk());
        assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    }

    @Test
    public void userExistsTestDoesNotExist() throws Exception {
        MvcResult result = getPOST("/api/v1/users/exists", "", generalToken, testSpecimen.testFakeUser(), status().isNotFound());
        assertFalse(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    }

    @Test
    public void userExistsInsufficientUserInformation() throws Exception {
        MvcResult result = getPOST("/api/v1/users/exists", "", generalToken, testSpecimen.testNullUser(), status().isNotFound());
        assertFalse(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    }


    //      /api/v1/{userID}/users/apply/reviewer

    @Test
    public void userApplyReviewerTest() throws Exception {
        MvcResult login = getPOST("/api/v1/supergroup/login", "", superToken, testSpecimen.testNewReviewerLogin(), status().isOk());
        MvcResult result = getPOST("/api/v1/admin/users/apply/reviewer", "", generalToken, testSpecimen.testNewReviewers(), status().isOk());
        assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    }

    @Test
    public void userApplyReviewerTestUserDoesNotExist() throws Exception {
        MvcResult result = getPOST("/api/v1/admin/users/apply/reviewer", "", generalToken, testSpecimen.testReviewerNotExists(false), status().isNotFound());
        assertFalse(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    }

    @Test
    public void userApplyReviewerTestUserHasApplied() throws Exception {
        MvcResult result = getPOST("/api/v1/admin/users/apply/reviewer", "", generalToken, testSpecimen.testReviewers(6), status().isConflict());
        assertFalse(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    }

    @Test
    public void userApplyReviewerTestUserIsReviewer() throws Exception {
        MvcResult loginReviewer = getPOST("/api/v1/supergroup/login", "", superToken, testSpecimen.reviewerLogin(), status().isOk());
        MvcResult result = getPOST("/api/v1/admin/users/apply/reviewer", "", generalToken, testSpecimen.testReviewers(1), status().isConflict());
        assertFalse(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    }


    //      /api/v1/users/get/{userID}

    @Test
    public void getUserTest() throws Exception {
        String userID = testSpecimen.testUsers(1).getUserID();
        MvcResult result = getGET("/api/v1/users/get/" + userID, "", generalToken, status().isOk());

    }

    @Test
    public void getUserTestUserDoesNotExist() throws Exception {
        String userID = testSpecimen.testFakeUser().getUserID();
        MvcResult result = getGET("/api/v1/users/get/" + userID, "", generalToken, status().isNotFound());
    }


    //      /api/v1/{userID}/users/get//role

    @Test
    public void getUserRoleTest() throws Exception {
        //Moderator
        String userID = testSpecimen.testModerators(1);
        MvcResult loginModerator = getPOST("/api/v1/supergroup/login", "", superToken, testSpecimen.moderatorLogin(), status().isOk());
        MvcResult result = getGET("/api/v1/" + userID + "/users/get/role", "", generalToken, status().isOk());

        // Reviewer
        userID  = testSpecimen.testReviewers(1).getReviewerID();
        MvcResult loginReviewer = getPOST("/api/v1/supergroup/login", "", superToken, testSpecimen.reviewerLogin(), status().isOk());
        result = getGET("/api/v1/" + userID + "/users/get/role", "", generalToken, status().isOk());

        // General
        userID = testSpecimen.testUsers(12).getUserID();
        MvcResult loginUser = getPOST("/api/v1/supergroup/login", "", superToken, testSpecimen.testLogin(), status().isOk());
        result = getGET("/api/v1/" + userID + "/users/get/role", "", generalToken, status().isOk());
    }

    @Test
    public void getUserRoleTestUserDoesNotExist() throws Exception {
        String userID = testSpecimen.testFakeUser().getUserID();
        MvcResult result = getGET("/api/v1/" + userID + "/users/get/role", "", generalToken, status().isNotFound());
    }


    //      /api/v1/{userID}/users/update/

    @Test
    public void updateUserTest() throws Exception {
        MvcResult loginUser = getPOST("/api/v1/supergroup/login", "", superToken, testSpecimen.testLogin(), status().isOk());
        MvcResult result = getPUT("/api/v1/20:a2f5689e-a5ae-4a9c-a7fd-6ab5b86ffd0f/users/update", "", generalToken, testSpecimen.testNewLogin(), status().isOk());
        //login.setEmail
    }

    //      /api/v1/{userID}/users/update/

    @Test
    public void updateUserTestEmailExists() throws Exception {
        Login login = testSpecimen.testLogin();
        login.setEmail(testSpecimen.testUsers(1).getEmail());
        MvcResult loginUser = getPOST("/api/v1/supergroup/login", "", superToken, testSpecimen.testLogin(), status().isOk());
        MvcResult result = getPUT("/api/v1/20:a2f5689e-a5ae-4a9c-a7fd-6ab5b86ffd0f/users/update", "", generalToken, login, status().isForbidden());
        //login.setEmail
    }






}