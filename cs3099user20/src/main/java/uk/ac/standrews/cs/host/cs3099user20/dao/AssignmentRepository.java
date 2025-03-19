package uk.ac.standrews.cs.host.cs3099user20.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AssignmentRepository {

    public void vote(int articleID, int versionID, String userID, int vote, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs;
        int accept = 0;
        int reject = 0;
        System.out.println(userID);
        if(userID == null || userID == "") {throw new SQLException("User not found");}


        rs = statement.executeQuery("SELECT * FROM assignment WHERE assignment.ArticleID = '" + articleID + "'");
        while(rs.next()) {
            if(rs.getString("UserID").equals(userID) && rs.getInt("Voted") > 0) {throw new IllegalAccessError("User has already voted");}
            if (rs.getInt("Voted") == 1) {
                reject++;
            } else if (rs.getInt("Voted") == 2) {
                accept++;
            }
        }
            if(reject == 0) {
                if (vote == 1 && accept >= 1) {accept(articleID, versionID, connection);return;}
                update(articleID, userID, vote, connection);
                return;
            }
            if (vote == 0) {reject(articleID, connection);return;}

            if (accept == 1){accept(articleID, versionID, connection);return;}

            update(articleID, userID, vote, connection);

            statement.close();
    }




    private void accept(int articleID, int versionID, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        System.out.println("attatch");
        statement.executeUpdate("UPDATE assignment SET assignment.Voted = 2 WHERE assignment.ArticleID = '" + articleID + "'");
        statement.executeUpdate("UPDATE version SET Version = Version + 1 WHERE version.VersionID = '" + versionID + "'");
        statement.executeUpdate("UPDATE version SET version.SubVersion = 0 WHERE versionID = '" + versionID + "'");
        statement.close();
    }

    private void reject(int articleID, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("UPDATE assignment SET assignment.Voted = 1 WHERE assignment.ArticleID = '" + articleID + "'");
        statement.close();
    }

    private void update(int articleID, String userID, int vote, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        int v = vote+1;
        System.out.println("v" + v);
        statement.executeUpdate("UPDATE assignment SET Voted = " + v + " WHERE ArticleID = '" + articleID + "' AND UserID = '" + userID + "'");
        statement.close();
    }

}