package uk.ac.standrews.cs.host.cs3099user20.dao;

import uk.ac.standrews.cs.host.cs3099user20.model.DiscussionMessage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DiscussionRepository {

    public DiscussionMessage[] getDiscussionMessages(int versionID, Connection connection) throws SQLException {
        List<DiscussionMessage> discussion = new ArrayList<>();
        boolean error = false;

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT *, DATE_FORMAT(`TimeStamp`, '%Y-%m-%dT%TZ') AS iso FROM discussionMessage INNER JOIN user ON discussionMessage.Author = user.UserID WHERE VersionID = '" + versionID + "'");
            while (rs.next()) {
                int messageID = rs.getInt("DiscussionID");
                int parentID = rs.getInt("ParentID");
                String author = rs.getString("Author");
                String email = rs.getString("Email");
                String text = rs.getString("Text");
                String timestamp = rs.getString("iso");

                discussion.add(new DiscussionMessage(messageID, parentID, versionID, author, email,  text, timestamp));
            }

            int discussionSize = discussion.size();
            DiscussionMessage[] discussionMessages = new DiscussionMessage[discussionSize];
            for (int i=0; i<discussionSize; i++){
                discussionMessages[i] = discussion.get(i);
            }
        return discussionMessages;
    }

}