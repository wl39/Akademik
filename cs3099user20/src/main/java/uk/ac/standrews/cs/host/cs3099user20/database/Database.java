package uk.ac.standrews.cs.host.cs3099user20.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.util.FileSystemUtils;
import org.webjars.NotFoundException;
import uk.ac.standrews.cs.host.cs3099user20.controller.SupergroupRequestController;
import uk.ac.standrews.cs.host.cs3099user20.dao.ArticleRepository;
import uk.ac.standrews.cs.host.cs3099user20.dao.DatabaseInitialisationRepository;
import uk.ac.standrews.cs.host.cs3099user20.dao.UserRepository;
import uk.ac.standrews.cs.host.cs3099user20.model.Article;
import uk.ac.standrews.cs.host.cs3099user20.model.Login;
import uk.ac.standrews.cs.host.cs3099user20.model.User;
import uk.ac.standrews.cs.host.cs3099user20.dao.DiscussionRepository;
import uk.ac.standrews.cs.host.cs3099user20.dao.*;
import uk.ac.standrews.cs.host.cs3099user20.model.*;

import java.io.*;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Sets up database for application
 */
public class Database {

    //Database information
    private static String MARIADB_URL;
    private static final String MARIADB_USERNAME = "cs3099user20";
    private static final String MARIADB_PASSWORD = "D85i4SQh.9umXB" +
            "";

    //File Index location
    private final String indexURL = "/Users/lim/Documents/Portfolio/Akademik/Index/";

    public Database() {
        MARIADB_URL = "jdbc:mariadb://localhost:3306/cs3099user20_" + DatabaseSetter.databaseURL;
    }

    Connection connection = null;
    DatabaseInitialisationRepository init = new DatabaseInitialisationRepository();
    ArticleRepository ar = new ArticleRepository();
    UserRepository ur = new UserRepository();
    DiscussionRepository dr = new DiscussionRepository();
    AssignmentRepository asr = new AssignmentRepository();
    CommentRepository cr = new CommentRepository();
    SupergroupRequestController requestController = new SupergroupRequestController();

    /**
     * Starts a connection with the database
     * @return A connection to database
     * @throws SQLException if connection fails
     */
    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection(MARIADB_URL, MARIADB_USERNAME, MARIADB_PASSWORD);
    }

    /**
     * Closes connection to database
     * @param connection if connection successful
     * @throws SQLException if connection fails
     */
    public void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }

    /**
     * Tries to access database for successful access
     * @throws SQLException if connection fails
     */
    public void tryToAccessDB() throws SQLException {
        createConnection();
    }

    /**
     * Sets up database for JUnit tests
     * @throws Exception if setup fails
     */
    public void setUpForTesting() throws Exception {
        Connection connection = createConnection();
        // Drop all tables
        init.dropAll(connection);
        // Create tables
        init.createTables(connection);
        // Populate tables
        init.populate(connection);
    }

    /**
     * Hashes user password
     * @param string User password to be hashed
     * @return Hashed password
     * @throws NoSuchAlgorithmException if SHA-256 algorithm does not exist
     */
    public String sha256(String string) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(string.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }
    //================================================== SUPERGROUP METHODS  ===============================================//

    /**
     * Server obtained from supergroup
     * @param groupNumber Supergroup number
     * @return Group with URL and token for access
     */
    public Group getServer(int groupNumber) {
        Group group = new Group();
        switch(groupNumber) {
            case(20):
//                group.setUrl("https://cs3099user20.host.cs.st-andrews.ac.uk/");
                group.setUrl("http://localhost:23417/");
                group.setToken("cs3099user20ThisIsSecretPlzDontShare:)");
                break;
            case(2):
                group.setUrl("https://cs3099user02.host.cs.st-andrews.ac.uk/");
                group.setToken("NWE0ZGExYTAxZTM2NjU4MjIxNTVmYzkyOTlkNGQ5OGFiMTFiMWI5NDEwY2RmNDhiODM2NGM5NGJhMDM0Mjc3N2E5NDMzNzQyZDM0NDcxNjk0NDU4NzdlYzljMjM3YTZhZDlmOWUxNGMwOGEwMTM2ZjI4MTI0YmM5MTRlZjliYmU0ZTg1NDc4OWY0NDI3YmFjZWM3MDBhMWU4OGNlZTIwZjM1NWFmZTlhZjFhZWEzNzA5ODE1MzVhMmUzZmMyYTE1ZjI3ZWQ3Mjc2ODM2NDcxYzA2ZTRjYjFhMzAzMDIwYWU2ODAxMWFlY2MwZWQ5MzE2NTg1ZTNkNmJmYjM5NjZkMQ==");
                break;
            case(5):
                group.setUrl("https://cs3099user05.host.cs.st-andrews.ac.uk/");
                group.setToken("LSVXQO1-w90P7XHYXdndSNPrUMUPQwiXzyJKdNqpgE5C6U0kZpZzKFk0eAiyHVwOI59M6GoyZSuDYyPNKe8ZYg");
                break;
            case(8):
                group.setUrl("https://cs3099user09.host.cs.st-andrews.ac.uk/");
                group.setToken("JkQb5TA63UGXP7qn6A6VTNy58EXCk2jMG7uo4sr2wYiqvHmVOc8LtUklax0NhDXTfIQbJA69zSFj1ZEKeBd53RMgCyWPnp");
                break;
            case(11):
                group.setUrl("https://cs3099user11.host.cs.st-andrews.ac.uk/");
                group.setToken("WgJbw+rNeA1vQiL5yUlP8C3pScF7sWnJawZrMeH1uQhL4y/lO8B3oSjF6sVmIcG/tWgJ3w+jNeA1nQiD5yUlH8CXpScF/sWfJawZjMez1uQhD4yTlO8BXoSbF6sVfIav");
                break;
            case(13):
                group.setUrl("https://cs3099user13.host.cs.st-andrews.ac.uk/");
                group.setToken("qLRxW6m5/ulgqdyimD0RY2M0C6sUOERFY87qH4IokWQGijkJzi7S33K3byXWEhqoC3IDL/9uNYCb/JI4keDK44JdqiHcxE5HpBuWFjLomuMlHsDpLDSXFz7U8Dzk7DMQqr/ipQf5zIV6k1p+4hZvAVgiTOXgUGlFfKI6yxaO/sU=");
                break;
            case(17):
                group.setUrl("https://cs3099user17.host.cs.st-andrews.ac.uk/");
                group.setToken("H6zVrKm8nsf8OeKzFqivTblb4yJgqrHSCh9oIxNC2aYV8Y9tArpgHaMmXNKlklp9hkMe3VeML3RG7QLMkcKUwuu66pvErVkdqz1pfBPFDTcQAx5fQir72juMylcXXY2C");
                break;
            case(23):
                group.setUrl("https://cs3099user23.host.cs.st-andrews.ac.uk/");
                group.setToken("0OTb5kV+qF9uD/bZ+kNp5vQ+O9PxznwtD9qDtVtQBHul4J+PENURYEQV0tayCISU");
                break;
            case(26):
                group.setUrl("https://cs3099user26.host.cs.st-andrews.ac.uk/");
                group.setToken("Mwjq2CmTcMhQovsBpOUBSCI20VSphI4o6nsaSs3yLeYklFQAKt1D5tklGLSa4svk0LJ8mKQ730YDk8Osme+KceIiJElEsQVH3NmEtU1ySqd0Lt+TUmsNf6ou3JAClcD1yUAbhosbVNnRMEHY0awK9wuJ2Vb7RnthWG4tgZcgQ+Q=");
                break;
            default:
                group.setUrl(null);
                group.setToken(null);
                break;
        }
        return group;
    }

    /**
     * Gets all supergroup users
     * @return array with all supergroup users
     * @throws SQLException if users not obtained
     */
    public User[] getSupergroupUsers() throws SQLException {
        Connection connection = createConnection();
        List<User> userList = ur.getSuperGroupUsers(connection);
        closeConnection(connection);
        return userList.toArray(new User[0]);
    }


    /**
     * Adds group numbers to users, i.e.: 5:UUID to a user in group 5
     * @param groupNumber number to groupify
     * @param user user to groupify
     * @return User with new UUID
     * @throws SQLException if fails in repository
     */
    public User groupify(int groupNumber, User user) throws SQLException {
        if (groupNumber == 20) {
            System.out.println("Groupify skipped");
            return user;
        }
        Connection connection = createConnection();
        String uuid = user.getUserID();
        User ret = new User(groupNumber + ":" + uuid, user.getEmail());
        String idInTable = ur.getID(user.getEmail(), connection);
        System.out.println(idInTable);
        System.out.println(ret.getUserID());
        if(idInTable != null && !idInTable.equals(ret.getUserID())) {
            throw new SQLException("User already exists");
        }
        ur.groupify(ret, connection);
        closeConnection(connection);
        return ret;
    }


    /**
     * Federated Search implementation
     * @param search search query
     * @return SimpleArticle[] list of queries that work
     * @throws IllegalArgumentException if invalid query
     * @throws IOException if group not accessed
     * @throws InterruptedException if network intercepted
     */
    public SimpleArticle[] searchInGroups(Search search) throws IllegalArgumentException, IOException, InterruptedException {
        List<SimpleArticle> ret = new ArrayList<>();

        for (String groupNumber:search.getGroupNumbers()) {
            Group group = getServer(Integer.parseInt(groupNumber));
            group.addAPI("api/v1/supergroup/search");

            // get Organisation object as a json string
            String requestBody = "{\"searchString\":\"" + search.getSearchString() + "\"}";
            //requestBody = Obj.writeValueAsString(search);
            // Displaying JSON String
            System.out.println(requestBody);

            // Send submission

            try {
                // create a client

                HttpResponse<String> response = requestController.postRequest(group, requestBody);
                System.out.println(response);
                if (response.statusCode() == 200) {
                    System.out.println(response.body());
                    JSONArray array = new JSONArray(response.body());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        ret.add(new SimpleArticle(
                                object.get("abstract").toString(),
                                object.get("title").toString(),
                                object.get("articleURL").toString(),
                                object.get("authors").toString()));
                    }
                } else {
                    System.out.println(response.statusCode());
                    // print response body
                    System.out.println(response.body());
                    throw new NotFoundException("Could not send submission");
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Group Not A Part Of the Federation");
            } catch (IOException e) {
                e.printStackTrace();
                throw new IOException("Error getting search objects");
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new InterruptedException("Error getting search objects");
            }
        }
        return ret.toArray(new SimpleArticle[0]);
    }


    /**
     * Gets the group journal of a given journal
     * @param journal journal to look at
     * @return string of group
     */
    public String getGroupNumber(String journal) {
        // assume the journal is of schema:
        String group = "20";
        Pattern p = Pattern.compile("(?:\\D*3099\\D*)(\\d*)(?:\\D*)");
        Matcher m = p.matcher(journal);
        while (m.find()) {
            group = m.group(1);
        }

        System.out.println(group);
        return group;
    }

    //================================================== ARTICLE METHODS  ===============================================//


    /**
     * Creates an article and files from a zip.
     * @param article article
     * @return success
     */
    public int createArticleFromZip(Article article) throws SQLException, FileSystemException {
        if (article.getFiles() == null) {
            throw new FileSystemException("New article must have files");
        }
        int versionID = createArticleBodyOnly(article);
        newArticleToIndex(article.getId());
        newVersionToIndex(article.getFiles().getBase64Value(),
                article.getId(),
                versionID);
        return versionID;
    }

    /**
     * Creates an article body only. Files must be made elsewhere
     * @param article article
     * @return success
     */
    public int createArticleBodyOnly(Article article) throws SQLException, FileSystemException {
        Connection connection = createConnection();
        System.out.println(article.getFiles());
        if(!userExists(getEmail(article.getUploaderID()))){
            throw new SQLException("User does not exist");
        } else if (article.getName() == null || article.getDescription() == null || article.getLicense() == null || article.getUploaderID() == null) {
            throw new SQLException();
        } else if (article.getAuthorsArray().length < 1) {
            throw new NullPointerException();
        }
        int versionID = ar.createArticle(article, connection);
        closeConnection(connection);
        return versionID;
    }


    /**
     * Gets an article
     * @param articleID articleID
     * @return article obtained
     * @throws SQLException if fails
     */
    public Article getArticle(int articleID) throws SQLException {
        Connection connection = createConnection();
        Article article = ar.getArticle(articleID, connection);
        closeConnection(connection);
        return article;
    }

    /**
     * Retrieves articles of a given quantity according to the access rights of the given role
     * @param role the role of the user
     * @param startID starting article ID
     * @param quantity how many to return
     * @return An array of articles
     * @throws SQLException Server Error
     */
    public SimpleArticle[] getArticles(int role, int startID, int quantity, String userID) throws SQLException, IllegalAccessException {
        System.out.println(userID);
        return getArticles(role, startID, quantity, userID, "");
    }

    /**
     * Retrieves articles of a given quantity according to the access rights of the given role
     * @param role the role of the user
     * @param startID starting article ID
     * @param quantity how many to return
     * @return An array of articles
     * @throws SQLException Server Error
     */
    public SimpleArticle[] getArticles(int role, int startID, int quantity, String userID, String subString) throws SQLException, IllegalAccessException {
        SimpleArticle[] ret;
        System.out.println("getArticles");
        System.out.println(userID);
        switch (role){
            // moderator
            case (1):
                System.out.println(isUserModerator(userID));
                if (isUserModerator(userID)) {
                    Connection connection = createConnection();
                    ret = ar.getModeratorArticles(connection);
                    closeConnection(connection);
                } else {
                    throw new IllegalAccessException("User is not a moderator");
                }
                break;

            // uploader
            case (2):
                connection = createConnection();
                ret = ar.getUploaderArticles(userID, connection);
                closeConnection(connection);
                break;

            // reviewer
            case (3):
                connection = createConnection();
                ret = ar.getReviewerArticles(startID, quantity, userID, connection);
                closeConnection(connection);
                break;

            // general
            case (4):
                System.out.println(subString);
                connection = createConnection();
                ret = ar.getArticles(startID, quantity, subString, connection);
                closeConnection(connection);
                break;
            default: throw new IllegalArgumentException("Role does not exist. Usage: 1(mod), 2(uploader), 3(reviewer), 4(general)");
        }
        return ret;
    }


    /**
     * Updates article information
     * @param versionID Article version to update
     * @param article article to update
     * @throws SQLException if fail to access article
     */
    public void updateArticle(int versionID, SimpleArticle article) throws SQLException {
        Connection connection = createConnection();
        int articleID = ar.getArticleID(versionID, connection);
        ar.updateArticle(articleID, article, connection);
        closeConnection(connection);
    }

    /**
     * Downloads an article
     * @param versionID version to download
     * @return a Submission to download
     * @throws SQLException if article not obtained
     */
    public Submission download(int versionID) throws SQLException {
        Connection connection = createConnection();
        int articleID = ar.getArticleID(versionID, connection);
        Article article = getArticle(articleID);
        Version[] versions = article.getVersions();
        List<CodeVersion> codeVersionList = new ArrayList<>();
        for (Version version: versions) {
            codeVersionList.add(new CodeVersion(version.getTime(), getFiles(articleID, version)));
        }
        closeConnection(connection);

        MetaData metaData = new MetaData(versions[0].getTime(), article.getDescription(), article.getLicense(), new String[0], new Author[]{new Author(article.getUploaderID())});
        SuperGroupComment[] superGroupComment = new SuperGroupComment[]{new SuperGroupComment(codeVersionList.get(0).getTimestamp(), article.getUploaderID(), articleID)};
        return new Submission(article.getName(), metaData, codeVersionList.toArray(new CodeVersion[0]), superGroupComment);
    }


    /**
     * Migrates to JSON
     * @param groupNumber group to migrate to
     * @param versionID version to migrate
     * @return true if article migrated, else false
     * @throws Exception if server failure
     */
    public boolean migrateToJournal(String groupNumber, int versionID) throws Exception {
        // Get group url and token
        Group migrationGroup = getServer(Integer.parseInt(groupNumber));
        migrationGroup.addAPI("api/v1/supergroup/submission");

        // Create submission model
        Submission submission = download(versionID);

        // Make JSON

        // Creating Object of ObjectMapper define in Jakson Api
        ObjectMapper Obj = new ObjectMapper();
        String requestBody = "{}";

        try {
            // get Organisation object as a json string
            requestBody = Obj.writeValueAsString(submission);
            // Displaying JSON String
            System.out.println(requestBody);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // Send submission

        try {
            // create a client

            HttpResponse<String> response = requestController.postRequest(migrationGroup, requestBody);

            if (response.statusCode() == 200) {
                return true;
            } else {
                System.out.println(response.statusCode());
                // print response body
                System.out.println(response.body());
                System.out.println(requestBody);
                throw new Exception("Could not send submission");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Could not send submission");
        }
    }


    /**
     * Submission turned to JSON
     * @param submission Turns into JSON
     * @return true if successfully migrated, else false
     * @throws SQLException if not found
     * @throws FileSystemException if file not found
     */
    public boolean migrateArticle(Submission submission) throws SQLException, FileSystemException {
        //create a user --> user groupify!
        Author[] authors = submission.getMetadata().getAuthors();
        Author uploader = authors[0];
        String uploaderGroup = getGroupNumber(uploader.getJournal());
        String uploaderEmail = getAuthorName(uploader);
        User uploaderUser = groupify(Integer.parseInt(uploaderGroup), new User(uploader.getUserId(), uploaderEmail));
        List<String> authorNames = new ArrayList<>(); // get the list of authors
        for (Author author: authors) {
            authorNames.add(getAuthorName(author));
        }

        // first convert to article
        Article article = new Article(0, submission.getName(), submission.getMetadata().getDescription(), authorNames.toArray(new String[0]), submission.getMetadata().getLicense(), uploaderUser.getUserID(), 0, null);
        int versionID = createArticleBodyOnly(article);

        // then get the latest code version to upload to it

        CodeVersion[] codeVersions = submission.getCodeVersions();
        // get top version
        CodeVersion topCodeVersion = codeVersions[0];
        // then add the files
        addFilesFromFileData(article.getId(), versionID, topCodeVersion.getFiles());
        return true;
    }


    //================================================== FILE METHODS  ===============================================//
    /**
     * Initialises the index system
     */
    public void indexSetUp() {
        File directory = new File(indexURL);
        boolean result = FileSystemUtils.deleteRecursively(directory);
        directory.mkdir();
    }


    /**
     * Creates the article's directory
     *
     * @param articleID Id of the created article
     */
    public void newArticleToIndex(int articleID) {
        File indexHead = new File(indexURL + articleID);
        indexHead.mkdir();
    }

    /**
     * Decodes the zip file, writes it to the index, unzips the file, and deletes the original zip
     *
     * @param encodedZIP zip file encoded in base64
     * @param articleID  articleID of the new version
     * @param versionID  versionID of the new version
     * @throws SQLException
     */
    public void newVersionToIndex(String encodedZIP, int articleID, int versionID) throws SQLException {
        String fileLocation = indexURL + articleID + "/" + versionID + "/";
        File indexHead = new File(fileLocation);
        indexHead.mkdir();
        byte[] zip = Base64.getDecoder().decode(encodedZIP.getBytes(StandardCharsets.UTF_8)); // decodes base64 to byte
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fileLocation + "zipFile.zip"), 4096)) { //streams to a file --> change zipFile.zip to filename
            out.write(zip);
            out.close();
            unzipFile(fileLocation);
            File oldZIP = new File(fileLocation + "zipFile.zip");
            oldZIP.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Checks if a filepath exists
     * @param filepath filepath to see if exists
     * @return true if filepath exists, else false
     */
    private boolean checkFilepathExists(String filepath) {
        Path path = Paths.get(filepath);
        return Files.exists(path);
    }

    /**
     * Gets a file directory
     * @param filename file to check
     * @return directory of file in order in array
     * @throws SQLException if file not accessed
     * @throws IOException if file not well built
     */
    public SimpleFile[] getDir(String filename) throws SQLException, IOException {
        Connection connection = createConnection();
        int articleID;
        String filepath;
        String[] pathnames;
        int counter;
        List<String> files = new ArrayList<>();
        List<String> directories = new ArrayList<>();

        //Get article ID
        articleID = getArticleIdFromFilePath(filename, connection);
        closeConnection(connection);

        //Set searchDirectory
        System.out.println(filename);
        System.out.println(articleID);
        filepath = indexURL + articleID + "/" +  filename;
        File searchDirectory = new File(filepath);
        System.out.println(searchDirectory.getCanonicalPath());
        if (!searchDirectory.exists()) {throw new FileNotFoundException("File not found");}
        System.out.println(searchDirectory.getPath());
        //Get files
        pathnames = searchDirectory.list();
        System.out.println("l: " + pathnames.length);

        //Sort Files
        for(String pathname: pathnames) {
            File item = new File(filepath + "/" + pathname);
            if (item.isDirectory()) {
                directories.add(pathname);
            } else {
                files.add(pathname);
            }
        }

        //Create SimpleFile Array
        SimpleFile[] dir = new SimpleFile[directories.size() + files.size()];

        //Add ordered dir to array
        counter = 0;
        for(String ds: directories) {
            dir[counter] = new SimpleFile(ds, true);
            System.out.println("ds: " + ds);
            counter++;
        }

        for(String fs: files) {
            dir[counter] = new SimpleFile(fs, false);
            counter++;
            System.out.println("fs: " + fs);
        }

        //return ordered dir
        return dir;

    }


    /**
     * Gets file from filename
     * @param filename file to obtain
     * @return file path from index
     * @throws SQLException if database not accessed
     * @throws IOException if file not constructed via filepath
     */
    public String getFile(String filename) throws SQLException, IOException {
        Connection connection = createConnection();
        int articleID = getArticleIdFromFilePath(filename, connection);
        String filepath = indexURL + articleID + "/" +  filename;
        File searchDirectory = new File(filepath);
        String ret = Files.readString(Paths.get(filepath), StandardCharsets.US_ASCII);
        if (!searchDirectory.exists()) {throw new NoSuchFileException("File not found");}
        closeConnection(connection);
        return ret;
    }

    /**
     * Gets article ID from a file path
     * @param filepath to obtain article ID from
     * @param connection database connection
     * @return article ID as int
     * @throws SQLException if article not found
     */
    private int getArticleIdFromFilePath(String filepath, Connection connection) throws SQLException {
        String dirs[] = filepath.split("/");
        int versionID = Integer.parseInt(dirs[0]);
        int articleID = ar.getArticleID(versionID, connection);
        closeConnection(connection);

        return articleID;
    }


    //================================================== VERSION METHODS  ===============================================//

    /**
     * Creates a version for an article
     * @param versionID version to create
     * @param encodedZipFile file added to version
     * @throws SQLException if article not found or other repository failures
     */
    public void createVersion(int versionID, FileData encodedZipFile) throws SQLException {
        Connection connection = createConnection();
        Version previousVersion = ar.getVersion(versionID, connection);
        int newVersionID = ar.createVersion(previousVersion, connection);
        newVersionToIndex(encodedZipFile.getBase64Value(), ar.getArticleID(newVersionID, connection), newVersionID);
        closeConnection(connection);
    }


    /**
     * Gets role from version
     * @param versionID version to get user role from
     * @param userID userID
     * @return role of user in given version
     * @throws SQLException if article not found
     */
    public Role getRole(int versionID, String userID) throws SQLException {
        Connection connection = createConnection();
        Role ret = new Role();

        // Case 2: check if they're the article uploader.
        // If so, return; otherwise, call the user role method
        if (ar.isAuthor(versionID, userID, connection)) {
            ret.setUploader();
        } else {
            ret = getRole(userID);
        }
        closeConnection(connection);
        return ret;
    }

    /**
     * Checks if a version exists
     * @param versionID version to check
     * @return true if exists, else false
     * @throws SQLException if article does not exist
     */
    public boolean versionExist(int versionID) throws SQLException {
        Connection connection = createConnection();
        boolean ret = ar.doesVersionExist(versionID, connection);
        closeConnection(connection);
        return ret;
    }




    /**
     * Deletes an article from the index
     * @param versionID version to delete
     * @param connection database connection
     * @throws SQLException if version not found
     * @throws FileSystemException if file cannot be deleted
     */
    private void deleteArticleFromIndex(int versionID, Connection connection) throws SQLException, FileSystemException {
        int articleID = ar.getArticleID(versionID, connection);
        closeConnection(connection);
        File articleFile = new File(indexURL + articleID);
        if(!FileSystemUtils.deleteRecursively(articleFile)){throw new FileSystemException("Article files cannot be deleted");}

    }

    /**
     * Updates a file subversion
     * @param versionID version to be updated
     * @return true if updated, else false
     * @throws SQLException if subversion fails to be accessed
     */
    public boolean updateSubversion(int versionID) throws SQLException {
        Connection connection = createConnection();
        boolean ret;
        int articleID = 0;

        if (versionExist(versionID)) {
            articleID = ar.getArticleID(versionID, connection);
        }

        ret = ar.updateSubversion(articleID, connection);
        closeConnection(connection);

        return ret;
    }

    //================================================== COMMENT METHODS  ===============================================//

    /**
     * Creates a comment
     * @param comment comment to create
     * @return true if comment created, else false
     * @throws SQLException if database not accessed
     * @throws NoSuchFileException if no file to add comment to
     */
    public boolean createComment(Comment comment) throws SQLException, NoSuchFileException {
        Connection connection = createConnection();
        if (!checkFilepathExists(getFilepath(comment, connection))) {
            throw new NoSuchFileException("File not found");
        }
        cr.createComment(comment, connection);
        closeConnection(connection);
        return true;
    }

    /**
     * Edits a comment
     * @param comment edited comment
     * @return true if comment edited, else false
     * @throws SQLException if comment fails to be accessed
     * @throws NoSuchFileException if file where comment is edited does not exist
     */
    public boolean editComment(Comment comment) throws SQLException, NoSuchFileException {
        Connection connection = createConnection();
        if (!checkFilepathExists(getFilepath(comment, connection))) {
            throw new NoSuchFileException("File not found");
        }
        cr.editComment(comment, connection);
        closeConnection(connection);
        return true;
    }

    /**
     * Get all comments
     * @param comment all comments to be obtained
     * @return Comment[] array of all comments
     * @throws SQLException if comment not obtained
     * @throws FileNotFoundException if file where comment exists not found
     */
    public Comment[] getComments(Comment comment) throws SQLException, FileNotFoundException {
        Connection connection = createConnection();
        if (!checkFilepathExists(getFilepath(comment, connection))) {
            throw new FileNotFoundException("File not found");
        }
        Comment[] comments = cr.getComments(comment.getVersionID(), comment.getFilename(), connection);
        closeConnection(connection);
        return comments;
    }

    /**
     * Gets filepath for a given comment
     * @param comment comment to get filepath from
     * @param connection to connect
     * @return Comment filepath
     * @throws SQLException if not obtained
     */
    private String getFilepath(Comment comment, Connection connection) throws SQLException {
        int articleID = ar.getArticleID(comment.getVersionID(), connection);
        String filepath = indexURL + articleID + "/" + comment.getVersionID() + "/" + comment.getFilename();
        System.out.println(filepath);
        return filepath;
    }


    //================================================== DISCUSSION MESSAGE METHODS  ===============================================//

    /**
     * Creates a discussion message
     * @param discussionMessage discussion message to be created
     * @return true if created, else false
     * @throws SQLException if article not found
     */
    public boolean createDiscussion(DiscussionMessage discussionMessage) throws SQLException {
        Connection connection = createConnection();
        boolean ret;
        if (versionExist(discussionMessage.getVersionID())) {
            ret = ar.createDiscussion(discussionMessage, connection);
        } else {
            throw new SQLException();
        }
        closeConnection(connection);
        return ret;
    }


    /**
     * Gets all discussion messages for a version
     * @param versionID version to get all discussion messages for
     * @return DiscussionMessage[] list of discussion messages
     * @throws SQLException if version not accessed
     */
    public DiscussionMessage[] getDiscussionMessages(int versionID) throws SQLException {
        Connection connection = createConnection();
        ar.getArticleID(versionID,connection);
        DiscussionMessage[] discussionMessages = dr.getDiscussionMessages(versionID, connection);
        closeConnection(connection);
        return discussionMessages;
    }

    //================================================== USER METHODS  ===============================================//
    /**
     * Registers a user
     * @param login login
     * @return success
     */
    public boolean registerUser(Login login) throws SQLException, NoSuchAlgorithmException {
        Connection connection = createConnection();
        if (login.getEmail() == null || login.getPassword() == null) throw new SQLException();
        login.setPassword(sha256(login.getPassword()));
        boolean ret = ur.createUser(login, connection);
        closeConnection(connection);
        return ret;
    }

    /**
     * Logs in a user
     * @param login user login
     * @return user if user logged in, else false
     * @throws SQLException if login fails
     * @throws NoSuchAlgorithmException if password hashing fails
     */
    public User login(Login login) throws SQLException, NoSuchAlgorithmException {
        Connection connection = createConnection();
        String userID = ur.getID(login.getEmail(), connection);
        if (ur.checkPassword(userID, sha256(login.getPassword()), connection)) {
            closeConnection(connection);
            return new User(userID, login.getEmail());
        }
        else {
            closeConnection(connection);
            return null;
        }
    }

    /**
     * Logs a user in via supergroup login url
     * @param group supergroup for login url
     * @param login User login
     * @return User if login successful, else false
     * @throws Exception If login fails
     */
    public User loginToURL(Group group, Login login) throws Exception {
        ObjectMapper Obj = new ObjectMapper();
        String requestBody = "{}";

        try {
            // get Organisation object as a json string
            requestBody = Obj.writeValueAsString(login);
            // Displaying JSON String
            System.out.println(requestBody);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            // create a client

            HttpResponse<String> response = requestController.postRequest(group, requestBody);

            if (response.statusCode() == 200) {
                JSONObject object = new JSONObject(response.body());
                return new User(object.get("id").toString(), object.get("email").toString());
            } else if (response.statusCode() == 401) {
                throw new IllegalAccessException("Incorrect Login Credentials");
            } else {
                System.out.println(response.statusCode());
                // print response body
                System.out.println(response.body());
                throw new Exception("Could not send submission");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new IllegalAccessException("Incorrect Login Credentials");
        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Could not send submission");
        }
    }

    /**
     * Checks that user exists
     * @param email user email
     * @return true if user exists
     * @throws SQLException if no email provided
     */
    public boolean userExists(String email) throws SQLException {
        Connection connection = createConnection();
        if (email == null) return false;
        boolean ret = ur.userExists(email, connection);
        closeConnection(connection);
        return ret;
    }

    /**
     * Gets user from userID
     * @param userID UUID
     * @return true if user exists
     * @throws SQLException if no email provided
     */
    public User getUser(String userID) throws SQLException {
        Connection connection = createConnection();
        if (userID == null) return null;
        User ret = ur.getUser(userID, connection);
        closeConnection(connection);
        return ret;
    }

    /**
     * Get email from userID
     * @param userID UUID
     * @return true if user exists
     * @throws SQLException if no email provided
     */
    public String getEmail(String userID) throws SQLException {
        Connection connection = createConnection();
        if (userID == null) return null;
        String ret = ur.getEmail(userID, connection);
        closeConnection(connection);
        return ret;
    }

    /**
     * Gets user role
     * @param userID UUID
     * @return 1 if moderator, 3 if reviewer, 4 is general user
     * @throws SQLException if role not obtained
     */
    public Role getRole(String userID) throws SQLException {
        Connection connection = createConnection();
        int role = ur.getRole(userID, connection);
        Role ret = new Role();
        switch (role) {
            case(1):
                ret.setModerator();
                break;
            case(3):
                ret.setReviewer();
                break;
            case(4):
                ret.setGeneral();
                break;
            default:
                break;
        }
        connection.close();
        return ret;
    }

    /**
     * Edits user email + password
     * @param id UUID
     * @param login user login
     * @return true if info changed, else false
     * @throws SQLException if key constraints found in SQL
     * @throws NoSuchAlgorithmException if password hashing fails
     */
    public boolean editUser(String id, Login login) throws SQLException, NoSuchAlgorithmException {
        Connection connection = createConnection();
        if(login.getPassword() != null){login.setPassword(sha256(login.getPassword()));}
        boolean ret = ur.editUser(id, login, connection);
        connection.close();
        return ret;
    }

    //================================================== MODERATOR METHODS  ===============================================//
    /**
     * Get a list of all authorised reviewers
     * @return list of all reviewers
     * @throws SQLException if fails to be accessed
     */
    public User[] getReviewers() throws SQLException {
        Connection connection = createConnection();
        User[] ret = ar.getAuthorisedReviewers(connection);
        closeConnection(connection);
        return ret;
    }

    /**
     * Gets all unauthorised reviewers
     * @return list of all unauthorised reviewers
     * @throws SQLException if repository not accessed
     */
    public SimpleReviewer[] getUnauthorisedReviewers() throws SQLException {
        Connection connection = createConnection();
        SimpleReviewer[] ret = ar.getUnauthorisedReviewers(connection);
        closeConnection(connection);
        return ret;
    }
    /**
     * Checks to see if a user is a moderator
     * @param userID user's ID
     * @return true/false
     * @throws SQLException
     */
    public boolean isUserModerator(String userID) throws SQLException {
        Connection connection = createConnection();
        boolean ret = ur.isUserModerator(userID, connection);
        closeConnection(connection);
        return ret;
    }

    /**
     * Accepts the applicant as a reviewer
     * @param reviewerDecision {userID: string, reviewerID: string}
     * @return true/false
     * @throws SQLException
     */
    public boolean acceptReviewer(ReviewerDecision reviewerDecision) throws SQLException {
        Connection connection = createConnection();
        if(!userExists(getEmail(reviewerDecision.getReviewerID()))){
            return false;
        }
        boolean ret = ur.acceptReviewer(reviewerDecision, connection);
        closeConnection(connection);
        return ret;
    }

    /**
     * Denies the applicant as a reviewer
     * @param reviewerDecision {userID: string, reviewerID: string}
     * @return true/false
     * @throws SQLException
     */
    public boolean rejectReviewer(ReviewerDecision reviewerDecision) throws SQLException {
        Connection connection = createConnection();
        if(!userExists(getEmail(reviewerDecision.getReviewerID()))){
            return false;
        }
        boolean ret = ur.rejectReviewer(reviewerDecision, connection);
        closeConnection(connection);
        return ret;
    }

    /**
     * Adds reviewers to an article
     * @param versionID version ID
     * @param users reviewers to be added
     * @return true if reviewers added, else otherwise
     * @throws SQLException if repository call fails
     */
    public boolean addReviewers(int versionID, User[] users) throws SQLException {
        Connection connection = createConnection();
        boolean ret = false;
        int articleID = 0;
        if (versionExist(versionID)) {
            articleID = ar.getArticleID(versionID, connection);
        }
        for (User user: users) { // need to check that all users exist before adding any to the database
            if(!userExists(getEmail(user.getUserID()))){
                return false;
            }
        }
        for (User user: users) {
            System.out.println(user.getUserID());
            ret = ar.addReviewer(articleID, user, connection);
        }

        updateSubversion(versionID);
        closeConnection(connection);

        return ret;
    }

    /**
     * Allows a moderator to reject an article
     * @param versionID version to reject
     * @return true if rejected, else false
     * @throws SQLException if article not accessed
     * @throws FileSystemException if files not found
     */
    public boolean rejectArticle(int versionID) throws SQLException, FileSystemException {
        Connection connection = createConnection();
        boolean ret = false;
        int articleID = 0;
        if (versionExist(versionID)) {
            articleID = ar.getArticleID(versionID, connection);
        }
        deleteArticleFromIndex(versionID, connection);
        if(!ar.removeRelatedData(versionID, connection) ) {
            System.out.println("ERROR IN REMOVE");
        }

        ret = ar.rejectArticle(articleID, connection);
        closeConnection(connection);

        return ret;
    }
    //================================================== REVIEWER METHODS  ===============================================//

    /**
     * Gets a user to apply to be a reviewer
     * @param reviewer user reviewer info
     * @return true if user applies to be a reviewer, false otherwise
     * @throws SQLException if fails
     */
    public boolean reviewerApply(Reviewer reviewer) throws SQLException {
        Connection connection = createConnection();
        if (reviewer.getBio().isEmpty() || reviewer.getExpertise().isEmpty() || reviewer.getInstitution().isEmpty()) {
            throw new IllegalArgumentException("An input attribute is empty");
        }
        boolean ret = ur.reviewerApply(reviewer, connection);
        closeConnection(connection);
        return ret;
    }

    /**
     * Allows for reviewers to vote on an article
     * @param versionID version to vote on
     * @param user user being voted on
     * @param vote Accept/reject version
     * @throws SQLException if version not accessed
     */
    public void vote(int versionID, User user, int vote) throws SQLException {
        Connection connection = createConnection();
        int articleID = ar.getArticleID(versionID, connection);
        System.out.println(user.getUserID());
        asr.vote(articleID, versionID, user.getUserID(), vote, connection);
        closeConnection(connection);
    }


    /**
     * Gets user supergroup method and return the author's email address
     * @param author author
     * @return author email address
     */
    public String getAuthorName(Author author) {

        try {
            Group group = getServer(Integer.parseInt(getGroupNumber(author.getJournal())));
            group.addAPI("api/v1/supergroup/user/" + author.getUserId());

            // use the client to send the request
            HttpResponse<String> response = requestController.getRequest(group);

            // return email
            JSONObject object = new JSONObject(response.body());
            return object.get("email").toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

    }

    // ============================================ FILE METHODS =================================================== //

    /**
     * Gets all files from an article
     * @param articleID article to get files from
     * @param version version to get files from
     * @return array of files
     */
    public FileData[] getFiles(int articleID, Version version) {
        int currentVersionID = version.getVersionID();
        System.out.println(articleID);
        System.out.println(currentVersionID);
        String currentFile = indexURL + articleID + "/" + currentVersionID + "/";
        List<FileData> files = new ArrayList<>();

        try (Stream<Path> fileWalker = Files.walk(Paths.get(currentFile))) {
            List<String> allFiles = fileWalker.filter(Files::isRegularFile)
                    .map(Path::toString).collect(Collectors.toList());

            for (String f : allFiles) {
                File file = new File(f);
                byte[] fileContent = Files.readAllBytes(file.toPath());
                System.out.println(f);
                System.out.println(Arrays.toString(fileContent));
                String base64Encoding = Base64.getEncoder().encodeToString(fileContent);
                f = f.replaceAll(currentFile, "");
                String mime = Files.probeContentType(file.toPath());
                files.add(new FileData(f, file.isDirectory(), base64Encoding, (mime == null) ?"" : mime));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files.toArray(new FileData[0]);
    }


    /**
     * unzips the zip file
     *
     * @param fileLocation location of the zip file
     * @throws IOException if zip fails
     */
    public static void unzipFile(String fileLocation) throws IOException {
        Path source = Paths.get(fileLocation + "zipFile.zip");
        Path target = Paths.get(fileLocation);

        FileInputStream fis = new FileInputStream(source.toFile());
        try (ZipInputStream zis = new ZipInputStream(fis)) {
            ZipEntry zipEntry = zis.getNextEntry();

            while (zipEntry != null) {

                boolean isDirectory = false;
                if (zipEntry.getName().endsWith(File.separator)) {
                    isDirectory = true;
                }

                Path targetDirResolved = target.resolve(zipEntry.getName());
                Path newPath = targetDirResolved.normalize();

                if (isDirectory) {
                    Files.createDirectories(newPath);
                } else {
                    if (newPath.getParent() != null) {
                        if (Files.notExists(newPath.getParent())) {
                            Files.createDirectories(newPath.getParent());
                        }
                    }
                    Files.copy(zis, newPath, StandardCopyOption.REPLACE_EXISTING);
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
        }
    }


    /**
     * Adds files to articles from fileData
     * @param articleID article to add files to
     * @param versionID version to add files to
     * @param files files to add
     */
    private void addFilesFromFileData(int articleID, int versionID, FileData[] files) {
        for (FileData fileData: files) {
            String indexPath = indexURL + articleID + "/" + versionID + "/";
            String filepath = Paths.get(indexPath + fileData.getFilename()).getParent().toString();
            System.out.println(filepath);
            File file = new File(filepath);
            file.mkdirs();
            byte[] fileBytes = Base64.getDecoder().decode(fileData.getBase64Value().getBytes(StandardCharsets.UTF_8)); // decodes base64 to byte
            System.out.println(fileData.getFilename());
            try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(indexPath + fileData.getFilename()), 4096)) { //streams to a file
                System.out.println(Arrays.toString(fileBytes));
                out.write(fileBytes);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }





}
