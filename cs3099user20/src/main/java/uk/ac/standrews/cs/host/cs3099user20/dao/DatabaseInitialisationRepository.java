package uk.ac.standrews.cs.host.cs3099user20.dao;

import uk.ac.standrews.cs.host.cs3099user20.model.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitialisationRepository {

    TestSpecimens specimen = new TestSpecimens();

    public void createTables(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE user (UserID varchar(255) NOT NULL PRIMARY KEY UNIQUE, Email varchar(255) NOT NULL UNIQUE)");
        statement.executeUpdate("CREATE TABLE article (ArticleID int(8) NOT NULL AUTO_INCREMENT PRIMARY KEY, Name varchar(255) NOT NULL, Description varchar(1024) NOT NULL, License varchar(255) NOT NULL, Uploader varchar(255) NOT NULL, FOREIGN KEY (Uploader) REFERENCES user(UserID))");
        statement.executeUpdate("CREATE TABLE version (VersionID int(8) NOT NULL AUTO_INCREMENT PRIMARY KEY, ArticleID int(8) NOT NULL, Version int(8) NOT NULL, SubVersion int(8) NOT NULL, TimeStamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY (ArticleID) REFERENCES article(ArticleID))");
        statement.executeUpdate("CREATE TABLE discussionMessage (DiscussionID int(8) NOT NULL AUTO_INCREMENT PRIMARY KEY, ParentID int(8) NOT NULL, Text varchar(255) NOT NULL, Author varchar(255) NOT NULL, VersionID int(8) NOT NULL, TimeStamp time NOT NULL DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY (Author) REFERENCES user(UserID), FOREIGN KEY (VersionID) REFERENCES version(VersionID))");
        statement.executeUpdate("CREATE TABLE authors (AuID int(8) NOT NULL AUTO_INCREMENT PRIMARY KEY, ArticleID int(8) NOT NULL, AuthorName varchar(255) NOT NULL, FOREIGN KEY (ArticleID) REFERENCES article(ArticleID))");
        statement.executeUpdate("CREATE TABLE reviewer (UserID varchar(255) NOT NULL PRIMARY KEY UNIQUE, Authorised BIT NOT NULL, Bio varchar(255) NOT NULL, Institution varchar(255) NOT NULL, Expertise varchar(255) NOT NULL, FOREIGN KEY (UserID) REFERENCES user(UserID))");
        statement.executeUpdate("CREATE TABLE assignment (UserID varchar(255) NOT NULL, ArticleID int(8) NOT NULL, Voted BIT NOT NULL, PRIMARY KEY (UserID, ArticleID), FOREIGN KEY (UserID) REFERENCES reviewer(UserID), FOREIGN KEY (ArticleID) REFERENCES article(ArticleID))");
        statement.executeUpdate("CREATE TABLE comment (CommentID int(8) NOT NULL AUTO_INCREMENT PRIMARY KEY, Text varchar(255) NOT NULL, VersionID int(8) NOT NULL, Filepath varchar(255) NOT NULL, Author varchar(255) NOT NULL, LineStart int NOT NULL, LineEnd int NOT NULL, TimeStamp time NOT NULL DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY (Author) REFERENCES user(UserID))");
        statement.executeUpdate("CREATE TABLE moderators (UserID varchar(255) NOT NULL PRIMARY KEY UNIQUE, FOREIGN KEY (UserID) REFERENCES user(UserID))");
        statement.executeUpdate("CREATE TABLE password (UserID varchar(255) NOT NULL PRIMARY KEY, Password varchar(255) NOT NULL, FOREIGN KEY (UserID) REFERENCES user(UserID))");
        statement.close();
    }

    public void dropAll(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("DROP TABLE IF EXISTS password");
        statement.executeUpdate("DROP TABLE IF EXISTS moderators");
        statement.executeUpdate("DROP TABLE IF EXISTS comment");
        statement.executeUpdate("DROP TABLE IF EXISTS assignment");
        statement.executeUpdate("DROP TABLE IF EXISTS reviewer");
        statement.executeUpdate("DROP TABLE IF EXISTS authors");
        statement.executeUpdate("DROP TABLE IF EXISTS discussionMessage");
        statement.executeUpdate("DROP TABLE IF EXISTS version");
        statement.executeUpdate("DROP TABLE IF EXISTS article");
        statement.executeUpdate("DROP TABLE IF EXISTS user");
        statement.close();
    }

    public void populate(Connection connection) throws Exception {
        populateUsers(connection);
        populateArticles(connection);
        populateDiscussionMessages(connection);
        populateReviewers(connection);
        populateAssignment(connection);
        populateComments(connection);
        populateModerators(connection);
        populatePasswords(connection);
    }

    private void populateUsers(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        for (int i = 1; i < 33; i++) {
            statement.executeUpdate("INSERT INTO user (UserID, Email) VALUES(" +
                    "'" + specimen.testUsers(i).getUserID() + "', '" + specimen.testUsers(i).getEmail() + "');");
        }
        statement.close();
    }

    private void populateArticles(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO article (Name, Description, License, Uploader) VALUES(" +
                "'TESTING', 'TESTING', 'TESTING', '20:6fa8005d-37d9-42eb-a882-952977d2f71b');\n"); // blank article for id
        for (int i = 1; i < 15; i++) {
            Article article = specimen.testArticle(i);
            statement.executeUpdate("INSERT INTO article (Name, Description, License, Uploader) VALUES(" +
                    "'" + article.getName() + "', '" + article.getDescription() +
                    "', '" + article.getLicense() + "', '" + article.getUploaderID()
                    + "');");
            for (Version version:article.getVersions()) {
                statement.executeUpdate("INSERT INTO version (VersionID, ArticleID, Version, SubVersion, TimeStamp) VALUES(" +
                        version.getVersionID() + ", " + article.getId() + ", " + version.getVersion() + ", " + version.getSubVersion() +
                        ", current_timestamp());");
            }
            for (String authorName: article.getAuthorsArray()) {
                statement.executeUpdate("INSERT INTO authors (ArticleID, AuthorName) VALUES(" +
                        article.getId() + ", '" + authorName + "');");
            }
        }
        statement.close();
    }

    private void populateDiscussionMessages(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        for (int i = 1; i < 5; i++) {
            statement.executeUpdate("INSERT INTO discussionMessage (ParentID, `Text`, Author, VersionID)" +
                    "VALUES(" + specimen.testDiscussionMessages(i).getParentID() + ", '" + 
                    specimen.testDiscussionMessages(i).getText() + "', '" +
                    specimen.testDiscussionMessages(i).getUserID() + "', " +
                    specimen.testDiscussionMessages(i).getVersionID()+ ");");
        }
        statement.close();
    }

    private void populateReviewers(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        for (int i = 1; i < 8; i++) {
            statement.executeUpdate("INSERT INTO reviewer (UserID, Authorised, Bio, Institution, Expertise) " +
                    "VALUES('" + specimen.testReviewers(i).getReviewerID() + "', " +
                    specimen.testReviewers(i).getAuthorised() + ", '" +
                    specimen.testReviewers(i).getBio() + "', '" +
                    specimen.testReviewers(i).getInstitution() + "', '" +
                    specimen.testReviewers(i).getExpertise() + "');");
        }
        statement.close();
    }

    private void populateAssignment(Connection connection) throws Exception {
        Statement statement = connection.createStatement();
        for (Assignment assignment : specimen.testAssignments()) {
            statement.executeUpdate("INSERT INTO assignment (UserID, ArticleID, Voted) VALUES('" +
                    assignment.getUserID() + "', " + 
                    assignment.getArticleID() + ", " +
                    assignment.getVoted() + ");");
        }
        statement.close();
    }

    private void populateComments(Connection connection) throws Exception {
        Statement statement = connection.createStatement();
        for (int i = 1; i < 28; i++) {
            statement.executeUpdate("INSERT INTO comment " +
                    "(Filepath, Author, LineStart, LineEnd, `TimeStamp`, `Text`, VersionID) " +
                    "VALUES('" + specimen.comment(i).getFilename() + "', '" +
                    specimen.comment(i).getUserID() + "', " +
                    specimen.comment(i).getLineStart() + ", " +
                    specimen.comment(i).getLineEnd() + ", current_timestamp(), '" +
                    specimen.comment(i).getText() + "', " +
                    specimen.comment(i).getVersionID() + ");");
        }
        statement.close();
    }

    private void populatePasswords(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO password (UserID,Password) VALUES\n" +
                    "\t ('20:01496cee-c8ea-4b07-9688-35c04ab89514','YDA64iuZiGG847KPM+7BvnWKITyGyTwHbb6fVYwRx1I='),\n" +
                    "\t ('20:0f264d7b-2060-4e6e-adea-3bc4ad6dd798','/WGgOvT3fYcPwh4F5+gGeAlcktgIz7O1wnnuBMdKyhM='),\n" +
                    "\t ('20:15b88d9a-68e1-4b6a-9c98-954692f38222','pOYk1obgPtJ2fAq9hcFEJrCxFX0s6B0nu0/k9vAdaIo='),\n" +
                    "\t ('20:1e786274-ff51-459a-94f7-2f1f62ecf0c4','oUDAwe2i3vK4MDY7o2KqTX0lXCYpYFRIIfVW4WZhtv8='),\n" +
                    "\t ('20:2450f138-ed08-420c-bf17-61f40ebbfa18','7Qy5C9+k+TmBp9A8/5khOoaqlqbLz4nsXoiJhx8Ihyc='),\n" +
                    "\t ('20:27ad8496-a243-4246-a155-a990a5c25390','vXyREmSq4Vtm1CkbaFCCmqlphrHT6tNNH9v+8nBWwRI='),\n" +
                    "\t ('20:29f472f2-bcfd-4564-bd7a-92e50983ccbc','H5v+sV/uihDE0HEcfrDAg5YhI+GRjkYbalCOcUbBibI='),\n" +
                    "\t ('20:2aefab22-29a1-43b4-84bb-bbee49c988e9','tEUQNNO2WQBgzpSEoouI3TMqgKIq6OOcnFy3NXqybJ8='),\n" +
                    "\t ('20:2c14456d-bb33-4266-a3d8-8168717cc401','7Cc4/rK7sLx4PrRmeQM5FBY3K6bti43dvrvbN+UQJHM='),\n" +
                    "\t ('20:2ca70ccd-b1b2-415a-8795-5a0c9bcb1656','dE6p7G+gqD6XZLTjI9W+a1WlrM/H/kwI6rao3h/KSFU='),\n" +
                    "\t ('20:38fbd577-d042-4f05-945a-94fbb0f66b02','qY7FxQRIAMiOhi8Ae5jYmBX8QMoVXWznkJUw15LpCc4='),\n" +
                    "\t ('20:45067c5c-c386-47d8-a381-0822700b9642','Fm+3jw9E0nGi2QZScqZ7o3PDJmtZ2FhHwC72la8Mvz8='),\n" +
                    "\t ('20:45113161-8c86-4181-b8df-d83828a72856','QMylzBOr+Rx9WnLArqm8vqQQiUbmfyTAwjADy/MH76I='),\n" +
                    "\t ('20:476857e1-cb39-441d-9896-ce19d9913dca','67ObNCuurXqlLAvNbA1LoGG0Lzqd1rr6JAeglrkbJFA='),\n" +
                    "\t ('20:518f4fb6-0d90-46e1-873e-7f3ee99400c0','j/0GO5Oin4Q4mmNVUnQKnwpyNBaZlBWPsZaS9ZZN1/U='),\n" +
                    "\t ('20:6b381604-2f47-486b-b61f-24820bd46398','gT5B1AkmVnFssLRqHlAChXBmza743s8YKuFavwtDuNU='),\n" +
                    "\t ('20:6f048203-7846-4f16-bf9f-7c7ee16163f4','s8Dl/r4eyIdc1KBvpKmavycN4/Ex2Dpl+JcyLtvBKuw='),\n" +
                    "\t ('20:6fa8005d-37d9-42eb-a882-952977d2f71b','hAsb9VCoc6Hb7ROBq+N5y58edgZ7beVLzTc2fObKPAo='),\n" +
                    "\t ('20:7b4a8fe-722f-42a1-9b9a-b4e67971a5b2','lGzBmIaXkDc82EJM2Qc+nimqoXtvam7FWzgRDK6FY4U='),\n" +
                    "\t ('20:82b631d3-5308-4dec-9e9d-5abdd0526aff','rv1XyMKv2q0KU1KwzocTGoWgj1yHqH8Wbwzh4hP0wP0='),\n" +
                    "\t ('20:a2f5689e-a5ae-4a9c-a7fd-6ab5b86ffd0f','G08OmFGXGZjnMgeFRMlrNsPQHO33yqMyNZ1vHYNWcBQ='),\n" +
                    "\t ('20:b82cda19-e36b-4f63-82ea-e407b15c510a','dZz94mWq3bb3KO0I2Xhiu9m1b9Od6XoEnGQLTFtwqsk='),\n" +
                    "\t ('20:c62c919d-9276-43c5-9f94-d830a73c5516','TnWNR2Cmz/w0fNtF8JZtIPSButgGcxxMDkTyHPnZC7U='),\n" +
                    "\t ('20:cb8e8897-14c1-4ae8-b247-9069be89cd56','ZbRA4uCpIeSprcFERTdNSYqV0F8pnXkVSNaDjrCuZd8='),\n" +
                    "\t ('20:d264cc0f-1f48-446d-b458-ea39e429e30d','A0KED2NA0VaR9L4cDgFX+wmDmSxPQ2wYJn1B2+a7dKI='),\n" +
                    "\t ('20:d5e9e911-a1fd-4620-b3db-12e7f76521bd','6+n5EWUlpmB1FgCn+JffSwPUW138F642qd0zs0+YSbQ='),\n" +
                    "\t ('20:d8f9a5fc-2e5c-4604-ab65-d86433cfaf25','pFxmz8iJFdH8kb+Zjqcms0xTC2OziYT1oPMTdmt5mAg='),\n" +
                    "\t ('20:de351720-c2b7-434b-8f9e-31981b869185','lDhPB5AEPZm3uFyw9Rjszb/fBmtyMk49mWxNDHOLZbU='),\n" +
                    "\t ('20:ed2a3b42-c174-4b9f-861d-07e7d1e9074e','Fd/oQs1etKWRRlyOiSfhbNKxbjyntDEpM9djiC84Jw8='),\n" +
                    "\t ('20:ef9c4bff-1d55-4318-b86e-41813f61320c','mLlkNkFrYO9inr52TcdbstMFIUWCmySqQ6UWiqvwlC0='),\n" +
                    "\t ('20:f1e84b9b-d50a-46ff-8663-83befbe7c1c0','UmGzfZRyjyIf2NJen57/VYQxbYqZA3L+koKqfm1nEOk='),\n" +
                    "\t ('20:fec6b855-53da-45ed-9130-9433ec7c74ea','kdNRDAZyUI+XsA+RflE+TcL1fdblPKuVwo7r3D+IoQg=');");
        statement.close();
        }

    private void populateModerators(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        for (int i = 1; i < 3; i++) {
            statement.executeUpdate("INSERT INTO moderators (UserID) VALUES('" + specimen.testModerators(i) + "');");
        }
        statement.close();
    }

}
