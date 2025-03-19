package uk.ac.standrews.cs.host.cs3099user20.dao;

import uk.ac.standrews.cs.host.cs3099user20.model.*;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ArticleRepository {

    public int createArticle(Article article, Connection connection) throws SQLException {
        // Adds main information and set the articleID
        createArticleBody(article, connection);

        // Add its authors
        createAuthors(article.getId(), article.getAuthorsArray(), connection);
        return createVersion0(article.getId(), connection);
    }

    public SimpleArticle[] getModeratorArticles(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(
                "SELECT * \n" +
                "FROM article \n" +
                "WHERE ArticleID IN (\n" +
                "\tSELECT ArticleID \n" +
                "\tFROM version \n" +
                "\tWHERE SubVersion = 0 \n" +
                "\tAND Version = 0)");
        SimpleArticle[] ret = getSimpleArticles(connection, rs);
        statement.close();
        return ret;
    }

    public SimpleArticle[] getUploaderArticles(String userID, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(
                "SELECT * \n" +
                "FROM article \n" +
                "WHERE ArticleID IN (\n" +
                "\tSELECT ArticleID \n" +
                "\tFROM article\n" +
                "\tWHERE Uploader = '" + userID +"')");
        SimpleArticle[] ret = getArticlesWithVersions(connection, rs);
        statement.close();
        return ret;
    }

    public SimpleArticle[] getReviewerArticles(int startID, int quantity, String userID, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM article\n" +
                "WHERE ArticleID IN\n" +
                "(SELECT DISTINCT ArticleID\n" +
                "FROM version v\n" +
                "WHERE v.ArticleID IN\n" +
                "(SELECT ArticleID FROM `assignment` a\n" +
                "WHERE UserID = '" + userID + "' AND a.Voted = 0)\n" +
                "AND v.SubVersion > 0\n" +
                "ORDER BY v.VersionID DESC)\n");
        SimpleArticle[] ret = getSimpleArticles(connection, rs);
        statement.close();
        return ret;
    }

    public SimpleArticle[] getArticles(int startID, int quantity, String subString, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        subString = subString.toLowerCase();
        ResultSet rs = statement.executeQuery("SELECT\n" +
                "\t * \n" +
                "FROM\n" +
                "\tarticle\n" +
                "WHERE\n" +
                "LOWER(Name) LIKE '%" + subString +  "%' AND" +
                "\tArticleID IN (\n" +
                "\tSELECT\n" +
                "\t\tDISTINCT ArticleID\n" +
                "\tFROM\n" +
                "\t\tversion v\n" +
                "\tWHERE\n" +
                "\t\t v.Version > 0\n" +
                "\t\tAND v.SubVersion = 0\n" +
                "\t\tAND ArticleID  >= " + startID + "\n" +
                "\tORDER BY\n" +
                "\t\t v.VersionID DESC) LIMIT " + quantity);
        SimpleArticle[] ret = getSimpleArticles(connection, rs);
        statement.close();
        return ret;
    }

    private SimpleArticle[] getSimpleArticles(Connection connection, ResultSet rs) throws SQLException {
        List<SimpleArticle> ret = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("ArticleID");
            System.out.println(id + "Moo");
            ret.add(new SimpleArticle(
                    id,
                    rs.getString("Name"),
                    rs.getString("Description"),
                    rs.getString("License"),
                    getAuthors(id, connection)
            ));
        }

        return ret.toArray(new SimpleArticle[0]);
    }

    private Article[] getArticlesWithVersions(Connection connection, ResultSet rs) throws SQLException {
        List<Article> ret = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("ArticleID");
            System.out.println(id + "Moo");
            Version[] versions = getVersions(id, connection);
            ret.add(new Article(
                    id,
                    rs.getString("Name"),
                    rs.getString("Description"),
                    getAuthors(id, connection),
                    rs.getString("License"),
                    rs.getString("Uploader"),
                    versions[versions.length - 1].getVersionID(),
                    versions

            ));
        }

        return ret.toArray(new Article[0]);
    }

    public int getArticleID(int versionID, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT ArticleID FROM version WHERE VersionID = '" + versionID + "'");
        if (!rs.next()) {
            throw new SQLException("Article not found");

        }

        statement.close();
        return rs.getInt("ArticleID");
    }

    /**
     * Creates the main article information record
     * @param article article
     * @param connection connection
     * @return successful?
     * @throws SQLException exception
     */
    private void createArticleBody(Article article, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        boolean ret = false;
        statement.executeUpdate("INSERT INTO article (Name, Description, License, Uploader) VALUES ('" + article.getName() + "', '" + article.getDescription() + "', '" + article.getLicense() + "', '" + article.getUploaderID() + "')");
        ResultSet rs = statement.executeQuery("SELECT LAST_INSERT_ID() AS lastID FROM article");
        while (rs.next()){
            article.setId(rs.getInt("lastID"));
            ret = true;
        }
        if(!ret) {throw new SQLException("Article not created");}
        statement.close();
    }

    private void createAuthors(int articleID, String[] authors, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        for (String authorName: authors){
            statement.executeUpdate("INSERT INTO authors (ArticleID, AuthorName) VALUES ('" + articleID + "', '" + authorName + "')");
        }
        statement.close();
    }

    private int createVersion0(int articleID, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        int versionID = 0;
        statement.executeQuery("INSERT INTO version (ArticleID, Version, SubVersion) VALUES ('" + articleID + "', 0,0)");
        ResultSet rs = statement.executeQuery("SELECT LAST_INSERT_ID() AS lastID FROM article");
        if(rs.next()) {
            versionID = rs.getInt("lastID");
        }
        return versionID;
    }


    public int createVersion(Version previousVersion, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs;
        int newVersionID = 0;
        int newSub = previousVersion.getSubVersion() + 1;

        int articleID = getArticleID(previousVersion.getVersionID(), connection);
        statement.executeQuery("INSERT INTO version (ArticleID, Version, SubVersion) VALUES ('" + articleID + "', '" + previousVersion.getVersionID() + "', '" + newSub + "')");

        rs = statement.executeQuery("SELECT LAST_INSERT_ID() AS lastID FROM version");
        while (rs.next()) {
            newVersionID = rs.getInt("lastID");
        }

        statement.close();
        return newVersionID;
    }

    public Version getVersion(int versionID, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs;

        rs = statement.executeQuery("SELECT *, DATE_FORMAT(`TimeStamp`, '%Y-%m-%dT%TZ') AS iso FROM version WHERE VersionID = '" + versionID + "'");
        if (!rs.next()) {
            throw new SQLException("Article not found");
        }
            int version = rs.getInt("Version");
            int subVersion = rs.getInt("SubVersion");
            String time = rs.getString("iso");
            Version retrievedVersion = new Version(versionID, version, subVersion, time);

            statement.close();
            return retrievedVersion;
        }


    public Article getArticle(int articleID, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM article WHERE ArticleID = '" + articleID + "'");
        if(!rs.next()){throw new SQLException("Article not found");}
            String name = rs.getString("Name");
            String description = rs.getString("Description");
            String license = rs.getString("License");
            String uploaderID = rs.getString("Uploader");

        Version[] versions = getVersions(articleID, connection);
        Article article = new Article(articleID, name, description, getAuthors(articleID, connection), license, uploaderID, versions[versions.length-1].getVersionID(), versions);

        statement.close();
        return article;

    }

    private String[] getAuthors(int articleID, Connection connection) throws SQLException {
        List<String> authors = new ArrayList();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT AuthorName FROM authors WHERE ArticleID = '" + articleID + "'");
        while(rs.next()){
            authors.add(rs.getString("AuthorName"));
        }
        statement.close();
        return authors.toArray(new String[0]);
    }

    private Version[] getVersions(int articleID, Connection connection) throws SQLException {
        List<Version> versions = new ArrayList();
        Statement statement = connection.createStatement();
        boolean error = false;
        ResultSet rs = statement.executeQuery("SELECT *, DATE_FORMAT(`TimeStamp`, '%Y-%m-%dT%TZ') AS iso FROM version WHERE ArticleID = '" + articleID +"'");
        while(rs.next()){
            int versionID = rs.getInt("VersionID");
            int version = rs.getInt("Version");
            int subVersion = rs.getInt("SubVersion");
            String time = rs.getString("iso");
            versions.add(new Version(versionID, version, subVersion, time));
            error = true;
        }
        if(!error) {throw new SQLException("Article missing versions");}
        Version[] v = new Version[versions.size()];
        for(int i = 0; i<versions.size(); i++){
            v[i] = versions.get(i);
        }

        statement.close();
        return v;
    }



    public void updateArticle(int articleID, SimpleArticle article, Connection connection) throws SQLException {
        if (article.getName() == null && article.getDescription() == null && article.getLicense() == null && article.getAuthorsArray() == null) {throw new SQLException("Update empty");}

        if (article.getName() != null) {
            updateArticleName(articleID, article.getName(), connection);
        }
        if (article.getDescription() != null) {
            updateArticleDescription(articleID, article.getDescription(), connection);
        }
        if (article.getLicense() != null) {
            updateArticleLicense(articleID, article.getLicense(), connection);
        }
        if(article.getAuthorsArray() != null) {
            createAuthors(articleID, article.getAuthorsArray(), connection);
        }
    }

    private void updateArticleName(int articleID, String name, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeQuery("UPDATE article SET article.Name = '" + name + "' WHERE article.ArticleID = '" + articleID + "'");
        statement.close();
    }

    private void updateArticleDescription(int articleID, String description, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeQuery("UPDATE article SET article.Description = '" + description + "' WHERE article.ArticleID = '" + articleID + "'");
        statement.close();
    }

    private void updateArticleLicense(int articleID, String license, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeQuery("UPDATE article SET article.License = '" + license + "' WHERE article.ArticleID = '" + articleID + "'");
        statement.close();
    }

    /**
     * Creates a discussion message record
     * @param message discussionMessage
     * @param connection connection
     * @return successful?
     * @throws SQLException
     */
    public boolean createDiscussion(DiscussionMessage message, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        boolean ret = false;
        //statement.executeUpdate("INSERT INTO discussionMessage (ParentID, Text, Author, VersionID) VALUES (" + message.getParentID() + ", '" + message.getText() + "', '4', 1)");
        statement.executeUpdate("INSERT INTO discussionMessage (ParentID, Text, Author, VersionID) VALUES ('" + message.getParentID() + "', '" + message.getText() + "', '" + message.getUserID() + "', '" + message.getVersionID() + "')");
        ResultSet rs = statement.executeQuery("SELECT LAST_INSERT_ID() AS lastID FROM discussionMessage");
        if (rs.next()){
            message.setMessageID(rs.getInt("lastID"));
            ret = true;
        }
        statement.close();
        return ret;
    }

    public boolean isAuthor(int versionID, String userID, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        boolean ret = false;
        ResultSet rs = statement.executeQuery("SELECT * FROM version version INNER JOIN article article ON version.ArticleID = article.ArticleID WHERE version.VersionID = " + versionID + " AND article.Uploader = " + userID);
        if (rs.next()){
            ret = true;
        }
        statement.close();
        return ret;
    }

    public boolean doesVersionExist(int versionID, Connection connection) throws SQLException {
        boolean ret = false;
        Statement statement = connection.createStatement();
        System.out.println(versionID);
        ResultSet rs = statement.executeQuery("SELECT * FROM version version WHERE version.VersionID = " + versionID);
        if (rs.next()) {
            ret = true;
        }
        statement.close();
        return ret;
    }

    public boolean addReviewer(int articleID, User user, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        boolean ret = false;
        System.out.println(articleID);
        System.out.println(user.getUserID());
        statement.executeUpdate("INSERT INTO assignment (UserID, ArticleID, Voted) VALUES('" + user.getUserID() + "', " + articleID + ", 0)");
        ResultSet rs = statement.executeQuery("SELECT ArticleID FROM assignment WHERE UserID='"+ user.getUserID() + "'");
        while (rs.next()){
            System.out.println(articleID + " : " + rs.getString("ArticleID"));
            if (Objects.equals(rs.getString("ArticleID"), Integer.toString(articleID))) {
                System.out.println("is  this working");
                ret = true;
            }
        }
        statement.close();
        return ret;
    }

    public boolean rejectArticle(int articleID, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();


        statement.executeUpdate("DELETE FROM authors WHERE ArticleID = '"+ articleID +"'"); //authors
        statement.executeUpdate("DELETE FROM article WHERE ArticleID = '"+ articleID +"'");
        return true;
    }

    public boolean removeRelatedData(int versionID, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();


        statement.executeUpdate("DELETE FROM comment WHERE VersionID = '"+ versionID +"'"); //authors
        statement.executeUpdate("DELETE FROM discussionMessage WHERE VersionID = '"+ versionID +"'"); //discussion
        statement.executeUpdate("DELETE FROM version WHERE VersionID = '"+ versionID +"'"); //comment

        return true;
    }

    public boolean updateSubversion(int articleID, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        boolean ret = true;
        statement.executeUpdate("UPDATE version SET Subversion = 1 WHERE ArticleID = '"+ articleID + "'");
        statement.close();
        return ret;
    }

    public User[] getAuthorisedReviewers(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        List<User> ret = new ArrayList<>();
        ResultSet rs = statement.executeQuery("SELECT * FROM reviewer reviewer INNER JOIN user user ON reviewer.UserID = user.UserID WHERE reviewer.Authorised = 1 ");
        while (rs.next()){
            ret.add(new User(rs.getString("UserID"), rs.getString("Email")));
        }
        statement.close();
        return ret.toArray(new User[0]);
    }

    public SimpleReviewer[] getUnauthorisedReviewers(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        List<SimpleReviewer> ret = new ArrayList<>();
        ResultSet rs = statement.executeQuery("SELECT * FROM reviewer reviewer INNER JOIN user user ON reviewer.UserID = user.UserID WHERE reviewer.Authorised = 0 ");
        while (rs.next()){
            ret.add(new SimpleReviewer(rs.getString("UserID"), rs.getString("Email"), rs.getString("Bio"), rs.getString("Institution"), rs.getString("Expertise")));
        }
        statement.close();
        return ret.toArray(new SimpleReviewer[0]);
    }
}

