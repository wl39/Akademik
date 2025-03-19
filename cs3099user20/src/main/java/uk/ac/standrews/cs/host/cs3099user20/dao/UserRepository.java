package uk.ac.standrews.cs.host.cs3099user20.dao;

import uk.ac.standrews.cs.host.cs3099user20.model.Login;
import uk.ac.standrews.cs.host.cs3099user20.model.Reviewer;
import uk.ac.standrews.cs.host.cs3099user20.model.User;
import uk.ac.standrews.cs.host.cs3099user20.model.ReviewerDecision;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepository {
    /**
     * Creates a new user and puts the user in the database
     * @param login User Login
     * @param connection Connection to Database
     * @return true if user is successfully created (else SQLException is thrown)
     * @throws SQLException if Database access error or SQL badly constructed
     */
    public boolean createUser(Login login, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        UUID userID =  UUID.randomUUID();
        statement.executeUpdate("INSERT into user (UserID, Email) VALUES ('20:" + userID + "','" + login.getEmail() + "')");
        setPassword(userID, login, connection);
        statement.close();
        return true;
    }

    private void hashPassword(Login login) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        KeySpec spec = new PBEKeySpec(login.getPassword().toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = factory.generateSecret(spec).getEncoded();
    }

    /**
     * Puts password in password table for user
     * @param userID UUID
     * @param login user Login
     * @param connection Connection to database
     * @throws SQLException if connection fails or SQL syntax is incorrect
     */
    private void setPassword(UUID userID, Login login, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO password (UserID, Password) VALUES ('20:" + userID + "','" + login.getPassword() + "')");
        statement.close();
    }


    /**
     * Checks if user exists from their e-mail.
     * @param email User email
     * @param connection Connection to database
     * @return true if user exists, false if they do not.
     * @throws SQLException if connection fails or SQL syntax is incorrect
     */

    public boolean userExists(String email, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM user WHERE email = '" + email + "'");
        boolean ret = rs.next();
        statement.close();
        return ret;
    }

    /**
     * Gets a user from their user ID.
     * @param userID User ID
     * @param connection Connection to database
     * @return user.
     * @throws SQLException if connection fails or SQL syntax is incorrect
     */
    public User getUser(String userID, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        User user = new User();
        user.setUserID(userID);
        ResultSet rs = statement.executeQuery("SELECT Email FROM user WHERE UserID = '" + userID + "'");
        if (rs.next()) user.setEmail(rs.getString("Email"));
        statement.close();
        return user;
    }

    /**
     * Gets email from user ID.
     * @param userID User email
     * @param connection Connection to database
     * @return user email
     * @throws SQLException if connection fails or SQL syntax is incorrect
     */
    public String getEmail(String userID, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String email = null;
        ResultSet rs = statement.executeQuery("SELECT Email FROM user WHERE UserID = '" + userID + "'");
        if (rs.next()) email = rs.getString("Email");
        statement.close();
        return email;
    }

    /**
     * Gets user ID from email.
     * @param email User email
     * @param connection Connection to database
     * @return user ID
     * @throws SQLException if connection fails or SQL syntax is incorrect
     */
    public String getID(String email, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String userID = null;
        ResultSet rs = statement.executeQuery("SELECT UserID FROM user WHERE Email = '" + email + "'");
        if (rs.next()) userID = rs.getString("UserID");
        statement.close();
        return userID;
    }

    /**
     * Checks if a user's password is correct.
     * @param userID User email
     * @param connection Connection to database
     * @return true if password is correct, else false
     * @throws SQLException if connection fails or SQL syntax is incorrect
     */
    public boolean checkPassword(String userID, String password, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        boolean ret;
        ResultSet rs = statement.executeQuery("SELECT * FROM password WHERE UserID = '" + userID + "'");
        System.out.println(password);
        ret = rs.next() && userID.equals(rs.getString("UserID")) && password.equals(rs.getString("Password"));
        statement.close();
        return ret;
    }

    /**
     * Applies a user to be a reviewer.
     * @param reviewer Reviewer info
     * @param connection Connection to database
     * @return true if user successfully applies, false otherwise
     * @throws SQLException if connection fails or SQL syntax is incorrect
     */

    public boolean reviewerApply(Reviewer reviewer, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        if (this.isReviewer(reviewer.getReviewerID(), 0, connection) || this.isReviewer(reviewer.getReviewerID(), 1, connection)) {
            statement.close();
            return false;
        }
        statement.executeUpdate("INSERT INTO reviewer (UserID, Authorised, Bio, Institution, Expertise) VALUES ('" + reviewer.getReviewerID() + "',0, '" + reviewer.getBio() + "','" + reviewer.getInstitution() + "','" + reviewer.getExpertise() + "')");
        statement.close();
        return true;
    }

    /**
     * Gets a user role.
     * @param userID User ID
     * @param connection Connection to database
     * @return 1 if user is a moderator; 2 if user is a reviewer; else 4 (user is a general user)
     * @throws SQLException if connection fails or SQL syntax is incorrect
     */
    public int getRole(String userID, Connection connection) throws SQLException {
        if (this.isModerator(userID, connection)) return 1;
        else if(this.isReviewer(userID, 1, connection)) return 3;
        else return 4;
    }

    /**
     * Checks if a user is a moderator.
     * @param userID User ID
     * @param connection Connection to database
     * @return true if user is moderator, false otherwise
     * @throws SQLException if connection fails or SQL syntax is incorrect
     */
    public boolean isModerator(String userID, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT UserID FROM moderators WHERE userID = '" + userID +"'");
        statement.close();
        return rs.next();
    }

    /**
     * Checks if a user is a reviewer.
     * @param userID User ID
     * @param connection Connection to database
     * @return true if user is a reviewer, false otherwise
     * @throws SQLException if connection fails or SQL syntax is incorrect
     */
    private Boolean isReviewer(String userID, int authorised, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT UserID FROM reviewer WHERE userID = '" + userID +"'  AND authorised =" + authorised);
        statement.close();
        return rs.next();
    }

    /**
     * Edits a user's information. Both email and password can be edited
     * @param userID User ID
     * @param login Email/password combo to change
     * @param connection Connection to database
     * @return true if email or password edited, else false
     * @throws SQLException if connection fails or SQL syntax is incorrect
     */
    public boolean editUser(String userID, Login login, Connection connection) throws SQLException {
        boolean emailEdited = false;
        boolean passwordEdited = false;
        if (login.getEmail() != null) {
            emailEdited = this.editEmail(userID, login.getEmail(), connection);
        }
        if (login.getPassword() != null) {
            passwordEdited = this.editPassword(userID, login.getPassword(), connection);
        }
        return (emailEdited || passwordEdited);
    }


    /**
     * Edits a user's email address.
     * @param userID User ID
     * @param connection Connection to database
     * @return true if email edited, false otherwise
     * @throws SQLException if connection fails or SQL syntax is incorrect
     */
    private boolean editEmail(String userID, String email, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("UPDATE user SET Email = '" + email + "' WHERE userID = '" + userID + "'");
        statement.close();
        return rs.next();
    }

    /**
     * Edits a user's password.
     * @param userID User ID
     * @param connection Connection to database
     * @return true if password edited, false otherwise
     * @throws SQLException if connection fails or SQL syntax is incorrect
     */
    private boolean editPassword(String userID, String password, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("UPDATE password SET Password = '" + password + "' WHERE userID = '" + userID + "'");
        statement.close();
        return rs.next();
    }

    /**
     * I'm leaving this one to Jon
     **/
    public List<User> getSuperGroupUsers(Connection connection) throws SQLException {
        List<User> userList = new ArrayList<User>();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * from user WHERE UserID LIKE '%20:%'");
        while (rs.next()) {
            User user = new User(rs.getString("UserID"), rs.getString("Email"));
            userList.add(user);
        }
        statement.close();
        return userList;
    }

    public boolean isUserModerator(String userID, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        boolean ret = false;
        System.out.println("isUser");
        System.out.println(userID);
        ResultSet rs = statement.executeQuery("SELECT UserID FROM moderators WHERE UserID='"+ userID + "'");
        if (rs.next()){
            ret = true;
        }
        statement.close();
        return ret;
    }

    /**
     * Accepts a user to be a reviewer.
     * @param reviewerDecision Pair of a Moderator ID and a Reviewer ID
     * @param connection Connection to database
     * @return true if password edited, false otherwise
     * @throws SQLException if connection fails or SQL syntax is incorrect
     */
    public boolean acceptReviewer(ReviewerDecision reviewerDecision, Connection connection) throws SQLException {
        return updateReviewerStatus(reviewerDecision.getReviewerID(), 1, connection);
    }

    public boolean rejectReviewer(ReviewerDecision reviewerDecision, Connection connection) throws SQLException {
        return deleteReviewers(reviewerDecision.getReviewerID(), connection);
    }

    private boolean deleteReviewers(String reviewerID, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM reviewer WHERE UserID='"+ reviewerID +"'");
        return true;
    }

    private boolean updateReviewerStatus(String reviewerID, int state, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        boolean ret = false;
        statement.executeUpdate("UPDATE reviewer SET Authorised=" + state + " WHERE UserID='"+ reviewerID + "'");
        ResultSet rs = statement.executeQuery("SELECT Authorised FROM reviewer WHERE UserID='"+ reviewerID + "'");
        if (rs.next()){
            if (rs.getInt("Authorised") == state) {
                ret = true;
            }
        }
        statement.close();
        return ret;
    }

    public void groupify(User user, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        System.out.println("Account created: " + user.getUserID() + ", " + user.getEmail());
        statement.executeUpdate("INSERT INTO user (UserID, Email ) VALUES('" + user.getUserID() +"', '" + user.getEmail() +"') ON DUPLICATE KEY UPDATE Email = '" + user.getEmail() + "'");
        statement.close();
    }
}
