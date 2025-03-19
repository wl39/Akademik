package uk.ac.standrews.cs.host.cs3099user20.dao;

import uk.ac.standrews.cs.host.cs3099user20.model.Comment;
import uk.ac.standrews.cs.host.cs3099user20.model.Version;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentRepository {

    public boolean createComment(Comment comment, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO comment (Text, Filepath, VersionID, Author, LineStart, LineEnd) VALUES ('" + comment.getText() + "', '" + comment.getFilename() + "', '" + comment.getVersionID() + "', '" + comment.getUserID() +  "', '" + comment.getLineStart() + "', '" + comment.getLineEnd() + "')");
        statement.close();
        return true;
    }

    public boolean editComment(Comment comment,  Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("UPDATE comment SET comment.Text = '" + comment.getText() + "', comment.LineStart = '"+ comment.getLineStart() +"', comment.LineEnd = '"+ comment.getLineEnd() +"' WHERE (comment.CommentID = '" + comment.getMessageID() + "' AND comment.FilePath = '"+ comment.getFilename() + "' AND comment.VersionID = '"+ comment.getVersionID() + "' AND comment.Author = '"+ comment.getUserID()+"')");
        statement.close();
        return true;
    }

    public Comment[] getComments(int versionID, String filename, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        List<Comment> comments = new ArrayList();
        int messageID;
        String author;
        String email;
        String text;
        String timeStamp;
        int lineStart;
        int lineEnd;
        System.out.println(filename);
        System.out.println(versionID);
        ResultSet rs = statement.executeQuery("SELECT *, DATE_FORMAT(`TimeStamp`, '%Y-%m-%dT%TZ') AS iso FROM comment INNER JOIN user ON comment.Author = user.UserID WHERE Filepath = '" + filename + "' AND VersionID = '" + versionID +  "'");
        while (rs.next()) {
            messageID = rs.getInt("CommentID");
            text = rs.getString("Text");
            author = rs.getString("Author");
            email = rs.getString("Email");
            lineStart = rs.getInt("LineStart");
            lineEnd = rs.getInt("LineEnd");
            timeStamp = rs.getString("iso");

            Comment comment = new Comment(messageID, versionID, filename, author, email,  text, lineStart, lineEnd, timeStamp);
            comments.add(comment);
        }

        Comment[] c = new Comment[comments.size()];
        for(int i = 0; i<comments.size(); i++){
            c[i] = comments.get(i);
        }
        return c;
    }

}